package com.example.widget;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author hahajing 企鹅：444511958
 * @createDate 2022/4/25 16:00
 * @description
 *
 * @updateUser hahajing
 * @updateDate 2022/4/25 16:00
 * @updateRemark
 *
 * @version 1.0.0
 */
public class RedPacketView extends View {
    private Paint bgPaint;
    private Paint fgPaint;
    private Paint bitmapPaint;
    /**
     * 默认宽高
     */
    private int defaultWidth=150;
    private int defaultHeight=150;

    /**
     * 默认半径
     */
    private int radius=45;

    /**
     * 起始角度
     */
    private int angle=0;

    /**
     * 弧形范围
     */
    private RectF rectF=null;

    private Callback callback=null;
    private ValueAnimator valueAnimator;
    private Bitmap bitmap;
    private Rect rect;
    private Rect dst;

    /**
     * 设置监听
     * @param callback
     */
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    /**
     * 开始动画
     */
    public void startAnimate(){
        valueAnimator = ValueAnimator.ofFloat(0,1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value= (float) animation.getAnimatedValue();
                angle= (int) (360*value);

                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (null!=callback){
                    angle=0;
                    invalidate();
                    callback.completed();
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (null!=callback){
                    callback.start();
                }
            }

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
                if (null!=callback){
                    callback.pause(angle);
                }
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
                if (null!=callback){
                    callback.resume();
                }
            }
        });
        valueAnimator.setDuration(10000);
        valueAnimator.start();

    }

    /**
     * 暂停动画
     */
    public void pause(){
       if (null!=valueAnimator){
           valueAnimator.pause();
       }
    }

    /**
     * 恢复动画
     */
    public void resume(){
        if (null!=valueAnimator){
            valueAnimator.resume();
        }
    }

    /**
     * 停止动画
     */
    public void stop(){
        if (null!=valueAnimator){
            valueAnimator.cancel();
        }
    }

    public RedPacketView(Context context) {
        super(context);
        init();
    }

    public RedPacketView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RedPacketView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initPaint();

    }

//    /**
//     * SVG 转 Bitmap
//     * @param context 上下文
//     * @param drawableId SVG资源
//     * @return
//     */
//    private Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
//        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            drawable = (DrawableCompat.wrap(drawable)).mutate();
//        }
//
//        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
//                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//        drawable.draw(canvas);
//
//        return bitmap;
//    }

    private Bitmap getBitmap(int id,int w,int h) {
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(getResources(),id,options);
        int oldWidth=options.outWidth;
        int oldHeight=options.outHeight;
//        int min = Math.min(oldWidth, oldHeight);

        int sampleSize = 1;
        while (oldHeight / sampleSize > h || oldWidth / sampleSize > w) {
            sampleSize *= 2;
        }
        options.inJustDecodeBounds=false;
        options.inSampleSize=sampleSize;
        options.inPreferredConfig= Bitmap.Config.RGB_565;

        Bitmap result= BitmapFactory.decodeResource(getResources(),R.drawable.redpacket,options);
        return result;
    }

    int storkeWidth=10;

    /**
     * 初始化画笔方法
     */
    private void initPaint() {
        bgPaint=new Paint();
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setColor(Color.rgb(221,221,221));
        bgPaint.setStrokeWidth(storkeWidth);

        fgPaint=new Paint();
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setColor(Color.rgb(255,0,51));
        fgPaint.setStrokeWidth(storkeWidth);

        bitmapPaint=new Paint();
        bitmapPaint.setAntiAlias(true);
        bitmapPaint.setDither(true);
    }

    int centerX=0;
    int centerY=0;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bitmap=getBitmap(R.drawable.redpacket,getMeasuredWidth()/2,getMeasuredHeight()/2);

        int padding=storkeWidth+2;
        rectF=new RectF(0+padding,0+padding,getMeasuredWidth()-padding,getMeasuredHeight()-padding);
//        radius=Math.min(getMeasuredWidth(),getMeasuredHeight())/2-(storkeWidth+2);
        centerX=getMeasuredWidth()/2;
        centerY=getMeasuredHeight()/2;

        rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        dst = new Rect(centerX-bitmap.getWidth()/2,centerY-bitmap.getHeight()/2,centerX+bitmap.getWidth()/2,centerY+getHeight()/2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);

        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode==MeasureSpec.AT_MOST&&heightMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(defaultWidth,defaultHeight);
        }else if (widthMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(defaultWidth,heightSize);
        }else if (heightMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize,defaultHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, rect, dst,bitmapPaint);
//        bgPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        canvas.drawArc(rectF,0,360,false,bgPaint);
        canvas.drawArc(rectF,270,angle,false,fgPaint);
    }

    public interface Callback{
        void start();
        void pause(int angle);
        void resume();
        void completed();
    }

    private Bitmap createCircleImage(Bitmap source) {
        int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(length / 2, length / 2, length / 2+20, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }
}
