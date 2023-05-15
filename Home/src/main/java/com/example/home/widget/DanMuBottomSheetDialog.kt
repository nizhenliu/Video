package com.example.home.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.example.home.R

import com.google.android.material.bottomsheet.BottomSheetDialogFragment



/**
 * @author hahajing 企鹅：444511958
 * @createDate 2022/9/16 7:43
 * @description
 *
 * @updateUser hahajing
 * @updateDate 2022/9/16 7:43
 * @updateRemark
 *
 * @version 1.0.0
 */
class DanMuBottomSheetDialog: BottomSheetDialogFragment() {
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val onCreateDialog = super.onCreateDialog(savedInstanceState)
//        val view= LayoutInflater.from(context).inflate(R.layout.view_danmu,null)
//        dialog?.setContentView(view)
//        initView(view)
//        return onCreateDialog
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.view_danmu, container, false)
        initView(view)
        return view
    }

    var ivSend:ImageView?= null
    var etContent:EditText?=null

    var listener: DanMuListener? = null

    fun setDanMuListener(_listener: DanMuListener){
        listener=_listener
    }

    private fun initView(view: View?) {
        etContent = view?.findViewById<EditText>(R.id.et_home_detail_view_danmu_content)!!
        ivSend= view?.findViewById<ImageView>(R.id.iv_home_detail_view_danmu_send)!!

        ivSend?.setOnClickListener {
            if(listener != null){
                listener?.onClick()
            }
        }
    }

    fun close(){
        etContent?.text?.clear()
        dismiss()
    }

    interface DanMuListener{
        fun onClick()
    }
}