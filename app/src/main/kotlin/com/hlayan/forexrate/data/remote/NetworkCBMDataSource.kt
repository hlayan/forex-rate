package com.hlayan.forexrate.data.remote

import com.hlayan.forexrate.ui.shared.currency.LatestRates
import com.skydoves.sandwich.getOrNull
import javax.inject.Inject

class NetworkCBMDataSource @Inject constructor(
    private val cbmService: CBMService
) : CBMDataSource {

    override suspend fun getLatestRates(): LatestRates? {
        return cbmService.getLatestRates().getOrNull()
    }

}