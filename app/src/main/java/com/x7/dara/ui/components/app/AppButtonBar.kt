package com.x7.dara.ui.components.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.x7.dara.R
import com.x7.dara.ui.theme.BrandPrimary
import com.x7.dara.ui.theme.Neutral20
import com.x7.dara.ui.theme.Neutral50
import com.x7.dara.ui.theme.SurfacePrimary

private data class BottomBarEntry(
    val title: String,
    val filledIcon: Int,
    val outlinedIcon: Int
)
@Composable
fun AppBottomBar(
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val density = LocalDensity.current
    val bottomInset = with(density) {
        WindowInsets.navigationBars.getBottom(this).toDp()
    }

    val items = listOf(
        BottomBarEntry("خانه", R.drawable.home_filled, R.drawable.home_outlined),
        BottomBarEntry("دارایی", R.drawable.wallet_filled, R.drawable.wallet_outlined),
        BottomBarEntry("پروفایل", R.drawable.profile_filled, R.drawable.profile_outlined)
    )

    Column(modifier = modifier.background(SurfacePrimary)
        .padding(bottom = (bottomInset - 20.dp).coerceAtLeast(0.dp))
    ) {
        HorizontalDivider(color = Neutral20, thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEachIndexed { index, item ->
                BottomBarItem(
                    title = item.title,
                    icon = if (index == selectedIndex) item.filledIcon else item.outlinedIcon ,
                    selected = index == selectedIndex,
                    onClick = {
                        onSelect(index)

                        when (index) {
                            0 -> {
                                navController.navigate("homeScreen/0"){
                                    popUpTo("homeScreen"){
                                        inclusive = true
                                    }
                                }
                            }
                            1 -> {
                                navController.navigate("assetScreen"){
                                    popUpTo("assetScreen"){
                                        inclusive = true
                                    }
                                }
                            }
                            2 -> {
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomBarItem(
    title: String,
    icon: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    val color = if (selected) BrandPrimary else Neutral50
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = title,
            tint = color,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = title,
            fontSize = 13.sp,
            color = color,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}