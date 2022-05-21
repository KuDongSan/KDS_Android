package com.codelab.kudongsan.src.main.detail.models

import com.google.gson.annotations.SerializedName

data class Floor(
    @SerializedName("floor") val floor: String,
    @SerializedName("floorAll") val floorAll: String,
    @SerializedName("floorString") val floorString: String
)