package com.x7.dara.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.x7.dara.model.database.account.AccountEntity
import com.x7.dara.model.database.transaction.CategoryEntity
import com.x7.dara.model.database.transaction.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query("""
    SELECT balance
    FROM accounts
    WHERE id = :accountId
""")
    suspend fun getBalance(accountId: Long): Long?
    @Insert
    suspend fun insert(account: AccountEntity): Long

    @Update
    suspend fun update(account: AccountEntity)

    @Delete
    suspend fun delete(account: AccountEntity)

    @Query("SELECT * FROM accounts ORDER BY id DESC")
    fun getAll(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    suspend fun getById(accountId: Long): AccountEntity?

    @Query("""
        UPDATE accounts
        SET balance = balance + :amount
        WHERE id = :accountId
    """)
    suspend fun increaseBalance(
        accountId: Long,
        amount: Long
    )

    @Query("""
        UPDATE accounts
        SET balance = balance - :amount
        WHERE id = :accountId
    """)
    suspend fun decreaseBalance(
        accountId: Long,
        amount: Long
    )

    @Query("DELETE FROM accounts")
    suspend fun deleteAll()

    @Query("""
        SELECT *
        FROM accounts
        WHERE name = :name
        LIMIT 1
    """)
    suspend fun getByNameAndType(
        name: String,
    ): AccountEntity?
}
