package com.personal.joefly.qrouter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.personal.joefly.model.JumpDataModel;
import com.personal.joefly.model.RouteActivityModel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiaojingfei on 2018/8/1.
 */

public class RouterBuilder extends UriHandler {
    private static UriHandler uriHandler;
    private static final String TAG = RouterBuilder.class.getSimpleName();
    private static RouterBuilder builder;
    private Map<Method, AnnotationParse> serviceMethodCache = new HashMap<>();
    private static HashMap<String, Class<? extends Activity>> classMap = new HashMap<>();
    private String scheme;
    private String host;
    private String port;
    private String path;

    public RouterBuilder() {
        super(builder);
    }

    public static void init() {
        if (builder == null) {
            builder = new RouterBuilder();
        }
        if (uriHandler == null) {
            uriHandler = new UriHandler(builder);
        }
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

    public static void startOriginUri(final Context context, String path, JumpDataModel jumpDataModel) {
        //实例化对应的接口类对象
        IPageRouterTable routerTable = (IPageRouterTable) Proxy.newProxyInstance(IPageRouterTable.class.getClassLoader(), new Class[]{IPageRouterTable.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                /*如果调用的是Object类中的方法,则直接调用*/
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }
                AnnotationParse annotationParse = uriHandler.loadServiceMethod(context, method, args);
                return annotationParse.toRoute();
            }
        });
        routerTable.originSkip(path, jumpDataModel);
    }

    public static void startWebUri(final Context context, String path, JumpDataModel jumpDataModel) {
        //实例化对应的接口类对象
        IPageRouterTable routerTable = (IPageRouterTable) Proxy.newProxyInstance(IPageRouterTable.class.getClassLoader(), new Class[]{IPageRouterTable.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                /*如果调用的是Object类中的方法,则直接调用*/
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }
                AnnotationParse annotationParse = uriHandler.loadServiceMethod(context, method, args);
                return annotationParse.toRoute();
            }
        });
        routerTable.webSkip(path, jumpDataModel);
    }
}
