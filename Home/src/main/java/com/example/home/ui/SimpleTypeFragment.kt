package com.example.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.example.home.databinding.FragmentSimpleTypeBinding
import com.example.home.viewmodel.SimpleVideoViewModel
import com.example.home.intent.SimpleVideoIntent
import com.example.home.model.entity.SimpleVideoEntity
import com.example.home.state.SimpleVideoState
import com.example.home.ui.adapter.SimpleRecyclerViewAdapter
import com.example.mvi.ui.BaseMVIFragment
import com.example.widget.common.RedPacketEnum
import com.example.widget.common.RedPacketEvent


import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus


class SimpleTypeFragment : BaseMVIFragment() {
    private var binding: FragmentSimpleTypeBinding? = null
    var viewmodel: SimpleVideoViewModel? = null



    override fun generateViewModel() {
        viewmodel = ViewModelProviders.of(requireActivity()).get(SimpleVideoViewModel::class.java)

    }


    private var isStart: Boolean = false

    override fun initEvent() {

        binding?.srlFragmentSimple?.setOnRefreshListener {
            page = 0
            getSimpleVideo()
            it.finishRefresh()
        }


        binding?.srlFragmentSimple?.setOnLoadMoreListener {
            page++
            getSimpleVideo()
            it.finishLoadMore()
        }

//        binding?.rvFragmentSimple?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                when (newState) {
//                    RecyclerView.SCROLL_STATE_IDLE -> {
//                        if (rvIsScoll) {
//                            //滚动暂停恢复加载图片
//                            Glide.with(requireActivity()).resumeRequests()
//                        }
//                        rvIsScoll = false
//                        EventBus.getDefault().post(RedPacketEvent(RedPacketEnum.PAUSE))
//                    }
//                    RecyclerView.SCROLL_STATE_DRAGGING -> {
//                        rvIsScoll = true
//                        //滚动停止加载图片
//                        Glide.with(requireActivity()).pauseRequests()
//                        startPlayAnim();
//                    }
//                    RecyclerView.SCROLL_STATE_SETTLING -> {
//                        rvIsScoll = true
//                        //滚动停止加载图片
//                        Glide.with(requireActivity()).pauseRequests()
//                        startPlayAnim();
//                    }
//                }
//            }

//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//            }
//        })
    }

    private fun startPlayAnim() {
        if (isStart) {
            EventBus.getDefault().post(RedPacketEvent(RedPacketEnum.RESUME))
        } else {
            EventBus.getDefault().post(RedPacketEvent(RedPacketEnum.INIT))
            isStart = true;
        }
    }


    private var rvIsScoll = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.srlFragmentSimple?.setEnableAutoLoadMore(true)
        binding?.srlFragmentSimple?.setEnableLoadMore(true)
        binding?.srlFragmentSimple?.setEnableRefresh(true)

        initRecycleView();
    }

    private val dataSource: MutableList<SimpleVideoEntity> = mutableListOf()
    var adapter: SimpleRecyclerViewAdapter? = null


    private fun initRecycleView() {
        adapter = SimpleRecyclerViewAdapter(requireActivity(), dataSource)
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = RecyclerView.VERTICAL
        binding?.rvFragmentSimple?.layoutManager = layoutManager
        binding?.rvFragmentSimple?.adapter = adapter
        adapter?.setPlayOnclickListener(object : SimpleRecyclerViewAdapter.PlayOnClickListener{
            override fun onClick(entity: SimpleVideoEntity) {
                ARouter.getInstance().build("/Home/HomeDetailActivity")
                    .withSerializable("data",entity)
                    .navigation(activity)
            }
        })
    }

    override fun getFragmentRootView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSimpleTypeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    var page: Int = 0
    val pagesize: Int = 10
    var channelId: String = ""
    override fun lazyLoad() {
        channelId = arguments?.getString("id").toString()
        dataSource.clear()
        getSimpleVideo()

    }

    private fun getSimpleVideo() {

        if (channelId=="2147483647"){
            dataSource.clear()
            ToastUtils.showLong("加载失败")
        }else{
            lifecycleScope.launch {
                viewmodel?.intents?.send(SimpleVideoIntent.getSimpleVideo(channelId, page, pagesize))
            }
        }
    }

    override fun handleState() {

        lifecycleScope.launchWhenStarted {
            viewmodel?.state?.collect {
                when (it) {
                    is SimpleVideoState.SimpleVideos -> {
                        dataSource.clear()
                        dataSource.addAll(it.list)
                        adapter?.notifyDataSetChanged()
                    }
                    is SimpleVideoState.Failed -> {
                        dataSource.clear()
                        adapter?.notifyDataSetChanged()
                        ToastUtils.showLong("加载失败")
                    }
                }
            }
        }
    }
}