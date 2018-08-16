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
    //通过Map传递单个或多个参数
    Class getDataModel() default JumpDataModel.class;
    //通过String传递单个或多个参数
    String value() default "" ;
}
