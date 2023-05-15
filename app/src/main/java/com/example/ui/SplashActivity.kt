package com.example.ui

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter

import com.example.video.databinding.ActivitySplashBinding


import com.jaeger.library.BuildConfig
import com.jaeger.library.StatusBarUtil


class SplashActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        StatusBarUtil.setColorNoTranslucent(this,Color.WHITE)
    }

    override fun onResume() {
        super.onResume()
        binding.tvSplashAppversion.text = String.format("当前版本：%s", BuildConfig.VERSION_NAME)

         object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvSplashTime.text = String.format("倒计时%sS", millisUntilFinished / 1000)
            }
            override fun onFinish() {
                ARouter.getInstance().build("/app/MainActivity").navigation()
                finish()
            }
        }.start()
    }
}