package com.vertice.app.domain.model

data class LessonCard(val heading: String, val body: String, val tip: String? = null)

data class Lesson(
    val id: String,
    val title: String,
    val emoji: String,
    val cards: List<LessonCard>,
    val locked: Boolean = false,
)

data class TModule(
    val id: String,
    val title: String,
    val lessons: List<Lesson>,
)
