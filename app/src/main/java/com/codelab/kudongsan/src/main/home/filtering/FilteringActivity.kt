package com.codelab.kudongsan.src.main.home.filtering

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityFilteringBinding
import com.codelab.kudongsan.src.main.home.assets.AssetsActivity
import com.codelab.kudongsan.src.main.home.assets.models.AssetsListData
import com.codelab.kudongsan.src.main.home.filtering.models.GetFilteredAssetsResponse

class FilteringActivity : BaseActivity<ActivityFilteringBinding>(ActivityFilteringBinding::inflate),
    FilteringActivityView {

//    val data: MutableList<AssetsListData> = mutableListOf()

    val data2 = ArrayList<AssetsListData>()

    val map = mutableMapOf<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val address = changeIdToAddress(intent.getIntExtra("regionId", 0))
//        binding.activityAssetsRegionTitleTextView.text = "$address 매물"
        init()

        binding.activityDetailBackButton.setOnClickListener {
            onBackPressed()
        }

        binding.activityDetailLikeButton.setOnClickListener {
            FilteringService(view = this).tryGetFilteredAssets(map)
        }

    }

    fun init() :Map<String, String>{

        binding.radioGroup.setOnCheckedChangeListener { group, checkId ->

            when(checkId) {

                R.id.radioButton1 -> map.put("serviceType", "ONEROOM")

                R.id.radioButton2 -> map.put("serviceType", "VILLA")

                R.id.radioButton3 -> map.put("serviceType", "OFFICETEL")

                else -> {}
            }
        }

        binding.radioGroup2.setOnCheckedChangeListener { group, checkId ->

            when(checkId) {

                R.id.radioButton4 -> map.put("salesType", "MONTHLY_RENT")

                R.id.radioButton5 -> map.put("salesType", "YEARLY_RENT")

                R.id.radioButton6 -> map.put("salesType", "DEALING")

                else -> {}
            }
        }

        map.put("address", "광진구")
        map.put("upperDeposit", "10")
        map.put("lowerDeposit", "10000")
        map.put("nearestDistance", "40")
        map.put("upperArea", "10")
        map.put("lowerArea", "40")

        return map

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
    }


    override fun onGetFilteringSuccess(response: GetFilteredAssetsResponse) {

        val intent = Intent(this, AssetsActivity::class.java)


        response.forEach {
            data2.add(
                AssetsListData(it.itemId,it.salesType,it.serviceType,
                    it.image_thumbnail,it.deposit,it.monthlyRentPrice, it.manageCost,
                    it.area, it.address, it.subways[0].name, it.subways[0].description, it.subways[0].distance)
            )
        }

        println(data2)
        intent.putParcelableArrayListExtra("response", data2)
        startActivity(intent)


    }


    override fun onGetFilteringFailure(message: String) {
        showCustomToast("오류 : $message")
        Log.d("okhttp", "오류 : $message")
    }
}