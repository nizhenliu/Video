package com.example.home.state

import com.example.home.model.entity.SimpleTypeEntity

sealed class SimpleTypeState {
    /*
    * 成功获取视频类型信息
    *
    * */
    data class SimpleTypes(val  list: List<SimpleTypeEntity>?): SimpleTypeState()
    /*
    * 获取数据失败
    * */
    object Failed: SimpleTypeState()
    /*
    *
    * 初始化情况
    *
    * */
    object Init: SimpleTypeState()
}