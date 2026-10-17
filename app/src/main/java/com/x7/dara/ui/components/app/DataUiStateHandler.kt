package com.x7.dara.ui.components.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.x7.dara.ui.state.DataUiState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> DataUiStateHandler(
    state : StateFlow<DataUiState<T>>,
    modifier: Modifier = Modifier,
    loadingHeight: Dp = 60.dp,
    errorHeight : Dp = 60.dp,
    loadingContent: @Composable (() -> Unit)? = null,
    errorContent: @Composable ((String) -> Unit)? = null,
    content: @Composable (T) -> Unit
) {
    val flowState = state.collectAsStateWithLifecycle()
    Box(modifier = modifier) {
        when {
            flowState.value.isLoading -> {
                loadingContent?.invoke() ?: Loading(
                    Modifier
                        .fillMaxWidth()
                        .height(loadingHeight)
                )
            }

            flowState.value.error != null -> {
                errorContent?.invoke(flowState.value.error!!) ?: ErrorBox(
                    flowState.value.error!!,
                    modifier = Modifier.height(errorHeight)
                )
            }

            flowState.value.data != null -> {
                content(flowState.value.data!!)
            }
        }
    }
}