package com.codelab.kudongsan.src.main.register

interface RegisterActivityView {

    fun onPostRegisterSuccess(responseCode: Int)

    fun onPostRegisterFailure(message: String)

}