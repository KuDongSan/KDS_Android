package com.codelab.kudongsan.src.main.home.filtering

import com.codelab.kudongsan.config.ApplicationClass
import com.codelab.kudongsan.src.main.home.filtering.models.GetFilteredAssetsResponse
import com.codelab.kudongsan.src.main.home.filtering.models.ResultGetFilteredAssets
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FilteringService(val view: FilteringActivityView) {

    fun tryGetFilteredAssets(filteredOptions: Map<String, String>){

        val getFilteringRetrofitInterface = ApplicationClass.sRetrofit.create(FilteringRetrofitInterface::class.java)

        getFilteringRetrofitInterface.getFilteredAssets(filteredOptions).enqueue(object :
            Callback<GetFilteredAssetsResponse> {
            override fun onResponse(call: Call<GetFilteredAssetsResponse>, response: Response<GetFilteredAssetsResponse>) {

                if (response.body() != null) {
                    view.onGetFilteringSuccess(response.body() as GetFilteredAssetsResponse)
                }
            }

            override fun onFailure(call: Call<GetFilteredAssetsResponse>, t: Throwable) {
                println(t.message)
            }
        })
    }

}