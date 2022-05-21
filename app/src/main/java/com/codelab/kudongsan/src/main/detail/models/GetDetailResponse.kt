package com.codelab.kudongsan.src.main.detail.models

import com.google.gson.annotations.SerializedName

data class GetDetailResponse(
    @SerializedName("address") val address: String,
    @SerializedName("agentPhone") val agentPhone: String,
    @SerializedName("agentTitle") val agentTitle: String,
    @SerializedName("area") val area: Area,
    @SerializedName("bathroomCount") val bathroomCount: Int,
    @SerializedName("deposit") val deposit: Int,
    @SerializedName("description") val description: String,
    @SerializedName("elevator") val elevator: String,
    @SerializedName("favorite") val favorite: Boolean,
    @SerializedName("floor") val floor: Floor,
    @SerializedName("imageThumbnail") val imageThumbnail: String,
    @SerializedName("images") val images: ArrayList<String>,
    @SerializedName("itemId") val itemId: Int,
    @SerializedName("jibunAddress") val jibunAddress: String,
    @SerializedName("location") val location: Location,
    @SerializedName("manageCost") val manageCost: Int,
    @SerializedName("manageCostInc") val manageCostInc: String,
    @SerializedName("manageCostNotInc") val manageCostNotInc: String,
    @SerializedName("monthlyRentPrice") val monthlyRentPrice: Int,
    @SerializedName("moveinDate") val moveinDate: String,
    @SerializedName("options") val options: String,
    @SerializedName("parking") val parking: String,
    @SerializedName("popular") val popular: Popular,
    @SerializedName("residenceType") val residenceType: String,
    @SerializedName("roomDirection") val roomDirection: String,
    @SerializedName("roomType") val roomType: RoomType,
    @SerializedName("salePrice") val salePrice: Int,
    @SerializedName("salesType") val salesType: String,
    @SerializedName("serviceType") val serviceType: String,
    @SerializedName("status") val status: String,
    @SerializedName("subways") val subways: ArrayList<Subway>,
    @SerializedName("title") val title: String,
    @SerializedName("updateAt") val updateAt: String
)