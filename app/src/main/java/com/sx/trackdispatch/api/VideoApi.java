package com.sx.trackdispatch.api;

import com.sx.trackdispatch.model.ResponseBaseData;
import com.sx.trackdispatch.model.VideoTokenBean;
import com.sx.trackdispatch.model.VidepResponseBase;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface VideoApi {
    public final static String url = "https://open.ys7.com/";
    // 总数据
    @POST("api/lapp/token/get")
    Observable<VidepResponseBase<VideoTokenBean>> accessToken(@Body RequestBody requestBody);


}
