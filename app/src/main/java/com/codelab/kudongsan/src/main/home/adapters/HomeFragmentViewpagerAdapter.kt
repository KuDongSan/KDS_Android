package com.codelab.kudongsan.src.main.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.codelab.kudongsan.R
import com.codelab.kudongsan.databinding.AssetsRecyclerItemBinding
import com.codelab.kudongsan.src.main.home.models.AssetsRecyclerViewData

class HomeFragmentViewpagerAdapter(bannerList: ArrayList<AssetsRecyclerViewData>): RecyclerView.Adapter<HomeFragmentViewpagerAdapter.Holder>() {
    interface OnItemClickListener {
        fun OnItemClick(position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    var item = bannerList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = AssetsRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = item[position%9]
        holder.setData(data)
    }

    inner class Holder(val binding: AssetsRecyclerItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.assetsImageView.setOnClickListener {
                itemClickListener?.OnItemClick(adapterPosition)
            }
        }
        fun setData(data: AssetsRecyclerViewData) {
            binding.assetsImageView.setImageResource(data.img)
            binding.assetsTitleTextView.text = data.title
            binding.assetsFirstTextView.text = data.hashtag_first
            binding.assetsSecondTextView.text = data.hashtag_second
        }
    }

}