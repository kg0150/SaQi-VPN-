package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val SaqiColorScheme = darkColorScheme(
    primary = ElectricBlue,
    onPrimary = TextPrimary,
    primaryContainer = NavyCardElevated,
    onPrimaryContainer = CyanGlow,
    secondary = CyanGlow,
    onSecondary = TextOnCyan,
    secondaryContainer = NavySurface,
    onSecondaryContainer = CyanGlow,
    tertiary = NeonYellow,
    onTertiary = TextOnYellow,
    tertiaryContainer = NavyCardElevated,
    onTertiaryContainer = NeonYellow,
    background = NavyDark,
    onBackground = TextPrimary,
    surface = NavySurface,
    onSurface = TextPrimary,
    surfaceVariant = NavyCardElevated,
    onSurfaceVariant = TextSecondary,
    outline = NavyBorder
)

@Composable
fun SaqiVpnTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SaqiColorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    SaqiVpnTheme(content = content)
}
