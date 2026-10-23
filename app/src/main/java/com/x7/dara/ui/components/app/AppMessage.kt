package com.x7.dara.ui.components.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.sign

enum class AppMessageType {
    SUCCESS,
    ERROR,
    WARNING,
    INFO
}

data class AppMessageData(
    val message: String,
    val type: AppMessageType = AppMessageType.INFO,
    val durationMillis: Long = 2000L,
    val title: String? = null
)

private data class MessageStyle(
    val background: Color,
    val foreground: Color,
    val icon: ImageVector,
    val defaultTitle: String
)

private fun messageStyle(type: AppMessageType): MessageStyle {
    return when (type) {
        AppMessageType.SUCCESS -> MessageStyle(
            background = Color(0xFFE8F5E9),
            foreground = Color(0xFF2E7D32),
            icon = Icons.Filled.CheckCircle,
            defaultTitle = "موفقیت"
        )

        AppMessageType.ERROR -> MessageStyle(
            background = Color(0xFFFFEBEE),
            foreground = Color(0xFFC62828),
            icon = Icons.Filled.Error,
            defaultTitle = "خطا"
        )

        AppMessageType.WARNING -> MessageStyle(
            background = Color(0xFFFFF8E1),
            foreground = Color(0xFF9A6700),
            icon = Icons.Filled.Warning,
            defaultTitle = "توجه"
        )

        AppMessageType.INFO -> MessageStyle(
            background = Color(0xFFE3F2FD),
            foreground = Color(0xFF1565C0),
            icon = Icons.Filled.Info,
            defaultTitle = "اطلاع‌رسانی"
        )
    }
}

@Composable
fun AppMessage(
    message: AppMessageData?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var visible by remember {
        mutableStateOf(false)
    }

    val offsetX = remember {
        Animatable(0f)
    }

    val coroutineScope = rememberCoroutineScope()

    var dismissJob by remember {
        mutableStateOf<Job?>(null)
    }

    LaunchedEffect(message) {
        dismissJob?.cancel()

        if (message == null) {
            visible = false
            offsetX.snapTo(0f)
            return@LaunchedEffect
        }

        visible = true
        offsetX.snapTo(0f)

        dismissJob = launch {
            delay(message.durationMillis.coerceAtLeast(0L))

            visible = false

            delay(250L)

            onDismiss()
        }
    }

    if (message == null) return

    val style = messageStyle(message.type)

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(220)
        ) + slideInVertically(
            animationSpec = tween(280),
            initialOffsetY = { it / 2 }
        ),
        exit = fadeOut(
            animationSpec = tween(180)
        ) + slideOutVertically(
            animationSpec = tween(180),
            targetOffsetY = { it / 2 }
        ),
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .graphicsLayer {
                    translationX = offsetX.value

                    alpha = (
                            1f - abs(offsetX.value) / (size.width * 1.5f)
                            ).coerceIn(0f, 1f)
                }
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(18.dp)
                )
                .pointerInput(message) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()

                            coroutineScope.launch {
                                offsetX.snapTo(
                                    offsetX.value + dragAmount
                                )
                            }
                        },
                        onDragEnd = {
                            coroutineScope.launch {
                                val threshold = size.width * 0.25f

                                if (abs(offsetX.value) > threshold) {
                                    // زمان‌سنج خودکار را لغو کن
                                    dismissJob?.cancel()

                                    val targetX =
                                        sign(offsetX.value) *
                                                size.width.toFloat() * 1.5f

                                    // حرکت نرم کارت به بیرون صفحه
                                    offsetX.animateTo(
                                        targetValue = targetX,
                                        animationSpec = tween(
                                            durationMillis = 220
                                        )
                                    )

                                    // محو شدن خود کارت
                                    visible = false

                                    // حذف کامل کامپوننت بعد از انیمیشن خروج
                                    delay(200L)
                                    onDismiss()
                                } else {
                                    // برگشت فنری در صورت سوایپ کوتاه
                                    offsetX.animateTo(
                                        targetValue = 0f,
                                        animationSpec = spring(
                                            dampingRatio = 0.72f,
                                            stiffness = 420f
                                        )
                                    )
                                }
                            }
                        },
                        onDragCancel = {
                            coroutineScope.launch {
                                offsetX.animateTo(
                                    targetValue = 0f,
                                    animationSpec = spring()
                                )
                            }
                        }
                    )
                },
            shape = RoundedCornerShape(18.dp),
            color = style.background
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 14.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = style.icon,
                    contentDescription = null,
                    tint = style.foreground
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = message.title ?: style.defaultTitle,
                        style = MaterialTheme.typography.titleSmall,
                        color = style.foreground
                    )

                    Text(
                        text = message.message,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp
                        ),
                        color = Color(0xFF333333)
                    )
                }
            }
        }
    }
}
