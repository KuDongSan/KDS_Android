package com.codelab.kudongsan.src.main.compare

import android.graphics.Typeface
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityCompareBinding
import com.codelab.kudongsan.databinding.ActivityMainBinding
import com.skydoves.progressview.progressView

class CompareActivity: BaseActivity<ActivityCompareBinding>(ActivityCompareBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activityCompareBackButton.setOnClickListener {
            onBackPressed()
        }

        val myProgressView = progressView(this) {
            setSize(300, 35)
            setProgress(70f)
            setMin(10f)
            setMax(100f)
            setRadius(12f)
            setDuration(1200L)
            setAutoAnimate(true)
            setLabelColorInner(ContextCompat.getColor(this@CompareActivity, R.color.white))
            setLabelColorOuter(ContextCompat.getColor(this@CompareActivity, R.color.black))
            setLabelText("archive 70%")
            setLabelSize(13f)
            setLabelSpace(10f)
            setLabelTypeface(Typeface.BOLD)
        }

        myProgressView.progressAnimate()
        myProgressView.setOnProgressChangeListener { myProgressView.labelText = "achieve ${it.toInt()}%" }
        myProgressView.setOnProgressClickListener { Toast.makeText(baseContext, "clicked", Toast.LENGTH_SHORT).show() }


    }

}