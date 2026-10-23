package com.x7.dara.api

import com.x7.dara.model.server.BrsResponse
import retrofit2.http.GET

interface CurrencyApi {

    @GET("api/market")
    suspend fun getMarketData(
    ): BrsResponse
}