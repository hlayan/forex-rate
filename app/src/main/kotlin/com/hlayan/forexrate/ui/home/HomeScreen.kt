package com.hlayan.forexrate.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hlayan.forexrate.Currency
import com.hlayan.forexrate.CurrencyItem
import com.hlayan.forexrate.SortBy
import com.hlayan.forexrate.SortMenu
import com.hlayan.forexrate.ui.theme.DefaultPreviewTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToConverter: (Currency) -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val lazyGridState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    @Composable
    fun SortIcon() {
        Column {
            IconButton(onClick = {
                scope.launch { sheetState.show() }
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
    }

    @Composable
    fun NavigationIcon() {
        IconButton(
            onClick = { }
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
            bottomBar = {

            },
            content = { paddingValues ->
                val contentDp = remember { 16.dp }
                LazyVerticalGrid(
                    cells = GridCells.Adaptive(300.dp),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(contentDp),
                    horizontalArrangement = Arrangement.spacedBy(contentDp),
                    contentPadding = PaddingValues(
                        contentDp,
                        contentDp,
                        contentDp,
                        bottom = paddingValues.calculateBottomPadding() + contentDp
                    ),
                    state = lazyGridState
                ) {
                    items(viewModel.currencies) {
                        CurrencyItem(it) { onNavigateToConverter(it) }
                    }
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