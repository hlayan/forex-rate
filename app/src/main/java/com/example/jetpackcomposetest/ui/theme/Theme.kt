package com.example.jetpackcomposetest.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColor = darkColors(
    primary = Orange,
    primaryVariant = Orange,
    secondary = Orange
)

private val LightColor = lightColors(
    primary = Orange,
    primaryVariant = Orange,
    secondary = Orange,
    secondaryVariant = Orange

    /* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/
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
    systemUiController.setStatusBarColor(
        if (isDarkMode) MaterialTheme.colors.surface else MaterialTheme.colors.primary
    )
}