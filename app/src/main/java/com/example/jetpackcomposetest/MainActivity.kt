package com.example.jetpackcomposetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposetest.ui.home.HomeScreen
import com.example.jetpackcomposetest.ui.home.HomeViewModel
import com.example.jetpackcomposetest.ui.theme.MMKExchangeTheme
import com.example.jetpackcomposetest.ui.theme.isDarkMode
import com.google.gson.Gson
import timber.log.Timber

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
    val selectedExchangeModel = rememberSaveable { mutableStateOf(ExchangeModel()) }
    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = NavHostScreen.Home.name) {
//        composable(NavHostScreen.Home.name) {
//            val context = LocalContext.current
//            val homeViewModel: HomeViewModel = viewModel(initializer = { HomeViewModel(context) })
//            HomeScreen(homeViewModel) {
//                selectedExchangeModel.value = it
//                navController.navigate(NavHostScreen.Converter.name)
//            }
//        }
//        composable(NavHostScreen.Converter.name) {
//            Converter(selectedExchangeModel.value) { navController.navigateUp() }
//        }
//    }

    val showConverter = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val homeViewModel: HomeViewModel = viewModel(initializer = { HomeViewModel(context) })

    HomeScreen(homeViewModel) {
        selectedExchangeModel.value = it
        showConverter.value = true
    }

    if (showConverter.value) {
        Converter(exchangeModel = selectedExchangeModel.value) {
            showConverter.value = false
        }
    }

    BackHandler(showConverter.value) {
        showConverter.value = false
    }

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