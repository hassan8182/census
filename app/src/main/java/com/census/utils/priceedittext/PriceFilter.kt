package com.census.utils.priceedittext

import android.widget.EditText
import androidx.core.widget.doAfterTextChanged


fun EditText.attachPriceWatcher(
    beforeDecimal: Int,
    afterDecimal: Int,
    price: (String) -> Unit
) {

    doAfterTextChanged {

        val input = text.toString()

        if (input.isEmpty()) {
            price(text.toString())
            return@doAfterTextChanged
        }

        val formatted = input.perfectDecimal(
            beforeDecimal = beforeDecimal,
            afterDecimal = afterDecimal
        )
        if (formatted != input) {
            setText(formatted)
            setSelection(
                formatted.length
            )
        }

        price(text.toString())
    }
}

fun String.perfectDecimal(
    beforeDecimal: Int, afterDecimal: Int
): String {

    var input = this

    if (input[0] == '.') {
        input = "0$input"
    }

    var after = false
    val max = input.length
    var result = String()

    var up = 0
    var char: Char
    var repeat = 0
    var decimal = 0

    while (repeat < max) {
        char = input[repeat]
        if (char != '.' && !after) {
            up++
            if (up > beforeDecimal) {
                return result
            }
        } else if (char == '.') {
            after = true
        } else {
            decimal++
            if (decimal > afterDecimal) {
                return result
            }
        }
        result += char
        repeat++
    }
    return result
}