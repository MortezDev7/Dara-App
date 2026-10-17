package com.x7.dara.repository.local

import androidx.room.withTransaction
import com.x7.dara.config.DaraDatabase
import com.x7.dara.dao.AccountDao
import com.x7.dara.dao.TransactionDao
import com.x7.dara.model.database.transaction.TransactionEntity
import com.x7.dara.model.database.transaction.TransactionType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionDaoRepository @Inject constructor(
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao,
    private val daraDatabase: DaraDatabase
) {

    fun getAll(): Flow<List<TransactionEntity>> {
        return transactionDao.getAll()
    }

    suspend fun getById(
        transactionId: Long
    ): TransactionEntity? {
        return transactionDao.getById(transactionId)
    }

    fun getByAccount(
        accountId: Long
    ): Flow<List<TransactionEntity>> {
        return transactionDao.getByAccount(accountId)
    }

    fun getByType(
        type: TransactionType
    ): Flow<List<TransactionEntity>> {
        return transactionDao.getByType(type)
    }

    fun getTotalByType(
        type: TransactionType
    ): Flow<Long> {
        return transactionDao.getTotalByType(type)
    }

    fun getTotalByAccountAndType(
        accountId: Long,
        type: TransactionType
    ): Flow<Long> {
        return transactionDao.getTotalByAccountAndType(
            accountId = accountId,
            type = type
        )
    }

    suspend fun addIncome(
        accountId: Long,
        categoryId: Long,
        amount: Long,
        description: String?,
        date: Long
    ): Long {

        require(amount > 0) {
            "Amount must be greater than zero"
        }

        return daraDatabase.withTransaction {

            accountDao.getBalance(accountId)
                ?: throw IllegalArgumentException(
                    "Account not found"
                )

            val transaction = TransactionEntity(
                accountId = accountId,
                categoryId = categoryId,
                type = TransactionType.INCOME,
                amount = amount,
                description = description,
                date = date,
                createdAt = System.currentTimeMillis()
            )

            val transactionId =
                transactionDao.insert(transaction)

            accountDao.increaseBalance(
                accountId = accountId,
                amount = amount
            )

            transactionId
        }
    }

    suspend fun addExpense(
        accountId: Long,
        categoryId: Long,
        amount: Long,
        description: String?,
        date: Long
    ): Long {

        require(amount > 0) {
            "Amount must be greater than zero"
        }

        return daraDatabase.withTransaction {

            val balance = accountDao.getBalance(accountId)
                ?: throw IllegalArgumentException(
                    "Account not found"
                )

            if (balance < amount) {
                throw IllegalStateException(
                    "موجودی کافی نیست!"
                )
            }

            val transaction = TransactionEntity(
                accountId = accountId,
                categoryId = categoryId,
                type = TransactionType.EXPENSE,
                amount = amount,
                description = description,
                date = date,
                createdAt = System.currentTimeMillis()
            )

            val transactionId =
                transactionDao.insert(transaction)

            accountDao.decreaseBalance(
                accountId = accountId,
                amount = amount
            )

            transactionId
        }
    }

    suspend fun update(
        transaction: TransactionEntity
    ) {

        daraDatabase.withTransaction {

            val oldTransaction =
                transactionDao.getById(transaction.id)
                    ?: throw IllegalArgumentException(
                        "Transaction not found"
                    )

            require(transaction.amount > 0) {
                "Amount must be greater than zero"
            }

            require(
                transaction.type != TransactionType.TRANSFER
            ) {
                "Transfer must be updated through TransferDaoRepository"
            }

            // برگرداندن اثر تراکنش قبلی
            when (oldTransaction.type) {

                TransactionType.INCOME -> {
                    accountDao.decreaseBalance(
                        accountId = oldTransaction.accountId,
                        amount = oldTransaction.amount
                    )
                }

                TransactionType.EXPENSE -> {
                    accountDao.increaseBalance(
                        accountId = oldTransaction.accountId,
                        amount = oldTransaction.amount
                    )
                }

                TransactionType.TRANSFER -> {
                    throw IllegalStateException(
                        "Transfer must be updated through TransferDaoRepository"
                    )
                }
            }

            // اعمال اثر تراکنش جدید
            when (transaction.type) {

                TransactionType.INCOME -> {

                    accountDao.getBalance(
                        transaction.accountId
                    ) ?: throw IllegalArgumentException(
                        "Account not found"
                    )

                    accountDao.increaseBalance(
                        accountId = transaction.accountId,
                        amount = transaction.amount
                    )
                }

                TransactionType.EXPENSE -> {

                    val balance =
                        accountDao.getBalance(
                            transaction.accountId
                        ) ?: throw IllegalArgumentException(
                            "Account not found"
                        )

                    if (balance < transaction.amount) {
                        throw IllegalStateException(
                            "Insufficient balance"
                        )
                    }

                    accountDao.decreaseBalance(
                        accountId = transaction.accountId,
                        amount = transaction.amount
                    )
                }

                TransactionType.TRANSFER -> {
                    throw IllegalStateException(
                        "Transfer must be updated through TransferDaoRepository"
                    )
                }
            }

            transactionDao.update(transaction)
        }
    }

    suspend fun delete(
        transactionId: Long
    ) {

        daraDatabase.withTransaction {

            val transaction =
                transactionDao.getById(transactionId)
                    ?: throw IllegalArgumentException(
                        "Transaction not found"
                    )

            when (transaction.type) {

                TransactionType.INCOME -> {

                    val balance =
                        accountDao.getBalance(
                            transaction.accountId
                        ) ?: throw IllegalArgumentException(
                            "Account not found"
                        )

                    if (balance < transaction.amount) {
                        throw IllegalStateException(
                            "Cannot delete income: insufficient balance"
                        )
                    }

                    accountDao.decreaseBalance(
                        accountId = transaction.accountId,
                        amount = transaction.amount
                    )
                }

                TransactionType.EXPENSE -> {

                    accountDao.getBalance(
                        transaction.accountId
                    ) ?: throw IllegalArgumentException(
                        "Account not found"
                    )

                    accountDao.increaseBalance(
                        accountId = transaction.accountId,
                        amount = transaction.amount
                    )
                }

                TransactionType.TRANSFER -> {
                    throw IllegalStateException(
                        "Transfer must be deleted through TransferDaoRepository"
                    )
                }
            }

            transactionDao.delete(transaction)
        }
    }
}