package com.x7.dara.ui.state

data class DataUiState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: String? = null
)
