package com.x7.dara.model.server

data class BrsCrypto(
    val date: String,
    val time: String,
    val time_unix: Long,
    val symbol: String,
    val name_en: String,
    val name: String,
    val price: String,
    val change_percent: Double,
    val market_cap: Long,
    val unit: String,
    val description: String
)