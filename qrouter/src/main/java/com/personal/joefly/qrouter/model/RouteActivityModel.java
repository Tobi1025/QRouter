package com.personal.joefly.qrouter.model;

import android.app.Activity;

import com.personal.joefly.qrouter.intercepter.InterceptorQueue;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 编译时存储所有注册过路由的Activity信息
 * Created by qiaojingfei on 2018/8/1.
 */

public class RouteActivityModel implements Serializable {
    private static RouteActivityModel routeActivityModel;
    private HashMap<String, Class<? extends Activity>> routeActivityClassMap = new HashMap<>();
    private HashMap<String, InterceptorQueue> routeActivityInterceptorMap = new HashMap<>();

    private RouteActivityModel() {
    }

    public static RouteActivityModel getInstance() {
        if (routeActivityModel == null) {
            synchronized (RouteActivityModel.class) {
                if (routeActivityModel == null) {
                    routeActivityModel = new RouteActivityModel();
                }
            }
        }

        return routeActivityModel;
    }

    public HashMap<String, Class<? extends Activity>> getRouteActivityClassMap() {
        return routeActivityClassMap;
    }

    public void setRouteActivityClassMap(HashMap<String, Class<? extends Activity>> routeActivityClass) {
        this.routeActivityClassMap = routeActivityClass;
    }


    public HashMap<String, InterceptorQueue> getRouteActivityInterceptorMap() {
        return routeActivityInterceptorMap;
    }

    public void setRouteActivityInterceptorMap(HashMap<String, InterceptorQueue> routeActivityInterceptorMap) {
        this.routeActivityInterceptorMap = routeActivityInterceptorMap;
    }
}
