package com.example.jetpackcomposetest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposetest.retrofit.getLatestRates
import com.example.jetpackcomposetest.ui.theme.DefaultPreviewTheme
import com.example.jetpackcomposetest.ui.theme.MMKExchangeTheme
import com.example.jetpackcomposetest.ui.theme.isDarkMode
import com.google.gson.Gson
import kotlinx.coroutines.launch

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
    NavHost(navController = navController, startDestination = "Home") {
        composable("Home") {
            HomeScreen {
                selectedExchangeModel = it
                navController.navigate("Converter")
            }
        }
        composable("Converter") {
            Converter(selectedExchangeModel) { navController.navigateUp() }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(onNavigateToConverter: (ExchangeModel) -> Unit = {}) {

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val lazyListState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val sharedPref = LocalContext.current.sharedPreferences
    var selectedOrder by rememberSaveable { mutableStateOf(sharedPref.sortOrder) }
    var exchangeList by rememberSaveable {
        val exchangeRates = sharedPref.sharedRates?.exchangesList ?: emptyList()
        mutableStateOf(exchangeRates.sortBy(selectedOrder))
    }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var timestamp by rememberSaveable { mutableStateOf(sharedPref.getString("timestamp", null)) }

    fun refreshExchangeRates() {
        isLoading = if (isLoading) return else true
        getLatestRates {
            isLoading = false
            it ?: return@getLatestRates
            val oldTimestamp = sharedPref.getString("timestamp", null)
            if (oldTimestamp == it.timestamp) return@getLatestRates
            scope.launch { exchangeList = it.rates.exchangesList.sortBy(selectedOrder) }
            scope.launch { timestamp = it.timestamp }
            scope.launch { sharedPref.edit { putString("timestamp", it.timestamp) } }
            scope.launch { sharedPref.sharedRates = it.rates }
        }
    }

    @Composable
    fun SortIcon() {
        IconButton(onClick = {
            scope.launch { sheetState.animateTo(ModalBottomSheetValue.Expanded) }
        }) {
            Icon(imageVector = Icons.Filled.Sort, contentDescription = "Sort")
        }
    }

    @Composable
    fun NavigationIcon() {
        IconButton(
            onClick = { scope.launch { scaffoldState.drawerState.open() } }
        ) {
            Icon(Icons.Filled.Menu, "menu")
        }
    }

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
        sheetContent = {
//            LaunchedEffect(exchangesList) { sheetState.hide() }
            SortBy(selectedOrder) { sortOrder ->
                scope.launch { sheetState.hide() }
                sortOrder ?: return@SortBy
                scope.launch { exchangeList = exchangeList.sortBy(sortOrder) }
                scope.launch { selectedOrder = sortOrder }
                scope.launch { sharedPref.sortOrder = sortOrder }
            }
        }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                Column {
                    TopAppBar(
                        title = { Text("MMK Exchange") },
                        navigationIcon = { NavigationIcon() },
                        actions = {
                            RefreshIcon { refreshExchangeRates() }
                            SortIcon()
                        }
                    )
                    if (isLoading) LinearProgressIndicator(Modifier.fillMaxWidth())
                }
            },
            drawerGesturesEnabled = true,
            drawerContent = {
                NavigationDrawerHeader(timestamp)
                Divider(Modifier.padding(bottom = 8.dp))
                LazyColumn {
                    items(3) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Setting",
                                modifier = Modifier.padding(24.dp, 12.dp)
                            )
                            Text(text = "Setting")
                        }
                    }
                }
            },
            content = {
                val contentDp = remember { 16.dp }
                LazyVerticalGrid(
                    cells = GridCells.Adaptive(300.dp),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(contentDp),
                    horizontalArrangement = Arrangement.spacedBy(contentDp),
                    contentPadding = PaddingValues(contentDp),
                    state = lazyListState
                ) {
                    items(exchangeList) {
                        ExchangeList(it) { onNavigateToConverter(it) }
                    }
                }
            }
        )
    }
    BackHandler(
        sheetState.isVisible ||
                scaffoldState.drawerState.isOpen ||
                lazyListState.firstVisibleItemScrollOffset != 0
    ) {
        when {
            sheetState.isVisible -> scope.launch { sheetState.hide() }
            scaffoldState.drawerState.isOpen -> scope.launch { scaffoldState.drawerState.close() }
            lazyListState.firstVisibleItemScrollOffset != 0 -> {
                scope.launch { lazyListState.animateScrollToItem(0) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DefaultPreviewTheme { HomeScreen() }
}

@Composable
fun RefreshIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
    }
}

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