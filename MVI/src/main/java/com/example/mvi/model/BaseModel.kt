package com.example.mvi.model

import androidx.lifecycle.ViewModel
import com.example.mvi.IIntent

import kotlinx.coroutines.channels.Channel

/**
 * @author hahajing 企鹅：444511958
 * @createDate 2022/4/2 8:42
 * @description
 *
 * @updateUser hahajing
 * @updateDate 2022/4/2 8:42
 * @updateRemark
 *
 * @version 1.0.0
 */
abstract class BaseModel<I: IIntent> : ViewModel(), IModel<I> {

    protected val _intents= Channel<I>(Channel.UNLIMITED)
    override val intents: Channel<I>
        get() = _intents

//    protected val _state = SingleLiveEvent<S>().apply { value=generateState() }
//    override val state: SingleLiveEvent<S>
//        get() = _state

//    /**
//     * 创建状态数据
//     */
//    abstract fun generateState():S

    init {
        /**
         * 处理意图
         */
        handleIntent()
    }

    /**
     * 处理用户行为
     */
    abstract fun handleIntent()

//    /**
//     * 更新状态
//     */
//    protected suspend fun updateState(handler:suspend (s:S)->S){
//        state.postValue(handler(state.value!!))
//    }
}