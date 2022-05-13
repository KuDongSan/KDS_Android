package com.codelab.kudongsan.src.main.home.assets.models

import com.google.gson.annotations.SerializedName

data class ResultGetAssets(
    @SerializedName("itemId") val itemId: Long,
    @SerializedName("salesType") val salesType: String,
    @SerializedName("serviceType") val serviceType: String,
    @SerializedName("image_thumbnail") val image_thumbnail: String,
    @SerializedName("deposit") val deposit: Long,
    @SerializedName("salePrice") val salePrice: String?,
    @SerializedName("monthlyRentPrice") val monthlyRentPrice: String?,
    @SerializedName("area") val area: Double,
    @SerializedName("address") val address: ResultGetAssetsAddressDetail,
    @SerializedName("location") val location: ResultGetAssetsLocationDetail,
    @SerializedName("agentTitle") val agentTitle: String,
    @SerializedName("agentPhone") val agentPhone: String,
    @SerializedName("manageCost") val manageCost: Double,
    @SerializedName("subway1") val subway1: ResultGetAssetsSubwayFirstDetail,
    @SerializedName("subway2") val subway2: ResultGetAssetsSubwaySecondDetail,
    @SerializedName("subway3") val subway3: ResultGetAssetsSubwayThirdDetail,
)