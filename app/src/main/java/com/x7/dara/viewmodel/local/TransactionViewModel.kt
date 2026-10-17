package com.x7.dara.viewmodel.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x7.dara.model.database.transaction.TransactionEntity
import com.x7.dara.repository.local.TransactionDaoRepository
import com.x7.dara.ui.state.DataUiState
import com.x7.dara.ui.state.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionDaoRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<DataUiState<List<TransactionEntity>>>(
            DataUiState(isLoading = true)
        )

    val uiState: StateFlow<DataUiState<List<TransactionEntity>>> =
        _uiState.asStateFlow()

    private val _selectedTransaction =
        MutableStateFlow<DataUiState<TransactionEntity>>(
            DataUiState(isLoading = false)
        )

    val selectedTransaction: StateFlow<DataUiState<TransactionEntity>> =
        _selectedTransaction.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>()

    val events: SharedFlow<UiEvent> =
        _events.asSharedFlow()

    init {
        observeTransactions()
    }

    private fun observeTransactions() {
        viewModelScope.launch {
            transactionRepository
                .getAll()
                .collect { transactions ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            data = transactions,
                            error = null
                        )
                    }
                }
        }
    }

    fun loadTransactionById(transactionId: Long) {
        viewModelScope.launch {
            _selectedTransaction.value =
                DataUiState(isLoading = true)

            try {
                val transaction =
                    transactionRepository.getById(transactionId)

                if (transaction != null) {
                    _selectedTransaction.value =
                        DataUiState(
                            isLoading = false,
                            data = transaction,
                            error = null
                        )
                } else {
                    _selectedTransaction.value =
                        DataUiState(
                            isLoading = false,
                            error = "تراکنش موردنظر پیدا نشد"
                        )
                }
            } catch (e: Exception) {
                _selectedTransaction.value =
                    DataUiState(
                        isLoading = false,
                        error = e.message
                            ?: "خطا در دریافت اطلاعات تراکنش"
                    )
            }
        }
    }

    fun clearSelectedTransaction() {
        _selectedTransaction.value =
            DataUiState(isLoading = false)
    }

    fun addIncome(
        accountId: Long,
        categoryId: Long,
        amount: Long,
        description: String?,
        date: Long
    ) {
        viewModelScope.launch {
            try {
                transactionRepository.addIncome(
                    accountId = accountId,
                    categoryId = categoryId,
                    amount = amount,
                    description = description,
                    date = date
                )

                _events.emit(UiEvent.Success)
            } catch (e: Exception) {
                val message =
                    e.message ?: "خطایی در ثبت درآمد رخ داد"

                _uiState.update {
                    it.copy(error = message)
                }

                _events.emit(UiEvent.Error(message))
            }
        }
    }

    fun addExpense(
        accountId: Long,
        categoryId: Long,
        amount: Long,
        description: String?,
        date: Long
    ) {
        viewModelScope.launch {
            try {
                transactionRepository.addExpense(
                    accountId = accountId,
                    categoryId = categoryId,
                    amount = amount,
                    description = description,
                    date = date
                )

                _events.emit(UiEvent.Success)
            } catch (e: Exception) {
                val message =
                    e.message ?: "خطایی در ثبت هزینه رخ داد"

                _uiState.update {
                    it.copy(error = message)
                }

                _events.emit(UiEvent.Error(message))
            }
        }
    }

    fun updateTransaction(
        transaction: TransactionEntity
    ) {
        viewModelScope.launch {
            try {
                transactionRepository.update(transaction)

                _events.emit(UiEvent.Success)
            } catch (e: Exception) {
                val message =
                    e.message ?: "خطایی در ویرایش تراکنش رخ داد"

                _uiState.update {
                    it.copy(error = message)
                }

                _events.emit(UiEvent.Error(message))
            }
        }
    }

    fun deleteTransaction(
        transactionId: Long
    ) {
        viewModelScope.launch {
            try {
                transactionRepository.delete(transactionId)

                _events.emit(UiEvent.Success)
            } catch (e: Exception) {
                val message =
                    e.message ?: "خطایی در حذف تراکنش رخ داد"

                _uiState.update {
                    it.copy(error = message)
                }

                _events.emit(UiEvent.Error(message))
            }
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}
