package com.codelab.kudongsan.src.main.home.assets

import android.os.Bundle
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityAssetsBinding
import com.codelab.kudongsan.src.main.home.assets.models.GetAssetsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AssetsActivity : BaseActivity<ActivityAssetsBinding>(ActivityAssetsBinding::inflate), AssetsActivityView {

    val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
    }

    private fun getData() {
        scope.launch {

        }
    }

    override fun onGetAssetsSuccess(response: GetAssetsResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetAssetsFailure(message: String) {
        showCustomToast("오류 : $message")
    }
}