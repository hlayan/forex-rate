package com.hlayan.forexrate.ui.converter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hlayan.forexrate.Currency
import com.hlayan.forexrate.ui.theme.DefaultPreviewTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Converter(
    currency: Currency,
    modifier: Modifier = Modifier,
    viewModel: ConverterViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit = {}
) {

    viewModel.setSelectedRate(currency.rate)

    Surface(modifier) {
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
                },
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.onSurface
            )

            Card(
                modifier = Modifier.padding(vertical = 10.dp),
                shape = RectangleShape
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    ConverterTextField(
                        value = viewModel.forexRate.value,
                        labelText = currency.run { "$name $flagEmoji" },
                        placeholderText = currency.name,
                        onValueChange = viewModel::updateForexRate
                    )

                    ConverterTextField(
                        value = viewModel.mmkRate.value,
                        onValueChange = viewModel::updateMmkRate
                    )
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