package com.hlayan.forexrate.ui.converter

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor() : ViewModel() {

    private val _forexRate = mutableStateOf(TextFieldValue())
    val forexRate: State<TextFieldValue> get() = _forexRate

    private val _mmkRate = mutableStateOf(TextFieldValue())
    val mmkRate: State<TextFieldValue> get() = _mmkRate

    private val _selectedRate = mutableStateOf(0.0)
    val selectedRate: State<Double> get() = _selectedRate

    fun setSelectedRate(value: Double) {
        _selectedRate.value = value
        updateForexRate(TextFieldValue("1"))
    }

    fun updateForexRate(value: TextFieldValue) {
        _forexRate.value = value
        val input = value.text.toDoubleOrNull()
        _mmkRate.value = if (input == null) TextFieldValue()
        else {
            val result = input * _selectedRate.value
            TextFieldValue(result.decimalFormat)
        }
    }

    fun updateMmkRate(value: TextFieldValue) {
        _mmkRate.value = value
        val input = value.text.toDoubleOrNull()
        _forexRate.value = if (input == null) TextFieldValue()
        else {
            val result = input / _selectedRate.value
            TextFieldValue(result.decimalFormat)
        }
    }
}