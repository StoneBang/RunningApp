package com.hbb.network.dragger.component;


import android.content.SharedPreferences;


import com.hbb.network.dragger.module.AppModule;
import com.hbb.network.dragger.module.NetModule;
import com.hbb.network.dragger.scope.UserScope;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


@Singleton
@Component(modules={AppModule.class,NetModule.class})
public interface NetComponent {
    // downstream components need these exposed
    OkHttpClient okHttpClient();
    Retrofit retrofit();
    SharedPreferences sharedPreferences();
}