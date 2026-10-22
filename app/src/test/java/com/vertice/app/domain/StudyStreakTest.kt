package com.vertice.app.domain

import com.vertice.app.domain.learning.computeStudyStreakDays
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneOffset

class StudyStreakTest {
    private val zone = ZoneOffset.UTC
    private val today = LocalDate.of(2026, 10, 21)

    private fun at(date: LocalDate): Long =
        date.atTime(12, 0).toInstant(zone).toEpochMilli()

    @Test
    fun `sem licoes concluidas a sequencia e zero`() {
        assertEquals(0, computeStudyStreakDays(emptyList(), today, zone))
    }

    @Test
    fun `tres dias seguidos terminando hoje`() {
        val stamps = listOf(0L, 1L, 2L).map { at(today.minusDays(it)) }
        assertEquals(3, computeStudyStreakDays(stamps, today, zone))
    }

    @Test
    fun `sequencia continua viva se a ultima licao foi ontem`() {
        val stamps = listOf(1L, 2L).map { at(today.minusDays(it)) }
        assertEquals(2, computeStudyStreakDays(stamps, today, zone))
    }

    @Test
    fun `um dia sem estudar zera a sequencia`() {
        val stamps = listOf(2L, 3L).map { at(today.minusDays(it)) }
        assertEquals(0, computeStudyStreakDays(stamps, today, zone))
    }

    @Test
    fun `varias licoes no mesmo dia contam como um dia`() {
        val stamps = listOf(at(today), at(today) + 1000, at(today.minusDays(1)))
        assertEquals(2, computeStudyStreakDays(stamps, today, zone))
    }
}
