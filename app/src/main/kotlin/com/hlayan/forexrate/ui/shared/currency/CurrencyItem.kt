package com.hlayan.forexrate.ui.shared.currency

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hlayan.forexrate.data.local.Currencies

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CurrencyItem(currency: Currency, onClick: (Currency) -> Unit = {}) {
    Card(
        elevation = 4.dp,
        onClick = { onClick(currency) }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "${currency.name} ${currency.flagEmoji}",
                    fontSize = 20.sp,
                )
                Text(
                    text = currency.fullName,
                    style = TextStyle.Default,
                    fontSize = 10.sp
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = currency.rate.decimalFormat,
                    fontSize = 20.sp,
                )
                Text(
                    text = "${Currencies.MMK.flagEmoji} MMK",
                    style = TextStyle.Default,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyCardPreview() {
    CurrencyItem(Currency())
}