package com.x7.dara.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.x7.dara.model.database.asset.AssetEntity
import com.x7.dara.model.database.asset.AssetType
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {

    @Insert
    suspend fun insert(asset: AssetEntity): Long

    @Update
    suspend fun update(asset: AssetEntity)

    @Delete
    suspend fun delete(asset: AssetEntity)

    @Query("""
        SELECT * FROM assets
        ORDER BY createdAt DESC
    """)
    fun getAll(): Flow<List<AssetEntity>>

    @Query("""
        SELECT * FROM assets
        WHERE id = :assetId
    """)
    suspend fun getById(assetId: Long): AssetEntity?

    @Query("""
        SELECT * FROM assets
        WHERE type = :type
        ORDER BY createdAt DESC
    """)
    fun getByType(type: AssetType): Flow<List<AssetEntity>>
}