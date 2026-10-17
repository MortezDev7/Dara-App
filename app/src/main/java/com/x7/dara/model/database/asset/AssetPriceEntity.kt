package com.x7.dara.model.database.asset

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "asset_prices",

    foreignKeys = [
        ForeignKey(
            entity = AssetEntity::class,
            parentColumns = ["id"],
            childColumns = ["assetId"],
            onDelete = ForeignKey.CASCADE
        )
    ],

    indices = [
        Index(value = ["assetId"]),
        Index(value = ["assetId", "timestamp"])
    ]
)
data class AssetPriceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val assetId: Long,
    val price: Long,
    val timestamp: Long
)
