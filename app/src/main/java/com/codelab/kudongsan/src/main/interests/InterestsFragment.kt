package com.codelab.kudongsan.src.main.interests

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.ApplicationClass.Companion.K_USER_ACCOUNT
import com.codelab.kudongsan.config.ApplicationClass.Companion.sSharedPreferences
import com.codelab.kudongsan.config.BaseFragment
import com.codelab.kudongsan.databinding.FragmentInterestsBinding
import com.codelab.kudongsan.src.main.detail.DetailActivity
import com.codelab.kudongsan.src.main.interests.adapters.InterestsRecyclerAdapter
import com.codelab.kudongsan.src.main.interests.models.GetInterestsResponse
import com.codelab.kudongsan.src.main.interests.models.InterestsListData

class InterestsFragment :
    BaseFragment<FragmentInterestsBinding>(FragmentInterestsBinding::bind, R.layout.fragment_interests),
    InterestsFragmentView {

    val data: MutableList<InterestsListData> = mutableListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email = sSharedPreferences.getString(K_USER_ACCOUNT, null)
        if (email != null) {
            InterestsService(view = this).tryGetInterests(email = email)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData(response: GetInterestsResponse) {
        val data: MutableList<InterestsListData> = data
        var adapter = InterestsRecyclerAdapter(requireActivity())
        adapter.listData = data
        binding.fragmentInterestsRecyclerView.adapter = adapter
        binding.fragmentInterestsRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        adapter.notifyDataSetChanged()

        adapter.listener = object : InterestsRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: InterestsListData, pos: Int) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra("itemId", adapter.listData[pos].itemId)
                startActivity(intent)
            }
        }

        response.forEach { item ->
            data.add(
                InterestsListData(
                    item.itemId,
                    item.salesType,
                    item.serviceType,
                    item.image_thumbnail,
                    item.deposit,
                    item.monthlyRentPrice,
                    item.manageCost,
                    item.area,
                    item.address,
                    item.subways[0].name,
                    item.subways[0].description,
                    item.subways[0].distance
                )
            )
        }
//        adapter.notifyDataSetChanged()
//        binding.activityAssetsSwipeRefreshLayout.isRefreshing = false
    }

    override fun onGetInterestsSuccess(response: GetInterestsResponse) {
        if (response.size != 0) {
            binding.fragmentInterestsConstraintLayout.visibility = View.GONE
            getData(response)
        }
    }

    override fun onGetInterestsFailure(message: String) {
        showCustomToast("오류 : $message")
        Log.d("okhttp", "오류 : $message")
        binding.fragmentInterestsSwipeRefreshLayout.isRefreshing = false
    }


}