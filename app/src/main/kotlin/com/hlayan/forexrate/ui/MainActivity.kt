package com.hlayan.forexrate.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.zIndex
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.gson.Gson
import com.hlayan.forexrate.data.local.isDarkMode
import com.hlayan.forexrate.data.local.sharedPreferences
import com.hlayan.forexrate.ui.converter.Converter
import com.hlayan.forexrate.ui.home.HomeScreen
import com.hlayan.forexrate.ui.search.SearchScreen
import com.hlayan.forexrate.ui.setting.SettingScreen
import com.hlayan.forexrate.ui.shared.currency.Currency
import com.hlayan.forexrate.ui.theme.MMKExchangeTheme
import com.hlayan.forexrate.ui.theme.isDarkMode
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        isDarkMode = sharedPreferences.isDarkMode
        setContent { MMKExchangeTheme { MainNavigation() } }
    }
}

@Composable
fun MainNavigation() {
    val selectedCurrency = rememberSaveable { mutableStateOf(Currency()) }
    val showConverter = rememberSaveable { mutableStateOf(false) }
    val showSearch = rememberSaveable { mutableStateOf(false) }
    val selectedScreen = remember { mutableStateOf(NavHostScreen.Home) }

    if (showConverter.value) {
        Converter(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxSize(),
            currency = selectedCurrency.value
        ) {
            showConverter.value = false
        }
    }

    if (showSearch.value) {
        SearchScreen(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxSize(),
            onNavigateUp = {
                showSearch.value = false
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            HomeScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(if (selectedScreen.value == NavHostScreen.Home) 1f else 0f),
                onSearch = {
                    showSearch.value = true
                }
            ) {
                selectedCurrency.value = it
                showConverter.value = true
            }

            SettingScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(if (selectedScreen.value == NavHostScreen.Setting) 1f else 0f),
            )

            BackHandler(selectedScreen.value != NavHostScreen.Home) {
                selectedScreen.value = NavHostScreen.Home
            }
        }

        BottomNavigation(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ) {
            NavHostScreen.values().forEach {
                BottomNavigationItem(
                    selected = it == selectedScreen.value,
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                    icon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.name
                        )
                    },
                    label = {
                        Text(text = it.name)
                    },
                    onClick = {
                        selectedScreen.value = it
                    }
                )
            }
        }
    }

    BackHandler(showConverter.value) {
        showConverter.value = false
    }

    BackHandler(showSearch.value) {
        showSearch.value = false
    }

}

enum class NavHostScreen(val icon: ImageVector) {
    Home(Icons.Default.Home),
    Setting(Icons.Default.Settings)
}

inline fun <reified T> String.getModel(): T? {
    return try {
        Gson().fromJson(this, T::class.java)
    } catch (exception: Throwable) {
        Timber.d(exception.toString())
        null
    }
}

val Any.json: String? get() = Gson().toJson(this)

val String.commaRemoved get() = replace(",", "").toDouble()