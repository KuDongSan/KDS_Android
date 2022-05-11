package com.codelab.kumpare.src.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.codelab.kumpare.R
import com.codelab.kumpare.config.BaseActivity
import com.codelab.kumpare.databinding.ActivityLoginBinding
import com.codelab.kumpare.databinding.ActivityMainBinding
import com.codelab.kumpare.src.main.MainActivity

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