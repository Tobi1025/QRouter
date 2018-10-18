package com.personal.joefly.qrouter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.personal.joefly.qrouter.api.IPageRouterTable;
import com.personal.joefly.qrouter.core.AnnotationParse;
import com.personal.joefly.qrouter.core.UriHandler;
import com.personal.joefly.qrouter.intercepter.InterceptorQueue;
import com.personal.joefly.qrouter.intercepter.UriInterceptor;
import com.personal.joefly.qrouter.model.JumpDataModel;
import com.personal.joefly.qrouter.model.RouteActivityModel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * Created by qiaojingfei on 2018/8/1.
 */

public class RouterBuilder extends UriHandler {
    private static RouterBuilder builder;
    private static UriHandler uriHandler;
    private static final String TAG = RouterBuilder.class.getSimpleName();
    private static HashMap<String, Class<? extends Activity>> classMap = new HashMap<>();
    private static HashMap<String, InterceptorQueue> interceptorMap = new HashMap<>();
    private String scheme;
    private String host;
    private String port;
    private String path;
    private static WeakHashMap<String, String> stringMap;
    private static WeakHashMap<String, Integer> intMap;
    private static WeakHashMap<String, Boolean> booleanMap;
    private static WeakHashMap<String, Object> objMap;
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
        stringMap = new WeakHashMap<>();
        intMap = new WeakHashMap<>();
        booleanMap = new WeakHashMap<>();
        objMap = new WeakHashMap<>();
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

    public static void saveRouterClass(String path, Class<? extends Activity> activity, UriInterceptor... interceptors) {
        classMap.put(path, activity);
        InterceptorQueue interceptorQueue = new InterceptorQueue();
        for (UriInterceptor interceptor : interceptors) {
            interceptorQueue.addInterceptor(interceptor);
        }
        interceptorMap.put(path, interceptorQueue);
        RouteActivityModel.getInstance().setRouteActivityClassMap(classMap);
        RouteActivityModel.getInstance().setRouteActivityInterceptorMap(interceptorMap);
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
        stringMap.put(key, value);
        jumpDataModel.setStringMap(stringMap);
        return builder;
    }

    public RouterBuilder putIntExtra(String key, int value) {
        intMap.put(key, value);
        jumpDataModel.setIntMap(intMap);
        return builder;
    }

    public RouterBuilder putBooleanExtra(String key, boolean value) {
        booleanMap.put(key, value);
        jumpDataModel.setBooleanMap(booleanMap);
        return builder;
    }

    public RouterBuilder putObjectExtra(String key, Object value) {
        objMap.put(key, value);
        jumpDataModel.setObjectMap(objMap);
        return builder;
    }


}
