package com.example.videohall.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.mvi.model.BaseModel
import com.example.videohall.intent.MutilVideoIntent
import com.example.videohall.model.service.MutilVideoService
import com.example.videohall.state.MutilVideoState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MutilVideoViewModel:BaseModel<MutilVideoIntent>(){
    val service by lazy { MutilVideoService() }

    private val _state = MutableStateFlow<MutilVideoState>(MutilVideoState.init)
    val state: StateFlow<MutilVideoState>
        get() = _state

    override fun handleIntent() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect {
                when (it){
                    is MutilVideoIntent.getMutilVideoInfo->doGetMutilVideoInfo(it)
                    is MutilVideoIntent.getRecommendMutilVideo ->doGetRecommendMutilVideoInfo(it)
                }
            }
        }
    }

    private fun doGetRecommendMutilVideoInfo(it: MutilVideoIntent.getRecommendMutilVideo) {
        viewModelScope.launch(Dispatchers.IO) {
            val data=service.getRecommendSimpleVideo(it.page,it.pagesize)
            if (data.code==0){
                _state.value=MutilVideoState.RecommendSimpleVideos(data.data)
            }else{
                _state.value=MutilVideoState.RecommendFailed
            }
        }
    }

    private fun doGetMutilVideoInfo(it: MutilVideoIntent.getMutilVideoInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            val data=service.getMutilVideoByTypeId(it.page,it.pagesize,it.typeid)
            if(data.code==0){
                _state.value=MutilVideoState.getMutilVideoInfoSuccess(data.data)
            }
            else{
                _state.value=MutilVideoState.getMutilVideoInfoFailed
            }
        }
    }
}