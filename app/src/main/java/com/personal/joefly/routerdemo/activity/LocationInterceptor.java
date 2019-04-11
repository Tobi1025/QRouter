package com.personal.joefly.routerdemo.activity;

import android.content.Context;
import android.os.Bundle;
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
    private static final String TAG = LocationInterceptor.class.getSimpleName();

    @Override
    public void intercept(@NonNull Context context, Bundle bundle, @NonNull UriCallback callback) {
        //TODO 可进行跳转前的一些逻辑判断,如获取位置信息
        Log.e(TAG, "定位拦截器执行");
        if (bundle != null) {
            Log.e(TAG, "userAge = " + bundle.getString("user_age"));
        }
        callback.onNext();
    }
}
