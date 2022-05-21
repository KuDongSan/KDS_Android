package com.codelab.kudongsan.src.main.detail

import com.codelab.kudongsan.src.main.detail.models.GetDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailRetrofitInterface {

    @GET("/api/property/{propertyId}")
    fun getDetail(@Path("propertyId") propertyId: String): Call<GetDetailResponse>

}