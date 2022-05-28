package com.codelab.kudongsan.src.main.detail

import com.codelab.kudongsan.src.main.detail.models.GetDetailResponse

interface DetailActivityView {

    fun onGetDetailSuccess(response: GetDetailResponse)

    fun onGetDetailFailure(message: String)

    fun onPostInterestsSuccess(responseCode: Int)

    fun onPostInterestsFailure(message: String)

}