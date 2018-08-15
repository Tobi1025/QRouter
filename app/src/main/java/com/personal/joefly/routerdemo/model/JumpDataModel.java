package com.personal.joefly.routerdemo.model;

import java.util.HashMap;

/**
 * Created by qiaojingfei on 2018/8/1.
 */

public class JumpDataModel {
    private HashMap<String, String> data = new HashMap<>();
    private static final JumpDataModel ourInstance = new JumpDataModel();

    private JumpDataModel() {
    }

    public static JumpDataModel getInstance() {
        return ourInstance;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }
}
