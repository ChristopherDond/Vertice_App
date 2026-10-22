package com.vertice.app.domain.learning

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * Dias seguidos com ao menos uma lição concluída.
 * A sequência continua viva se a última lição foi hoje ou ontem.
 */
fun computeStudyStreakDays(
    completedAtMillis: List<Long>,
    today: LocalDate,
    zone: ZoneId,
): Int {
    val days = completedAtMillis
        .map { Instant.ofEpochMilli(it).atZone(zone).toLocalDate() }
        .toSet()
    var cursor = if (today in days) today else today.minusDays(1)
    var streak = 0
    while (cursor in days) {
        streak++
        cursor = cursor.minusDays(1)
    }
    return streak
}
