package com.example.home.service

import com.example.home.model.api.VideoTypeApi
import com.example.home.model.entity.SimpleTypeEntity
import com.example.request.video.RetrofitManager
import com.example.request.video.entity.ResponseData

class VideoTypeService {
     var api: VideoTypeApi
    init {
        api = RetrofitManager.Companion.instance?.retrofit!!.create(VideoTypeApi::class.java)
    }
    /*
    * 获取视频类型信息
    *
    * */
    suspend fun getSimpleType(): ResponseData<List<SimpleTypeEntity>> {
        return api.getSimpleType()
    }
}