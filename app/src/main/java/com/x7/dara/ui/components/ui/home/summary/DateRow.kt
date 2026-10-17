package com.x7.dara.ui.components.ui.home.summary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.x7.dara.ui.theme.IconSecondary
import com.x7.dara.ui.theme.TextPrimary

@Composable
fun DateRow(
    dateText: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp)
    ) {
        Text(
            text = dateText,
            fontSize = 14.sp,
            color = TextPrimary,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}