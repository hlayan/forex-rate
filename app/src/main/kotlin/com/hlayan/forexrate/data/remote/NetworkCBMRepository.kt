package com.hlayan.forexrate.data.remote

import com.hlayan.forexrate.ui.shared.currency.Currency
import com.hlayan.forexrate.ui.shared.currency.currencies
import javax.inject.Inject

class NetworkCBMRepository @Inject constructor(
    private val cbmDataSource: CBMDataSource
) : CBMRepository {

    override suspend fun getCurrencies(): List<Currency>? {
        return cbmDataSource.getLatestRates()?.rates?.currencies
    }
}