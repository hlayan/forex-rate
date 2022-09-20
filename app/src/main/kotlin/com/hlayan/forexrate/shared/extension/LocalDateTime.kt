package com.hlayan.forexrate.shared.extension

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

val LocalDateTime.uiFormat: String get() = toString("E, d MMM yyyy")

fun LocalDateTime.toString(pattern: String): String = format(DateTimeFormatter.ofPattern(pattern))

val String.localDateTime: LocalDateTime
    get() = LocalDateTime.ofInstant(Instant.ofEpochSecond(toLong()), ZoneId.systemDefault())
