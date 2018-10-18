package com.personal.joefly.qrouter.model;

import java.io.Serializable;
import java.util.WeakHashMap;

/**
 * 页面跳转时将参数存入map进行传递
 * Created by qiaojingfei on 2018/8/1.
 */

public class JumpDataModel implements Serializable {
//    public static String KEY = JumpDataModel.class.getSimpleName();
    private WeakHashMap<String, String> stringMap = new WeakHashMap<>();
    private WeakHashMap<String, Integer> intMap = new WeakHashMap<>();
    private WeakHashMap<String, Boolean> booleanMap = new WeakHashMap<>();
    private WeakHashMap<String, Object> objectMap = new WeakHashMap<>();

    private JumpDataModel() {
    }

    private static class InnerFactory {
        private static JumpDataModel jumpDataModel = new JumpDataModel();
    }

    public static JumpDataModel getInstance() {
        return InnerFactory.jumpDataModel;
    }

    public void setStringMap(WeakHashMap<String, String> stringMap) {
        this.stringMap = stringMap;
    }

    public String getStringExtra(String key) {
        return stringMap.containsKey(key) ? stringMap.get(key) : "";
    }

    public void setIntMap(WeakHashMap<String, Integer> intMap) {
        this.intMap = intMap;
    }

    public int getIntExtra(String key, int defaultValue) {
        return intMap.containsKey(key) ? intMap.get(key) : defaultValue;
    }

    public boolean getBooleanExtra(String key, boolean defaultValue) {
        return booleanMap.containsKey(key) ? booleanMap.get(key) : defaultValue;
    }

    public void setBooleanMap(WeakHashMap<String, Boolean> booleanMap) {
        this.booleanMap = booleanMap;
    }

    public <T> T getObjectExtra(String key, Class<T> clazz) {
        T retObject = null;
        if (objectMap.containsKey(key)) {
            retObject = clazz.cast(objectMap.get(key));
        }
        return retObject;
    }

    public void setObjectMap(WeakHashMap<String, Object> objectMap) {
        this.objectMap = objectMap;
    }


}
