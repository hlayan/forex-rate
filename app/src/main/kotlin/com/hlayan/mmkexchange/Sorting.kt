package com.hlayan.mmkexchange

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun SortByDialog(
    openDialog: MutableState<Boolean>,
    selectedOrder: SortOrder,
    onClick: (SortOrder) -> Unit = {}
) {
    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            Surface(shape = RoundedCornerShape(8.dp)) {
                SortBy(selectedOrder) {
                    openDialog.value = false
                    it?.let { onClick(it) }
                }
            }
        }
    }
}

@Composable
fun SortBy(selectedOrder: SortOrder, onSelect: (SortOrder?) -> Unit = {}) {
    Column{
        Text(
            text = "Sort by",
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold
        )
        Divider(Modifier.padding(bottom = 10.dp))

        SortOrder.values().forEach { sortOrder ->
            RadioItem(
                title = sortOrder.title,
                selected = sortOrder == selectedOrder
            ) {
                onSelect(sortOrder.takeIf { it != selectedOrder })
            }
        }

        Button(
            onClick = { onSelect(null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Cancel")
        }
    }
}

@Composable
fun RadioItem(title: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .height(48.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
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

fun List<ExchangeModel>.sortBy(order: SortOrder): List<ExchangeModel> {
    return when (order) {
        SortOrder.ASCENDING -> sortedBy { it.currency.name }
        SortOrder.DESCENDING -> sortedByDescending { it.currency.name }
        SortOrder.LARGEST_FIRST -> sortedByDescending { it.rate.safeDouble }
        SortOrder.SMALLEST_FIRST -> sortedBy { it.rate.safeDouble }
    }
}

@Preview(showBackground = true)
@Composable
fun SortByPreview() {
    val selectedOrder by remember { mutableStateOf(SortOrder.LARGEST_FIRST) }
    SortBy(selectedOrder)
}