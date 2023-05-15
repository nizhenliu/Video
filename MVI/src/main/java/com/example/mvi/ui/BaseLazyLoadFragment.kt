package com.example.mvi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * @author hahajing 企鹅：444511958
 * @createDate 2022/4/16 8:52
 * @description
 *
 * @updateUser hahajing
 * @updateDate 2022/4/16 8:52
 * @updateRemark
 *
 * @version 1.0.0
 */
abstract class BaseLazyLoadFragment : Fragment() {

    /**
     * 实体是否初始化
     */
    protected var isInit:Boolean=false

    /**
     * 是否已经完成加载
     */
    protected var isLoaded:Boolean=false

    protected lateinit var mView:View


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView=getFragmentRootView(inflater,container,savedInstanceState)//inflater.inflate(getLayoutId(),container,false)
        isInit=true
        preparLoadData();
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent();
    }

    /**
     * 初始化事件
     */
    abstract fun initEvent();

    /**
     * 获取Fragment视图
     */
    abstract fun getFragmentRootView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        preparLoadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isInit=false
        isLoaded=false
    }


    private fun preparLoadData() {
        if (!isInit)return
        if (userVisibleHint){
            lazyLoad()
            isLoaded=true
        }else{
            if (isLoaded){
                stopLoad()
            }
        }
    }

    /**
     * 停止加载可以用于释放资源
     */
    protected fun stopLoad(){}

    /**
     * 延时加载
     */
    abstract fun lazyLoad()

//    /**
//     *
//     */
//    abstract fun getLayoutId(): Int

    /**
     * 查找并获取资源
     */
    protected fun <T:View> findViewById(id:Int):T{
        return mView.findViewById(id)
    }

    /**
     * 显示提示消息
     */
    protected fun showMsg(msg: String?){

        if (activity is BaseActivity){
            (activity as BaseActivity).showMsg(msg?:"")
        }

    }

    /**
     * 获取框架中BaseActivity 注意可能为空 取决于Activity是否继承BaseActivity
     */
    protected fun getBaseActivity(): BaseActivity {
        return requireActivity() as BaseActivity;
    }

}