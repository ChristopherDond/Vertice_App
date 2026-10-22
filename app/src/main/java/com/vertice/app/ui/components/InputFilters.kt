package com.vertice.app.ui.components

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/** Filtros aplicados em onValueChange: o estado nunca guarda caractere inválido (nem colado). */
fun String.onlyDigits(max: Int): String = filter(Char::isDigit).take(max)

fun String.onlyLetters(max: Int): String =
    filter { it.isLetter() || it == ' ' || it == '\'' || it == '-' || it == '.' }
        .trimStart()
        .replace(Regex(" {2,}"), " ")
        .take(max)

/** Valor em reais: só dígitos e uma vírgula, no máximo 2 casas decimais. */
fun String.moneyInput(maxInt: Int = 7): String {
    val clean = filter { it.isDigit() || it == ',' }
    val comma = clean.indexOf(',')
    if (comma < 0) return clean.take(maxInt)
    val whole = clean.substring(0, comma).take(maxInt)
    val cents = clean.substring(comma + 1).filter(Char::isDigit).take(2)
    return "$whole,$cents"
}

fun String.moneyToDouble(): Double? = replace(',', '.').toDoubleOrNull()

/** O estado guarda só dígitos; a máscara ('#' = dígito) existe apenas na exibição. */
class DigitMask(private val mask: String) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text
        val shown = StringBuilder()
        var next = 0
        for (m in mask) {
            if (next >= digits.length) break
            if (m == '#') shown.append(digits[next++]) else shown.append(m)
        }
        val mapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var pos = 0
                var seen = 0
                while (pos < mask.length && seen < offset) {
                    if (mask[pos] == '#') seen++
                    pos++
                }
                return pos.coerceAtMost(shown.length)
            }

            override fun transformedToOriginal(offset: Int): Int =
                mask.take(offset.coerceAtMost(mask.length)).count { it == '#' }.coerceAtMost(digits.length)
        }
        return TransformedText(AnnotatedString(shown.toString()), mapping)
    }
}

object Masks {
    val Cpf = DigitMask("###.###.###-##")
    val Birth = DigitMask("##/##/####")
    val Phone = DigitMask("(##) #####-####")
    val Cep = DigitMask("#####-###")
}
