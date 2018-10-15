package com.personal.joefly.qrouter;

import android.content.Context;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class UriHandler {
    private Map<Method, AnnotationParse> serviceMethodCache = new HashMap<>();
    private RouterBuilder builder;

    public void setBuilder(RouterBuilder builder) {
        this.builder = builder;
    }

    /**
     * 检查注解是否完成了解析
     *
     * @param context
     * @param method
     * @param args
     * @return
     */
    public AnnotationParse loadServiceMethod(Context context, Method method, Object[] args) {
        AnnotationParse serviceMethod = serviceMethodCache.get(method);
        if (null != serviceMethod)
            return serviceMethod;
        synchronized (serviceMethodCache) {
            serviceMethod = new AnnotationParse(builder);
            serviceMethodCache.put(method, serviceMethod);
        }
        serviceMethod.parseAnnotation(context, method, args);
        return serviceMethod;
    }
}
