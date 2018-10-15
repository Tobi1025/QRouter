package com.personal.joefly.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 页面跳转时将参数存入map进行传递
 * Created by qiaojingfei on 2018/8/1.
 */

public class JumpDataModel implements Serializable {
    public static String KEY = JumpDataModel.class.getSimpleName();
    private HashMap<String, String> data = new HashMap<>();

    private JumpDataModel() {
    }

    public static JumpDataModel getInstance() {
        return InnerFactory.jumpDataModel;
    }

    private static class InnerFactory {
        private static JumpDataModel jumpDataModel = new JumpDataModel();
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }
}
