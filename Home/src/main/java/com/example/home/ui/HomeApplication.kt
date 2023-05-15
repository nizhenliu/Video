package com.example.home.ui

import android.annotation.SuppressLint
import com.alibaba.android.arouter.launcher.ARouter
import com.example.tool.BaseApplication


class HomeApplication : BaseApplication(){
    @SuppressLint("MissingSuperCall")
    override fun onCreate() {
        super.onCreate()
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }
}