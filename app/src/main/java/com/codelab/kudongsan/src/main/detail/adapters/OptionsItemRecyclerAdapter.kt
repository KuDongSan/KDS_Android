package com.codelab.kudongsan.src.main.detail.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codelab.kudongsan.databinding.ActivityDetailOptionsItemRecyclerItemBinding
import com.codelab.kudongsan.src.main.detail.models.ManageItem
import com.codelab.kudongsan.src.main.detail.models.OptionsItem

class OptionsItemRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<OptionsItemRecyclerAdapter.ItemHolder>() {

    var listData = mutableListOf<OptionsItem>()

    interface OnItemClickListener{
        fun onItemClick(pos : Int)
    }

    private var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding = ActivityDetailOptionsItemRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val data = listData[position]
        holder.setData(data)
    }

    inner class ItemHolder(val binding: ActivityDetailOptionsItemRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(data: OptionsItem) {

            binding.apply {
                Glide.with(itemView).load(data.image).into(binding.activityDetailOptionsGirdItemImageView)
                activityDetailOptionsGridItemTextView.text = data.name
            }

        }
    }
}