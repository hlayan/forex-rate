package com.hlayan.forexrate.data.remote

import com.hlayan.forexrate.ui.shared.currency.LatestRates
import javax.inject.Inject

class NetworkCBMRepository @Inject constructor(
    private val cbmDataSource: CBMDataSource
) : CBMRepository {

    override suspend fun getLatestRates(): LatestRates? {
        return cbmDataSource.getLatestRates()
    }
}