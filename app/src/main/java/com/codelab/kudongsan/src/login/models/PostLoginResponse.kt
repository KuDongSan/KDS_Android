package com.codelab.kudongsan.src.login.models

import com.google.gson.annotations.SerializedName

data class PostLoginResponse(

    @SerializedName("name") val nickname: String,
    @SerializedName("email") val email: String,
    @SerializedName("imageThumbnamil") val imageThumbnail: String

)