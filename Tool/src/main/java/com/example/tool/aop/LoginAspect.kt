package com.example.tool.aop

import android.util.Log
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import java.lang.reflect.Method

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
@Aspect
class LoginAspect {
    private var descript: String? = null

    /**
     * 切点：
     * 方法执行
     * 表示拦截@com.zy.news.WatcherMethod 注解的任意（任意返回值 - * 任意方法名 - * 任意的参数个数已经参数类型 - （..））方法
     */
    @Pointcut("execution(@com.example.tool.aop.WatcherLogin * *(..))")
    private fun doMethod() {
    }

    @Around("doMethod()")
    @Throws(Throwable::class)
    fun doWatchMethod(joinPoint: ProceedingJoinPoint) {
        //获取到方法的签名
        val signature = joinPoint.signature as MethodSignature
            ?: return
        //获取类名称
        val className = signature.declaringType.simpleName
        //获取方法名
        val methodName = signature.name
        //获取到方法  但是此处直接通过该Mehtod获取注解可能为null 所以要使用如下方法来获取"真实"的Method对象
        val method: Method = signature.method
        //获取真实的Method
        val realMethod: Method =
            joinPoint.target.javaClass.getDeclaredMethod(methodName, *method.getParameterTypes())
        //通过从方法上获取到对应的注解 WatcherMethod
        val watcherMethod: WatcherLogin = realMethod.getAnnotation(WatcherLogin::class.java)
        if (null != watcherMethod) {
            //获取到注解中的Descript描述属性信息
            descript = watcherMethod.Descript
        }
        //获取方法开始执行的时间戳
        val begin = System.currentTimeMillis()
        //执行原方法
        joinPoint.proceed()
        //获取方法执行结束的时间戳
        val end = System.currentTimeMillis()

        //用end-begin 获取到的就是方法的总耗时时间
        Log.d(
            "123",
            String.format(
                "方法执行时间监控：%s 类 - %s 方法 - 描述：%s 用时：%d",
                className,
                methodName,
                descript,
                end - begin
            )
        )
    }
}