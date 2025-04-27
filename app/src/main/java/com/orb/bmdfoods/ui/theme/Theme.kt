package com.orb.bmdfoods.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = Black,
    onBackground = White,
    surface = Gray,
    onSurface = White,
    surfaceBright = HighlightGray,
    tertiaryContainer = Black1,
    outline = SurfaceVariant,
    scrim = SurfaceVariant,
    surfaceDim = SurfaceVariant
)

private val LightColorScheme = lightColorScheme(
    background = White,
    onBackground = Black,
    surface = Gray,
    onSurface = White,
    surfaceBright = HighlightGray,
    tertiaryContainer = White,
    outline = Black1,
    scrim = White,
    surfaceDim = Light
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}