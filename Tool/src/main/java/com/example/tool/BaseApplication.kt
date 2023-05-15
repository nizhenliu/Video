package com.example.tool

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.example.tool.websocket.WebSocketUtils


/**
 * @author hahajing 企鹅：444511958
 * @createDate 2022/4/16 20:41
 * @description
 *
 * @updateUser hahajing
 * @updateDate 2022/4/16 20:41
 * @updateRemark
 *
 * @version 1.0.0
 */
open class BaseApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        context =this.applicationContext
        // 初始化MultiDex
        MultiDex.install(this)
    }

    companion object{
        private lateinit var context: Context
        fun getAppContext():Context{
            return context
        }

        private lateinit var websocket: WebSocketUtils
        @JvmOverloads
        fun getWebSocket(listener:WebSocketUtils.ReceiveMsgListener):WebSocketUtils {
//            if(websocket!=null){
//                return websocket
//            }
            websocket = WebSocketUtils.Builder()
                .setListener(listener)
                .build()
            return websocket
        }

        fun getWS():WebSocketUtils{
            if(websocket ==null){
                throw IllegalStateException("websocket is null please first call getWebSocket method")
            }
            return websocket
        }
    }
}