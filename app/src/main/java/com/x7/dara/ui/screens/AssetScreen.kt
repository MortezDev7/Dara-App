package com.x7.dara.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.x7.dara.R
import com.x7.dara.model.database.asset.AssetType
import com.x7.dara.ui.components.app.AddButton
import com.x7.dara.ui.components.ui.assets.AssetItem
import com.x7.dara.ui.components.ui.assets.AssetListCard
import com.x7.dara.ui.components.ui.assets.AssetsChartCard
import com.x7.dara.ui.components.app.header.AppHeader
import com.x7.dara.ui.components.app.header.HeaderScaffold
import com.x7.dara.ui.components.app.header.HeaderTitle
import com.x7.dara.ui.theme.BrandSecondaryContainer
import com.x7.dara.ui.theme.SurfacePrimary
import com.x7.dara.ui.theme.SurfaceSecondary
import com.x7.dara.ui.theme.TextTertiary
import com.x7.dara.viewmodel.local.AssetPriceViewModel
import com.x7.dara.viewmodel.local.AssetViewModel
import com.x7.dara.viewmodel.remote.CurrencyViewModel
import kotlin.math.roundToInt

@Composable
fun AssetsScreen(
    navController: NavController,
    assetViewModel: AssetViewModel = hiltViewModel(),
    currencyViewModel: CurrencyViewModel = hiltViewModel(),
    assetPriceViewModel: AssetPriceViewModel = hiltViewModel()
) {
    val assetState by assetViewModel.uiState.collectAsStateWithLifecycle()
    val marketState by currencyViewModel.marketState.collectAsStateWithLifecycle()
    val priceState by assetPriceViewModel.uiState.collectAsStateWithLifecycle()

    val assets = assetState.data.orEmpty()

    val goldAssets = assets.filter {
        it.type == AssetType.GOLD
    }

    val assetIds = goldAssets.map {
        it.id
    }

    val gold18 = marketState.data
        ?.gold
        ?.gold18

    val currentGoldPrice = gold18
        ?.price
        ?.replace(",", "")
        ?.toLongOrNull()
        ?.times(10L)

    LaunchedEffect(assetIds) {
        assetPriceViewModel.observePrices(assetIds)
    }

    LaunchedEffect(currentGoldPrice, assetIds) {

        if (currentGoldPrice != null) {

            goldAssets.forEach { asset ->

                assetPriceViewModel.addPriceIfChanged(
                    assetId = asset.id,
                    price = currentGoldPrice
                )
            }
        }
    }

    val assetItems = goldAssets.mapNotNull { asset ->

        val unitPrice = currentGoldPrice
            ?: return@mapNotNull null

        val totalValue = (
                asset.quantity * unitPrice
                ).toLong()

        AssetItem(
            id = asset.id.toInt(),
            title = asset.name,
            unitPrice = unitPrice,
            totalValue = totalValue,
            iconRes = R.drawable.gold_image
        )
    }

    val totalValue = assetItems.sumOf {
        it.totalValue
    }

    val priceHistory = priceState.data
        .orEmpty()
        .sortedBy {
            it.timestamp
        }

    val chartValues = priceHistory.map { pricePoint ->

        val asset = goldAssets.find {
            it.id == pricePoint.assetId
        }

        if (asset == null) {
            0L
        } else {
            (
                    asset.quantity * pricePoint.price
                    ).toLong()
        }
    }

    val validChartValues = chartValues.filter {
        it > 0L
    }

    val percentChange = if (
        validChartValues.size >= 2 &&
        validChartValues.first() > 0L
    ) {

        (
                (
                        (
                                validChartValues.last().toDouble() -
                                        validChartValues.first().toDouble()
                                ) /
                                validChartValues.first().toDouble()
                        ) * 100.0
                ).roundToInt()

    } else {
        0
    }

    HeaderScaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceSecondary),
        overlap = 50.dp,
        header = { overlap ->

            AppHeader(
                height = 160.dp,
                overlap = overlap,
                title = {
                    HeaderTitle("دارایی من")
                }
            ) {

                Spacer(
                    Modifier.height(8.dp)
                )

                Text(
                    text = "۹ اردیبهشت، ۱۴۰۵",
                    fontSize = 18.sp,
                    color = BrandSecondaryContainer
                )
            }
        }
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                AssetsChartCard(
                    totalValue = totalValue,
                    percentChange = percentChange,
                    chartValues = validChartValues,
                    modifier = Modifier.padding(
                        horizontal = 16.dp
                    )
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(
                            rememberScrollState()
                        )
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 20.dp,
                            bottom = 96.dp
                        )
                ) {

                    if (assetState.isLoading) {

                        Text(
                            text = "در حال دریافت دارایی‌ها...",
                            color = TextTertiary
                        )

                    } else if (assets.isEmpty()) {

                        Text(
                            text = "هنوز دارایی‌ای ثبت نشده",
                            color = TextTertiary
                        )

                    } else if (assetItems.isEmpty()) {

                        Text(
                            text = "قیمت فعلی دارایی در دسترس نیست",
                            color = TextTertiary
                        )

                    } else {

                        AssetListCard(
                            items = assetItems
                        )
                    }
                }
            }

            AddButton(
                onClick = {
                    navController.navigate(
                        "addAssetScreen"
                    )
                },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(24.dp)
            )
        }
    }
}