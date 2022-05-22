package com.codelab.kudongsan.src.main.detail.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codelab.kudongsan.R

class ImageSliderAdapter(private val context: Context, private val sliderImage: MutableList<String>) : RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.activity_detail_viewpager, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindSliderImage(sliderImage[position])
    }

    override fun getItemCount(): Int {
        return sliderImage.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mImageView: ImageView = itemView.findViewById(R.id.imageSlider)
        fun bindSliderImage(imageURL: String?) {
            Glide.with(context).load(imageURL).into(mImageView)
        }
    }

}