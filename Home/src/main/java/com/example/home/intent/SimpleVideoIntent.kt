package com.example.home.intent

import com.example.mvi.IIntent


sealed class SimpleVideoIntent: IIntent {
    /**
     * 获取视频信息意图
     */
    data class getSimpleVideo(val channelId:String,val page:Int,val pagesize:Int):
        SimpleVideoIntent()

    /**
     * 获取推荐视频信息意图
     */
    data class getRecommendSimpleVideo(val page:Int,val pagesize:Int): SimpleVideoIntent()
}