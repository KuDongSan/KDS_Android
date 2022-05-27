package com.codelab.kudongsan.src.main.home.assets.filtering

import com.codelab.kudongsan.config.ApplicationClass
import com.codelab.kudongsan.src.main.home.assets.AssetsActivity
import com.codelab.kudongsan.src.main.home.assets.models.GetAssetsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FilteringService(val view: AssetsActivity) {

    fun tryGetFilteredAssets(filteredOptions: Map<String, String>){

        val getFilteringRetrofitInterface = ApplicationClass.sRetrofit.create(FilteringRetrofitInterface::class.java)

        getFilteringRetrofitInterface.getFilteredAssets(filteredOptions).enqueue(object :
            Callback<GetAssetsResponse> {

            override fun onResponse(call: Call<GetAssetsResponse>, response: Response<GetAssetsResponse>) {

                if (response.body() != null) {
                    view.onGetFilteringSuccess(response.body() as GetAssetsResponse)
                }
            }

            override fun onFailure(call: Call<GetAssetsResponse>, t: Throwable) {
                println(t.message)
            }
        })
    }

}