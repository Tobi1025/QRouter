package com.personal.joefly.routerdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.personal.joefly.interfaces.RouterUri;
import com.personal.joefly.qrouter.model.JumpDataModel;
import com.personal.joefly.routerdemo.R;

@RouterUri(path = "/jumpSecondActivity")
public class SecondActivity extends AppCompatActivity {
    //页面跳转时传递的参数KEY值
    public static String userName = "user_name";
    public static String userAge = "user_age";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initData();
    }

    private void initData() {
        JumpDataModel jumpDataModel = JumpDataModel.getInstance();
        if (jumpDataModel != null) {
            String name = jumpDataModel.getStringExtra(userName);
            String age = jumpDataModel.getStringExtra(userAge);
            Log.e("JumpData==",
                    "str1 = " + name + ",str2 = " + age
            );
        }
    }
}
