package com.codelab.kudongsan.src.main.home.assets.models

import com.google.gson.annotations.SerializedName

data class ResultGetAssetsSubwayFirstDetail(
    @SerializedName("name") val name: String,
    @SerializedName("distance") val distance: Int
)