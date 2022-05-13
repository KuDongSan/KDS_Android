package com.codelab.kudongsan.src.main.home.assets.models

import com.google.gson.annotations.SerializedName

data class ResultGetAssetsSubwayDetail(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("distance") val distance: Int
)