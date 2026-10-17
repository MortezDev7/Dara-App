package com.x7.dara.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.x7.dara.model.database.asset.AssetPriceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetPriceDao {

    @Insert
    suspend fun insert(price: AssetPriceEntity): Long

    @Update
    suspend fun update(price: AssetPriceEntity)

    @Delete
    suspend fun delete(price: AssetPriceEntity)

    @Query("""
        SELECT * FROM asset_prices
        WHERE assetId = :assetId
        ORDER BY timestamp DESC
    """)
    fun getByAsset(
        assetId: Long
    ): Flow<List<AssetPriceEntity>>

    @Query("""
        SELECT * FROM asset_prices
        WHERE assetId IN (:assetIds)
        ORDER BY timestamp DESC
    """)
    fun getByAssets(
        assetIds: List<Long>
    ): Flow<List<AssetPriceEntity>>

    @Query("""
        SELECT * FROM asset_prices
        WHERE assetId = :assetId
        ORDER BY timestamp DESC
        LIMIT 1
    """)
    suspend fun getLatest(
        assetId: Long
    ): AssetPriceEntity?

    @Query("""
        DELETE FROM asset_prices
        WHERE assetId = :assetId
    """)
    suspend fun deleteByAsset(
        assetId: Long
    )
}