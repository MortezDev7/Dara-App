package com.x7.dara.ui.components.ui.home

import android.R
import com.x7.dara.model.database.account.AccountEntity
import com.x7.dara.model.database.transaction.CategoryEntity
import com.x7.dara.model.database.transaction.TransactionEntity

fun TransactionEntity.toRecentTransactionItem(
    account: AccountEntity?,
    category: CategoryEntity?,
    dateText: String
): RecentTransactionItem {

    return RecentTransactionItem(
        transaction = this,
        title = category?.name ?: "دسته‌بندی نامشخص",
        iconRes = category?.icon ?: R.drawable.ic_menu_help,
        accountName = account?.name ?: "حساب نامشخص",
        dateText = dateText
    )
}
