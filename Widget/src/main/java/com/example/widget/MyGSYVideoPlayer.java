package com.example.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/8/18 19:25
 * @description
 * @updateUser hahajing
 * @updateDate 2022/8/18 19:25
 * @updateRemark
 */
public class MyGSYVideoPlayer extends StandardGSYVideoPlayer {
    private MyGSYVideoPlayerStateListener listener;

    public MyGSYVideoPlayerStateListener getListener() {
        return listener;
    }

    public void setListener(MyGSYVideoPlayerStateListener listener) {
        this.listener = listener;
    }

    public MyGSYVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public MyGSYVideoPlayer(Context context) {
        super(context);
    }

    public MyGSYVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        if (listener!=null){
            listener.finish();
        }
    }

    public interface MyGSYVideoPlayerStateListener{
        void finish();
        void start();
    }
}
