package com.example.home.ui

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.example.home.R
import com.example.home.databinding.FragmentRecommendVideosBinding
import com.example.home.intent.SimpleVideoIntent
import com.example.home.model.entity.SimpleVideoEntity
import com.example.home.state.SimpleVideoState
import com.example.home.viewmodel.SimpleVideoViewModel
import com.example.mvi.ui.BaseMVIFragment


import kotlinx.coroutines.flow.collect


class RecommendVideosFragment : BaseMVIFragment() {
    /**
     * ViewModel
     */
    lateinit var vm: SimpleVideoViewModel

    /**
     * 列表数据源
     */
    lateinit var dataSource:List<SimpleVideoEntity>

    /**
     * 列表适配器
     */
    lateinit var adapter: RecommendAdapter

    /**
     * ViewBinding
     */
    lateinit var binding: FragmentRecommendVideosBinding

    init {
        //初始化数据源
        dataSource= mutableListOf()
    }

    /**
     * 构建ViewModel
     */
    override fun generateViewModel() {
        vm = ViewModelProviders.of(requireActivity()).get(SimpleVideoViewModel::class.java)
    }

    override fun initView() {
        super.initView()
        val gridLayoutManager:GridLayoutManager =GridLayoutManager(context,3)
        binding.rvViewRecommend.layoutManager=gridLayoutManager
        //处理间隔
        binding.rvViewRecommend.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val pos = parent.getChildAdapterPosition(view)
                val column = (pos % 3)
                //设置左上右下间距
                outRect.left = 15
                outRect.right = 15
                outRect.top = 5
                outRect.bottom = 5
                //设置第一列间距
                if (column == 0){
                    outRect.left = 10
                }
                //设置第三列间距
                if (column == 2){
                    outRect.right = 10
                }
            }
        })
        adapter= RecommendAdapter(requireActivity(),dataSource)
        binding.rvViewRecommend.adapter=adapter
        //Item被点击处理
        adapter.setOnClickListener(object : RecommendAdapter.RecommendClickListener {
            override fun onClick(view: View,entity: SimpleVideoEntity) {
                ARouter.getInstance().build("/Home/HomeDetailActivity").withParcelable("data",entity).navigation()
                getBaseActivity().finish()
            }
        })
    }

    override fun initEvent() {

    }

    override fun getFragmentRootView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentRecommendVideosBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun lazyLoad() {
        lifecycleScope.launchWhenStarted {
            vm.intents.send(SimpleVideoIntent.getRecommendSimpleVideo(0,12))
        }

    }

    override fun handleState() {
        lifecycleScope.launchWhenStarted {
            vm.state.collect {
                when(it){
                    is SimpleVideoState.RecommendSimpleVideos -> {
                        dataSource=it.list
                        adapter.updateDataSource(dataSource)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }

    }


    /**
     * RecyclerView 适配器  对应推荐数据列表
     */
    class RecommendAdapter(val context: Context,var list:List<SimpleVideoEntity>): RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder>() {
        private lateinit var onClickListener: RecommendClickListener

        /**
         * 设置点击监听
         */
        fun setOnClickListener(clickListener: RecommendClickListener){
            onClickListener=clickListener;
        }

        class RecommendViewHolder(view:View):RecyclerView.ViewHolder(view){
            val primaryImg:ImageView=view.findViewById(R.id.iv_home_recommendlist_item_primaryimage)
            val tvTitle:TextView=view.findViewById(R.id.tv_home_recommendlist_item_title)
            val llCommend:LinearLayout=view.findViewById(R.id.ll_home_recommend_item)
        }

        /**
         * 更新数据源
         */
        fun updateDataSource(_datasource:List<SimpleVideoEntity>){
            list=_datasource;
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
            val inflate =
                LayoutInflater.from(context).inflate(R.layout.item_recommend_item, null, false)
            return RecommendViewHolder(inflate)
        }

        override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
            val entity = list.get(position)
            Glide.with(context).load(entity.videomainimag).into(holder.primaryImg)
            holder.tvTitle.text=entity.title

            holder.llCommend.setOnClickListener {
                if (onClickListener!= null){
                    onClickListener.onClick(it,entity)
                }
            }

        }

        override fun getItemCount(): Int {
            return list.size
        }

        /**
         * 推荐数据点击监听接口
         */
        interface RecommendClickListener{
            fun onClick(view:View,entity: SimpleVideoEntity)
        }
    }


}