package com.x7.dara.viewmodel.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x7.dara.model.database.asset.AssetPriceEntity
import com.x7.dara.repository.local.AssetPriceDaoRepository
import com.x7.dara.ui.state.DataUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetPriceViewModel @Inject constructor(
    private val assetPriceRepository: AssetPriceDaoRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<DataUiState<List<AssetPriceEntity>>>(
            DataUiState(isLoading = true)
        )

    val uiState: StateFlow<DataUiState<List<AssetPriceEntity>>> =
        _uiState.asStateFlow()

    fun observePrices(
        assetId: Long
    ) {
        viewModelScope.launch {
            assetPriceRepository
                .getByAsset(assetId)
                .collect { prices ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            data = prices,
                            error = null
                        )
                    }
                }
        }
    }

    fun observePrices(
        assetIds: List<Long>
    ) {
        viewModelScope.launch {

            if (assetIds.isEmpty()) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        data = emptyList(),
                        error = null
                    )
                }

                return@launch
            }

            assetPriceRepository
                .getByAssets(assetIds)
                .collect { prices ->

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            data = prices,
                            error = null
                        )
                    }
                }
        }
    }

    fun addPrice(
        assetId: Long,
        price: Long,
        timestamp: Long = System.currentTimeMillis()
    ) {
        viewModelScope.launch {
            try {

                require(price >= 0) {
                    "Price cannot be negative"
                }

                val assetPrice = AssetPriceEntity(
                    assetId = assetId,
                    price = price,
                    timestamp = timestamp
                )

                assetPriceRepository.insert(
                    assetPrice
                )

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun addPriceIfChanged(
        assetId: Long,
        price: Long,
        timestamp: Long = System.currentTimeMillis()
    ) {
        viewModelScope.launch {
            try {

                require(price >= 0) {
                    "Price cannot be negative"
                }

                val latestPrice =
                    assetPriceRepository.getLatest(
                        assetId
                    )

                if (
                    latestPrice == null ||
                    latestPrice.price != price
                ) {

                    val assetPrice = AssetPriceEntity(
                        assetId = assetId,
                        price = price,
                        timestamp = timestamp
                    )

                    assetPriceRepository.insert(
                        assetPrice
                    )
                }

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun updatePrice(
        price: AssetPriceEntity
    ) {
        viewModelScope.launch {
            try {

                require(price.price >= 0) {
                    "Price cannot be negative"
                }

                assetPriceRepository.update(
                    price
                )

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun deletePrice(
        price: AssetPriceEntity
    ) {
        viewModelScope.launch {
            try {

                assetPriceRepository.delete(
                    price
                )

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun deletePricesByAsset(
        assetId: Long
    ) {
        viewModelScope.launch {
            try {

                assetPriceRepository.deleteByAsset(
                    assetId
                )

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    suspend fun getLatest(
        assetId: Long
    ): AssetPriceEntity? {
        return assetPriceRepository.getLatest(
            assetId
        )
    }

    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}
