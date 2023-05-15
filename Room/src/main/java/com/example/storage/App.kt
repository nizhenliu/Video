package com.example.storage

import android.app.Application
import android.content.Context

class App :Application(){
    companion object{
        @JvmField
        var context: Context?=null
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
