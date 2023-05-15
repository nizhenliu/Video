package com.example.tool.msgbus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * @author hahajing 企鹅：444511958
 * @createDate 2022/8/10 10:15
 * @description
 *
 * @updateUser hahajing
 * @updateDate 2022/8/10 10:15
 * @updateRemark
 *
 * @version 1.0.0
 */
class SingleLiveData<T> : MutableLiveData<T>() {
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        hook(observer)
    }

    fun observeStick(owner: LifecycleOwner, observer: Observer<in T>){
        super.observe(owner, observer)
    }

    /**
     * 反射获取 mLastVersion 修改值
     */
    private fun hook( observer: Observer<in T>) {
        val liveDataClass = LiveData::class.java
        val mObserversField = liveDataClass.getDeclaredField("mObservers")
        mObserversField.isAccessible = true
        val mObservers = mObserversField.get(this)
        val observerClass = mObservers::class.java

        val get = observerClass.getDeclaredMethod("get",Any::class.java)
        get.isAccessible = true
        val entry = get.invoke(mObservers, observer)
        val observerWrapper = (entry as Map.Entry<Any,Any>).value
        val wrapperClass = observerWrapper.javaClass.superclass

        val mLastVersion = wrapperClass.getDeclaredField("mLastVersion")
        mLastVersion.isAccessible = true
        val mVersion = liveDataClass.getDeclaredField("mVersion")
        mVersion.isAccessible = true
        val mVersionValue = mVersion.get(this)
        mLastVersion.set(observerWrapper,mVersionValue)
    }
}
