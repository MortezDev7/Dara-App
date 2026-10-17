package com.x7.dara.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.x7.dara.R
import com.x7.dara.model.database.account.AccountEntity
import com.x7.dara.ui.theme.BrandPrimary
import com.x7.dara.ui.theme.BrandPrimaryContainer
import com.x7.dara.ui.theme.ErrorPrimary
import com.x7.dara.ui.theme.SurfacePrimary
import com.x7.dara.ui.theme.Typography

@Composable
fun AccountSelectDialog(
    accounts: List<AccountEntity>,
    selectedId: Long,
    totalBalance: Long,
    onConfirm: (Long) -> Unit,
    onDismiss: () -> Unit,
    showTotal: Boolean = true
) {

    var tempSelectedId by remember(selectedId) {
        mutableLongStateOf(selectedId)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),

            shape = RoundedCornerShape(28.dp),

            color = SurfacePrimary
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Text(
                    text = "انتخاب حساب",
                    style = Typography.titleLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                if (showTotal) {
                    TotalAccountRow(
                        totalBalance = totalBalance,
                        selected = tempSelectedId == -1L,
                        onClick = {
                            tempSelectedId = -1L
                        }
                    )
                }

                accounts.forEach { account ->

                    AccountRow(
                        account = account,
                        selected = account.id == tempSelectedId,
                        onClick = {
                            tempSelectedId = account.id
                        }
                    )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Row {

                    TextButton(
                        onClick = {
                            onConfirm(tempSelectedId)
                        }
                    ) {
                        Text(
                            text = "انتخاب",
                            color = BrandPrimary,
                            style = Typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text(
                            text = "بستن",
                            color = ErrorPrimary,
                            style = Typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TotalAccountRow(
    totalBalance: Long,
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
            onClick = onClick
        )

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        Box(
            modifier = Modifier
                .size(42.dp)
                .background(
                    color = BrandPrimaryContainer,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                painter = painterResource(R.drawable.kol),
                contentDescription = null,
                tint = BrandPrimary,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Column {

            Text(
                text = "کل",
                style = Typography.titleMedium,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = formatAmount(totalBalance),
                    style = Typography.bodyMedium,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    Modifier.width(8.dp)
                )

                Text(
                    text = "ریال",
                    style = Typography.bodyMedium,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold

                )
            }
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