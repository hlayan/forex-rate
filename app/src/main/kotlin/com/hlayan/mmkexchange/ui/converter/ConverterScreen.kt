package com.hlayan.mmkexchange.ui.converter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hlayan.mmkexchange.Currency
import com.hlayan.mmkexchange.ui.theme.DefaultPreviewTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Converter(currency: Currency, onNavigateUp: () -> Unit = {}) {
    Surface(Modifier.fillMaxSize()) {
        Column {
            TopAppBar(
                title = { Text(text = "Exchange Converter") },
                navigationIcon = {
                    val keyboardController = LocalSoftwareKeyboardController.current
                    IconButton(onClick = {
                        keyboardController?.hide()
                        onNavigateUp()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "Navigate up")
                    }
                }
            )

            val checkedState = rememberSaveable { mutableStateOf(false) }

            Card(
                modifier = Modifier.padding(vertical = 10.dp),
                shape = RectangleShape
            ) {
                if (checkedState.value) {
                    CustomConverter(currency)
                } else {
                    ExchangeConverter(currency)
                }
            }

//        Card(
//            onClick = { checkedState.value = checkedState.value.not() },
//            shape = RectangleShape
//        ) {
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column {
//                    Text(text = "Safe Mode", fontSize = 16.sp)
//                    Text(
//                        text = "Determine whether input should be safe or not.",
//                        fontSize = 13.sp,
//                        color = Color.Gray
//                    )
//                }
//                Switch(checked = checkedState.value, onCheckedChange = null)
//            }
//        }

        }
    }
}

@Composable
fun ExchangeConverter(currency: Currency) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {

        val exchangeRate = rememberSaveable { currency.rate.replace(",", "").toDouble() }

        var otherRate by rememberSaveable { mutableStateOf("1") }
        var mmkRate by rememberSaveable { mutableStateOf(currency.rate) }

        ExchangeTextField(
            text = otherRate,
            labelText = currency.run { "$name $flagEmoji" },
            placeholderText = currency.name
        ) { text ->
            if (!text.matches(Regex(floatingPattern))) return@ExchangeTextField
            otherRate = text
            mmkRate = try {
                (text.toDouble() * exchangeRate).formattedString
            } catch (e: Throwable) {
                ""
            }
        }

        ExchangeTextField(text = mmkRate) { text ->
            if (!text.matches(Regex(floatingPattern))) return@ExchangeTextField
            mmkRate = text
            otherRate = try {
                (text.toDouble() / exchangeRate).formattedString
            } catch (e: Throwable) {
                ""
            }
        }
    }
}

private const val floatingPattern = "([0-9]{0,16}([.][0-9]{0,2})?)"

//private const val floatingPattern = "[+-]?([0-9]{0,16}([.][0-9]{0,2})?|[.][0-9]{0,2})"
private const val floatingPattern1 = "^(\\d+\\.?\\d*)|(\\.\\d+)$"
//private const val floatingPattern2 = "^(\\d+[.]?\\d*)|([.]\\d+)$"

@Composable
fun CustomConverter(currency: Currency) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {

        val exchangeRate = currency.rate.replace(",", "").toDouble()

        val otherRate = remember { mutableStateOf("1") }
        val mmkRate = remember { mutableStateOf(currency.rate) }

        ExchangeTextField(
            text = TextFieldValue(otherRate.value, TextRange(otherRate.value.length)),
            labelText = currency.run { "$name $flagEmoji" },
            placeholderText = currency.name
        ) { text ->
            if (!text.text.matches(Regex(floatingPattern))) return@ExchangeTextField
            otherRate.value = text.text
            try {
                mmkRate.value = (text.text.toDouble() * exchangeRate).formattedString
            } catch (e: NumberFormatException) {
                mmkRate.value = ""
            }
        }

        ExchangeTextField(
            text = TextFieldValue(
                mmkRate.value,
                TextRange(mmkRate.value.length)
            )
        ) { text ->
            if (!text.text.matches(Regex(floatingPattern1))) return@ExchangeTextField
            mmkRate.value = text.text
            try {
                otherRate.value = (text.text.toDouble() / exchangeRate).formattedString
            } catch (e: NumberFormatException) {
                otherRate.value = ""
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConverterPreview() {
    DefaultPreviewTheme {
        Converter(Currency())
    }
}