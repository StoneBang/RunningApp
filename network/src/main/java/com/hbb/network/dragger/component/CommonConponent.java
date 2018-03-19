package com.hbb.network.dragger.component;
import com.hbb.network.BaseActivity;
import com.hbb.network.dragger.module.PresentModule;
import dagger.Component;


/**
 * Created by Administrator on 2018/3/13.
 * 通用的组件接口
 */

@Component(modules = PresentModule.class)
public interface CommonConponent {
    void inject (BaseActivity baseActivity);

}
