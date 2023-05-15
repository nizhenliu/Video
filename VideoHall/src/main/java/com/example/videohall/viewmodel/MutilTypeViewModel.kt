package com.example.videohall.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.mvi.model.BaseModel
import com.example.videohall.intent.MutilTypeIntent
import com.example.videohall.model.service.MutilTypeService
import com.example.videohall.state.MutilTypeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MutilTypeViewModel:BaseModel<MutilTypeIntent>(){
    val service by lazy { MutilTypeService()}

    private val _state = MutableStateFlow<MutilTypeState>(MutilTypeState.mutilTypeInit)
    val state: StateFlow<MutilTypeState>
        get() = _state

    override fun handleIntent() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect {
                when (it){
                    is MutilTypeIntent.getMutilType -> doGetMutilType(it)
                    is MutilTypeIntent.getSubMutilType -> doGetSubMutilType(it)
                }
            }
        }
    }

    private fun doGetSubMutilType(it: MutilTypeIntent.getSubMutilType) {
        viewModelScope.launch(Dispatchers.IO) {
            val data=service.getSubMutilType(it.pid)
            if(data.code==0){
                _state.value=MutilTypeState.getSubMutilTypeSuccessState(data.data)

            }else{
                _state.value=MutilTypeState.getSubMutilTypeFailureState
            }
        }
    }

    private fun doGetMutilType(it: MutilTypeIntent.getMutilType) {
        viewModelScope.launch(Dispatchers.IO) {
            val data=service.getMutilType()
            if (data.code==0){
                _state.value=MutilTypeState.getMutilTypeSuccessState(data.data)
            }else{
                _state.value=MutilTypeState.getMutilTypeFailureState
            }
        }
    }
}