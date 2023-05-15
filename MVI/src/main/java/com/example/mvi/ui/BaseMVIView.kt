package com.example.mvi.ui

import android.content.Context


/**
 * @author hahajing 企鹅：444511958
 * @createDate 2022/5/13 9:00
 * @description
 *
 * @updateUser hahajing
 * @updateDate 2022/5/13 9:00
 * @updateRemark
 *
 * @version 1.0.0
 */
abstract class BaseMVIView(context: Context): BaseView(context) {
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        generateViewModel()
        handleState()
    }

    abstract fun handleState()

    abstract fun generateViewModel()
}