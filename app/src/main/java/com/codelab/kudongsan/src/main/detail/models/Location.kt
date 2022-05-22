package com.codelab.kudongsan.src.main.detail.models

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longtitude") val longtitude: Double
)