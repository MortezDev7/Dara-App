package com.x7.dara.ui.components.ui.home.summary

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.x7.dara.model.database.transaction.TransactionEntity
import com.x7.dara.ui.components.app.AddButton
import com.x7.dara.ui.theme.Neutral30
import com.x7.dara.ui.theme.SurfacePrimary
import com.x7.dara.ui.theme.TextPrimary
import com.x7.dara.utils.money.MoneyUtils.formatAmount

private val ExpandedChartHeight = 220.dp
private val CollapsedChartHeight = 72.dp

@Composable
fun SummaryCard(
    selectedPeriod: Int,
    onPeriodChange: (Int) -> Unit,
    dateText: String,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    transactions: List<TransactionEntity> = emptyList(),
    categoryColors: Map<Long, Color> = emptyMap(),
    collapseProgress: Float = 0f
) {
    val p = if (transactions.isEmpty()) {
        0f
    } else {
        collapseProgress.coerceIn(0f, 1f)
    }

    val chartHeight =
        ExpandedChartHeight + (CollapsedChartHeight - ExpandedChartHeight) * p

    val donutScale = 1f - 0.7f * p
    val donutAlpha = (1f - 2f * p).coerceIn(0f, 1f)
    val barAlpha = (2f * p - 1f).coerceIn(0f, 1f)
    val buttonScale = 1f - 0.35f * p

    val total = transactions.sumOf { it.amount }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfacePrimary
        ),
        border = BorderStroke(1.dp, Neutral30),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PeriodTabs(
                titles = listOf("روز", "هفته", "ماه", "سال"),
                selectedIndex = selectedPeriod,
                onSelect = onPeriodChange
            )

            Spacer(Modifier.height(20.dp))

            DateRow(dateText = dateText)

            Spacer(Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(chartHeight)
                    .clipToBounds()
            ) {
                if (transactions.isEmpty()) {
                    EmptyDonut(
                        text = text,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    CategoryDonut(
                        transactions = transactions,
                        categoryColors = categoryColors,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .graphicsLayer {
                                scaleX = donutScale
                                scaleY = donutScale
                                alpha = donutAlpha
                            }
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth()
                            .padding(top = 6.dp)
                            .graphicsLayer {
                                alpha = barAlpha
                            }
                    ) {
                        SegmentedBar(
                            transactions = transactions,
                            categoryColors = categoryColors
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = "${total.formatAmount()} ریال",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextPrimary
                        )
                    }
                }

                AddButton(
                    onClick = onClick,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .graphicsLayer {
                            scaleX = buttonScale
                            scaleY = buttonScale
                            transformOrigin = TransformOrigin(1f, 1f)
                        }
                )
            }
        }
    }
}