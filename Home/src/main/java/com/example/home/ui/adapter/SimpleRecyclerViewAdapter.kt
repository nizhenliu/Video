package com.example.home.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.home.R
import com.example.home.databinding.ItemSimpleInfoBinding
import com.example.tool.utils.DateUtils

import com.example.home.model.entity.SimpleVideoEntity


class SimpleRecyclerViewAdapter(val context: Context, var list: List<SimpleVideoEntity>) :
    RecyclerView.Adapter<SimpleRecyclerViewAdapter.ViewHolder>() {
   inner class ViewHolder(val binding: ItemSimpleInfoBinding):RecyclerView.ViewHolder(binding.root){
       val headerImg = binding.ivItemSimpleInfoHeader
       val author = binding.tvItemSimpleInfoAuthor
       val publishTime = binding.tvItemSimpleInfoPublishtime
       val primaryImg = binding.ivItemSimpleInfoPrimaryimage
       val title = binding.tvItemSimpleInfoTitle
       val shared = binding.llItemSimpleInfoShared
       val commentNum = binding.llItemSimpleInfoCommentnum
       val ivagreeNum = binding.ivItemSimpleInfoAgree
       val agreeNum = binding.llItemSimpleInfoAgreenum
       val subscibe = binding.btnItemSimpleInfoSubscibe
       val txtCommentNum = binding.tvItemSimpleInfoCommentnum
       val txtAgreeNum = binding.tvItemSimpleInfoAgreenum
       val ivPlay=binding.ivItemSimpleInfoPlay
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemSimpleInfoBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val entity= list[position]
        Glide.with(context).load(entity.avatar_url).into(holder.headerImg)
        holder.author.text = entity.name
        holder.publishTime.text = String.format(context.resources.getString(R.string.home_simple_videoinfo_publishtime), DateUtils.diffDateFromUTC(entity.ctime))
        Glide.with(context).load(entity.videomainimag).into(holder.primaryImg)
        holder.title.text = entity.title
        holder.txtAgreeNum.text= entity.commentnum.toString()
        holder.txtAgreeNum.text=  entity.playnum.toString()
        holder.ivPlay.setOnClickListener {
            if (null!=playOnClickListener){
                playOnClickListener.onClick(entity)
            }
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }
    private lateinit var playOnClickListener: PlayOnClickListener
    fun setPlayOnclickListener(_playOnClickListener: PlayOnClickListener){
        playOnClickListener = _playOnClickListener
    }
    interface PlayOnClickListener{
        fun onClick(entity: SimpleVideoEntity)
    }
}