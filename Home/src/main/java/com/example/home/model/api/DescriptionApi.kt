package com.example.home.model.api

import com.example.request.video.entity.ResponseData
import com.example.home.model.entity.AgreeEntity


import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.PUT

interface DescriptionApi {
    /*
    * 点赞
    *
    * */
    @PUT("/agree/agree")
    suspend fun agree(@Body body: RequestBody): ResponseData<AgreeEntity>
}