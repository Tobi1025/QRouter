package com.personal.joefly.qrouter.intercepter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * description: 拦截URI跳转并做处理
 * author: qiaojingfei
 * date: 2018/10/17 下午6:28
*/
public interface UriInterceptor {

    /**
     * 处理完成后，要调用 {@link UriCallback#onNext()} 或 {@link UriCallback#onComplete()} 方法
     */
    void intercept(@NonNull Context context, Bundle bundle, @NonNull UriCallback callback);
}
