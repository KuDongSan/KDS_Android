package com.codelab.kudongsan.src.main.register.models

import com.google.gson.annotations.SerializedName

data class RoomType(
    @SerializedName("roomType") val roomType: String,
    @SerializedName("roomTypeCode") val roomTypeCode: String
)