package com.x7.dara.repository.local

import com.x7.dara.dao.AssetPriceDao
import com.x7.dara.model.database.asset.AssetPriceEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AssetPriceDaoRepository @Inject constructor(
    private val assetPriceDao: AssetPriceDao
) {

    suspend fun insert(
        price: AssetPriceEntity
    ): Long {
        return assetPriceDao.insert(price)
    }

    suspend fun update(
        price: AssetPriceEntity
    ) {
        assetPriceDao.update(price)
    }

    suspend fun delete(
        price: AssetPriceEntity
    ) {
        assetPriceDao.delete(price)
    }

    fun getByAsset(
        assetId: Long
    ): Flow<List<AssetPriceEntity>> {
        return assetPriceDao.getByAsset(
            assetId
        )
    }

    fun getByAssets(
        assetIds: List<Long>
    ): Flow<List<AssetPriceEntity>> {
        return assetPriceDao.getByAssets(
            assetIds
        )
    }

    suspend fun getLatest(
        assetId: Long
    ): AssetPriceEntity? {
        return assetPriceDao.getLatest(
            assetId
        )
    }

    suspend fun deleteByAsset(
        assetId: Long
    ) {
        assetPriceDao.deleteByAsset(
            assetId
        )
    }
}