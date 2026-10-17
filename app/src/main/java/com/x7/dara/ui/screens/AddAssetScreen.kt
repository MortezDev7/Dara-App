package com.x7.dara.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.x7.dara.ui.components.ui.assets.BuyForm
import com.x7.dara.ui.components.ui.assets.SellForm
import com.x7.dara.ui.components.app.header.AppHeader
import com.x7.dara.ui.components.app.header.AppTabs
import com.x7.dara.ui.components.app.header.HeaderBackButton
import com.x7.dara.ui.components.app.header.HeaderScaffold
import com.x7.dara.ui.components.app.header.HeaderTitle
import com.x7.dara.ui.theme.SurfacePrimary
import com.x7.dara.ui.theme.SurfaceSecondary

@Composable
fun AddAssetScreen(
    navController: NavController,
) {
    var type by rememberSaveable {
        mutableIntStateOf(0)
    }

    var validationMessage by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(validationMessage) {
        validationMessage?.let { message ->
            snackBarHostState.showSnackbar(
                message = message
            )

            validationMessage = null
        }
    }

    HeaderScaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceSecondary),
        header = { overlap ->
            AppHeader(
                centerTitle = false,
                height = 120.dp,
                overlap = overlap,
                navigationIcon = {
                    HeaderBackButton {
                        navController.popBackStack()
                    }
                },
                title = {
                    HeaderTitle(
                        "دارایی"
                    )
                }
            ) {
                AppTabs(
                    firstTitle = "خرید",
                    secondTitle = "فروش",
                    selectedIndex = type,
                    onSelect = {
                        type = it
                    }
                )
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Crossfade(
                targetState = type,
                modifier = Modifier.fillMaxSize(),
                label = "assetForm"
            ) { tab ->

                if (tab == 0) {

                    BuyForm(
                        onSubmit = { _, _, _ ->
                            navController.popBackStack()
                        },
                        onValidationError = { message ->
                            validationMessage = message
                        }
                    )

                } else {

                    SellForm(
                        onSubmit = { _, _, _ ->
                            navController.popBackStack()
                        },
                        onValidationError = { message ->
                            validationMessage = message
                        }
                    )
                }
            }

            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )
        }
    }
}
