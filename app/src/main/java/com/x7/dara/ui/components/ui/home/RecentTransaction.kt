package com.x7.dara.ui.components.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.x7.dara.model.database.transaction.TransactionEntity
import com.x7.dara.model.database.transaction.TransactionType
import com.x7.dara.ui.theme.BrandPrimary
import com.x7.dara.ui.theme.ErrorPrimary
import com.x7.dara.ui.theme.Neutral50
import com.x7.dara.ui.theme.SurfaceSecondary
import com.x7.dara.ui.theme.SuccessPrimary
import com.x7.dara.ui.theme.TextPrimary
import com.x7.dara.ui.theme.TextTertiary
import com.x7.dara.utils.money.MoneyUtils.formatAmount

data class RecentTransactionItem(
    val transaction: TransactionEntity,
    val title: String,
    val iconRes: Int,
    val accountName: String,
    val dateText: String
)

@Composable
fun RecentTransactions(
    transactions: List<RecentTransactionItem>,
    onTransactionClick: (TransactionEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        transactions.forEach { item ->
            RecentTransactionRow(
                item = item,
                onClick = {
                    onTransactionClick(item.transaction)
                }
            )
        }
    }
}

@Composable
private fun RecentTransactionRow(
    item: RecentTransactionItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isIncome = item.transaction.type == TransactionType.INCOME

    val amountColor =
        if (isIncome) {
            SuccessPrimary
        } else {
            ErrorPrimary
        }

    val amountPrefix =
        if (isIncome) {
            "+"
        } else {
            "-"
        }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceSecondary)
            .clickable(onClick = onClick)
            .padding(
                horizontal = 16.dp,
                vertical = 14.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(item.iconRes),
            contentDescription = item.title,
            tint = BrandPrimary,
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Neutral50)
                .padding(9.dp)
        )

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Text(
                text = "${item.accountName} • ${item.dateText}",
                fontSize = 12.sp,
                color = TextTertiary
            )
        }

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Text(
            text = "$amountPrefix${item.transaction.amount.formatAmount()} تومان",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = amountColor
        )
    }
}
