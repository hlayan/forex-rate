package com.hlayan.mmkexchange.data.remote

import com.hlayan.mmkexchange.LatestRates
import com.skydoves.sandwich.getOrNull
import com.skydoves.sandwich.onSuccess
import timber.log.Timber
import javax.inject.Inject

class ExchangeRepositoryImpl @Inject constructor(
    private val cbmForexService: CbmForexService
) : ExchangeRepository {

    override suspend fun getLatestRates(): LatestRates? {
        val response = cbmForexService.syncLatestRates()
            .onSuccess {
                Timber.i("Url = " + raw.request.url)
            }
        return response.getOrNull()
    }
}