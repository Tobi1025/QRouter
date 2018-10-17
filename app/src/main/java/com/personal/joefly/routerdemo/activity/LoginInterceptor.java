package com.personal.joefly.routerdemo.activity;

import android.content.Context;
import android.support.annotation.NonNull;

import com.personal.joefly.qrouter.intercepter.UriCallback;
import com.personal.joefly.qrouter.intercepter.UriInterceptor;
/**
 * description: 登录拦截器
 * author: qiaojingfei
 * date: 2018/10/17 下午6:27
*/
public class LoginInterceptor implements UriInterceptor {
    @Override
    public void intercept(@NonNull Context context, @NonNull UriCallback callback) {
        //TODO 可进行跳转前的一些逻辑判断,如判断登录状态
        callback.onNext();
    }
}
