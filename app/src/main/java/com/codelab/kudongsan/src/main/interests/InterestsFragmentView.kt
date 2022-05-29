package com.codelab.kudongsan.src.main.interests

import com.codelab.kudongsan.src.main.interests.models.GetInterestsResponse

interface InterestsFragmentView {

    fun onGetInterestsSuccess(response: GetInterestsResponse)

    fun onGetInterestsFailure(message: String)

}