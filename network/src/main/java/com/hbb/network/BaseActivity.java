package com.hbb.network;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hbb.network.base.BasePresent;
import com.hbb.network.dragger.component.DaggerCommonConponent;
import com.hbb.network.dragger.module.PresentModule;
import com.hbb.network.interfaces.IView;
import com.hbb.network.utils.ResourseUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import dagger.internal.DaggerCollections;


/**
 * Created by Administrator on 2018/3/14.
 */

public abstract class BaseActivity extends AppCompatActivity implements IView {

    @Inject
    public BasePresent mBasePresent;

    public final static String TAG = "This  is my test";

    public ContentPage mContentPage;
    private Toolbar mToolbar;
    private TextView mTitleIcon;
    private TextView mTitleText;


    public ContentPage getContentPage() {
        return mContentPage;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerCommonConponent.

                builder().
                presentModule(new PresentModule(this)).
                build().
                inject(this);


        mContentPage = new ContentPage(this) {
            @Override
            protected View createSuccessView() {
                return getSuccessView();
            }

            @Override
            protected void loadData() {
                requestData();
            }

        };

        setContentView(mContentPage);
        mToolbar=mContentPage.findViewById(R.id.toolbar);
        mTitleIcon = (TextView) findViewById(R.id.title_icon);
        mTitleText = (TextView) findViewById(R.id.title_text);
        initToolbar();

    }


    /**
     * 注意没有toolbar的页面
     */
    private void initToolbar() {
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            AppBarLayout.LayoutParams params = new AppBarLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int topPadding= ResourseUtils.getInternalDimensionSize(
                    getResources(), ResourseUtils.STATUS_BAR_HEIGHT);
//            mToolbar.setLayoutParams(params);
//            mToolbar.setPadding(0, 0, 0, 0);

            if (mTitleIcon != null) {
                mTitleIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }

        }
    }

    public void setTitleContent(String title) {
        mTitleText.setText(title);
    }

    /**
     * 获取请求的数据，每个界面自己去实现，已经在子线程调用了
     *
     * @return
     */
    protected abstract void requestData();


    /**
     * 获取成功的View，每个界面自己去实现
     *
     * @return
     */
    public abstract View getSuccessView();


    public void startMyActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(Intent.EXTRA_TEXT, "南京");
        startActivity(intent);
    }

}
