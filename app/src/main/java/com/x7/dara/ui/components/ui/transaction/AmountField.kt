package com.x7.dara.ui.components.ui.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight

import com.x7.dara.ui.theme.BrandPrimary
import com.x7.dara.ui.theme.Neutral30
import com.x7.dara.ui.theme.Neutral70
import com.x7.dara.ui.theme.TextSecondary
import com.x7.dara.ui.theme.TextTertiary
import com.x7.dara.ui.theme.Typography


@Composable
fun AmountField(
    text : String = "ریال",
    amount: String,
    onAmountChange: (String) -> Unit,
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.width(180.dp)
        ) {

            BasicTextField(
                value = amount,

                onValueChange = { input ->

                    val rawValue = input
                        .toEnglishDigits()
                        .filter { it.isDigit() }

                    onAmountChange(rawValue)
                },

                singleLine = true,

                textStyle = Typography.titleLarge.copy(
                    color = Neutral70,
                    textAlign = TextAlign.Center
                ),

                cursorBrush = SolidColor(BrandPrimary),

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),

                visualTransformation = AmountVisualTransformation(),

                modifier = Modifier.fillMaxWidth(),

                decorationBox = { innerTextField ->

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {

                        if (amount.isEmpty()) {
                            Text(
                                text = "۰",
                                style = Typography.titleLarge,
                                color = TextTertiary
                            )
                        }

                        innerTextField()
                    }
                }
            )

            Spacer(Modifier.height(6.dp))

            HorizontalDivider(
                color = Neutral30,
                thickness = 1.dp
            )
        }

        Spacer(Modifier.width(12.dp))

        Text(
            text = text,
            style = Typography.titleMedium,
            color = TextSecondary,
            fontWeight = FontWeight.Bold
        )
    }
}
private class AmountVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {

        val rawText = text.text

        if (rawText.isEmpty()) {
            return TransformedText(
                text = AnnotatedString(""),
                offsetMapping = OffsetMapping.Identity
            )
        }

        val formattedText = formatAmount(rawText)

        return TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = createOffsetMapping(rawText)
        )
    }
}
private fun createOffsetMapping(
    original: String
): OffsetMapping {

    val formatted = formatAmount(original)

    return object : OffsetMapping {

        override fun originalToTransformed(offset: Int): Int {

            if (offset <= 0) {
                return 0
            }

            if (offset >= original.length) {
                return formatted.length
            }

            var digitCount = 0

            for (index in formatted.indices) {

                if (formatted[index].isDigit()) {

                    digitCount++

                    if (digitCount == offset) {
                        return index + 1
                    }
                }
            }

            return formatted.length
        }

        override fun transformedToOriginal(offset: Int): Int {

            if (offset <= 0) {
                return 0
            }

            if (offset >= formatted.length) {
                return original.length
            }

            var digitCount = 0

            for (index in 0 until offset) {

                if (formatted[index].isDigit()) {
                    digitCount++
                }
            }

            return digitCount.coerceIn(
                0,
                original.length
            )
        }
    }
}
private fun formatAmount(
    value: String
): String {

    if (value.isEmpty()) {
        return ""
    }

    val rawValue = value
        .toEnglishDigits()
        .filter { it.isDigit() }

    if (rawValue.isEmpty()) {
        return ""
    }

    return rawValue
        .reversed()
        .chunked(3)
        .joinToString("٬")
        .reversed()
        .toPersianDigits()
}

private fun String.toEnglishDigits(): String {

    return map { char ->

        when (char) {

            '۰' -> '0'
            '۱' -> '1'
            '۲' -> '2'
            '۳' -> '3'
            '۴' -> '4'
            '۵' -> '5'
            '۶' -> '6'
            '۷' -> '7'
            '۸' -> '8'
            '۹' -> '9'

            else -> char
        }
    }.joinToString("")
}
private fun String.toPersianDigits(): String {

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