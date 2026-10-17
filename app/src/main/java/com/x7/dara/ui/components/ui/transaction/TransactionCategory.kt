package com.x7.dara.ui.components.ui.transaction

import androidx.annotation.DrawableRes


data class TransactionCategory(
    val id: Int,
    val title: String,
    @DrawableRes val iconRes: Int? = null
)