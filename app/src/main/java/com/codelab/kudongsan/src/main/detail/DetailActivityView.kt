package com.codelab.kudongsan.src.main.detail

import com.codelab.kudongsan.src.main.home.assets.models.GetAssetsResponse

interface DetailActivityView {

    //fun onGetDetailSuccess(response: GetAssetsResponse)

    fun onGetDetailFailure(message: String)

}