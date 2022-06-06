package com.codelab.kudongsan.src.main.register

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityRegisterBinding
import com.codelab.kudongsan.util.CameraBottomSheetDialog
import com.codelab.kudongsan.util.CameraBottomSheetDialogTemp
import com.google.android.material.bottomsheet.BottomSheetBehavior


class RegisterActivity : BaseActivity<ActivityRegisterBinding>(ActivityRegisterBinding::inflate) {

    var salesType: String = ""
    var serviceType: String = ""
    var roomType: String = ""
    var isSuggested: Boolean = false
    var manageCost: String = "N"

    var imageURL: Uri? = null

    enum class SalesType {
        YEARLY_RENT, MONTHLY_RENT, DEALING
    }

    private val cameraBottomSheetDialog = CameraBottomSheetDialog()

    enum class ServiceType {
        ONEROOM, VILLA, OFFICETEL, APARTMENT
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.apply {
            activityRegisterCameraLayout.setOnClickListener {
                cameraBottomSheetDialog.show(supportFragmentManager, cameraBottomSheetDialog.tag)
            }
            activityRegisterBackButton.setOnClickListener {
                onBackPressed()
            }
            activityRegisterSalesTypeButton.setOnClickListener {
                showSalesTypeDialog()
            }
            activityRegisterServiceTypeButton.setOnClickListener {
                showServiceTypeDialog()
            }
            activityRegisterRoomTypeButton.setOnClickListener {
                showRoomTypeDialog()
            }
            activityRegisterManageCostEditText.addTextChangedListener {
                // 글자 입력시 색상 변화
                detectKeyboard()
            }
            activityRegisterFinishText.setOnClickListener {
                showCustomToast("내 부동산이 등록되었습니다.")
                // 임시
                onBackPressed()
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
                salesType = when (which) {
                    0 -> SalesType.YEARLY_RENT.toString()
                    1 -> SalesType.MONTHLY_RENT.toString()
                    2 -> SalesType.DEALING.toString()
                    else -> "error"
                }
            }
            .show()
    }

    private fun showServiceTypeDialog() {
        val items = arrayOf(
            "원룸",
            "빌라",
            "오피스텔",
            "아파트"
        )
        AlertDialog.Builder(this)
            .setItems(items) { _, which ->
                binding.activityRegisterServiceTypeTextView.text = items[which]
                serviceType = when (which) {
                    0 -> ServiceType.ONEROOM.toString()
                    1 -> ServiceType.VILLA.toString()
                    2 -> ServiceType.OFFICETEL.toString()
                    3 -> ServiceType.APARTMENT.toString()
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

    fun openGallery() {
        showCustomToast("openGallery 호출")
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.type = "image/*"
        activityResultLauncher.launch(intent)
    }

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            Log.d("img", "${it.data?.data}")
            Glide.with(this).load(it.data?.data).into(binding.activityRegisterCameraImageReal)
        }
    }

}