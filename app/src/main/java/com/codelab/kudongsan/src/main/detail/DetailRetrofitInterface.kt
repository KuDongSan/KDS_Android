package com.codelab.kudongsan.src.main.detail

import com.codelab.kudongsan.src.main.detail.models.GetDetailResponse
import com.codelab.kudongsan.src.main.detail.models.PostInterestsRequest
import retrofit2.Call
import retrofit2.http.*

interface DetailRetrofitInterface {

    @GET("/api/property/{propertyId}")
    fun getDetail(@Path("propertyId") propertyId: Int, @Query("email") email: String): Call<GetDetailResponse>

    @POST("/api/favorites")
    fun postInterests(@Body postInterestsRequest: PostInterestsRequest): Call<Unit>

}