package com.vertice.app.domain.validation

import java.util.Calendar
import java.util.GregorianCalendar

object FullNameValidator {
    /** Pelo menos nome e sobrenome, só letras (aceita acento, hífen e apóstrofo). */
    fun isValid(name: String): Boolean {
        val parts = name.trim().split(Regex("\\s+"))
        return parts.size >= 2 && parts.all { p -> p.any(Char::isLetter) && p.all { it.isLetter() || it == '\'' || it == '-' || it == '.' } }
    }
}

object BirthDateValidator {
    /** [digits] = DDMMAAAA, só números. Rejeita data inexistente (31/02) e data futura. */
    fun isValid(digits: String, now: Calendar = Calendar.getInstance()): Boolean {
        if (digits.length != 8 || !digits.all(Char::isDigit)) return false
        val day = digits.substring(0, 2).toInt()
        val month = digits.substring(2, 4).toInt()
        val year = digits.substring(4, 8).toInt()
        if (year < 1900) return false
        val cal = GregorianCalendar().apply { isLenient = false; clear(); set(year, month - 1, day) }
        val millis = try {
            cal.timeInMillis
        } catch (e: IllegalArgumentException) {
            return false
        }
        return millis <= now.timeInMillis
    }
}

object EmailValidator {
    private val pattern = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$")
    fun isValid(email: String): Boolean = pattern.matches(email.trim())
}

object BrPhoneValidator {
    /** [digits] = DDD + número (10 ou 11 dígitos). Celular (11) começa com 9 depois do DDD. */
    fun isValid(digits: String): Boolean {
        if (!digits.all(Char::isDigit) || digits.length !in 10..11) return false
        val ddd = digits.take(2).toInt()
        if (ddd < 11) return false
        return digits.length == 10 || digits[2] == '9'
    }
}

object UfValidator {
    private val ufs = setOf(
        "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
        "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO",
    )
    fun isValid(uf: String): Boolean = uf.uppercase() in ufs
}
