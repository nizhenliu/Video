package com.example.home.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * @author hahajing 企鹅：444511958
 * @createDate 2022/4/23 19:14
 * @description
 *
 * @updateUser hahajing
 * @updateDate 2022/4/23 19:14
 * @updateRemark
 *
 * @version 1.0.0
 */
class HomeViewPager constructor(val c:Context,val attrs: AttributeSet) : ViewPager(c,attrs) {
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val finalRect = Rect(this.left + 200, this.top, this.right - 200, this.bottom)
                val contains = finalRect.contains(ev.x.toInt(), ev.y.toInt())
                if (contains) {
                    parent.requestDisallowInterceptTouchEvent(true)
                } else {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }

            MotionEvent.ACTION_MOVE -> {
//                val curPosition = this.currentItem
//                val count = this.adapter!!.count
//                // 当当前页面在最后一页和第0页的时候，由父亲拦截触摸事件
//                if (curPosition == count - 1 || curPosition == 0) {
//                    parent.requestDisallowInterceptTouchEvent(false)
//                } else { //其他情况，由孩子拦截触摸事件
//                    parent.requestDisallowInterceptTouchEvent(true)
//                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}