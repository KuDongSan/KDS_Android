package com.codelab.kudongsan.src.main.home.filtering.models

import com.google.gson.annotations.SerializedName

data class ResultGetFilteredAssets(
    @SerializedName("itemId") val itemId: Int,
    @SerializedName("salesType") val salesType: String,
    @SerializedName("serviceType") val serviceType: String,
    @SerializedName("image_thumbnail") val image_thumbnail: String,
    @SerializedName("deposit") val deposit: Int,
    //@SerializedName("salePrice") val salePrice: Int?,
    @SerializedName("monthlyRentPrice") val monthlyRentPrice: Int?,
    @SerializedName("manageCost") val manageCost: Double,
    @SerializedName("area") val area: Double,
    @SerializedName("address") val address: String,
    @SerializedName("subways") val subways: ArrayList<ResultGetFilteredAssetsSubwayDetail>
)