package com.x7.dara.ui.components.ui.home.summary

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.x7.dara.model.database.transaction.TransactionEntity
import com.x7.dara.ui.components.ui.home.groupTransactionsByCategory
import com.x7.dara.ui.theme.TextPrimary
import com.x7.dara.utils.money.MoneyUtils.formatAmount

@Composable
fun CategoryDonut(
    transactions: List<TransactionEntity>,
    categoryColors: Map<Long, Color>,
    modifier: Modifier = Modifier
) {
    val categoryTotals = transactions.groupTransactionsByCategory()
    val rawTotal = transactions.sumOf { it.amount }
    val total = rawTotal.coerceAtLeast(1L)

    val progress = remember { Animatable(0f) }

    LaunchedEffect(transactions) {
        progress.snapTo(0f)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 700)
        )
    }

    Box(
        modifier = modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val ringStroke = 25.dp.toPx()
            val rimStroke = 5.dp.toPx()

            val ringInset = ringStroke / 2
            val rimInset = ringStroke + rimStroke / 2

            var startAngle = -90f

            categoryTotals.forEach { item ->
                val color = categoryColors[item.categoryId] ?: Color.Gray
                val sweep = item.amount.toFloat() /
                        total.toFloat() * 360f * progress.value

                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = Offset(ringInset, ringInset),
                    size = Size(
                        size.width - ringStroke,
                        size.height - ringStroke
                    ),
                    style = Stroke(width = ringStroke)
                )

                drawArc(
                    color = lerp(color, Color.Black, 0.3f),
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = Offset(rimInset, rimInset),
                    size = Size(
                        size.width - 2 * rimInset,
                        size.height - 2 * rimInset
                    ),
                    style = Stroke(width = rimStroke)
                )

                startAngle += sweep
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = rawTotal.formatAmount(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Text(
                text = "ریال",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
    }
}