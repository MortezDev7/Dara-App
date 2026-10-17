package com.x7.dara.ui.components.ui.home.summary

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.x7.dara.model.database.transaction.TransactionEntity
import com.x7.dara.ui.components.ui.transaction.TransactionRow

@Composable
fun SummaryContent(
    periodIndex: Int,
    onPeriodChange: (Int) -> Unit,
    dateText: String,
    emptyText: String,
    transactions: List<TransactionEntity>,
    categoryNames: Map<Long, String>,
    categoryIcons: Map<Long, Int?>,
    categoryColors: Map<Long, Color>,
    onAddClick: () -> Unit,
    onTransactionClick: (TransactionEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val collapseState = rememberCardCollapseState()

    val collapseProgress by animateFloatAsState(
        targetValue = if (collapseState.collapsed) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "cardCollapse"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(collapseState.connection)
    ) {
        SummaryCard(
            selectedPeriod = periodIndex,
            onPeriodChange = onPeriodChange,
            dateText = dateText,
            text = emptyText,
            onClick = onAddClick,
            transactions = transactions,
            categoryColors = categoryColors,
            collapseProgress = collapseProgress,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 12.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = transactions,
                key = { transaction -> transaction.id }
            ) { transaction ->
                TransactionRow(
                    transaction = transaction,
                    categoryName = categoryNames[
                        transaction.categoryId
                    ].orEmpty(),
                    iconRes = categoryIcons[
                        transaction.categoryId
                    ],
                    iconColor = categoryColors[
                        transaction.categoryId
                    ] ?: Color.Gray,
                    onClick = {
                        onTransactionClick(transaction)
                    }
                )
            }
        }
    }
}
