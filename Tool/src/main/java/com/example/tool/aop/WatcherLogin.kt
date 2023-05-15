package com.example.tool.aop

/**
 * @author hahajing 企鹅：444511958
 * @createDate 2022/8/8 14:00
 * @description
 *
 * @updateUser hahajing
 * @updateDate 2022/8/8 14:00
 * @updateRemark
 *
 * @version 1.0.0
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class WatcherLogin(val Descript:String=""){

}
