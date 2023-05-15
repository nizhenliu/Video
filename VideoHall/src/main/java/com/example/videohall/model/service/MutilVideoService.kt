package com.example.videohall.model.service

import com.example.request.video.RetrofitManager
import com.example.request.video.entity.ResponseData
import com.example.videohall.model.api.MutilVideoApi
import com.example.videohall.model.entity.MutilVideoEntity
import retrofit2.create

class MutilVideoService {
    val api:MutilVideoApi
    init {
        api = RetrofitManager.instance?.retrofit!!.create(MutilVideoApi::class.java)
    }
    suspend fun getMutilVideoByTypeId(page:Int,pagesize:Int,typeid:Int): ResponseData<List<MutilVideoEntity>> {
        return api.getMutilVideoByTypeId(page,pagesize,typeid)
    }

    /**
     * 获取推荐的Simple视频信息
     */
    suspend fun getRecommendSimpleVideo(page:Int,pagesize:Int): ResponseData<List<MutilVideoEntity>> {
        return api.getRecommendMutilVideo(page,pagesize)
    }
}