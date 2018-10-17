package com.personal.joefly.qrouter.intercepter;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 拦截URI跳转并做处理，支持异步操作。
 *
 * Created by jzj on 2017/4/11.
 */
public interface UriInterceptor {

    /**
     * 处理完成后，要调用 {@link UriCallback#onNext()} 或 {@link UriCallback#onComplete()} 方法
     */
    void intercept(@NonNull Context context, @NonNull UriCallback callback);
}
