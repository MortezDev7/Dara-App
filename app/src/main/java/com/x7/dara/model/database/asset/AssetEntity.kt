package com.x7.dara.model.database.asset

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assets")
data class AssetEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val name: String,
    val type: AssetType,
    val quantity: Double,
    val unit: String,
    val purchasePrice: Long,
    val purchaseDate: Long,
    val createdAt: Long
)

