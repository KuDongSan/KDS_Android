package com.codelab.kudongsan.src.main.settings

import android.os.Bundle
import android.view.View
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseFragment
import com.codelab.kudongsan.databinding.FragmentSettingsBinding
import com.codelab.kudongsan.src.main.interests.InterestsFragmentView

class SettingsFragment: BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::bind, R.layout.fragment_settings),
    InterestsFragmentView {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}