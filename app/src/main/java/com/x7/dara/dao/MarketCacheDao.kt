package com.x7.dara.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.x7.dara.model.database.market.MarketCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketCacheDao {

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertAll(
        items: List<MarketCacheEntity>
    )

    @Query("""
        DELETE FROM market_cache
    """)
    suspend fun deleteAll()

    @Query("""
        SELECT * FROM market_cache
    """)
    fun observeAll(): Flow<List<MarketCacheEntity>>

    @Query("""
        SELECT * FROM market_cache
        WHERE category = :category
          AND symbol = :symbol
        LIMIT 1
    """)
    fun observeBySymbol(
        category: String,
        symbol: String
    ): Flow<MarketCacheEntity?>
}