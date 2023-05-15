package com.example.mvi.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jaeger.library.StatusBarUtil
import kotlin.math.ceil


/**
 * @author hahajing 企鹅：444511958
 * @createDate 2022/4/2 8:14
 * @description
 *
 * @updateUser hahajing
 * @updateDate 2022/4/2 8:14
 * @updateRemark
 *
 * @version 1.0.0
 */
abstract class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        injectARouter()
        super.onCreate(savedInstanceState)
//        transparentStatusBar(window)
        if (-1==getLayoutId()){
            setContentView(getLayoutRootView())
        }else{
            setContentView(getLayoutId())
        }

        //设置状态栏
//        StatusBarUtil.setColor(this,generatStatusBarColor(),1)
        StatusBarUtil.setColorNoTranslucent(this,generatStatusBarColor());
        initEnv()
        initView();
        initData();
        initEvent();
    }

    protected open fun initEnv(){}

    protected open fun injectARouter() {

    }

    /**
     * 设置状态栏颜色
     */
    protected open fun generatStatusBarColor(): Int{
        return Color.TRANSPARENT
    }

    /**
     * 获取Content View 配合ViewBinding使用
     */
    abstract fun getLayoutRootView(): View

    protected open fun initEvent() {}

    protected open fun initData(){}

    protected open fun initView() {}

    /**
     * 获取布局资源ID
     */
    protected open fun getLayoutId(layoutId:Int=-1): Int{
        return layoutId;
    }

    /**
     * 设置透明状态栏
     */
    private fun transparentStatusBar(window: Window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val option: Int =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            val vis: Int = window.getDecorView().getSystemUiVisibility()
            window.getDecorView().setSystemUiVisibility(option or vis)
            window.setStatusBarColor(Color.TRANSPARENT)
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    /**
     * 获取状态栏高度
     */
    private fun getStatusBarHeight(): Int {
        val identifier = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (identifier > 0) {
            resources.getDimensionPixelSize(identifier)
        } else {
            val density = resources.displayMetrics.density
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ceil(24 * density).toInt()
            } else {
                ceil(25 * density).toInt()
            }
        }
    }

    /**
     * 显示提示消息
     */
    fun showMsg(msg:String){
        if(Looper.getMainLooper().thread==Thread.currentThread()){
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
        }
        else{
            handler.post {
                Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val handler: Handler = Handler(Looper.getMainLooper())

    /**
     * 跳转到指定页面
     */
    fun routeActivity(target:Class<*>){
        startActivity(Intent(this,target))
    }

    /**
     * 跳转指定页面并传递参数
     */
    fun routeActivity(target: Class<*>,params:Bundle){
        val intent=Intent(this,target)
        intent.putExtra("params",params)
        startActivity(intent)
    }

    fun addFragment(fragment:Fragment,tag:String){
        supportFragmentManager.beginTransaction().add(fragment,tag).commitAllowingStateLoss()
    }

    fun hideFragment(tag:String){
        supportFragmentManager.findFragmentByTag(tag)
            ?.let { supportFragmentManager.beginTransaction().hide(it).commitAllowingStateLoss() }
    }

    fun showFragment(tag:String){
        supportFragmentManager.findFragmentByTag(tag)?.let {
            supportFragmentManager.beginTransaction().show(it).commitAllowingStateLoss()
        }
    }

    fun replaceFragment(containerId:Int,fragment:Fragment){
        supportFragmentManager.beginTransaction().replace(containerId,fragment).commitAllowingStateLoss()
    }

    fun switchFragment(fl:Int,from: Fragment?, to: Fragment) {
        if (!to.isAdded()) {
            supportFragmentManager.beginTransaction().hide(from!!).add(fl, to).commit()
        } else {
            supportFragmentManager.beginTransaction().hide(from!!).show(to).commit()
        }
    }

    fun closeThis(){
        finish()
    }

}