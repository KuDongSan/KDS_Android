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

        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "광진구", "#건대입구역", "#성수동 펍"))
        listData.add(AssetsRecyclerViewData(R.drawable.img_songpa, "송파구", "#석촌호수", "#방이동 술집"))
        listData.add(AssetsRecyclerViewData(R.drawable.img_jongro, "종로구", "#경복궁", "#인사동 놀거리"))
        listData.add(AssetsRecyclerViewData(R.drawable.img_mapo, "마포구", "#홍대입구역", "#망원동 카페"))
        listData.add(AssetsRecyclerViewData(R.drawable.img_gangnam, "강남구", "#강남역", "#압구정로데오"))
        listData.add(AssetsRecyclerViewData(R.drawable.img_dongjak, "동작구", "#동작 노을카페", "#노량진 컵밥거리"))
        listData.add(AssetsRecyclerViewData(R.drawable.img_seocho, "서초구", "#반포 한강공원", "#방배동 카페거리"))
        listData.add(AssetsRecyclerViewData(R.drawable.img_yongsan, "용산구", "#이태원 루프탑", "#용산 아이파크몰 놀거리"))
        listData.add(AssetsRecyclerViewData(R.drawable.img_yeongdeungpo, "영등포구", "#타임스퀘어 맛집", "#여의도 한강공원"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "강동구", "#건대입구역", "#성수동 맛집"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "노원구", "#건대입구역", "#성수동 맛집"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "강서구", "#건대입구역", "#성수동 맛집"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "성북구", "#건대입구역", "#성수동 맛집"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "성동구", "#건대입구역", "#성수동 맛집"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "동대문구", "#건대입구역", "#성수동 맛집"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "도봉구", "#건대입구역", "#성수동 맛집"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "서대문구", "#건대입구역", "#성수동 맛집"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "금천구", "#건대입구역", "#성수동 맛집"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "관악구", "#건대입구역", "#성수동 맛집"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "중구", "#건대입구역", "#성수동 맛집"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "양천구", "#건대입구역", "#성수동 맛집"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "강북구", "#건대입구역", "#성수동 맛집"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "중랑구", "#건대입구역", "#성수동 맛집"))


        return listData
    }
}