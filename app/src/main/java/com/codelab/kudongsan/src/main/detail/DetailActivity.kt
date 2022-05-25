package com.codelab.kudongsan.src.main.detail

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.DiscretePathEffect
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityDetailBinding
import com.codelab.kudongsan.src.main.detail.adapters.ImageSliderAdapter
import com.codelab.kudongsan.src.main.detail.adapters.OptionsItemRecyclerAdapter
import com.codelab.kudongsan.src.main.detail.models.GetDetailResponse
import com.codelab.kudongsan.src.main.detail.models.ManageItem
import com.codelab.kudongsan.src.main.detail.models.OptionsItem
import com.codelab.kudongsan.src.main.detail.models.Subway
import com.codelab.kudongsan.src.main.home.assets.AssetsService
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.coroutines.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class DetailActivity : BaseActivity<ActivityDetailBinding>(ActivityDetailBinding::inflate),
    DetailActivityView, OnMapReadyCallback {

    // 개선해야할 사항 (2022.05.23)
    // adapter class 여러개 관리하기 힘듦 
    // adapter class 하나로 줄이고 viewholder 여러개 만드는 과정 필요함
    // getItemViewType 오버라이드
    // https://blog.naver.com/ksjmgrkks

    var data: MutableList<OptionsItem> = mutableListOf()
    var manageData: MutableList<OptionsItem> = mutableListOf()

    var latitude: Double = 0.0
    var longitude: Double = 0.0

    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private val mapView: MapView by lazy {
        binding.activityDetailLocationMapView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val itemId = intent.getIntExtra("itemId", -1)
        DetailService(view = this).tryGetDetail(itemId = itemId)
        mapView.getMapAsync(this)
        Log.d("okhttp", "Oncreate getMapAsync")
    }


    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onGetDetailSuccess(response: GetDetailResponse) {
        Log.d("okhttp", "onGetDetailSuccess")

        binding.apply {
            activityDetailTitleTextView.text = response.address.replace("서울시 ", "") // 서울시 없애고 구 동 정보만 표시
            activityDetailAreaContentTextView.text =
                "${((response.area.exclusiveArea) * 0.3025).roundToInt()}평"
            activityDetailSalesTypeTextView.text = if (response.salesType == "YEARLY_RENT") {
                "전세"
            } else if (response.salesType == "MONTHLY_RENT") {
                "월세"
            } else {
                "매매"
            }
            activityDetailDepositTextView.text = if(response.deposit >= 10000) {
                if(response.monthlyRentPrice!=null) {
                    "${response.deposit/10000}억/${response.monthlyRentPrice}"
                }
                else {
                    "${response.deposit/10000}억"
                }
            } else {
                val dec = DecimalFormat("#,###")
                if(response.monthlyRentPrice!=null) {
                    "${dec.format(response.deposit)}/${response.monthlyRentPrice}"
                }
                else {
                    "${dec.format(response.deposit)}"
                }
            }
            activityDetailUpdatedAtTextView.text = "${calcDate(response.updateAt)}일전"
            activityDetailManageCostContentTextView.text = if(response.manageCost == 0.0) {
                "없음"
            } else {
                "${response.manageCost.toInt()}만원"
            }
            activityDetailRoomTypeContentTextView.text = when(response.roomType.roomTypeCode) {
                "01" -> "오픈형 원룸"
                "02" -> "분리형 원룸"
                "03" -> "복층형 원룸"
                "04" -> "투룸"
                "05" -> "쓰리룸+"
                "06" -> "포룸+"
                else -> "정보 없음"
            }
            activityDetailTitleContentTextView.text = response.title

            // images
            val images = mutableListOf<String>()
            response.images.forEach {
                val url = "${it}?w=0&h=640"
                images.add(url)
            }
            val dotsIndicator = findViewById<WormDotsIndicator>(R.id.activity_detail_dots_indicator)
            val viewPager = findViewById<ViewPager2>(R.id.activity_detail_viewpager)
            val adapter = ImageSliderAdapter(this@DetailActivity, images)
            viewPager.adapter = adapter
            dotsIndicator.setViewPager2(viewPager)
            binding.activityDetailViewpager.adapter = adapter
            //

            activityDetailParkingContentTextView.text = response.parking
            activityDetailElevatorContentTextView.text = if(response.elevator == "true") {
                "있음"
            } else {
                "없음"
            }
            activityDetailMoveInDateContentTextView.text = response.moveinDate
            activityDetailManageCostTotalContentTextView.text = if(response.manageCostNotInc==null) {
                if (response.manageCost == 0.0) {
                    "관리비 없음"
                }
                else {
                    "${response.manageCost.toInt()}만원"
                }
            } else {
                if (response.manageCost == 0.0) {
                    "관리비 없음\n관리비 외 사용 따라 부과\n${manageCostListToKor(response.manageCostNotInc)}"
                }
                else {
                    "${response.manageCost.toInt()}만원\n관리비 외 사용 따라 부과\n${manageCostListToKor(response.manageCostNotInc)}"
                }
            }
            activityDetailRoomTypeTotalContentTextView.text = when(response.roomType.roomTypeCode) {
                "01" -> "오픈형 원룸 (욕실 ${response.bathroomCount}개)"
                "02" -> "분리형 원룸 (욕실 ${response.bathroomCount}개)"
                "03" -> "복층형 원룸 (욕실 ${response.bathroomCount}개)"
                "04" -> "투룸 (욕실 ${response.bathroomCount}개)"
                "05" -> "쓰리룸+ (욕실 ${response.bathroomCount}개)"
                "06" -> "포룸+ (욕실 ${response.bathroomCount}개)"
                else -> "정보 없음"
            }
            activityDetailAreaTotalContentTextView.text = "${((response.area.exclusiveArea) * 0.3025).roundToInt()}평"
            activityDetailRoomDirectionContentTextView.text = roomDirectionToKor(response.roomDirection)
            activityDetailFloorContentTextView.text = if(response.floor.floorString.length != 1) {
                "${response.floor.floorString}/${response.floor.floorAll}"
            } else {
                "${response.floor.floorString}층/${response.floor.floorAll}"
            }
            activityDetailResidenceTypeContentTextView.text = response.residenceType
            activityDetailJibunAddressContentTextView.text = response.jibunAddress ?: "문의"

            if (response.options == null) {
                data.clear()
                activityDetailOptionsNoIncTextView.visibility = View.VISIBLE
                activityDetailOptionsGridRecyclerView.visibility = View.GONE
            }
            else {
                data.clear()
                activityDetailOptionsNoIncTextView.visibility = View.GONE
                activityDetailOptionsGridRecyclerView.visibility = View.VISIBLE
                optionsListToItem(response.options)
                val data: MutableList<OptionsItem> = data
                var adapterSeller = OptionsItemRecyclerAdapter(this@DetailActivity)

                adapterSeller.listData = data
                binding.activityDetailOptionsGridRecyclerView.adapter = adapterSeller
                binding.activityDetailOptionsGridRecyclerView.layoutManager = GridLayoutManager(this@DetailActivity, 4)
                val spaceDecoration = VerticalSpaceItemDecoration(20)
                binding.activityDetailOptionsGridRecyclerView.addItemDecoration(spaceDecoration)

                adapterSeller.notifyDataSetChanged()
            }

            if (response.manageCostInc == null) {
                manageData.clear()
                activityDetailManageCostNoIncTextView.visibility = View.VISIBLE
                activityDetailManageCostGridRecyclerView.visibility = View.GONE
                activityDetailMangeCostIncTitleSubTextView.text = if(response.manageCost == 0.0) {
                    "관리비 없음"
                } else {
                    "관리비 : ${response.manageCost.toInt()}만원 (전기, 가스, 수도, 인터넷, 티비 별도)"
                }
            }
            else {
                manageData.clear()
                activityDetailManageCostNoIncTextView.visibility = View.GONE
                activityDetailManageCostGridRecyclerView.visibility = View.VISIBLE
                manageCostListToItem(response.manageCostInc)
                val data: MutableList<OptionsItem> = manageData
                var adapterSeller = OptionsItemRecyclerAdapter(this@DetailActivity)

                adapterSeller.listData = data
                binding.activityDetailManageCostGridRecyclerView.adapter = adapterSeller
                binding.activityDetailManageCostGridRecyclerView.layoutManager = GridLayoutManager(this@DetailActivity, 4)
                val spaceDecoration = VerticalSpaceItemDecoration(20)
                binding.activityDetailManageCostGridRecyclerView.addItemDecoration(spaceDecoration)

                adapterSeller.notifyDataSetChanged()

                activityDetailMangeCostIncTitleSubTextView.text = "관리비 : ${response.manageCost.toInt()}만원 (${manageCostListToKor(response.manageCostNotInc)} 별도)"
            }

            activityDetailNearSubwaysContentTextView.text = getSubwaysNameList(response.subways)
            activityDetailDescriptionContentTextView.text = response.description.substring(0 until (response.description.length - 26)) // regex 사용해서 바꾸는게 더 안전할 듯

            latitude = response.location.latitude
            longitude = response.location.longtitude

        }
    }

    override fun onGetDetailFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    @SuppressLint("SimpleDateFormat")
    private fun calcDate(date: String): String {
        val sf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val newDate = sf.parse(date)

        val today = Calendar.getInstance()
        val calcDate = (today.time.time - newDate.time) / (60 * 60 * 24 * 1000)

        return calcDate.toString()
    }

    private fun manageCostListToKor(manageCostList: String): String {
        val arr = manageCostList.split(";")
        //showCustomToast("$arr")
        val newArr = ArrayList<String>()
        arr.forEach {
            when(it) {
                "01" -> newArr.add("전기")
                "02" -> newArr.add("가스")
                "03" -> newArr.add("수도")
                "04" -> newArr.add("인터넷")
                "05" -> newArr.add("티비")
            }
        }
        //showCustomToast("$newArr")
        return newArr.joinToString(", ")
    }


    private fun roomDirectionToKor(roomDirection: String): String {
        when(roomDirection) {
            "E" -> return "동향"
            "W" -> return "서향"
            "S" -> return "남향"
            "N" -> return "북향"
            "NE" -> return "북동향"
            "NW" -> return "북서향"
            "SE" -> return "남동향"
            "SW" -> return "남서향"
            else -> return "알 수 없음"
        }
    }

    private fun optionsListToItem(options: String) {
        val arr = options.split(";")
        arr.forEach {
            when(it) {
                "01" -> data.add(OptionsItem(image = R.drawable.options_01, name = "에어컨"))
                "02" -> data.add(OptionsItem(image = R.drawable.options_02, name = "냉장고"))
                "03" -> data.add(OptionsItem(image = R.drawable.options_03, name = "세탁기"))
                "04" -> data.add(OptionsItem(image = R.drawable.options_04, name = "가스레인지"))
                "05" -> data.add(OptionsItem(image = R.drawable.options_05, name = "인덕션"))
                "06" -> data.add(OptionsItem(image = R.drawable.options_06, name = " 전자레인지"))
                "07" -> data.add(OptionsItem(image = R.drawable.options_07, name = "책상"))
                "08" -> data.add(OptionsItem(image = R.drawable.options_08, name = "책장"))
                "09" -> data.add(OptionsItem(image = R.drawable.options_09, name = " 침대"))
                "10" -> data.add(OptionsItem(image = R.drawable.options_10, name = "옷장"))
                "11" -> data.add(OptionsItem(image = R.drawable.options_11, name = "신발장"))
                "12" -> data.add(OptionsItem(image = R.drawable.options_12, name = "싱크대"))
            }
        }
    }

    private fun manageCostListToItem(items: String) {
        val arr = items.split(";")
        arr.forEach {
            when(it) {
                "01" -> manageData.add(OptionsItem(image = R.drawable.manage_cost_01, name = "전기"))
                "02" -> manageData.add(OptionsItem(image = R.drawable.manage_cost_03, name = "가스")) // 가스 이미지를 못찾아서 일단 이걸로 대체
                "03" -> manageData.add(OptionsItem(image = R.drawable.manage_cost_03, name = "수도"))
                "04" -> manageData.add(OptionsItem(image = R.drawable.manage_cost_04, name = "인터넷"))
                "05" -> manageData.add(OptionsItem(image = R.drawable.manage_cost_05, name = "티비"))
            }
        }
    }

    private fun getSubwaysNameList(subways: ArrayList<Subway>): String {
        val arr = mutableListOf<String>()
        for (subway in subways) {
            arr.add("${subway.name}(${subway.description})")
        }
        return arr.joinToString(", ")
    }

    inner class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = verticalSpaceHeight
        }
    }



    override fun onMapReady(map: NaverMap) {

        // onGetDetailSuccess 호출 후에 onMapReady 콜백 호출되어야 하는데,
        // 외부 라이브러리 오버라이드된 함수여서 suspend 함수로 바꾸면 안될 듯 해서 일단 임시방편으로 핸들러로 처리
        // 추후 코드 수정할 것

        Handler(Looper.getMainLooper()).postDelayed({
            Log.d("okhttp", "onMapReady")
            naverMap = map

            val latitude = latitude
            val longitude = longitude

            Log.d("okhttp", latitude.toString())
            Log.d("okhttp",longitude.toString())

            // 초기 위치값 설정 (강남역 위,경도)
            val cameraUpdate = CameraUpdate.scrollTo(LatLng(latitude, longitude))
            naverMap.moveCamera(cameraUpdate)

            val uiSetting = naverMap.uiSettings
            uiSetting.isLocationButtonEnabled = false // 현위치 버튼 비활성화
            uiSetting.isZoomControlEnabled = false // 줌 버튼 비활성화

            locationSource = FusedLocationSource(this@DetailActivity, LOCATION_PERMISSION_REQUEST_CODE)
            naverMap.locationSource = locationSource

            // 마커
            val marker = Marker()
            marker.position = LatLng(latitude, longitude)
            marker.map = naverMap
            marker.width = 130
            marker.height = 130
            marker.icon = OverlayImage.fromResource(R.drawable.ic_map_pin_area);
        }, 100)

    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

}