package com.personal.joefly.qrouter.core;

import android.content.Context;

import com.personal.joefly.qrouter.RouterBuilder;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class UriHandler {
    private Map<Method, AnnotationParse> serviceMethodCache = new HashMap<>();

    /**
     * 解析注解
     *
     * @param builder
     * @param context
     * @param method
     * @param args
     * @return
     */
    public AnnotationParse loadServiceMethod(RouterBuilder builder, Context context, Method method, Object[] args) {
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
