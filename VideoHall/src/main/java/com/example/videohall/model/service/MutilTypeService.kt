package com.example.videohall.model.service

import com.example.request.video.RetrofitManager
import com.example.request.video.entity.ResponseData
import com.example.videohall.model.api.MutilTypeApi
import com.example.videohall.model.entity.MutilTypeEntity
import retrofit2.create

class MutilTypeService {
    val api: MutilTypeApi

    init {
        api = RetrofitManager.instance?.retrofit!!.create(MutilTypeApi::class.java)
    }

    /**
     * 获取Mutil类别
     */
    suspend fun getMutilType(): ResponseData<List<MutilTypeEntity>>{
        return api.getMutilType()
    }

    suspend fun getSubMutilType(pid:Int): ResponseData<List<MutilTypeEntity>>{
        return api.getSubMutilType(pid)
    }
}