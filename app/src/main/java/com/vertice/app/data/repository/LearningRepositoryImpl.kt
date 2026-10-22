package com.vertice.app.data.repository

import com.vertice.app.core.result.VerticeResult
import com.vertice.app.core.result.runResult
import com.vertice.app.data.local.LessonProgressDao
import com.vertice.app.data.local.LessonProgressEntity
import com.vertice.app.data.mapper.toDomain
import com.vertice.app.data.remote.api.LearningApi
import com.vertice.app.domain.learning.computeStudyStreakDays
import com.vertice.app.domain.model.TModule
import com.vertice.app.domain.repository.LearningRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneId

class LearningRepositoryImpl(
    private val api: LearningApi,
    private val lessonProgressDao: LessonProgressDao,
) : LearningRepository {

    override suspend fun modules(): VerticeResult<List<TModule>> = runResult {
        api.modules().map { it.toDomain() }
    }

    override fun progress(): Flow<Set<String>> =
        lessonProgressDao.doneLessonIds().map { it.toSet() }

    override fun studyStreakDays(): Flow<Int> =
        lessonProgressDao.doneTimestamps().map { stamps ->
            computeStudyStreakDays(stamps, LocalDate.now(), ZoneId.systemDefault())
        }

    override suspend fun markLesson(lessonId: String, done: Boolean): VerticeResult<Unit> = runResult {
        lessonProgressDao.upsert(
            LessonProgressEntity(
                lessonId = lessonId,
                moduleId = lessonId.takeWhile { !it.isDigit() },
                done = done,
                updatedAt = System.currentTimeMillis(),
            ),
        )
    }
}
