package com.personal.joefly.routerdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.personal.joefly.routerdemo.R;
import com.personal.joefly.routerdemo.model.JumpDataModel;
import com.personal.joefly.routerdemo.router.RouterBuilder;
import com.personal.joefly.routerdemo.routertable.IPageRouterTable;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListener();
    }

    private void setListener() {
        findViewById(R.id.btn_map_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> params = new HashMap<>();
                params.put(SecondActivity.userName, "joker");
                params.put(SecondActivity.userAge, "18");
                JumpDataModel jumpDataModel = JumpDataModel.getInstance();
                jumpDataModel.setData(params);

                RouterBuilder.getInstance(MainActivity.this)
                        .create(IPageRouterTable.class)
                        .skipSecondActivity(jumpDataModel);

            }
        });

        findViewById(R.id.btn_uri_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterBuilder.getInstance(MainActivity.this)
                        .create(IPageRouterTable.class)
                        .skipThirdActivity("luffy", "18");
            }
        });


        findViewById(R.id.btn_common_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> params = new HashMap<>();
                params.put(CommonRouterActivity.userName, "solo");
                params.put(CommonRouterActivity.userAge, "22");
                JumpDataModel jumpDataModel = JumpDataModel.getInstance();
                jumpDataModel.setData(params);

                RouterBuilder.getInstance(MainActivity.this)
                        .create(IPageRouterTable.class)
                        .navigation(CommonRouterActivity.class.getName(), jumpDataModel);
            }
        });
    }

}
