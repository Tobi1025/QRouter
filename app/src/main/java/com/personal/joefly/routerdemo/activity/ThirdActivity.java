package com.personal.joefly.routerdemo.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.personal.joefly.routerdemo.R;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        initData();
    }

    private void initData() {
        Uri uri = getIntent().getData();
        if (uri != null) {
            String name = uri.getQueryParameter("userName");
            String age = uri.getQueryParameter("userAge");
            Toast.makeText(this,
                    "uri拼接方式获取参数:"+"name = " + name + ",age = " + age,
                    Toast.LENGTH_LONG).show();
        }

    }
}
