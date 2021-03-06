package com.sx.base.net;

import com.example.common.Constants;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtil {

    private static final String TAG = "HttpUtils";

    /**
     * 默认 test-a环境
     */
//    public static String BASE_URL = "http://10.0.1.15:9000/";
    public static String BASE_URL = Constants.SERVER_URL;

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    /**
     * 根据各种配置创建出Retrofit
     *
     * @return 返回创建好的Retrofit
     */
    public static Retrofit getRetrofit() {
        // OKHttp客户端
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        // 各种参数配置
        OkHttpClient okHttpClient = httpBuilder
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new HttpInterceptor())
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(okHttpClient)
                // 添加一个json解析的工具
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                // 添加rxjava处理工具
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 根据各种配置创建出Retrofit
     *
     * @return 返回创建好的Retrofit
     */
    public static Retrofit getRetrofitNoHeader() {
        // OKHttp客户端
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        // 各种参数配置
        OkHttpClient okHttpClient = httpBuilder
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new HttpNoHeaderInterceptor())
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(okHttpClient)
                // 添加一个json解析的工具
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                // 添加rxjava处理工具
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 根据各种配置创建出Retrofit
     * @return 返回创建好的Retrofit
     */
    public static Retrofit getRetrofit(String url) {
        // OKHttp客户端
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        // 各种参数配置
        OkHttpClient okHttpClient = httpBuilder
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new HttpInterceptor())
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder().baseUrl(url)
                // TODO 请求用 OKhttp
                .client(okHttpClient)
                // TODO 响应RxJava
                // 添加一个json解析的工具
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                // 添加rxjava处理工具
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
