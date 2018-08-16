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
 * Created by qiaojingfei on 2018/8/1.
 */

public interface IPageRouterTable {
    @Action(Intent.ACTION_DEFAULT)
    @Scheme("content")
    @Host("jump")
    @Port("8080")
    @Path("/jumpSecondActivity")
    void skipSecondActivity(@RouterParam JumpDataModel model);

    /**
     *
     * @param name key:userName value name
     * @param age  key:userAge  value age
     */
    @Action(Intent.ACTION_DEFAULT)
    @Scheme("content")
    @Host("jump")
    @Path("/jumpThirdActivity")
    void skipThirdActvity(@RouterParam("userName") String name, @RouterParam("userAge") String age);
}
