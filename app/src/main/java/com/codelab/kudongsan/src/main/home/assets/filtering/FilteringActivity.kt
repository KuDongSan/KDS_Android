package com.codelab.kudongsan.src.main.home.assets.filtering

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityFilteringBinding
import com.codelab.kudongsan.src.main.home.assets.AssetsActivity
import com.codelab.kudongsan.src.main.home.assets.models.AssetsListData
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar

class FilteringActivity : BaseActivity<ActivityFilteringBinding>(ActivityFilteringBinding::inflate) {

    val data = ArrayList<AssetsListData>()
    val filteredOptions = HashMap<String, String>()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // 기존에 액션바 어떻게?
        val actionBar: ActionBar? = supportActionBar
        actionBar!!.hide()

        filteredOptions.put("address", intent.getStringExtra("address").toString())
        init()

        binding.activityDetailBackButton.setOnClickListener {
            onBackPressed()
        }

        binding.activityDetailLikeButton.setOnClickListener {

            val intent = Intent(this, AssetsActivity::class.java)
            intent.putExtra("map", filteredOptions)
            setResult(RESULT_OK, intent)
            finish()

        }

    }

    fun init() {

        binding.serviceTypeGroup.setOnCheckedChangeListener { group, checkId ->

            when (checkId) {

                R.id.radioButton1 -> {
                    filteredOptions.put("serviceType", "ONEROOM")
                }

                R.id.radioButton2 -> {
                    filteredOptions.put("serviceType", "VILLA")
                }

                R.id.radioButton3 -> {
                    filteredOptions.put("serviceType", "OFFICETEL")
                }
            }
        }

        binding.salesTypeGroup.setOnCheckedChangeListener { group, checkId ->

            binding.activityFilteringDepositConstraintLayout.visibility = View.VISIBLE

            binding.activityFilteringMonthlyRentValueTextView.visibility = View.VISIBLE
            binding.activityFilteringMonthlyRentRangeSeekBar.visibility = View.VISIBLE
            binding.activityFilteringMonthlyRentTitleTextView.visibility = View.VISIBLE

            when (checkId) {
                R.id.radioButton4 -> {
                    filteredOptions.put("salesType", "MONTHLY_RENT")
                }

                R.id.radioButton5 -> {
                    filteredOptions.put("salesType", "YEARLY_RENT")
                    binding.activityFilteringMonthlyRentValueTextView.visibility = View.GONE
                    binding.activityFilteringMonthlyRentRangeSeekBar.visibility = View.GONE
                    binding.activityFilteringMonthlyRentTitleTextView.visibility = View.GONE

                }

                R.id.radioButton6 -> {
                    filteredOptions.put("salesType", "DEALING")
                    binding.activityFilteringDepositConstraintLayout.visibility = View.GONE
                }
            }
        }

        binding.nearestDistanceGroup.setOnCheckedChangeListener { group, checkId ->

            when (checkId) {
                R.id.radioButton7 -> {
                    filteredOptions.put("nearestDistance", "5")
                }

                R.id.radioButton8 -> {
                    filteredOptions.put("nearestDistance", "10")
                }

                R.id.radioButton9 -> {
                    filteredOptions.put("nearestDistance", "20")
                }
                R.id.radioButton10 -> {
                    filteredOptions.put("nearestDistance", "30")
                }
                R.id.radioButton11 -> {
                    filteredOptions.put("nearestDistance", "")
                }
            }
        }

        binding.activityFilteringDepositRangeSeekBar.setOnRangeChangedListener(object :
            OnRangeChangedListener {
            override fun onRangeChanged(view: RangeSeekBar?, leftValue: Float, rightValue: Float, isFromUser: Boolean) {
                val stringArray = resources.getStringArray(R.array.deposit_values)

                var upperDeposit = stringArray.get(leftValue.toInt())
                var lowerDeposit = stringArray.get(rightValue.toInt())


                if (upperDeposit.length > 4) {
                    upperDeposit = upperDeposit.get(0) + "억 " + upperDeposit.subSequence(1, upperDeposit.length) +"만 원"
                }else {
                    upperDeposit = upperDeposit +"만 원"

                }

                if (lowerDeposit.length > 4) {
                    lowerDeposit = lowerDeposit.get(0) + "억 " + lowerDeposit.subSequence(1, lowerDeposit.length) +"만 원"
                }else {
                    lowerDeposit = lowerDeposit +"만 원"
                }


                filteredOptions.put("upperDeposit", stringArray.get(leftValue.toInt()))
                filteredOptions.put("lowerDeposit", stringArray.get(rightValue.toInt()))


                if (leftValue.toInt() == 0) {
                    upperDeposit = "0"
                }
                if (rightValue.toInt() == 34) {
                    filteredOptions.remove("lowerDeposit")
                    lowerDeposit = "무제한"
                }

                val str = "%s ~ %s".format(upperDeposit, lowerDeposit)
                binding.activityFilteringDepositValueTextView.text = str


            }

            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }

            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {

            }

        })

        binding.activityFilteringMonthlyRentRangeSeekBar.setOnRangeChangedListener(object :
            OnRangeChangedListener {
            override fun onRangeChanged(view: RangeSeekBar?, leftValue: Float, rightValue: Float, isFromUser: Boolean) {

                var upperMonthlyRent = (leftValue.toInt() * 10).toString() + "만 원"
                var lowerMonthlyRent = (rightValue.toInt() * 10).toString() + "만 원"

                filteredOptions.put("upperMonthlyRent", (leftValue.toInt() * 10).toString())
                filteredOptions.put("lowerMonthlyRent", (rightValue.toInt() * 10).toString())

                if (leftValue.toInt() == 0) {
                    upperMonthlyRent = "0"
                }
                if (rightValue.toInt() == 36) {
                    filteredOptions.remove("lowerMonthlyRent")
                    lowerMonthlyRent = "무제한"
                }

                val str = "%s ~ %s".format(upperMonthlyRent, lowerMonthlyRent)
                binding.activityFilteringMonthlyRentValueTextView.text = str


            }

            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }

            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {

            }

        })


        binding.activityFilteringAreaRangeSeekBar.setOnRangeChangedListener(object :
            OnRangeChangedListener {
            override fun onRangeChanged(view: RangeSeekBar?, leftValue: Float, rightValue: Float, isFromUser: Boolean) {

                var upperArea = (leftValue.toInt()).toString() + "㎡"
                var lowerArea = (rightValue.toInt()).toString() + "㎡"


                filteredOptions.put("upperArea", (leftValue.toInt()).toString())
                filteredOptions.put("lowerArea", (rightValue.toInt()).toString())

                if (rightValue.toInt() == 146) {
                    filteredOptions.remove("lowerArea")
                    lowerArea = "무제한"
                }

                val str = "%s ~ %s".format(upperArea, lowerArea)
                binding.activityFilteringAreaValueTextView.text = str

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

}