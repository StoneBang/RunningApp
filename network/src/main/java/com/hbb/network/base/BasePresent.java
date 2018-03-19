package com.hbb.network.base;

import android.util.Log;

import com.hbb.network.BaseActivity;
import com.hbb.network.Function.HttpResultFunc;
import com.hbb.network.MyAppication;
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

public class BasePresent{

    public  final String TAG = "This  is  my test";

    public  BaseActivity mBaseActivity;


    public BasePresent(BaseActivity baseActivity) {
        this.mBaseActivity=baseActivity;
        ((MyAppication)(mBaseActivity.getApplication())).
                getDaggerServiceComponent().inject(this);
    }



    @Inject
    CommonServices mCommonServices;




    public void request() {

        //步骤4:创建Retrofit对象
        Call<CommonResponse<Translater>> translateCall = RetrofitHelper.getInstance().
                getServices(CommonServices.class)
                .getTranslateCall("Hello ,My name is  REQUEST");

        //步骤6:发送网络请求(异步)
        translateCall.enqueue(new Callback<CommonResponse<Translater>>(){



            //请求成功时回调
            @Override
            public void onResponse(Call<CommonResponse<Translater>> call, Response<CommonResponse<Translater>> response) {
                Log.e(TAG, response.body().getData().getTranslateResult().get(0).get(0).getTgt());
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<CommonResponse<Translater>> call, Throwable throwable) {
                Log.e(TAG,"请求失败");
                System.out.println(throwable.getMessage());
            }
        });
    }

    public void requestObserverable() {

        //步骤4:创建Retrofit对象
        Observable<CommonResponse<List<List<Translater.TranslateResultBean>>>> translateCall =
//                RetrofitHelper.getInstance().
//                getServices(CommonServices.class).
                mCommonServices.
                getTranslateObservable("Hello ,I  love  you  forever");

        translateCall.map(new HttpResultFunc<List<List<Translater.TranslateResultBean>>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<List<Translater.TranslateResultBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<List<Translater.TranslateResultBean>> value) {

                        mBaseActivity.success(value.get(0).get(0).getTgt());

                    }

                    @Override
                    public void onError(Throwable e) {

                        mBaseActivity.fail("requestObserverable 请求失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
