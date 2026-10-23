package com.x7.dara.module

import android.content.Context
import androidx.room.Room
import com.x7.dara.config.DaraDatabase
import com.x7.dara.dao.AccountDao
import com.x7.dara.dao.AssetDao
import com.x7.dara.dao.AssetPriceDao
import com.x7.dara.dao.BudgetDao
import com.x7.dara.dao.CategoryDao
import com.x7.dara.dao.MarketCacheDao
import com.x7.dara.dao.TransactionDao
import com.x7.dara.dao.TransferDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext context: Context
    ): DaraDatabase {

        return Room.databaseBuilder(
            context,
            DaraDatabase::class.java,
            "dara_db"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideAccountDao(
        database: DaraDatabase
    ): AccountDao {
        return database.accountDao()
    }

    @Provides
    @Singleton
    fun categoryDao(
        database: DaraDatabase
    ): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    @Singleton
    fun transactionDao(
        database: DaraDatabase
    ): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    @Singleton
    fun transferDao(
        database: DaraDatabase
    ): TransferDao {
        return database.transferDao()
    }

    @Provides
    @Singleton
    fun assetDao(
        database: DaraDatabase
    ): AssetDao {
        return database.assetDao()
    }

    @Provides
    @Singleton
    fun assetPriceDao(
        database: DaraDatabase
    ): AssetPriceDao {
        return database.assetPriceDao()
    }

    @Provides
    @Singleton
    fun budgetDao(
        database: DaraDatabase
    ): BudgetDao {
        return database.budgetDao()
    }

    @Provides
    @Singleton
    fun marketCacheDao(
        database: DaraDatabase
    ): MarketCacheDao {
        return database.marketCacheDao()
    }
}