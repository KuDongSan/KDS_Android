package com.codelab.kudongsan.src.login

import com.codelab.kudongsan.config.ApplicationClass
import com.codelab.kudongsan.src.login.models.PostLoginRequest
import com.codelab.kudongsan.src.login.models.PostLoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val view: LoginActivityView) {

    fun tryPostLogin(postLoginRequest: PostLoginRequest) {
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.postLogin(postLoginRequest).enqueue(object :
            Callback<PostLoginResponse> {
            override fun onResponse(call: Call<PostLoginResponse>, response: Response<PostLoginResponse>) {
                println(response.body())
                view.onPostLoginSuccess(response.body() as PostLoginResponse)
            }

            override fun onFailure(call: Call<PostLoginResponse>, t: Throwable) {
                view.onPostLoginFailure(t.message ?: "통신 오류")
            }
        })
    }

}
