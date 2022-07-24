package com.hlayan.forexrate.ui.home

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hlayan.forexrate.*
import com.hlayan.forexrate.ui.theme.DefaultPreviewTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
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
                viewModel.openSortMenu()
//            scope.launch { sheetState.animateTo(ModalBottomSheetValue.Expanded) }
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
            onClick = { scope.launch { scaffoldState.drawerState.open() } }
        ) {
            Icon(Icons.Filled.Menu, Icons.Filled.Menu.name)
        }
    }

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
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
                        title = { Text("MMK Exchange") },
                        navigationIcon = { NavigationIcon() },
                        actions = {
                            RefreshIcon { viewModel.syncExchangeRates() }
                            SortIcon()
                        }
                    )
                    if (viewModel.isLoading) LinearProgressIndicator(Modifier.fillMaxWidth())
                }
            },
            drawerGesturesEnabled = true,
            drawerContent = {
                NavigationDrawerHeader(viewModel.timestamp)
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