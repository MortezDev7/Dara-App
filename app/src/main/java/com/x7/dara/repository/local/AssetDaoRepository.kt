package com.x7.dara.repository.local

import com.x7.dara.dao.AssetDao
import com.x7.dara.model.database.asset.AssetEntity
import com.x7.dara.model.database.asset.AssetType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AssetDaoRepository @Inject constructor(
    private val assetDao: AssetDao
) {

    suspend fun insert(asset: AssetEntity): Long {
        return assetDao.insert(asset)
    }

    suspend fun update(asset: AssetEntity) {
        assetDao.update(asset)
    }

    suspend fun delete(asset: AssetEntity) {
        assetDao.delete(asset)
    }

    fun getAll(): Flow<List<AssetEntity>> {
        return assetDao.getAll()
    }

    suspend fun getById(assetId: Long): AssetEntity? {
        return assetDao.getById(assetId)
    }

    fun getByType(
        type: AssetType
    ): Flow<List<AssetEntity>> {
        return assetDao.getByType(type)
    }
}