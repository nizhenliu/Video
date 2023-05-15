package com.example.tool.msgbus

/**
 * @author hahajing 企鹅：444511958
 * @createDate 2022/8/10 10:16
 * @description
 *
 * @updateUser hahajing
 * @updateDate 2022/8/10 10:16
 * @updateRemark
 *
 * @version 1.0.0
 */
object LiveDataBus {
    private val mBus = hashMapOf<String, SingleLiveData<Any>>()

    fun <T>with(channel: String): SingleLiveData<T> {
        if (!mBus.containsKey(channel)) {
            mBus[channel] = SingleLiveData()
        }
        return mBus[channel]!! as SingleLiveData<T>
    }
}