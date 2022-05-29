package com.codelab.kudongsan.src.main.interests

import com.codelab.kudongsan.src.main.detail.models.GetDetailResponse
import com.codelab.kudongsan.src.main.interests.models.GetInterestsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface InterestsRetrofitInterface {

    @GET("/api/favorites")
    fun getInterests(@Query("email") email: String): Call<GetInterestsResponse>

}