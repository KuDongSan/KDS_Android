package com.codelab.kudongsan.src.main.home.assets.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColorStateList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codelab.kudongsan.R
import com.codelab.kudongsan.databinding.ActivityAssetsRecyclerItemBinding
import com.codelab.kudongsan.src.main.home.assets.models.AssetsListData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class AssetsRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<AssetsRecyclerAdapter.Holder>() {

    interface OnItemClickListener {
        fun onItemClick(v: View, data: AssetsListData, pos: Int)
    }

    var listData = mutableListOf<AssetsListData>()

    var listener: OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ActivityAssetsRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = listData[position]
        holder.setData(data)
    }


    inner class Holder(val binding: ActivityAssetsRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(data: AssetsListData) {

            Glide.with(itemView).load("${data.image_thumbnail}?w=0&h=640").placeholder(R.drawable.kudongsan_logo).into(binding.activityAssetsImageView)
            binding.activityAssetsAddressTextView.text = data.address
            binding.activityAssetsPriceTextView.text = if(data.deposit >= 10000) {
                if(data.monthlyRentPrice!=null) {
                    "${data.deposit/10000}억/${data.monthlyRentPrice}"
                }
                else {
                    "${data.deposit/10000}억"
                }
            } else {
                val dec = DecimalFormat("#,###")
                if(data.monthlyRentPrice!=null) {
                    "${dec.format(data.deposit)}/${data.monthlyRentPrice}"
                }
                else {
                    "${dec.format(data.deposit)}"
                }
            }
            with(binding.activityAssetsSalesTypeButton) {
                if (data.salesType == "YEARLY_RENT") {
                    text = "전세"
                    setBackgroundColor(Color.parseColor("#E09100"))
                }
                else if (data.salesType == "MONTHLY_RENT") {
                    text = "월세"
                    backgroundTintList = ColorStateList.valueOf(Color.parseColor("#72CC82"))
                }
                else {
                    text = "매매"
                    setBackgroundColor(Color.parseColor("#4D515A"))
                }
            }
            binding.activityAssetsServiceTypeButton.text = if (data.serviceType == "ONEROOM") {
                "원룸"
            } else if (data.serviceType == "VILLA") {
                "빌라"
            } else if (data.serviceType == "OFFICETEL") {
                "오피스텔"
            } else {
                "아파트"
            }
            binding.activityAssetsManageCostTextView.text = if(data.manageCost == 0.0) {
                "관리비 없음"
            } else {
                "관리비 ${data.manageCost.toInt()}만원"
            }
            binding.activityAssetsAreaTextView.text = "${data.area}㎡"
            binding.activityAssetsAddressTextView.text = data.address


            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, data, pos)
                }
            }

        }

    }


}