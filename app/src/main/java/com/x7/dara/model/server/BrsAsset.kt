package com.x7.dara.model.server

data class BrsAsset(
    val date: String,
    val time: String,
    val time_unix: Long,
    val symbol: String,
    val name_en: String,
    val name: String,
    val price: Long,
    val change_value: Long,
    val change_percent: Double,
    val unit: String
)