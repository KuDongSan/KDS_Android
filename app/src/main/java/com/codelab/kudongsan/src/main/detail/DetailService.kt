package com.codelab.kudongsan.src.main.detail

import com.codelab.kudongsan.config.ApplicationClass
import com.codelab.kudongsan.src.main.detail.models.GetDetailResponse
import com.codelab.kudongsan.src.main.detail.models.PostInterestsRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailService(val view: DetailActivityView) {

    fun tryGetDetail(itemId: Int, email: String){
        val getDetailRetrofitInterface = ApplicationClass.sRetrofit.create(DetailRetrofitInterface::class.java)
        getDetailRetrofitInterface.getDetail(itemId, email).enqueue(object :
            Callback<GetDetailResponse> {
            override fun onResponse(call: Call<GetDetailResponse>, response: Response<GetDetailResponse>) {
                view.onGetDetailSuccess(response.body() as GetDetailResponse)
            }

            override fun onFailure(call: Call<GetDetailResponse>, t: Throwable) {
                view.onGetDetailFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostInterests(postInterestsRequest: PostInterestsRequest) {
        val postInterestsRetrofitInterface = ApplicationClass.sRetrofit.create(DetailRetrofitInterface::class.java)
        postInterestsRetrofitInterface.postInterests(postInterestsRequest).enqueue(object : Callback<Unit> {

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                view.onPostInterestsSuccess(response.code())
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                view.onPostInterestsFailure(t.message ?: "통신 오류")
            }

        })
    }

}