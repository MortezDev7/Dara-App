package com.x7.dara.ui.components.app.header

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
    @Composable
    fun AppTabs(
        firstTitle : String = "دخل",
        secondTitle : String = "خرج",
        selectedIndex: Int,
        onSelect: (Int) -> Unit
    ) {

        val tabPositions = remember {
            mutableStateListOf(0, 0)
        }

        val indicatorX by animateIntAsState(
            targetValue = tabPositions[selectedIndex],
            animationSpec = tween(durationMillis = 300),
            label = "indicatorX"
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {

                HeaderTab(
                    title = firstTitle,
                    selected = selectedIndex == 0,
                    onClick = {
                        onSelect(0)
                    },
                    modifier = Modifier.onGloballyPositioned {

                        tabPositions[0] =
                            it.positionInParent().x.roundToInt()
                    }
                )

                HeaderTab(
                    title = secondTitle,
                    selected = selectedIndex == 1,
                    onClick = {
                        onSelect(1)
                    },
                    modifier = Modifier.onGloballyPositioned {

                        tabPositions[1] =
                            it.positionInParent().x.roundToInt()
                    }
                )
            }

            Box(
                modifier = Modifier
                    .align(AbsoluteAlignment.BottomLeft)
                    .absoluteOffset {
                        IntOffset(indicatorX, 0)
                    }
                    .width(136.dp)
                    .height(2.dp)
                    .background(Color.White)
            )
        }
    }