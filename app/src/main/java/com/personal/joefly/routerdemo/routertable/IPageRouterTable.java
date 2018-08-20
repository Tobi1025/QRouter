package com.personal.joefly.routerdemo.routertable;

import android.content.Intent;

import com.personal.joefly.routerdemo.model.JumpDataModel;
import com.personal.joefly.routerdemo.router.Action;
import com.personal.joefly.routerdemo.router.Host;
import com.personal.joefly.routerdemo.router.Path;
import com.personal.joefly.routerdemo.router.Port;
import com.personal.joefly.routerdemo.router.RouterParam;
import com.personal.joefly.routerdemo.router.Scheme;

/**
 * 路由表
 * Created by qiaojingfei on 2018/8/1.
 */

public interface IPageRouterTable {
    /**
     * 通过序列化对象，进行参数传递
     * @param model 内部封装了Map
     */
    @Action(Intent.ACTION_DEFAULT)
    @Scheme("content")
    @Host("jump")
    @Port("8080")
    @Path("/jumpSecondActivity")
    void skipSecondActivity(@RouterParam JumpDataModel model);

    /**
     *通过key - value 进行参数传递
     * @param name key:userName value name
     * @param age  key:userAge  value age
     */
    @Action(Intent.ACTION_DEFAULT)
    @Scheme("content")
    @Host("jump")
    @Path("/jumpThirdActivity")
    void skipThirdActivity(@RouterParam("userName") String name, @RouterParam("userAge") String age);
}
