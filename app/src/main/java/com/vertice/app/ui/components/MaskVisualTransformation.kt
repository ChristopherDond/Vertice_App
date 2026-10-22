package com.vertice.app.ui.components

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Aplica máscara visual a um texto contendo apenas dígitos.
 * O caractere '#' na máscara representa um dígito do texto original.
 * Ex: MaskVisualTransformation("###.###.###-##") para CPF
 *     MaskVisualTransformation("#####-###") para CEP
 *     MaskVisualTransformation("##/##/####") para Data
 */
class MaskVisualTransformation(private val mask: String) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val raw = text.text
        val out = StringBuilder()
        var textIdx = 0
        for (m in mask) {
            if (textIdx >= raw.length) break
            if (m == '#') {
                out.append(raw[textIdx++])
            } else {
                out.append(m)
            }
        }
        val mapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var pos = 0
                var seen = 0
                while (pos < mask.length && seen < offset) {
                    if (mask[pos] == '#') seen++
                    pos++
                }
                return pos.coerceAtMost(out.length)
            }

            override fun transformedToOriginal(offset: Int): Int =
                mask.take(offset.coerceAtMost(mask.length)).count { it == '#' }.coerceAtMost(raw.length)
        }
        return TransformedText(AnnotatedString(out.toString()), mapping)
    }
}

/**
 * Máscara dinâmica para telefones brasileiros:
 * - Até 10 dígitos: (##) ####-#### (fixo)
 * - 11 dígitos: (##) #####-#### (celular)
 */
class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text
        val mask = if (digits.length > 10) "(##) #####-####" else "(##) ####-####"
        val out = StringBuilder()
        var textIdx = 0
        for (m in mask) {
            if (textIdx >= digits.length) break
            if (m == '#') {
                out.append(digits[textIdx++])
            } else {
                out.append(m)
            }
        }
        val mapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var pos = 0
                var seen = 0
                while (pos < mask.length && seen < offset) {
                    if (mask[pos] == '#') seen++
                    pos++
                }
                return pos.coerceAtMost(out.length)
            }

            override fun transformedToOriginal(offset: Int): Int =
                mask.take(offset.coerceAtMost(mask.length)).count { it == '#' }.coerceAtMost(digits.length)
        }
        return TransformedText(AnnotatedString(out.toString()), mapping)
    }
}
