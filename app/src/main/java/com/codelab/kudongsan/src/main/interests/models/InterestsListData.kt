package com.codelab.kudongsan.src.main.interests.models

data class InterestsListData(
    var itemId: Int,
    var salesType: String,
    var serviceType: String,
    var image_thumbnail: String,
    var deposit: Int,
    var monthlyRentPrice: Int?,
    var manageCost: Double,
    var area: Double,
    var address: String,
    var subwayName: String,
    var subwayDescription: String,
    var subwayDistance: Int
)