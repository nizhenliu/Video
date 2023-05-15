package com.example.request.video



import com.example.request.video.entity.ResponseData
import com.example.request.video.entity.VideoEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/videosimple/getSimpleVideoByChannelId")
    suspend fun getSimpleVideoByChannelId(@Query("channelId")channelId:String,@Query("page")page:Int,@Query("pagesize")pageSize:Int): ResponseData<List<VideoEntity>>
}