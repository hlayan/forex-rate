package com.example.jetpackcomposetest.data.remote

import android.util.Log
import com.example.jetpackcomposetest.LatestRates

private const val TAG = "ExchangeRepositoryImpl"

class ExchangeRepositoryImpl(private val exchangeDao: ExchangeDao) : ExchangeRepository {

    override suspend fun getLatestRates(): LatestRates? {
        val responseResult = exchangeDao.runCatching { syncLatestRates() }
        val url = responseResult.getOrNull()?.raw()?.request()?.url()
        Log.i(TAG, "Url = $url")
        responseResult.getOrElse {
            Log.e(TAG, it.stackTraceToString())
        }
        return responseResult.getOrNull()?.body()
    }
}