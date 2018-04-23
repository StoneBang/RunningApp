package com.hbb.coder.crimenalintent.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hbb.coder.crimenalintent.R;
import com.hbb.coder.crimenalintent.util.StatusUtils;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusUtils.setStatusBarColor(this, Color.MAGENTA);
//        StatusUtils.translucentStatusBar(this, false);
        setContentView(R.layout.activity_test);
    }
}
