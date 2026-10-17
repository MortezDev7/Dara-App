package com.x7.dara.viewmodel.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x7.dara.model.database.transaction.TransferEntity
import com.x7.dara.repository.local.TransferDaoRepository
import com.x7.dara.ui.state.DataUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    private val transferRepository: TransferDaoRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<DataUiState<Unit>>(
            DataUiState()
        )

    val uiState: StateFlow<DataUiState<Unit>> =
        _uiState.asStateFlow()

    fun addTransfer(
        fromAccountId: Long,
        toAccountId: Long,
        categoryId: Long,
        amount: Long,
        description: String?,
        date: Long
    ) {
        viewModelScope.launch {
            try {
                transferRepository.addTransfer(
                    fromAccountId = fromAccountId,
                    toAccountId = toAccountId,
                    categoryId = categoryId,
                    amount = amount,
                    description = description,
                    date = date
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        data = Unit,
                        error = null
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    fun updateTransfer(
        transferId: Long,
        fromAccountId: Long,
        toAccountId: Long,
        categoryId: Long,
        amount: Long,
        description: String?,
        date: Long
    ) {
        viewModelScope.launch {
            try {
                transferRepository.update(
                    transferId = transferId,
                    fromAccountId = fromAccountId,
                    toAccountId = toAccountId,
                    categoryId = categoryId,
                    amount = amount,
                    description = description,
                    date = date
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        data = Unit,
                        error = null
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    fun deleteTransfer(transferId: Long) {
        viewModelScope.launch {
            try {
                transferRepository.delete(transferId)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        data = Unit,
                        error = null
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
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