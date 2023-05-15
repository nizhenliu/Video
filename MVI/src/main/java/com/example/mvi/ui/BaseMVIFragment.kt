package com.example.mvi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mvi.IView

/**
 * @author hahajing 企鹅：444511958
 * @createDate 2022/4/16 9:06
 * @description
 *
 * @updateUser hahajing
 * @updateDate 2022/4/16 9:06
 * @updateRemark
 *
 * @version 1.0.0
 */
abstract class BaseMVIFragment : BaseLazyLoadFragment(), IView {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= super.onCreateView(inflater, container, savedInstanceState)
        initView()
        return view
    }

    protected open fun initView() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generateViewModel()
        handleState()
        initData()
    }

    protected open fun initData(){}

    abstract fun generateViewModel()
}