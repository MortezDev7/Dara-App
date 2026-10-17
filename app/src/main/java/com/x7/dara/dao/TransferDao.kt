package com.x7.dara.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.x7.dara.model.database.transaction.TransferEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransferDao {

    @Insert
    suspend fun insert(transfer: TransferEntity): Long

    @Update
    suspend fun update(transfer: TransferEntity)

    @Delete
    suspend fun delete(transfer: TransferEntity)

    @Query("""
        SELECT * FROM transfers
        WHERE id = :transferId
    """)
    suspend fun getById(transferId: Long): TransferEntity?

    @Query("""
        SELECT * FROM transfers
        WHERE transactionId = :transactionId
    """)
    suspend fun getByTransactionId(
        transactionId: Long
    ): TransferEntity?

    @Query("""
        SELECT * FROM transfers
        WHERE fromAccountId = :accountId
        OR toAccountId = :accountId
        ORDER BY id DESC
    """)
    fun getByAccount(accountId: Long): Flow<List<TransferEntity>>
}