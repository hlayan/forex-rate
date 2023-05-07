package com.hlayan.forexrate.shared.extension

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import timber.log.Timber
import kotlin.math.min

object AmountFormat {

    fun format(former: TextFieldValue, current: TextFieldValue): TextFieldValue {

        if (former.text == current.text) return current

        return try {
            format(validateDecimal(former, current))
        } catch (e: Exception) {
            Timber.e(e)
            former
        }

    }

    private fun validateDecimal(former: TextFieldValue, current: TextFieldValue): TextFieldValue {

        if (current.text.count { it == '.' } < 2) return current

        val transformed = if ('.' in former.text && former.text.indexOf('.') !in former.selection) {

            val newInputRange = former.selection.min until current.selection.max
            val newInput = current.text.substring(newInputRange).replace(".", "")

            current.text.replaceRange(newInputRange, newInput)

        } else {
            val beforeDot = current.text.substringBefore('.')
            val afterDot = current.text.substringAfter('.')

            "$beforeDot.${afterDot.replace(".", "")}"
        }

        val newCursor = current.selection.max - (current.text.length - transformed.length)

        return current.copy(transformed, TextRange(newCursor))
    }

    private fun format(current: TextFieldValue): TextFieldValue {

        val before = current.text
        val numberDecimal = before.filter { it in DECIMAL_NUMBER }

        val after = if (!numberDecimal.contains('.')) addCommasByThousand(numberDecimal)
        else {
            val beforeDot = numberDecimal.substringBefore('.')
            val afterDot = numberDecimal.substringAfter('.')
            "${addCommasByThousand(beforeDot)}.$afterDot"
        }

        if (before == after) return current

        val cursor = getNewCursor(current.selection.max, before, after)

        return current.copy(after, TextRange(cursor))
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