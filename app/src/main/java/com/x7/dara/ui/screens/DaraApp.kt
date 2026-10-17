package com.x7.dara.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.x7.dara.ui.components.app.AppBottomBar
import com.x7.dara.ui.components.app.SystemBars
import com.x7.dara.ui.theme.BrandPrimary
import com.x7.dara.ui.theme.SurfacePrimary

@Composable
fun DaraApp() {
    val navController = rememberNavController()
    val isFullScreen = checkForFullScreen(navController)

    var navIndex by remember {
        mutableIntStateOf(0)
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val navigationBarColor = SurfacePrimary

    SystemBars(
        navigationBarColor = navigationBarColor
    )

    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        Scaffold(
            bottomBar = {
                if (!isFullScreen) {
                    AppBottomBar(
                        selectedIndex = navIndex,
                        onSelect = {
                            navIndex = it
                        },
                        navController = navController
                    )
                }
            },
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets(0)
        ) { innerPadding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "homeScreen/0"
                ) {
//                    composable("splashScreen") { SplashScreen(navController) }
                    composable(
                        route = "homeScreen/{type}",
                        arguments = listOf(
                            navArgument("type") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->

                        val type =
                            backStackEntry.arguments?.getInt("type") ?: 1

                        HomeScreen(
                            navController = navController,
                            initialType = type
                        )
                    }
                    composable(
                        route = "addTransactionScreen/{type}",
                        arguments = listOf(
                            navArgument("type") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->

                        val type =
                            backStackEntry.arguments?.getInt("type") ?: 1

                        AddTransactionScreen(
                            navController = navController,
                            initialType = type
                        )
                    }
                    composable("assetScreen") { AssetsScreen(navController) }
                    composable("addAssetScreen") { AddAssetScreen(navController) }
                    composable(
                        route = "transactionDetailScreen/{transactionId}",
                        arguments = listOf(
                            navArgument("transactionId") {
                                type = NavType.LongType
                            }
                        )
                    ) { backStackEntry ->

                        val transactionId =
                            backStackEntry.arguments?.getLong("transactionId")
                                ?: return@composable

                        TransactionDetailScreen(
                            transactionId = transactionId,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun checkForFullScreen(
    navController: NavHostController
): Boolean {

    val fullScreenRoutes = listOf(
        "addTransactionScreen",
        "splashScreen"
    )

    val currentRoute =
        navController
            .currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route
            ?: "home"

    return fullScreenRoutes.any {
        currentRoute.startsWith(it)
    }
}