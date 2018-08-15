package com.personal.joefly.routerdemo.router;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.personal.joefly.routerdemo.model.JumpDataModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * Created by qiaojingfei on 2018/8/1.
 */

public class AnnotationParse {
    private String url = "";
    private String mAction = Intent.ACTION_VIEW;
    private PackageManager packageManager;
    private RouterBuilder builder;

    public AnnotationParse(RouterBuilder builder) {
        this.builder = builder;
    }

    public void parseAnnotation(Method method, Object[] args) {
        /*解析方法注解*/
        parseMethodAnnotation(method);
        /*解析方法参数注解*/
        parseParamsAnnotation(method, args);
    }

    /**
     * 执行路由跳转
     *
     * @return
     */
    public boolean toRoute() {
        PackageManager packageManager = builder.applicationContext.getPackageManager();
        Intent intent = new Intent(mAction, Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isValid = !activities.isEmpty();
        if (isValid) {
            builder.applicationContext.startActivity(intent);
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

        /*RouteUri: Scheme + Host + Port + Path*/
//        RouteUri routeUri = method.getAnnotation(RouteUri.class);
//        if (null != routeUri) {
//            url += routeUri.routeUri();
//            return;
//        }
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
        Path path = method.getAnnotation(Path.class);
        if (null != path) {
            String value = path.value();
            url += (TextUtils.isEmpty(value) ? builder.getPath() : value);
        }
    }

    /**
     * 解析方法参数注解
     *
     * @param method
     */
    public void parseParamsAnnotation(Method method, Object[] args) {
        HashMap<String, String> params = new HashMap<>();
        /**/
        Annotation[][] annotations = method.getParameterAnnotations();
        StringBuilder reqParamsBuilder = new StringBuilder();
        for (int i = 0; i < annotations.length; i++) {
            Annotation[] annotationsArrays = annotations[i];
            if (annotationsArrays.length > 0) {
                Annotation annotationsItem = annotationsArrays[0];
//                if (!(annotationsItem instanceof RouterParam))
//                    break;
//                if (i == 0) {
//                    reqParamsBuilder.append("?");
//                } else {
//                    reqParamsBuilder.append("&");
//                }
//                /*添加Key*/
//                reqParamsBuilder.append(((RouterParam) annotationsItem).getDataModel());
//                reqParamsBuilder.append("=");
//                /*添加Value*/
//                reqParamsBuilder.append(args[i]);
                if (annotationsItem instanceof RouterParam) {
                    params = ((JumpDataModel) args[0]).getData();
                }
            }
        }
        Log.e("params", params.toString());
//        url += reqParamsBuilder.toString();

    }

}
