package com.x7.dara.ui.components.ui.home


import com.x7.dara.model.database.transaction.TransactionEntity

internal data class CategoryTotal(
    val categoryId: Long,
    val amount: Long
)

internal fun List<TransactionEntity>.groupTransactionsByCategory(): List<CategoryTotal> {
    return groupBy { it.categoryId }
        .map { (categoryId, items) ->
            CategoryTotal(
                categoryId = categoryId,
                amount = items.sumOf { it.amount }
            )
        }
        .filter { it.amount > 0L }
        .sortedByDescending { it.amount }
}