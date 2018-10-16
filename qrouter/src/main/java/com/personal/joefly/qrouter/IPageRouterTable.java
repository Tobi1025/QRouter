package com.personal.joefly.qrouter;

import android.content.Intent;

import com.personal.joefly.model.JumpDataModel;

/**
 * 路由表
 * Created by qiaojingfei on 2018/8/1.
 */

public interface IPageRouterTable {
    /**
     * 隐示路由跳转，通过Map进行参数传递
     * 优先解析注解，在解析builder里的Scheme，Host等
     *
     * @param path  目标页面路由path 隐示跳转path必须以"/"开头
     * @param model 内部封装了Map
     */
    @Action(Intent.ACTION_DEFAULT)
    @Scheme("content")
    @Host("jump")
    void webSkip(@Path String path, @RouterParam JumpDataModel model);

    /**
     * 显示路由跳转
     *
     * @param path  目标页面路由path
     * @param model 内部封装了Map
     */
    void originSkip(@Path String path, @RouterParam JumpDataModel model);

}
