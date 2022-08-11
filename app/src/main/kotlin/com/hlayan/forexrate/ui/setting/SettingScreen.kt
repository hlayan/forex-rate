package com.hlayan.forexrate.ui.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hlayan.forexrate.ui.theme.isDarkMode

@Composable
fun SettingScreen(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.height(56.dp), verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.size(32.dp))
                Text(
                    text = "Setting",
                    style = MaterialTheme.typography.h6,
                )
            }
            Divider()
            DarkModeItem()
        }
    }
}

@Composable
fun DarkModeItem() {
    val checkedMode = remember { mutableStateOf(DarkMode.System) }

    isDarkMode = when (checkedMode.value) {
        DarkMode.Off -> {
            false
        }
        DarkMode.On -> {
            true
        }
        DarkMode.System -> {
            isSystemInDarkTheme()
        }
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text(
                text = "Dark Mode",
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                fontWeight = FontWeight.Medium
            )
        }
        items(DarkMode.values()) {
            CheckItem(name = it.name, checked = checkedMode.value == it) {
                checkedMode.value = it
            }
        }
    }

    Divider()
    Spacer(Modifier.height(8.dp))

    SettingItem(icon = null, title = "Package", body = "com.hlayan.forexrate")

    SettingItem(icon = null, title = "Version", body = "2.0.0 (2)")

    SettingItem(icon = null, title = "Developer", body = "Hlayan Htet Aung")
}

@Composable
fun CheckItem(name: String, checked: Boolean = false, onClick: () -> Unit) {
    Row(
        Modifier
            .clickable(onClick = onClick)
            .height(42.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.size(16.dp))
        Text(
            text = name,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colors.run { if (checked) primary else onSurface },
        )
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Check",
                tint = MaterialTheme.colors.primary
            )
        }
        Spacer(Modifier.size(16.dp))
    }
}

enum class DarkMode {
    On, Off, System
}

@Composable
fun SettingItem(icon: ImageVector?, title: String, body: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.size(16.dp))
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = title,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.LightGray.copy(0.5f))
                    .padding(8.dp)
            )
        }
        Spacer(Modifier.size(16.dp))
        Column {
            Text(text = title, fontWeight = FontWeight.Medium)
            Text(
                text = body,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Gray,
            )
        }
    }
}