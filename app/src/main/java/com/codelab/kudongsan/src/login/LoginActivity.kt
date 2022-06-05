package com.codelab.kudongsan.src.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.ApplicationClass.Companion.K_USER_ACCOUNT
import com.codelab.kudongsan.config.ApplicationClass.Companion.K_USER_NAME
import com.codelab.kudongsan.config.ApplicationClass.Companion.K_USER_THUMB
import com.codelab.kudongsan.config.ApplicationClass.Companion.sSharedPreferences
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityLoginBinding
import com.codelab.kudongsan.src.login.models.PostLoginRequest
import com.codelab.kudongsan.src.login.models.PostLoginResponse
import com.codelab.kudongsan.src.main.MainActivity
import com.codelab.kudongsan.src.main.home.assets.filtering.FilteringActivity
import com.kakao.sdk.common.util.Utility
import com.codelab.kudongsan.util.KudongsanLoadingDialog
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import java.sql.Types.NULL


class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginActivityView {

    private val TAG = LoginActivity::class.java.simpleName
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Glide.with(this).load(R.drawable.home_animation_unscreen)
            .into(binding.activityLoginGif)

//        val keyHash = Utility.getKeyHash(this)
//        Log.d("Hash", keyHash)

        binding.activityLoginKakaoLayout.setOnClickListener {
            kakaoLogin()
        }

    }

    private fun kakaoLogin() {
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
                showCustomToast("카카오계정으로 로그인 실패")
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                showCustomToast("카카오계정으로 로그인 성공")
                loginKuDongSan()
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)
                    showCustomToast("카카오톡으로 로그인 실패")

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    showCustomToast("카카오톡으로 로그인 성공")
                    loginKuDongSan()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    private fun loginKuDongSan() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
                showCustomToast("사용자 정보 요청 실패")
            } else if (user != null) {
                Log.i(TAG, "사용자 정보 요청 성공")
                showCustomToast("사용자 정보 요청 성공")
                LoginService(view = this).tryPostLogin(
                    postLoginRequest = PostLoginRequest(
                        user.kakaoAccount?.profile?.nickname!!,
                        user.kakaoAccount?.email!!,
                        user.kakaoAccount?.profile?.thumbnailImageUrl!!
                    )
                )
                showLoadingDialog(this)
                editor = sSharedPreferences.edit()
                editor.putString(K_USER_NAME,user.kakaoAccount?.profile?.nickname)
                editor.putString(K_USER_ACCOUNT,user.kakaoAccount?.email )
                //user?.kakaoAccount?.profile?.profileImageUrl
                editor.putString(K_USER_THUMB,user.kakaoAccount?.profile?.thumbnailImageUrl)
                editor.apply()
            }
        }
    }

    override fun onPostLoginSuccess(response: PostLoginResponse) {
        dismissLoadingDialog()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
    }

    override fun onPostLoginFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
