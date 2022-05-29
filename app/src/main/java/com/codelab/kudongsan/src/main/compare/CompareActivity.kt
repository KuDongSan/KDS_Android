package com.codelab.kudongsan.src.main.compare

import android.os.Bundle
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityCompareBinding

class CompareActivity: BaseActivity<ActivityCompareBinding>(ActivityCompareBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activityCompareBackButton.setOnClickListener {
            onBackPressed()
        }





    }

}