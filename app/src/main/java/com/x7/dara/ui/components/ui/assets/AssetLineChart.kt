package com.x7.dara.ui.components.ui.assets

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import com.x7.dara.ui.theme.BrandPrimary
import com.x7.dara.ui.theme.SuccessPrimary

@Composable
fun AssetsLineChart(
    values: List<Long>,
    modifier: Modifier = Modifier,
    lineColor: Color = SuccessPrimary,
    dotColor: Color = BrandPrimary
) {
    val progress = remember { Animatable(0f) }
    LaunchedEffect(values) {
        progress.snapTo(0f)
        progress.animateTo(1f, tween(durationMillis = 900))
    }

    Canvas(modifier = modifier) {
        if (values.size < 2) return@Canvas

        val startInset = 4.dp.toPx()
        val endInset = 16.dp.toPx()
        val topInset = 56.dp.toPx()
        val bottomInset = 12.dp.toPx()

        val chartWidth = size.width - startInset - endInset
        val chartHeight = size.height - topInset - bottomInset

        val minValue = values.minOrNull() ?: 0L
        val maxValue = values.maxOrNull() ?: 0L
        val range = (maxValue - minValue).coerceAtLeast(1L).toFloat()

        val points = values.mapIndexed { index, value ->
            Offset(
                x = startInset + chartWidth * index / (values.size - 1),
                y = topInset + chartHeight * (1f - (value - minValue) / range)
            )
        }

        val linePath = Path().apply {
            moveTo(points.first().x, points.first().y)
            points.drop(1).forEach { lineTo(it.x, it.y) }
        }

        val areaPath = Path().apply {
            moveTo(points.first().x, size.height)
            points.forEach { lineTo(it.x, it.y) }
            lineTo(points.last().x, size.height)
            close()
        }

        val topY = points.minOf { it.y }

        clipRect(right = size.width * progress.value) {
            drawPath(
                path = areaPath,
                brush = Brush.verticalGradient(
                    colors = listOf(lineColor.copy(alpha = 0.35f), Color.Transparent),
                    startY = topY,
                    endY = size.height
                )
            )
            drawPath(
                path = linePath,
                color = lineColor,
                style = Stroke(
                    width = 2.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }

        if (progress.value >= 0.99f) {
            drawCircle(
                color = dotColor,
                radius = 5.dp.toPx(),
                center = points.last()
            )
        }
    }
}