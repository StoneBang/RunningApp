package com.hbb.network.interfaces;

import com.hbb.network.netmodel.CommonResponse;
import com.hbb.network.netmodel.Translater;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/3/4.
 */

public interface CommonServices{

    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Observable<CommonResponse<List<List<Translater.TranslateResultBean>>>> getTranslateObservable(@Field("i") String targetSentence);

    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Call<CommonResponse<Translater>> getTranslateCall(@Field("i") String targetSentence);

}
