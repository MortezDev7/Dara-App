package com.x7.dara.ui.components.ui.assets

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
import com.x7.dara.ui.components.ui.transaction.FieldLabel
import com.x7.dara.ui.theme.BrandPrimary
import com.x7.dara.ui.theme.Typography

@Composable
fun AssetSelector(
    assetName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            )
    ) {

        FieldLabel(
            "دارایی"
        )

        Spacer(
            Modifier.height(12.dp)
        )

        Text(
            text = assetName,
            style = Typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = BrandPrimary
        )
    }
}
