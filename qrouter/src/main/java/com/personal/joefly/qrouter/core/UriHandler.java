package com.personal.joefly.qrouter.core;

import android.content.Context;

import com.personal.joefly.qrouter.RouterBuilder;

import java.lang.reflect.Method;

public class UriHandler {

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
        AnnotationParse serviceMethod = new AnnotationParse(builder);
        serviceMethod.parseAnnotation(context, method, args);
        return serviceMethod;
    }
}
