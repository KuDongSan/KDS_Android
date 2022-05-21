package com.codelab.kudongsan.src.main.detail.models

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("latitude") val latitude: Int,
    @SerializedName("longtitude") val longtitude: Int
)