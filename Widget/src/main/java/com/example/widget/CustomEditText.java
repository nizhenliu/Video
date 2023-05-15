package com.example.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/5/7 16:49
 * @description
 * @updateUser hahajing
 * @updateDate 2022/5/7 16:49
 * @updateRemark
 */
public class CustomEditText extends AppCompatEditText {
    private Paint mPaint=null;
    private RectF rectF;

    public CustomEditText(@NonNull Context context) {
        super(context);
        initPaint();
    }

    public CustomEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CustomEditText(@NonNull Context context, @Nullable AttributeSet attrs,
                          int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint=new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(2);

        rectF = new RectF();
    }

    float rx,ry=10.0F;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        rectF.top=0;
        rectF.bottom=getMeasuredHeight();
        rectF.right=getMeasuredWidth();
        rectF.left=0;
        canvas.drawRoundRect(rectF,rx,ry,mPaint);
    }
}
