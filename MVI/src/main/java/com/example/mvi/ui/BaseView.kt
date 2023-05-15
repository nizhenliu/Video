package com.example.mvi.ui

import android.content.Context
import android.view.View

/**
 * @author hahajing 企鹅：444511958
 * @createDate 2022/5/13 8:58
 * @description
 *
 * @updateUser hahajing
 * @updateDate 2022/5/13 8:58
 * @updateRemark
 *
 * @version 1.0.0
 */
abstract class BaseView(context:Context):View(context) {
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        initData()
        initEvent()
    }

    abstract fun initEvent()

    abstract fun initData()

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }
}