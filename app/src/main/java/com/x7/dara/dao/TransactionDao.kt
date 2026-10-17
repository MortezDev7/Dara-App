package com.x7.dara.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.x7.dara.model.database.transaction.TransactionEntity
import com.x7.dara.model.database.transaction.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(transaction: TransactionEntity): Long

    @Update
    suspend fun update(transaction: TransactionEntity)

    @Delete
    suspend fun delete(transaction: TransactionEntity)

    @Query("""
        SELECT * FROM transactions
        ORDER BY date DESC, id DESC
    """)
    fun getAll(): Flow<List<TransactionEntity>>

    @Query("""
        SELECT * FROM transactions
        WHERE id = :transactionId
    """)
    suspend fun getById(transactionId: Long): TransactionEntity?

    @Query("""
        SELECT * FROM transactions
        WHERE accountId = :accountId
        ORDER BY date DESC, id DESC
    """)
    fun getByAccount(accountId: Long): Flow<List<TransactionEntity>>

    @Query("""
        SELECT * FROM transactions
        WHERE type = :type
        ORDER BY date DESC, id DESC
    """)
    fun getByType(type: TransactionType): Flow<List<TransactionEntity>>

    @Query("""
        SELECT COALESCE(SUM(amount), 0)
        FROM transactions
        WHERE type = :type
    """)
    fun getTotalByType(type: TransactionType): Flow<Long>

    @Query("""
        SELECT COALESCE(SUM(amount), 0)
        FROM transactions
        WHERE accountId = :accountId
        AND type = :type
    """)
    fun getTotalByAccountAndType(
        accountId: Long,
        type: TransactionType
    ): Flow<Long>
}