package com.example.video

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.ext.loadFragments
import com.example.ext.showHideFragment
import com.example.home.ui.HomeFragment
import com.example.video.databinding.ActivityMainBinding
import com.example.videohall.ui.VideoHallFragment
import com.example.view.fragment.MyFragment
import com.example.view.fragment.ProjectFragment
import com.example.view.fragment.SystemFragment
import com.example.view.fragment.WechatFragment
@Route(path = "/app/MainActivity")
class MainActivity : AppCompatActivity() {
    companion object {
        private const val BOTTOM_INDEX = "bottom_index"
        private const val FRAGMENT_HOME = 0
        private const val FRAGMENT_WECHAT = 1
        private const val FRAGMENT_SYSTEM = 2
        private const val FRAGMENT_PROJECT = 3
        private const val FRAGMENT_MY = 4
    }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val mFragments = mutableListOf<Fragment>().apply {
        add(HomeFragment())
        add(VideoHallFragment())
        add(SystemFragment())
        add(ProjectFragment())
        add(MyFragment())
    }
    private var index = FRAGMENT_HOME
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(BOTTOM_INDEX, index)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadFragments(R.id.fl_container, FRAGMENT_HOME, mFragments)
        showFragment(FRAGMENT_HOME)
        savedInstanceState?.apply {
            index = getInt(BOTTOM_INDEX)
            showFragment(index)
        }
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bar_home -> showFragment(FRAGMENT_HOME)
                R.id.bar_cinema -> showFragment(FRAGMENT_WECHAT)
                R.id.bar_send -> showFragment(FRAGMENT_SYSTEM)
                R.id.bar_msg -> showFragment(FRAGMENT_PROJECT)
                R.id.bar_mine -> showFragment(FRAGMENT_MY)
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun showFragment(index: Int) {
        this.index = index
        showHideFragment(mFragments[index])
//        when (index) {
//            FRAGMENT_HOME -> supportActionBar?.title = StringUtils.getString(R.string.home)
//            FRAGMENT_WECHAT -> supportActionBar?.title = StringUtils.getString(R.string.wechat)
//            FRAGMENT_SYSTEM -> supportActionBar?.title = StringUtils.getString(R.string.system)
//            FRAGMENT_PROJECT -> supportActionBar?.title = StringUtils.getString(R.string.project)
//            FRAGMENT_MY -> supportActionBar?.title = StringUtils.getString(R.string.my)
//        }
    }
}