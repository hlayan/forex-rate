package com.hlayan.forexrate.shared.extension

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

object AmountFormat {

    fun format(old: TextFieldValue): TextFieldValue {

        val before = old.text
        val numberDecimal = before.filter { it.isDigit() || it == '.' }

        val after = addCommasByThousand(numberDecimal)
        val cursor = getNewCursor(old.selection.max, before, after)

        return old.copy(after, TextRange(cursor))
    }

    private fun getNewCursor(beforeCursor: Int, before: String, after: String): Int {
        val newCursor = after.length - (before.length - beforeCursor)

        val cursorBeforeComma = after.getOrNull(newCursor - 1)?.equals(',')?.let {
            if (it) newCursor - 1 else newCursor
        } ?: newCursor

        return 0.coerceAtLeast(cursorBeforeComma)
    }

    private fun addCommasByThousand(number: String): String {
        return number.replace(Regex(THOUSANDS_REPLACEMENT_PATTERN), ",")
    }

    private const val THOUSANDS_REPLACEMENT_PATTERN = "\\B(?=(?:\\d{3})+(?!\\d))"
}