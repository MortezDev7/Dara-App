package com.x7.dara.model.database.transaction

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.x7.dara.model.database.account.AccountEntity

@Entity(
    tableName = "transfers",

    foreignKeys = [
        ForeignKey(
            entity = TransactionEntity::class,
            parentColumns = ["id"],
            childColumns = ["transactionId"],
            onDelete = ForeignKey.CASCADE
        ),

        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["id"],
            childColumns = ["fromAccountId"],
            onDelete = ForeignKey.RESTRICT
        ),

        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["id"],
            childColumns = ["toAccountId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],

    indices = [
        Index(value = ["transactionId"], unique = true),
        Index(value = ["fromAccountId"]),
        Index(value = ["toAccountId"])
    ]
)
data class TransferEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val transactionId: Long,

    val fromAccountId: Long,

    val toAccountId: Long
)