package com.codelab.kudongsan.src.main.compare

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityCompareBinding
import com.codelab.kudongsan.src.main.compare.model.GetCompareResponse
import java.text.DecimalFormat
import kotlin.math.roundToInt

class CompareActivity : BaseActivity<ActivityCompareBinding>(ActivityCompareBinding::inflate),
    CompareActivityView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activityCompareBackButton.setOnClickListener {
            onBackPressed()
        }

        val itemId = intent.getIntExtra("itemId", -1)

        if (itemId != -1) {
            // 내 매물은 임시로 1
            CompareService(this).tryGetCompare(p1 = 1, p2 = itemId)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onGetCompareSuccess(response: GetCompareResponse) {
        binding.apply {
            // 왼쪽 매물
            // 썸네일
            Glide.with(this@CompareActivity).load("${response[0].imageThumbnail}?w=0&h=640")
                .placeholder(
                    R.drawable.kudongsan_logo
                ).into(activityCompareLeftAssetImageView)
            // 매물번호
            activityCompareLeftItemIdTextView.text = "매물번호 ${response[0].itemId}"
            activityComparePriceLeftAssetNameTextView.text = "매물번호 ${response[0].itemId}"
            activityCompareAreaLeftAssetNameTextView.text = "매물번호 ${response[0].itemId}"
            activityCompareManageCostLeftAssetNameTextView.text = "매물번호 ${response[0].itemId}"
            activityCompareRoomTypeLeftAssetNameTextView.text = "매물번호 ${response[0].itemId}"
            activityCompareTransportationLeftAssetNameTextView.text = "매물번호 ${response[0].itemId}"
            // 주소
            activityCompareLeftAssetAddressTextView.text = response[0].address
            // salesType
            activityComparePriceLeftAssetTitleTextView.text =
                if (response[0].salesType == "YEARLY_RENT") {
                    "전세"
                } else if (response[0].salesType == "MONTHLY_RENT") {
                    "월세"
                } else {
                    "매매"
                }
            // 보증금, 월세
            activityComparePriceLeftAssetValueTextView.text = if (response[0].deposit >= 10000) {
                if (response[0].monthlyRentPrice != null) {
                    "${response[0].deposit / 10000}억/${response[0].monthlyRentPrice}"
                } else {
                    "${response[0].deposit / 10000}억"
                }
            } else {
                val dec = DecimalFormat("#,###")
                if (response[0].monthlyRentPrice != null) {
                    "${dec.format(response[0].deposit)}/${response[0].monthlyRentPrice}"
                } else {
                    dec.format(response[0].deposit)
                }
            }
            // 면적
            activityCompareAreaLeftAssetValueTextView.text =
                "${((response[0].area) * 0.3025).roundToInt()}"
            // 관리비
            activityCompareManageCostLeftAssetValueTextView.text =
                if (response[0].manageCost == 0.0) {
                    activityCompareManageCostLeftAssetValueKorTextView.visibility = View.INVISIBLE
                    "관리비 없음"
                } else {
                    activityCompareManageCostLeftAssetValueKorTextView.visibility = View.VISIBLE
                    "${response[0].manageCost.toInt()}"
                }
            // 구조
            activityCompareRoomTypeLeftAssetValueTextView.text =
                getRoomTypeName(response[0].roomType.roomTypeCode)
            // 가까운 지하철역
            activityCompareTransportationLeftAssetTitleTextView.text = response[0].subway.name
            // 가까운 지하철역까지의 거리
            activityCompareTransportationLeftAssetValueTextView.text =
                "${response[0].subway.distance}m"

            // 오른쪽 매물
            Glide.with(this@CompareActivity).load("${response[1].imageThumbnail}?w=0&h=640")
                .placeholder(
                    R.drawable.kudongsan_logo
                ).into(activityCompareRightAssetImageView)
            activityCompareRightItemIdTextView.text = "매물번호 ${response[1].itemId}"
            activityComparePriceRightAssetNameTextView.text = "매물번호 ${response[1].itemId}"
            activityCompareAreaRightAssetNameTextView.text = "매물번호 ${response[1].itemId}"
            activityCompareManageCostRightAssetNameTextView.text = "매물번호 ${response[1].itemId}"
            activityCompareRoomTypeRightAssetNameTextView.text = "매물번호 ${response[1].itemId}"
            activityCompareTransportationRightAssetNameTextView.text = "매물번호 ${response[1].itemId}"
            activityCompareRightAssetAddressTextView.text = response[1].address
            activityComparePriceRightAssetTitleTextView.text =
                if (response[1].salesType == "YEARLY_RENT") {
                    "전세"
                } else if (response[1].salesType == "MONTHLY_RENT") {
                    "월세"
                } else {
                    "매매"
                }
            // 보증금, 월세
            activityComparePriceRightAssetValueTextView.text = if (response[1].deposit >= 10000) {
                if (response[1].monthlyRentPrice != null) {
                    "${response[1].deposit / 10000}억/${response[1].monthlyRentPrice}"
                } else {
                    "${response[1].deposit / 10000}억"
                }
            } else {
                val dec = DecimalFormat("#,###")
                if (response[1].monthlyRentPrice != null) {
                    "${dec.format(response[1].deposit)}/${response[1].monthlyRentPrice}"
                } else {
                    dec.format(response[1].deposit)
                }
            }
            activityCompareAreaRightAssetValueTextView.text =
                "${((response[1].area) * 0.3025).roundToInt()}"
            activityCompareManageCostRightAssetValueTextView.text =
                if (response[1].manageCost == 0.0) {
                    activityCompareManageCostRightAssetValueKorTextView.visibility = View.INVISIBLE
                    "관리비 없음"
                } else {
                    activityCompareManageCostRightAssetValueKorTextView.visibility = View.VISIBLE
                    "${response[1].manageCost.toInt()}"
                }
            activityCompareRoomTypeRightAssetValueTextView.text =
                getRoomTypeName(response[1].roomType.roomTypeCode)
            activityCompareTransportationRightAssetTitleTextView.text = response[1].subway.name
            activityCompareTransportationRightAssetValueTextView.text =
                "${response[1].subway.distance}m"

        }
    }

    override fun onGetCompareFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    private fun getRoomTypeName(roomTypeCode: String): String {
        return when (roomTypeCode) {
            "01" -> "오픈형 원룸"
            "02" -> "분리형 원룸"
            "03" -> "복층형 원룸"
            "04" -> "투룸"
            "05" -> "쓰리룸+"
            "06" -> "포룸+"
            else -> "정보 없음"
        }
    }

}