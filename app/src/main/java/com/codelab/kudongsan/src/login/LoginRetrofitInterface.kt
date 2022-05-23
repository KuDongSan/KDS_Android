package com.codelab.kudongsan.src.login

import com.codelab.kudongsan.src.login.models.PostLoginRequest
import com.codelab.kudongsan.src.login.models.PostLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {

    @POST("/api/user/login")
    fun postLogin(@Body postLoginRequest: PostLoginRequest): Call<PostLoginResponse>

}
