package com.codelab.kudongsan.src.main.interests.adapters

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
import com.codelab.kudongsan.databinding.FragmentInterestsBinding
import com.codelab.kudongsan.databinding.FragmentInterestsRecyclerItemBinding
import com.codelab.kudongsan.src.main.home.assets.models.AssetsListData
import com.codelab.kudongsan.src.main.interests.models.InterestsListData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class InterestsRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<InterestsRecyclerAdapter.Holder>() {

    interface OnItemClickListener {
        fun onItemClick(v: View, data: InterestsListData, pos: Int)
    }

    var listData = mutableListOf<InterestsListData>()

    var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = FragmentInterestsRecyclerItemBinding.inflate(
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


    inner class Holder(val binding: FragmentInterestsRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(data: InterestsListData) {

            Glide.with(itemView).load("${data.image_thumbnail}?w=0&h=640").into(binding.fragmentInterestsImageView)
            binding.fragmentInterestsAddressTextView.text = data.address
            binding.fragmentInterestsPriceTextView.text = if(data.deposit >= 10000) {
                if(data.monthlyRentPrice!=null) {
                    "${data.deposit/10000}???/${data.monthlyRentPrice}"
                }
                else {
                    "${data.deposit/10000}???"
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
            with(binding.fragmentInterestsSalesTypeButton) {
                if (data.salesType == "YEARLY_RENT") {
                    text = "??????"
                    setBackgroundColor(Color.parseColor("#E09100"))
                }
                else if (data.salesType == "MONTHLY_RENT") {
                    text = "??????"
                    backgroundTintList = ColorStateList.valueOf(Color.parseColor("#72CC82"))
                }
                else {
                    text = "??????"
                    setBackgroundColor(Color.parseColor("#4D515A"))
                }
            }
            binding.fragmentInterestsServiceTypeButton.text = if (data.serviceType == "ONEROOM") {
                "??????"
            } else if (data.serviceType == "VILLA") {
                "??????"
            } else if (data.serviceType == "OFFICETEL") {
                "????????????"
            } else {
                "?????????"
            }
            binding.fragmentInterestsManageCostTextView.text = if(data.manageCost == 0.0) {
                "????????? ??????"
            } else {
                "????????? ${data.manageCost.toInt()}??????"
            }
            binding.fragmentInterestsAreaTextView.text = "${data.area}???"
            binding.fragmentInterestsAddressTextView.text = data.address


            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, data, pos)
                }
            }

        }

    }


}