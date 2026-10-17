package com.x7.dara.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.x7.dara.R

// Set of Material typography styles to start with
val estedad = FontFamily(
    Font(R.font.estedad)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = estedad,
        fontWeight = FontWeight(700),
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    displayMedium = TextStyle(
        fontFamily = estedad,
        fontWeight = FontWeight(700),
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    titleLarge = TextStyle(
        fontFamily = estedad,
        fontWeight =FontWeight(700),
        fontSize = 22.sp,
        lineHeight = 30.sp
    ),
    titleMedium = TextStyle(
        fontFamily = estedad,
        fontWeight = FontWeight(500),
        fontSize = 18.sp,
        lineHeight = 26.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = estedad,
        fontWeight = FontWeight(400),
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = estedad,
        fontWeight = FontWeight(400),
        fontSize = 14.sp,
        lineHeight = 22.sp
    ),
    bodySmall = TextStyle(
        fontFamily = estedad,
        fontWeight = FontWeight(400),
        fontSize = 13.sp,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle( // Used for Caption
        fontFamily = estedad,
        fontWeight = FontWeight(300),
        fontSize = 12.sp,
        lineHeight = 18.sp
    ),
    labelSmall = TextStyle( // Used for Overline
        fontFamily = estedad,
        fontWeight = FontWeight(500),
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp // 0.5% approximated or using specific sp
    )
)
