package com.hbb.coder.smartgeoponics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.internal.LinkedTreeMap;
import com.hbb.coder.smartgeoponics.R;
import com.hbb.coder.smartgeoponics.adapter.WeatherRecyckeAdapter;
import com.hbb.coder.smartgeoponics.utils.StatusUtils;
import com.hbb.coder.smartgeoponics.utils.ToastUtils;
import com.hbb.network.BaseActivity;
import com.hbb.network.base.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class Test10Activity extends BaseActivity {

    private RecyclerView mWeatherRecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusUtils.translucentStatusBar(this);


    }

    @Override
    protected void requestData() {
        if(getIntent()!=null){
            String city = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            mBasePresent.getWeatherForecast(city, Constants.weatherForecastUrl);
        }
    }


    @Override
    public View getSuccessView() {
        View inflate = View.inflate(Test10Activity.this, R.layout.activity_weather,
                null);
        mWeatherRecycle = inflate.findViewById(R.id.weather_recycle);
        inflate.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyActivity(Test11Activity.class);
            }
        });
        return inflate;
    }

    @Override
    public void success(HashMap<String, Object> object) {


        ArrayList heWeather6 = (ArrayList) object.get("HeWeather6");

        ArrayList<LinkedTreeMap<String, Object>> daily_forecast = (ArrayList<LinkedTreeMap<String, Object>>)
                ((LinkedTreeMap<String, Object>) heWeather6.get(0)).get("daily_forecast");

        WeatherRecyckeAdapter weatherRecyckeAdapter = new WeatherRecyckeAdapter(daily_forecast);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mWeatherRecycle.setLayoutManager(linearLayoutManager);

        mWeatherRecycle.setAdapter(weatherRecyckeAdapter);

    }

    @Override
    public void fail(Object object) {
        ToastUtils.showMessage("请求失败"+object);
    }


}
