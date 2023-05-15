package com.example.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.home.intent.DescriptionIntent

import com.example.home.service.DescriptionService
import com.example.home.state.DescriptionState
import com.example.mvi.model.BaseModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class DescriptionViewModel: BaseModel<DescriptionIntent>(){
    val service: DescriptionService by lazy { DescriptionService() }
    private val _state = MutableStateFlow<DescriptionState>(DescriptionState.Init)
    val state:StateFlow<DescriptionState>
    get() = _state
    override fun handleIntent() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect {
                when(it){
                    is DescriptionIntent.AgreeDescription ->{
                        doDescription(it)
                    }
                }
            }
        }
    }

    private fun doDescription(it: DescriptionIntent.AgreeDescription) {
        viewModelScope.launch(Dispatchers.IO){
           val data = service.agree(it.body)
            if (data.code==0){
                _state.value = DescriptionState.Agree(data.data)
            }else{
                _state.value= DescriptionState.Failed
            }
        }
    }
}