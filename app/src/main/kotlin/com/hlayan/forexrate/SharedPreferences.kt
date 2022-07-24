package com.hlayan.forexrate

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit

val Context.sharedPreferences: SharedPreferences
    get() = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)

const val RATES = "Rates"
var SharedPreferences.sharedRates: Rates?
    set(value) = edit { putString(RATES, value?.json) }
    get() = getString(RATES, null)?.getModel<Rates>()

const val SORT_ORDER = "SortOrder"
var SharedPreferences.sortOrder: SortOrder
    set(value) = edit { putString(SORT_ORDER, value.name) }
    get() = SortOrder.valueOf(getString(SORT_ORDER, SortOrder.ASCENDING.name)!!)

const val IS_DARK_MODE = "IsDarkMode"
var SharedPreferences.isDarkMode: Boolean
    set(value) = edit { putBoolean(IS_DARK_MODE, value) }
    get() = getBoolean(IS_DARK_MODE, false)

const val TIMESTAMP = "Timestamp"
var SharedPreferences.timestamp: String?
    set(value) = edit { putString(TIMESTAMP, value) }
    get() = getString(TIMESTAMP, null)

