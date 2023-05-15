package com.example.videohall.model.api

import com.example.request.video.entity.ResponseData
import com.example.videohall.model.entity.MutilTypeEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface MutilTypeApi {
    @GET("/videotype/getMutilType")
    suspend fun getMutilType():ResponseData<List<MutilTypeEntity>>

    @GET("/videotype/getMutilTypeByPid")
    suspend fun getSubMutilType(@Query("pid") pid:Int):ResponseData<List<MutilTypeEntity>>
}