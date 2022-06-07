package com.codelab.kudongsan.src.main.register

import com.codelab.kudongsan.src.main.register.models.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterRetrofitInterface {

    // 내 부동산 등록
    @POST("/api/property")
    fun postRegister(@Body registerRequest: RegisterRequest): Call<Unit>

}