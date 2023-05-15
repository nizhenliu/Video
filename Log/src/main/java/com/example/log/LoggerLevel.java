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
public enum LoggerLevel {
    Verbose(1),Debug(2),Info(3),Warn(4),Error(5);
    private int value=0;
    LoggerLevel(int _value){
        this.value=_value;
    }
}
