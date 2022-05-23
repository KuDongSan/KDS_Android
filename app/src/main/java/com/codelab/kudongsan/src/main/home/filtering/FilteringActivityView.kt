package com.codelab.kudongsan.src.main.home.filtering

import com.codelab.kudongsan.src.main.detail.models.GetDetailResponse
import com.codelab.kudongsan.src.main.home.filtering.models.GetFilteredAssetsResponse

interface FilteringActivityView {

    fun onGetFilteringSuccess(response: GetFilteredAssetsResponse)

    fun onGetFilteringFailure(message: String)

}