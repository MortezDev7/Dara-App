package com.x7.dara.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import com.x7.dara.ui.components.app.header.HeaderBalance
import com.x7.dara.ui.components.app.header.HeaderDropdownTitle
import com.x7.dara.ui.components.app.header.HeaderScaffold
import com.x7.dara.ui.components.ui.home.ExpenseContent
import com.x7.dara.ui.components.ui.home.IncomeContent
import com.x7.dara.ui.theme.SurfacePrimary
import com.x7.dara.ui.theme.SurfaceSecondary
import com.x7.dara.viewmodel.local.AccountViewModel

@Composable
fun HomeScreen(
    initialType: Int,
    navController: NavController,
    viewModel: AccountViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var tabIndex by rememberSaveable {
        mutableIntStateOf(initialType)
    }

    var showAccountDialog by remember {
        mutableStateOf(false)
    }

    var selectedAccountId by remember {
        mutableLongStateOf(-1L)
    }
    LaunchedEffect(Unit) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<Int?>(
                "transaction_type_result",
                null
            )
            ?.collect { result ->

                if (result != null) {
                    tabIndex = result

                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.remove<Int>("transaction_type_result")
                }
            }
    }

    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        return
    }

    uiState.error?.let { error ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = error
            )
        }

        return
    }

    val accounts = uiState.data.orEmpty()

    val totalBalance = accounts.sumOf {
        it.balance
    }

    val selectedAccount = accounts.firstOrNull {
        it.id == selectedAccountId
    }

    val selectedBalance =
        if (selectedAccountId == -1L) {
            totalBalance
        } else {
            selectedAccount?.balance ?: 0L
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceSecondary)
    ) {

        HeaderScaffold(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            overlap = 40.dp,
            header = { overlap ->

                AppHeader(
                    topSpace = 0.dp,
                    height = 180.dp,
                    overlap = overlap,
                    title = {
                        HeaderDropdownTitle(
                            text = if (selectedAccountId == -1L) {
                                "موجودی کل"
                            } else {
                                selectedAccount?.name
                                    ?: "انتخاب حساب"
                            },
                            onClick = {
                                showAccountDialog = true
                            }
                        )
                    }
                ) {
                    HeaderBalance(selectedBalance)

                    AppTabs(
                        selectedIndex = tabIndex,
                        onSelect = { index ->
                            tabIndex = index
                        }
                    )
                }
            }
        ) {

            Crossfade(
                modifier = Modifier.fillMaxSize(),
                targetState = tabIndex,
                label = "homeContent"
            ) { tab ->

                if (tab == 0) {
                    IncomeContent(
                        navController = navController,
                        selectedAccountId = selectedAccountId
                    )
                } else {
                    ExpenseContent(
                        navController = navController,
                        selectedAccountId = selectedAccountId
                    )
                }
            }
        }

        if (showAccountDialog) {
            AccountSelectDialog(
                accounts = accounts,
                selectedId = selectedAccountId,
                totalBalance = totalBalance,
                onConfirm = { id ->
                    selectedAccountId = id
                    showAccountDialog = false
                },
                onDismiss = {
                    showAccountDialog = false
                }
            )
        }
    }
}
