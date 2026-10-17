package com.x7.dara.ui.components.ui.assets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.x7.dara.model.database.asset.AssetEntity
import com.x7.dara.ui.theme.BrandPrimary
import com.x7.dara.ui.theme.ErrorPrimary
import com.x7.dara.ui.theme.SurfacePrimary
import com.x7.dara.ui.theme.Typography

@Composable
fun AssetSelectDialog(
    assets: List<AssetEntity>,
    selectedId: Long,
    onConfirm: (Long) -> Unit,
    onDismiss: () -> Unit
) {

    var tempSelectedId by remember(
        selectedId
    ) {
        mutableLongStateOf(
            selectedId
        )
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
                .padding(
                    horizontal = 16.dp
                ),

            shape = RoundedCornerShape(
                28.dp
            ),

            color = SurfacePrimary
        ) {

            Column(
                modifier = Modifier.padding(
                    24.dp
                )
            ) {

                Text(
                    text = "انتخاب دارایی",
                    style = Typography.titleLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(
                        16.dp
                    )
                )

                assets.forEach { asset ->

                    AssetRow(
                        asset = asset,
                        selected = asset.id == tempSelectedId,
                        onClick = {
                            tempSelectedId = asset.id
                        }
                    )
                }

                Spacer(
                    modifier = Modifier.height(
                        16.dp
                    )
                )

                Row {

                    TextButton(
                        onClick = {
                            onConfirm(
                                tempSelectedId
                            )
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
private fun AssetRow(
    asset: AssetEntity,
    selected: Boolean,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            )
            .padding(
                vertical = 10.dp
            ),

        verticalAlignment = Alignment.CenterVertically
    ) {

        RadioButton(
            selected = selected,
            onClick = onClick
        )

        Spacer(
            modifier = Modifier.width(
                8.dp
            )
        )

        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = Color.DarkGray,
            modifier = Modifier.size(
                22.dp
            )
        )

        Spacer(
            modifier = Modifier.width(
                12.dp
            )
        )

        Column {

            Text(
                text = asset.name,
                style = Typography.titleMedium,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(
                    2.dp
                )
            )

            Text(
                text = "${asset.quantity} ${asset.unit}",
                style = Typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}
