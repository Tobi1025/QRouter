package com.personal.joefly.qrouter.intercepter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

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
    public void intercept(@NonNull Context context , @NonNull UriCallback callback) {
        next(mInterceptors.iterator(), context, callback);
    }

    private void next(@NonNull final Iterator<UriInterceptor> iterator, @NonNull final Context context,
                      @NonNull final UriCallback callback) {
        if (iterator.hasNext()) {
            UriInterceptor t = iterator.next();
            t.intercept(context, new UriCallback() {
                @Override
                public void onNext() {
                    Log.e("Interceptor","Interceptor执行");
                    next(iterator, context, callback);
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
