package com.example.log.impl;

import android.util.Log;


import com.example.log.ILogger;
import com.example.log.LoggerLevel;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/4/25 9:27
 * @description
 * @updateUser hahajing
 * @updateDate 2022/4/25 9:27
 * @updateRemark
 */
public class LogcatImpl implements ILogger {
    private boolean isDebug=true;
    private String TAG="";
    private LoggerLevel level= LoggerLevel.Verbose;
    private String loggerFormat="===> %s -> %s";

    @Override
    public void v(String tag, String log) {
        if (isDebug&&level.ordinal()<=LoggerLevel.Verbose.ordinal()){
            Log.v(TAG,String.format(loggerFormat,tag,log));
        }

    }

    @Override
    public void d(String tag, String log) {
        if (isDebug&&level.ordinal()<=LoggerLevel.Debug.ordinal()){
            Log.d(TAG,String.format(loggerFormat,tag,log));
        }
    }

    @Override
    public void i(String tag, String log) {
        if (isDebug&&level.ordinal()<=LoggerLevel.Info.ordinal()){
            Log.i(TAG,String.format(loggerFormat,tag,log));
        }
    }

    @Override
    public void w(String tag, String log) {
        if (isDebug&&level.ordinal()<=LoggerLevel.Warn.ordinal()){
            Log.w(TAG,String.format(loggerFormat,tag,log));
        }
    }

    @Override
    public void e(String tag, String log) {
        if (isDebug&&level.ordinal()<=LoggerLevel.Error.ordinal()){
            Log.e(TAG,String.format(loggerFormat,tag,log));
        }
    }

    @Override
    public void setDebug(boolean _isDebug) {
        this.isDebug=_isDebug;
    }

    @Override
    public void setLogTag(String _tag) {
        this.TAG=_tag;
    }

    @Override
    public void setLoggerLevel(LoggerLevel _level) {
        this.level=_level;
    }

    @Override
    public void setTarget(String url) {

    }
}
