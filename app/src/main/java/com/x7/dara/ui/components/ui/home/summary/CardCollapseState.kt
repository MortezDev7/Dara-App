package com.x7.dara.ui.components.ui.home.summary

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource

@Stable
class CardCollapseState {

    var collapsed by mutableStateOf(false)
        private set

    val connection = object : NestedScrollConnection {

        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            if (available.y < -COLLAPSE_THRESHOLD_PX) collapsed = true
            return Offset.Zero
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            if (available.y > 0f) collapsed = false
            return Offset.Zero
        }
    }

    private companion object {
        const val COLLAPSE_THRESHOLD_PX = 4f
    }
}

@Composable
fun rememberCardCollapseState(): CardCollapseState = remember { CardCollapseState() }