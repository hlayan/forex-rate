package com.hlayan.forexrate.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColor = darkColors(
    primary = Primary,
    primaryVariant = Primary,
    secondary = Primary,
    background = Dark,
    surface = Dark,
    onPrimary = OnDark,
    onSecondary = OnDark,
    onBackground = OnDark,
    onSurface = OnDark,
)

private val LightColor = lightColors(
    primary = Primary,
    primaryVariant = Primary,
    secondary = Primary,
    secondaryVariant = Primary,
    background = Light,
    surface = Light,
    onPrimary = OnLight,
    onSecondary = OnLight,
    onBackground = OnLight,
    onSurface = OnLight,
)

var isDarkMode by mutableStateOf(false)

@Composable
fun DefaultPreviewTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (isDarkMode) DarkColor else LightColor,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun MMKExchangeTheme(content: @Composable () -> Unit) {
    DefaultPreviewTheme {
        StatusBarColor()
        content()
    }
}

@Composable
private fun StatusBarColor() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(MaterialTheme.colors.surface)
}