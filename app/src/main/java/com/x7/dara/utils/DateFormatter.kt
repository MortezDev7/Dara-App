package com.x7.dara.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatTransactionDate(timestamp: Long): String {
    val formatter = SimpleDateFormat(
        "yyyy/MM/dd - HH:mm",
        Locale("fa", "IR")
    )
    return formatter.format(Date(timestamp))
}