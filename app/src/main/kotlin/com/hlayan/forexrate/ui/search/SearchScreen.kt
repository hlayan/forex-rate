package com.hlayan.forexrate.ui.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.hlayan.forexrate.shared.extension.spacesTrimmed
import com.hlayan.forexrate.ui.converter.Converter
import com.hlayan.forexrate.ui.shared.currency.Currency
import com.hlayan.forexrate.ui.shared.currency.CurrencyList

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit = {}
) {
    val selectedCurrency = rememberSaveable { mutableStateOf(Currency()) }
    val showConverter = rememberSaveable { mutableStateOf(false) }

    val currencies = viewModel.currencies.collectAsState()

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

            val searchedCurrencies = remember { mutableStateOf(emptyList<Currency>()) }

            val focusManager = LocalFocusManager.current
            val inputted = remember { mutableStateOf(TextFieldValue()) }

            val isInputNotBlank =
                remember { derivedStateOf { inputted.value.text.isNotBlank() } }

            TopAppBar(
                title = {
                    val focusRequester = remember { FocusRequester() }

                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }

                    LaunchedEffect(inputted.value) {
                        searchedCurrencies.value = if (inputted.value.text.isBlank()) emptyList()
                        else {
                            currencies.value.filter { currency ->
                                inputted.value.text.spacesTrimmed.let {
                                    currency.name.spacesTrimmed.contains(it, true) ||
                                            currency.fullName.spacesTrimmed.contains(it, true)
                                }
                            }
                        }
                    }

                    Box {

                        if (!isInputNotBlank.value) {
                            Text(
                                text = "Search",
                                fontSize = 18.sp,
                                color = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                            )
                        }

                        BasicTextField(
                            value = inputted.value,
                            onValueChange = { input ->
                                inputted.value = input
                            },
                            textStyle = TextStyle(
                                fontSize = 18.sp,
                                color = MaterialTheme.colors.onSurface
                            ),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            ),
                            cursorBrush = SolidColor(MaterialTheme.colors.onSurface)
                        )
                    }
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
                    if (isInputNotBlank.value) {
                        IconButton(
                            onClick = {
                                focusManager.moveFocus(FocusDirection.Previous)
                                inputted.value = TextFieldValue()
                                searchedCurrencies.value = emptyList()
                            }
                        ) {
                            Icon(Icons.Filled.Close, contentDescription = "Clean Input")
                        }
                    }
                },
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.onSurface,
                elevation = 0.dp
            )
            Divider()

            CurrencyList(Modifier.fillMaxSize(), searchedCurrencies.value) {
                focusManager.clearFocus()
                selectedCurrency.value = it
                showConverter.value = true
            }
        }
    }

    BackHandler { onNavigateUp() }
}