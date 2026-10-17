package com.x7.dara.viewmodel.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x7.dara.model.database.user.BudgetEntity
import com.x7.dara.repository.local.BudgetDaoRepository
import com.x7.dara.ui.state.DataUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetRepository: BudgetDaoRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<DataUiState<List<BudgetEntity>>>(
            DataUiState(isLoading = true)
        )

    val uiState: StateFlow<DataUiState<List<BudgetEntity>>> =
        _uiState.asStateFlow()

    fun observeBudgets(
        month: Int,
        year: Int
    ) {

        viewModelScope.launch {

            budgetRepository
                .getByMonth(
                    month = month,
                    year = year
                )
                .collect { budgets ->

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            data = budgets,
                            error = null
                        )
                    }
                }
        }
    }

    fun addBudget(
        categoryId: Long,
        amount: Long,
        month: Int,
        year: Int
    ) {
        viewModelScope.launch {

            try {

                require(amount > 0) {
                    "Budget amount must be greater than zero"
                }

                require(month in 1..12) {
                    "Month must be between 1 and 12"
                }

                require(year > 0) {
                    "Invalid year"
                }

                val budget = BudgetEntity(
                    categoryId = categoryId,
                    amount = amount,
                    month = month,
                    year = year
                )

                budgetRepository.insert(
                    budget
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

    fun updateBudget(
        budget: BudgetEntity
    ) {
        viewModelScope.launch {

            try {

                require(budget.amount > 0) {
                    "Budget amount must be greater than zero"
                }

                require(budget.month in 1..12) {
                    "Month must be between 1 and 12"
                }

                require(budget.year > 0) {
                    "Invalid year"
                }

                budgetRepository.update(
                    budget
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

    fun deleteBudget(
        budget: BudgetEntity
    ) {
        viewModelScope.launch {

            try {

                budgetRepository.delete(
                    budget
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

    suspend fun getBudget(
        categoryId: Long,
        month: Int,
        year: Int
    ): BudgetEntity? {

        return budgetRepository.getByCategoryAndMonth(
            categoryId = categoryId,
            month = month,
            year = year
        )
    }

    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}