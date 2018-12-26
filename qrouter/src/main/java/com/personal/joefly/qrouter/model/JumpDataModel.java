package com.personal.joefly.qrouter.model;

import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

/**
 * 跳转时参数传递的配置
 * Created by qiaojingfei on 2018/8/1.
 */

public class JumpDataModel implements Serializable {
    private static String _PKG = "com.daojia.jz.router.activity.";
    /**
     * 附加到Intent的Extra，{@link Bundle} 类型
     */
    public static String FIELD_INTENT_EXTRA = _PKG + "intent_extra";

    /**
     * 用于startActivityForResult的requestCode，int类型
     *
     * @see android.app.Activity#startActivityForResult(Intent, int)
     */
    public static String FIELD_REQUEST_CODE = _PKG + "request_code";

    /**
     * 设置Activity切换动画，int[]类型，长度为2
     *
     * @see android.app.Activity#overridePendingTransition(int, int)
     */
    public static String FIELD_START_ACTIVITY_ANIMATION = _PKG + "animation";

    /**
     * 设置Intent的Flags，int型
     *
     * @see Intent#setFlags(int)
     */
    public static String FIELD_START_ACTIVITY_FLAGS = _PKG + "flags";

    private JumpDataModel() {
    }

    private static class InnerFactory {
        private static JumpDataModel jumpDataModel = new JumpDataModel();
    }

    public static JumpDataModel getInstance() {
        return InnerFactory.jumpDataModel;
    }


}
