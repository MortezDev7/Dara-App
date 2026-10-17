package com.x7.dara.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.x7.dara.model.database.account.AccountEntity
import com.x7.dara.ui.theme.Typography

@Composable
fun AccountRow(
    account: AccountEntity,
    selected: Boolean,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        RadioButton(
            selected = selected,
            onClick = { onClick() }
        )

        Spacer(
            modifier = Modifier.width(8.dp)
        )


        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = Color.DarkGray,
            modifier = Modifier.size(22.dp)
        )
    }

    Spacer(
        modifier = Modifier.width(12.dp)
    )

    Column {
        Text(
            text = account.name,
            style = Typography.titleMedium,
            color = Color.Black
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = formatAmount(account.balance),
                style = Typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = account.currency,
                style = Typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

private fun formatAmount(amount: Long): String {
    return amount
        .toString()
        .reversed()
        .chunked(3)
        .joinToString("٬")
        .reversed()
}