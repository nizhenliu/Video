package com.example.log.impl;


import com.example.log.ILogger;
import com.example.log.LoggerLevel;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/4/25 9:28
 * @description
 * @updateUser hahajing
 * @updateDate 2022/4/25 9:28
 * @updateRemark
 */
public class ServerImpl implements ILogger {
    @Override
    public void v(String tag, String log) {
        //发网络请求
        //IO
    }

    @Override
    public void d(String tag, String log) {

    }

    @Override
    public void i(String tag, String log) {

    }

    @Override
    public void w(String tag, String log) {

    }

    @Override
    public void e(String tag, String log) {

    }

    @Override
    public void setDebug(boolean isDebug) {

    }

    @Override
    public void setLogTag(String tag) {

    }

    @Override
    public void setLoggerLevel(LoggerLevel level) {

    }

    @Override
    public void setTarget(String url) {

    }
}
