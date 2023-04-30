package com.hlayan.forexrate.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hlayan.forexrate.data.local.CurrencyRepository
import com.hlayan.forexrate.ui.shared.currency.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val _currencies = MutableStateFlow(emptyList<Currency>())
    val currencies: StateFlow<List<Currency>> get() = _currencies

    init {
        getLocalCurrencies()
    }

    private fun getLocalCurrencies() {
        viewModelScope.launch {
            _currencies.value = currencyRepository.getCurrencies() ?: emptyList()
        }
    }
}