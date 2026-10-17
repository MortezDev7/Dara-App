package com.x7.dara.viewmodel.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x7.dara.model.database.asset.AssetEntity
import com.x7.dara.model.database.asset.AssetType
import com.x7.dara.repository.local.AssetDaoRepository
import com.x7.dara.ui.state.DataUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetViewModel @Inject constructor(
    private val assetRepository: AssetDaoRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<DataUiState<List<AssetEntity>>>(
            DataUiState(isLoading = true)
        )

    val uiState: StateFlow<DataUiState<List<AssetEntity>>> =
        _uiState.asStateFlow()

    init {
        observeAssets()
    }

    private fun observeAssets() {
        viewModelScope.launch {

            assetRepository
                .getAll()
                .collect { assets ->

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            data = assets,
                            error = null
                        )
                    }
                }
        }
    }

    fun getByType(
        type: AssetType
    ): Flow<List<AssetEntity>> {
        return assetRepository.getByType(type)
    }

    fun addAsset(
        name: String,
        type: AssetType,
        quantity: Double,
        unit: String,
        purchasePrice: Long,
        purchaseDate: Long
    ) {
        viewModelScope.launch {

            try {

                require(quantity > 0) {
                    "Quantity must be greater than zero"
                }

                require(purchasePrice >= 0) {
                    "Purchase price cannot be negative"
                }

                val asset = AssetEntity(
                    name = name,
                    type = type,
                    quantity = quantity,
                    unit = unit,
                    purchasePrice = purchasePrice,
                    purchaseDate = purchaseDate,
                    createdAt = System.currentTimeMillis()
                )

                assetRepository.insert(asset)

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun updateAsset(
        asset: AssetEntity
    ) {
        viewModelScope.launch {

            try {

                require(asset.quantity > 0) {
                    "Quantity must be greater than zero"
                }

                require(asset.purchasePrice >= 0) {
                    "Purchase price cannot be negative"
                }

                assetRepository.update(asset)

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun deleteAsset(
        asset: AssetEntity
    ) {
        viewModelScope.launch {

            try {

                assetRepository.delete(asset)

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}