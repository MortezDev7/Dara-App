package com.x7.dara.ui.components.ui.assets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.x7.dara.model.database.asset.AssetType
import com.x7.dara.viewmodel.local.AssetViewModel
import com.x7.dara.viewmodel.remote.CurrencyViewModel

@Composable
fun BuyForm(
    onSubmit: (String, Long, String) -> Unit = { _, _, _ -> },
    onValidationError: (String) -> Unit = {},
    assetViewModel: AssetViewModel = hiltViewModel(),
    currencyViewModel: CurrencyViewModel = hiltViewModel()
) {
    val marketState by currencyViewModel.marketState.collectAsStateWithLifecycle()

    var amount by rememberSaveable {
        mutableStateOf("")
    }

    var note by rememberSaveable {
        mutableStateOf("")
    }

    val gold18 = marketState.data
        ?.gold
        ?.gold18

    val currentGoldPrice = gold18
        ?.price
        ?.replace(",", "")
        ?.toLongOrNull()
        ?.times(10L)

    AssetForm(
        amount = amount,
        onAmountChange = {
            amount = it
        },
        note = note,
        onNoteChange = {
            note = it
        },
        onSubmit = {

            val quantity = amount
                .replace(",", "")
                .toDoubleOrNull()

            if (quantity == null || quantity <= 0.0) {
                onValidationError(
                    "مقدار طلا را وارد کنید"
                )
                return@AssetForm
            }

            if (currentGoldPrice == null) {
                onValidationError(
                    "قیمت فعلی طلا هنوز دریافت نشده است"
                )
                return@AssetForm
            }

            assetViewModel.addAsset(
                name = "طلای 18 عیار",
                type = AssetType.GOLD,
                quantity = quantity,
                unit = "گرم",
                purchasePrice = currentGoldPrice,
                purchaseDate = System.currentTimeMillis()
            )

            onSubmit(
                amount,
                currentGoldPrice,
                note
            )
        }
    )
}
