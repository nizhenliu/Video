package com.example.request.video.entity

data class ResponseData<T>(val code:Int,val data:T,val msg:String)