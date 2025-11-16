package com.example.sesi5kelompok4.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.Typography as MTypography
import androidx.compose.material.Shapes as MShapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

// gunakan warna yang sudah kamu definisikan di Color.kt (Purple80, Purple40, dll.)
private val DarkColorPalette: Colors = darkColors(
    primary = Purple80,
    primaryVariant = PurpleGrey80,
    secondary = Pink80
)

private val LightColorPalette: Colors = lightColors(
    primary = Purple40,
    primaryVariant = PurpleGrey40,
    secondary = Pink40

    /* contoh override lainnya:
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    */
)

// Simple Material v1 Typography (fallback) — sesuaikan kalau kamu punya Type.kt lain
private val AppTypography = MTypography(
    h6 = TextStyle(fontSize = 20.sp),
    body1 = TextStyle(fontSize = 16.sp),
    body2 = TextStyle(fontSize = 14.sp)
)

// Simple Material v1 Shapes (fallback) — gunakan Shapes default
private val AppShapes = MShapes()

@Composable
fun Sesi5Kelompok4Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // parameter dynamicColor dihapus karena bukan fitur di Material v1
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
