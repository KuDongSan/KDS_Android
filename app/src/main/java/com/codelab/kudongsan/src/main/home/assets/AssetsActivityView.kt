package com.codelab.kudongsan.src.main.home.assets

import com.codelab.kudongsan.src.main.home.assets.models.GetAssetsResponse
import com.codelab.kudongsan.src.main.home.filtering.models.GetFilteredAssetsResponse

interface AssetsActivityView {

    fun onGetAssetsSuccess(response: GetAssetsResponse)

    fun onGetAssetsFailure(message: String)

    fun onGetFilteringSuccess(response: GetFilteredAssetsResponse)

    fun onGetFilteringFailure(message: String)


}