package com.hbb.network;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hbb.network.base.BasePresent;
import com.hbb.network.dragger.component.DaggerCommonConponent;
import com.hbb.network.dragger.module.PresentModule;
import com.hbb.network.interfaces.IView;

import javax.inject.Inject;

import dagger.internal.DaggerCollections;


/**
 * Created by Administrator on 2018/3/14.
 */

public abstract class BaseActivity extends AppCompatActivity  implements IView {

    @Inject
    public BasePresent mBasePresent;

    public final  static String TAG="This  is my test";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerCommonConponent.
                builder().
                presentModule(new PresentModule(this)).
                build().
                inject(this);
        //test
//        mBasePresent.requestObserverable();

    }


}
