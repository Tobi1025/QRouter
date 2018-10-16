package com.personal.joefly.routerdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.personal.joefly.interfaces.RouterUri;
import com.personal.joefly.qrouter.model.JumpDataModel;
import com.personal.joefly.routerdemo.R;

import java.util.HashMap;

@RouterUri(path = "commonRouterActivity")
public class CommonRouterActivity extends AppCompatActivity {
    //页面跳转时传递的参数KEY值
    public static String userName = "user_name";
    public static String userAge = "user_age";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_router);
        initData();
    }

    private void initData() {
        JumpDataModel jumpDataModel = JumpDataModel.getInstance();
        if (jumpDataModel != null) {
            String name = jumpDataModel.getStringExtra(userName);
            String age = jumpDataModel.getStringExtra(userAge);
            int intValue1 = jumpDataModel.getIntExtra("intKey1", -1);
            int intValue2 = jumpDataModel.getIntExtra("intKey2", -1);
            boolean bValue1 = jumpDataModel.getBooleanExtra("booleanKey1", false);
            boolean bValue2 = jumpDataModel.getBooleanExtra("booleanKey2", false);
            HashMap map1 = jumpDataModel.getObjectExtra("objKey1", HashMap.class);
            HashMap map2 = jumpDataModel.getObjectExtra("objKey2", HashMap.class);
            Log.e("JumpData==",
                    "str1 = " + name + ",str2 = " + age
                            + ",int1 = " + intValue1 + ",int2 = " + intValue2
                            + ",b1 = " + bValue1 + ",b2 = " + bValue2
                            + ",map1 = " + map1.toString() + ",map2 = " + map2.toString());
        }
    }
}
