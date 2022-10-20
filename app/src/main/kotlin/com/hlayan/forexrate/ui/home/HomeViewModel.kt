package com.hlayan.forexrate.ui.home

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hlayan.forexrate.data.local.sharedPreferences
import com.hlayan.forexrate.data.local.sharedRates
import com.hlayan.forexrate.data.local.sortOrder
import com.hlayan.forexrate.data.local.timestamp
import com.hlayan.forexrate.data.remote.CBMRepository
import com.hlayan.forexrate.ui.shared.currency.Currency
import com.hlayan.forexrate.ui.shared.currency.Rates
import com.hlayan.forexrate.ui.shared.currency.currencies
import com.hlayan.forexrate.ui.shared.sorting.SortOrder
import com.hlayan.forexrate.ui.shared.sorting.sortBy
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val cbmRepository: CBMRepository
) : ViewModel() {

    private val sharedPref = context.sharedPreferences

    private val _isLoading = mutableStateOf(false)
    val isLoading get() = _isLoading.value

    private val _isExpandSortMenu = mutableStateOf(false)
    val isExpandSortMenu get() = _isExpandSortMenu.value

    private val _selectedOrder = mutableStateOf(sharedPref.sortOrder)
    val selectedOrder get() = _selectedOrder.value

    private var sharedCurrencies = sharedPref.sharedRates?.currencies ?: emptyList()

    private val _currencies = mutableStateOf(emptyList<Currency>())
    val currencies get() = _currencies.value

    private val _timestamp = mutableStateOf(sharedPref.timestamp)
    val timestamp get() = _timestamp.value

    private fun updateTimestamp(value: String) {
        _timestamp.value = value
        sharedPref.timestamp = value
    }

    private fun updateRates(value: Rates) {
        viewModelScope.launch {
            sharedCurrencies = value.currencies
            sharedPref.sharedRates = value
            updateCurrencies()
        }
    }

    fun updateSortOrder(value: SortOrder) {
        viewModelScope.launch {
            _selectedOrder.value = value
            sharedPref.sortOrder = value
            updateCurrencies(value)
        }
    }

    fun updateCurrencies(value: SortOrder = _selectedOrder.value) {
        viewModelScope.launch {
            _currencies.value = sharedCurrencies.sortBy(value)
        }
    }

    fun syncExchangeRates() {
        _isLoading.value = if (_isLoading.value) return else true
        viewModelScope.launch {
            cbmRepository.getLatestRates().run {
                _isLoading.value = false
                this ?: return@run
                if (_timestamp.value == timestamp) return@run
                updateRates(rates)
                updateTimestamp(timestamp)
            }
        }
    }

    fun closeSortMenu() {
        _isExpandSortMenu.value = false
    }

    fun openSortMenu() {
        _isExpandSortMenu.value = true
    }
}