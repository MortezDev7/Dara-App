package com.x7.dara.ui.components.ui.assets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.x7.dara.ui.theme.BrandSecondary
import com.x7.dara.utils.money.MoneyUtils.toPersianDigits

@Composable
fun TrendBadge(
    percent: Int,
    modifier: Modifier = Modifier
) {
    val isPositive = percent > 0
    val isNegative = percent < 0

    val trendColor = if (isNegative) {
        Color(0xFFD32F2F)
    } else {
        BrandSecondary
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(
                    RoundedCornerShape(8.dp)
                )
                .background(trendColor),
            contentAlignment = Alignment.Center
        ) {

            Canvas(
                modifier = Modifier.size(16.dp)
            ) {

                val w = size.width
                val h = size.height
                val strokeWidth = 1.6.dp.toPx()

                if (isPositive) {

                    val zigzag = Path().apply {
                        moveTo(w * 0.05f, h * 0.80f)
                        lineTo(w * 0.38f, h * 0.45f)
                        lineTo(w * 0.58f, h * 0.62f)
                        lineTo(w * 0.95f, h * 0.20f)
                    }

                    drawPath(
                        path = zigzag,
                        color = Color.White,
                        style = Stroke(
                            width = strokeWidth,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )

                    drawLine(
                        color = Color.White,
                        start = Offset(
                            w * 0.95f,
                            h * 0.20f
                        ),
                        end = Offset(
                            w * 0.72f,
                            h * 0.20f
                        ),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round
                    )

                    drawLine(
                        color = Color.White,
                        start = Offset(
                            w * 0.95f,
                            h * 0.20f
                        ),
                        end = Offset(
                            w * 0.95f,
                            h * 0.43f
                        ),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round
                    )

                } else if (isNegative) {

                    val zigzag = Path().apply {
                        moveTo(w * 0.05f, h * 0.20f)
                        lineTo(w * 0.38f, h * 0.55f)
                        lineTo(w * 0.58f, h * 0.38f)
                        lineTo(w * 0.95f, h * 0.80f)
                    }

                    drawPath(
                        path = zigzag,
                        color = Color.White,
                        style = Stroke(
                            width = strokeWidth,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )

                    drawLine(
                        color = Color.White,
                        start = Offset(
                            w * 0.95f,
                            h * 0.80f
                        ),
                        end = Offset(
                            w * 0.72f,
                            h * 0.80f
                        ),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round
                    )

                    drawLine(
                        color = Color.White,
                        start = Offset(
                            w * 0.95f,
                            h * 0.80f
                        ),
                        end = Offset(
                            w * 0.95f,
                            h * 0.57f
                        ),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round
                    )
                }
            }
        }

        Spacer(
            Modifier.width(8.dp)
        )

        Text(
            text = "${percent.toString().toPersianDigits()}%",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = trendColor
        )
    }
}
