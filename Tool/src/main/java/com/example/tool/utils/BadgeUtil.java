package com.example.tool.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import q.rorbin.badgeview.QBadgeView;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/4/19 14:49
 * @description
 * @updateUser hahajing
 * @updateDate 2022/4/19 14:49
 * @updateRemark
 */
public class BadgeUtil {
    /**
     * 右上小红点数量提示
     * @param context  当前的activity
     * @param view  要显示的控件
     * @param i 数量（点内数字）
     */
    public static void Badge(Context context, View view, int i){
        new QBadgeView(context)
                .bindTarget(view)
                .setBadgeNumber(i)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(0,0,true);
    }
}
