package com.hbb.coder.smartgeoponics;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.hbb.coder.citychoose.CityPicker;
import com.hbb.coder.citychoose.bean.City;
import com.hbb.coder.citychoose.bean.HotCity;
import com.hbb.coder.citychoose.bean.LocateState;
import com.hbb.coder.citychoose.bean.LocatedCity;
import com.hbb.coder.citychoose.listener.OnPickListener;
import com.hbb.coder.smartgeoponics.activity.WeatherActivity;
import com.hbb.network.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class MainActivity extends BaseActivity {


    private List<HotCity> hotCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));
    }


    @Override
    public void success(HashMap<String, Object> object) {

    }

    @Override
    public void fail(Object object) {

    }

    public void testClick(View view) {
        CityPicker.getInstance()
                .setFragmentManager(getSupportFragmentManager())
                .enableAnimation(true)
                .setAnimationStyle(R.style.DefaultCityPickerAnimation)
                .setLocatedCity(null)
                .setHotCities(hotCities)
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {

                            Toast.makeText(
                                    getApplicationContext(),
                                    String.format("点击的数据：%s，%s", data.getName(), data.getCode()),
                                    Toast.LENGTH_SHORT)
                                    .show();

                    }

                    @Override
                    public void onLocate() {
                        //开始定位，这里模拟一下定位
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                CityPicker.getInstance().locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
                            }
                        }, 1000);
                    }
                })
                .show();
    }
}
