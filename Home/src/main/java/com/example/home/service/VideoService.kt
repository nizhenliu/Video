package com.example.home.service

import com.example.home.model.api.VideoApi
import com.example.home.model.entity.SimpleVideoEntity
import com.example.request.video.RetrofitManager
import com.example.request.video.entity.ResponseData


class VideoService {
    val api: VideoApi
    init {
        api= RetrofitManager.Companion.instance?.retrofit!!.create(VideoApi::class.java)
    }
    /*
    * 获取推荐的simple视频信息
    *
    * */

    /*
    *
    * 根据channeid获取对应的设配信息
    *
    * */

    /**
     * 根据ChannelId获取对应的适配信息
     */
    suspend fun getSimpleVideoByChannelId(channelId:String, page:Int, pagesize:Int): ResponseData<List<SimpleVideoEntity>> {
        return api.getSimpleVideoByChannelId(channelId,page,pagesize)
    }
    /*
    * 获取推荐的Simple视频信息
    * */
    suspend fun getRecommendSimpleVideo(page: Int,pagesize: Int):ResponseData<List<SimpleVideoEntity>>{
           return api.getRecommendSimpleVideo(page,pagesize)
    }

}