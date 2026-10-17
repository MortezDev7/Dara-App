package com.x7.dara.repository.local

import com.x7.dara.dao.AccountDao
import com.x7.dara.model.database.account.AccountEntity
import com.x7.dara.model.database.transaction.CategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountDaoRepository @Inject constructor (
    private val accountDao: AccountDao
) {

    suspend fun insert(account: AccountEntity): Long {
        return accountDao.insert(account)
    }

    suspend fun update(account: AccountEntity) {
        accountDao.update(account)
    }

    suspend fun delete(account: AccountEntity) {
        accountDao.delete(account)
    }

    fun getAll(): Flow<List<AccountEntity>> {
        return accountDao.getAll()
    }

    suspend fun getById(accountId: Long): AccountEntity? {
        return accountDao.getById(accountId)
    }

    suspend fun increaseBalance(
        accountId: Long,
        amount: Long
    ) {
        accountDao.increaseBalance(accountId, amount)
    }

    suspend fun decreaseBalance(
        accountId: Long,
        amount: Long
    ) {
        accountDao.decreaseBalance(accountId, amount)
    }

    suspend fun insertIfNotExists(
        account : AccountEntity
    ): Long {

        val existingAccount =
            accountDao.getByNameAndType(
                name = account.name,
            )

        if (existingAccount != null) {
            return existingAccount.id
        }

        return accountDao.insert(account)
    }
}