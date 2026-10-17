package com.x7.dara.viewmodel.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x7.dara.model.database.account.AccountEntity
import com.x7.dara.model.database.account.AccountType
import com.x7.dara.repository.local.AccountDaoRepository
import com.x7.dara.ui.state.DataUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountDaoRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<DataUiState<List<AccountEntity>>>(
            DataUiState(isLoading = true)
        )

    val uiState: StateFlow<DataUiState<List<AccountEntity>>> =
        _uiState.asStateFlow()

    init {
        observeAccounts()
        createTestAccount()
    }

    fun createTestAccount() {
        addAccount(
            name = "حساب تست",
            type = AccountType.BANK,
            balance = 1_000_000L,
            currency = "IRR",
            icon = null
        )
    }

    private fun observeAccounts() {
        viewModelScope.launch {

            accountRepository
                .getAll()
                .collect { accounts ->

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            data = accounts,
                            error = null
                        )
                    }
                }
        }
    }

    fun addAccount(
        name: String,
        type: AccountType,
        balance: Long,
        currency: String,
        icon: String?
    ) {
        viewModelScope.launch {

            try {

                require(balance >= 0) {
                    "Initial balance cannot be negative"
                }

                val account = AccountEntity(
                    name = name,
                    accountType = type,
                    balance = balance,
                    currency = currency,
                    icon = icon,
                    createdAt = System.currentTimeMillis()
                )

                accountRepository.insertIfNotExists(account)

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun updateAccount(
        account: AccountEntity
    ) {
        viewModelScope.launch {

            try {

                accountRepository.update(account)

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun deleteAccount(
        account: AccountEntity
    ) {
        viewModelScope.launch {

            try {

                accountRepository.delete(account)

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