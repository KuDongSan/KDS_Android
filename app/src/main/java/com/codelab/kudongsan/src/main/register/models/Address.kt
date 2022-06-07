package com.codelab.kudongsan.src.main.register.models

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("jibunAddress") val jibunAddress: String,
    @SerializedName("address1") val address1: String,
    @SerializedName("address2") val address2: String,
    @SerializedName("address3") val address3: String
)