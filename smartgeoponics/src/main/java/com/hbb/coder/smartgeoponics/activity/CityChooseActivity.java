package com.hbb.coder.smartgeoponics.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.hbb.coder.smartgeoponics.R;
import com.hbb.coder.smartgeoponics.customerview.SideBar;
import com.hbb.coder.smartgeoponics.database.CityBean;
import com.hbb.coder.smartgeoponics.database.CityLabel;
import com.hbb.network.BaseActivity;

import java.util.HashMap;
import java.util.List;

/**
 * 城市选择
 */
public class CityChooseActivity extends BaseActivity {

    private List<CityBean> mCityList;
    private TextView mSlideTip;
    private SideBar mSideBar;
    private RecyclerView mCityRecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_choose);
        initView();
        CityLabel.getCrimeLabel(CityChooseActivity.this).copyDBFile();
        mCityList = CityLabel.getCrimeLabel(CityChooseActivity.this).getCityList();
        mSideBar.setTipView(mSlideTip);

    }

    private void initView() {
        mSlideTip = findViewById(R.id.side_tip);
        mSideBar = findViewById(R.id.city_side);
        mCityRecycle = findViewById(R.id.city_recycle);
    }

    @Override
    public void success(HashMap<String, Object> object) {

    }

    @Override
    public void fail(Object object) {

    }
}
