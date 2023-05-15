package com.example.mvi.ui

import com.example.mvi.IView

/**
 * @author hahajing 企鹅：444511958
 * @createDate 2022/4/2 8:39
 * @description
 *
 * @updateUser hahajing
 * @updateDate 2022/4/2 8:39
 * @updateRemark
 *
 * @version 1.0.0
 */
abstract class BaseMVIActivity: BaseActivity(), IView {

    init {

    }

    abstract fun generateViewModel()

    override fun initData() {
        super.initData()
    }

    override fun initEnv() {
        generateViewModel()
    }

    override fun onResume() {
        super.onResume()
        handleState()
    }
}