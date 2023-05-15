package com.example.videohall.intent

import com.example.mvi.IIntent
import kotlin.random.Random

sealed class MutilVideoIntent : IIntent {
    data class getMutilVideoInfo(val page:Int,val pagesize:Int,val typeid:Int):MutilVideoIntent(){
        override fun equals(other: Any?): Boolean {
            return false
        }

        override fun hashCode(): Int {
            return Random.nextInt()
        }
    }

    /**
     * 获取推荐视频信息意图
     */
    data class getRecommendMutilVideo(val page:Int,val pagesize:Int):MutilVideoIntent()

}