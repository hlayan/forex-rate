package com.example.jetpackcomposetest.ui.home

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposetest.*
import com.example.jetpackcomposetest.data.remote.ExchangeDao
import com.example.jetpackcomposetest.data.remote.ExchangeRepository
import com.example.jetpackcomposetest.data.remote.ExchangeRepositoryImpl
import kotlinx.coroutines.launch

class HomeViewModel(
    context: Context,
    private val exchangeRepository: ExchangeRepository = ExchangeRepositoryImpl(ExchangeDao.Instance)
) : ViewModel() {

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
}