package com.hbb.network.dragger.module;


import com.hbb.network.BaseActivity;
import com.hbb.network.base.BasePresent;
import com.hbb.network.dragger.present.DemoPresent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2018/3/13.
 */

@Module
public class PresentModule {

    private BaseActivity mBaseActivity;

    public PresentModule(BaseActivity baseActivity) {
        this.mBaseActivity = baseActivity;
    }

    @Provides
    public BasePresent getBasePresent() {

        return new BasePresent(mBaseActivity);
    }

}
