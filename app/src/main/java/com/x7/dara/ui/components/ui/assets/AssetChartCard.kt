package com.x7.dara.ui.components.ui.assets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.x7.dara.ui.theme.Neutral70
import com.x7.dara.ui.theme.SuccessContainer
import com.x7.dara.ui.theme.SurfaceSecondary
import com.x7.dara.utils.money.MoneyUtils.formatAmount

@Composable
fun AssetsChartCard(
    totalValue: Long,
    percentChange: Int,
    chartValues: List<Long>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(190.dp)
            .clip(RoundedCornerShape(24.dp))
            .drawBehind {
                drawRect(
                    brush = Brush.linearGradient(
                        colorStops = arrayOf(
                            0f to SuccessContainer,
                            0.5f to SurfaceSecondary,
                            1f to SuccessContainer
                        ),
                        start = Offset(size.width, 0f),
                        end = Offset(0f, size.height)
                    )
                )
            }
    ) {
        AssetsLineChart(
            values = chartValues,
            modifier = Modifier.fillMaxSize()
        )

        Text(
            text = "${totalValue.formatAmount()} ریال",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Neutral70,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        )

        TrendBadge(
            percent = percentChange,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(18.dp)
        )
    }
}