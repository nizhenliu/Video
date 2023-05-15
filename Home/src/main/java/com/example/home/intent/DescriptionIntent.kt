package com.example.home.intent

import com.example.mvi.IIntent
import okhttp3.RequestBody

sealed class DescriptionIntent: IIntent {
    /*
    * 获得点赞信息的意图
    * */
    data class AgreeDescription(val body: RequestBody): DescriptionIntent()
}