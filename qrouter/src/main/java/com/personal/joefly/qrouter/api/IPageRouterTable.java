package com.personal.joefly.qrouter.api;

import android.content.Intent;

import com.personal.joefly.qrouter.uri.Action;
import com.personal.joefly.qrouter.uri.Path;


/**
 * 路由表
 * Created by qiaojingfei on 2018/8/1.
 */

public interface IPageRouterTable {
    /**
     * 隐示路由跳转，通过Map进行参数传递
     * 优先解析注解，再解析builder里的Scheme，Host等
     *
     * @param path  目标页面路由path 隐示跳转path必须以"/"开头
     */
    @Action(Intent.ACTION_DEFAULT)
    void webSkip(@Path String path);

    /**
     * 显示路由跳转
     *
     * @param path  目标页面路由path
     */
    void originSkip(@Path String path);

}
