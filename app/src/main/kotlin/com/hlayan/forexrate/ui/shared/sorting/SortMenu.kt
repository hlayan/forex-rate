package com.hlayan.forexrate.ui.shared.sorting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hlayan.forexrate.ui.shared.currency.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun SortMenu(expanded: Boolean, selectedOrder: SortOrder, onSelect: (SortOrder?) -> Unit = {}) {
    DropdownMenu(
        expanded = expanded, onDismissRequest = { onSelect(null) }
    ) {
        SortOrder.values().forEach { sortOrder ->
            DropdownMenuItem(onClick = { onSelect(sortOrder.takeIf { it != selectedOrder }) }) {
                RadioButton(selected = sortOrder == selectedOrder, onClick = null)
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = sortOrder.title)
            }
        }
    }
}

@Composable
fun SortBy(selectedOrder: SortOrder, onSelect: (SortOrder?) -> Unit = {}) {
    Column {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = { onSelect(null) }
            ) {
                Icon(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                    imageVector = Icons.Default.Close, contentDescription = "Close"
                )
            }

            Text(
                text = "Sort by",
                fontSize = 20.sp,
            )
        }

        Divider(Modifier.padding(bottom = 10.dp))

        LazyColumn {
            items(SortOrder.values()) { sortOrder ->
                RadioItem(
                    title = sortOrder.title,
                    selected = sortOrder == selectedOrder
                ) {
                    onSelect(sortOrder.takeIf { it != selectedOrder })
                }
            }
        }

        Spacer(modifier = Modifier.size(10.dp))
    }
}

@Composable
fun RadioItem(title: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
            selected = selected,
            onClick = null
        )
        Text(text = title)
    }
}

enum class SortOrder(val title: String) {
    ASCENDING("Ascending"),
    DESCENDING("Descending"),
    LARGEST_FIRST("Largest first"),
    SMALLEST_FIRST("Smallest first")
}

suspend fun List<Currency>.sortBy(order: SortOrder): List<Currency> {
    return withContext(Dispatchers.IO) {
        when (order) {
            SortOrder.ASCENDING -> sortedBy { it.name }
            SortOrder.DESCENDING -> sortedByDescending { it.name }
            SortOrder.LARGEST_FIRST -> sortedByDescending { it.rate }
            SortOrder.SMALLEST_FIRST -> sortedBy { it.rate }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SortByPreview() {
    SortBy(SortOrder.ASCENDING)
}

@Preview(showBackground = true)
@Composable
fun SortMenuPreview() {
    SortMenu(true, SortOrder.ASCENDING)
}