package com.hlayan.forexrate.shared.extension

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import kotlin.math.min

object AmountFormat {

    fun format(old: TextFieldValue): TextFieldValue {

        val before = old.text
        val numberDecimal = before.filter { it in DECIMAL_NUMBER }

        val after = if (!numberDecimal.contains('.')) addCommasByThousand(numberDecimal)
        else {
            val beforeDot = numberDecimal.substringBefore('.')
            val afterDot = numberDecimal.substringAfter('.')
            "${addCommasByThousand(beforeDot)}.$afterDot"
        }

        val cursor = getNewCursor(old.selection.max, before, after)

        return old.copy(after, TextRange(cursor))
    }

    private fun getNewCursor(oldCursor: Int, before: String, after: String): Int {

        if (oldCursor <= 0) return 0
        if (oldCursor >= before.length) return after.length

        val beforeDigitCount = before.substring(0, oldCursor).count { it in DECIMAL_NUMBER }

        val afterDigitCount = if (oldCursor <= after.length) {
            after.substring(0, oldCursor).count { it in DECIMAL_NUMBER }
        } else {
            oldCursor - after.length + after.count { it in DECIMAL_NUMBER }
        }

        val newCursor = oldCursor + (beforeDigitCount - afterDigitCount)

        val cursor = if (newCursor < oldCursor) {
            newCursor - after.substring(newCursor, min(oldCursor, after.length)).count { it == ',' }
        } else {
            newCursor + after.substring(oldCursor, min(newCursor, after.length)).count { it == ',' }
        }

        val finalCursor = after.getOrNull(cursor - 1)?.equals(',')?.let {
            if (it) cursor - 1 else cursor
        } ?: cursor

        if (finalCursor <= 0) return 0
        if (finalCursor >= after.length) return after.length

        return finalCursor
    }

    private fun addCommasByThousand(number: String): String {
        return number.replace(Regex(THOUSANDS_REPLACEMENT_PATTERN), ",")
    }

    private const val THOUSANDS_REPLACEMENT_PATTERN = "\\B(?=(?:\\d{3})+(?!\\d))"
    private const val DECIMAL_NUMBER = "1234567890."
}