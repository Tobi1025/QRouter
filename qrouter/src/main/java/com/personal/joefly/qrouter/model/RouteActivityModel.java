package com.personal.joefly.qrouter.model;

import android.app.Activity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 页面跳转时将参数存入map进行传递
 * Created by qiaojingfei on 2018/8/1.
 */

public class RouteActivityModel implements Serializable {
    private static RouteActivityModel routeActivityModel;
    private HashMap<String, Class<? extends Activity>> routeActivityClassMap = new HashMap<>();

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


}
