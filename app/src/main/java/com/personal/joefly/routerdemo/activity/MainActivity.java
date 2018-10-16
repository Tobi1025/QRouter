package com.personal.joefly.routerdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.personal.joefly.qrouter.RouterBuilder;
import com.personal.joefly.routerdemo.R;

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
                RouterBuilder.getBuilder()
                        .putStringExtra(SecondActivity.userName, "web")
                        .putStringExtra(SecondActivity.userAge, "18")
                        .startWebUri(MainActivity.this, "/jumpSecondActivity");

            }
        });


        findViewById(R.id.btn_common_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterBuilder.getBuilder()
                        .putStringExtra(CommonRouterActivity.userName, "origin")
                        .putStringExtra(CommonRouterActivity.userAge, "22")
                        .startOriginUri(MainActivity.this, "commonRouterActivity");
            }
        });
    }

}
