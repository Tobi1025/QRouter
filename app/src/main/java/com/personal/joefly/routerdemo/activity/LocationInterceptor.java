package com.personal.joefly.routerdemo.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.personal.joefly.qrouter.intercepter.UriCallback;
import com.personal.joefly.qrouter.intercepter.UriInterceptor;

/**
 * description: 定位拦截器
 * author: qiaojingfei
 * date: 2018/10/17 下午6:27
 */
public class LocationInterceptor implements UriInterceptor {
    @Override
    public void intercept(@NonNull Context context, @NonNull UriCallback callback) {
        //TODO 可进行跳转前的一些逻辑判断,如获取位置信息
        Log.e("Interceptor", "定位拦截器执行");
        callback.onNext();
    }
}
