package com.x7.dara.ui.state


sealed interface UiEvent {

    data object Success : UiEvent

    data class Error(
        val message: String
    ) : UiEvent
}
