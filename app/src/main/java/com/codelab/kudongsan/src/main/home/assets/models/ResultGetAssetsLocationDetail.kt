package com.codelab.kudongsan.src.main.home.assets.models

import com.google.gson.annotations.SerializedName

data class ResultGetAssetsLocationDetail(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("lontitude") val lontitude: Double,
)