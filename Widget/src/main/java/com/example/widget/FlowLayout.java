package com.example.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/8/19 8:30
 * @description 流式布局
 * @updateUser hahajing
 * @updateDate 2022/8/19 8:30
 * @updateRemark
 */
public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    int x,y=0;

    int currentWidth=0;
    int currentHeight=0;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        currentWidth=getMeasuredWidth();
        currentHeight=getMeasuredHeight();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onLayout(boolean b, int l, int t, int r, int bb) {
        int count=getChildCount();
        for (int i=0;i<count;i++){
            View subView = getChildAt(i);
            int subViewWidth= subView.getMeasuredWidth();
            int subViewHeight= subView.getMeasuredHeight();

            int right=x+subViewWidth;
            int bottom=y+subViewHeight;

            if (right>currentWidth){
                x=0;

                right=x+subViewWidth;

                y=bottom;

                bottom=y+subViewHeight;
            }

            //设置子View的左上右下4个坐标位置
            subView.layout(x,y,right,bottom);

            x=right;

        }
    }
}
