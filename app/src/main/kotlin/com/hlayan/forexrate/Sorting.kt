package com.hlayan.forexrate

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SortMenu(expanded: Boolean, selectedOrder: SortOrder, onSelect: (SortOrder?) -> Unit = {}) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onSelect(null) }
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            Text(
                text = "Sort by",
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 20.dp)
            )

            IconButton(
                modifier = Modifier.padding(end = 8.dp),
                onClick = { onSelect(null) }
            ) {
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Close")
            }
        }

        Divider(Modifier.padding(bottom = 10.dp))

        SortOrder.values().forEach { sortOrder ->
            RadioItem(
                title = sortOrder.title,
                selected = sortOrder == selectedOrder
            ) {
                onSelect(sortOrder.takeIf { it != selectedOrder })
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
            .clickable { onClick() }
            .height(48.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = null)
        Text(text = title)
    }
}

enum class SortOrder(val title: String) {
    ASCENDING("Ascending"),
    DESCENDING("Descending"),
    LARGEST_FIRST("Largest first"),
    SMALLEST_FIRST("Smallest first")
}

fun List<Currency>.sortBy(order: SortOrder): List<Currency> {
    return when (order) {
        SortOrder.ASCENDING -> sortedBy { it.name }
        SortOrder.DESCENDING -> sortedByDescending { it.name }
        SortOrder.LARGEST_FIRST -> sortedByDescending { it.rate }
        SortOrder.SMALLEST_FIRST -> sortedBy { it.rate }
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