package com.hbb.coder.smartgeoponics;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.hbb.coder.citychoose.CityPickDialogFragment;
import com.hbb.coder.citychoose.CityPicker;
import com.hbb.coder.citychoose.bean.City;
import com.hbb.coder.citychoose.bean.HotCity;
import com.hbb.coder.citychoose.bean.LocateState;
import com.hbb.coder.citychoose.bean.LocatedCity;
import com.hbb.coder.citychoose.listener.OnPickListener;
import com.hbb.coder.citychoose.location.LocateCityService;
import com.hbb.coder.citychoose.utils.SharePerferenceUtils;
import com.hbb.coder.smartgeoponics.activity.Test1Activity;
import com.hbb.coder.smartgeoponics.activity.WeatherActivity;
import com.hbb.coder.smartgeoponics.utils.CalendarUtils;
import com.hbb.coder.smartgeoponics.utils.StatusUtils;
import com.hbb.coder.smartgeoponics.utils.StringUtils;
import com.hbb.coder.smartgeoponics.utils.ToastUtils;
import com.hbb.coder.smartgeoponics.utils.WeatherUtils;
import com.hbb.coder.widget.banner.Banner;
import com.hbb.coder.widget.banner.BannerConfig;
import com.hbb.coder.widget.banner.GlideImageLoader;
import com.hbb.coder.widget.banner.Transformer;
import com.hbb.network.BaseActivity;
import com.hbb.network.base.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 *
 */
public class MainActivity extends BaseActivity {


    private static final int CITY_REQUEST_CODE = 100;
    private static final int MAIN_REQUEST_CODE = 200;
    private TextView mCity;
    private List<HotCity> hotCities;
    private Banner mBaner;
    private ArrayList<String> mBannerList;
    private ArrayList<String> mBannerTitleList;
    private BroadcastReceiver mLocationBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == CityPickDialogFragment.MAIN_REQUEST_ACTION) {
                mMCityStr = intent.getStringExtra(Intent.EXTRA_TEXT);
                mCity.setText(mMCityStr);
                SharePerferenceUtils.setString(MainActivity.this, SharePerferenceUtils.locateCity, mMCityStr);
                mBasePresent.getWeatherForecast(mMCityStr, Constants.weatherUrl);
            }
        }
    };
    private String mMCityStr;
    private ImageView mWeatherImage;
    private TextView mWeatherTemp;
    private TextView mWeatherText;
    private TextView mWeatherWind;
    private TextView mWeek;
    private TextView mDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusUtils.translucentStatusBar(this);

    }

    @Override
    protected void requestData() {
        mBasePresent.getWeatherForecast(mMCityStr, Constants.weatherUrl);
    }

    @Override
    public View getSuccessView() {
        View inflate = View.inflate(MainActivity.this, R.layout.activity_main, null);
        mBaner = inflate.findViewById(R.id.banner);
        mCity = inflate.findViewById(R.id.textView7);
        mWeatherImage = inflate.findViewById(R.id.imageView5);
        mWeatherTemp = inflate.findViewById(R.id.textView9);
        mWeatherText = inflate.findViewById(R.id.textView12);
        mWeatherWind = inflate.findViewById(R.id.textView13);
        mWeek = inflate.findViewById(R.id.textView14);
        mDate = inflate.findViewById(R.id.textView15);
        initBroadCastAndCity();
        mBannerList = new ArrayList<>();
        mBannerTitleList = new ArrayList<>();
        mBannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527333809430&di=d279831a93cfeb4c958544a36121a62d&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F15%2F37%2F60M58PICcHi_1024.jpg");
        mBannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527333809430&di=8798d96966bf75de9f3c8954d5bbeb72&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F13%2F87%2F57%2F13858PICmvc_1024.jpg");
        mBannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527333809429&di=1d4f259bd2ea9dc497fa36213b506155&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F14%2F44%2F42Z58PICMGu_1024.jpg");
        mBannerTitleList.add("今天天气好晴朗");
        mBannerTitleList.add("这个地方没爆了");
        mBannerTitleList.add("约不?美丽的春天");

        mBaner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        mBaner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBaner.setImages(mBannerList);
        //设置banner动画效果
        mBaner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        mBaner.setBannerTitles(mBannerTitleList);
        //设置自动轮播，默认为true
        mBaner.isAutoPlay(true);
        //设置轮播时间
        mBaner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        mBaner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBaner.start();
        hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));
        return inflate;
    }

    private void initBroadCastAndCity() {
        registerReceiver(mLocationBroadcast, new IntentFilter(CityPickDialogFragment.MAIN_REQUEST_ACTION));

        myPermissionRequest(MAIN_REQUEST_CODE, CityPickDialogFragment.MAIN_REQUEST_ACTION);

//        if (getResources().getString(R.string.unknow).equals(SharePerferenceUtils.getString(MainActivity.this, SharePerferenceUtils.locateCity
//                , getResources().getString(R.string.unknow)))) {
//
//        } else {
//            mCity.setText(SharePerferenceUtils.getString(MainActivity.this,
//                    SharePerferenceUtils.locateCity, getResources().getString(R.string.unknow)));
//        }
    }


    /**
     * 动态请求权限，安卓手机版本在5.0以上时需要
     */
    public void myPermissionRequest(int requestCode, String intentAction) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查是否拥有权限，申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, requestCode);
            } else {
                location(intentAction);
            }
        } else {
            // 配置清单中已申明权限，作相应处理，此处正对sdk版本低于23的手机
            location(intentAction);
        }
    }

    private void location(String intentAction) {

        LocateCityService.startUploadImg(MainActivity.this, intentAction);

    }


    /**
     * 权限请求的返回结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case CITY_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 第一次获取到权限，请求定位
                    location(CityPickDialogFragment.CITY_REQUEST_ACTION);
                } else {
                    // 没有获取到权限，做特殊处理
                    Log.i("=========", "请求权限失败");
                    ToastUtils.showMessage("请求权限失败");
                }
                break;


            case MAIN_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 第一次获取到权限，请求定位
                    location(CityPickDialogFragment.MAIN_REQUEST_ACTION);
                } else {
                    // 没有获取到权限，做特殊处理
                    Log.i("=========", "请求权限失败");
                    ToastUtils.showMessage("请求权限失败");
                }

                break;

            default:
                break;
        }
    }

    public void cityClick(View view) {

        CityPicker.getInstance()
                .setFragmentManager(getSupportFragmentManager())
                .enableAnimation(true)
                .setAnimationStyle(R.style.DefaultCityPickerAnimation)
                .setLocatedCity(null)
                .setHotCities(hotCities)
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {

                        if (data != null) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    String.format("点击的数据：%s，%s", data.getName(), data.getCode()),
                                    Toast.LENGTH_SHORT)
                                    .show();

                            if (!StringUtils.isEmpty(data.getName())) {
                                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                                intent.putExtra(Intent.EXTRA_TEXT, data.getName());
                                startActivity(intent);
                            }

                        }

                    }

                    @Override
                    public void onLocate() {

                        myPermissionRequest(CITY_REQUEST_CODE,
                                CityPickDialogFragment.CITY_REQUEST_ACTION);

                    }
                })
                .show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mLocationBroadcast);
    }

    @Override
    public void success(HashMap<String, Object> object) {
        ArrayList<LinkedTreeMap<String, Object>> heWeather6 = (ArrayList<LinkedTreeMap<String, Object>>) object.get("HeWeather6");

        LinkedTreeMap<String, Object> currentForecast = (LinkedTreeMap<String, Object>)
                heWeather6.get(0);

        LinkedTreeMap<String, Object> nowDate = (LinkedTreeMap<String, Object>) currentForecast.get("now");

        //阴天
        String condTxt = StringUtils.buildString(nowDate.get("cond_txt"));
        String condCode = StringUtils.buildString(nowDate.get("cond_code"));

        //实时温度
        String tmpMax = StringUtils.buildString(nowDate.get("tmp"));
        //风力情况

        String windSc = StringUtils.buildString(nowDate.get("wind_dir"),
                nowDate.get("wind_sc") + "级");

        String week = CalendarUtils.getCurrentWeek();

        String date = CalendarUtils.getCurrentDate("yyyy-MM-dd");


        if (WeatherUtils.getWeatherType(condCode) == WeatherUtils.weather_sun) {
            mWeatherImage.setImageResource(R.drawable.sunshine);
        } else if (WeatherUtils.getWeatherType(condCode) == WeatherUtils.weather_cloudy) {

            mWeatherImage.setImageResource(R.drawable.cloudy);
        } else if (WeatherUtils.getWeatherType(condCode) == WeatherUtils.weather_rain) {
            mWeatherImage.setImageResource(R.drawable.rain);

        } else if (WeatherUtils.getWeatherType(condCode) == WeatherUtils.weather_snow) {

            mWeatherImage.setImageResource(R.drawable.snow);
        } else if (WeatherUtils.getWeatherType(condCode) == WeatherUtils.weather_thunder) {

            mWeatherImage.setImageResource(R.drawable.thunder);
        } else {
            mWeatherImage.setImageResource(R.drawable.sunshine);
        }

        mWeatherTemp.setText(tmpMax);
        mWeatherText.setText(condTxt);
        mWeatherWind.setText(windSc);
        mWeek.setText(week);
        mDate.setText(date);
    }

    @Override
    public void fail(Object object) {

    }
}
