package com.personal.joefly.routerdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.personal.joefly.routerdemo.routertable.IPageRouterTable;
import com.personal.joefly.routerdemo.R;
import com.personal.joefly.routerdemo.model.JumpDataModel;
import com.personal.joefly.routerdemo.router.RouterBuilder;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_skip).setOnClickListener(new View.OnClickListener() {
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


    }
}
