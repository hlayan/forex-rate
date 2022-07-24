package com.hlayan.forexrate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.hlayan.forexrate.ui.converter.Converter
import com.hlayan.forexrate.ui.home.HomeScreen
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
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavHostScreen.Home.name) {
        composable(NavHostScreen.Home.name) {
            HomeScreen {
                selectedCurrency.value = it
                navController.navigate(NavHostScreen.Converter.name)
            }
        }
        composable(NavHostScreen.Converter.name) {
            Converter(selectedCurrency.value) { navController.navigateUp() }
        }
    }

//    val showConverter = rememberSaveable { mutableStateOf(false) }
//
//    HomeScreen {
//        selectedExchangeModel.value = it
//        showConverter.value = true
//    }
//
//    if (showConverter.value) {
//        Converter(exchangeModel = selectedExchangeModel.value) {
//            showConverter.value = false
//        }
//    }
//
//    BackHandler(showConverter.value) {
//        showConverter.value = false
//    }

}

enum class NavHostScreen { Home, Converter }

inline fun <reified T> String.getModel(): T? {
    return try {
        Gson().fromJson(this, T::class.java)
    } catch (exception: Throwable) {
        Timber.d(exception.toString())
        null
    }
}

val Any.json: String? get() = Gson().toJson(this)

val String.safeDouble get() = replace(",", "").toDouble()