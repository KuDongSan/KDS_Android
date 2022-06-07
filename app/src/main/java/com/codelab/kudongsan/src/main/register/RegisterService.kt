package com.codelab.kudongsan.src.main.register

import com.codelab.kudongsan.config.ApplicationClass
import com.codelab.kudongsan.src.main.register.models.RegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterService(val view: RegisterActivityView) {

    fun tryPostRegister(registerRequest: RegisterRequest){
        val tryPostRegisterRetrofitInterface = ApplicationClass.sRetrofit.create(RegisterRetrofitInterface::class.java)
        tryPostRegisterRetrofitInterface.postRegister(registerRequest = registerRequest).enqueue(object :
            Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                view.onPostRegisterSuccess(response.code())
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                view.onPostRegisterFailure(t.message ?: "통신 오류")
            }
        })
    }
}