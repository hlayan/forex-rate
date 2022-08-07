package com.hlayan.forexrate.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.hlayan.forexrate.data.local.sharedPreferences
import com.hlayan.forexrate.data.local.sharedRates
import com.hlayan.forexrate.shared.extension.spacesTrimmed
import com.hlayan.forexrate.ui.converter.Converter
import com.hlayan.forexrate.ui.shared.currency.Currency
import com.hlayan.forexrate.ui.shared.currency.CurrencyList
import com.hlayan.forexrate.ui.shared.currency.currencies

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit = {}
) {
    val selectedCurrency = rememberSaveable { mutableStateOf(Currency()) }
    val showConverter = rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val fullCurrencies =
        remember { context.sharedPreferences.sharedRates?.currencies ?: emptyList() }

    if (showConverter.value) {
        Converter(
            modifier = Modifier
                .zIndex(2f)
                .fillMaxSize(),
            currency = selectedCurrency.value
        ) {
            showConverter.value = false
        }
    }

    Surface(modifier) {

        Column {

            val currencies = remember { mutableStateOf(emptyList<Currency>()) }

            val focusManager = LocalFocusManager.current
            val inputted = remember { mutableStateOf(TextFieldValue()) }

            TopAppBar(
                title = {
                    val focusRequester = remember { FocusRequester() }

                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }

                    LaunchedEffect(inputted.value) {
                        currencies.value = if (inputted.value.text.isBlank()) emptyList()
                        else {
                            fullCurrencies.filter { currency ->
                                inputted.value.text.spacesTrimmed.let {
                                    currency.name.spacesTrimmed.contains(it, true) ||
                                            currency.fullName.spacesTrimmed.contains(it, true)
                                }
                            }
                        }
                    }

                    BasicTextField(
                        value = inputted.value,
                        onValueChange = { input ->
                            inputted.value = input
                        },
                        textStyle = TextStyle(fontSize = 18.sp),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                        onNavigateUp()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    val isInputNotBlank =
                        remember { derivedStateOf { inputted.value.text.isNotBlank() } }
                    if (isInputNotBlank.value) {
                        IconButton(
                            onClick = {
                                focusManager.moveFocus(FocusDirection.Previous)
                                inputted.value = TextFieldValue()
                                currencies.value = emptyList()
                            }
                        ) {
                            Icon(Icons.Filled.Close, contentDescription = "Clean Input")
                        }
                    }
                },
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.onSurface
            )

            CurrencyList(Modifier.fillMaxSize(), currencies.value) {
                focusManager.clearFocus()
                selectedCurrency.value = it
                showConverter.value = true
            }
        }
    }
}