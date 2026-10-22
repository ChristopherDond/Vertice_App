package com.vertice.app.domain

import com.vertice.app.domain.validation.BirthDateValidator
import com.vertice.app.domain.validation.BrPhoneValidator
import com.vertice.app.domain.validation.EmailValidator
import com.vertice.app.domain.validation.FullNameValidator
import com.vertice.app.domain.validation.UfValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.GregorianCalendar

class FormValidatorsTest {
    private val now = GregorianCalendar(2026, 9, 21)

    @Test fun birthDate() {
        assertTrue(BirthDateValidator.isValid("15031998", now))
        assertTrue(BirthDateValidator.isValid("29022000", now))
        assertFalse(BirthDateValidator.isValid("31022000", now))
        assertFalse(BirthDateValidator.isValid("29022001", now))
        assertFalse(BirthDateValidator.isValid("00012000", now))
        assertFalse(BirthDateValidator.isValid("01132000", now))
        assertFalse(BirthDateValidator.isValid("01012027", now))
        assertFalse(BirthDateValidator.isValid("01011850", now))
        assertFalse(BirthDateValidator.isValid("1503199", now))
        assertFalse(BirthDateValidator.isValid("1503199a", now))
    }

    @Test fun fullName() {
        assertTrue(FullNameValidator.isValid("Ana Maria Rodrigues"))
        assertTrue(FullNameValidator.isValid("João D'Ávila-Silva"))
        assertFalse(FullNameValidator.isValid("Ana"))
        assertFalse(FullNameValidator.isValid("Ana 123"))
        assertFalse(FullNameValidator.isValid("   "))
    }

    @Test fun email() {
        assertTrue(EmailValidator.isValid("ana@exemplo.com.br"))
        assertFalse(EmailValidator.isValid("ana@exemplo"))
        assertFalse(EmailValidator.isValid("ana exemplo.com"))
        assertFalse(EmailValidator.isValid("@exemplo.com"))
    }

    @Test fun phone() {
        assertTrue(BrPhoneValidator.isValid("11987654321"))
        assertTrue(BrPhoneValidator.isValid("1134567890"))
        assertFalse(BrPhoneValidator.isValid("11887654321"))
        assertFalse(BrPhoneValidator.isValid("0987654321"))
        assertFalse(BrPhoneValidator.isValid("119876543"))
    }

    @Test fun uf() {
        assertTrue(UfValidator.isValid("sp"))
        assertFalse(UfValidator.isValid("XX"))
        assertFalse(UfValidator.isValid("S"))
    }
}
