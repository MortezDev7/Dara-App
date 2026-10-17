package com.x7.dara.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.x7.dara.model.database.user.BudgetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Insert
    suspend fun insert(budget: BudgetEntity): Long

    @Update
    suspend fun update(budget: BudgetEntity)

    @Delete
    suspend fun delete(budget: BudgetEntity)

    @Query("""
        SELECT * FROM budgets
        WHERE month = :month
        AND year = :year
        ORDER BY categoryId ASC
    """)
    fun getByMonth(
        month: Int,
        year: Int
    ): Flow<List<BudgetEntity>>

    @Query("""
        SELECT * FROM budgets
        WHERE categoryId = :categoryId
        AND month = :month
        AND year = :year
    """)
    suspend fun getByCategoryAndMonth(
        categoryId: Long,
        month: Int,
        year: Int
    ): BudgetEntity?

    @Query("""
        SELECT * FROM budgets
        WHERE id = :budgetId
    """)
    suspend fun getById(budgetId: Long): BudgetEntity?
}