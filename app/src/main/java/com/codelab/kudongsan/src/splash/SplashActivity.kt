package com.codelab.kudongsan.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import com.codelab.kudongsan.config.ApplicationClass
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivitySplashBinding
import com.codelab.kudongsan.src.login.LoginActivity
import com.codelab.kudongsan.src.login.LoginService
import com.codelab.kudongsan.src.login.models.PostLoginRequest
import com.codelab.kudongsan.src.main.MainActivity
import com.kakao.sdk.user.UserApiClient

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            loginKuDongSan()
        }, 1500)

    }

    private fun loginKuDongSan() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else if (user != null) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}