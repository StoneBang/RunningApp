package com.hbb.coder.crimenalintent.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.hbb.coder.crimenalintent.R;
import com.hbb.coder.crimenalintent.fragment.CrimeListFragmet;
import com.hbb.coder.crimenalintent.util.StatusUtils;

public class CrimeListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crime_list);

//        StatusUtils.setStatusBarLightMode(this, Color.WHITE);

        CrimeListFragmet crimeListFragmet = new CrimeListFragmet();

        FragmentManager supportFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        FragmentTransaction add = fragmentTransaction.add
                (R.id.activity_crime_fragment_container, crimeListFragmet);

        add.commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("CrimeListActivity","CrimeListActivity+onActivityResult");
            if(data!=null){
                Log.e("CrimeListActivity","CrimeListActivity+"+data.getIntExtra("ssss",-1));
            }
    }
}
