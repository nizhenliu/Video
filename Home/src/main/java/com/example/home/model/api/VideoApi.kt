package com.example.home.model.api

import com.example.request.video.entity.ResponseData
import com.example.home.model.entity.SimpleVideoEntity


import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApi {
    /*
    * 获取推荐信息接口
    *
    * */
    @GET("/videosimple/getRecommendSimpleVideo")
    suspend fun getRecommendSimpleVideo(@Query("page") page: Int,@Query("pagesize")pagesize: Int): ResponseData<List<SimpleVideoEntity>>
    /**
     * 获取视频信息接口
     */
    @GET("/videosimple/getSimpleVideoByChannelId")
    suspend fun getSimpleVideoByChannelId(@Query("channelId") channelId:String,@Query("page") page:Int,@Query("pagesize") pagesize:Int): ResponseData<List<SimpleVideoEntity>>
}