package com.example.mvi.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author hahajing 企鹅：444511958
 * @createDate 2022/4/2 14:39
 * @description
 * 单一LiveData 不会被多次回调
 * @updateUser hahajing
 * @updateDate 2022/4/2 14:39
 * @updateRemark
 *
 * @version 1.0.0
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {
    val mPending=AtomicBoolean(false)
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner) { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    override fun setValue(value: T?) {
        mPending.set(true)
        super.setValue(value)
    }

    fun clean(){
        setValue(null)
    }
}