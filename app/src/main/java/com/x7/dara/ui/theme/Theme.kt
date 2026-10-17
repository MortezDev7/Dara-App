package com.x7.dara.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DaraLightColorScheme = lightColorScheme(
    primary = BrandPrimary,
    onPrimary = TextInverse,

    primaryContainer = BrandPrimaryContainer,
    onPrimaryContainer = BrandOnPrimaryContainer,

    secondary = BrandSecondary,
    onSecondary = TextInverse,

    secondaryContainer = BrandSecondaryContainer,
    onSecondaryContainer = BrandOnSecondaryContainer,

    tertiary = BrandAccent,
    onTertiary = TextInverse,

    background = SurfaceSecondary,
    onBackground = TextPrimary,

    surface = SurfacePrimary,
    onSurface = TextPrimary,

    surfaceVariant = SurfaceTertiary,
    onSurfaceVariant = TextSecondary,

    outline = BorderNormal,
    outlineVariant = BorderSubtle,

    error = ErrorPrimary,
    onError = ErrorOnPrimary,

    errorContainer = ErrorContainer,
    onErrorContainer = ErrorOnContainer
)

//private val DaraDarkColorScheme = darkColorScheme(
//    primary = BrandPrimary,
//    onPrimary = TextInverse,
//
//    primaryContainer = BrandPrimaryContainer,
//    onPrimaryContainer = BrandOnPrimaryContainer,
//
//    secondary = BrandSecondary,
//    onSecondary = TextInverse,
//
//    secondaryContainer = BrandSecondaryContainer,
//    onSecondaryContainer = BrandOnSecondaryContainer,
//
//    tertiary = BrandAccent,
//    onTertiary = TextInverse,
//
//    background = Neutral110,
//    onBackground = TextInverse,
//
//    surface = Neutral100,
//    onSurface = TextInverse,
//
//    surfaceVariant = Neutral90,
//    onSurfaceVariant = Neutral40,
//
//    outline = Neutral60,
//    outlineVariant = Neutral70,
//
//    error = ErrorPrimary,
//    onError = ErrorOnPrimary,
//
//    errorContainer = ErrorContainer,
//    onErrorContainer = ErrorOnContainer
//)

@Composable
fun DaraTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = DaraLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
