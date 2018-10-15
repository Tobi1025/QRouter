package com.personal.joefly.routerdemo.routertable;

import android.content.Intent;

import com.personal.joefly.model.JumpDataModel;
import com.personal.joefly.qrouter.Action;
import com.personal.joefly.qrouter.Host;
import com.personal.joefly.qrouter.Path;
import com.personal.joefly.qrouter.RouterParam;
import com.personal.joefly.qrouter.Scheme;

/**
 * 路由表
 * Created by qiaojingfei on 2018/8/1.
 */

public interface IPageRouterTable {
    /**
     * 隐示路由跳转，通过Map进行参数传递
     *
     * @param model 内部封装了Map
     */
    @Action(Intent.ACTION_DEFAULT)
    @Scheme("content")
    @Host("jump")
    @Path("/jumpSecondActivity")
    void skipSecondActivity(@RouterParam JumpDataModel model);

    /**
     * 隐示路由跳转
     * 通过key - value  Uri拼接进行参数传递
     *
     * @param name key:userName value name
     * @param age  key:userAge  value age
     */
    @Action(Intent.ACTION_DEFAULT)
    @Scheme("content")
    @Host("jump")
    @Path("/jumpThirdActivity")
    void skipThirdActivity(@RouterParam("userName") String name, @RouterParam("userAge") String age);

    /**
     * 显示路由跳转
     *
     * @param path  目标页面路由path
     * @param model 内部封装了Map
     */
    void navigation( @Path String path, @RouterParam JumpDataModel model);

}
