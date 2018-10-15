package com.personal.joefly.routerdemo.activity;

import android.app.Application;

import com.personal.joefly.qrouter.RouterBuilder;

public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RouterBuilder.init();
    }
}
