package com.personal.joefly.model;

import android.app.Activity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 页面跳转时将参数存入map进行传递
 * Created by qiaojingfei on 2018/8/1.
 */

public class RouteActivityModel implements Serializable {
    public static String KEY = RouteActivityModel.class.getSimpleName();
    private HashMap<String, Class<? extends Activity>> routeActivityClassMap = new HashMap<>();

    public static RouteActivityModel getInstance() {
        return new RouteActivityModel();
    }

    public HashMap<String, Class<? extends Activity>> getRouteActivityClassMap() {
        return routeActivityClassMap;
    }

    public void setRouteActivityClassMap(HashMap<String, Class<? extends Activity>> routeActivityClass) {
        this.routeActivityClassMap = routeActivityClass;
    }


}
