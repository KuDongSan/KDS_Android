package com.codelab.kudongsan.src.main.home.assets.filtering

import com.codelab.kudongsan.src.main.home.assets.models.GetAssetsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FilteringRetrofitInterface {

    @GET("/api/property/filter")
    fun getFilteredAssets(@QueryMap filteringOptions: Map<String, String>): Call<GetAssetsResponse>

}