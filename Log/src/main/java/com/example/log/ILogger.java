package com.example.log;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/4/25 9:21
 * @description
 * @updateUser hahajing
 * @updateDate 2022/4/25 9:21
 * @updateRemark
 */
public interface ILogger {
    void v(String tag,String log);
    void d(String tag,String log);
    void i(String tag,String log);
    void w(String tag,String log);
    void e(String tag,String log);

    /**
     * 日志开关 true-打开 false-关闭
     * @param isDebug
     */
    void setDebug(boolean isDebug);

    /**
     * 设置log的总tag
     * @param tag
     */
    void setLogTag(String tag);

    /**
     * 设置日志最小输出级别
     * @param level
     */
    void setLoggerLevel(LoggerLevel level);

    /**
     * 对应Email-邮箱地址 Server-网络请求地址。。。
     * @param url
     */
    void setTarget(String url);
}
