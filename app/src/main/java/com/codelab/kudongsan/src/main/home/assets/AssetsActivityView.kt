package com.codelab.kudongsan.src.main.home.assets

import com.codelab.kudongsan.src.main.home.assets.models.GetAssetsResponse

interface AssetsActivityView {

    fun onGetAssetsSuccess(response: GetAssetsResponse)

    fun onGetAssetsFailure(message: String)

}