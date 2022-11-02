package com.hlayan.forexrate.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hlayan.forexrate.ui.shared.currency.Currency
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class LocalCurrencyDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
) : CurrencyDataSource {

    override suspend fun getCurrencies(): List<Currency>? {
        val file = File(context.filesDir, "currencies.json")

        if (file.exists().not()) return null

        val json = file.readText()

        val typeToken = object : TypeToken<List<Currency>>() {}.type
        val currencies = Gson().fromJson<List<Currency>>(json, typeToken)
        return currencies
    }

}