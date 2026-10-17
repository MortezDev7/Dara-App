package com.x7.dara.model.database.user

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.x7.dara.model.database.transaction.CategoryEntity

@Entity(
    tableName = "budgets",

    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],

    indices = [
        Index(value = ["categoryId"]),

        Index(
            value = ["categoryId", "month", "year"],
            unique = true
        )
    ]
)
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val categoryId: Long,
    val amount: Long,
    val month: Int,
    val year: Int
)