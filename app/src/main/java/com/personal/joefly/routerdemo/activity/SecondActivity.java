package com.personal.joefly.routerdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.personal.joefly.routerdemo.R;
import com.personal.joefly.routerdemo.model.JumpDataModel;
import com.personal.joefly.routerdemo.router.RouterBuilder;
import com.personal.joefly.routerdemo.routertable.IPageRouterTable;

import java.util.HashMap;

public class SecondActivity extends AppCompatActivity {
    //页面跳转时传递的参数KEY值
    public static String userName = "user_name";
    public static String userAge = "user_age";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setListener();
        initData();
    }

    private void setListener() {
        findViewById(R.id.btn_goto_third_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterBuilder.getInstance(SecondActivity.this)
                        .create(IPageRouterTable.class)
                        .skipThirdActivity("luffy", "18");
            }
        });
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
