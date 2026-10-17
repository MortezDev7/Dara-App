package com.x7.dara.repository.local

import androidx.room.withTransaction
import com.x7.dara.config.DaraDatabase
import com.x7.dara.dao.AccountDao
import com.x7.dara.dao.TransactionDao
import com.x7.dara.dao.TransferDao
import com.x7.dara.model.database.transaction.TransactionEntity
import com.x7.dara.model.database.transaction.TransactionType
import com.x7.dara.model.database.transaction.TransferEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransferDaoRepository @Inject constructor(
    private val transactionDao: TransactionDao,
    private val transferDao: TransferDao,
    private val accountDao: AccountDao,
    private val daraDatabase: DaraDatabase
) {

    suspend fun addTransfer(
        fromAccountId: Long,
        toAccountId: Long,
        categoryId: Long,
        amount: Long,
        description: String?,
        date: Long
    ): Long {

        require(amount > 0) {
            "Amount must be greater than zero"
        }

        require(fromAccountId != toAccountId) {
            "Source and destination accounts must be different"
        }

        return daraDatabase.withTransaction {

            val fromBalance =
                accountDao.getBalance(fromAccountId)
                    ?: throw IllegalArgumentException(
                        "Source account not found"
                    )

            accountDao.getBalance(toAccountId)
                ?: throw IllegalArgumentException(
                    "Destination account not found"
                )

            if (fromBalance < amount) {
                throw IllegalStateException(
                    "Insufficient balance"
                )
            }

            val transaction = TransactionEntity(
                accountId = fromAccountId,
                categoryId = categoryId,
                type = TransactionType.TRANSFER,
                amount = amount,
                description = description,
                date = date,
                createdAt = System.currentTimeMillis()
            )

            val transactionId =
                transactionDao.insert(transaction)

            val transfer = TransferEntity(
                transactionId = transactionId,
                fromAccountId = fromAccountId,
                toAccountId = toAccountId
            )

            transferDao.insert(transfer)

            accountDao.decreaseBalance(
                accountId = fromAccountId,
                amount = amount
            )

            accountDao.increaseBalance(
                accountId = toAccountId,
                amount = amount
            )

            transactionId
        }
    }

    suspend fun getById(
        transferId: Long
    ): TransferEntity? {
        return transferDao.getById(transferId)
    }

    suspend fun getByTransactionId(
        transactionId: Long
    ): TransferEntity? {
        return transferDao.getByTransactionId(transactionId)
    }

    fun getByAccount(
        accountId: Long
    ): Flow<List<TransferEntity>> {
        return transferDao.getByAccount(accountId)
    }

    suspend fun delete(
        transferId: Long
    ) {

        daraDatabase.withTransaction {

            val transfer =
                transferDao.getById(transferId)
                    ?: throw IllegalArgumentException(
                        "Transfer not found"
                    )

            val transaction =
                transactionDao.getById(
                    transfer.transactionId
                ) ?: throw IllegalArgumentException(
                    "Transaction not found"
                )

            require(transaction.type == TransactionType.TRANSFER) {
                "Invalid transfer transaction"
            }

            accountDao.getBalance(
                transfer.fromAccountId
            ) ?: throw IllegalArgumentException(
                "Source account not found"
            )

            val destinationBalance =
                accountDao.getBalance(
                    transfer.toAccountId
                ) ?: throw IllegalArgumentException(
                    "Destination account not found"
                )

            if (destinationBalance < transaction.amount) {
                throw IllegalStateException(
                    "Cannot delete transfer: insufficient destination balance"
                )
            }

            // برگرداندن اثر انتقال
            accountDao.increaseBalance(
                accountId = transfer.fromAccountId,
                amount = transaction.amount
            )

            accountDao.decreaseBalance(
                accountId = transfer.toAccountId,
                amount = transaction.amount
            )

            transferDao.delete(transfer)

            transactionDao.delete(transaction)
        }
    }
    suspend fun update(
        transferId: Long,
        fromAccountId: Long,
        toAccountId: Long,
        categoryId: Long,
        amount: Long,
        description: String?,
        date: Long
    ) {

        require(amount > 0) {
            "Amount must be greater than zero"
        }

        require(fromAccountId != toAccountId) {
            "Source and destination accounts must be different"
        }

        daraDatabase.withTransaction {

            val oldTransfer =
                transferDao.getById(transferId)
                    ?: throw IllegalArgumentException(
                        "Transfer not found"
                    )

            val oldTransaction =
                transactionDao.getById(
                    oldTransfer.transactionId
                ) ?: throw IllegalArgumentException(
                    "Transaction not found"
                )

            require(
                oldTransaction.type == TransactionType.TRANSFER
            ) {
                "Invalid transfer transaction"
            }

            // بررسی حساب‌های جدید
            accountDao.getBalance(fromAccountId)
                ?: throw IllegalArgumentException(
                    "New source account not found"
                )

            accountDao.getBalance(toAccountId)
                ?: throw IllegalArgumentException(
                    "New destination account not found"
                )

            // --------------------------------
            // 1. برگرداندن Transfer قبلی
            // --------------------------------

            accountDao.increaseBalance(
                accountId = oldTransfer.fromAccountId,
                amount = oldTransaction.amount
            )

            val oldDestinationBalance =
                accountDao.getBalance(
                    oldTransfer.toAccountId
                ) ?: throw IllegalArgumentException(
                    "Old destination account not found"
                )

            if (oldDestinationBalance < oldTransaction.amount) {
                throw IllegalStateException(
                    "Cannot update transfer: insufficient old destination balance"
                )
            }

            accountDao.decreaseBalance(
                accountId = oldTransfer.toAccountId,
                amount = oldTransaction.amount
            )

            // --------------------------------
            // 2. بررسی موجودی Transfer جدید
            // --------------------------------

            val newSourceBalance =
                accountDao.getBalance(fromAccountId)
                    ?: throw IllegalArgumentException(
                        "New source account not found"
                    )

            if (newSourceBalance < amount) {
                throw IllegalStateException(
                    "Insufficient balance"
                )
            }

            // --------------------------------
            // 3. اعمال Transfer جدید
            // --------------------------------

            accountDao.decreaseBalance(
                accountId = fromAccountId,
                amount = amount
            )

            accountDao.increaseBalance(
                accountId = toAccountId,
                amount = amount
            )

            // --------------------------------
            // 4. Update Transaction
            // --------------------------------

            val updatedTransaction =
                oldTransaction.copy(
                    accountId = fromAccountId,
                    categoryId = categoryId,
                    amount = amount,
                    description = description,
                    date = date
                )

            transactionDao.update(
                updatedTransaction
            )

            // --------------------------------
            // 5. Update Transfer
            // --------------------------------

            val updatedTransfer =
                oldTransfer.copy(
                    fromAccountId = fromAccountId,
                    toAccountId = toAccountId
                )

            transferDao.update(
                updatedTransfer
            )
        }
    }
}