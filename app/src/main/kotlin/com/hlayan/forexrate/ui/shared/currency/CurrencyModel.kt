package com.hlayan.forexrate.ui.shared.currency

import android.os.Parcelable
import androidx.annotation.Keep
import com.hlayan.forexrate.ui.commaRemoved
import com.hlayan.forexrate.data.local.Currencies
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ExchangeModel(
    val currency: Currencies = Currencies.MMK,
    val rate: String = "1"
) : Parcelable

@Keep
@Parcelize
data class Currency(
    val rate: Double = 1.0,
    val name: String = "MMK",
    val flagEmoji: String = "\uD83C\uDDF2\uD83C\uDDF2",
    val fullName: String = "Myanmar Kyat"
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

val Rates.currencies
    get() = listOf(
        Currencies.AUD.makeCurrency(AUD),
        Currencies.BDT.makeCurrency(BDT),
        Currencies.BND.makeCurrency(BND),
        Currencies.BRL.makeCurrency(BRL),
        Currencies.CAD.makeCurrency(CAD),
        Currencies.CHF.makeCurrency(CHF),
        Currencies.CNY.makeCurrency(CNY),
        Currencies.CZK.makeCurrency(CZK),
        Currencies.DKK.makeCurrency(DKK),
        Currencies.EGP.makeCurrency(EGP),
        Currencies.EUR.makeCurrency(EUR),
        Currencies.GBP.makeCurrency(GBP),
        Currencies.HKD.makeCurrency(HKD),
        Currencies.IDR.makeCurrency(IDR),
        Currencies.ILS.makeCurrency(ILS),
        Currencies.INR.makeCurrency(INR),
        Currencies.JPY.makeCurrency(JPY),
        Currencies.KES.makeCurrency(KES),
        Currencies.KHR.makeCurrency(KHR),
        Currencies.KRW.makeCurrency(KRW),
        Currencies.KWD.makeCurrency(KWD),
        Currencies.LAK.makeCurrency(LAK),
        Currencies.LKR.makeCurrency(LKR),
        Currencies.MYR.makeCurrency(MYR),
        Currencies.NOK.makeCurrency(NOK),
        Currencies.NPR.makeCurrency(NPR),
        Currencies.NZD.makeCurrency(NZD),
        Currencies.PHP.makeCurrency(PHP),
        Currencies.PKR.makeCurrency(PKR),
        Currencies.RSD.makeCurrency(RSD),
        Currencies.RUB.makeCurrency(RUB),
        Currencies.SAR.makeCurrency(SAR),
        Currencies.SEK.makeCurrency(SEK),
        Currencies.SGD.makeCurrency(SGD),
        Currencies.THB.makeCurrency(THB),
        Currencies.USD.makeCurrency(USD),
        Currencies.VND.makeCurrency(VND),
        Currencies.ZAR.makeCurrency(ZAR),
    )

fun Currencies.makeCurrency(rate: Double): Currency = Currency(rate, name, flagEmoji, fullName)

fun Currencies.makeCurrency(rate: String): Currency = makeCurrency(rate.commaRemoved)

