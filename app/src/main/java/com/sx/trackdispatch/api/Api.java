package com.sx.trackdispatch.api;

import com.sx.trackdispatch.model.DataBean;
import com.sx.trackdispatch.model.ProjectItem;
import com.sx.trackdispatch.model.ResponseBaseData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    // 总数据
    @GET("project/tree/json")
    Observable<ResponseBaseData<List<DataBean>>> getProject();  // 异步线程 耗时操作

    // ITem数据
    @GET("project/list/{pageIndex}/json") // ?cid=294
    Observable<ProjectItem> getProjectItem(@Path("pageIndex") int pageIndex, @Query("cid") int cid);  // 异步线程 耗时操作

}
