package com.codelab.kudongsan.src.main.home.assets.models

import com.google.gson.annotations.SerializedName

data class ResultGetAssetsSubwaySecondDetail(
    @SerializedName("name") val name: String,
    @SerializedName("distance") val distance: Int
)