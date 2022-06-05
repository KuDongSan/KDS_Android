package com.codelab.kudongsan.src.main.compare.model

import com.google.gson.annotations.SerializedName

data class RoomType(
    @SerializedName("roomTypeCode") val roomTypeCode: String,
    @SerializedName("roomType") val roomType: String
)