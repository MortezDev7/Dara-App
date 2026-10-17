package com.x7.dara.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.x7.dara.R
import com.x7.dara.ui.components.app.header.AppHeader
import com.x7.dara.ui.components.app.header.HeaderBackButton
import com.x7.dara.ui.components.app.header.HeaderScaffold
import com.x7.dara.ui.components.app.header.HeaderTitle
import com.x7.dara.ui.components.ui.home.summary.CategoryPalette
import com.x7.dara.ui.theme.ErrorPrimary
import com.x7.dara.ui.theme.SurfacePrimary
import com.x7.dara.ui.theme.SurfaceSecondary
import com.x7.dara.ui.theme.TextPrimary
import com.x7.dara.ui.theme.Typography
import com.x7.dara.utils.PersianDateUtils
import com.x7.dara.viewmodel.local.CategoryViewModel
import com.x7.dara.viewmodel.local.TransactionViewModel
import kotlin.let
import kotlin.math.abs

@Composable
fun TransactionDetailScreen(
    transactionId: Long,
    transactionViewModel: TransactionViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    navController: NavController
) {
    LaunchedEffect(transactionId) {
        transactionViewModel.loadTransactionById(transactionId)
    }

    val transactionState by
    transactionViewModel.selectedTransaction.collectAsStateWithLifecycle()

    val categoryState by
    categoryViewModel.uiState.collectAsStateWithLifecycle()

    val transaction = transactionState.data

    val categoryName = transaction?.let { currentTransaction ->
        categoryState.data
            .orEmpty()
            .firstOrNull { it.id == currentTransaction.categoryId }
            ?.name
    } ?: "نامشخص"

    val categoryIcon = transaction?.let { currentTransaction ->
        categoryState.data
            .orEmpty()
            .firstOrNull { it.id == currentTransaction.categoryId }
            ?.icon
    }
    val categoryColor = transaction?.let { currentTransaction ->
        val colorIndex = abs(currentTransaction.categoryId.hashCode())
            .mod(CategoryPalette.size)

        CategoryPalette[colorIndex]
    } ?: TextPrimary

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
                    height = 80.dp,
                    overlap = overlap,
                    navigationIcon = {
                        HeaderBackButton {
                            navController.popBackStack()
                        }
                    },
                    title = {
                        HeaderTitle(text = "جزئیات تراکنش")
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
                Spacer(Modifier.height(40.dp))

                Text(
                    "مبلغ",
                    style = Typography.titleMedium,
                )

                Spacer(Modifier.height(15.dp))

                Text(
                    text = transaction?.amount
                        ?.let { "$it تومان" }
                        ?: "نامشخص",
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(40.dp))

                Text(
                    "دسته‌بندی",
                    style = Typography.titleMedium,
                )

                Spacer(Modifier.height(15.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (categoryIcon != null && categoryIcon != 0) {
                        Icon(
                            painter = painterResource(id = categoryIcon),
                            contentDescription = categoryName,
                            modifier = Modifier.size(28.dp),
                            categoryColor
                        )

                        Spacer(Modifier.width(8.dp))
                    }

                    Text(
                        text = categoryName,
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(40.dp))

                Text(
                    "تاریخ", style = Typography.titleMedium,
                )

                Spacer(Modifier.height(15.dp))

                Text(
                    text = transaction?.date
                        ?.let { PersianDateUtils.formatDateTime(it) }
                        ?: "نامشخص",
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(40.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        transactionViewModel.deleteTransaction(transactionId)
                        navController.popBackStack()
                    }
                ) {
                    Icon(painter = painterResource(R.drawable.trash), "",
                        modifier = Modifier.size(20.dp),
                        tint = ErrorPrimary
                        )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "حدف تراکنش",
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = ErrorPrimary
                    )
                }
            }
        }
    }
}
