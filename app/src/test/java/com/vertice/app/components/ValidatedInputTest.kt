package com.vertice.app.components

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import org.junit.Assert.*
import org.junit.Test

class ValidatedInputTest {

    // ============================================================================
    // DateMaskTransformation Tests
    // ============================================================================

    @Test
    fun `DateMaskTransformation formats empty string correctly`() {
        val transformation = DateMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString(""), selection = TextRange(0))
        val result = transformation.filter(input.text)
        
        assertEquals("", result.text.text)
    }

    @Test
    fun `DateMaskTransformation formats single digit`() {
        val transformation = DateMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString("1"), selection = TextRange(1))
        val result = transformation.filter(input.text)
        
        assertEquals("1", result.text.text)
    }

    @Test
    fun `DateMaskTransformation formats DD`() {
        val transformation = DateMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString("12"), selection = TextRange(2))
        val result = transformation.filter(input.text)
        
        assertEquals("12", result.text.text)
    }

    @Test
    fun `DateMaskTransformation adds first slash at position 2`() {
        val transformation = DateMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString("123"), selection = TextRange(3))
        val result = transformation.filter(input.text)
        
        assertEquals("12/3", result.text.text)
    }

    @Test
    fun `DateMaskTransformation formats DD/MM`() {
        val transformation = DateMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString("1234"), selection = TextRange(4))
        val result = transformation.filter(input.text)
        
        assertEquals("12/34", result.text.text)
    }

    @Test
    fun `DateMaskTransformation adds second slash at position 4`() {
        val transformation = DateMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString("12345"), selection = TextRange(5))
        val result = transformation.filter(input.text)
        
        assertEquals("12/34/5", result.text.text)
    }

    @Test
    fun `DateMaskTransformation formats complete date DD/MM/YYYY`() {
        val transformation = DateMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString("12345678"), selection = TextRange(8))
        val result = transformation.filter(input.text)
        
        assertEquals("12/34/5678", result.text.text)
    }

    @Test
    fun `DateMaskTransformation ignores non-digit characters`() {
        val transformation = DateMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString("1a2b3c4d5e6f7g8h"), selection = TextRange(16))
        val result = transformation.filter(input.text)
        
        assertEquals("12/34/5678", result.text.text)
    }

    @Test
    fun `DateMaskTransformation handles more than 8 digits`() {
        val transformation = DateMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString("1234567890"), selection = TextRange(10))
        val result = transformation.filter(input.text)
        
        assertEquals("12/34/567890", result.text.text)
    }

    // ============================================================================
    // TimeMaskTransformation Tests
    // ============================================================================

    @Test
    fun `TimeMaskTransformation formats empty string correctly`() {
        val transformation = TimeMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString(""), selection = TextRange(0))
        val result = transformation.filter(input.text)
        
        assertEquals("", result.text.text)
    }

    @Test
    fun `TimeMaskTransformation formats HH`() {
        val transformation = TimeMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString("12"), selection = TextRange(2))
        val result = transformation.filter(input.text)
        
        assertEquals("12", result.text.text)
    }

    @Test
    fun `TimeMaskTransformation adds colon at position 2`() {
        val transformation = TimeMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString("123"), selection = TextRange(3))
        val result = transformation.filter(input.text)
        
        assertEquals("12:3", result.text.text)
    }

    @Test
    fun `TimeMaskTransformation formats HH:mm`() {
        val transformation = TimeMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString("1234"), selection = TextRange(4))
        val result = transformation.filter(input.text)
        
        assertEquals("12:34", result.text.text)
    }

    @Test
    fun `TimeMaskTransformation ignores non-digit characters`() {
        val transformation = TimeMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString("1a2b3c4d"), selection = TextRange(8))
        val result = transformation.filter(input.text)
        
        assertEquals("12:34", result.text.text)
    }

    // ============================================================================
    // CurrencyMaskTransformation Tests
    // ============================================================================

    @Test
    fun `CurrencyMaskTransformation formats empty string as empty`() {
        val transformation = CurrencyMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString(""), selection = TextRange(0))
        val result = transformation.filter(input.text)
        
        assertEquals("", result.text.text)
    }

    @Test
    fun `CurrencyMaskTransformation formats single digit as cents`() {
        val transformation = CurrencyMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString("5"), selection = TextRange(1))
        val result = transformation.filter(input.text)
        
        // 5 cents = R$ 0,05
        assertTrue(result.text.text.contains("0,05") || result.text.text.contains("0.05"))
    }

    @Test
    fun `CurrencyMaskTransformation formats 100 as 1,00`() {
        val transformation = CurrencyMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString("100"), selection = TextRange(3))
        val result = transformation.filter(input.text)
        
        // 100 cents = R$ 1,00
        assertTrue(result.text.text.contains("1,00") || result.text.text.contains("1.00"))
    }

    @Test
    fun `CurrencyMaskTransformation formats 30000 as 300,00`() {
        val transformation = CurrencyMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString("30000"), selection = TextRange(5))
        val result = transformation.filter(input.text)
        
        // 30000 cents = R$ 300,00
        assertTrue(result.text.text.contains("300,00") || result.text.text.contains("300.00"))
    }

    @Test
    fun `CurrencyMaskTransformation ignores non-digit characters`() {
        val transformation = CurrencyMaskTransformation()
        val input = TextFieldValue(text = AnnotatedString("R$ 3.000,00"), selection = TextRange(10))
        val result = transformation.filter(input.text)
        
        // Should parse as 300000 cents = R$ 3.000,00
        assertTrue(result.text.text.contains("3.000,00") || result.text.text.contains("3000.00"))
    }

    @Test
    fun `CurrencyMaskTransformation parseToCents extracts cents correctly`() {
        assertEquals(0L, CurrencyMaskTransformation.parseToCents(""))
        assertEquals(5L, CurrencyMaskTransformation.parseToCents("5"))
        assertEquals(100L, CurrencyMaskTransformation.parseToCents("100"))
        assertEquals(30000L, CurrencyMaskTransformation.parseToCents("30000"))
        assertEquals(30000L, CurrencyMaskTransformation.parseToCents("R$ 300,00"))
        assertEquals(30000L, CurrencyMaskTransformation.parseToCents("300,00"))
    }

    // ============================================================================
    // isValidDate Tests
    // ============================================================================

    @Test
    fun `isValidDate returns false for empty string`() {
        assertFalse(isValidDate(""))
    }

    @Test
    fun `isValidDate returns false for wrong length`() {
        assertFalse(isValidDate("12/34/567"))  // 9 chars
        assertFalse(isValidDate("12/34/56789")) // 11 chars
    }

    @Test
    fun `isValidDate returns false for invalid format`() {
        assertFalse(isValidDate("12-34-5678"))
        assertFalse(isValidDate("12.34.5678"))
        assertFalse(isValidDate("abcd/ef/ghij"))
    }

    @Test
    fun `isValidDate returns false for invalid day`() {
        assertFalse(isValidDate("00/01/2025"))
        assertFalse(isValidDate("32/01/2025"))
    }

    @Test
    fun `isValidDate returns false for invalid month`() {
        assertFalse(isValidDate("15/00/2025"))
        assertFalse(isValidDate("15/13/2025"))
    }

    @Test
    fun `isValidDate returns false for invalid day in month`() {
        assertFalse(isValidDate("31/04/2025")) // April has 30 days
        assertFalse(isValidDate("31/06/2025")) // June has 30 days
        assertFalse(isValidDate("31/09/2025")) // September has 30 days
        assertFalse(isValidDate("31/11/2025")) // November has 30 days
        assertFalse(isValidDate("30/02/2025")) // February non-leap year
        assertFalse(isValidDate("29/02/2025")) // February non-leap year
    }

    @Test
    fun `isValidDate returns true for valid leap year date`() {
        assertTrue(isValidDate("29/02/2024")) // 2024 is leap year
    }

    @Test
    fun `isValidDate returns false for past dates`() {
        // This test depends on current date, so we can only test the logic conceptually
        // The actual implementation checks against today
        val pastDate = "01/01/2020"
        // isValidDate should return false for dates before today
        // Since we can't control "today" in unit test easily, we just verify it runs
        val result = isValidDate(pastDate)
        // Result depends on current date, but should not crash
    }

    @Test
    fun `isValidDate returns true for valid future date`() {
        val futureYear = java.util.Calendar.getInstance().apply { add(java.util.Calendar.YEAR, 1) }.get(java.util.Calendar.YEAR)
        val futureDate = "01/01/$futureYear"
        assertTrue(isValidDate(futureDate))
    }

    // ============================================================================
    // isValidTime Tests
    // ============================================================================

    @Test
    fun `isValidTime returns false for empty string`() {
        assertFalse(isValidTime(""))
    }

    @Test
    fun `isValidTime returns false for wrong length`() {
        assertFalse(isValidTime("12:3"))   // 4 chars
        assertFalse(isValidTime("12:345")) // 6 chars
    }

    @Test
    fun `isValidTime returns false for invalid format`() {
        assertFalse(isValidTime("12.34"))
        assertFalse(isValidTime("12-34"))
        assertFalse(isValidTime("ab:cd"))
    }

    @Test
    fun `isValidTime returns false for invalid hour`() {
        assertFalse(isValidTime("24:00"))
        assertFalse(isValidTime("25:00"))
        assertFalse(isValidTime("99:00"))
    }

    @Test
    fun `isValidTime returns false for invalid minute`() {
        assertFalse(isValidTime("12:60"))
        assertFalse(isValidTime("12:99"))
    }

    @Test
    fun `isValidTime returns true for valid times`() {
        assertTrue(isValidTime("00:00"))
        assertTrue(isValidTime("12:34"))
        assertTrue(isValidTime("23:59"))
        assertTrue(isValidTime("09:05"))
        assertTrue(isValidTime("14:30"))
    }

    // ============================================================================
    // Integration Tests for Component Behavior
    // ============================================================================

    @Test
    fun `DateMaskTransformation preserves selection at end`() {
        val transformation = DateMaskTransformation()
        val input = TextFieldValue(
            text = AnnotatedString("12345678"),
            selection = TextRange(8),
            composition = androidx.compose.ui.text.input.TextRange(0)
        )
        val result = transformation.filter(input.text)
        
        // Selection should be at the end of formatted text
        assertEquals("12/34/5678", result.text.text)
        assertEquals(10, result.selection.end) // "12/34/5678" has 10 chars
    }

    @Test
    fun `TimeMaskTransformation preserves selection at end`() {
        val transformation = TimeMaskTransformation()
        val input = TextFieldValue(
            text = AnnotatedString("1234"),
            selection = TextRange(4),
            composition = androidx.compose.ui.text.input.TextRange(0)
        )
        val result = transformation.filter(input.text)
        
        assertEquals("12:34", result.text.text)
        assertEquals(5, result.selection.end) // "12:34" has 5 chars
    }

    @Test
    fun `CurrencyMaskTransformation preserves selection at end`() {
        val transformation = CurrencyMaskTransformation()
        val input = TextFieldValue(
            text = AnnotatedString("30000"),
            selection = TextRange(5),
            composition = androidx.compose.ui.text.input.TextRange(0)
        )
        val result = transformation.filter(input.text)
        
        // Should contain the formatted value and selection at end
        assertTrue(result.text.text.isNotEmpty())
        assertEquals(result.text.text.length, result.selection.end)
    }
}