package com.x7.dara.ui.components.app.header

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.Locale
import com.x7.dara.ui.theme.Typography

@Composable
fun HeaderTitle(text: String) {
    Text(
        text = text,
        color = Color.White,
        style = Typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun HeaderDropdownTitle(text: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        HeaderTitle(text)
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "انتخاب موجودی",
            tint = Color.White,
            modifier = Modifier.size(35.dp)
        )
    }
}

@Composable
fun HeaderBackButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "بازگشت",
            tint = Color.White
        )
    }
}

@Composable
fun HeaderIconButton(
    @DrawableRes iconRes: Int,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = contentDescription,
            tint = Color.Unspecified
        )
    }
}


@Composable
fun HeaderBalance(balance: Long) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = String.format(Locale.forLanguageTag("fa"), "%,d", balance),
            color = Color.White,
            style = Typography.titleLarge
        )
        Text(
            text = "ریال",
            color = Color.White,
            style = Typography.titleLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}