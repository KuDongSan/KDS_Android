package com.codelab.kudongsan.src.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityAssetsBinding.inflate
import com.codelab.kudongsan.databinding.ActivityMainBinding
import com.codelab.kudongsan.src.main.home.HomeFragment
import com.codelab.kudongsan.src.main.interests.InterestsFragment
import com.codelab.kudongsan.src.main.map.MapFragment
import com.codelab.kudongsan.src.main.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private var doubleBackToExit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()

        binding.mainBtmNav.setOnItemSelectedListener{ item ->
                when (item.itemId) {
                    R.id.menu_main_btm_nav_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, HomeFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }
                    R.id.menu_main_btm_nav_map -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, MapFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }
                    R.id.menu_main_btm_nav_interests -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, InterestsFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }
                    R.id.menu_main_btm_nav_settings -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, SettingsFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }
                }
                false
            }
    }

    override fun onBackPressed() {
        if (doubleBackToExit) {
            finishAffinity()
        } else {
            Toast.makeText(this, "종료하시려면 뒤로가기를 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
            doubleBackToExit = true
            runDelayed(1500L) {
                doubleBackToExit = false
            }
        }
    }

    private fun runDelayed(millis: Long, function: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(function, millis)
    }
}