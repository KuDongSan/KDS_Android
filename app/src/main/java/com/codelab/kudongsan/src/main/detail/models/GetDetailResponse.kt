package com.codelab.kudongsan.src.main.detail.models

import com.google.gson.annotations.SerializedName

data class GetDetailResponse(
    @SerializedName("itemId") val itemId: Int,
    @SerializedName("salesType") val salesType: String,
    @SerializedName("serviceType") val serviceType: String,
    @SerializedName("images") val images: ArrayList<String>,
    @SerializedName("imageThumbnail") val imageThumbnail: String,
    @SerializedName("deposit") val deposit: Int,
    @SerializedName("salePrice") val salePrice: Int?,
    @SerializedName("monthlyRentPrice") val monthlyRentPrice: Int?,
    @SerializedName("area") val area: Area,
    @SerializedName("jibunAddress") val jibunAddress: String,
    @SerializedName("address") val address: String,
    @SerializedName("roomType") val roomType: RoomType,
    @SerializedName("title") val title: String,
    @SerializedName("status") val status: String,
    @SerializedName("description") val description: String,
    @SerializedName("parking") val parking: String,
    @SerializedName("elevator") val elevator: String,
    @SerializedName("roomDirection") val roomDirection: String,
    @SerializedName("moveinDate") val moveinDate: String,
    @SerializedName("floor") val floor: Floor,
    @SerializedName("options") val options: String?,
    @SerializedName("manageCostInc") val manageCostInc: String?,
    @SerializedName("bathroomCount") val bathroomCount: Int,
    @SerializedName("residenceType") val residenceType: String,
    @SerializedName("manageCostNotInc") val manageCostNotInc: String,
    @SerializedName("popular") val popular: Popular?,
    @SerializedName("location") val location: Location,
    @SerializedName("agentTitle") val agentTitle: String,
    @SerializedName("agentPhone") val agentPhone: String,
    @SerializedName("manageCost") val manageCost: Double,
    @SerializedName("favorite") val favorite: Boolean,
    @SerializedName("updateAt") val updateAt: String,
    @SerializedName("subways") val subways: ArrayList<Subway>
)