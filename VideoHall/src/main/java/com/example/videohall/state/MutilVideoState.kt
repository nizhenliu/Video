package com.example.videohall.state

import com.example.videohall.model.entity.MutilVideoEntity
import kotlin.random.Random

sealed class MutilVideoState {
    data class getMutilVideoInfoSuccess(val data:List<MutilVideoEntity>):MutilVideoState(){
        override fun equals(other: Any?): Boolean {
            return false
        }

        override fun hashCode(): Int {
            return Random.nextInt()
        }
    }
    object init: MutilVideoState()
    object getMutilVideoInfoFailed: MutilVideoState()

    /**
     * 成功获取推荐视频信息
     */
    data class RecommendSimpleVideos(val list:List<MutilVideoEntity>):MutilVideoState()
    /**
     * 获取推荐视频信息失败
     */
    object RecommendFailed:MutilVideoState()
}