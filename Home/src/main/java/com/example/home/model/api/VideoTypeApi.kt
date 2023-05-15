package com.example.home.model.api

import com.example.home.model.entity.SimpleTypeEntity
import com.example.request.video.entity.ResponseData


import retrofit2.http.GET

interface VideoTypeApi {
    /*
    *
    * 获取视频类型信息接口
    *
    * */
    @GET("/videotype/getSimpleType")
    suspend fun getSimpleType(): ResponseData<List<SimpleTypeEntity>>
}