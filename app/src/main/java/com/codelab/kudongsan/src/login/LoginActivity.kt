package com.codelab.kudongsan.src.login

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityLoginBinding
import com.codelab.kudongsan.src.main.MainActivity


class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Glide.with(this).load(R.drawable.home_animation_unscreen)
            .into(binding.activityLoginGif)
        // 임시
        binding.activityLoginKakaoLayout.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}