package com.hbb.network.dragger.module;

import com.hbb.network.interfaces.CommonServices;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/3/15.
 */

@Module
public class ServiceModule {


    @Provides
    public CommonServices providesCommonInterface(Retrofit retrofit) {
        return retrofit.create(CommonServices.class);
    }
}
