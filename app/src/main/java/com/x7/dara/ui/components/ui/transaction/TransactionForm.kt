package com.x7.dara.ui.components.ui.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.x7.dara.model.database.transaction.CategoryEntity

@Composable
fun TransactionForm(
    amount: String,
    onAmountChange: (String) -> Unit,
    accountName: String,
    onAccountClick: () -> Unit,
    note: String,
    onNoteChange: (String) -> Unit,
    categories: List<CategoryEntity>,
    selectedCategoryId: Long,
    onCategorySelect: (Long) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val bottomInset = with(density) {
        WindowInsets.navigationBars.getBottom(this).toDp()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {

            Spacer(
                Modifier.height(10.dp)
            )

            AmountField(
                amount = amount,
                onAmountChange = onAmountChange
            )

            Spacer(
                Modifier.height(30.dp)
            )

            AccountSelector(
                accountName = accountName,
                onClick = onAccountClick
            )

            Spacer(
                Modifier.height(32.dp)
            )

            NoteField(
                note = note,
                onNoteChange = onNoteChange
            )

            Spacer(
                Modifier.height(32.dp)
            )

            FieldLabel("دسته بندی")

            Spacer(
                Modifier.height(16.dp)
            )

            CategoryGrid(
                categories = categories,
                selectedId = selectedCategoryId,
                onSelect = onCategorySelect
            )
        }

        SubmitButton(
            text = "ثبت",
            onClick = onSubmit,
            modifier = Modifier.padding(
                start = 56.dp,
                end = 56.dp,
                top = 8.dp,
                bottom = (bottomInset - 10.dp).coerceAtLeast(0.dp)
            )
        )
    }
}