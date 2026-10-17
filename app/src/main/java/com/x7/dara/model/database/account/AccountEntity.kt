package com.x7.dara.model.database.account

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val accountType: AccountType,
    val balance: Long,
    val currency: String = "IRR",
    val icon: String?,
    val createdAt: Long,
    )