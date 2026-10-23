package com.x7.dara.model.server.mapper

data class MarketItemUi(
    var symbol: String,
    var title : String,
    var price : String,
    var unit : String,
    var changeValue : String?,
    var changePercent: String,
    var isPositive: Boolean?,
    var category:MarketCategory
    )