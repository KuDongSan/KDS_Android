package com.codelab.kudongsan.src.main.home.assets.filtering

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityFilteringBinding
import com.codelab.kudongsan.databinding.ActivityFilteringTempBinding
import com.codelab.kudongsan.src.main.home.assets.AssetsActivity
import com.codelab.kudongsan.src.main.home.assets.models.AssetsListData
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import kotlin.math.absoluteValue

class FilteringActivity :
    BaseActivity<ActivityFilteringBinding>(ActivityFilteringBinding::inflate) {

    val data = ArrayList<AssetsListData>()
    val filteredOptions = HashMap<String, String>()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        filteredOptions.put("address", intent.getStringExtra("address").toString())
        init()

        binding.activityFilteringBackButton.setOnClickListener {
            onBackPressed()
        }

        binding.activityFilteringApplyButton.setOnClickListener {
            val intent = Intent(this, AssetsActivity::class.java)
            intent.putExtra("map", filteredOptions)
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.activityFilteringTopLayoutClearAllTextView.setOnClickListener {
            clearAllValues()
        }

    }

    private fun clearAllValues() {
        showCustomToast("설정 값이 모두 초기화 됩니다.")
        binding.apply {
            activityFilteringServiceTypeRadioGroup.clearCheck()
            activityFilteringServiceTypeValueTextView.text = "선택"
            activityFilteringSalesTypeRadioGroup.clearCheck()
            activityFilteringSalesTypeValueTextView.text = "선택"
            activityFilteringDepositTitleTextView.text = "보증금"
            activityFilteringDistanceRadioGroup.clearCheck()
            activityFilteringDistanceValueTextView.text = "선택"
            activityFilteringDepositRangeSeekBar.setProgress(0f, 34f)
            activityFilteringMonthlyRentRangeSeekBar.setProgress(0f, 36f)
            activityFilteringAreaRangeSeekBar.setProgress(0f, 36f)
        }
    }

    fun init() {

        binding.activityFilteringServiceTypeRadioGroup.setOnCheckedChangeListener { group, checkId ->

            when (checkId) {

                R.id.activity_filtering_one_room_radio_button -> {
                    filteredOptions.put("serviceType", "ONEROOM")
                    binding.activityFilteringServiceTypeValueTextView.text = "원룸"
                }

                R.id.activity_filtering_villa_radio_button -> {
                    filteredOptions.put("serviceType", "VILLA")
                    binding.activityFilteringServiceTypeValueTextView.text = "빌라"
                }

                R.id.activity_filtering_office_tel_radio_button -> {
                    filteredOptions.put("serviceType", "OFFICETEL")
                    binding.activityFilteringServiceTypeValueTextView.text = "오피스텔"
                }
            }
        }

        binding.activityFilteringSalesTypeRadioGroup.setOnCheckedChangeListener { group, checkId ->

            binding.apply {
                activityFilteringDepositTitleTextView.visibility = View.VISIBLE
                activityFilteringDepositValueTextView.visibility = View.VISIBLE
                activityFilteringDepositRangeSeekBar.visibility = View.VISIBLE
                activityFilteringDepositThickDivider.visibility = View.VISIBLE

                activityFilteringMonthlyRentValueTextView.visibility = View.VISIBLE
                activityFilteringMonthlyRentRangeSeekBar.visibility = View.VISIBLE
                activityFilteringMonthlyRentTitleTextView.visibility = View.VISIBLE
                activityFilteringMonthlyRentThickDivider.visibility = View.VISIBLE
            }

            when (checkId) {
                R.id.activity_filtering_monthly_rent_radio_button -> {
                    filteredOptions.put("salesType", "MONTHLY_RENT")
                    binding.apply {
                        activityFilteringSalesTypeValueTextView.text = "월세"
                        activityFilteringDepositTitleTextView.text = "보증금"
                    }
                }

                R.id.activity_filtering_yearly_rent_radio_button -> {
                    filteredOptions.put("salesType", "YEARLY_RENT")
                    binding.apply {
                        activityFilteringMonthlyRentValueTextView.visibility = View.GONE
                        activityFilteringMonthlyRentRangeSeekBar.visibility = View.GONE
                        activityFilteringMonthlyRentTitleTextView.visibility = View.GONE
                        activityFilteringMonthlyRentThickDivider.visibility = View.GONE
                        activityFilteringSalesTypeValueTextView.text = "전세"
                        activityFilteringDepositTitleTextView.text = "전세금"
                    }
                }

                R.id.activity_filtering_transaction_radio_button -> {
                    filteredOptions.put("salesType", "DEALING")
                    binding.apply {
                        activityFilteringDepositTitleTextView.visibility = View.GONE
                        activityFilteringDepositValueTextView.visibility = View.GONE
                        activityFilteringDepositRangeSeekBar.visibility = View.GONE
                        activityFilteringDepositThickDivider.visibility = View.GONE
                        activityFilteringMonthlyRentValueTextView.visibility = View.GONE
                        activityFilteringMonthlyRentRangeSeekBar.visibility = View.GONE
                        activityFilteringMonthlyRentTitleTextView.visibility = View.GONE
                        activityFilteringMonthlyRentThickDivider.visibility = View.GONE
                        activityFilteringSalesTypeValueTextView.text = "매매"
                    }
                }
            }
        }

        binding.activityFilteringDistanceRadioGroup.setOnCheckedChangeListener { group, checkId ->

            when (checkId) {
                R.id.activity_filtering_within_five_minutes_radio_button -> {
                    filteredOptions.put("nearestDistance", "5")
                    binding.activityFilteringDistanceValueTextView.text = "5분 이내"
                }

                R.id.activity_filtering_within_ten_minutes_radio_button -> {
                    filteredOptions.put("nearestDistance", "10")
                    binding.activityFilteringDistanceValueTextView.text = "10분 이내"
                }

                R.id.activity_filtering_within_twenty_minutes_radio_button -> {
                    filteredOptions.put("nearestDistance", "20")
                    binding.activityFilteringDistanceValueTextView.text = "20분 이내"
                }
                R.id.activity_filtering_within_thirty_minutes_radio_button -> {
                    filteredOptions.put("nearestDistance", "30")
                    binding.activityFilteringDistanceValueTextView.text = "30분 이내"
                }
                R.id.activity_filtering_does_not_matter_radio_button -> {
                    filteredOptions.remove("nearestDistance")
                    binding.activityFilteringDistanceValueTextView.text = "상관 없음"
                }
            }
        }
        binding.activityFilteringDepositRangeSeekBar.setProgress(0f, 34f)
        binding.activityFilteringDepositRangeSeekBar.setOnRangeChangedListener(object :
            OnRangeChangedListener {
            override fun onRangeChanged(
                view: RangeSeekBar?,
                leftValue: Float,
                rightValue: Float,
                isFromUser: Boolean
            ) {
                val stringArray = resources.getStringArray(R.array.deposit_values)

                var upperDeposit = stringArray.get(leftValue.toInt())
                var lowerDeposit = stringArray.get(rightValue.toInt())


                if (upperDeposit.length > 4) {
                    upperDeposit = upperDeposit.get(0) + "억 " + upperDeposit.subSequence(
                        1,
                        upperDeposit.length
                    ) + "만 원"
                } else {
                    upperDeposit = upperDeposit + "만 원"

                }

                if (lowerDeposit.length > 4) {
                    lowerDeposit = lowerDeposit.get(0) + "억 " + lowerDeposit.subSequence(
                        1,
                        lowerDeposit.length
                    ) + "만 원"
                } else {
                    lowerDeposit = lowerDeposit + "만 원"
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

        binding.activityFilteringMonthlyRentRangeSeekBar.setProgress(0f, 36f)
        binding.activityFilteringMonthlyRentRangeSeekBar.setOnRangeChangedListener(object :
            OnRangeChangedListener {
            override fun onRangeChanged(
                view: RangeSeekBar?,
                leftValue: Float,
                rightValue: Float,
                isFromUser: Boolean
            ) {

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

        binding.activityFilteringAreaRangeSeekBar.setProgress(0f, 36f)
        binding.activityFilteringAreaRangeSeekBar.setOnRangeChangedListener(object :
            OnRangeChangedListener {
            override fun onRangeChanged(
                view: RangeSeekBar?,
                leftValue: Float,
                rightValue: Float,
                isFromUser: Boolean
            ) {

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