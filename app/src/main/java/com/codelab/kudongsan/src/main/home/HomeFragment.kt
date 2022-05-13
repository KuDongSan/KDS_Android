package com.codelab.kudongsan.src.main.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseFragment
import com.codelab.kudongsan.databinding.FragmentHomeBinding
import com.codelab.kudongsan.src.main.home.adapters.AssetsRecyclerViewAdapter
import com.codelab.kudongsan.src.main.home.models.AssetsRecyclerViewData

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home),
    HomeFragmentView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val assetsData: MutableList<AssetsRecyclerViewData> = loadData()
        val magazineAdapter = AssetsRecyclerViewAdapter()
        magazineAdapter.listData = assetsData
        with(binding.fragmentHomeHorizontalAssetsListRecyclerView) {
            adapter = magazineAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun loadData(): MutableList<AssetsRecyclerViewData> {
        val listData: MutableList<AssetsRecyclerViewData> = mutableListOf()

        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "광진구", "#건대입구역", "#성수동 맛집"))

        return listData
    }
}