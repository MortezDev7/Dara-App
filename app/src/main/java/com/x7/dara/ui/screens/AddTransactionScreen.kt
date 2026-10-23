package com.x7.dara.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.x7.dara.ui.components.AccountSelectDialog
import com.x7.dara.ui.components.app.header.AppHeader
import com.x7.dara.ui.components.app.header.AppTabs
import com.x7.dara.ui.components.app.header.HeaderBackButton
import com.x7.dara.ui.components.app.header.HeaderScaffold
import com.x7.dara.ui.components.app.header.HeaderTitle
import com.x7.dara.ui.components.ui.transaction.ExpenseForm
import com.x7.dara.ui.components.ui.transaction.IncomeForm
import com.x7.dara.ui.state.UiEvent
import com.x7.dara.ui.theme.SurfaceSecondary
import com.x7.dara.ui.components.app.AppMessage
import com.x7.dara.ui.components.app.AppMessageData
import com.x7.dara.ui.components.app.AppMessageType
import com.x7.dara.utils.money.MoneyUtils
import com.x7.dara.viewmodel.local.AccountViewModel
import com.x7.dara.viewmodel.local.TransactionViewModel

@Composable
fun AddTransactionScreen(
    initialType: Int,
    navController: NavController,
    accountViewModel: AccountViewModel = hiltViewModel(),
    transactionViewModel: TransactionViewModel = hiltViewModel()
) {
    val accountUiState by accountViewModel.uiState.collectAsStateWithLifecycle()
    val accounts = accountUiState.data.orEmpty()

    var message by remember {
        mutableStateOf<AppMessageData?>(null)
    }

    var type by rememberSaveable {
        mutableIntStateOf(initialType)
    }

    var selectedAccountId by rememberSaveable {
        mutableLongStateOf(-1L)
    }

    var showAccountDialog by rememberSaveable {
        mutableStateOf(false)
    }

    fun goBackToHome() {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set("transaction_type_result", type)

        navController.popBackStack()
    }

    BackHandler {
        goBackToHome()
    }

    LaunchedEffect(accounts) {
        if (
            selectedAccountId == -1L &&
            accounts.isNotEmpty()
        ) {
            selectedAccountId = accounts.first().id
        }
    }

    LaunchedEffect(Unit) {
        transactionViewModel.events.collect { event ->
            when (event) {
                UiEvent.Success -> {
                    goBackToHome()
                }

                is UiEvent.Error -> {
                    message = AppMessageData(
                        message = event.message,
                        type = AppMessageType.ERROR
                    )
                }
            }
        }
    }

    val selectedAccount = accounts.firstOrNull {
        it.id == selectedAccountId
    }

    val selectedAccountName =
        selectedAccount?.name
            ?: if (accounts.isEmpty()) {
                "حسابی وجود ندارد"
            } else {
                "انتخاب حساب"
            }

    val totalBalance = accounts.sumOf {
        it.balance
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceSecondary)
    ) {
        HeaderScaffold(
            modifier = Modifier.fillMaxSize(),
            header = { overlap ->
                AppHeader(
                    centerTitle = false,
                    height = 120.dp,
                    overlap = overlap,
                    navigationIcon = {
                        HeaderBackButton {
                            goBackToHome()
                        }
                    },
                    title = {
                        HeaderTitle("تراکنش جدید")
                    }
                ) {
                    AppTabs(
                        selectedIndex = type,
                        onSelect = {
                            type = it
                        }
                    )
                }
            }
        ) {
            Crossfade(
                targetState = type,
                modifier = Modifier.fillMaxSize(),
                label = "transactionForm"
            ) { tab ->

                if (tab == 0) {
                    IncomeForm(
                        accountName = selectedAccountName,

                        onAccountClick = {
                            if (accounts.isNotEmpty()) {
                                showAccountDialog = true
                            } else {
                                message = AppMessageData(
                                    message = "ابتدا یک حساب ایجاد کنید",
                                    type = AppMessageType.INFO
                                )
                            }
                        },

                        onSubmit = { amountText, categoryId, note ->
                            val amount =
                                MoneyUtils.parseAmount(amountText)

                            when {
                                selectedAccountId == -1L -> {
                                    message = AppMessageData(
                                        message = "لطفاً یک حساب انتخاب کنید",
                                        type = AppMessageType.WARNING
                                    )
                                }

                                categoryId == -1L -> {
                                    message = AppMessageData(
                                        message = "لطفاً یک دسته‌بندی انتخاب کنید",
                                        type = AppMessageType.WARNING
                                    )
                                }

                                amount == null || amount <= 0L -> {
                                    message = AppMessageData(
                                        message = "مبلغ وارد شده معتبر نیست",
                                        type = AppMessageType.ERROR
                                    )
                                }

                                else -> {
                                    transactionViewModel.addIncome(
                                        accountId = selectedAccountId,
                                        categoryId = categoryId,
                                        amount = amount,
                                        description = note
                                            .trim()
                                            .ifEmpty { null },
                                        date = System.currentTimeMillis()
                                    )
                                }
                            }
                        }
                    )
                } else {
                    ExpenseForm(
                        accountName = selectedAccountName,

                        onAccountClick = {
                            if (accounts.isNotEmpty()) {
                                showAccountDialog = true
                            } else {
                                message = AppMessageData(
                                    message = "ابتدا یک حساب ایجاد کنید",
                                    type = AppMessageType.INFO
                                )
                            }
                        },

                        onSubmit = { amountText, categoryId, note ->
                            val amount =
                                MoneyUtils.parseAmount(amountText)

                            when {
                                selectedAccountId == -1L -> {
                                    message = AppMessageData(
                                        message = "لطفاً یک حساب انتخاب کنید",
                                        type = AppMessageType.WARNING
                                    )
                                }

                                categoryId == -1L -> {
                                    message = AppMessageData(
                                        message = "لطفاً یک دسته‌بندی انتخاب کنید",
                                        type = AppMessageType.WARNING
                                    )
                                }

                                amount == null || amount <= 0L -> {
                                    message = AppMessageData(
                                        message = "مبلغ وارد شده معتبر نیست",
                                        type = AppMessageType.ERROR
                                    )
                                }

                                else -> {
                                    transactionViewModel.addExpense(
                                        accountId = selectedAccountId,
                                        categoryId = categoryId,
                                        amount = amount,
                                        description = note
                                            .trim()
                                            .ifEmpty { null },
                                        date = System.currentTimeMillis()
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }

        AppMessage(
            message = message,
            onDismiss = {
                message = null
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        if (showAccountDialog) {
            AccountSelectDialog(
                accounts = accounts,
                selectedId = selectedAccountId,
                totalBalance = totalBalance,
                showTotal = false,

                onConfirm = { accountId ->
                    selectedAccountId = accountId
                    showAccountDialog = false
                },

                onDismiss = {
                    showAccountDialog = false
                },
                onAddAccount = { navController.navigate("addAccountScreen") }
            )
        }
    }
}
