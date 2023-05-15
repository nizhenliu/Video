package com.example.home.state



import com.example.home.model.entity.SimpleVideoEntity
import com.example.mvi.IState

sealed class SimpleVideoState: IState {
    /**
     * 成功获取视频信息
     */
    data class SimpleVideos(val list:List<SimpleVideoEntity>): SimpleVideoState()

    /**
     * 成功获取推荐视频信息
     */
    data class RecommendSimpleVideos(val list:List<SimpleVideoEntity>): SimpleVideoState()

    /**
     * 获取视频信息失败
     */
    object Failed: SimpleVideoState()

    /**
     * 获取推荐视频信息失败
     */
    object RecommendFailed: SimpleVideoState()

    /**
     * 初始状态
     */
    object Init: SimpleVideoState()


}