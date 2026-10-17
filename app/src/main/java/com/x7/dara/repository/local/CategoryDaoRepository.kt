package com.x7.dara.repository.local

import com.x7.dara.dao.CategoryDao
import com.x7.dara.model.database.transaction.CategoryEntity
import com.x7.dara.model.database.transaction.TransactionType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryDaoRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {

    suspend fun insert(
        category: CategoryEntity
    ): Long {
        return categoryDao.insert(category)
    }

    suspend fun insertIfNotExists(
        category: CategoryEntity
    ): Long {

        val existingCategory =
            categoryDao.getByNameAndType(
                name = category.name,
                type = category.type
            )

        if (existingCategory != null) {
            return existingCategory.id
        }

        return categoryDao.insert(category)
    }

    suspend fun update(
        category: CategoryEntity
    ) {
        categoryDao.update(category)
    }

    suspend fun delete(
        category: CategoryEntity
    ) {
        categoryDao.delete(category)
    }

    fun getAll(): Flow<List<CategoryEntity>> {
        return categoryDao.getAll()
    }

    suspend fun getById(
        categoryId: Long
    ): CategoryEntity? {
        return categoryDao.getById(categoryId)
    }

    fun getByType(
        type: TransactionType
    ): Flow<List<CategoryEntity>> {
        return categoryDao.getByType(type)
    }

    suspend fun getByNameAndType(
        name: String,
        type: TransactionType
    ): CategoryEntity? {
        return categoryDao.getByNameAndType(
            name = name,
            type = type
        )
    }

    suspend fun updateDefaultCategoryIcon(
        name: String,
        type: TransactionType,
        icon: Int
    ) {
        categoryDao.updateDefaultCategoryIcon(
            name = name,
            type = type,
            icon = icon
        )
    }
}