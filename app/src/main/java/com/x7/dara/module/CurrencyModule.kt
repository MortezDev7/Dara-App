package com.x7.dara.module

import com.x7.dara.BuildConfig
import com.x7.dara.api.CurrencyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrencyModule {

    private const val BASE_URL =
        BuildConfig.SERVER_BASE_URL
    @Singleton
    @Provides
    fun provieApi() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyApi(retrofit: Retrofit) : CurrencyApi{
        return provieApi().create(CurrencyApi::class.java)
    }
}