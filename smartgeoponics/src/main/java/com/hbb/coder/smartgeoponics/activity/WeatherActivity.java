package com.hbb.coder.smartgeoponics.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.hbb.coder.smartgeoponics.R;
import com.hbb.coder.smartgeoponics.adapter.WeatherRecyckeAdapter;
import com.hbb.coder.smartgeoponics.utils.StatusUtils;
import com.hbb.coder.smartgeoponics.utils.ToastUtils;
import com.hbb.network.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class WeatherActivity extends BaseActivity {

    private RecyclerView mWeatherRecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusUtils.translucentStatusBar(this);
        setContentView(R.layout.activity_weather);
        mWeatherRecycle = findViewById(R.id.weather_recycle);
        mBasePresent.getWeatherForecast("南京市");
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
