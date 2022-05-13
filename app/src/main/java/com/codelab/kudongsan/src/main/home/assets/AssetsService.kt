package com.codelab.kudongsan.src.main.home.assets

import com.codelab.kudongsan.config.ApplicationClass
import com.codelab.kudongsan.src.main.home.assets.models.GetAssetsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssetsService(val view: AssetsActivityView) {

    fun tryGetAssets(address: String?){

        val getAssetsRetrofitInterface = ApplicationClass.sRetrofit.create(AssetsRetrofitInterface::class.java)
        getAssetsRetrofitInterface.getAssets(address).enqueue(object :
            Callback<GetAssetsResponse> {
            override fun onResponse(call: Call<GetAssetsResponse>, response: Response<GetAssetsResponse>) {
                view.onGetAssetsSuccess(response.body() as GetAssetsResponse)
            }

            override fun onFailure(call: Call<GetAssetsResponse>, t: Throwable) {
                view.onGetAssetsFailure(t.message ?: "통신 오류")
            }
        })
    }
}