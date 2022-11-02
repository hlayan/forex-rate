package com.hlayan.forexrate.ui.home

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hlayan.forexrate.data.local.CurrencyRepository
import com.hlayan.forexrate.data.local.sharedPreferences
import com.hlayan.forexrate.data.local.sortOrder
import com.hlayan.forexrate.data.remote.CBMRepository
import com.hlayan.forexrate.ui.shared.currency.Currency
import com.hlayan.forexrate.ui.shared.sorting.SortOrder
import com.hlayan.forexrate.ui.shared.sorting.sortBy
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val cbmRepository: CBMRepository,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val sharedPreferences: SharedPreferences

    private val _isLoading = mutableStateOf(false)
    val isLoading get() = _isLoading.value

    private val _isExpandSortMenu = mutableStateOf(false)
    val isExpandSortMenu get() = _isExpandSortMenu.value

    private val _selectedOrder = mutableStateOf(SortOrder.ASCENDING)
    val selectedOrder get() = _selectedOrder.value

    private val _currencies = mutableStateOf(emptyList<Currency>())
    val currencies get() = _currencies.value

    init {
        sharedPreferences = context.sharedPreferences
        _selectedOrder.value = sharedPreferences.sortOrder
        getLocalCurrencies()
    }

    fun updateSortOrder(value: SortOrder) {
        viewModelScope.launch {
            _selectedOrder.value = value
            _currencies.value = _currencies.value.sortBy(value)
            sharedPreferences.sortOrder = value
        }
    }

    private fun getLocalCurrencies() {
        _isLoading.value = if (_isLoading.value) return else true
        viewModelScope.launch {
            currencyRepository.getCurrencies().let {
                _isLoading.value = false
                it?.let {
                    _currencies.value = it.sortBy(_selectedOrder.value)
                }
            }
        }
    }

    fun updateCurrencies() {
        _isLoading.value = if (_isLoading.value) return else true
        viewModelScope.launch {
            cbmRepository.getCurrencies().let {
                _isLoading.value = false
                it?.let {
                    _currencies.value = it.sortBy(_selectedOrder.value)
                }
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