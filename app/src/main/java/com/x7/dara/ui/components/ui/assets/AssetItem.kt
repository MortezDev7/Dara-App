package com.x7.dara.ui.components.ui.assets

import androidx.annotation.DrawableRes

data class AssetItem(
    val id: Int,
    val title: String,
    val unitPrice: Long,
    val totalValue: Long,
    @DrawableRes val iconRes: Int? = null
)