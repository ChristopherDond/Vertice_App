package com.vertice.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lesson_progress")
data class LessonProgressEntity(
    @PrimaryKey val lessonId: String,
    val moduleId: String,
    val done: Boolean,
    val updatedAt: Long,
)

@Entity(tableName = "cached_profile")
data class CachedProfileEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val cpfMasked: String,
    val bio: String,
    val skills: String,
    val isPro: Boolean,
    val violetaVerified: Boolean,
    val profileCompletion: Int,
    val rating: Double,
    val completedServices: Int,
    val updatedAt: Long,
)

@Entity(tableName = "service_draft")
data class ServiceDraftEntity(
    @PrimaryKey val freelancerId: String,
    val serviceType: String,
    val date: String,
    val time: String,
    val budget: String,
    val location: String,
    val description: String,
    val urgency: String,
    val updatedAt: Long,
)
