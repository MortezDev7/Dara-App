package com.x7.dara.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.x7.dara.R
import com.x7.dara.model.database.account.AccountType
import com.x7.dara.ui.components.app.AddButton
import com.x7.dara.ui.components.app.AppMessage
import com.x7.dara.ui.components.app.AppMessageData
import com.x7.dara.ui.components.app.AppMessageType
import com.x7.dara.ui.components.app.header.AppHeader
import com.x7.dara.ui.components.app.header.HeaderBackButton
import com.x7.dara.ui.components.app.header.HeaderScaffold
import com.x7.dara.ui.components.app.header.HeaderTitle
import com.x7.dara.ui.components.ui.transaction.AmountField
import com.x7.dara.ui.components.ui.transaction.NoteField
import com.x7.dara.ui.theme.SurfaceSecondary
import com.x7.dara.ui.theme.Typography
import com.x7.dara.viewmodel.local.AccountViewModel

@Composable
fun AddAccountScreen(
    navController: NavController,
    vm: AccountViewModel = hiltViewModel()
) {
    var message by remember {
        mutableStateOf<AppMessageData?>(null)
    }

    var amount by rememberSaveable { mutableStateOf("") }

    var name by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceSecondary)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HeaderScaffold(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                overlap = 40.dp,
                header = { overlap ->
                    AppHeader(
                        height = 80.dp,
                        overlap = overlap,
                        navigationIcon = {
                            HeaderBackButton {
                                navController.popBackStack()
                            }
                        },
                        title = {
                            HeaderTitle(text = "اضافه کردن حساب جدید")
                        }
                    )
                }
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Spacer(Modifier.height(60.dp))

                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "موجودی",
                        style = Typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(20.dp))

                    AmountField(
                        amount = amount,
                        onAmountChange = { amount = it }
                    )

                    Spacer(Modifier.height(32.dp))

                    Text(
                        text = "نام",
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(5.dp))

                    NoteField(
                        note = name,
                        onNoteChange = { name = it },
                        emptyText = "نام حساب خود را وارد کنید."
                    )
                }
            }

            AddButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 24.dp),
                onClick = {
                    when {
                        name.isBlank() -> {
                            message = AppMessageData(
                                message = "لطفاً یک نام برای حساب خود وارد کنید",
                                type = AppMessageType.WARNING
                            )
                        }

                        amount.isBlank() -> {
                            message = AppMessageData(
                                message = "لطفاً موجودی حساب را وارد کنید",
                                type = AppMessageType.WARNING
                            )
                        }

                        amount.toLongOrNull() == null -> {
                            message = AppMessageData(
                                message = "مبلغ واردشده معتبر نیست",
                                type = AppMessageType.WARNING
                            )
                        }

                        else -> {
                            vm.addAccount(
                                name = name.trim(),
                                balance = amount.toLongOrNull()!!,
                                icon = R.drawable.blu
                            )
                            navController.popBackStack()
                        }
                    }
                }
            )
        }

        // پیام روی صفحه قرار می‌گیرد و فضای دکمه را تغییر نمی‌دهد.
        AppMessage(
            message = message,
            onDismiss = { message = null },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    bottom = 16.dp
                )
        )
    }
}
