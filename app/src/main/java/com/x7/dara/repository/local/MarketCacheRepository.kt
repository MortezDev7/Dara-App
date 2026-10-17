package com.x7.dara.repository.local

import com.x7.dara.dao.MarketCacheDao
import com.x7.dara.model.brs.BrsAsset
import com.x7.dara.model.brs.BrsCrypto
import com.x7.dara.model.brs.BrsResponse
import com.x7.dara.model.database.market.MarketCacheEntity
import com.x7.dara.model.brs.mapper.MarketCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarketCacheRepository @Inject constructor(
    private val marketCacheDao: MarketCacheDao
) {

    suspend fun saveResponse(
        response: BrsResponse
    ) {
        val items = mutableListOf<MarketCacheEntity>()

        response.gold.forEach { asset ->
            items.add(
                asset.toCacheEntity(
                    category = MarketCategory.GOLD
                )
            )
        }

        response.currency.forEach { asset ->
            items.add(
                asset.toCacheEntity(
                    category = MarketCategory.CURRENCY
                )
            )
        }

        response.cryptocurrency.forEach { crypto ->
            items.add(
                crypto.toCacheEntity()
            )
        }

        marketCacheDao.deleteAll()

        if (items.isNotEmpty()) {
            marketCacheDao.insertAll(items)
        }
    }

    fun observeResponse(): Flow<BrsResponse?> {
        return marketCacheDao
            .observeAll()
            .map { items ->

                if (items.isEmpty()) {
                    null
                } else {

                    val gold = items
                        .filter {
                            it.category ==
                                    MarketCategory.GOLD.name
                        }
                        .map {
                            it.toBrsAsset()
                        }

                    val currency = items
                        .filter {
                            it.category ==
                                    MarketCategory.CURRENCY.name
                        }
                        .map {
                            it.toBrsAsset()
                        }

                    val cryptocurrency = items
                        .filter {
                            it.category ==
                                    MarketCategory.CRYPTO.name
                        }
                        .map {
                            it.toBrsCrypto()
                        }

                    BrsResponse(
                        gold = gold,
                        currency = currency,
                        cryptocurrency = cryptocurrency
                    )
                }
            }
    }
}

private fun BrsAsset.toCacheEntity(
    category: MarketCategory
): MarketCacheEntity {

    return MarketCacheEntity(
        category = category.name,
        symbol = symbol,
        date = date,
        time = time,
        timeUnix = time_unix,
        nameEn = name_en,
        name = name,
        price = price.toString(),
        changeValue = change_value,
        changePercent = change_percent,
        marketCap = null,
        unit = unit,
        description = null,
        cachedAt = System.currentTimeMillis()
    )
}

private fun BrsCrypto.toCacheEntity(): MarketCacheEntity {

    return MarketCacheEntity(
        category = MarketCategory.CRYPTO.name,
        symbol = symbol,
        date = date,
        time = time,
        timeUnix = time_unix,
        nameEn = name_en,
        name = name,
        price = price,
        changeValue = null,
        changePercent = change_percent,
        marketCap = market_cap,
        unit = unit,
        description = description,
        cachedAt = System.currentTimeMillis()
    )
}

private fun MarketCacheEntity.toBrsAsset(): BrsAsset {

    return BrsAsset(
        date = date,
        time = time,
        time_unix = timeUnix,
        symbol = symbol,
        name_en = nameEn,
        name = name,
        price = price.toLongOrNull() ?: 0L,
        change_value = changeValue ?: 0L,
        change_percent = changePercent,
        unit = unit
    )
}

private fun MarketCacheEntity.toBrsCrypto(): BrsCrypto {

    return BrsCrypto(
        date = date,
        time = time,
        time_unix = timeUnix,
        symbol = symbol,
        name_en = nameEn,
        name = name,
        price = price,
        change_percent = changePercent,
        market_cap = marketCap ?: 0L,
        unit = unit,
        description = description.orEmpty()
    )
}