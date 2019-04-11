package com.personal.joefly.qrouter.intercepter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * description: 支持添加多个子 {@link UriInterceptor} ，按先后顺序依次异步执行
 * author: qiaojingfei
 * date: 2018/10/17 下午5:08
 */
public class InterceptorQueue implements UriInterceptor {

    private final List<UriInterceptor> mInterceptors = new LinkedList<>();

    @SuppressWarnings("ConstantConditions")
    public void addInterceptor(@NonNull UriInterceptor interceptor) {
        if (interceptor != null) {
            mInterceptors.add(interceptor);
        }
    }

    @Override
    public void intercept(@NonNull Context context, Bundle bundle, @NonNull UriCallback callback) {
        next(mInterceptors.iterator(), context, bundle, callback);
    }

    private void next(@NonNull final Iterator<UriInterceptor> iterator, @NonNull final Context context,
                      final Bundle bundle, @NonNull final UriCallback callback) {
        if (iterator.hasNext()) {
            UriInterceptor t = iterator.next();
            t.intercept(context, bundle, new UriCallback() {
                @Override
                public void onNext() {
                    next(iterator, context, bundle, callback);
                }

                @Override
                public void onComplete() {
                    callback.onComplete();
                }
            });
        } else {
            callback.onNext();
        }
    }
}
