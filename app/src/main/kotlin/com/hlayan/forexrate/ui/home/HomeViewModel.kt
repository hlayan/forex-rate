package com.hlayan.forexrate.ui.home

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hlayan.forexrate.*
import com.hlayan.forexrate.data.remote.ExchangeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val exchangeRepository: ExchangeRepository
) : ViewModel() {

    private val sharedPref = context.sharedPreferences

    private val _isLoading = mutableStateOf(false)
    val isLoading get() = _isLoading.value

    private val _isExpandSortMenu = mutableStateOf(false)
    val isExpandSortMenu get() = _isExpandSortMenu.value

    private val _selectedOrder = mutableStateOf(sharedPref.sortOrder)
    val selectedOrder get() = _selectedOrder.value

    private val sharedCurrencies = sharedPref.sharedRates?.currencies ?: emptyList()

    private val ascending = sharedCurrencies.sortBy(SortOrder.ASCENDING)
    private val descending = sharedCurrencies.sortBy(SortOrder.DESCENDING)
    private val largestFirst = sharedCurrencies.sortBy(SortOrder.LARGEST_FIRST)
    private val smallestFirst = sharedCurrencies.sortBy(SortOrder.SMALLEST_FIRST)

    private fun SortOrder.sortedList(): List<Currency> {
        return when (this) {
            SortOrder.ASCENDING -> ascending
            SortOrder.DESCENDING -> descending
            SortOrder.LARGEST_FIRST -> largestFirst
            SortOrder.SMALLEST_FIRST -> smallestFirst
        }
    }

    private val _currencies = mutableStateOf(selectedOrder.sortedList())
    val currencies get() = _currencies.value

    private val _timestamp = mutableStateOf(sharedPref.timestamp)
    val timestamp get() = _timestamp.value

    private fun updateTimestamp(value: String) {
        _timestamp.value = value
        sharedPref.timestamp = value
    }

    private fun updateExchangesList(value: Rates) {
        viewModelScope.launch {
            _currencies.value = value.currencies
            sharedPref.sharedRates = value
        }
    }

    fun updateSortOrder(value: SortOrder) {
        viewModelScope.launch {
            _currencies.value = value.sortedList()
            _selectedOrder.value = value
            sharedPref.sortOrder = value
        }
    }

    fun syncExchangeRates() {
        _isLoading.value = if (_isLoading.value) return else true
        viewModelScope.launch {
            exchangeRepository.getLatestRates().run {
                _isLoading.value = false
                this ?: return@run
                if (_timestamp.value == timestamp) return@run
                updateExchangesList(rates)
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