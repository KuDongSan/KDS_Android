package com.codelab.kudongsan.src.main.interests

import com.codelab.kudongsan.config.ApplicationClass
import com.codelab.kudongsan.src.main.interests.models.GetInterestsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InterestsService(val view: InterestsFragmentView) {

    fun tryGetInterests(email: String) {

        val getInterestsRetrofitInterface = ApplicationClass.sRetrofit.create(InterestsRetrofitInterface::class.java)

        getInterestsRetrofitInterface.getInterests(email).enqueue(object : Callback<GetInterestsResponse> {
            override fun onResponse(call: Call<GetInterestsResponse>, response: Response<GetInterestsResponse>) {
                view.onGetInterestsSuccess(response.body() as GetInterestsResponse)
            }

            override fun onFailure(call: Call<GetInterestsResponse>, t: Throwable) {
                view.onGetInterestsFailure(t.message ?: "통신 오류")
            }
        })

    }

}