package com.codelab.kudongsan.src.main.home.assets.models

data class AssetsListData(
    var itemId: Int,
    var salesType: String,
    var serviceType: String,
    var image_thumbnail: String,
    var deposit: Long,
    var monthlyRentPrice: Long?,
    var area: Double,
    var address: String,
    var manageCost: Double,
    var subways: ArrayList<String>
)