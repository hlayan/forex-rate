package com.example.jetpackcomposetest

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

@Composable
fun CalculateDialog(openDialog: MutableState<Boolean>, exchangeModel: ExchangeModel) {
    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            Surface(shape = RoundedCornerShape(8.dp)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    val exchangeRate = exchangeModel.rate
                        .replace(",", "").toBigDecimal()

                    val selectedCurrency = remember { mutableStateOf(exchangeModel.currency) }
                    val differRate = remember { mutableStateOf("1") }
                    val mmkRate = remember { mutableStateOf(exchangeRate.toString()) }

                    CurrencyRow(differRate.value, selectedCurrency, exchangeModel.currency)
                    CurrencyRow(mmkRate.value, selectedCurrency)

                    KeysPad(onClick = { inputNumber ->
                        if (selectedCurrency.value == exchangeModel.currency) {
                            differRate.updateValue(inputNumber) {
                                mmkRate.value = (it * exchangeRate).toString()
                            }
                        } else {
                            mmkRate.updateValue(inputNumber) {
                                differRate.value = (it / exchangeRate).toString()
                            }
                        }
                    }) {
                        if (selectedCurrency.value == exchangeModel.currency) {
                            differRate.value =
                                differRate.value.run { substring(0, lastIndex).ifBlank { "0" } }
                            mmkRate.value =
                                (differRate.value.toBigDecimal() * exchangeRate).toString()
                        } else {
                            mmkRate.value =
                                mmkRate.value.run { substring(0, lastIndex).ifBlank { "0" } }
                            differRate.value =
                                (mmkRate.value.toBigDecimal() / exchangeRate).toString()
                        }
                    }

                    Button(
                        onClick = { openDialog.value = false },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Close", color = Color.White)
                    }
                }
            }
        }
    }
}

fun MutableState<String>.updateValue(inputNumber: String, onFinish: (BigDecimal) -> Unit) {
    when (inputNumber) {
        "0" -> if (value == "0") return
        "." -> {
            if (value.contains('.')) return
            if (value == "0") {
                value += inputNumber
                return
            }
        }
    }
    if (value == "0") value = inputNumber else value += inputNumber
    onFinish(value.toBigDecimal())
}

@Preview(showBackground = true)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun KeysPad(onClick: (String) -> Unit = {}, onDelete: () -> Unit = {}) {
    val numbersList = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "0")
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(numbersList) {
            OutlinedButton(
                onClick = { onClick(it) },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.height(40.dp)
            ) {
                Text(text = it, color = Color.Black, fontSize = 16.sp)
            }
        }
        item {
            OutlinedButton(
                onClick = onDelete,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.height(40.dp)
            ) {
                Icon(imageVector = Icons.Filled.DeleteOutline, contentDescription = "Delete")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CurrencyRow(
    rate: String,
    selectedCurrency: MutableState<Currencies>,
    currency: Currencies = Currencies.MMK
) {
    val primaryColor = MaterialTheme.colors.primary
    val normalColor = Color.DarkGray.copy(0.12f)
    Surface(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            1.dp, if (selectedCurrency.value == currency) primaryColor else normalColor
        ),
        onClick = { selectedCurrency.value = currency }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = currency.run { "$flagEmoji $name" },
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colors.primary,
            )
            Text(text = rate, textAlign = TextAlign.Right, fontSize = 18.sp, maxLines = 1)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyRowPreview() {
    val selectedCurrency = remember { mutableStateOf(Currencies.MMK) }
    CurrencyRow("1", selectedCurrency)
}