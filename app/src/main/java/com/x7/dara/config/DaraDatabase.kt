package com.x7.dara.config

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.x7.dara.dao.AccountDao
import com.x7.dara.dao.AssetDao
import com.x7.dara.dao.AssetPriceDao
import com.x7.dara.dao.BudgetDao
import com.x7.dara.dao.CategoryDao
import com.x7.dara.dao.MarketCacheDao
import com.x7.dara.dao.TransactionDao
import com.x7.dara.dao.TransferDao
import com.x7.dara.model.database.account.AccountEntity
import com.x7.dara.model.database.asset.AssetEntity
import com.x7.dara.model.database.asset.AssetPriceEntity
import com.x7.dara.model.database.converter.Converters
import com.x7.dara.model.database.market.MarketCacheEntity
import com.x7.dara.model.database.transaction.CategoryEntity
import com.x7.dara.model.database.transaction.TransactionEntity
import com.x7.dara.model.database.transaction.TransferEntity
import com.x7.dara.model.database.user.BudgetEntity

@Database(
    entities = [
        AccountEntity::class,
        CategoryEntity::class,
        TransactionEntity::class,
        TransferEntity::class,
        AssetEntity::class,
        AssetPriceEntity::class,
        BudgetEntity::class,
        MarketCacheEntity::class
    ],
    version = 2,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class DaraDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun transferDao(): TransferDao
    abstract fun assetDao(): AssetDao
    abstract fun assetPriceDao(): AssetPriceDao
    abstract fun budgetDao(): BudgetDao

    abstract fun marketCacheDao(): MarketCacheDao
}