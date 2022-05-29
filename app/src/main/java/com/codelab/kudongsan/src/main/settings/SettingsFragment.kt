package com.codelab.kudongsan.src.main.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.ApplicationClass.Companion.K_USER_ACCOUNT
import com.codelab.kudongsan.config.ApplicationClass.Companion.K_USER_NAME
import com.codelab.kudongsan.config.ApplicationClass.Companion.K_USER_THUMB
import com.codelab.kudongsan.config.ApplicationClass.Companion.sSharedPreferences
import com.codelab.kudongsan.config.BaseFragment
import com.codelab.kudongsan.databinding.FragmentSettingsBinding
import com.kakao.sdk.user.UserApiClient

class SettingsFragment :
    BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::bind, R.layout.fragment_settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settingsName = binding.fragmentMyCarrotProfileName
        val settingsEmail = binding.fragmentMyCarrotProfileMemberNumber
        val settingsThumbnail = binding.fragmentSettingsProfileImage

        checkAgreedInfo()

        settingsName.text = sSharedPreferences.getString(K_USER_NAME, "none")
        Glide.with(this)
            .load(sSharedPreferences.getString(K_USER_THUMB, null))
            .circleCrop()
            .into(settingsThumbnail)
        // 이메일 넘어온게 없는경우 권한동의를 추가로 요청해야함
        if (null == sSharedPreferences.getString(K_USER_ACCOUNT, null)) {
            settingsEmail.text = "Please agree for email"

        } else {
            settingsEmail.text = sSharedPreferences.getString(K_USER_ACCOUNT, null)
        }

//        추후 로그아웃 버튼 생성시 버튼 아이디만 바꿔서 그대로사용
//        binding.fragmentSettingsReportTextView.setOnClickListener {
//            UserApiClient.instance.logout { error ->
//                if (error != null) {
//                    Log.e("logoutTag", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
//                }
//                else {
//                    Log.i("logoutTag", "로그아웃 성공. SDK에서 토큰 삭제됨")
//                }
//            }
//        }


    }

    private fun checkAgreedInfo() {
        //현재 가지고 있는 동의 항목 log 창에 확인용
        // ScopeInfo(id=2239600170,
        // scopes=[Scope(id=profile_nickname, displayName=닉네임, type=PRIVACY, using=true, delegated=null, agreed=true, revocable=false),
        // Scope(id=profile_image, displayName=프로필 사진, type=PRIVACY, using=true, delegated=null, agreed=true, revocable=false),
        // Scope(id=friends, displayName=카카오 서비스 내 친구목록(프로필사진, 닉네임, 즐겨찾기 포함), type=PRIVACY, using=true, delegated=null, agreed=false, revocable=null),
        // Scope(id=account_email, displayName=카카오계정(이메일),type=PRIVACY, using=true, delegated=null, agreed=false, revocable=null)])
        UserApiClient.instance.scopes { scopeInfo, error ->
            if (error != null) {
                Log.e("agreedInfoCheck", "동의 정보 확인 실패", error)
            } else if (scopeInfo != null) {
                Log.i("agreedInfoCheck", "동의 정보 확인 성공\n 현재 가지고 있는 동의 항목 $scopeInfo")
            }
        }
    }


}