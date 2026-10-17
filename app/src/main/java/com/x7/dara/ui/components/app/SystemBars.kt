package com.x7.dara.ui.components.app

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

@Composable
fun SystemBars(
    navigationBarColor: Color,
    statusBarColor: Color = Color.Transparent
) {
    val view = LocalView.current
    val window = (view.context as Activity).window
    SideEffect {
        window.navigationBarColor = navigationBarColor.toArgb()
        window.statusBarColor = statusBarColor.toArgb()
    }
}