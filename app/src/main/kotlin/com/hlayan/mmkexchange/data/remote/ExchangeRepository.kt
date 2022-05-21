package com.hlayan.mmkexchange.data.remote

import com.hlayan.mmkexchange.LatestRates

interface ExchangeRepository {

    suspend fun getLatestRates(): LatestRates?

}