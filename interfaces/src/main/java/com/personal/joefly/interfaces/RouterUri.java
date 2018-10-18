package com.personal.joefly.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by qiaojingfei on 2018/8/1.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RouterUri {
    /**
     * 路由path
     */
    String path() default "";
    /**
     * 要添加的interceptors
     */
    Class[] interceptors() default {};
}
