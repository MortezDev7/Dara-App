package com.x7.dara.ui.components.ui.assets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.x7.dara.model.database.asset.AssetType
import com.x7.dara.viewmodel.local.AssetViewModel

@Composable
fun SellForm(
    onSubmit: (String, Long, String) -> Unit = { _, _, _ -> },
    onValidationError: (String) -> Unit = {},
    assetViewModel: AssetViewModel = hiltViewModel()
) {
    val state by assetViewModel.uiState.collectAsStateWithLifecycle()

    val goldAssets = state.data
        .orEmpty()
        .filter {
            it.type == AssetType.GOLD
        }

    var amount by rememberSaveable {
        mutableStateOf("")
    }

    var note by rememberSaveable {
        mutableStateOf("")
    }

    var selectedAssetId by rememberSaveable {
        mutableLongStateOf(-1L)
    }

    var showAssetDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val selectedAsset = goldAssets.find {
        it.id == selectedAssetId
    }

    val selectedAssetName =
        selectedAsset?.name
            ?: "انتخاب دارایی"

    AssetForm(
        amount = amount,
        onAmountChange = {
            amount = it
        },
        note = note,
        onNoteChange = {
            note = it
        },
        assetName = selectedAssetName,
        onAssetClick = {
            if (goldAssets.isEmpty()) {
                onValidationError(
                    "هنوز دارایی‌ای برای فروش ثبت نشده"
                )
            } else {
                showAssetDialog = true
            }
        },
        onSubmit = {

            if (selectedAsset == null) {
                onValidationError(
                    "ابتدا یک دارایی را انتخاب کنید"
                )
                return@AssetForm
            }

            val quantity = amount
                .replace(",", "")
                .toDoubleOrNull()

            if (quantity == null || quantity <= 0.0) {
                onValidationError(
                    "مقدار فروش را وارد کنید"
                )
                return@AssetForm
            }

            if (quantity > selectedAsset.quantity) {
                onValidationError(
                    "مقدار فروش بیشتر از موجودی دارایی است"
                )
                return@AssetForm
            }

            val newQuantity =
                selectedAsset.quantity - quantity

            if (newQuantity <= 0.0) {
                assetViewModel.deleteAsset(
                    selectedAsset
                )
            } else {
                assetViewModel.updateAsset(
                    selectedAsset.copy(
                        quantity = newQuantity
                    )
                )
            }

            onSubmit(
                amount,
                selectedAsset.purchasePrice,
                note
            )
        }
    )

    if (showAssetDialog) {
        AssetSelectDialog(
            assets = goldAssets,
            selectedId = selectedAssetId,
            onConfirm = { assetId ->
                selectedAssetId = assetId
                showAssetDialog = false
            },
            onDismiss = {
                showAssetDialog = false
            }
        )
    }
}