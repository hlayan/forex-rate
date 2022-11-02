package com.hlayan.forexrate.data.remote

import android.content.Context
import com.hlayan.forexrate.data.local.sharedPreferences
import com.hlayan.forexrate.data.local.timestamp
import com.hlayan.forexrate.ui.json
import com.hlayan.forexrate.ui.shared.currency.LatestRates
import com.hlayan.forexrate.ui.shared.currency.currencies
import com.skydoves.sandwich.getOrNull
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class NetworkCBMDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cbmService: CBMService
) : CBMDataSource {

    override suspend fun getLatestRates(): LatestRates? {
        return cbmService.getLatestRates().suspendOnSuccess {
            val sharedPreferences = context.sharedPreferences
            val file = File(context.filesDir, "currencies.json")

            if (data.timestamp != sharedPreferences.timestamp) {
                sharedPreferences.timestamp = data.timestamp
                data.rates.currencies.json?.let { file.writeText(it) }
            }

        }.getOrNull()
    }

}