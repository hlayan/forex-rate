package com.hlayan.forexrate.ui.shared.currency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CurrencyList(
    modifier: Modifier = Modifier,
    currencies: List<Currency> = emptyList(),
    padding: Dp = remember { 16.dp },
    onItemClick: (Currency) -> Unit = {}
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(300.dp),
        verticalArrangement = Arrangement.spacedBy(padding),
        horizontalArrangement = Arrangement.spacedBy(padding),
        contentPadding = PaddingValues(padding),
    ) {
        items(currencies) {
            CurrencyItem(currency = it, onClick = onItemClick)
        }
    }
}