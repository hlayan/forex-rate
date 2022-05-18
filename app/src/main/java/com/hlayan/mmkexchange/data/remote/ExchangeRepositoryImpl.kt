package com.hlayan.mmkexchange.data.remote

import android.util.Log
import com.hlayan.mmkexchange.LatestRates
import com.skydoves.sandwich.getOrNull
import com.skydoves.sandwich.onSuccess
import timber.log.Timber

private const val TAG = "ExchangeRepositoryImpl"

class ExchangeRepositoryImpl(private val exchangeDao: ExchangeDao) : ExchangeRepository {

    override suspend fun getLatestRates(): LatestRates? {
        val response = exchangeDao.syncLatestRates()
            .onSuccess {
                Log.i(TAG, "Url = ${raw.request.url}")
                Timber.i("Url = " + raw.request.url)
            }
        return response.getOrNull()
    }
}