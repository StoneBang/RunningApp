package com.hbb.coder.smartgeoponics;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hbb.coder.citychoose.CityPicker;
import com.hbb.coder.citychoose.bean.City;
import com.hbb.coder.citychoose.bean.HotCity;
import com.hbb.coder.citychoose.listener.OnPickListener;
import com.hbb.coder.citychoose.location.LocateCityService;
import com.hbb.coder.smartgeoponics.activity.WeatherActivity;
import com.hbb.coder.smartgeoponics.utils.StatusUtils;
import com.hbb.coder.smartgeoponics.utils.StringUtils;
import com.hbb.coder.smartgeoponics.utils.ToastUtils;
import com.hbb.coder.widget.banner.Banner;
import com.hbb.coder.widget.banner.BannerConfig;
import com.hbb.coder.widget.banner.GlideImageLoader;
import com.hbb.coder.widget.banner.Transformer;
import com.hbb.network.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 *
 */
public class MainActivity extends AppCompatActivity {


    private static final int BAIDU_ACCESS_COARSE_LOCATION = 100;
    private List<HotCity> hotCities;
    private Banner mBaner;
    private ArrayList<String> mBannerList;
    private ArrayList<String> mBannerTitleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusUtils.translucentStatusBar(this);
        mBaner = findViewById(R.id.banner);
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
    }




    /**
     * 动态请求权限，安卓手机版本在5.0以上时需要
     */
    public void myPermissionRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查是否拥有权限，申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, BAIDU_ACCESS_COARSE_LOCATION);
            } else {
                // 已拥有权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                location();
            }
        } else {
            // 配置清单中已申明权限，作相应处理，此处正对sdk版本低于23的手机
            location();
        }
    }

    private void location() {

        LocateCityService.startUploadImg(MainActivity.this);
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
            case BAIDU_ACCESS_COARSE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 第一次获取到权限，请求定位
                    location();
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

                            if(!StringUtils.isEmpty(data.getName())){
                                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                                intent.putExtra(Intent.EXTRA_TEXT,data.getName());
                                startActivity(intent);
                            }

                        }

                    }

                    @Override
                    public void onLocate() {

                        myPermissionRequest();

                    }
                })
                .show();

    }
}
