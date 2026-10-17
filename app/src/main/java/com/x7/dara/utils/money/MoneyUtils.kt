package com.x7.dara.utils.money

import java.util.Locale

object MoneyUtils {

    fun Long.formatAmount(): String =
        String.format(Locale.US, "%,d", this).toPersianDigits()

    fun String.toPersianDigits(): String =
        map { if (it in '0'..'9') '۰' + (it - '0') else it }.joinToString("")
    fun normalizeDigits(value: String): String {
        return value
            .replace('۰', '0')
            .replace('۱', '1')
            .replace('۲', '2')
            .replace('۳', '3')
            .replace('۴', '4')
            .replace('۵', '5')
            .replace('۶', '6')
            .replace('۷', '7')
            .replace('۸', '8')
            .replace('۹', '9')
            .replace('٠', '0')
            .replace('١', '1')
            .replace('٢', '2')
            .replace('٣', '3')
            .replace('٤', '4')
            .replace('٥', '5')
            .replace('٦', '6')
            .replace('٧', '7')
            .replace('٨', '8')
            .replace('٩', '9')
    }
    fun removeSeparators(value: String): String {
        return value
            .replace(",", "")
            .replace("،", "")
            .replace("٬", "")
            .replace(" ", "")
    }

    fun parseAmount(value: String): Long? {
        val normalized =
            removeSeparators(
                normalizeDigits(value)
            )

        return normalized
            .takeIf { it.isNotEmpty() }
            ?.toLongOrNull()
    }

    fun isValidAmount(value: String): Boolean {
        return parseAmount(value)?.let {
            it > 0L
        } == true
    }
}
