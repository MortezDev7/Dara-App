package com.x7.dara.ui.components.app.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.x7.dara.ui.theme.BrandSecondary

@Composable
fun AppHeader(
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit)? = null,
    title: @Composable () -> Unit = {},
    actions: (@Composable () -> Unit)? = null,
    centerTitle: Boolean = true,
    height: Dp? = null,
    topSpace: Dp = 8.dp,
    overlap: Dp = 0.dp,
    bottomSpace: Dp = 0.dp,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val heightModifier = if (height != null) {
        Modifier.height(height + statusBarHeight)
    } else {
        Modifier
    }

    val bottomPadding = if (height != null) 0.dp else bottomSpace + overlap

    Column(
        modifier = modifier
            .then(heightModifier)
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
            .background(BrandSecondary)
            .statusBarsPadding()
            .padding(top = topSpace, bottom = bottomPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderTopRow(
            navigationIcon = navigationIcon,
            title = title,
            actions = actions,
            centerTitle = centerTitle
        )

        content()
    }
}

@Composable
private fun HeaderTopRow(
    navigationIcon: (@Composable () -> Unit)?,
    title: @Composable () -> Unit,
    actions: (@Composable () -> Unit)?,
    centerTitle: Boolean
) {
    if (centerTitle) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .padding(horizontal = 8.dp)
        ) {
            if (navigationIcon != null) {
                Box(Modifier.align(Alignment.CenterStart)) { navigationIcon() }
            }
            Box(Modifier.align(Alignment.Center)) { title() }
            if (actions != null) {
                Box(Modifier.align(Alignment.CenterEnd)) { actions() }
            }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (navigationIcon != null) navigationIcon()

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = if (navigationIcon == null) 8.dp else 0.dp),
                contentAlignment = Alignment.CenterStart
            ) { title() }

            if (actions != null) actions()
        }
    }
}