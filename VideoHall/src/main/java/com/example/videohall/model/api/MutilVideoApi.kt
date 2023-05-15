package com.example.videohall.model.api

import com.example.request.video.entity.ResponseData
import com.example.videohall.model.entity.MutilVideoEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface MutilVideoApi {
    @GET("/videomulti/getMutilVideoByTypeId")
    suspend fun getMutilVideoByTypeId(@Query("page") page:Int, @Query("pagesize") pagesize:Int, @Query("typeid") typeId:Int): ResponseData<List<MutilVideoEntity>>
    /**
     * 获取推荐信息接口
     */
    @GET("/videosimple/getRecommendSimpleVideo")
    suspend fun getRecommendMutilVideo(@Query("page") page:Int,@Query("pagesize") pagesize:Int):ResponseData<List<MutilVideoEntity>>
}