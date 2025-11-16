package com.example.sesi5kelompok4.ui.theme

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightPalette = lightColors(
    primary = GreenMed,
    primaryVariant = GreenDark,
    secondary = GreenDark,
    background = GreenCream,
    surface = GreenSoft,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF222222),
    onSurface = Color(0xFF222222)
)

private val DarkPalette = darkColors(
    primary = GreenMed,
    primaryVariant = GreenDark,
    secondary = GreenSoft,
    background = Color(0xFF111111),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun Sesi5Kelompok4Theme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkPalette else LightPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
