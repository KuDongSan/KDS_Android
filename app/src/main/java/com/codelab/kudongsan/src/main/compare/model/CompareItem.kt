package com.codelab.kudongsan.src.main.compare.model

import com.google.gson.annotations.SerializedName

data class CompareItem(
    @SerializedName("imageThumbnail") val imageThumbnail: String,
    @SerializedName("itemId") val itemId: Int,
    @SerializedName("address") val address: String,
    @SerializedName("area") val area: Double,
    @SerializedName("manageCost") val manageCost: Double,
    @SerializedName("roomType") val roomType: RoomType,
    @SerializedName("salesType") val salesType: String,
    @SerializedName("serviceType") val serviceType: String,
    @SerializedName("deposit") val deposit: Int,
    @SerializedName("monthlyRentPrice") val monthlyRentPrice: Int?,
    @SerializedName("subway") val subway: Subway
)