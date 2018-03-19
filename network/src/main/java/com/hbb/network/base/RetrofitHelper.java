package com.hbb.network.base;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 定义了一个请求的流程模式
 * RetrofitHelper.getInstance().getServices(Class<T>  clazz );
 */
public class RetrofitHelper {

    private Retrofit mRetrofit;


    private RetrofitHelper(){

        mRetrofit=createRetrofit();

    }


    public  static class RetrofitHelperSingle{

        public static RetrofitHelper retrofitHelper=new RetrofitHelper();

    }


    public static  RetrofitHelper getInstance(){

        return RetrofitHelperSingle.retrofitHelper;

    }


    private OkHttpClient createOkHttpClient() {
        final OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();

        /*httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request original = chain.request();
                final HttpUrl originalHttpUrl = original.url();

                final HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("username", "demo")
                        .build();
                final Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                final Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });*/

        return httpClient.build();
    }


    private Retrofit createRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient())
                .build();
    }


    /**
     * 用retrofit得到一个网络请求,以及响应(既Call或者Observerable)
     * @param clazz
     * @param <T>
     * @return
     */
    public <T>  T getServices(Class<T> clazz){

        return  mRetrofit.create(clazz);
    }



}
