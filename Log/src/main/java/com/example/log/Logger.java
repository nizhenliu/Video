package com.example.log;


import com.example.log.impl.LogcatImpl;
import com.example.log.impl.ServerImpl;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/4/25 9:38
 * @description
 * @updateUser hahajing
 * @updateDate 2022/4/25 9:38
 * @updateRemark
 */
public class Logger {

    private String mTag;
    private LoggerLevel mLevel;
    private LoggerType mType;
    private boolean mIsDebug=true;
    private String mUrl;

    private ILogger  logger=null;

    private Logger(String _tag,LoggerLevel _level,LoggerType _type,boolean _isDebug,String _url){
        mTag=_tag;
        mLevel=_level;
        mType=_type;
        mIsDebug=_isDebug;
        mUrl=_url;
        logger=getLogger(mTag,mLevel,mType,mIsDebug,mUrl);
    }

    private ILogger getLogger(String mTag, LoggerLevel mLevel, LoggerType mType, boolean mIsDebug, String mUrl) {
        ILogger _logger= null;
        switch (mType){
            case EMAIL:
                throw new IllegalStateException("EMAIL方案未实现暂时无法使用");
            case LOGCAT:
                _logger=new LogcatImpl();
                break;
            case SERVER:
                _logger=new ServerImpl();
                break;
            case LOCALFILE:
                throw new IllegalStateException("LocalFile方案未实现暂时无法使用");
            default:
                _logger=new LogcatImpl();
                break;
        }

        if (null!=_logger){
            _logger.setDebug(mIsDebug);
            _logger.setLoggerLevel(mLevel);
            _logger.setTarget(mUrl);
            _logger.setLogTag(mTag);
        }

        return _logger;
    }

    public void v(String tag,String log){
        logger.v(tag,log);
    }

    public void d(String tag,String log){
        logger.d(tag,log);
    }

    public void i(String tag,String log){
        logger.i(tag,log);
    }

    public void w(String tag,String log){
        logger.w(tag,log);
    }

    public void e(String tag,String log){
        logger.e(tag,log);
    }


   public static class Builder{
        private String Tag;
        private LoggerLevel level;
        private LoggerType loggerType;
        private boolean isDebug=true;
        private String url;

       public Builder setTag(String tag) {
           Tag = tag;
           return this;
       }

       public Builder setLevel(LoggerLevel level) {
           this.level = level;
           return this;
       }

       public Builder setLoggerType(LoggerType loggerType) {
           this.loggerType = loggerType;
           return this;
       }

       public Builder setDebug(boolean debug) {
           isDebug = debug;
           return this;
       }

       public Builder setUrl(String url) {
           this.url = url;
           return this;
       }

       public Logger build(){
            return new Logger(Tag,level,loggerType,isDebug,url);
        }
    }
}
