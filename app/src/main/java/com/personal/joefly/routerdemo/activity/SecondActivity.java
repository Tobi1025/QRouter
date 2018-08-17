package com.personal.joefly.routerdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.personal.joefly.routerdemo.R;
import com.personal.joefly.routerdemo.model.JumpDataModel;
import com.personal.joefly.routerdemo.router.RouterBuilder;
import com.personal.joefly.routerdemo.routertable.IPageRouterTable;

import java.util.HashMap;

public class SecondActivity extends AppCompatActivity {
    public static String userName = "name";
    public static String userAge = "age";
    private String paramValue1;
    private String paramValue2;
    private Button btn_goto_third_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
        initData();
    }

    private void initView() {
        btn_goto_third_page = findViewById(R.id.btn_goto_third_page);
        btn_goto_third_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterBuilder.getInstance(SecondActivity.this)
                        .create(IPageRouterTable.class)
                        .skipThirdActvity("luffy", "18");
            }
        });
    }

    private void initData() {
        JumpDataModel jumpDataModel = (JumpDataModel) getIntent().getSerializableExtra(JumpDataModel.KEY);
        if (jumpDataModel != null) {
            HashMap<String, String> data = jumpDataModel.getData();
            paramValue1 = data.get(userName);
            paramValue2 = data.get(userAge);
            Toast.makeText(this,
                    "Map方式获取参数:" + userName + " = " + paramValue1 + "," + userAge + " = " + paramValue2,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
