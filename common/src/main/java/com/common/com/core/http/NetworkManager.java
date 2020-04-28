package com.common.com.core.http;



import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.common.com.core.WDPresenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 聂俊鹏
 * @date 2018/12/28 10:07
 * qq:2241659414
 */
public class NetworkManager {

    private static NetworkManager instance;
    private Retrofit app_retrofit,baidu_retrofit;
    private OkHttpClient okHttpClient;


    private NetworkManager(){
        init();
    }

    public static NetworkManager instance() {
        if (instance == null){
            instance = new NetworkManager();
        }
        return instance;
    }

    private void init(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//打印请求参数，请求结果

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)

                .connectTimeout(5,TimeUnit.SECONDS)
                .writeTimeout(5,TimeUnit.SECONDS)
                .readTimeout(5,TimeUnit.SECONDS)
                .build();

        app_retrofit = new Retrofit.Builder()
                .client(okHttpClient)
//                .baseUrl("http://169.254.101.220:8080/")//base_url:http+域名
//                .baseUrl("http://172.17.8.100/small/")//base_url:http+域名
                .baseUrl("http://mobile.bwstudent.com/small/")//base_url:http+域名
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用Rxjava对回调数据进行处理
                .addConverterFactory(GsonConverterFactory.create())//响应结果的解析器，包含gson，xml，protobuf
                .build();

        baidu_retrofit = new Retrofit.Builder()
                .client(okHttpClient)
//                .baseUrl("http://169.254.101.220:8080/")//base_url:http+域名
//                .baseUrl("http://172.17.8.100/small/")//base_url:http+域名
                .baseUrl("http://mobile.bwstudent.com/small/")//base_url:http+域名
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用Rxjava对回调数据进行处理
                .addConverterFactory(GsonConverterFactory.create())//响应结果的解析器，包含gson，xml，protobuf
                .build();
    }

    public <T> T create(int requestType ,final Class<T> service){
        if (requestType == WDPresenter.REQUEST_TYPE_SDK_BD){//如果请求百度SDK的接口
            return baidu_retrofit.create(service);
        }
        return app_retrofit.create(service);
    }








}
