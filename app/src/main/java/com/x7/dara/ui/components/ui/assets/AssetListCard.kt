package com.x7.dara.ui.components.ui.assets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.x7.dara.ui.theme.Neutral30
import com.x7.dara.ui.theme.SurfaceSecondary

@Composable
fun AssetListCard(
    items: List<AssetItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceSecondary)
    ) {
        items.forEachIndexed { index, item ->
            AssetRow(item = item)

            if (index < items.lastIndex) {
                HorizontalDivider(
                    color = Neutral30,
                    thickness = 1.dp,
                    modifier = Modifier.padding(start = 56.dp, end = 16.dp)
                )
            }
        }
    }
}