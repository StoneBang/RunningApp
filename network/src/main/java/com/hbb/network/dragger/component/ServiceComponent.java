package com.hbb.network.dragger.component;

import com.hbb.network.BaseActivity;
import com.hbb.network.base.BasePresent;
import com.hbb.network.dragger.module.ServiceModule;
import com.hbb.network.dragger.scope.UserScope;
import com.hbb.network.interfaces.CommonServices;

import javax.inject.Scope;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2018/3/15.
 */
@UserScope
@Component(dependencies = NetComponent.class,modules = ServiceModule.class)

public interface ServiceComponent {

    void inject(BasePresent basePresent);

}
