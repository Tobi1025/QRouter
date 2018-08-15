package com.personal.joefly.routerdemo.router;

import com.personal.joefly.routerdemo.model.JumpDataModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by qiaojingfei on 2018/8/1.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RouterParam {
//    int[] array() default {};
    Class getDataModel() default JumpDataModel.class;
    String value() default "" ;
}
