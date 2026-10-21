package com.vertice.app.domain.validation

object CpfValidator {

    fun isValid(raw: String): Boolean {
        val digits = raw.filter { it.isDigit() }
        if (digits.length != 11) return false
        if (digits.all { it == digits.first() }) return false
        val firstCheck = checkDigit(digits, 9)
        if (firstCheck != digits[9].digitToInt()) return false
        val secondCheck = checkDigit(digits, 10)
        return secondCheck == digits[10].digitToInt()
    }

    private fun checkDigit(digits: String, length: Int): Int {
        var sum = 0
        for (i in 0 until length) {
            sum += digits[i].digitToInt() * (length + 1 - i)
        }
        val rest = sum % 11
        return if (rest < 2) 0 else 11 - rest
    }

    fun format(raw: String): String {
        val digits = raw.filter { it.isDigit() }.take(11)
        return buildString {
            digits.forEachIndexed { index, c ->
                if (index == 3 || index == 6) append('.')
                if (index == 9) append('-')
                append(c)
            }
        }
    }
}

object PasswordValidator {

    fun isValid(password: String): Boolean = score(password) >= 1

    fun score(password: String): Int {
        var score = 0
        if (password.length >= 8) score++
        if (password.any { it.isUpperCase() }) score++
        if (password.any { it.isDigit() }) score++
        if (password.any { !it.isLetterOrDigit() }) score++
        return score
    }

    fun requirementLabel(password: String): String = when {
        password.isEmpty() -> "Mínimo 8 caracteres"
        score(password) == 1 -> "Fraca — adicione números"
        score(password) == 2 -> "Razoável — adicione maiúscula"
        score(password) == 3 -> "Boa"
        else -> "Forte"
    }
}

object PhoneValidator {
    fun format(raw: String): String {
        val digits = raw.filter { it.isDigit() }.take(11)
        if (digits.isEmpty()) return ""
        return buildString {
            append("(")
            digits.forEachIndexed { index, c ->
                if (index == 2) append(") ")
                if (index == 7) append("-")
                append(c)
            }
        }
    }
}

object CurrencyFormatter {
    fun format(value: Double): String {
        val cents = Math.round(value * 100)
        val reais = cents / 100
        val rest = Math.abs(cents % 100)
        return "R\$ " + thousands(reais) + "," + rest.toString().padStart(2, '0')
    }

    fun formatBare(value: Double): String {
        val cents = Math.round(value * 100)
        val reais = cents / 100
        val rest = Math.abs(cents % 100)
        return thousands(reais) + "," + rest.toString().padStart(2, '0')
    }

    private fun thousands(value: Long): String {
        val text = Math.abs(value).toString()
        val builder = StringBuilder()
        text.reversed().forEachIndexed { index, c ->
            if (index > 0 && index % 3 == 0) builder.append('.')
            builder.append(c)
        }
        val sign = if (value < 0) "-" else ""
        return sign + builder.reverse().toString()
    }
}
