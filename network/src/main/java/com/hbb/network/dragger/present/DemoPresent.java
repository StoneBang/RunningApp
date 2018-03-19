package com.hbb.network.dragger.present;

import android.util.Log;

import com.hbb.network.BaseActivity;
import com.hbb.network.Function.HttpResultFunc;
import com.hbb.network.base.BasePresent;
import com.hbb.network.base.RetrofitHelper;
import com.hbb.network.interfaces.CommonServices;
import com.hbb.network.netmodel.CommonResponse;
import com.hbb.network.netmodel.Translater;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/3/14.
 */

public class DemoPresent extends BasePresent {

    public DemoPresent(BaseActivity baseActivity) {
        super(baseActivity);
    }


}
