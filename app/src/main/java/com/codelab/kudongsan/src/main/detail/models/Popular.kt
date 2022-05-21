package com.codelab.kudongsan.src.main.detail.models

import com.google.gson.annotations.SerializedName

data class Popular(
    @SerializedName("popularDescription") val popularDescription: String,
    @SerializedName("popularTitle") val popularTitle: String,
    @SerializedName("popularURL") val popularURL: String
)