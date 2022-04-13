package com.example.jetpackcomposetest.data.remote

import com.example.jetpackcomposetest.LatestRates

interface ExchangeRepository {

    suspend fun getLatestRates(): LatestRates?

}