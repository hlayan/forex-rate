package com.hlayan.forexrate.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.hlayan.forexrate.ui.shared.currency.Currency
import com.hlayan.forexrate.ui.shared.currency.CurrencyList
import com.hlayan.forexrate.ui.shared.sorting.SortBy
import com.hlayan.forexrate.ui.shared.sorting.SortMenu
import com.hlayan.forexrate.ui.theme.DefaultPreviewTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onSearch: () -> Unit = {},
    onNavigateToConverter: (Currency) -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
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

    ModalBottomSheetLayout(
        modifier = modifier,
        sheetState = sheetState,
        sheetShape = RectangleShape,
        sheetContent = {
            SortBy(viewModel.selectedOrder) { sortOrder ->
                scope.launch { sheetState.hide() }
                sortOrder ?: return@SortBy
                viewModel.updateSortOrder(sortOrder)
            }
        }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                Column {
                    TopAppBar(
                        title = { Text("Forex Rate") },
                        navigationIcon = { NavigationIcon() },
                        actions = {
                            RefreshIcon { viewModel.syncExchangeRates() }
                            SortIcon()
                        },
                        backgroundColor = MaterialTheme.colors.surface,
                        contentColor = MaterialTheme.colors.onSurface
                    )
                    if (viewModel.isLoading) LinearProgressIndicator(Modifier.fillMaxWidth())
                }
            },
            content = {
                CurrencyList(Modifier.fillMaxSize(), viewModel.currencies) {
                    onNavigateToConverter(it)
                }
            }
        )
    }

    val firstItemOffset = derivedStateOf { lazyGridState.firstVisibleItemScrollOffset }
    BackHandler(
        sheetState.isVisible ||
                scaffoldState.drawerState.isOpen ||
                firstItemOffset.value != 0
    ) {
        when {
            sheetState.isVisible -> scope.launch { sheetState.hide() }
            scaffoldState.drawerState.isOpen -> scope.launch { scaffoldState.drawerState.close() }
            lazyGridState.firstVisibleItemScrollOffset != 0 -> {
                scope.launch { lazyGridState.animateScrollToItem(0) }
            }
        }
    }
}

@Composable
fun RefreshIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DefaultPreviewTheme { HomeScreen() }
}