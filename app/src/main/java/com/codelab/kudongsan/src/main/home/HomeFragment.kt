package com.codelab.kudongsan.src.main.home

import android.os.Bundle
import android.view.View
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseFragment
import com.codelab.kudongsan.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home),
    HomeFragmentView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}