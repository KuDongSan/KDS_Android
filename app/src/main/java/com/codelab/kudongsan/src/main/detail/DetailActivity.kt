package com.codelab.kudongsan.src.main.detail

import android.os.Bundle
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity<ActivityDetailBinding>(ActivityDetailBinding::inflate),
    DetailActivityView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onGetDetailFailure(message: String) {

    }

}