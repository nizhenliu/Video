package com.example.home.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.home.R
import com.example.home.databinding.ActivityHomeDetailBinding

import com.google.android.material.tabs.TabLayout
import com.example.home.model.entity.SimpleVideoEntity
import com.example.mvi.ui.BaseActivity
import com.example.widget.common.RedPacketEnum
import com.example.widget.common.RedPacketEvent


import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import org.greenrobot.eventbus.EventBus

@Route(path = "/Home/HomeDetailActivity")
class HomeDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeDetailBinding
    /*
    * 简介Fragment
    *
    * */
    private lateinit var descriptionFragment: HomeDetailDescriptionFragment

    /*
    *
    * 评论Fragment
    *
    * */
    private lateinit var commentFragment: HomeDetailCommentFragment
    /*
    * 背景颜色
    *
    * */
    private val mContentColorBg = arrayOf("#0099ff", "#b2d15c", "#b9b9f1", "#f46c77")
    @JvmField
    @Autowired
    var data: SimpleVideoEntity?=null
    override fun injectARouter() {
        super.injectARouter()
        ARouter.getInstance().inject(this)
    }

    override fun generatStatusBarColor(): Int {
        return Color.BLACK
    }
    override fun initView() {
        super.initView()
      //  Serializable
        binding.tabHomeDetailType.addTab(binding.tabHomeDetailType.newTab().setText(getString(R.string.home_detail_descriptioin)))
        binding.tabHomeDetailType.addTab(binding.tabHomeDetailType.newTab().setText(getString(R.string.home_detail_recomment)))

        val bundle:Bundle= Bundle()
        bundle.putParcelable("data",data)
        //创建Fragment并传递参数
        descriptionFragment= HomeDetailDescriptionFragment()
        descriptionFragment.arguments=bundle
        commentFragment= HomeDetailCommentFragment()
        commentFragment.arguments=bundle

        supportFragmentManager.beginTransaction().add(R.id.fl_home_detail_content,descriptionFragment).commitAllowingStateLoss()
    }

    override fun initEvent() {
        super.initEvent()
        binding.tabHomeDetailType.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.text==getString(R.string.home_detail_descriptioin)){
                    switchFragment(R.id.fl_home_detail_content,commentFragment,descriptionFragment)
                }
                else{
                    switchFragment(R.id.fl_home_detail_content,descriptionFragment,commentFragment)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
    override fun initData() {
        super.initData()
        binding.gsyHomeDetail.setUpLazy(data?.videopath,true,null,null,"")
        binding.gsyHomeDetail.titleTextView.visibility = View.GONE
        binding.gsyHomeDetail.backButton.visibility=View.GONE
        binding.gsyHomeDetail.fullscreenButton.setOnClickListener {
            binding.gsyHomeDetail.startWindowFullscreen(this,false,false)
        }
        binding.gsyHomeDetail.isShowFullAnimation=true
        binding.gsyHomeDetail.startButton.performClick()
        /*
        *   视频点击暂停 恢复处理
        *
        * */
        binding.gsyHomeDetail.setVideoAllCallBack(object : GSYSampleCallBack() {
            override fun onClickResume(url: String?, vararg objects: Any?) {
                super.onClickResume(url, *objects)
                EventBus.getDefault().post(RedPacketEvent(RedPacketEnum.RESUME))
                dmResume()
            }

            override fun onClickStop(url: String?, vararg objects: Any?) {
                super.onClickStop(url, *objects)
                EventBus.getDefault().post(RedPacketEvent(RedPacketEnum.PAUSE))
                dmPause()
            }
        })
        binding.ivHomeDetailBack.setOnClickListener {
            finish()
        }
    }
    /*
    * 弹幕暂停
    *
    * */
    private fun dmPause() {
        if (binding.dvHomeDetail!=null&&binding.dvHomeDetail.isActivated){
            binding.dvHomeDetail.pause()
        }
    }
    /*
    * 弹幕恢复
    *
    * */
    private fun dmResume() {

    }

    override fun onPause() {
        super.onPause()
        binding.gsyHomeDetail.onVideoPause()
        dmPause()
    }

    override fun onResume() {
        super.onResume()
        binding.gsyHomeDetail.onVideoResume()
        dmResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()

    }
    override fun getLayoutRootView(): View {
        binding = ActivityHomeDetailBinding.inflate(layoutInflater)
        return binding.root
    }

}