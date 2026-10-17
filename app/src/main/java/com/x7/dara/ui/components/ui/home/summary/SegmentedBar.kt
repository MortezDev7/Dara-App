package com.x7.dara.ui.components.ui.home.summary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.x7.dara.model.database.transaction.TransactionEntity
import com.x7.dara.ui.components.ui.home.groupTransactionsByCategory

@Composable
fun SegmentedBar(
    transactions: List<TransactionEntity>,
    categoryColors: Map<Long, Color>,
    modifier: Modifier = Modifier
) {
    val categoryTotals = transactions.groupTransactionsByCategory()

    if (categoryTotals.isEmpty()) return

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(12.dp)
            .clip(RoundedCornerShape(4.dp)),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        categoryTotals.forEach { item ->
            Box(
                modifier = Modifier
                    .weight(item.amount.toFloat().coerceAtLeast(1f))
                    .fillMaxHeight()
                    .background(
                        categoryColors[item.categoryId] ?: Color.Gray
                    )
            )
        }
    }
}