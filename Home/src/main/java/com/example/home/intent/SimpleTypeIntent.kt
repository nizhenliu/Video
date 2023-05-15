package com.example.home.intent


import com.example.mvi.IIntent


sealed class SimpleTypeIntent: IIntent {
    /*
    * 获取视频类型意图
    * */
    object  getSimpleType: SimpleTypeIntent()
}