package com.codelab.kudongsan.src.main.detail.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatDrawableManager.preload
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codelab.kudongsan.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageSliderAdapter(
    private val context: Context,
    private val sliderImage: MutableList<String>
) : RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_detail_viewpager, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            holder.bindSliderImage(sliderImage[position])
        }
    }

    override fun getItemCount(): Int {
        return sliderImage.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mImageView: ImageView = itemView.findViewById(R.id.imageSlider)
        fun bindSliderImage(imageURL: String?) {
            // skeleton 사용해서 이미지 로딩 추후 바꿀것 (시간 남으면)
            // https://leveloper.tistory.com/214
            Glide.with(context).load(imageURL).placeholder(R.drawable.kudongsan_logo).into(mImageView)
        }
    }

}