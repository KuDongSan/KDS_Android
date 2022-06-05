package com.codelab.kudongsan.src.main.compare

import com.codelab.kudongsan.src.main.compare.model.GetCompareResponse

interface CompareActivityView {

    fun onGetCompareSuccess(response: GetCompareResponse)

    fun onGetCompareFailure(message: String)

}