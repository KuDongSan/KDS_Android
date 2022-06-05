package com.codelab.kudongsan.src.main.home

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseFragment
import com.codelab.kudongsan.databinding.FragmentHomeBinding
import com.codelab.kudongsan.src.main.MainActivity
import com.codelab.kudongsan.src.main.home.adapters.AssetsRecyclerViewAdapter
import com.codelab.kudongsan.src.main.home.adapters.HomeFragmentViewpagerAdapter
import com.codelab.kudongsan.src.main.home.assets.AssetsActivity
import com.codelab.kudongsan.src.main.home.models.AssetsRecyclerViewData
import com.codelab.kudongsan.src.main.register.RegisterActivity

class HomeFragment : Fragment(), HomeFragmentView{

    private var binding: FragmentHomeBinding? = null

    private var numBanner = 9
    private var currentPosition = 9
    private var myHandler = MyHandler()
    private val intervalTime = 4000.toLong() // 몇초 간격으로 페이지를 넘길것인지 (4000 = 4.0초)


//    private val MIN_SCALE = 0.85f // 뷰가 몇퍼센트로 줄어들 것인지
//    private val MIN_ALPHA = 0.5f // 어두워지는 정도를 나타낸 듯 하다.

    private inner class MyHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            if (msg.what == 0) {
                binding?.fragmentHomeBannerViewpager?.setCurrentItemWithDuration(
                    ++currentPosition,+500
                ) // 다음 페이지로 이동
                autoScrollStart(intervalTime) // 스크롤을 계속 이어서 한다.
            }
        }
    }

    private fun getBannerList(): ArrayList<AssetsRecyclerViewData> {
        return arrayListOf(
            AssetsRecyclerViewData(R.drawable.img_gwangjin, "광진구", "#건대입구역", "#성수동 펍"),
            AssetsRecyclerViewData(R.drawable.img_songpa, "송파구", "#석촌호수", "#방이동 술집"),
            AssetsRecyclerViewData(R.drawable.img_jongro, "종로구", "#경복궁", "#인사동 놀거리"),
            AssetsRecyclerViewData(R.drawable.img_mapo, "마포구", "#홍대입구역", "#망원동 카페"),
            AssetsRecyclerViewData(R.drawable.img_gangnam, "강남구", "#강남역", "#압구정로데오"),
            AssetsRecyclerViewData(R.drawable.img_dongjak, "동작구", "#동작 노을카페", "#노량진 컵밥거리"),
            AssetsRecyclerViewData(R.drawable.img_seocho, "서초구", "#반포 한강공원", "#방배동 카페거리"),
            AssetsRecyclerViewData(R.drawable.img_yongsan, "용산구", "#이태원 루프탑", "#용산 아이파크몰 놀거리"),
            AssetsRecyclerViewData(R.drawable.img_yeongdeungpo, "영등포구", "#타임스퀘어 맛집", "#여의도 한강공원")
        )
    }

    private fun autoScrollStart(intervalTime: Long) {
        myHandler.removeMessages(0) // 이거 안하면 핸들러가 1개, 2개, 3개 ... n개 만큼 계속 늘어남
        myHandler.sendEmptyMessageDelayed(0, intervalTime) // intervalTime 만큼 반복해서 핸들러를 실행하게 함
    }

    private fun autoScrollStop() {
        myHandler.removeMessages(0) // 핸들러를 중지시킴
    }

    fun ViewPager2.setCurrentItemWithDuration(
        item: Int,
        duration: Long,
        interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
        pagePxWidth: Int = width // Default value taken from getWidth() from ViewPager2 view
    ) {
        val pxToDrag: Int = pagePxWidth * (item - currentItem)
        val animator = ValueAnimator.ofInt(0, pxToDrag)
        var previousValue = 0
        animator.addUpdateListener { valueAnimator ->
            val currentValue = valueAnimator.animatedValue as Int
            val currentPxToDrag = (currentValue - previousValue).toFloat()
            fakeDragBy(-currentPxToDrag)
            previousValue = currentValue
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) { beginFakeDrag() }
            override fun onAnimationEnd(animation: Animator?) { endFakeDrag() }
            override fun onAnimationCancel(animation: Animator?) { /* Ignored */ }
            override fun onAnimationRepeat(animation: Animator?) { /* Ignored */ }
        })
        animator.interpolator = interpolator
        animator.duration = duration
        animator.start()
    }

    private fun setBannerViewPager() {

        val bannerAdapter = HomeFragmentViewpagerAdapter(getBannerList())
        bannerAdapter.itemClickListener = object : HomeFragmentViewpagerAdapter.OnItemClickListener {
            override fun OnItemClick(position: Int) {
                val intent = Intent(requireActivity(), AssetsActivity::class.java)
                intent.putExtra("regionId", position%9)
                startActivity(intent)
                (activity as MainActivity).overridePendingTransition(
                    R.anim.activity_fade_in,
                    R.anim.activity_fade_out
                )
            }
        }
        binding?.apply {
            fragmentHomeBannerViewpager.adapter = bannerAdapter
            fragmentHomeBannerViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//            fragmentHomeBannerViewpager.setPageTransformer(ZoomOutPageTransformer()) // 슬라이딩 애니메이션, 없는게 더 이쁜듯...
            fragmentHomeBannerViewpager.setCurrentItem(currentPosition, false) // 현재 위치를 지정

            currentBannerTextView.text ="01"
            totalBannerTextView.text = "%02d".format(numBanner)

            // 현재 몇 번째 배너인지 보여주는 숫자를 변경함
            fragmentHomeBannerViewpager.apply {
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        currentBannerTextView.text =
                            "%02d".format((position % 9) + 1) // position이 0부터 시작해서 + 문자열 포매팅
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                        super.onPageScrollStateChanged(state)
                        when (state) {
                            // 뷰페이저에서 손 떼었을때 / 뷰페이저 멈춰있을 때
                            ViewPager2.SCROLL_STATE_IDLE -> autoScrollStart(intervalTime)

                            // 뷰페이저 움직이는 중
                            ViewPager2.SCROLL_STATE_DRAGGING -> autoScrollStop()
                        }
                    }
                })
            }
            fragmentHomeBannerViewpager.setCurrentItem(++currentPosition, true)
        }
    }

//    /* 공식문서에 있는 코드 긁어온거임 */
//    inner class ZoomOutPageTransformer : ViewPager2.PageTransformer {
//        override fun transformPage(view: View, position: Float) {
//            view.apply {
//                val pageWidth = width
//                val pageHeight = height
//                when {
//                    position < -1 -> { // [-Infinity,-1)
//                        // This page is way off-screen to the left.
//                        alpha = 0f
//                    }
//                    position <= 1 -> { // [-1,1]
//                        // Modify the default slide transition to shrink the page as well
//                        val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
//                        val vertMargin = pageHeight * (1 - scaleFactor) / 2
//                        val horzMargin = pageWidth * (1 - scaleFactor) / 2
//                        translationX = if (position < 0) {
//                            horzMargin - vertMargin / 2
//                        } else {
//                            horzMargin + vertMargin / 2
//                        }
//
//                        // Scale the page down (between MIN_SCALE and 1)
//                        scaleX = scaleFactor
//                        scaleY = scaleFactor
//
//                        // Fade the page relative to its size.
//                        alpha = (MIN_ALPHA +
//                                (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
//                    }
//                    else -> { // (1,+Infinity]
//                        // This page is way off-screen to the right.
//                        alpha = 0f
//                    }
//                }
//            }
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBannerViewPager()
        binding?.fragmentHomeRegisterMyAssetButton?.setOnClickListener{
            startActivity(Intent(requireActivity(), RegisterActivity::class.java))
            (activity as MainActivity).overridePendingTransition(
                R.anim.activity_fade_in,
                R.anim.activity_fade_out
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

//    private fun initRecyclerView() {
//        val assetsData: MutableList<AssetsRecyclerViewData> = loadData()
//        val magazineAdapter = AssetsRecyclerViewAdapter()
//        magazineAdapter.itemClickListener = object : AssetsRecyclerViewAdapter.OnItemClickListener {
//            override fun OnItemClick(position: Int) {
//                val intent = Intent(requireActivity(), AssetsActivity::class.java)
//                intent.putExtra("regionId", position)
//                startActivity(intent)
//                (activity as MainActivity).overridePendingTransition(
//                    R.anim.activity_fade_in,
//                    R.anim.activity_fade_out
//                )
//            }
//        }
//        magazineAdapter.listData = assetsData
//        binding?.apply {
//            fragmentHomeHorizontalAssetsListRecyclerView.adapter = magazineAdapter
//            fragmentHomeHorizontalAssetsListRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//
//        }
//    }

//    private fun loadData(): MutableList<AssetsRecyclerViewData> {
//        val listData: MutableList<AssetsRecyclerViewData> = mutableListOf()
//
//        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "광진구", "#건대입구역", "#성수동 펍"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_songpa, "송파구", "#석촌호수", "#방이동 술집"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_jongro, "종로구", "#경복궁", "#인사동 놀거리"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_mapo, "마포구", "#홍대입구역", "#망원동 카페"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_gangnam, "강남구", "#강남역", "#압구정로데오"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_dongjak, "동작구", "#동작 노을카페", "#노량진 컵밥거리"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_seocho, "서초구", "#반포 한강공원", "#방배동 카페거리"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_yongsan, "용산구", "#이태원 루프탑", "#용산 아이파크몰 놀거리"))
//        listData.add(AssetsRecyclerViewData(R.drawable.img_yeongdeungpo, "영등포구", "#타임스퀘어 맛집", "#여의도 한강공원"))
////        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "강동구", "#건대입구역", "#성수동 맛집"))
////        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "노원구", "#건대입구역", "#성수동 맛집"))
////        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "강서구", "#건대입구역", "#성수동 맛집"))
////        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "성북구", "#건대입구역", "#성수동 맛집"))
////        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "성동구", "#건대입구역", "#성수동 맛집"))
////        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "동대문구", "#건대입구역", "#성수동 맛집"))
////        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "도봉구", "#건대입구역", "#성수동 맛집"))
////        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "서대문구", "#건대입구역", "#성수동 맛집"))
////        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "금천구", "#건대입구역", "#성수동 맛집"))
////        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "관악구", "#건대입구역", "#성수동 맛집"))
////        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "중구", "#건대입구역", "#성수동 맛집"))
////        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "양천구", "#건대입구역", "#성수동 맛집"))
////        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "강북구", "#건대입구역", "#성수동 맛집"))
////        listData.add(AssetsRecyclerViewData(R.drawable.img_gwangjin, "중랑구", "#건대입구역", "#성수동 맛집"))
//
//        return listData
//    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }


}