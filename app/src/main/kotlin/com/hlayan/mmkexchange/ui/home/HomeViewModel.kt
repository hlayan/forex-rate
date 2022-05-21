package com.hlayan.mmkexchange.ui.home

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hlayan.mmkexchange.*
import com.hlayan.mmkexchange.data.remote.ExchangeDao
import com.hlayan.mmkexchange.data.remote.ExchangeRepository
import com.hlayan.mmkexchange.data.remote.ExchangeRepositoryImpl
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

    private val ascending = exchangeRates.sortBy(SortOrder.ASCENDING)
    private val descending = exchangeRates.sortBy(SortOrder.DESCENDING)
    private val largestFirst = exchangeRates.sortBy(SortOrder.LARGEST_FIRST)
    private val smallestFirst = exchangeRates.sortBy(SortOrder.SMALLEST_FIRST)

    private fun SortOrder.sortedList(): List<ExchangeModel> {
        return when (this) {
            SortOrder.ASCENDING -> ascending
            SortOrder.DESCENDING -> descending
            SortOrder.LARGEST_FIRST -> largestFirst
            SortOrder.SMALLEST_FIRST -> smallestFirst
        }
    }

    private val _exchangesList = mutableStateOf(selectedOrder.sortedList())
    val exchangesList get() = _exchangesList.value

    private val _timestamp = mutableStateOf(sharedPref.timestamp)
    val timestamp get() = _timestamp.value

    private fun updateTimestamp(value: String) {
        _timestamp.value = value
        sharedPref.timestamp = value
    }

    private fun updateExchangesList(value: Rates) {
        viewModelScope.launch {
            _exchangesList.value = value.exchangesList
//            _exchangesList.value = value.exchangesList.sortBy(_selectedOrder.value)
            sharedPref.sharedRates = value
        }
    }

    fun updateSortOrder(value: SortOrder) {
        viewModelScope.launch {
            _exchangesList.value = value.sortedList()
//            _exchangesList.value = _exchangesList.value.sortBy(value)
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
}