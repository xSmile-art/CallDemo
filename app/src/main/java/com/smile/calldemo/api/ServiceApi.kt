package com.smile.calldemo.api

import com.smile.calldemo.bean.BaseBean
import com.smile.calldemo.bean.Call
import com.smile.calldemo.bean.Event
import com.smile.calldemo.bean.FindTask
import com.smile.calldemo.bean.OfflineBean
import com.smile.calldemo.bean.Online
import com.smile.calldemo.bean.OnlineBean
import com.smile.calldemo.bean.OrderTask
import com.smile.calldemo.bean.OrderTaskInfo
import com.smile.calldemo.bean.TaskInfo
import com.smile.calldemo.common.utils.RetrofitUtils
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

val api: ServiceApi by lazy { RetrofitUtils.instance.createService(ServiceApi::class.java) }

interface ServiceApi {


    @POST("/woosh/device/callbox/Online")
    suspend fun online(@Body online: Online): BaseBean<OnlineBean>

    @POST("/woosh/device/callbox/Offline")
    suspend fun offline(@Body body: OfflineBean): BaseBean<OfflineBean>

    @POST("/woosh/device/callbox/Call")
    suspend fun call(@Body call: Call): BaseBean<Call>

    @POST("/woosh/dispatch/task/FindTask")
    suspend fun findTask(@Body task: FindTask): BaseBean<TaskInfo>

    @POST("/woosh/dispatch/task/TaskOrder")
    suspend fun orderTask(@Body task: OrderTask): BaseBean<OrderTaskInfo>
}