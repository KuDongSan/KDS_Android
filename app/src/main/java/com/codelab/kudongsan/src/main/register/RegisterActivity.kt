package com.codelab.kudongsan.src.main.register

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity<ActivityRegisterBinding>(ActivityRegisterBinding::inflate) {

    var saleType: String = ""
    var roomType: String = ""
    var isSuggested: Boolean = false
    var manageCost: String = "N"

    enum class SalesType {
        YEARLY_RENT, MONTHLY_RENT, DEALING
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            activityRegisterBackButton.setOnClickListener {
                onBackPressed()
            }
            activityRegisterSalesTypeButton.setOnClickListener {
                showSalesTypeDialog()
            }
            activityRegisterRoomTypeButton.setOnClickListener {
                showRoomTypeDialog()
            }
            activityRegisterManageCostEditText.addTextChangedListener {
                // 글자 입력시 색상 변화
                detectKeyboard()
            }
            activityRegisterNoManageCostTextView.setOnClickListener {
                isSuggested = !isSuggested
                binding.apply {
                    if (isSuggested) {
                        activityRegisterManageCostButtonDefault.visibility = View.INVISIBLE
                        activityRegisterManageCostButtonEvent.visibility = View.INVISIBLE
                        activityRegisterManageCostButtonClicked.visibility = View.VISIBLE
                        manageCost = "Y"
                    }

                    // 일단 대충
                    else {
                        activityRegisterManageCostButtonDefault.visibility = View.INVISIBLE
                        activityRegisterManageCostButtonEvent.visibility = View.VISIBLE
                        activityRegisterManageCostButtonClicked.visibility = View.INVISIBLE
                        manageCost = "Y"
                    }
                }
            }



        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
    }

    private fun showSalesTypeDialog() {
        val items = arrayOf(
            "전세",
            "월세",
            "매매"
        )
        AlertDialog.Builder(this)
            .setItems(items) { _, which ->
                binding.activityRegisterSalesTypeTextView.text = items[which]
                saleType = when (which) {
                    0 -> SalesType.YEARLY_RENT.toString()
                    1 -> SalesType.MONTHLY_RENT.toString()
                    2 -> SalesType.DEALING.toString()
                    else -> "error"
                }
            }
            .show()
    }

    private fun showRoomTypeDialog() {
        val items = arrayOf(
            "오픈형 원룸",
            "분리형 원룸",
            "복층형 원룸",
            "투룸",
            "쓰리룸+",
            "포룸+"
        )
        AlertDialog.Builder(this)
            .setItems(items) { _, which ->
                binding.activityRegisterRoomTypeTextView.text = items[which]
                roomType = when (which) {
                    0 -> "01"
                    1 -> "02"
                    2 -> "03"
                    3 -> "04"
                    4 -> "05"
                    5 -> "06"
                    else -> "error"
                }
            }
            .show()
    }

    private fun detectKeyboard() {
        when (binding.activityRegisterManageCostEditText.text.toString().length) {

            0 -> {
                binding.apply {
                    activityRegisterManageCostWonText.setTextColor(Color.GRAY)
                    activityRegisterNoManageCostTextView.setTextColor(Color.GRAY)
                    activityRegisterManageCostWonText.alpha = 0.6F
                    activityRegisterNoManageCostTextView.alpha = 0.6F
                    activityRegisterManageCostButtonDefault.visibility = View.VISIBLE
                    activityRegisterManageCostButtonEvent.visibility = View.INVISIBLE
                    activityRegisterManageCostButtonClicked.visibility = View.INVISIBLE
                }
            }

            in 1..10 -> {
                binding.apply {
                    activityRegisterManageCostWonText.setTextColor(Color.BLACK)
                    activityRegisterNoManageCostTextView.setTextColor(Color.BLACK)
                    activityRegisterManageCostWonText.alpha = 0.9F
                    activityRegisterNoManageCostTextView.alpha = 0.9F
                    if (isSuggested) {
                        activityRegisterManageCostButtonDefault.visibility = View.INVISIBLE
                        activityRegisterManageCostButtonEvent.visibility = View.INVISIBLE
                        activityRegisterManageCostButtonClicked.visibility = View.VISIBLE
                        manageCost = "Y"
                    } else {
                        activityRegisterManageCostButtonDefault.visibility = View.INVISIBLE
                        activityRegisterManageCostButtonEvent.visibility = View.VISIBLE
                        activityRegisterManageCostButtonClicked.visibility = View.INVISIBLE
                        manageCost = "N"
                    }
                }
            }
        }
    }
}