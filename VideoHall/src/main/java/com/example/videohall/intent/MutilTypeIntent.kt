package com.example.videohall.intent

import com.example.mvi.IIntent

sealed class MutilTypeIntent:IIntent{
    /**
     * 获取MutilType意图
     */
    object getMutilType:MutilTypeIntent()

    data class getSubMutilType(val pid:Int):MutilTypeIntent()
}