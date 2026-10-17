package com.x7.dara.ui.components.ui.transaction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.x7.dara.ui.theme.BrandPrimary
import com.x7.dara.ui.theme.Typography

@Composable
fun AccountSelector(
    text : String = "حساب",
    accountName: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        FieldLabel(text)

        Spacer(Modifier.height(12.dp))

        Text(
            text = accountName,
            style = Typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = BrandPrimary
        )
    }
}