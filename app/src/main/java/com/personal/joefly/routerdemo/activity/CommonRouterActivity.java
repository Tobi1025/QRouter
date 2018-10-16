package com.personal.joefly.routerdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.personal.joefly.interfaces.RouterUri;
import com.personal.joefly.model.JumpDataModel;
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
        JumpDataModel jumpDataModel = (JumpDataModel) getIntent().getSerializableExtra(JumpDataModel.KEY);
        if (jumpDataModel != null) {
            HashMap<String, String> data = jumpDataModel.getData();
            String name = data.get(userName);
            String age = data.get(userAge);
            Toast.makeText(this,
                    "Map方式获取参数:" + userName + " = " + name + "," + userAge + " = " + age,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
