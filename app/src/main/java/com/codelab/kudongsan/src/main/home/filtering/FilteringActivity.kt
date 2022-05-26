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

class FilteringActivity : BaseActivity<ActivityFilteringBinding>(ActivityFilteringBinding::inflate)
     {

    val data = ArrayList<AssetsListData>()
    val map = HashMap<String, String>()

    var serviceType = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //TODO intent를 통해 address 받아오는 로직 구현
        map.put("address", "광진구")
//        val address = changeIdToAddress(intent.getIntExtra("regionId", 0))
        init()

        binding.activityDetailBackButton.setOnClickListener {
            onBackPressed()
        }

        binding.activityDetailLikeButton.setOnClickListener {

            val intent = Intent(this, AssetsActivity::class.java)
            intent.putExtra("map", map)
            setResult(RESULT_OK, intent)
            finish()
//            FilteringService(view = this).tryGetFilteredAssets(map)
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
                }

                R.id.radioButton5 -> {
                    map.put("salesType", "YEARLY_RENT")
                }

                R.id.radioButton6 -> {
                    map.put("salesType", "DEALING")
                    binding.activityFilteringDepositConstraintLayout.visibility = View.GONE
                }
            }
        }

        binding.radioGroup3.setOnCheckedChangeListener { group, checkId ->

            when (checkId) {
                R.id.radioButton7 -> {
                    map.put("nearestDistance", "5")
                }

                R.id.radioButton8 -> {
                    map.put("nearestDistance", "10")
                }

                R.id.radioButton9 -> {
                    map.put("nearestDistance", "20")
                }
                R.id.radioButton10 -> {
                    map.put("nearestDistance", "30")
                }
                R.id.radioButton11 -> {
                    map.put("nearestDistance", "")
                }
            }
        }

        binding.activityFilteringDepositRangeSeekBar.setOnRangeChangedListener(object :
            OnRangeChangedListener {
            override fun onRangeChanged(view: RangeSeekBar?, leftValue: Float, rightValue: Float, isFromUser: Boolean) {
                val stringArray = resources.getStringArray(R.array.deposit_values)

                map.put("upperDeposit", stringArray.get(leftValue.toInt()))
                map.put("lowerDeposit", stringArray.get(rightValue.toInt()))

            }

            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }

            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {

            }

        })

        binding.activityFilteringAreaRangeSeekBar.setOnRangeChangedListener(object :
        OnRangeChangedListener {
            override fun onRangeChanged(view: RangeSeekBar?, leftValue: Float, rightValue: Float, isFromUser: Boolean) {

                map.put("upperArea", (leftValue.toInt()).toString())
                map.put("lowerArea", (rightValue.toInt()).toString())

            }

            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }

            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
    }


//    override fun onGetFilteringSuccess(response: GetFilteredAssetsResponse) {
//
//
//        response.forEach {
//            data.add(
//                AssetsListData(
//                    it.itemId, it.salesType, it.serviceType,
//                    it.image_thumbnail, it.deposit, it.monthlyRentPrice, it.manageCost,
//                    it.area, it.address, it.subways[0].name, it.subways[0].description, it.subways[0].distance
//                )
//            )
//        }
//        val intent = Intent(this, AssetsActivity::class.java)
//
//        intent.putParcelableArrayListExtra("response", data)
//        setResult(RESULT_OK, intent)
//
//        showCustomToast("===============Check!!!!==========")
//
//        finish()
//
//    }
//
//
//    override fun onGetFilteringFailure(message: String) {
//        showCustomToast("오류 : $message")
//        Log.d("okhttp", "오류 : $message")
//    }
}