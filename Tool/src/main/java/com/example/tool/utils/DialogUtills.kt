package com.example.tool.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import java.lang.ref.WeakReference

/**
 * @author hahajing 企鹅：444511958
 * @createDate 2022/4/15 13:33
 * @description
 *
 * @updateUser hahajing
 * @updateDate 2022/4/15 13:33
 * @updateRemark
 *
 * @version 1.0.0
 */
object DialogUtills {
    /**
     * 显示确认对话框
     * @param context 上下文
     * @param title 标题
     * @param msg 显示文本
     * @param agreeTxt 确认文本
     * @param unagreeTxt 取消文本
     * @param agree 确认回调
     * @param close 关闭回调
     */
    fun showConfirmDialog(context:Context,title:String,msg:String,agreeTxt:String,unagreeTxt:String,agree:DialogInterface.OnClickListener,close:DialogInterface.OnClickListener){
        val weakReference=WeakReference(context)
        val builder: AlertDialog.Builder? = weakReference.get()?.let { AlertDialog.Builder(it) };
        builder?.setTitle(title)
        builder?.setMessage(msg)
        builder?.setPositiveButton(agreeTxt,agree)
        builder?.setNegativeButton(unagreeTxt,close)
        builder?.show()
    }
}