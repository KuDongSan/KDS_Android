package com.codelab.kudongsan.src.main.home.filtering.models

import com.google.gson.annotations.SerializedName

data class ResultGetFilteredAssetsSubwayDetail(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("distance") val distance: Int
)