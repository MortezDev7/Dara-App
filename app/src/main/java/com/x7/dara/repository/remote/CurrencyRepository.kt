package com.x7.dara.repository.remote

import com.x7.dara.api.CurrencyApi
import com.x7.dara.model.brs.BrsResponse
import com.x7.dara.repository.local.MarketCacheRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val api: CurrencyApi,
    private val marketCacheRepository: MarketCacheRepository
) {

    suspend fun getMarketData(): BrsResponse {

        val response = api.getMarketData()

        marketCacheRepository.saveResponse(
            response
        )

        return response
    }

    fun observeCachedMarketData(): Flow<BrsResponse?> {
        return marketCacheRepository.observeResponse()
    }
}