package com.codelab.kudongsan.src.main.register.models

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("imageThumbnail") val imageThumbnail: String,
    @SerializedName("address") val address: Address,
    @SerializedName("area") val area: Double,
    @SerializedName("manageCost") val manageCost: Double,
    @SerializedName("roomType") val roomType: RoomType,
    @SerializedName("salesType") val salesType: String,
    @SerializedName("serviceType") val serviceType: String,
    @SerializedName("deposit") val deposit: Int,
    @SerializedName("monthlyRentPrice") val monthlyRentPrice: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)