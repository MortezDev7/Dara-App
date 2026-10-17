package com.x7.dara.ui.components.ui.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.x7.dara.model.database.transaction.TransactionType
import com.x7.dara.viewmodel.local.CategoryViewModel

@Composable
fun IncomeForm(
    accountName: String,
    onAccountClick: () -> Unit,
    modifier: Modifier = Modifier,
    onSubmit: (String, Long, String) -> Unit = { _, _, _ -> },
    vm: CategoryViewModel = hiltViewModel()
) {
    val state by vm.uiState.collectAsStateWithLifecycle()

    val categories =
        state.data
            .orEmpty()
            .filter {
                it.type == TransactionType.INCOME
            }

    var amount by rememberSaveable {
        mutableStateOf("")
    }

    var note by rememberSaveable {
        mutableStateOf("")
    }

    var selectedCategoryId by rememberSaveable {
        mutableLongStateOf(-1L)
    }

    LaunchedEffect(categories) {
        if (
            selectedCategoryId == -1L &&
            categories.isNotEmpty()
        ) {
            selectedCategoryId = categories.first().id
        }
    }

    TransactionForm(
        amount = amount,
        onAmountChange = {
            amount = it
        },
        accountName = accountName,
        onAccountClick = onAccountClick,
        note = note,
        onNoteChange = {
            note = it
        },
        categories = categories,
        selectedCategoryId = selectedCategoryId,
        onCategorySelect = {
            selectedCategoryId = it
        },
        onSubmit = {
            onSubmit(
                amount,
                selectedCategoryId,
                note
            )
        },
        modifier = modifier
    )
}
