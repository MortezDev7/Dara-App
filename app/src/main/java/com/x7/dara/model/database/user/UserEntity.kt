package com.x7.dara.model.database.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val firstName: String? = null,
    val lastName: String? = null,
    val profileImage: String? = null,
    val phoneNumber: String? = null,
    val isGuest: Boolean,
    val createdAt: Long,
    val updatedAt : Long,
)