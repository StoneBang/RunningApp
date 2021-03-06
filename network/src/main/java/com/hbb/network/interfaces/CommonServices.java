package com.hbb.network.interfaces;

import com.hbb.network.base.Constants;
import com.hbb.network.netmodel.CommonResponse;
import com.hbb.network.netmodel.Translater;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

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



    @GET
    Call<HashMap<String,Object>> getWeatherForecast(@Url String  url,
                                                    @QueryMap HashMap<String,Object> map);
    //大文件时要加不然会OOM
    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);

}
