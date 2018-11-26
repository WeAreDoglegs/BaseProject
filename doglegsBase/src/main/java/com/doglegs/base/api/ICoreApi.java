package com.doglegs.base.api;

import com.doglegs.base.bean.point.DeviceOperate;
import com.doglegs.core.base.BaseResponse;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ICoreApi {

    @POST("/b/api/v1/device/operate")
    Flowable<BaseResponse> deviceOperate(@Body DeviceOperate deviceOperate);

}
