package com.example.home.service

import com.example.home.model.api.DescriptionApi
import com.example.home.model.entity.AgreeEntity
import com.example.request.video.RetrofitManager
import com.example.request.video.entity.ResponseData


import okhttp3.RequestBody

class DescriptionService {
    val api: DescriptionApi
    init {
        api = RetrofitManager.Companion.instance?.retrofit!!.create(DescriptionApi::class.java)
    }
    /*
    * 点赞评论
    * */
    suspend fun agree(body: RequestBody): ResponseData<AgreeEntity> {
        return api.agree(body)
    }
}