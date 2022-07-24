package com.hlayan.forexrate

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hlayan.forexrate.ui.theme.DefaultPreviewTheme
import com.hlayan.forexrate.ui.theme.isDarkMode
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun NavigationDrawerHeader(timestamp: String?) {
    val scope = rememberCoroutineScope()
    val sharedPref = LocalContext.current.sharedPreferences
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "MMK Exchange",
                fontSize = 20.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(16.dp)
            )
            IconButton(onClick = {
                scope.launch {
                    isDarkMode = !isDarkMode
                    sharedPref.isDarkMode = isDarkMode
                }
            }) {
                val darkOrLight =
                    if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode
                Icon(imageVector = darkOrLight, contentDescription = "DarkOrLight")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Last updated:",
                fontSize = 12.sp
            )
            Text(
                text = timestamp?.localDateTime?.uiFormat ?: "",
                fontSize = 12.sp
            )
        }
    }
}

val LocalDateTime.uiFormat: String get() = toString("dd/MM/yyyy h:mm a")

fun LocalDateTime.toString(pattern: String): String = format(DateTimeFormatter.ofPattern(pattern))

val String.localDateTime: LocalDateTime
    get() = LocalDateTime.ofInstant(Instant.ofEpochSecond(toLong()), ZoneId.systemDefault())

@Preview(showBackground = true)
@Composable
fun NavigationDrawerHeaderPreview() {
    DefaultPreviewTheme {
        NavigationDrawerHeader("1640764800")
    }
}