package com.example.jetpackcomposetest

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.DecimalFormat

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ExchangeTextField(
    text: String = "",
    labelText: String = Currencies.MMK.run { "$name $flagEmoji" },
    placeholderText: String = Currencies.MMK.name,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        label = {
            Text(
                text = labelText,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        placeholder = {
            Text(
                text = placeholderText,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
        },
        textStyle = TextStyle(textAlign = TextAlign.Right, fontSize = 18.sp),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        )
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ExchangeTextField(
    text: TextFieldValue = TextFieldValue(),
    labelText: String = Currencies.MMK.run { "$name $flagEmoji" },
    placeholderText: String = Currencies.MMK.name,
    onValueChange: (TextFieldValue) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        label = {
            Text(
                text = labelText,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        placeholder = {
            Text(
                text = placeholderText,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
        },
        textStyle = TextStyle(textAlign = TextAlign.Right, fontSize = 18.sp),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        )
    )
}

val Double.formattedString
    get() : String {
        return if (this < 1) {
            getSignificantDigits(2)
        } else {
            DecimalFormat("0.##").format(this)
        }
    }

fun Double.getSignificantDigits(significantDigits: Int): String {
    var bigDecimal = BigDecimal(this, MathContext.DECIMAL64)
    bigDecimal = bigDecimal.round(MathContext(significantDigits, RoundingMode.HALF_UP))
    bigDecimal.run {
        val precision = precision()
        if (precision < significantDigits)
            bigDecimal = setScale(scale() + (significantDigits - precision))
    }
    return bigDecimal.toPlainString()
}

@Preview(showBackground = true)
@Composable
fun ConverterFieldPreview() {
    ExchangeTextField("") {}
}