package com.codelab.kudongsan.src.main.compare

import com.codelab.kudongsan.src.main.compare.model.GetCompareResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CompareRetrofitInterface {

    @GET("/api/property/compare")
    fun getCompare(@Query("p1") p1: Int, @Query("p2") p2: Int): Call<GetCompareResponse>

}