package com.personal.joefly.routerdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.personal.joefly.qrouter.RouterBuilder;
import com.personal.joefly.routerdemo.R;

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
                RouterBuilder.getBuilder()
                        .putStringExtra(SecondActivity.userName, "web")
                        .putStringExtra(SecondActivity.userAge, "18")
                        .startWebUri(MainActivity.this, "/jumpSecondActivity");

            }
        });


        findViewById(R.id.btn_common_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map1 = new HashMap<>();
                map1.put("objMapKey1", "objMapValue1");
                HashMap<String, Integer> map2 = new HashMap<>();
                map2.put("objMapKey2", 2);
                RouterBuilder.getBuilder()
                        .putStringExtra(CommonRouterActivity.userName, "str-origin")
                        .putStringExtra(CommonRouterActivity.userAge, "str-22")
                        .putIntExtra("intKey1", 1)
                        .putIntExtra("intKey2", 2)
                        .putBooleanExtra("booleanKey1", true)
                        .putBooleanExtra("booleanKey2", false)
                        .putObjectExtra("objKey1", map1)
                        .putObjectExtra("objKey2", map2)
                        .startOriginUri(MainActivity.this, "commonRouterActivity");
            }
        });
    }

}
