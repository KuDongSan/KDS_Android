package com.codelab.kudongsan.src.main.detail.models

import com.google.gson.annotations.SerializedName

data class Area(
    @SerializedName("exclusiveArea") val exclusiveArea: Double,
    @SerializedName("supplyArea") val supplyArea: Double
)