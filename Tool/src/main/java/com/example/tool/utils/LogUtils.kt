package com.example.tool.utils


import com.example.log.BuildConfig
import com.example.log.Logger
import com.example.log.LoggerLevel
import com.example.log.LoggerType


/**
 * @author hahajing 企鹅：444511958
 * @createDate 2022/4/25 14:55
 * @description
 *
 * @updateUser hahajing
 * @updateDate 2022/4/25 14:55
 * @updateRemark
 *
 * @version 1.0.0
 */
object LogUtils {
    val logger: Logger by lazy {
        Logger.Builder()
            .setDebug(BuildConfig.DEBUG)
            .setLevel(LoggerLevel.Verbose)
            .setLoggerType(LoggerType.LOGCAT)
            .setTag("123")
            .build()
    }

    fun v(tag: String?, log: String?) {
        logger.v(tag, log)
    }

    fun d(tag: String?, log: String?) {
        logger.d(tag, log)
    }

    fun i(tag: String?, log: String?) {
        logger.i(tag, log)
    }

    fun w(tag: String?, log: String?) {
        logger.w(tag, log)
    }

    fun e(tag: String?, log: String?) {
        logger.e(tag, log)
    }
}