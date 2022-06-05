package com.codelab.kudongsan.src.main.compare

import com.codelab.kudongsan.config.ApplicationClass
import com.codelab.kudongsan.src.main.compare.model.GetCompareResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompareService(val view: CompareActivityView) {

    fun tryGetCompare(p1: Int, p2: Int){

        val getCompareRetrofitInterface = ApplicationClass.sRetrofit.create(CompareRetrofitInterface::class.java)
        getCompareRetrofitInterface.getCompare(p1, p2).enqueue(object :
            Callback<GetCompareResponse> {
            override fun onResponse(call: Call<GetCompareResponse>, response: Response<GetCompareResponse>) {
                view.onGetCompareSuccess(response.body() as GetCompareResponse)
            }

            override fun onFailure(call: Call<GetCompareResponse>, t: Throwable) {
                view.onGetCompareFailure(t.message ?: "통신 오류")
            }
        })
    }
}