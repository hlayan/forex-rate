package com.example.jetpackcomposetest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposetest.ui.home.HomeScreen
import com.example.jetpackcomposetest.ui.home.HomeViewModel
import com.example.jetpackcomposetest.ui.theme.MMKExchangeTheme
import com.example.jetpackcomposetest.ui.theme.isDarkMode
import com.google.gson.Gson

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
    var selectedExchangeModel = rememberSaveable { ExchangeModel() }
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavHostScreen.Home.name) {
        composable(NavHostScreen.Home.name) {
            val context = LocalContext.current
            val homeViewModel: HomeViewModel = viewModel(initializer = { HomeViewModel(context) })
            HomeScreen(homeViewModel) {
                selectedExchangeModel = it
                navController.navigate(NavHostScreen.Converter.name)
            }
        }
        composable(NavHostScreen.Converter.name) {
            Converter(selectedExchangeModel) { navController.navigateUp() }
        }
    }
}

enum class NavHostScreen { Home, Converter }

inline fun <reified T> String.getModel(): T? {
    return try {
        Gson().fromJson(this, T::class.java)
    } catch (exception: Throwable) {
        Log.d("Exception", exception.toString())
        null
    }
}

val Any.json: String? get() = Gson().toJson(this)

val String.safeDouble get() = replace(",", "").toDouble()