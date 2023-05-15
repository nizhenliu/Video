package com.example.mvi.model

import com.example.mvi.IIntent
import kotlinx.coroutines.channels.Channel

/**
 * @author hahajing 企鹅：444511958
 * @createDate 2022/4/1 19:47
 * @description
 *
 * @updateUser hahajing
 * @updateDate 2022/4/1 19:47
 * @updateRemark
 *
 * @version 1.0.0
 */
interface IModel<I: IIntent> {
    val intents: Channel<I>
//    val state: MutableLiveData<S>
}