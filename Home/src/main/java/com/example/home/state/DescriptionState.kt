package com.example.home.state



import com.example.home.model.entity.AgreeEntity
import com.example.mvi.IState

sealed class DescriptionState : IState {
    /*
    * 点赞
    * */
    data class Agree(val body: AgreeEntity): DescriptionState()

    /*
    *
    * 点赞失败
    * */
    object Failed: DescriptionState()

    /*
    *
    * 点赞初始化
    * */
    object Init: DescriptionState()
}