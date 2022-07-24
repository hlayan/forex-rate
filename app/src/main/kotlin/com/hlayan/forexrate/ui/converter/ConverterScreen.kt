package com.hlayan.forexrate.ui.converter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hlayan.forexrate.Currency
import com.hlayan.forexrate.ui.theme.DefaultPreviewTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Converter(
    currency: Currency,
    viewModel: ConverterViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit = {}
) {
    Surface(Modifier.fillMaxSize()) {
        Column {
            TopAppBar(
                title = { Text(text = "Converter") },
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

            Card(
                modifier = Modifier.padding(vertical = 10.dp),
                shape = RectangleShape
            ) {
                var otherRate by remember { mutableStateOf(TextFieldValue("1.00")) }
                var mmkRate by remember { mutableStateOf(TextFieldValue(currency.rate.decimalFormat)) }

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    ConverterTextField(
                        value = otherRate,
                        labelText = currency.run { "$name $flagEmoji" },
                        placeholderText = currency.name
                    ) {
                        otherRate = it
                        val input = it.text.toDoubleOrNull()
                        mmkRate = if (input == null) TextFieldValue()
                        else {
                            val result = input * currency.rate
                            TextFieldValue(result.decimalFormat)
                        }
                    }

                    ConverterTextField(value = mmkRate) {
                        mmkRate = it
                        val input = it.text.toDoubleOrNull()
                        otherRate = if (input == null) TextFieldValue()
                        else {
                            val result = input / currency.rate
                            TextFieldValue(result.decimalFormat)
                        }
                    }
                }
            }
        }
    }
}

private const val floatingPattern = "([0-9]{0,16}([.][0-9]{0,2})?)"

@Preview(showBackground = true)
@Composable
fun ConverterPreview() {
    DefaultPreviewTheme {
        Converter(Currency())
    }
}