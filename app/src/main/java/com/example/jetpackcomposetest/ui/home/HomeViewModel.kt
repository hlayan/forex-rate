package com.example.jetpackcomposetest.ui.home

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetpackcomposetest.*
import com.example.jetpackcomposetest.retrofit.getLatestRates

class HomeViewModel(context: Context) : ViewModel() {

    private val sharedPref = context.sharedPreferences

    private val _isLoading = mutableStateOf(false)
    val isLoading get() = _isLoading.value

    private val _selectedOrder = mutableStateOf(sharedPref.sortOrder)
    val selectedOrder get() = _selectedOrder.value

    private val exchangeRates = sharedPref.sharedRates?.exchangesList ?: emptyList()
    private val _exchangesList = mutableStateOf(exchangeRates.sortBy(_selectedOrder.value))
    val exchangesList get() = _exchangesList.value

    private val _timestamp = mutableStateOf(sharedPref.timestamp)
    val timestamp get() = _timestamp.value

    private fun updateTimestamp(value: String) {
        _timestamp.value = value
        sharedPref.timestamp = value
    }

    private fun updateExchangesList(value: Rates) {
        _exchangesList.value = value.exchangesList.sortBy(_selectedOrder.value)
        sharedPref.sharedRates = value
    }

    fun updateSortOrder(value: SortOrder) {
        _selectedOrder.value = value
        _exchangesList.value = _exchangesList.value.sortBy(value)
        sharedPref.sortOrder = value
    }

    fun refreshExchangeRates() {
        _isLoading.value = if (_isLoading.value) return else true
        getLatestRates {
            _isLoading.value = false
            it ?: return@getLatestRates
            if (_timestamp.value == it.timestamp) return@getLatestRates
            updateExchangesList(it.rates)
            updateTimestamp(it.timestamp)
        }
    }
}