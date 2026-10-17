package com.x7.dara.utils

import java.util.Calendar
import java.util.Locale

data class PersianDate(
    val year: Int,
    val month: Int,
    val day: Int
)

data class PersianDateRange(
    val startMillis: Long,
    val endMillis: Long
)

object PersianDateUtils {

    private val monthNames = listOf(
        "فروردین",
        "اردیبهشت",
        "خرداد",
        "تیر",
        "مرداد",
        "شهریور",
        "مهر",
        "آبان",
        "آذر",
        "دی",
        "بهمن",
        "اسفند"
    )

    private val weekDayNames = listOf(
        "شنبه",
        "یکشنبه",
        "دوشنبه",
        "سه‌شنبه",
        "چهارشنبه",
        "پنجشنبه",
        "جمعه"
    )

    /**
     * تبدیل timestamp میلادی به تاریخ شمسی
     */
    fun fromMillis(
        millis: Long
    ): PersianDate {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis

        return gregorianToPersian(
            gy = calendar.get(Calendar.YEAR),
            gm = calendar.get(Calendar.MONTH) + 1,
            gd = calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    /**
     * تبدیل تاریخ میلادی به تاریخ شمسی
     */
    fun gregorianToPersian(
        gy: Int,
        gm: Int,
        gd: Int
    ): PersianDate {

        val gDaysInMonth = intArrayOf(
            31, 28, 31, 30, 31, 30,
            31, 31, 30, 31, 30, 31
        )

        val jDaysInMonth = intArrayOf(
            31, 31, 31, 31, 31, 31,
            30, 30, 30, 30, 30, 29
        )

        val gyValue = gy - 1600
        val gmValue = gm - 1
        val gdValue = gd - 1

        var gDayNo =
            365 * gyValue +
                    (gyValue + 3) / 4 -
                    (gyValue + 99) / 100 +
                    (gyValue + 399) / 400

        for (i in 0 until gmValue) {
            gDayNo += gDaysInMonth[i]
        }

        if (
            gmValue > 1 &&
            (
                    gy % 4 == 0 &&
                            gy % 100 != 0 ||
                            gy % 400 == 0
                    )
        ) {
            gDayNo++
        }

        gDayNo += gdValue

        var jDayNo = gDayNo - 79

        val jNp = jDayNo / 12053
        var jy = 979 + 33 * jNp

        jDayNo %= 12053

        jy += 4 * (jDayNo / 1461)

        jDayNo %= 1461

        if (jDayNo >= 366) {
            jy += (jDayNo - 1) / 365
            jDayNo = (jDayNo - 1) % 365
        }

        var jm = 0

        while (
            jm < 11 &&
            jDayNo >= jDaysInMonth[jm]
        ) {
            jDayNo -= jDaysInMonth[jm]
            jm++
        }

        val jd = jDayNo + 1

        return PersianDate(
            year = jy,
            month = jm + 1,
            day = jd
        )
    }

    /**
     * تبدیل تاریخ شمسی به timestamp میلادی
     *
     * ساعت روی 00:00:00.000 تنظیم می‌شود.
     */
    fun persianToMillis(
        year: Int,
        month: Int,
        day: Int
    ): Long {

        val jDaysInMonth = intArrayOf(
            31, 31, 31, 31, 31, 31,
            30, 30, 30, 30, 30, 29
        )

        val jy = year - 979
        val jm = month - 1
        val jd = day - 1

        var jDayNo =
            365 * jy +
                    jy / 33 * 8 +
                    (jy % 33 + 3) / 4

        for (i in 0 until jm) {
            jDayNo += jDaysInMonth[i]
        }

        jDayNo += jd

        var gDayNo = jDayNo + 79

        var gy = 1600 + 400 * (gDayNo / 146097)

        gDayNo %= 146097

        var leap = true

        if (gDayNo >= 36525) {
            gDayNo--

            gy += 100 * (gDayNo / 36524)

            gDayNo %= 36524

            if (gDayNo >= 365) {
                gDayNo++
            } else {
                leap = false
            }
        }

        gy += 4 * (gDayNo / 1461)

        gDayNo %= 1461

        if (gDayNo >= 366) {
            leap = false
            gDayNo--

            gy += gDayNo / 365
            gDayNo %= 365
        }

        val gDaysInMonth = intArrayOf(
            31,
            if (leap) 29 else 28,
            31,
            30,
            31,
            30,
            31,
            31,
            30,
            31,
            30,
            31
        )

        var gm = 0

        while (
            gm < 12 &&
            gDayNo >= gDaysInMonth[gm]
        ) {
            gDayNo -= gDaysInMonth[gm]
            gm++
        }

        val gd = gDayNo + 1

        return Calendar.getInstance().apply {
            clear()

            set(
                Calendar.YEAR,
                gy
            )

            set(
                Calendar.MONTH,
                gm
            )

            set(
                Calendar.DAY_OF_MONTH,
                gd
            )

            set(
                Calendar.HOUR_OF_DAY,
                0
            )

            set(
                Calendar.MINUTE,
                0
            )

            set(
                Calendar.SECOND,
                0
            )

            set(
                Calendar.MILLISECOND,
                0
            )
        }.timeInMillis
    }

    /**
     * نام ماه شمسی
     */
    fun monthName(
        month: Int
    ): String {
        return monthNames.getOrElse(month - 1) {
            ""
        }
    }

    /**
     * نام روز هفته به فارسی
     */
    fun weekDayName(
        millis: Long
    ): String {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis

        val dayIndex =
            when (calendar.get(Calendar.DAY_OF_WEEK)) {
                Calendar.SATURDAY -> 0
                Calendar.SUNDAY -> 1
                Calendar.MONDAY -> 2
                Calendar.TUESDAY -> 3
                Calendar.WEDNESDAY -> 4
                Calendar.THURSDAY -> 5
                Calendar.FRIDAY -> 6
                else -> 0
            }

        return weekDayNames[dayIndex]
    }

    /**
     * تاریخ کامل
     *
     * مثال:
     * ۱۷ شهریور ۱۴۰۵
     */
    fun formatFull(
        millis: Long
    ): String {

        val date = fromMillis(millis)

        return buildString {
            append(toPersianDigits(date.day))
            append(" ")
            append(monthName(date.month))
            append(" ")
            append(toPersianDigits(date.year))
        }
    }

    /**
     * تاریخ کوتاه
     *
     * مثال:
     * ۱۷ شهریور
     */
    fun formatShort(
        millis: Long
    ): String {

        val date = fromMillis(millis)

        return buildString {
            append(toPersianDigits(date.day))
            append(" ")
            append(monthName(date.month))
        }
    }

    /**
     * تاریخ همراه با روز هفته
     *
     * مثال:
     * سه‌شنبه، ۱۷ شهریور ۱۴۰۵
     */
    fun formatWithWeekDay(
        millis: Long
    ): String {

        return buildString {
            append(weekDayName(millis))
            append("، ")
            append(formatFull(millis))
        }
    }

    /**
     * تبدیل عدد به ارقام فارسی
     */
    fun toPersianDigits(
        value: Int
    ): String {
        return value
            .toString()
            .toPersianDigits()
    }

    fun toPersianDigits(
        value: Long
    ): String {
        return value
            .toString()
            .toPersianDigits()
    }

    fun String.toPersianDigits(): String {
        return map { char ->
            when (char) {
                '0' -> '۰'
                '1' -> '۱'
                '2' -> '۲'
                '3' -> '۳'
                '4' -> '۴'
                '5' -> '۵'
                '6' -> '۶'
                '7' -> '۷'
                '8' -> '۸'
                '9' -> '۹'
                else -> char
            }
        }.joinToString("")
    }

    /**
     * ساعت و دقیقه
     *
     * مثال:
     * ۱۴:۳۰
     */
    fun formatTime(
        millis: Long
    ): String {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return String.format(
            Locale.US,
            "%02d:%02d",
            hour,
            minute
        ).toPersianDigits()
    }

    /**
     * تاریخ و ساعت
     *
     * مثال:
     * ۱۷ شهریور ۱۴۰۵، ۱۴:۳۰
     */
    fun formatDateTime(
        millis: Long
    ): String {

        return buildString {
            append(formatFull(millis))
            append("، ")
            append(formatTime(millis))
        }
    }

    /**
     * شروع امروز
     */
    fun startOfToday(): Long {

        val now = System.currentTimeMillis()
        val today = fromMillis(now)

        return persianToMillis(
            year = today.year,
            month = today.month,
            day = today.day
        )
    }

    /**
     * پایان امروز
     */
    fun endOfToday(): Long {

        return endOfDay(
            startOfToday()
        )
    }

    /**
     * شروع روزی که timestamp به آن تعلق دارد
     */
    fun startOfDay(
        millis: Long
    ): Long {

        val date = fromMillis(millis)

        return persianToMillis(
            year = date.year,
            month = date.month,
            day = date.day
        )
    }

    /**
     * پایان روزی که timestamp به آن تعلق دارد
     */
    fun endOfDay(
        millis: Long
    ): Long {

        return Calendar.getInstance().apply {
            timeInMillis = startOfDay(millis)

            add(
                Calendar.DAY_OF_MONTH,
                1
            )

            add(
                Calendar.MILLISECOND,
                -1
            )
        }.timeInMillis
    }

    /**
     * تعداد روزهای ماه شمسی
     */
    fun daysInMonth(
        year: Int,
        month: Int
    ): Int {

        return when {
            month in 1..6 -> 31
            month in 7..11 -> 30
            month == 12 -> {
                val start =
                    persianToMillis(
                        year = year,
                        month = 12,
                        day = 1
                    )

                val nextYearStart =
                    persianToMillis(
                        year = year + 1,
                        month = 1,
                        day = 1
                    )

                val days =
                    (
                            nextYearStart - start
                            ) / (24L * 60L * 60L * 1000L)

                days.toInt()
            }

            else -> 0
        }
    }

    /**
     * شروع ماه شمسی
     */
    fun startOfPersianMonth(
        millis: Long
    ): Long {

        val date = fromMillis(millis)

        return persianToMillis(
            year = date.year,
            month = date.month,
            day = 1
        )
    }

    /**
     * پایان ماه شمسی
     */
    fun endOfPersianMonth(
        millis: Long
    ): Long {

        val date = fromMillis(millis)

        val lastDay =
            daysInMonth(
                year = date.year,
                month = date.month
            )

        return endOfDay(
            persianToMillis(
                year = date.year,
                month = date.month,
                day = lastDay
            )
        )
    }

    /**
     * شروع سال شمسی
     */
    fun startOfPersianYear(
        millis: Long
    ): Long {

        val date = fromMillis(millis)

        return persianToMillis(
            year = date.year,
            month = 1,
            day = 1
        )
    }

    /**
     * پایان سال شمسی
     */
    fun endOfPersianYear(
        millis: Long
    ): Long {

        val date = fromMillis(millis)

        return endOfDay(
            persianToMillis(
                year = date.year,
                month = 12,
                day = daysInMonth(
                    year = date.year,
                    month = 12
                )
            )
        )
    }

    /**
     * شروع هفته شمسی
     *
     * هفته از شنبه شروع می‌شود
     * و جمعه تمام می‌شود.
     */
    fun startOfPersianWeek(
        millis: Long
    ): Long {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis

        val daysFromSaturday =
            when (calendar.get(Calendar.DAY_OF_WEEK)) {
                Calendar.SATURDAY -> 0
                Calendar.SUNDAY -> 1
                Calendar.MONDAY -> 2
                Calendar.TUESDAY -> 3
                Calendar.WEDNESDAY -> 4
                Calendar.THURSDAY -> 5
                Calendar.FRIDAY -> 6
                else -> 0
            }

        return Calendar.getInstance().apply {
            timeInMillis = startOfDay(millis)

            add(
                Calendar.DAY_OF_MONTH,
                -daysFromSaturday
            )
        }.timeInMillis
    }

    /**
     * پایان هفته شمسی
     */
    fun endOfPersianWeek(
        millis: Long
    ): Long {

        return Calendar.getInstance().apply {
            timeInMillis = startOfPersianWeek(millis)

            add(
                Calendar.DAY_OF_MONTH,
                7
            )

            add(
                Calendar.MILLISECOND,
                -1
            )
        }.timeInMillis
    }

    /**
     * گرفتن بازه زمانی بر اساس periodIndex
     *
     * 0 = روز
     * 1 = هفته
     * 2 = ماه
     * 3 = سال
     */
    fun getPeriodRange(
        periodIndex: Int,
        millis: Long = System.currentTimeMillis()
    ): PersianDateRange {

        return when (periodIndex) {

            0 -> {
                PersianDateRange(
                    startMillis = startOfDay(millis),
                    endMillis = endOfDay(millis)
                )
            }

            1 -> {
                PersianDateRange(
                    startMillis = startOfPersianWeek(millis),
                    endMillis = endOfPersianWeek(millis)
                )
            }

            2 -> {
                PersianDateRange(
                    startMillis = startOfPersianMonth(millis),
                    endMillis = endOfPersianMonth(millis)
                )
            }

            3 -> {
                PersianDateRange(
                    startMillis = startOfPersianYear(millis),
                    endMillis = endOfPersianYear(millis)
                )
            }

            else -> {
                throw IllegalArgumentException(
                    "Invalid period index: $periodIndex"
                )
            }
        }
    }

    /**
     * عنوان بازه برای Home
     */
    fun getPeriodTitle(
        periodIndex: Int,
        millis: Long = System.currentTimeMillis()
    ): String {

        val date = fromMillis(millis)

        return when (periodIndex) {

            0 -> {
                "امروز، ${formatShort(millis)}"
            }

            1 -> {
                val range =
                    getPeriodRange(
                        periodIndex = 1,
                        millis = millis
                    )

                val start = fromMillis(range.startMillis)
                val end = fromMillis(range.endMillis)

                if (start.month == end.month) {
                    "${toPersianDigits(start.day)} تا ${toPersianDigits(end.day)} ${monthName(start.month)}"
                } else {
                    "${toPersianDigits(start.day)} ${monthName(start.month)} تا ${toPersianDigits(end.day)} ${monthName(end.month)}"
                }
            }

            2 -> {
                monthName(date.month)
            }

            3 -> {
                toPersianDigits(date.year)
            }

            else -> {
                ""
            }
        }
    }

    /**
     * متن وضعیت خالی برای Home
     */
    fun getEmptyText(
        periodIndex: Int,
        isIncome: Boolean
    ): String {

        val typeText =
            if (isIncome) {
                "دخلی"
            } else {
                "خرجی"
            }

        return when (periodIndex) {

            0 -> {
                "برای امروز\n$typeText نیست"
            }

            1 -> {
                "برای این هفته\n$typeText نیست"
            }

            2 -> {
                "برای این ماه\n$typeText نیست"
            }

            3 -> {
                "برای امسال\n$typeText نیست"
            }

            else -> {
                ""
            }
        }
    }

    /**
     * آیا دو timestamp در یک روز شمسی هستند؟
     */
    fun isSameDay(
        firstMillis: Long,
        secondMillis: Long
    ): Boolean {

        return fromMillis(firstMillis) ==
                fromMillis(secondMillis)
    }

    /**
     * آیا timestamp مربوط به امروز است؟
     */
    fun isToday(
        millis: Long
    ): Boolean {

        return isSameDay(
            firstMillis = millis,
            secondMillis = System.currentTimeMillis()
        )
    }

    /**
     * متن نسبی تاریخ
     */
    fun relativeDayText(
        millis: Long
    ): String {

        val targetDate =
            fromMillis(millis)

        val todayDate =
            fromMillis(System.currentTimeMillis())

        if (targetDate == todayDate) {
            return "امروز"
        }

        val yesterdayMillis =
            Calendar.getInstance().apply {
                timeInMillis = startOfToday()

                add(
                    Calendar.DAY_OF_MONTH,
                    -1
                )
            }.timeInMillis

        if (
            fromMillis(yesterdayMillis) ==
            targetDate
        ) {
            return "دیروز"
        }

        val tomorrowMillis =
            Calendar.getInstance().apply {
                timeInMillis = startOfToday()

                add(
                    Calendar.DAY_OF_MONTH,
                    1
                )
            }.timeInMillis

        if (
            fromMillis(tomorrowMillis) ==
            targetDate
        ) {
            return "فردا"
        }

        return formatShort(millis)
    }
}
