package com.x7.dara.ui.components.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.x7.dara.model.database.transaction.TransactionType
import com.x7.dara.ui.components.ui.home.summary.CategoryPalette
import com.x7.dara.ui.components.ui.home.summary.SummaryContent
import com.x7.dara.utils.PersianDateUtils
import com.x7.dara.viewmodel.local.CategoryViewModel
import com.x7.dara.viewmodel.local.TransactionViewModel

@Composable
fun ExpenseContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    selectedAccountId: Long,
    transactionViewModel: TransactionViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    var periodIndex by remember {
        mutableIntStateOf(0)
    }

    val transactionState by
    transactionViewModel.uiState.collectAsStateWithLifecycle()

    val categoryState by
    categoryViewModel.uiState.collectAsStateWithLifecycle()

    val transactions = transactionState.data.orEmpty()

    val categories = categoryState.data.orEmpty()

    val periodRange = PersianDateUtils.getPeriodRange(
        periodIndex = periodIndex
    )

    val filteredTransactions = transactions.filter { transaction ->
        transaction.type == TransactionType.EXPENSE &&
                transaction.date in
                periodRange.startMillis..periodRange.endMillis &&
                (
                        selectedAccountId == -1L ||
                                transaction.accountId == selectedAccountId
                        )
    }

    val dateText = PersianDateUtils.getPeriodTitle(
        periodIndex = periodIndex
    )

    val emptyText = PersianDateUtils.getEmptyText(
        periodIndex = periodIndex,
        isIncome = false
    )

    val categoryColors: Map<Long, Color> = categories.associate { category ->
        val colorIndex = kotlin.math.abs(category.id.hashCode())
            .mod(CategoryPalette.size)

        category.id to CategoryPalette[colorIndex]
    }

    SummaryContent(
        modifier = modifier,
        periodIndex = periodIndex,
        onPeriodChange = { periodIndex = it },
        dateText = dateText,
        emptyText = emptyText,
        transactions = filteredTransactions.sortedByDescending { it.date },
        categoryNames = categories.associate { it.id to it.name },
        categoryIcons = categories.associate { it.id to it.icon },
        categoryColors = categoryColors,
        onAddClick = {
            navController.navigate("addTransactionScreen/1")
        },
        onTransactionClick = { transaction ->
            navController.navigate(
                "transactionDetailScreen/${transaction.id}"
            )
        }
    )
}