package com.hbb.network;

import android.app.Application;
import android.content.Context;

import com.hbb.network.base.Constants;
import com.hbb.network.dragger.component.DaggerNetComponent;
import com.hbb.network.dragger.component.DaggerServiceComponent;
import com.hbb.network.dragger.component.NetComponent;
import com.hbb.network.dragger.component.ServiceComponent;
import com.hbb.network.dragger.module.AppModule;
import com.hbb.network.dragger.module.NetModule;
import com.hbb.network.dragger.module.ServiceModule;

/**
 * Created by Administrator on 2018/3/16.
 */

public class MyAppication extends Application {

    private NetComponent mDaggerNetComponent;

    private ServiceComponent mDaggerServiceComponent;

    public NetComponent getDaggerNetComponent() {
        return mDaggerNetComponent;
    }

    public ServiceComponent getDaggerServiceComponent() {
        return mDaggerServiceComponent;
    }

    public static Context sContext;
    @Override
    public void onCreate() {
        super.onCreate();

        sContext=this;

        mDaggerNetComponent = DaggerNetComponent.builder().
                appModule(new AppModule(this)).
                netModule(new NetModule(Constants.BASE_URL)).
                build();

        mDaggerServiceComponent = DaggerServiceComponent.builder().
                netComponent(mDaggerNetComponent).
                serviceModule(new ServiceModule()).
                build();

    }


}
