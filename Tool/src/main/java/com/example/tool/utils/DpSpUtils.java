package com.example.tool.utils;

import android.content.Context;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/4/26 10:40
 * @description
 * @updateUser hahajing
 * @updateDate 2022/4/26 10:40
 * @updateRemark
 */
public class DpSpUtils {
    /**
     * dp转换为px
     *
     * @param context
     * @param value 单位dp
     * @return
     */
    public static int dp2px(Context context, int value) {
        float v = context.getResources().getDisplayMetrics().density;
        return (int) (v * value + 0.5f);
    }

    /**
     * sp转换为px
     *
     * @param context
     * @param value 单位sp
     * @return
     */
    public static int sp2px(Context context, int value) {
        float v = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (v * value + 0.5f);
    }

    /**
     * px转换为dp
     *
     * @param context
     * @param value
     * @return
     */
    public static int px2dp(Context context, int value) {
        float v = context.getResources().getDisplayMetrics().density;
        return (int) (value / v + 0.5f);
    }

    /**
     * px转换为sp
     *
     * @param context
     * @param value
     * @return
     */
    public static int px2sp(Context context, int value) {
        float v = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (value / v + 0.5f);
    }
}
