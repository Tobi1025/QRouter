package com.personal.joefly.routerdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.personal.joefly.routerdemo.routertable.ISecondPageRouter;
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
                HashMap<String, String> param = new HashMap<>();
                param.put("name", "joker");
                param.put("age", "18");
                JumpDataModel.getInstance().setData(param);

                RouterBuilder.getInstance(MainActivity.this)
                        .create(ISecondPageRouter.class)
                        .skip(JumpDataModel.getInstance());
            }
        });


    }
}
