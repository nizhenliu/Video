package com.example.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.example.widget.common.RedPacketEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/4/29 8:36
 * @description
 * @updateUser hahajing
 * @updateDate 2022/4/29 8:36
 * @updateRemark
 */
public class RedPacketWidget extends LinearLayout {

    private TextView tvNum;
    private RedPacketView redPacketView;
    private TranslateAnimation translateAnimation;

    public RedPacketWidget(Context context) {
        super(context);
        initView(context);
    }

    public RedPacketWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RedPacketWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        releaseRes();

    }

    public void releaseRes(){
        redPacketView.stop();
        //注销EventBus
        EventBus.getDefault().unregister(this);
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 初始化视图
     * @param context
     */
    private void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_redpacketwidget, null, false);
        tvNum = view.findViewById(R.id.tv_redpacket_num);
        redPacketView = view.findViewById(R.id.rpv_redpacket);
        addView(view);

        tvNum.setVisibility(INVISIBLE);

        translateAnimation = new TranslateAnimation(0.0F,0.0F,0.0F,-70.0F);
        translateAnimation.setFillAfter(true);
        //动画执行时间
        translateAnimation.setDuration(2000);


        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tvNum.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvNum.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private Handler mHandler=new Handler();

    /**
     * EventBus 事件处理
     * @param event
     */
    @Subscribe()
    public void onEvent(RedPacketEvent event){
        switch (event.getState()){
            case INIT:
                start();
                break;

            case PAUSE:
                //2秒后暂停 实现一个缓冲效果
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        redPacketView.pause();
                    }
                },2000);

                break;

            case RESUME:
                redPacketView.resume();
                break;
        }
    }

    /**
     * 开始执行动画
     */
    public void start(){
        redPacketView.startAnimate();

        redPacketView.setCallback(new RedPacketView.Callback() {
            @Override
            public void start() {

            }

            @Override
            public void pause(int angle) {

            }

            @Override
            public void resume() {

            }

            @Override
            public void completed() {
                tvNum.startAnimation(translateAnimation);
                redPacketView.startAnimate();
                if (stateCallback!= null){
                    stateCallback.completed();
                }
            }
        });
    }

    /**
     * 设置文本提示信息
     * @param num
     */
    public void setTvNum(int num){
        tvNum.setText("+"+num);
    }

    private StateCallback stateCallback;

    public void setStateCallback(StateCallback stateCallback) {
        this.stateCallback = stateCallback;
    }

    /**
     * 状态回调接口
     */
    public interface StateCallback{
        void completed();
    }
}
