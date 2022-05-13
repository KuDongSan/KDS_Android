package com.codelab.kudongsan.src.main.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelab.kudongsan.databinding.AssetsRecyclerItemBinding
import com.codelab.kudongsan.src.main.home.models.AssetsRecyclerViewData

class AssetsRecyclerViewAdapter: RecyclerView.Adapter<AssetsRecyclerViewAdapter.Holder>() {
    interface OnItemClickListener {
        fun OnItemClick(position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    var listData = mutableListOf<AssetsRecyclerViewData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = AssetsRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = listData.get(position)
        holder.setData(data)
    }

    inner class Holder(val binding: AssetsRecyclerItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.assetsItemCardView.setOnClickListener {
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

