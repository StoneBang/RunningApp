package com.hbb.coder.crimenalintent.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hbb.coder.crimenalintent.R;
import com.hbb.coder.crimenalintent.fragment.CrimeFragmet;
import com.hbb.coder.crimenalintent.util.StatusUtils;

import java.io.Serializable;
import java.util.UUID;

public class CrimeActivity extends AppCompatActivity {

    public  static  final String CRIME_UUID="crime_uuid";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        CrimeFragmet crimeFragmet = CreateCrimeFragmet();
        FragmentTransaction add = fragmentTransaction.add(R.id.activity_crime_fragment_container_, crimeFragmet);
        add.commit();

    }

    private CrimeFragmet CreateCrimeFragmet() {
        UUID uuid = (UUID) getIntent().getSerializableExtra(CRIME_UUID);
        return CrimeFragmet.newInstantce(uuid);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("CrimeActivity","CrimeActivity+onActivityResult");
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent=new Intent();
        intent.putExtra("ssss",1);
        setResult(RESULT_OK,intent);
        finish();
    }
}
