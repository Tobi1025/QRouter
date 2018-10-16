package com.personal.joefly.qrouter.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.personal.joefly.qrouter.RouterBuilder;
import com.personal.joefly.qrouter.api.RouterParam;
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
    private JumpDataModel paramsModel;
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
        Class<?> clazz;
        HashMap<String, Class<? extends Activity>> activityClassMap = RouteActivityModel.getInstance().getRouteActivityClassMap();
        if (activityClassMap.containsKey(targetActivityRoutePath)) {
            clazz = activityClassMap.get(targetActivityRoutePath);
            Intent intent = new Intent(context, clazz);
//            if (paramsModel != null) {
//                intent.putExtra(JumpDataModel.KEY, paramsModel);
//            }
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "path配置错误", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    /**
     * 执行隐示路由跳转
     *
     * @return
     */
    public boolean toWebRoute() {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(mAction, Uri.parse(url));
//        if (paramsModel != null) {
//            intent.putExtra(JumpDataModel.KEY, paramsModel);
//        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isValid = !activities.isEmpty();
        if (isValid) {
            context.startActivity(intent);
        }
        return isValid;
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
        }
        /*拼接主机参数*/
        Host host = method.getAnnotation(Host.class);
        if (null != host) {
            String value = host.value();
            url += "://";
            url += (TextUtils.isEmpty(value) ? builder.getHost() : value);
        }
        /*拼接端口参数*/
        Port port = method.getAnnotation(Port.class);
        if (null != port) {
            String value = port.value();
            url += ":";
            url += (TextUtils.isEmpty(value) ? builder.getPort() : value);
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
                    if (args[i] instanceof JumpDataModel) {
                        paramsModel = ((JumpDataModel) args[i]);
                    } else if (args[i] instanceof String) {
                        paramsModel = null;
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
                    targetActivityRoutePath = (String) args[i];
                    Log.e(TAG, "targetActivityRoutePath = " + targetActivityRoutePath);
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