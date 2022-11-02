package com.hlayan.forexrate.data.local

import com.hlayan.forexrate.ui.shared.currency.Currency
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface CurrencyRepository {

    suspend fun getCurrencies(): List<Currency>?

}

@Module
@InstallIn(ViewModelComponent::class)
abstract class CurrencyRepositoryModule {

    @Binds
    abstract fun bindCurrencyRepository(
        localCurrencyRepository: LocalCurrencyRepository
    ): CurrencyRepository
}