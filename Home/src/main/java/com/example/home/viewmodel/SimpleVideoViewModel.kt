package com.example.home.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope

import com.example.home.intent.SimpleVideoIntent
import com.example.home.state.SimpleVideoState
import com.example.home.service.VideoService
import com.example.mvi.model.BaseModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class SimpleVideoViewModel : BaseModel<SimpleVideoIntent>() {
    val service: VideoService by lazy { VideoService() }

    private val _state = MutableStateFlow<SimpleVideoState>(SimpleVideoState.Init)
    val state: StateFlow<SimpleVideoState>
        get() = _state

    override fun handleIntent() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect {
                when (it) {
                    is SimpleVideoIntent.getSimpleVideo -> {
                        doSimpleVideos(it)
                    }
                    is SimpleVideoIntent.getRecommendSimpleVideo -> {
                        doRecommendSimpleVideos(it)
                    }
                }
            }
        }
    }

    private fun doRecommendSimpleVideos(it: SimpleVideoIntent.getRecommendSimpleVideo) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = service.getRecommendSimpleVideo(it.page, it.pagesize)
            if (data.code == 0) {
                _state.value = SimpleVideoState.RecommendSimpleVideos(data.data)
            } else {
                _state.value = SimpleVideoState.RecommendFailed
            }
        }
    }

    private fun doSimpleVideos(it: SimpleVideoIntent.getSimpleVideo) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = service.getSimpleVideoByChannelId(it.channelId, it.page, it.pagesize)
            Log.e("=====", "" + data.code)
            if (data.code == 0) {
                _state.value = SimpleVideoState.SimpleVideos(data.data)
            } else {
                _state.value = SimpleVideoState.Failed
            }
        }
    }
}