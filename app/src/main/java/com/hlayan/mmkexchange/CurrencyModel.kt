package com.hlayan.mmkexchange

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CurrencyModel(
    val currencyCode: String = Currencies.MMK.name,
    val currencyName: String = Currencies.MMK.fullName,
    val flagEmoji: String = Currencies.MMK.flagEmoji,
    val exchangeRate: String = "1"
) : Parcelable

@Keep
@Parcelize
data class ExchangeModel(
    val currency: Currencies = Currencies.MMK,
    val rate: String = "1"
) : Parcelable

@Keep
data class LatestRates(
    val info: String = "Central Bank of Myanmar",
    val description: String = "Official Website of Central Bank of Myanmar",
    val timestamp: String = "1640764800",
    val rates: Rates = Rates()
)

@Keep
data class Rates(
    val USD: String = "1,778.0",
    val NZD: String = "1,208.4",
    val LKR: String = "8.7802",
    val CZK: String = "80.624",
    val JPY: String = "1,546.4",
    val VND: String = "7.7897",
    val PHP: String = "34.808",
    val KRW: String = "149.75",
    val BRL: String = "315.88",
    val HKD: String = "228.05",
    val RSD: String = "17.067",
    val MYR: String = "425.00",
    val CAD: String = "1,387.1",
    val GBP: String = "2,384.9",
    val NOK: String = "201.56",
    val ILS: String = "572.17",
    val SEK: String = "195.98",
    val DKK: String = "269.89",
    val AUD: String = "1,285.8",
    val RUB: String = "24.143",
    val KWD: String = "5,872.8",
    val INR: String = "23.773",
    val BND: String = "1,312.7",
    val EUR: String = "2,006.7",
    val ZAR: String = "112.94",
    val NPR: String = "14.858",
    val CNY: String = "279.15",
    val CHF: String = "1,935.8",
    val THB: String = "53.043",
    val PKR: String = "9.9570",
    val KES: String = "15.714",
    val EGP: String = "112.89",
    val BDT: String = "20.724",
    val SAR: String = "473.50",
    val LAK: String = "15.915",
    val IDR: String = "12.475",
    val KHR: String = "43.664",
    val SGD: String = "1,312.7"
)

val Rates.exchangesList
    get() = listOf(
        ExchangeModel(Currencies.USD, USD),
        ExchangeModel(Currencies.NZD, NZD),
        ExchangeModel(Currencies.LKR, LKR),
        ExchangeModel(Currencies.CZK, CZK),
        ExchangeModel(Currencies.JPY, JPY),
        ExchangeModel(Currencies.VND, VND),
        ExchangeModel(Currencies.PHP, PHP),
        ExchangeModel(Currencies.KRW, KRW),
        ExchangeModel(Currencies.BRL, BRL),
        ExchangeModel(Currencies.HKD, HKD),
        ExchangeModel(Currencies.RSD, RSD),
        ExchangeModel(Currencies.MYR, MYR),
        ExchangeModel(Currencies.CAD, CAD),
        ExchangeModel(Currencies.GBP, GBP),
        ExchangeModel(Currencies.NOK, NOK),
        ExchangeModel(Currencies.ILS, ILS),
        ExchangeModel(Currencies.SEK, SEK),
        ExchangeModel(Currencies.DKK, DKK),
        ExchangeModel(Currencies.AUD, AUD),
        ExchangeModel(Currencies.RUB, RUB),
        ExchangeModel(Currencies.KWD, KWD),
        ExchangeModel(Currencies.INR, INR),
        ExchangeModel(Currencies.BND, BND),
        ExchangeModel(Currencies.EUR, EUR),
        ExchangeModel(Currencies.ZAR, ZAR),
        ExchangeModel(Currencies.NPR, NPR),
        ExchangeModel(Currencies.CNY, CNY),
        ExchangeModel(Currencies.CHF, CHF),
        ExchangeModel(Currencies.THB, THB),
        ExchangeModel(Currencies.PKR, PKR),
        ExchangeModel(Currencies.KES, KES),
        ExchangeModel(Currencies.EGP, EGP),
        ExchangeModel(Currencies.BDT, BDT),
        ExchangeModel(Currencies.SAR, SAR),
        ExchangeModel(Currencies.LAK, LAK),
        ExchangeModel(Currencies.IDR, IDR),
        ExchangeModel(Currencies.KHR, KHR),
        ExchangeModel(Currencies.SGD, SGD)
    )