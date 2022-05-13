package com.codelab.kudongsan.src.main.interests

import android.os.Bundle
import android.view.View
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseFragment
import com.codelab.kudongsan.databinding.FragmentInterestsBinding

class InterestsFragment: BaseFragment<FragmentInterestsBinding>(FragmentInterestsBinding::bind, R.layout.fragment_interests),
    InterestsFragmentView {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}