package com.x7.dara.model.database.market

import androidx.room.Entity

@Entity(
    tableName = "market_cache",
    primaryKeys = [
        "category",
        "symbol"
    ]
)
data class MarketCacheEntity(
    val category: String,
    val symbol: String,
    val date: String,
    val time: String,
    val timeUnix: Long,
    val nameEn: String,
    val name: String,
    val price: String,
    val changeValue: Long?,
    val changePercent: Double,
    val marketCap: Long?,
    val unit: String,
    val description: String?,
    val cachedAt: Long
)