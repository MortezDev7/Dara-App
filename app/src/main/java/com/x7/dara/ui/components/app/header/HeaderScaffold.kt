package com.x7.dara.ui.components.app.header

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HeaderScaffold(
    modifier: Modifier = Modifier,
    overlap: Dp = 0.dp,
    header: @Composable (overlap: Dp) -> Unit,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = {
            Box { header(overlap) }
            Box { content() }
        }
    ) { measurables, constraints ->

        val width = constraints.maxWidth
        val overlapPx = overlap.roundToPx()

        val headerPlaceable = measurables[0].measure(
            Constraints(
                minWidth = width,
                maxWidth = width,
                minHeight = 0,
                maxHeight = constraints.maxHeight
            )
        )

        val bodyTop = (headerPlaceable.height - overlapPx).coerceAtLeast(0)

        val bodyMaxHeight = if (constraints.hasBoundedHeight) {
            (constraints.maxHeight - bodyTop).coerceAtLeast(0)
        } else {
            Constraints.Infinity
        }

        val bodyPlaceable = measurables[1].measure(
            Constraints(
                minWidth = width,
                maxWidth = width,
                minHeight = 0,
                maxHeight = bodyMaxHeight
            )
        )

        val totalHeight = if (constraints.hasBoundedHeight) {
            constraints.maxHeight
        } else {
            bodyTop + bodyPlaceable.height
        }

        layout(width, totalHeight) {
            headerPlaceable.place(0, 0)
            bodyPlaceable.place(0, bodyTop, zIndex = 1f)
        }
    }
}