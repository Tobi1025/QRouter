package com.personal.joefly.qrouter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.personal.joefly.qrouter.api.IPageRouterTable;
import com.personal.joefly.qrouter.core.AnnotationParse;
import com.personal.joefly.qrouter.core.UriHandler;
import com.personal.joefly.qrouter.model.JumpDataModel;
import com.personal.joefly.qrouter.model.RouteActivityModel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * Created by qiaojingfei on 2018/8/1.
 */

public class RouterBuilder extends UriHandler {
    private static RouterBuilder builder;
    private static UriHandler uriHandler;
    private static final String TAG = RouterBuilder.class.getSimpleName();
    private static HashMap<String, Class<? extends Activity>> classMap = new HashMap<>();
    private String scheme;
    private String host;
    private String port;
    private String path;
    private static HashMap<String, String> params ;
    private static JumpDataModel jumpDataModel = JumpDataModel.getInstance();

    public static void register(String defaultScheme, String defaultHost) {
        if (builder == null) {
            builder = new RouterBuilder();
            String scheme = TextUtils.isEmpty(defaultScheme) ? "" : defaultScheme;
            String host = TextUtils.isEmpty(defaultHost) ? "" : defaultHost;
            builder.scheme(scheme);
            builder.host(host);
        }
        if (uriHandler == null) {
            uriHandler = new UriHandler();
            uriHandler.setBuilder(builder);
        }
        try {
            Class<?> uriAnnotationInit = Class.forName("com.personal.joefly.qrouter.UriAnnotationInit");
            uriAnnotationInit.getMethod("routerInit").invoke(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static RouterBuilder getBuilder() {
        params = new HashMap<>();
        return builder;
    }

    public RouterBuilder scheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    public RouterBuilder host(String host) {
        this.host = host;
        return this;
    }

    public RouterBuilder port(String port) {
        this.port = port;
        return this;
    }

    public RouterBuilder path(String path) {
        this.path = path;
        return this;
    }

    public String getScheme() {
        return scheme;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public static void saveRouterClass(String path, Class<? extends Activity> activity) {
        classMap.put(path, activity);
        RouteActivityModel.getInstance().setRouteActivityClassMap(classMap);
    }

    public void startOriginUri(final Context context, String path) {
        //实例化对应的接口类对象
        IPageRouterTable routerTable = (IPageRouterTable) Proxy.newProxyInstance(IPageRouterTable.class.getClassLoader(), new Class[]{IPageRouterTable.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                /*如果调用的是Object类中的方法,则直接调用*/
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }
                AnnotationParse annotationParse = uriHandler.loadServiceMethod(context, method, args);
                return annotationParse.toOriginRoute();
            }
        });
        routerTable.originSkip(path, jumpDataModel);
    }

    public void startWebUri(final Context context, String path) {
        //实例化对应的接口类对象
        IPageRouterTable routerTable = (IPageRouterTable) Proxy.newProxyInstance(IPageRouterTable.class.getClassLoader(), new Class[]{IPageRouterTable.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                /*如果调用的是Object类中的方法,则直接调用*/
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }
                AnnotationParse annotationParse = uriHandler.loadServiceMethod(context, method, args);
                return annotationParse.toWebRoute();
            }
        });
        routerTable.webSkip(path, jumpDataModel);
    }

    public RouterBuilder putStringExtra(String key, String value) {
        params.put(key, value);
        jumpDataModel.setData(params);
        return builder;
    }

    public RouterBuilder putObjectExtra(String key, Object obj) {
        return builder;
    }

}
