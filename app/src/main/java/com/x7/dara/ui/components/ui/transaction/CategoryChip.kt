package com.x7.dara.ui.components.ui.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.x7.dara.model.database.transaction.CategoryEntity
import com.x7.dara.ui.theme.BrandPrimary
import com.x7.dara.ui.theme.BrandPrimaryContainer
import com.x7.dara.ui.theme.Neutral40
import com.x7.dara.ui.theme.Neutral60
import com.x7.dara.ui.theme.SurfacePrimary
import com.x7.dara.ui.theme.TextPrimary

@Composable
fun CategoryChip(
    category: CategoryEntity,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)

    Row(
        modifier = modifier
            .clip(shape)
            .background(if (selected) BrandPrimaryContainer else SurfacePrimary)
            .border(
                width = if (selected) 1.5.dp else 1.dp,
                color = if (selected) BrandPrimary else Neutral40,
                shape = shape
            )
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .heightIn(min = 56.dp)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = BrandPrimary,
                unselectedColor = Neutral60
            )
        )

        category.icon.let { iconRes ->
            Spacer(Modifier.width(8.dp))
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = if (selected) BrandPrimary else Neutral60,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(Modifier.width(8.dp))

        Text(
            text = category.name,
            fontSize = 14.sp,
            color = TextPrimary,
            fontWeight = FontWeight.Bold
        )
    }
}