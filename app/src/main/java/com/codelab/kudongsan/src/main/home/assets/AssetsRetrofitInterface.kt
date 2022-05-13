package com.codelab.kudongsan.src.main.home.assets

import com.codelab.kudongsan.src.main.home.assets.models.GetAssetsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AssetsRetrofitInterface {

    @GET("/api/property")
    fun getAssets(@Query("region") region: String?): Call<GetAssetsResponse>

}