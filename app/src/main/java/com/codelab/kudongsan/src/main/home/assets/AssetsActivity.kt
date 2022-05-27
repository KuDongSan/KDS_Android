package com.codelab.kudongsan.src.main.home.assets

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityAssetsBinding
import com.codelab.kudongsan.src.main.detail.DetailActivity
import com.codelab.kudongsan.src.main.home.assets.adapters.AssetsRecyclerAdapter
import com.codelab.kudongsan.src.main.home.assets.models.AssetsListData
import com.codelab.kudongsan.src.main.home.assets.models.GetAssetsResponse
import com.codelab.kudongsan.src.main.home.assets.filtering.FilteringActivity
import com.codelab.kudongsan.src.main.home.assets.filtering.FilteringService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class AssetsActivity : BaseActivity<ActivityAssetsBinding>(ActivityAssetsBinding::inflate),
    AssetsActivityView {

    private lateinit var adapter: AssetsRecyclerAdapter

    private var resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {

                val hashMap = it.data!!.getSerializableExtra("map") as HashMap<String, String>
                FilteringService(view = this).tryGetFilteredAssets(filteredOptions = hashMap)

            }
        }

    val scope = CoroutineScope(Dispatchers.IO)
    val data: MutableList<AssetsListData> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val address = changeIdToAddress(intent.getIntExtra("regionId", 0))

        adapter = AssetsRecyclerAdapter(this@AssetsActivity)

        binding.activityAssetsRegionTitleTextView.text = "$address 매물"
        binding.activityAssetsBackButton.setOnClickListener {
            onBackPressed()
        }

        binding.activityFilteringButton.setOnClickListener {

            val intent = Intent(this@AssetsActivity, FilteringActivity::class.java)
            intent.putExtra("address", address)
            resultLauncher.launch(intent)

        }



        AssetsService(view = this).tryGetAssets(address = address)
        //init()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
    }

    private fun init() {

        binding.activityAssetsSwipeRefreshLayout.setOnRefreshListener {
            binding.activityAssetsSwipeRefreshLayout.isRefreshing = true
            val address = changeIdToAddress(intent.getIntExtra("regionId", 0))
            AssetsService(this).tryGetAssets(address = address)
        }

    }

    private fun changeIdToAddress(id: Int): String? {
        var address: String? = null
        when (id) {
            0 -> address = "광진구"
            1 -> address = "송파구"
            2 -> address = "종로구"
            3 -> address = "마포구"
            4 -> address = "강남구"
            5 -> address = "동작구"
            6 -> address = "서초구"
            7 -> address = "용산구"
            8 -> address = "영등포구"
        }
        return address
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData(response: GetAssetsResponse) {


        val data: MutableList<AssetsListData> = data

        adapter.listData.clear()

        adapter.listData = data
        binding.activityAssetsRecyclerView.adapter = adapter
        binding.activityAssetsRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter.notifyDataSetChanged()

        adapter.listener = object : AssetsRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: AssetsListData, pos: Int) {
                val intent = Intent(this@AssetsActivity, DetailActivity::class.java)
                intent.putExtra("itemId", adapter.listData[pos].itemId)
                startActivity(intent)
            }
        }



        response.forEach { item ->
            data.add(
                AssetsListData(
                    item.itemId,
                    item.salesType,
                    item.serviceType,
                    item.image_thumbnail,
                    item.deposit,
                    item.monthlyRentPrice,
                    item.manageCost,
                    item.area,
                    item.address,
                    item.subways[0].name,
                    item.subways[0].description,
                    item.subways[0].distance
                )
            )
        }
//        adapter.notifyDataSetChanged()
//        binding.activityAssetsSwipeRefreshLayout.isRefreshing = false
    }

    override fun onGetAssetsSuccess(response: GetAssetsResponse) {
        getData(response)
    }

    override fun onGetAssetsFailure(message: String) {
        showCustomToast("오류 : $message")
        Log.d("okhttp", "오류 : $message")
        binding.activityAssetsSwipeRefreshLayout.isRefreshing = false
    }

    override fun onGetFilteringSuccess(response: GetAssetsResponse) {
        getData(response)
    }

    override fun onGetFilteringFailure(message: String) {
        showCustomToast("오류 : $message")
        Log.d("okhttp", "오류 : $message")
        binding.activityAssetsSwipeRefreshLayout.isRefreshing = false
    }
}