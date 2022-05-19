package com.codelab.kudongsan.src.main

import android.os.Bundle
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
}