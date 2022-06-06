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
import java.util.*
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

            // 요약 설명
            activityCompareGeneralOverviewContentTextView.text =
                "고객님이 등록한 매물은 면적 ${((response[0].area) * 0.3025).roundToInt()}평의 ${
                    getSalesTypeName(response[0].salesType)
                }매물이고, 고객님이 현재 보고있는 매물은 면적 ${((response[1].area) * 0.3025).roundToInt()}평의 ${
                    getSalesTypeName(
                        response[1].salesType
                    )
                }매물입니다."

            // 유사성 분석 결과
            activityCompareSimilarityPercentageProgressView.progress =
                calculateSimilarityValue().toFloat()
            activityCompareSimilarityPercentageTextView.text =
                "${activityCompareSimilarityPercentageProgressView.progress.toInt()}%"
            activityCompareSimilarityPercentageProgressView.animate()

            // 보증금 및 월세 요약
            activityComparePriceDescriptionTextView.text =
                if (discriminateSalesType(response[0].salesType, response[1].salesType)) {
                    when (response[0].salesType) {
                        "YEARLY_RENT" -> {
                            if (response[0].deposit > response[1].deposit) {
                                "등록한 매물의 전세금이 보고 있는 매물의 전세금보다 ${(response[0].deposit) - (response[1].deposit)}만원 높아요."
                            } else if (response[0].deposit < response[1].deposit) {
                                "등록한 매물의 전세금이 보고 있는 매물의 전세금보다 ${(response[1].deposit) - (response[0].deposit)}만원 낮아요."
                            } else {
                                "등록한 매물의 전세금과 보고 있는 매물의 전세금이 동일해요!"
                            }
                        }
                        "MONTHLY_RENT" -> {
                            if (response[0].deposit > response[1].deposit) {
                                if (response[0].monthlyRentPrice!! > response[1].monthlyRentPrice!!) {
                                    "등록한 매물의 보증금이 보고 있는 매물의 보증금보다 ${(response[0].deposit) - (response[1].deposit)}만원 높고,\n" +
                                            "등록한 매물의 월세는 보고 있는 매물의 월세보다 ${(response[0].monthlyRentPrice!!) - (response[1].monthlyRentPrice!!)}만원 높아요."
                                } else if (response[0].monthlyRentPrice!! < response[1].monthlyRentPrice!!) {
                                    "등록한 매물의 보증금이 보고 있는 매물의 보증금보다 ${(response[0].deposit) - (response[1].deposit)}만원 높고,\n" +
                                            "등록한 매물의 월세는 보고 있는 매물의 월세보다 ${(response[1].monthlyRentPrice!!) - (response[0].monthlyRentPrice!!)}만원 낮아요."
                                } else {
                                    "등록한 매물의 보증금이 보고 있는 매물의 보증금보다 ${(response[0].deposit) - (response[1].deposit)}만원 높고,\n" +
                                            "월세는 동일해요!"
                                }
                            } else if (response[0].deposit < response[1].deposit) {
                                if (response[0].monthlyRentPrice!! > response[1].monthlyRentPrice!!) {
                                    "등록한 매물의 보증금이 보고 있는 매물의 보증금보다 ${(response[1].deposit) - (response[0].deposit)}만원 낮고,\n" +
                                            "등록한 매물의 월세는 보고 있는 매물의 월세보다 ${(response[0].monthlyRentPrice!!) - (response[1].monthlyRentPrice!!)}만원 높아요."
                                } else if (response[0].monthlyRentPrice!! < response[1].monthlyRentPrice!!) {
                                    "등록한 매물의 보증금이 보고 있는 매물의 보증금보다 ${(response[1].deposit) - (response[0].deposit)}만원 낮고,\n" +
                                            "등록한 매물의 월세는 보고 있는 매물의 월세보다 ${(response[1].monthlyRentPrice!!) - (response[0].monthlyRentPrice!!)}만원 낮아요."
                                } else {
                                    "등록한 매물의 보증금이 보고 있는 매물의 보증금보다 ${(response[1].deposit) - (response[0].deposit)}만원 낮고,\n" +
                                            "월세는 동일해요!"
                                }
                            } else {
                                if (response[0].monthlyRentPrice!! > response[1].monthlyRentPrice!!) {
                                    "등록한 매물의 보증금과 보고 있는 매물의 보증금이 동일하고,\n" +
                                            "등록한 매물의 월세는 보고 있는 매물의 월세보다 ${(response[0].monthlyRentPrice!!) - (response[1].monthlyRentPrice!!)}만원 높아요."
                                } else if (response[0].monthlyRentPrice!! < response[1].monthlyRentPrice!!) {
                                    "등록한 매물의 보증금과 보고 있는 매물의 보증금이 동일하고,\n" +
                                            "등록한 매물의 월세는 보고 있는 매물의 월세보다 ${(response[1].monthlyRentPrice!!) - (response[0].monthlyRentPrice!!)}만원 낮아요."
                                } else {
                                    "등록한 매물과 보고 있는 매물의 보증금과 월세 모두 동일해요!"
                                }
                            }
                        }
                        else -> {
                            "매매 데이터 알 수 없음"
                        }
                    }
                } else {
                    "등록 매물과 현재 보고 있는 매물의 거래 유형이 달라요."
                }

            // 면적 요약
            activityCompareAreaDescriptionTextView.text = if (response[0].area > response[1].area) {
                "등록한 매물의 면적이 보고 있는 매물보다 ${(((response[0].area) * 0.3025).roundToInt())-(((response[1].area) * 0.3025).roundToInt())}평 더 넓어요."
            } else if (response[0].area < response[1].area) {
                "등록한 매물의 면적이 보고 있는 매물보다 ${(((response[1].area) * 0.3025).roundToInt())-(((response[0].area) * 0.3025).roundToInt())}평 더 좁아요."
            } else {
                "면적이 동일해요!"
            }
            
            // 관리비 요약
            activityCompareManageCostDescriptionTextView.text = if(response[0].manageCost == response[1].manageCost) {
                "관리비가 동일해요!"
            }
            else {
                if (response[0].manageCost > response[1].manageCost) {
                    "등록한 매물의 관리비가 보고 있는 매물의 관리비보다 ${((response[0].manageCost) - (response[1].manageCost)).toInt()}만원 더 높아요."
                }
                else{
                    "등록한 매물의 관리비가 보고 있는 매물의 관리비보다 ${((response[1].manageCost) - (response[0].manageCost)).toInt()}만원 더 낮아요."
                }
            }
            
            // 구조 요약
            activityCompareRoomTypeDescriptionTextView.text = if(response[0].roomType.roomTypeCode==response[1].roomType.roomTypeCode) {
                "구조가 동일해요!"
            }
            else {
                "등록한 매물과 보고 있는 매물의 구조가 달라요!"
            }

            // 지하철 요약
            activityCompareTransportationDescriptionTextView.text = if(response[0].subway.distance>response[1].subway.distance) {
                "등록한 매물이 보고 있는 매물보다 지하철역에서 ${(response[0].subway.distance) - (response[1].subway.distance)}m 더 가까워요."
            }
            else if(response[0].subway.distance<response[1].subway.distance) {
                "등록한 매물이 보고 있는 매물보다 지하철역에서 ${(response[1].subway.distance) - (response[0].subway.distance)}m 더 멀어요."
            }
            else {
                "가까운 지하철역까지의 거리가 동일해요!"
            }

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

    private fun getSalesTypeName(salesType: String): String {
        return when (salesType) {
            "YEARLY_RENT" -> "전세"
            "MONTHLY_RENT" -> "월세"
            else -> "매매"
        }
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

    private fun discriminateSalesType(leftSalesType: String, rightSalesType: String): Boolean {
        return leftSalesType == rightSalesType
    }

    // TODO 유사도 알고리즘 만들어야함.
    // 임시
    private fun calculateSimilarityValue(): Int {
        return Random().nextInt(101)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
    }

}