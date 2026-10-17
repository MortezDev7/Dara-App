package com.x7.dara.viewmodel.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x7.dara.model.brs.mapper.MarketMapper
import com.x7.dara.model.brs.mapper.MarketUiModel
import com.x7.dara.repository.remote.CurrencyRepository
import com.x7.dara.ui.state.DataUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    private var updateJob: Job? = null

    private val _marketState =
        MutableStateFlow(
            DataUiState<MarketUiModel>()
        )

    val marketState =
        _marketState.asStateFlow()

    init {
        observeCachedMarketData()
        startAutoUpdate()
    }

    private fun observeCachedMarketData() {

        viewModelScope.launch {

            repository
                .observeCachedMarketData()
                .collect { response ->

                    if (
                        response != null &&
                        _marketState.value.data == null
                    ) {

                        val mappedData =
                            MarketMapper.map(response)

                        _marketState.value =
                            DataUiState(
                                isLoading = false,
                                data = mappedData,
                                error = null
                            )
                    }
                }
        }
    }

    private fun startAutoUpdate() {

        if (updateJob != null) return

        updateJob = viewModelScope.launch {

            while (isActive) {

                loadMarketData()

                delay(
                    (2 * 60 * 1000L).milliseconds
                )
            }
        }
    }

    private suspend fun loadMarketData() {

        try {

            if (_marketState.value.data == null) {
                _marketState.value =
                    DataUiState(
                        isLoading = true,
                        data = null,
                        error = null
                    )
            }

            val response =
                repository.getMarketData()

            val mappedData =
                MarketMapper.map(response)

            _marketState.value =
                DataUiState(
                    isLoading = false,
                    data = mappedData,
                    error = null
                )

        } catch (e: Exception) {

            _marketState.value =
                DataUiState(
                    isLoading = false,
                    data = _marketState.value.data,
                    error = e.message
                        ?: "خطا در دریافت اطلاعات بازار"
                )
        }
    }

    override fun onCleared() {

        updateJob?.cancel()
        updateJob = null

        super.onCleared()
    }
}