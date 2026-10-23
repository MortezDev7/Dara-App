package com.x7.dara.ui.components.ui.transaction

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.x7.dara.ui.theme.BrandPrimary
import com.x7.dara.ui.theme.Neutral30
import com.x7.dara.ui.theme.TextPrimary
import com.x7.dara.ui.theme.TextTertiary
import com.x7.dara.ui.theme.Typography

@Composable
fun NoteField(
    note: String,
    emptyText : String = "توضیحات",
    onNoteChange: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        BasicTextField(
            value = note,
            onValueChange = onNoteChange,
            textStyle = Typography.bodyLarge.copy(color = TextPrimary),
            cursorBrush = SolidColor(BrandPrimary),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box {
                    if (note.isEmpty()) {
                        Text(
                            text = emptyText,
                            fontSize = 16.sp,
                            color = TextTertiary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    innerTextField()
                }
            }
        )

        Spacer(Modifier.height(10.dp))

        HorizontalDivider(color = Neutral30, thickness = 1.dp)
    }
}