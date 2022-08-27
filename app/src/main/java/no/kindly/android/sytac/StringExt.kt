package no.kindly.android.sytac

import android.content.Context
import androidx.annotation.RawRes

private fun String.isDigit(): Boolean = all { char -> char.isDigit() }


fun convertToPair(@RawRes res: Int, ctx: Context) =
    ctx.resources.openRawResource(res).bufferedReader().use { it.readText() }.trim().lines()
        .filter { it.isNotEmpty() }
        .mapNotNull { dictionary ->
            val (left, right) = dictionary.split("-")
            if (left.isDigit()) {
                left to right
            } else {
                null
            }
        }.toMap()