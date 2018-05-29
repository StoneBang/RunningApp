package com.hbb.network;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hbb.network.base.BasePresent;
import com.hbb.network.dragger.component.DaggerCommonConponent;
import com.hbb.network.dragger.module.PresentModule;
import com.hbb.network.interfaces.IView;

import java.util.HashMap;

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


    public  void startMyActivity(Class clazz){
        Intent intent = new Intent(this, clazz);
        intent.putExtra(Intent.EXTRA_TEXT,"南京");
        startActivity(intent);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mContentPage.startAnimator();
//    }
//
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mContentPage.resetAnimator();
//    }
}
