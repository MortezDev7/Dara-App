package com.x7.dara.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.x7.dara.model.database.transaction.CategoryEntity
import com.x7.dara.model.database.transaction.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(category: CategoryEntity): Long

    @Update
    suspend fun update(category: CategoryEntity)

    @Delete
    suspend fun delete(category: CategoryEntity)

    @Query("""
        SELECT *
        FROM categories
        ORDER BY
            isDefault DESC,
            CASE
                WHEN name = 'سایر' THEN 1
                ELSE 0
            END,
            name ASC
    """)
    fun getAll(): Flow<List<CategoryEntity>>

    @Query("""
        SELECT *
        FROM categories
        WHERE id = :categoryId
    """)
    suspend fun getById(
        categoryId: Long
    ): CategoryEntity?

    @Query("""
        SELECT *
        FROM categories
        WHERE type = :type
        ORDER BY
            isDefault DESC,
            CASE
                WHEN name = 'سایر' THEN 1
                ELSE 0
            END,
            name ASC
    """)
    fun getByType(
        type: TransactionType
    ): Flow<List<CategoryEntity>>

    @Query("""
        SELECT *
        FROM categories
        WHERE name = :name
        AND type = :type
        LIMIT 1
    """)
    suspend fun getByNameAndType(
        name: String,
        type: TransactionType
    ): CategoryEntity?

    @Query("""
    UPDATE categories
    SET icon = :icon
    WHERE name = :name
      AND type = :type
      AND isDefault = 1
""")
    suspend fun updateDefaultCategoryIcon(
        name: String,
        type: TransactionType,
        icon: Int
    )
}