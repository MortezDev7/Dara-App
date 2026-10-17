package com.x7.dara.ui.components.ui.transaction

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.x7.dara.ui.theme.TextSecondary

@Composable
fun FieldLabel(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 14.sp,
        color = TextSecondary,
        modifier = modifier,
        fontWeight = FontWeight.Bold
    )
}