package com.example.videohall.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.home.databinding.ItemSimpleInfoBinding
import com.example.mvi.ui.BaseMVIFragment
import com.example.tool.utils.LogUtils
import com.example.videohall.R
import com.example.videohall.databinding.FragmentVideoHallBinding
import com.example.videohall.databinding.ItemCinemaMutilvideoBinding
import com.example.videohall.databinding.ItemCinemaSubtypeBinding
import com.example.videohall.intent.MutilTypeIntent
import com.example.videohall.intent.MutilVideoIntent
import com.example.videohall.model.entity.MutilTypeEntity
import com.example.videohall.model.entity.MutilVideoEntity
import com.example.videohall.state.MutilTypeState
import com.example.videohall.state.MutilVideoState
import com.example.videohall.viewmodel.MutilTypeViewModel
import com.example.videohall.viewmodel.MutilVideoViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.UTC
import javax.sql.CommonDataSource


class VideoHallFragment : BaseMVIFragment() {
    lateinit var binding: FragmentVideoHallBinding
    lateinit var viewmodel: MutilTypeViewModel
    lateinit var mutilVideoViewModel: MutilVideoViewModel
    override fun generateViewModel() {
        viewmodel = ViewModelProvider(this).get(MutilTypeViewModel::class.java)
        mutilVideoViewModel = ViewModelProvider(this).get(MutilVideoViewModel::class.java)
    }

    override fun initEvent() {
        binding.cinemaTabTypes.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.tag!=null){
                    val entity:MutilTypeEntity= tab?.tag as MutilTypeEntity
                    lifecycleScope.launch {
                        viewmodel.intents.send(MutilTypeIntent.getSubMutilType(entity.id))
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        /**
         * 加载更多视频列表数据
         */
        binding.srlCinemaSimple.setOnLoadMoreListener {
            currentPage=currentPage+1
            lifecycleScope.launch {
                mutilVideoViewModel.intents.send(MutilVideoIntent.getMutilVideoInfo(currentPage,pageSize,currentTypeId))
            }
        }

        /**
         * 下列刷新列表数据
         */
        binding.srlCinemaSimple.setOnRefreshListener {
            currentPage=0
            lifecycleScope.launch {
                mutilVideoViewModel.intents.send(MutilVideoIntent.getMutilVideoInfo(currentPage,pageSize,currentTypeId))
            }
        }
    }

    override fun getFragmentRootView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoHallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewmodel.intents.send(MutilTypeIntent.getMutilType)
        }
        initSubMutilTypeRv()
        initMutilVideoRv()
    }


    lateinit var mutilVideoAdapter:MutilVideoAdapter
    private var mutilVideoDataSource:MutableList<MutilVideoEntity> = mutableListOf<MutilVideoEntity>()
    private fun initMutilVideoRv() {
        val layoutManager = GridLayoutManager(requireActivity(),2)
        binding.cinemaRvMutilvideo.layoutManager = layoutManager
        mutilVideoAdapter = MutilVideoAdapter(requireActivity(),mutilVideoDataSource,object :MutilVideoOnClickListener{
            override fun onClick(view: View, entity: MutilVideoEntity) {

            }
        })
        binding.cinemaRvMutilvideo.adapter=mutilVideoAdapter
    }

    private var subTypesDataSource = mutableListOf<MutilTypeEntity>()
    private lateinit var subMutilTypeAdapter: SubMutilTypeAdapter
    private fun initSubMutilTypeRv() {
        val layoutManager: GridLayoutManager = GridLayoutManager(requireContext(), 4)
        binding.cinemaRvSubtypes.layoutManager = layoutManager
        subMutilTypeAdapter = SubMutilTypeAdapter(requireActivity(),subTypesDataSource,object :SubMutilTypeOnCilckListener{
            override fun onClick(view: View, entity: MutilTypeEntity) {

                //清空视频列表数据
                mutilVideoAdapter._mutilVideoDataSource.clear()
//                LogUtils.d("123","clear size = ${mutilVideoAdapter._mutilVideoDataSource.size}")
//                mutilVideoAdapter.notifyDataSetChanged()

                //获取到子类型的id
                //根据id获取视频列表信息
                lifecycleScope.launch {
                    currentTypeId=entity.id
                    currentPage=0
                    mutilVideoViewModel.intents.send(MutilVideoIntent.getMutilVideoInfo(currentPage,pageSize,currentTypeId))
                }
            }

            override fun onClick(p0: View?) {

            }
        })
        binding.cinemaRvSubtypes.adapter=subMutilTypeAdapter
    }

    override fun lazyLoad() {

    }

    /*
    * 视频列表当前请求页数及每页数据数
    * */
    private var currentPage = 0
    private var pageSize = 20

    /*
    * 当前类别
    * */
    private var currentTypeId = 0
    override fun handleState() {
        lifecycleScope.launch {

            viewmodel.state.collect {
                when (it){
                    is MutilTypeState.getMutilTypeSuccessState ->{
                        if (it.data.isNotEmpty()){
                            for(item in it.data){
                                binding.cinemaTabTypes.addTab(binding.cinemaTabTypes.newTab().setText(item.typename).setTag(item))
                            }
                        }
                    }
                    is MutilTypeState.getMutilTypeFailureState -> {
                        showMsg("分类信息获取失败")
                    }
                    is MutilTypeState.getSubMutilTypeSuccessState -> {
                        subMutilTypeAdapter.dataSource=it.data
                        subMutilTypeAdapter.notifyDataSetChanged()

                        currentTypeId=it.data.get(0).id
                        mutilVideoViewModel.intents.send(MutilVideoIntent.getMutilVideoInfo(currentPage,pageSize,it.data.get(0).id))

                    }
                    is MutilTypeState.getSubMutilTypeFailureState -> {
                        showMsg("获取子类型失败")
                    }
                }
            }
        }
        lifecycleScope.launch {
            mutilVideoViewModel.state.collect {
                when(it){
                    is MutilVideoState.getMutilVideoInfoSuccess -> {
                        if(mutilVideoAdapter._mutilVideoDataSource.size>0){
                            LogUtils.d("123","size=${mutilVideoAdapter._mutilVideoDataSource.size}")
                            mutilVideoAdapter._mutilVideoDataSource.addAll(it.data)
                        }else{
                            mutilVideoAdapter._mutilVideoDataSource= it.data as MutableList<MutilVideoEntity>
                        }

                        mutilVideoAdapter.notifyDataSetChanged()
                        withContext(Dispatchers.Main){
                            binding.srlCinemaSimple.finishRefresh(true)
                            binding.srlCinemaSimple.finishLoadMore()
                        }
                    }
                    is MutilVideoState.getMutilVideoInfoFailed -> {
                        withContext(Dispatchers.Main){
                            binding.srlCinemaSimple.finishRefresh(false)
                            binding.srlCinemaSimple.finishLoadMore(false)
                        }
                        showMsg("获取视频列表信息失败")
                    }
                }
            }
        }
    }


    /*
    * 子類別適配器
    * */
    class SubMutilTypeAdapter(val context: Context,var dataSource:List<MutilTypeEntity>,val listener: SubMutilTypeOnCilckListener):RecyclerView.Adapter<SubMutilTypeAdapter.SubMutilTypeVieHolder>(){

        class SubMutilTypeVieHolder(binding:ItemCinemaSubtypeBinding,view:View):RecyclerView.ViewHolder(view){
            val txtContent=binding.tvCinemaItemSubtypeText
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubMutilTypeVieHolder {
            val binding = ItemCinemaSubtypeBinding.inflate(LayoutInflater.from(context),parent,false)
            return SubMutilTypeVieHolder(binding,binding.root)
        }

        override fun onBindViewHolder(holder: SubMutilTypeVieHolder, position: Int) {
            val entity = dataSource.get(position)
            holder.txtContent.text=entity.typename
            holder.txtContent.setOnClickListener {
                listener.onClick(it,entity)
            }
        }

        override fun getItemCount(): Int {
            return dataSource.size
        }
    }

    /*
      * 视频列表适配器
      * */
    class MutilVideoAdapter(val context: Context,var _mutilVideoDataSource:MutableList<MutilVideoEntity>,val callback:MutilVideoOnClickListener):RecyclerView.Adapter<MutilVideoAdapter.MutilVideoViewHolder>(){
        class MutilVideoViewHolder(view:View,binding: ItemCinemaMutilvideoBinding): RecyclerView.ViewHolder(view){
            val star=binding.ivCinemaItemStar
            val dubo=binding.tvCinemaItemVideoDb
            val title=binding.tvCinemaItemVideoTitle
            val author=binding.tvCinemaItemVideoAuthor
            val hot=binding.tvCinemaItemVideoHot
            val more=binding.ivCinemaItemMore
            val primaryImg=binding.ivCinemaItemPrimaryImage
            val root=binding.clCinemaRoot
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MutilVideoViewHolder {
            val binding = ItemCinemaMutilvideoBinding.inflate(LayoutInflater.from(context),parent,false)
            return MutilVideoViewHolder(binding.root,binding)
        }

        override fun onBindViewHolder(holder: MutilVideoViewHolder, position: Int) {
            val entity= _mutilVideoDataSource.get(position)
            holder.author.text=entity.actorlist
            if(position>3){
                holder.dubo.visibility=View.GONE
            }
            if(position>3){
                holder.star.visibility=View.GONE
            }
            holder.title.text=entity.title
            holder.hot.setOnClickListener {

            }
            holder.more.setOnClickListener {

            }

            holder.root.setOnClickListener {
                callback.onClick(it,entity)
            }

            Glide.with(context).load(entity.videomainimag).into(holder.primaryImg)
        }

        override fun getItemCount(): Int {
            return _mutilVideoDataSource.size
        }
    }

    interface SubMutilTypeOnCilckListener : View.OnClickListener {
        fun onClick(view: View, entity: MutilTypeEntity)
    }

    /*
    * 视频点击回调接口
    * */
    interface MutilVideoOnClickListener {
        fun onClick(view: View, entity: MutilVideoEntity)
    }
}