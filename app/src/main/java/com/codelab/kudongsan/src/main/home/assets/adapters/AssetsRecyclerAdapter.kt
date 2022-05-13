package com.codelab.kudongsan.src.main.home.assets.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelab.kudongsan.databinding.AssetsRecyclerItemBinding
import com.codelab.kudongsan.src.main.home.assets.models.AssetsListData

class AssetsRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<AssetsRecyclerAdapter.Holder>() {

    interface OnItemClickListener {
        fun onItemClick(v: View, data: AssetsListData, pos: Int)
    }

    var listData = mutableListOf<AssetsListData>()

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = AssetsRecyclerItemBinding.inflate(
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


    inner class Holder(val binding: AssetsRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(data: AssetsListData) {

            with(binding) {
//                Glide.with(itemView).load(data.img).into(binding.fragmentHomeItemImage)
//                fragmentHomeItemTitle.text = data.title
//                fragmentHomeItemAddress.text = "${data.address} · "
//                if (data.pullUp) {
//                    fragmentHomeItemTime.text = "끌올 ${data.time}"
//                } else if (!data.pullUp) {
//                    fragmentHomeItemTime.text = data.time
//                }
//
//                if (data.price == "무료나눔") {
//                    fragmentHomeItemPrice.text = "나눔 \uD83E\uDDE1"
//                    fragmentHomeItemPricePure.text = "나눔 \uD83E\uDDE1"
//                    fragmentHomeItemPriceKor.visibility = View.GONE
//                    fragmentHomeItemPriceKorPure.visibility = View.GONE
//                } else if (data.price != "무료나눔") {
//                    fragmentHomeItemPrice.text = "${data.price}"
//                    fragmentHomeItemPricePure.text = "${data.price}"
//                    fragmentHomeItemPriceKor.visibility = View.VISIBLE
//                }
//
//
//                if (data.commentNum == 0) {
//                    fragmentHomeItemCommentImage.visibility = View.INVISIBLE
//                    fragmentHomeItemCommentText.visibility = View.INVISIBLE
//                } else if (data.commentNum != 0) {
//                    fragmentHomeItemCommentImage.visibility = View.VISIBLE
//                    fragmentHomeItemCommentText.visibility = View.VISIBLE
//                    fragmentHomeItemCommentText.text = "${data.commentNum}"
//                }
//
//                if (data.likeNum == 0) {
//                    fragmentHomeItemLikeImage.visibility = View.INVISIBLE
//                    fragmentHomeItemLikeText.visibility = View.INVISIBLE
//                } else if (data.likeNum != 0) {
//                    fragmentHomeItemLikeImage.visibility = View.VISIBLE
//                    fragmentHomeItemLikeText.visibility = View.VISIBLE
//                    fragmentHomeItemLikeText.text = "${data.likeNum}"
//                }
//
//                if (data.status == "예약중") {
//                    fragmentHomeButtonStatus.visibility = View.VISIBLE
//                    fragmentHomeButtonStatus.backgroundTintList =
//                        ContextCompat.getColorStateList(context, R.color.green_button)
//                    fragmentHomeButtonStatus.text = "예약중"
//
//                    fragmentHomeItemPrice.visibility = View.VISIBLE
//                    fragmentHomeItemPriceKor.visibility = View.VISIBLE
//                    fragmentHomeItemPricePure.visibility = View.INVISIBLE
//                    fragmentHomeItemPriceKorPure.visibility = View.INVISIBLE
//
//                } else if (data.status == "거래완료") {
//                    fragmentHomeButtonStatus.visibility = View.VISIBLE
//                    fragmentHomeButtonStatus.backgroundTintList =
//                        ContextCompat.getColorStateList(context, R.color.gray_button)
//                    fragmentHomeButtonStatus.text = "거래완료"
//
//                    fragmentHomeItemPrice.visibility = View.VISIBLE
//                    fragmentHomeItemPriceKor.visibility = View.VISIBLE
//                    fragmentHomeItemPricePure.visibility = View.INVISIBLE
//                    fragmentHomeItemPriceKorPure.visibility = View.INVISIBLE
//                } else if (data.status == "나눔완료") {
//                    fragmentHomeButtonStatus.visibility = View.VISIBLE
//                    fragmentHomeButtonStatus.backgroundTintList =
//                        ContextCompat.getColorStateList(context, R.color.gray_button)
//                    fragmentHomeButtonStatus.text = "나눔완료"
//
//                } else if (data.status != "예약중" && data.status != "거래완료" && data.status != "나눔완료") {
//                    fragmentHomeButtonStatus.visibility = View.GONE
//                    if (data.price != "무료나눔") {
//                        fragmentHomeItemPrice.visibility = View.INVISIBLE
//                        fragmentHomeItemPriceKor.visibility = View.INVISIBLE
//                        fragmentHomeItemPricePure.visibility = View.VISIBLE
//                        fragmentHomeItemPriceKorPure.visibility = View.VISIBLE
//                    }
//                    else {
//                        fragmentHomeItemPrice.visibility = View.VISIBLE
//                        fragmentHomeItemPriceKor.visibility = View.INVISIBLE
//                        fragmentHomeItemPricePure.visibility = View.INVISIBLE
//                        fragmentHomeItemPriceKorPure.visibility = View.INVISIBLE
//                    }
//                }

            }

            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, data, pos)
                }
            }

        }

    }


}