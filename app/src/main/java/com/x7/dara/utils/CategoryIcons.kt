package com.x7.dara.utils

import androidx.annotation.DrawableRes
import com.x7.dara.R

object CategoryIcons {

    @DrawableRes
    fun drawable(iconKey: String): Int {
        return when (iconKey) {
            "health" -> R.drawable.health
            "emoji_happy" -> R.drawable.emoji_happy
            "task_square" -> R.drawable.task_square
            "home_outlined" -> R.drawable.home_outlined
            "buy_crypto" -> R.drawable.buy_crypto
            "teacher" -> R.drawable.teacher
            "gift" -> R.drawable.gift
            "more_2" -> R.drawable.more_2
            "money_change" -> R.drawable.money_change
            else -> R.drawable.more_2
        }
    }
}
