package com.example.jetpackcomposetest

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExchangeCard(exchangeModel: ExchangeModel, onClick: () -> Unit = {}) {
    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
        ExchangeList(exchangeModel = exchangeModel, onClick = onClick)
    } else {
        ExchangeGrid(exchangeModel = exchangeModel, onClick = onClick)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExchangeList(exchangeModel: ExchangeModel, onClick: () -> Unit = {}) {
    Card(
//        shape = RoundedCornerShape(8.dp),
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
                    text = "${exchangeModel.currency.name} ${exchangeModel.currency.flagEmoji}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.primary
                )
                Text(
                    text = exchangeModel.currency.fullName,
                    style = TextStyle.Default,
                    fontSize = 10.sp
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = exchangeModel.rate,
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExchangeGrid(exchangeModel: ExchangeModel, onClick: () -> Unit = {}) {
    Card(
//        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = exchangeModel.rate.rateForOneMMK,
                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.Black,
            )
            Text(
                text = exchangeModel.currency.run { "$name $flagEmoji" },
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

val String.rateForOneMMK: String get() = (1 / safeDouble).formattedString

@Preview(showBackground = true)
@Composable
fun CurrencyCardPreview() {
    ExchangeCard(ExchangeModel(Currencies.USD, "1778"))
}

@Preview(showBackground = true)
@Composable
fun ExchangeGridPreview() {
    ExchangeGrid(ExchangeModel(Currencies.USD, "1778"))
}