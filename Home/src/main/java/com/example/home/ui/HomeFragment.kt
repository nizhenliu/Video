package com.example.home.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.PagerAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.home.databinding.FragmentHomeBinding
import com.example.mvi.ui.BaseMVIFragment
import com.example.home.intent.SimpleTypeIntent
import com.example.home.model.entity.SimpleTypeEntity
import com.example.home.state.SimpleTypeState
import com.example.home.viewmodel.SimpleTypeViewModel

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Route(path ="/Home/HomeFragment")
class HomeFragment : BaseMVIFragment() {
    lateinit var binding: FragmentHomeBinding
   var flog :Boolean = false
    var simpleTypeViewModel: SimpleTypeViewModel? = null
    override fun generateViewModel() {
        simpleTypeViewModel =
            ViewModelProviders.of(requireActivity()).get(SimpleTypeViewModel::class.java)//
    }

    override fun initEvent() {

    }

    override fun getFragmentRootView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
//        ReadWriteProperty
//        ReadOnlyProperty<>
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSimpleTypeData()
    }

    private fun loadSimpleTypeData() {
        lifecycleScope.launch {
            simpleTypeViewModel?.intents?.send(SimpleTypeIntent.getSimpleType)
        }
    }

    override fun lazyLoad() {

    }

    override fun handleState() {
        lifecycleScope.launchWhenStarted {
            simpleTypeViewModel?.state?.collect {
                flog = it  is SimpleTypeState.Failed
//                Log.e("====77777",""+flog)

                when (it) {
                    is SimpleTypeState.SimpleTypes -> {
                        generateTabs(it.list)
                    }
                    is SimpleTypeState.Failed -> {
                        showMsg("Failed")
                    }
                }
            }
        }
    }
    private fun generateTabs(list: List<SimpleTypeEntity>?) {
        val adapter = list?.let { HomePagerAdapter(it, this.childFragmentManager) }
        binding.vpHome.adapter = adapter
        binding.tlHomeVideotype.setupWithViewPager(binding.vpHome)
        binding.vpHome.adapter?.notifyDataSetChanged()

    }


    class HomePagerAdapter(val types: List<SimpleTypeEntity>, val manager: FragmentManager) :
        FragmentStatePagerAdapter(manager) {
        private val fragments: MutableList<Fragment>

        init {
            fragments = mutableListOf()
            for (entity in types) {
                val fragment = SimpleTypeFragment()
                val bundle = Bundle()
                bundle.putString("id", entity.channelid)
                fragment.arguments = bundle
                fragments.add(fragment)
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
//            Log.e("===", "" + types.get(position).typename)
            return types.get(position).typename
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments.get(position)
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }
    }
}

