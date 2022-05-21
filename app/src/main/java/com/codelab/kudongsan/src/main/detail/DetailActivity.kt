package com.codelab.kudongsan.src.main.detail

import android.os.Bundle
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityDetailBinding
import com.codelab.kudongsan.src.main.detail.models.GetDetailResponse

class DetailActivity : BaseActivity<ActivityDetailBinding>(ActivityDetailBinding::inflate),
    DetailActivityView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onGetDetailSuccess(response: GetDetailResponse) {

    }

    override fun onGetDetailFailure(message: String) {
        showCustomToast("오류 : $message")
    }

}