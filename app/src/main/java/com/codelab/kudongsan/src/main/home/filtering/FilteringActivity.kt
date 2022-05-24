package com.codelab.kudongsan.src.main.home.filtering

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityFilteringBinding
import com.codelab.kudongsan.src.main.home.assets.AssetsActivity
import com.codelab.kudongsan.src.main.home.assets.models.AssetsListData
import com.codelab.kudongsan.src.main.home.filtering.models.GetFilteredAssetsResponse
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar

class FilteringActivity : BaseActivity<ActivityFilteringBinding>(ActivityFilteringBinding::inflate),
    FilteringActivityView {

    val data = ArrayList<AssetsListData>()

    val map = mutableMapOf<String, String>()

    var serviceType = ""

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

    fun init() {

        binding.radioGroup.setOnCheckedChangeListener { group, checkId ->

            when (checkId) {

                R.id.radioButton1 -> {
                    map.put("serviceType", "ONEROOM")
                }

                R.id.radioButton2 -> {
                    map.put("serviceType", "VILLA")
                }

                R.id.radioButton3 -> {
                    map.put("serviceType", "OFFICETEL")
                }
            }
        }

        binding.radioGroup2.setOnCheckedChangeListener { group, checkId ->

            binding.activityFilteringDepositConstraintLayout.visibility = View.VISIBLE

            when (checkId) {
                R.id.radioButton4 -> {
                    map.put("salesType", "MONTHLY_RENT")
                    binding.activityFilteringDepositTitleTextView.text = "보증금( 단위: 십 만원)"
                }

                R.id.radioButton5 -> {
                    map.put("salesType", "YEARLY_RENT")
                    binding.activityFilteringDepositTitleTextView.text = "보증금( 단위: 백 만원)"
                }

                R.id.radioButton6 -> {
                    map.put("salesType", "DEALING")
                    binding.activityFilteringDepositConstraintLayout.visibility = View.GONE
                }
            }
        }
//        binding.activityFilteringDepositRangeSeekBar.left = 0
//        binding.activityFilteringDepositRangeSeekBar.right = 1000000

        binding.activityFilteringDepositRangeSeekBar.setOnRangeChangedListener(object :
            OnRangeChangedListener {
            override fun onRangeChanged(view: RangeSeekBar?, leftValue: Float, rightValue: Float, isFromUser: Boolean) {
                map.put("upperDeposit", (leftValue * 10).toInt().toString())
                map.put("lowerDeposit", (rightValue * 10).toInt().toString())

                println(map.get("upperDeposit"))
                println(map.get("lowerDeposit"))

            }

            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }

            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }

        })

        map.put("address", "광진구")
        map.put("nearestDistance", "40")
        map.put("upperArea", "10")
        map.put("lowerArea", "40")

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
    }


    override fun onGetFilteringSuccess(response: GetFilteredAssetsResponse) {

        val intent = Intent(this, AssetsActivity::class.java)

        response.forEach {
            data.add(
                AssetsListData(
                    it.itemId, it.salesType, it.serviceType,
                    it.image_thumbnail, it.deposit, it.monthlyRentPrice, it.manageCost,
                    it.area, it.address, it.subways[0].name, it.subways[0].description, it.subways[0].distance
                )
            )
        }

        println(data)
        intent.putParcelableArrayListExtra("response", data)
        startActivity(intent)


    }


    override fun onGetFilteringFailure(message: String) {
        showCustomToast("오류 : $message")
        Log.d("okhttp", "오류 : $message")
    }
}