package com.hbb.network.base;

import android.util.Log;
import android.view.View;

import com.google.gson.internal.LinkedTreeMap;
import com.hbb.network.BaseActivity;
import com.hbb.network.ContentPage;
import com.hbb.network.Function.HttpResultFunc;
import com.hbb.network.MyAppication;
import com.hbb.network.interfaces.CommonServices;
import com.hbb.network.netmodel.CommonResponse;
import com.hbb.network.netmodel.Translater;

import java.util.ArrayList;
import java.util.HashMap;
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

public  class BasePresent {

    public final String TAG = "This  is  my test";

    public BaseActivity mBaseActivity;

    public BasePresent(BaseActivity baseActivity) {
        this.mBaseActivity = baseActivity;
        ((MyAppication) (mBaseActivity.getApplication())).
                getDaggerServiceComponent().inject(this);

    }
    @Inject
    public CommonServices mCommonServices;

    /**
     * 测试
     */
    public void request() {

        //步骤4:创建Retrofit对象
        Call<CommonResponse<Translater>> translateCall = RetrofitHelper.getInstance().
                getServices(CommonServices.class)
                .getTranslateCall("Hello ,My name is  REQUEST");

        //步骤6:发送网络请求(异步)
        translateCall.enqueue(new Callback<CommonResponse<Translater>>() {


            //请求成功时回调
            @Override
            public void onResponse(Call<CommonResponse<Translater>> call, Response<CommonResponse<Translater>> response) {
                Log.e(TAG, response.body().getData().getTranslateResult().get(0).get(0).getTgt());
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<CommonResponse<Translater>> call, Throwable throwable) {
                Log.e(TAG, "请求失败");
                System.out.println(throwable.getMessage());
            }
        });
    }

    /**
     * 测试
     */
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

//                        mBaseActivity.success(value.get(0).get(0).getTgt());

                    }

                    @Override
                    public void onError(Throwable e) {

//                        mBaseActivity.fail("requestObserverable 请求失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 请求天气
     */
    public void requestObserverable(Call<HashMap<String, Object>> weatherForecast) {

        weatherForecast.enqueue(new Callback<HashMap<String, Object>>() {
            @Override
            public void onResponse(Call<HashMap<String, Object>> call, Response<HashMap<String, Object>> response) {
                if (response.body() != null) {
                    ArrayList heWeather6 = (ArrayList) response.body().get("HeWeather6");
                    LinkedTreeMap<String, Object> majorDate = (LinkedTreeMap<String, Object>)
                            heWeather6.get(0);
                    String status = majorDate.get("status") + "";
                    if ("ok".equals(status)) {
                        mBaseActivity.success(response.body());
                        mBaseActivity.getContentPage().checkData(response.body());
                    } else {
                        mBaseActivity.fail("未请求到天气数据,请检查网络,稍后再试");
                        mBaseActivity.getContentPage().checkData(null);
                    }

                }

            }

            @Override
            public void onFailure(Call<HashMap<String, Object>> call, Throwable t) {
                mBaseActivity.fail(t.getMessage());
                mBaseActivity.getContentPage().checkData(null);
            }
        });
    }

    public void getWeatherForecast(String city) {
        HashMap<String, Object> weatherRequest = new HashMap<>();
        weatherRequest.put("location", city);
        weatherRequest.put("key", Constants.weatherKey);
        Call<HashMap<String, Object>> weatherForecast = mCommonServices.
                getWeatherForecast(Constants.weatherForecastUrl, weatherRequest);
        requestObserverable(weatherForecast);
    }
}
