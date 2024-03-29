package com.hlayan.forexrate.data.remote

import com.hlayan.forexrate.ui.shared.currency.Currency
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface CBMRepository {

    suspend fun getCurrencies(): List<Currency>?

}

@Module
@InstallIn(ViewModelComponent::class)
abstract class CBMRepositoryModule {

    @Binds
    abstract fun bindCBMRepository(
        networkCBMRepository: NetworkCBMRepository
    ): CBMRepository
}