package com.example.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.home.intent.SimpleTypeIntent
import com.example.home.service.VideoTypeService
import com.example.home.state.SimpleTypeState
import com.example.mvi.model.BaseModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class SimpleTypeViewModel: BaseModel<SimpleTypeIntent>() {
    val service: VideoTypeService by lazy { VideoTypeService() }

    private val _state = MutableStateFlow<SimpleTypeState>(SimpleTypeState.Init)
    val state:StateFlow<SimpleTypeState>
        get() = _state

    override fun handleIntent() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect {
                when(it){
                    is SimpleTypeIntent.getSimpleType->{
                        doSimpleType(it)
                    }
                }
            }
        }
    }

    /**
     * 获取Simple视频类型
     */
    private fun doSimpleType(it: SimpleTypeIntent.getSimpleType) {
        viewModelScope.launch(Dispatchers.IO) {
            val data=service.getSimpleType()
            if (data.code==0){
                _state.value= SimpleTypeState.SimpleTypes(data.data)
            }
            else{
                _state.value= SimpleTypeState.Failed
            }
        }
    }
}