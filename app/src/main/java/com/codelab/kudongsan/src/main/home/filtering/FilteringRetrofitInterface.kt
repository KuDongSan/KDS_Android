package com.codelab.kudongsan.src.main.home.filtering

import com.codelab.kudongsan.src.main.home.filtering.models.GetFilteredAssetsResponse
import com.codelab.kudongsan.src.main.home.filtering.models.ResultGetFilteredAssets
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FilteringRetrofitInterface {

    @GET("/api/property/filter")
    fun getFilteredAssets(@QueryMap filteringOptions: Map<String, String>): Call<GetFilteredAssetsResponse>

}