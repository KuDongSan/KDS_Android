package com.codelab.kudongsan.src.login

import com.codelab.kudongsan.src.login.models.PostLoginResponse

interface LoginActivityView {

    fun onPostLoginSuccess(response: PostLoginResponse)

    fun onPostLoginFailure(message: String)

}
