package com.sx.trackdispatch.api;

import com.sx.trackdispatch.model.DataBean;
import com.sx.trackdispatch.model.DeviceInfo;
import com.sx.trackdispatch.model.IMToken;
import com.sx.trackdispatch.model.LoginToken;
import com.sx.trackdispatch.model.ProjectBean;
import com.sx.trackdispatch.model.ProjectItem;
import com.sx.trackdispatch.model.ResponseBaseData;
import com.sx.trackdispatch.model.TransferOrderBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    // 查询所有项目
//    @GET("/admin/project")
//    Observable<ResponseBaseData<ArrayList<ProjectBean>>> getProject(@Query("page") int page, @Query("limit") int limit);

    // 查询所有项目
    @GET("/admin/projectDispatch")
    Observable<ResponseBaseData<ArrayList<TransferOrderBean>>> getProjectDispatch(@Query("page") int page, @Query("limit") int limit);

    // 查询所有项目
    @PUT("/admin/projectDispatch")
    Observable<ResponseBaseData<String>> putProjectDispatch(@Body RequestBody body);

    @POST("/admin/im/login")
    Observable<IMToken> getIMToken(@Body RequestBody requestBody);

    @POST("/admin/org/login")
    Observable<ResponseBaseData<LoginToken>> getToken(@Body RequestBody requestBody);

    //添加车载主控
    @POST("/app/trainMaster/addOrUpdate")
    Observable<ResponseBaseData<DeviceInfo>> addOrUpdate(@Body RequestBody requestBody);

    // 查询工单模板
    @GET("/app/trainMaster/workOrderSetSecurityEvent")
    Observable<ResponseBaseData<ArrayList<ProjectBean>>> workOrderSetSecurityEvent();

    // 获取编组内所有车载主控
    @GET("/app/trainMaster/marshallingTrainMaster")
    Observable<ResponseBaseData<ArrayList<ProjectBean>>> marshallingTrainMaster();

    // 在调令中获取当前编组
    @GET("/app/trainMaster/queryTrainMarshalling")
    Observable<ResponseBaseData<ArrayList<ProjectBean>>> queryTrainMarshalling();

    // 根据用户ID获取调令
    @GET("/app/trainMaster/userGetProjectDispatch")
    Observable<ResponseBaseData<ArrayList<ProjectBean>>> userGetProjectDispatch();

    // 根据mac获取调令
    @GET("/app/trainMaster/queryProjectDispatch")
    Observable<ResponseBaseData<ArrayList<ProjectBean>>> queryProjectDispatch();

    // 根据调令ID修改调令状态
    @GET("/app/trainMaster/updateConfirmOrReject")
    Observable<ResponseBaseData<ArrayList<ProjectBean>>> updateConfirmOrReject();

    // 车载主控重新设置项目
    @GET("/app/trainMaster/updateProject")
    Observable<ResponseBaseData<String>> updateProject(@Query("mac") String mac, @Query("projectId") String projectId);


    // 查询所有项目
    @GET("/app/trainMaster/projectList")
    Observable<ResponseBaseData<ArrayList<ProjectBean>>> getProject();

    // 车载主控添加项目
    @PUT("/app/trainMaster/trainMasterAddProject")
    Observable<ResponseBaseData<String>> trainMasterAddProject(@Query("mac") String mac, @Query("projectId") String projectId);
}
