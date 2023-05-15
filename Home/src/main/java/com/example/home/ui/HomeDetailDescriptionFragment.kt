package com.example.home.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.home.R
import com.example.home.databinding.FragmentHomeDetailDescriptionBinding
import com.example.mvi.ui.BaseMVIFragment
import com.example.tool.utils.DpSpUtils
import com.example.home.model.entity.SimpleVideoEntity
import com.example.home.state.DescriptionState
import com.example.home.viewmodel.DescriptionViewModel
import com.example.storage.DBUtils
import com.example.storage.entity.HistoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressWarnings("all")
class HomeDetailDescriptionFragment : BaseMVIFragment() {
    lateinit var data: SimpleVideoEntity
    lateinit var binding: FragmentHomeDetailDescriptionBinding
    lateinit var descriptionViewModel: DescriptionViewModel

    override fun generateViewModel() {
        descriptionViewModel = ViewModelProvider(this).get(DescriptionViewModel::class.java)
    }

    override fun initEvent() {

    }

    override fun getFragmentRootView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeDetailDescriptionBinding.inflate(inflater, container, false)
        lifecycleScope.launch(Dispatchers.IO){
            DBUtils.init(requireContext().applicationContext,"myshop")
            val all = DBUtils.getDB().historyDao().getAll()
            val currentAgreeNum = binding.tvHomeDetailDescriptionAgree.text
            for (historyEntity in all) {
                Log.e("===1",""+historyEntity.title)
                Log.e("===2",""+data.title)
                if (historyEntity.title==data.title) {
                    lifecycleScope.launch(Dispatchers.IO){
                        binding.tvHomeDetailDescriptionAgree.setCompoundDrawables(getLeftDrawable(R.drawable.ic_thumb_up_check), null, null, null)
                        binding.tvHomeDetailDescriptionAgree.text = (currentAgreeNum.toString().toInt() + 1).toString()
                        DBUtils.getDB().historyDao().delete(historyEntity)
                    }
                }
            }
        }
        return binding.root
    }

    var agreeFlag: Boolean = false
    override fun lazyLoad() {
        data = arguments?.getParcelable<SimpleVideoEntity>("data") as SimpleVideoEntity
        binding.tvHomeDetailDescriptionAuthor.text=data.name
        val options:RequestOptions=RequestOptions().circleCrop()
        Glide.with(this).load(data.avatar_url).apply(options).into(binding.ivHomeDetailDescriptionAuthorimg)
        binding.tvHomeDetailDescriptionTitle.text=data.title

        parentFragmentManager.beginTransaction().add(R.id.fl_home_detail_Recommend, RecommendVideosFragment()).commitAllowingStateLoss()
        var historyEntity = HistoryEntity(0, data.id, data.title!!, data.preview_url!!, data.channelid!!, data.image_url!!, data.name!!)
        //点赞
        binding.tvHomeDetailDescriptionAgree.setOnClickListener {
            val currentAgreeNum = binding.tvHomeDetailDescriptionAgree.text
            if (!agreeFlag) {
                binding.tvHomeDetailDescriptionAgree.setCompoundDrawables(getLeftDrawable(R.drawable.ic_thumb_up_check), null, null, null)
                binding.tvHomeDetailDescriptionAgree.text = (currentAgreeNum.toString().toInt() + 1).toString()
                lifecycleScope.launch(Dispatchers.IO){
                    DBUtils.init(requireContext().applicationContext,"myshop")
                    DBUtils.getDB().historyDao().insertAll(historyEntity)
                }
            } else {
                binding.tvHomeDetailDescriptionAgree.setCompoundDrawables(getLeftDrawable(R.drawable.ic_agree), null, null, null)
                binding.tvHomeDetailDescriptionAgree.text = (currentAgreeNum.toString().toInt() - 1).toString()
               lifecycleScope.launch(Dispatchers.IO){
                   DBUtils.getDB().historyDao().delete(historyEntity)
               }
            }
            agreeFlag = !agreeFlag
        }
    }

    private fun getLeftDrawable(icThumbUpCheck: Int): Drawable? {
        val left = resources.getDrawable(icThumbUpCheck)
        val px2dp = DpSpUtils.dp2px(requireContext(),30)
        left.setBounds(0,0,px2dp,px2dp)
        return left
    }
    override fun handleState() {
        lifecycleScope.launchWhenStarted {
            descriptionViewModel.state.collect {
                when(it){
                    is DescriptionState.Agree ->{
                        ToastUtils.showLong("点赞成功")
                    }
                    is DescriptionState.Failed->{
                        ToastUtils.showLong("点赞失败")
                    }
                    else -> {}
                }
            }
        }
    }
}