package com.hlayan.forexrate

import android.os.Parcelable
import androidx.annotation.Keep
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
    val rate: String = "1",
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

val map = mutableMapOf(
    "AUD" to "\ud83c\udde6\ud83c\uddfa",
    "BDT" to "\ud83c\udde7\ud83c\udde9",
    "BND" to "\ud83c\udde7\ud83c\uddf3",
    "BRL" to "\ud83c\udde7\ud83c\uddf7",
    "CAD" to "\ud83c\udde8\ud83c\udde6",
    "CHF" to "\ud83c\uddeb\ud83c\uddf7",
    "CNY" to "\ud83c\udde8\ud83c\uddf3",
    "CZK" to "\ud83c\udde8\ud83c\uddff",
    "DKK" to "\ud83c\udde9\ud83c\uddf0",
    "EGP" to "\ud83c\uddea\ud83c\uddec",
    "EUR" to "\ud83c\uddea\ud83c\uddfa",
    "GBP" to "\ud83c\uddec\ud83c\udde7",
    "HKD" to "\ud83c\udded\ud83c\uddf0",
    "IDR" to "\ud83c\uddee\ud83c\udde9",
    "ILS" to "\ud83c\uddee\ud83c\uddf1",
    "INR" to "\ud83c\uddee\ud83c\uddf3",
    "JPY" to "\ud83c\uddef\ud83c\uddf5",
    "KES" to "\ud83c\uddf0\ud83c\uddea",
    "KHR" to "\ud83c\uddf0\ud83c\udded",
    "KRW" to "\ud83c\uddf0\ud83c\uddf7",
    "KWD" to "\ud83c\uddf0\ud83c\uddfc",
    "LAK" to "\ud83c\uddf1\ud83c\udde6",
    "LKR" to "\ud83c\uddf1\ud83c\uddf0",
    "MMK" to "\ud83c\uddf2\ud83c\uddf2",
    "MYR" to "\ud83c\uddf2\ud83c\uddfe",
    "NOK" to "\ud83c\uddf3\ud83c\uddf4",
    "NPR" to "\ud83c\uddf3\ud83c\uddf5",
    "NZD" to "\ud83c\uddf3\ud83c\uddff",
    "PHP" to "\ud83c\uddf5\ud83c\udded",
    "PKR" to "\ud83c\uddf5\ud83c\uddf0",
    "RSD" to "\ud83c\uddf7\ud83c\uddf8",
    "RUB" to "\ud83c\uddf7\ud83c\uddfa",
    "SAR" to "\ud83c\uddf8\ud83c\udde6",
    "SEK" to "\ud83c\uddf8\ud83c\uddea",
    "SGD" to "\ud83c\uddf8\ud83c\uddec",
    "THB" to "\ud83c\uddf9\ud83c\udded",
    "USD" to "\ud83c\uddfa\ud83c\uddf8",
    "VND" to "\ud83c\uddfb\ud83c\uddf3",
    "ZAR" to "\ud83c\uddff\ud83c\udde6",
)


val Rates.currencies
    get() = listOf(
        Currency(AUD, "AUD", "\ud83c\udde6\ud83c\uddfa", "Australian Dollar"),
        Currency(BDT, "BDT", "\ud83c\udde7\ud83c\udde9", "Bangladesh Taka"),
        Currency(BND, "BND", "\ud83c\udde7\ud83c\uddf3", "Brunei Dollar"),
        Currency(BRL, "BRL", "\ud83c\udde7\ud83c\uddf7", "Brazilian Real"),
        Currency(CAD, "CAD", "\ud83c\udde8\ud83c\udde6", "Canadian Dollar"),
        Currency(CHF, "CHF", "\ud83c\uddeb\ud83c\uddf7", "Swiss Franc"),
        Currency(CNY, "CNY", "\ud83c\udde8\ud83c\uddf3", "Chinese Yuan"),
        Currency(CZK, "CZK", "\ud83c\udde8\ud83c\uddff", "Czech Koruna"),
        Currency(DKK, "DKK", "\ud83c\udde9\ud83c\uddf0", "Danish Krone"),
        Currency(EGP, "EGP", "\ud83c\uddea\ud83c\uddec", "Egyptian Pound"),
        Currency(EUR, "EUR", "\ud83c\uddea\ud83c\uddfa", "Euro"),
        Currency(GBP, "GBP", "\ud83c\uddec\ud83c\udde7", "Pound Sterling"),
        Currency(HKD, "HKD", "\ud83c\udded\ud83c\uddf0", "Hong Kong Dollar"),
        Currency(IDR, "IDR", "\ud83c\uddee\ud83c\udde9", "Indonesian Rupiah"),
        Currency(ILS, "ILS", "\ud83c\uddee\ud83c\uddf1", "Israeli Shekel"),
        Currency(INR, "INR", "\ud83c\uddee\ud83c\uddf3", "Indian Rupee"),
        Currency(JPY, "JPY", "\ud83c\uddef\ud83c\uddf5", "Japanese Yen"),
        Currency(KES, "KES", "\ud83c\uddf0\ud83c\uddea", "Kenya Shilling"),
        Currency(KHR, "KHR", "\ud83c\uddf0\ud83c\udded", "Cambodian Riel"),
        Currency(KRW, "KRW", "\ud83c\uddf0\ud83c\uddf7", "Korean Won"),
        Currency(KWD, "KWD", "\ud83c\uddf0\ud83c\uddfc", "Kuwaiti Dinar"),
        Currency(LAK, "LAK", "\ud83c\uddf1\ud83c\udde6", "Lao Kip"),
        Currency(LKR, "LKR", "\ud83c\uddf1\ud83c\uddf0", "Sri Lankan Rupee"),
        Currency(MYR, "MYR", "\ud83c\uddf2\ud83c\uddfe", "Malaysian Ringgit"),
        Currency(NOK, "NOK", "\ud83c\uddf3\ud83c\uddf4", "Norwegian Kroner"),
        Currency(NPR, "NPR", "\ud83c\uddf3\ud83c\uddf5", "Nepalese Rupee"),
        Currency(NZD, "NZD", "\ud83c\uddf3\ud83c\uddff", "New Zealand Dollar"),
        Currency(PHP, "PHP", "\ud83c\uddf5\ud83c\udded", "Philippines Peso"),
        Currency(PKR, "PKR", "\ud83c\uddf5\ud83c\uddf0", "Pakistani Rupee"),
        Currency(RSD, "RSD", "\ud83c\uddf7\ud83c\uddf8", "Serbian Dinar"),
        Currency(RUB, "RUB", "\ud83c\uddf7\ud83c\uddfa", "Russian Rouble"),
        Currency(SAR, "SAR", "\ud83c\uddf8\ud83c\udde6", "Saudi Arabian Riyal"),
        Currency(SEK, "SEK", "\ud83c\uddf8\ud83c\uddea", "Swedish Krona"),
        Currency(SGD, "USD", "\ud83c\uddf8\ud83c\uddec", "Singapore Dollar"),
        Currency(THB, "THB", "\ud83c\uddf9\ud83c\udded", "Thai Baht"),
        Currency(USD, "USD", "\ud83c\uddfa\ud83c\uddf8", "United State Dollar"),
        Currency(VND, "VND", "\ud83c\uddfb\ud83c\uddf3", "Vietnamese Dong"),
        Currency(ZAR, "ZAR", "\ud83c\uddff\ud83c\udde6", "South Africa Rand")
    )
