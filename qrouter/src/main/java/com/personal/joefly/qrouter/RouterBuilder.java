package com.personal.joefly.qrouter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.personal.joefly.qrouter.api.IPageRouterTable;
import com.personal.joefly.qrouter.core.AnnotationParse;
import com.personal.joefly.qrouter.core.UriHandler;
import com.personal.joefly.qrouter.intercepter.InterceptorQueue;
import com.personal.joefly.qrouter.intercepter.UriInterceptor;
import com.personal.joefly.qrouter.model.JumpDataModel;
import com.personal.joefly.qrouter.model.RouteActivityModel;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by qiaojingfei on 2018/8/1.
 */

public class RouterBuilder extends UriHandler {
    private static RouterBuilder builder = RouterBuilderFactory.routerBuilder;
    private static final String TAG = RouterBuilder.class.getSimpleName();
    private static HashMap<String, Class<? extends Activity>> classMap = new HashMap<>();
    private static HashMap<String, InterceptorQueue> interceptorMap = new HashMap<>();
    private String scheme;
    private String host;
    private String port;
    private String path;
    //    private static WeakHashMap<String, String> stringMap;
//    private static WeakHashMap<String, Integer> intMap;
//    private static WeakHashMap<String, Boolean> booleanMap;
//    private static WeakHashMap<String, Object> objMap;
//    private static JumpDataModel jumpDataModel = JumpDataModel.getInstance();
    private static HashMap<String, Object> mFields;
    private static String mdefaultScheme;
    private static String mdefaultHost;

    private RouterBuilder() {
        mFields = new HashMap<>();
    }

    private static class RouterBuilderFactory {
        private static RouterBuilder routerBuilder = new RouterBuilder();
    }

    public static void register(String defaultScheme, String defaultHost) {
        mdefaultScheme = TextUtils.isEmpty(defaultScheme) ? "" : defaultScheme;
        mdefaultHost = TextUtils.isEmpty(defaultHost) ? "" : defaultHost;
        builder.scheme(mdefaultScheme);
        builder.host(mdefaultHost);
        try {
            Class<?> uriAnnotationInit = Class.forName("com.personal.joefly.qrouter.UriAnnotationInit");
            uriAnnotationInit.getMethod("routerInit").invoke(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static RouterBuilder getBuilder() {
        builder.scheme = mdefaultScheme;
        builder.host = mdefaultHost;
//        stringMap = new WeakHashMap<>();
//        intMap = new WeakHashMap<>();
//        booleanMap = new WeakHashMap<>();
//        objMap = new WeakHashMap<>();
        if (mFields != null) {
            mFields.clear();
        }
        return builder;
    }

    public RouterBuilder scheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    public RouterBuilder host(String host) {
        this.host = host;
        return this;
    }

    public RouterBuilder port(String port) {
        this.port = port;
        return this;
    }

    public RouterBuilder path(String path) {
        this.path = path;
        return this;
    }

    public String getScheme() {
        return scheme;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public static void saveRouterClass(String path, Class<? extends Activity> activity, UriInterceptor... interceptors) {
        classMap.put(path, activity);
        InterceptorQueue interceptorQueue = new InterceptorQueue();
        for (UriInterceptor interceptor : interceptors) {
            interceptorQueue.addInterceptor(interceptor);
        }
        interceptorMap.put(path, interceptorQueue);
        RouteActivityModel.getInstance().setRouteActivityClassMap(classMap);
        RouteActivityModel.getInstance().setRouteActivityInterceptorMap(interceptorMap);
    }

    public void startOriginUri(final Context context, String path) {
        //实例化对应的接口类对象
        IPageRouterTable routerTable = (IPageRouterTable) Proxy.newProxyInstance(IPageRouterTable.class.getClassLoader(), new Class[]{IPageRouterTable.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                /*如果调用的是Object类中的方法,则直接调用*/
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }
                AnnotationParse annotationParse = loadServiceMethod(RouterBuilder.this, context, method, args);
                return annotationParse.toOriginRoute();
            }
        });
        routerTable.originSkip(path);
    }

    public void startWebUri(final Context context, String path) {
        //实例化对应的接口类对象
        IPageRouterTable routerTable = (IPageRouterTable) Proxy.newProxyInstance(IPageRouterTable.class.getClassLoader(), new Class[]{IPageRouterTable.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                /*如果调用的是Object类中的方法,则直接调用*/
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }
                AnnotationParse annotationParse = loadServiceMethod(RouterBuilder.this, context, method, args);
                return annotationParse.toWebRoute();
            }
        });
        routerTable.webSkip(path);
    }

    /**
     * 用于startActivityForResult的requestCode
     *
     * @see Activity#startActivityForResult(Intent, int)
     */
    public RouterBuilder activityRequestCode(int requestCode) {
        putField(JumpDataModel.FIELD_REQUEST_CODE, requestCode);
        return this;
    }

    /**
     * 设置Activity切换动画
     *
     * @see Activity#overridePendingTransition(int, int)
     */
    public RouterBuilder overridePendingTransition(int enterAnim, int exitAnim) {
        putField(JumpDataModel.FIELD_START_ACTIVITY_ANIMATION,
                new int[]{enterAnim, exitAnim});
        return this;
    }

    /**
     * 设置Intent的Flags
     *
     * @see Intent#setFlags(int)
     */
    public RouterBuilder setIntentFlags(int flags) {
        putField(JumpDataModel.FIELD_START_ACTIVITY_FLAGS, flags);
        return this;
    }


    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, boolean value) {
        extra().putBoolean(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, byte value) {
        extra().putByte(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, char value) {
        extra().putChar(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, short value) {
        extra().putShort(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, int value) {
        extra().putInt(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, long value) {
        extra().putLong(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, float value) {
        extra().putFloat(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, double value) {
        extra().putDouble(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, String value) {
        extra().putString(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, CharSequence value) {
        extra().putCharSequence(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, Parcelable value) {
        extra().putParcelable(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, Parcelable[] value) {
        extra().putParcelableArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putIntentParcelableArrayListExtra(String name,
                                                           ArrayList<? extends Parcelable> value) {
        extra().putParcelableArrayList(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putIntentIntegerArrayListExtra(String name, ArrayList<Integer> value) {
        extra().putIntegerArrayList(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putIntentStringArrayListExtra(String name, ArrayList<String> value) {
        extra().putStringArrayList(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putIntentCharSequenceArrayListExtra(String name,
                                                             ArrayList<CharSequence> value) {
        extra().putCharSequenceArrayList(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, Serializable value) {
        extra().putSerializable(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, boolean[] value) {
        extra().putBooleanArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, byte[] value) {
        extra().putByteArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, short[] value) {
        extra().putShortArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, char[] value) {
        extra().putCharArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, int[] value) {
        extra().putIntArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, long[] value) {
        extra().putLongArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, float[] value) {
        extra().putFloatArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, double[] value) {
        extra().putDoubleArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, String[] value) {
        extra().putStringArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, CharSequence[] value) {
        extra().putCharSequenceArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtra(String name, Bundle value) {
        extra().putBundle(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public RouterBuilder putExtras(Bundle extras) {
        if (extras != null) {
            extra().putAll(extras);
        }
        return this;
    }


    @NonNull
    private synchronized Bundle extra() {
        Bundle extra = getField(Bundle.class, JumpDataModel.FIELD_INTENT_EXTRA, null);
        if (extra == null) {
            extra = new Bundle();
            putField(JumpDataModel.FIELD_INTENT_EXTRA, extra);
        }
        return extra;
    }

    public <T> T getField(@NonNull Class<T> clazz, @NonNull String key) {
        return getField(clazz, key, null);
    }

    private <T> T getField(@NonNull Class<T> clazz, @NonNull String key, T defaultValue) {
        Object field = mFields.get(key);
        if (field != null) {
            try {
                return clazz.cast(field);
            } catch (ClassCastException e) {
                Log.e(TAG, e.toString());
            }
        }
        return defaultValue;
    }

    /**
     * 设置Extra参数
     */
    private <T> void putField(@NonNull String key, T val) {
        if (val != null) {
            mFields.put(key, val);
        }
    }

}
