package com.codelab.kudongsan.src.main.interests.models

import com.google.gson.annotations.SerializedName

data class ResultGetInterestsSubwayDetail(

    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("distance") val distance: Int

)