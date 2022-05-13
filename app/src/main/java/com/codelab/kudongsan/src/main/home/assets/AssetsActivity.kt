package com.codelab.kudongsan.src.main.home.assets

import android.os.Bundle
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityAssetsBinding

class AssetsActivity : BaseActivity<ActivityAssetsBinding>(ActivityAssetsBinding::inflate), AssetsActivityView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
    }
}