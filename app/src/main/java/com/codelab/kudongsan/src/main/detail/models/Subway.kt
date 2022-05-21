package com.codelab.kudongsan.src.main.detail.models

import com.google.gson.annotations.SerializedName

data class Subway(
    @SerializedName("description") val description: String,
    @SerializedName("distance") val distance: Int,
    @SerializedName("name") val name: String
)