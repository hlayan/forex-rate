package com.hlayan.forexrate.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Sync
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hlayan.forexrate.ui.shared.currency.Currency
import com.hlayan.forexrate.ui.shared.currency.CurrencyList
import com.hlayan.forexrate.ui.shared.sorting.SortMenu
import com.hlayan.forexrate.ui.theme.DefaultPreviewTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onSearch: () -> Unit = {},
    onNavigateToConverter: (Currency) -> Unit = {}
) {
    val lazyGridState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    @Composable
    fun SortIcon() {
        IconButton(onClick = {
            scope.launch { viewModel.openSortMenu() }
        }) {
            Icon(imageVector = Icons.Filled.Sort, contentDescription = "Sort")
        }

        SortMenu(
            expanded = viewModel.isExpandSortMenu,
            selectedOrder = viewModel.selectedOrder
        ) {
            viewModel.closeSortMenu()
            it?.let { viewModel.updateSortOrder(it) }
        }
    }

    @Composable
    fun NavigationIcon() {
        IconButton(
            onClick = onSearch
        ) {
            Icon(Icons.Filled.Search, Icons.Filled.Search.name)
        }
    }

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            Column {
                TopAppBar(
                    title = {
//                        Column {
//                            Text("Forex Rate")
////                            Text(
////                                viewModel.timestamp?.localDateTime?.uiFormat.toString(),
////                                fontSize = 12.sp,
////                                fontWeight = FontWeight.Normal,
////                                modifier = Modifier.alpha(0.78f)
////                            )
//                        }
                    },
                    navigationIcon = { NavigationIcon() },
                    actions = {
//                        IconButton(onClick = { /*TODO*/ }) {
//                            Icon(imageVector = Icons.Outlined.Settings, contentDescription = null)
//                        }
                        RefreshIconButton { viewModel.updateCurrencies() }
                        SortIcon()
                    },
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.onSurface,
                    elevation = 0.dp
                )
//                Divider(thickness = 1.dp)
                if (viewModel.isLoading) LinearProgressIndicator(Modifier.fillMaxWidth())
            }
        },
        content = { padding ->
            Column(Modifier.padding(padding)) {
                CurrencyList(Modifier.fillMaxSize(), viewModel.currencies) {
                    onNavigateToConverter(it)
                }
            }
        }
    )


    val firstItemOffset = remember {
        derivedStateOf { lazyGridState.firstVisibleItemScrollOffset }
    }
    BackHandler(firstItemOffset.value != 0) {
        if (lazyGridState.firstVisibleItemScrollOffset != 0) {
            scope.launch { lazyGridState.animateScrollToItem(0) }
        }
    }
}

@Composable
fun RefreshIconButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Sync,
            contentDescription = "Refresh"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DefaultPreviewTheme { HomeScreen() }
}