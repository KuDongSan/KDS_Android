package com.codelab.kudongsan.src.main.home.assets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.codelab.kudongsan.databinding.ActivityAssetsDetailBinding

class AssetsDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityAssetsDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssetsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemid = intent.getIntExtra("itemId",-1)
        //Log.d("checkitemid", itemid.toString())
        binding.textView.text = "itemId: "+itemid


    }
}