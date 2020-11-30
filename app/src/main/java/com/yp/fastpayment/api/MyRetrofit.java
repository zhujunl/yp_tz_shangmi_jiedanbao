package com.yp.fastpayment.api;


import android.util.Log;

import com.yp.fastpayment.util.GsonUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 18682 on 2018/11/11.
 */

public class MyRetrofit {
    private static final String TAG = "MyRetrofit";
    public static String ipAddress = "https://binguoai.com/";

    public static String ipAddress2 = "https://tzapi.yunpengai.com/";
    public static String ipAddress3 = "https://tzapi.yunpengai.com/";
    /*public static Api getApiService() {


        Retrofit retrofit = new Retrofit.Builder()
                //设置OKHttpClient,如果不设置会提供一个默认的
                .client(genericClient())
                .baseUrl("https://binguoai.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                //添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api service = retrofit.create(Api.class);
        return service;
    }*/

    public static Api getApiService() {
        Log.d(TAG, "ipAddress==" + ipAddress);
        Retrofit retrofit = new Retrofit.Builder()
                //设置OKHttpClient,如果不设置会提供一个默认的
                .client(genericClient())
                .baseUrl(ipAddress)
                .addConverterFactory(ScalarsConverterFactory.create())
                //添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api service = retrofit.create(Api.class);
        return service;
    }

    public static Api getApiService2() {
        Log.d(TAG, "ipAddress==" + ipAddress2);
        Retrofit retrofit = new Retrofit.Builder()
                //设置OKHttpClient,如果不设置会提供一个默认的
                .client(genericClient())
                .baseUrl(ipAddress2)
                .addConverterFactory(ScalarsConverterFactory.create())
                //添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api service = retrofit.create(Api.class);
        return service;
    }
    public static Api getApiService3() {
        Log.d(TAG, "ipAddress==" + ipAddress3);
        Retrofit retrofit = new Retrofit.Builder()
                //设置OKHttpClient,如果不设置会提供一个默认的
                .client(genericClient())
                .baseUrl(ipAddress3)
                .addConverterFactory(ScalarsConverterFactory.create())
                //添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api service = retrofit.create(Api.class);
        return service;
    }

    public static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).
                readTimeout(10, TimeUnit.SECONDS).
                writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {

                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .build();

                        return chain.proceed(request);
                    }

                })
                .build();

        return httpClient;
    }


}
