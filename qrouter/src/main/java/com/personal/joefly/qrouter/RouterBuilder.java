package com.personal.joefly.qrouter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.personal.joefly.model.RouteActivityModel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiaojingfei on 2018/8/1.
 */

public class RouterBuilder {
    private static final String TAG = RouterBuilder.class.getSimpleName();
    private static RouterBuilder builder;
    private Map<Method, AnnotationParse> serviceMethodCache = new HashMap<>();
    private static HashMap<String, Class<? extends Activity>> classMap = new HashMap<>();
    Context context;
    private String scheme;
    private String host;
    private String port;
    private String path;
    private String activityName;

    /**
     * 实例化对应的接口类对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> clazz) {
        validateServiceInterface(clazz);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                /*如果调用的是Object类中的方法,则直接调用*/
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }
                if (context instanceof Activity) {
                    activityName = ((Activity) context).getComponentName().getClassName();
                    Log.e(TAG, "ActivityName = " + activityName);
                }
                AnnotationParse annotationParse = loadServiceMethod(method, args);
                return annotationParse.toRoute();
            }
        });
    }


    /**
     * 检查注解是否完成了解析
     *
     * @param method
     * @param args
     * @return
     */
    AnnotationParse loadServiceMethod(Method method, Object[] args) {
        AnnotationParse serviceMethod = serviceMethodCache.get(method);
        if (null != serviceMethod)
            return serviceMethod;
        synchronized (serviceMethodCache) {
            serviceMethod = new AnnotationParse(this);
            serviceMethodCache.put(method, serviceMethod);
        }
        serviceMethod.parseAnnotation(method, args);
        return serviceMethod;
    }

    /**
     * 校验接口是否合法
     *
     * @param clazz 接口类的字节码
     * @param <T>
     */
    <T> void validateServiceInterface(Class<T> clazz) {
        if (!clazz.isInterface())
            throw new IllegalArgumentException("clazz must be a interface.");

        if (clazz.getInterfaces().length > 0)
            throw new IllegalArgumentException("clazz must be not extent other interface.");
    }

    public RouterBuilder(Context context) {
        this.context = context;
    }

    public static RouterBuilder getInstance(Context context) {
        if (builder != null) {
            return builder;
        }
        return new RouterBuilder(context);
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
}
