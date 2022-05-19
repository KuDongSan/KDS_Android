package com.codelab.kudongsan.src.login

import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.ApplicationClass
import com.codelab.kudongsan.config.ApplicationClass.Companion.K_USER_ACCOUNT
import com.codelab.kudongsan.config.ApplicationClass.Companion.K_USER_NAME
import com.codelab.kudongsan.config.ApplicationClass.Companion.K_USER_THUMB
import com.codelab.kudongsan.config.ApplicationClass.Companion.sSharedPreferences
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityLoginBinding
import com.codelab.kudongsan.src.main.MainActivity
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient


class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Glide.with(this).load(R.drawable.home_animation_unscreen)
            .into(binding.activityLoginGif)

        updateKakaoLogin()
        initLayout()

    }

    private fun initLayout(){
        // 아래 두 줄을 실행해야 API 접근이 가능해서 해쉬코드를 제게 보내주시면 감사하겠습니다.
        var keyHash = Utility.getKeyHash(this)
        Log.i("log_gethash",keyHash)

        binding.activityLoginKakaoLayout.setOnClickListener {
            // 카카오톡이 설치되어있는지 확인하여 ture or false return 하는 함수
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
                UserApiClient.instance.loginWithKakaoTalk(this){
                        token, error ->
                    if (error != null) {
                        Log.e(ContentValues.TAG, "로그인 실패", error)
                    }
                    else if (token != null) {
                        Log.i(ContentValues.TAG, "로그인 성공 ${token.accessToken}")
                    }
                    // 로그인 성공시 아래의 함수 호출
                    updateKakaoLogin()
                }
            }else{
                UserApiClient.instance.loginWithKakaoAccount(this){
                        token, error ->
                    if (error != null) {
                        Log.e(ContentValues.TAG, "로그인 실패", error)
                    }
                    else if (token != null) {
                        Log.i(ContentValues.TAG, "로그인 성공 ${token.accessToken}")
                    }
                    // 로그인 성공시 아래의 함수 호출
                    updateKakaoLogin()
                }
            }
        }
        //로그아웃버튼 리스너 => 설정화면에 추후 부착
//        binding.logout.setOnClickListener {
//            UserApiClient.instance.logout {
//                updateKakaoLoginUi()
//                return@logout
//            }
//        }
    }


    fun updateKakaoLogin() {
        UserApiClient.instance.me { user, error ->
            // 로그인이 되어있는 경우
            // 유저 데이터 등록 및 서버에 로그인 정보 전송예정
            if(user !=null){

                Log.d("myLog","invoke: id=" + user.id)
                Log.d("myLog","invoke: kakao_email=" + user.kakaoAccount?.email)
                Log.d("myLog","invoke: nickname=" + user.kakaoAccount?.profile?.nickname)
                Log.d("myLog","invoke: hasSignedUp=" + user.hasSignedUp)
                Log.d("myLog","invoke: properties=" + user.properties?.keys)

//                user.kakaoAccount?.profile?.nickname
//                user.kakaoAccount?.profile?.thumbnailImageUrl

                editor = sSharedPreferences.edit()
                editor.putString(K_USER_NAME,user?.kakaoAccount?.profile?.nickname)
                editor.putString(K_USER_ACCOUNT,user?.kakaoAccount?.email )
                //user?.kakaoAccount?.profile?.profileImageUrl
                editor.putString(K_USER_THUMB,user?.kakaoAccount?.profile?.thumbnailImageUrl)
                editor.apply()
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)

            }else{

            }
        }
    }

}