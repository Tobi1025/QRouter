package com.personal.joefly.qrouter.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.personal.joefly.qrouter.RouterBuilder;
import com.personal.joefly.qrouter.api.RouterParam;
import com.personal.joefly.qrouter.intercepter.InterceptorQueue;
import com.personal.joefly.qrouter.intercepter.UriCallback;
import com.personal.joefly.qrouter.model.JumpDataModel;
import com.personal.joefly.qrouter.model.RouteActivityModel;
import com.personal.joefly.qrouter.uri.Action;
import com.personal.joefly.qrouter.uri.Host;
import com.personal.joefly.qrouter.uri.Path;
import com.personal.joefly.qrouter.uri.Port;
import com.personal.joefly.qrouter.uri.Scheme;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * Created by qiaojingfei on 2018/8/1.
 */

public class AnnotationParse {
    private Context context;
    private static final String TAG = AnnotationParse.class.getSimpleName();
    private String url = "";
    private String mAction = Intent.ACTION_VIEW;
    private RouterBuilder builder;
    private String targetActivityRoutePath;

    public AnnotationParse(RouterBuilder builder) {
        this.builder = builder;
    }

    public void parseAnnotation(Context context, Method method, Object[] args) {
        this.context = context;
        /*解析方法注解*/
        parseMethodAnnotation(method);
        /*解析方法参数注解*/
        parseParamsAnnotation(method, args);
    }

    /**
     * 执行显示路由跳转
     *
     * @return
     */
    public boolean toOriginRoute() {
        HashMap<String, InterceptorQueue> routeActivityInterceptorMap = RouteActivityModel.getInstance().getRouteActivityInterceptorMap();
        final HashMap<String, Class<? extends Activity>> activityClassMap = RouteActivityModel.getInstance().getRouteActivityClassMap();
        if (routeActivityInterceptorMap.containsKey(targetActivityRoutePath)) {
            routeActivityInterceptorMap.get(targetActivityRoutePath).intercept(context, new UriCallback() {
                @Override
                public void onNext() {
                    Log.e("Interceptor", targetActivityRoutePath + " 所有Interceptor已执行完成");
                    if (activityClassMap.containsKey(targetActivityRoutePath)) {
                        Class<?> clazz = activityClassMap.get(targetActivityRoutePath);
                        Intent intent = new Intent(context, clazz);
                        //intent参数
                        Bundle extra = builder.getField(Bundle.class, JumpDataModel.FIELD_INTENT_EXTRA);
                        if (extra != null) {
                            intent.putExtras(extra);
                        }
                        // Flags
                        Integer flags = builder.getField(Integer.class, JumpDataModel.FIELD_START_ACTIVITY_FLAGS);
                        if (flags != null) {
                            intent.setFlags(flags);
                        }
                        //requestCode
                        Integer requestCode = builder.getField(Integer.class, JumpDataModel.FIELD_REQUEST_CODE);
                        if (requestCode != null && context instanceof Activity) {
                            ((Activity) context).startActivityForResult(intent, requestCode);
                        } else {
                            context.startActivity(intent);
                        }
                        //Activity切换动画
                        int[] anim = builder.getField(int[].class, JumpDataModel.FIELD_START_ACTIVITY_ANIMATION);
                        if (context instanceof Activity && anim != null && anim.length == 2) {
                            ((Activity) context).overridePendingTransition(anim[0], anim[1]);
                        }
                    } else {
                        Log.e(TAG, "没有找到对应的path");
                    }
                }

                @Override
                public void onComplete() {
                    Log.e("Interceptor", targetActivityRoutePath + " Activity的Interceptor已手动结束");
                }
            });
        } else {
            Toast.makeText(context, "没有找到对应的path", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    /**
     * 执行隐示路由跳转
     *
     * @return
     */
    public boolean toWebRoute() {
        HashMap<String, InterceptorQueue> routeActivityInterceptorMap = RouteActivityModel.getInstance().getRouteActivityInterceptorMap();
        if (routeActivityInterceptorMap.containsKey(targetActivityRoutePath)) {
            routeActivityInterceptorMap.get(targetActivityRoutePath).intercept(context, new UriCallback() {
                @Override
                public void onNext() {
                    Log.e("Interceptor", targetActivityRoutePath + " 所有Interceptor已执行完成");
                    PackageManager packageManager = context.getPackageManager();
                    Intent intent = new Intent(mAction, Uri.parse(url));
                    //intent参数
                    Bundle extra = builder.getField(Bundle.class, JumpDataModel.FIELD_INTENT_EXTRA);
                    if (extra != null) {
                        intent.putExtras(extra);
                    }
                    // Flags
                    Integer flags = builder.getField(Integer.class, JumpDataModel.FIELD_START_ACTIVITY_FLAGS);
                    if (flags != null) {
                        intent.setFlags(flags);
                    }
                    List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
                    boolean isValid = !activities.isEmpty();
                    if (isValid) {
                        //requestCode
                        Integer requestCode = builder.getField(Integer.class, JumpDataModel.FIELD_REQUEST_CODE);
                        if (requestCode != null && context instanceof Activity) {
                            ((Activity) context).startActivityForResult(intent, requestCode);
                        } else {
                            context.startActivity(intent);
                        }
                    }

                    //Activity切换动画
                    int[] anim = builder.getField(int[].class, JumpDataModel.FIELD_START_ACTIVITY_ANIMATION);
                    if (context instanceof Activity && anim != null && anim.length == 2) {
                        ((Activity) context).overridePendingTransition(anim[0], anim[1]);
                    }
                }

                @Override
                public void onComplete() {
                    Log.e("Interceptor", targetActivityRoutePath + " Activity的Interceptor已手动结束");
                }
            });
        }
        return true;
    }


    /**
     * 解析方法注解
     *
     * @param method
     */
    public void parseMethodAnnotation(Method method) {
        /*解析Action*/
        Action action = method.getAnnotation(Action.class);
        if (null != action) {
            mAction = action.value();
        }
        /* Uri: Scheme + Host + Port + Path*/

        /*拼接协议参数*/
        Scheme scheme = method.getAnnotation(Scheme.class);
        if (null != scheme) {
            String value = scheme.value();
            url += (TextUtils.isEmpty(value) ? builder.getScheme() : value);
        } else {
            url += builder.getScheme();
        }
        /*拼接主机参数*/
        Host host = method.getAnnotation(Host.class);
        if (null != host) {
            String value = host.value();
            url += "://";
            url += (TextUtils.isEmpty(value) ? builder.getHost() : value);
        } else {
            url += "://";
            url += builder.getHost();
        }
        /*拼接端口参数*/
        Port port = method.getAnnotation(Port.class);
        if (null != port) {
            String value = port.value();
            url += ":";
            url += (TextUtils.isEmpty(value) ? builder.getPort() : value);
        } else {
            if (!TextUtils.isEmpty(builder.getPort())) {
                url += ":";
                url += builder.getPort();
            }
        }
        /*拼接路径参数*/
//        Path path = method.getAnnotation(Path.class);
//        if (null != path) {
//            String value = path.value();
//            routerPath = TextUtils.isEmpty(value) ? builder.getPath() : value;
//            url += routerPath;
//        }

    }

    /**
     * 解析方法参数注解
     *
     * @param method
     */
    private void parseParamsAnnotation(Method method, Object[] args) {
        Annotation[][] annotations = method.getParameterAnnotations();
        StringBuilder reqParamsBuilder = new StringBuilder();
        for (int i = 0; i < annotations.length; i++) {
            Annotation[] annotationsArrays = annotations[i];
            for (int j = 0; j < annotationsArrays.length; j++) {
                Annotation annotationsItem = annotationsArrays[j];
                if (annotationsItem instanceof RouterParam) {
                    if (args[i] instanceof String) {
                        if (i == 0) {
                            reqParamsBuilder.append("?");
                        } else {
                            reqParamsBuilder.append("&");
                        }
                        /*添加Key*/
                        reqParamsBuilder.append(((RouterParam) annotationsItem).value());
                        reqParamsBuilder.append("=");
                        /*添加Value*/
                        reqParamsBuilder.append(args[i]);
                    }
                } else if (annotationsItem instanceof Path) {
                    if (args[i] instanceof String) {
                        targetActivityRoutePath = (String) args[i];
                        Log.e(TAG, "targetActivityRoutePath = " + targetActivityRoutePath);
                    } else {
                        Log.e(TAG, "targetActivityRoutePath : path 类型错误");
                    }
                }
            }

        }
        if (!TextUtils.isEmpty(targetActivityRoutePath)) {
            url += targetActivityRoutePath;
        }
        url += reqParamsBuilder.toString();
        Log.e("uri===", url);

    }

}
