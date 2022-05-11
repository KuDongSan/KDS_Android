package com.codelab.kumpare.src.main.myPage

import android.os.Bundle
import android.view.View
import com.codelab.kumpare.R
import com.codelab.kumpare.config.BaseFragment
import com.codelab.kumpare.databinding.FragmentMyPageBinding

class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page) {
    private var mCount = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonChangeCounterText.setOnClickListener {
            binding.textViewCounter.text =
                resources.getString(R.string.my_page_tv_counter, ++mCount)
        }
    }
}