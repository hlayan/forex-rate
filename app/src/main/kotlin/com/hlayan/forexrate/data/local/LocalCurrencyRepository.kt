package com.hlayan.forexrate.data.local

import com.hlayan.forexrate.ui.shared.currency.Currency
import javax.inject.Inject

class LocalCurrencyRepository @Inject constructor(
    private val currencyDataSource: CurrencyDataSource
) : CurrencyRepository {

    override suspend fun getCurrencies(): List<Currency>? {
        return currencyDataSource.getCurrencies()
    }
}