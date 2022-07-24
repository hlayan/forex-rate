package com.hlayan.forexrate

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hlayan.forexrate.ui.converter.decimalFormat

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CurrencyItem(currency: Currency, onClick: () -> Unit = {}) {
    Card(
        elevation = 4.dp,
        onClick = onClick
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
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.primary
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
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.primary
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