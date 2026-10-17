package com.x7.dara.repository.local

import com.x7.dara.dao.BudgetDao
import com.x7.dara.model.database.user.BudgetEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BudgetDaoRepository @Inject constructor(
    private val budgetDao: BudgetDao
) {

    suspend fun insert(budget: BudgetEntity): Long {
        return budgetDao.insert(budget)
    }

    suspend fun update(budget: BudgetEntity) {
        budgetDao.update(budget)
    }

    suspend fun delete(budget: BudgetEntity) {
        budgetDao.delete(budget)
    }

    fun getByMonth(
        month: Int,
        year: Int
    ): Flow<List<BudgetEntity>> {
        return budgetDao.getByMonth(month, year)
    }

    suspend fun getByCategoryAndMonth(
        categoryId: Long,
        month: Int,
        year: Int
    ): BudgetEntity? {
        return budgetDao.getByCategoryAndMonth(
            categoryId,
            month,
            year
        )
    }

    suspend fun getById(
        budgetId: Long
    ): BudgetEntity? {
        return budgetDao.getById(budgetId)
    }
}