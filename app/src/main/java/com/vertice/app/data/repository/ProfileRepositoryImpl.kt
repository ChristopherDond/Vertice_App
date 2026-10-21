package com.vertice.app.data.repository

import com.vertice.app.core.result.VerticeResult
import com.vertice.app.core.result.runResult
import com.vertice.app.data.local.CachedProfileDao
import com.vertice.app.data.local.CachedProfileEntity
import com.vertice.app.data.local.LessonProgressDao
import com.vertice.app.data.local.LessonProgressEntity
import com.vertice.app.data.mapper.toDomain
import com.vertice.app.data.mapper.toDto
import com.vertice.app.data.remote.api.ProfileApi
import com.vertice.app.data.remote.dto.UpdateProfileRequestDto
import com.vertice.app.domain.model.ProfileForm
import com.vertice.app.domain.model.User
import com.vertice.app.domain.model.VioletaStatus
import com.vertice.app.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileRepositoryImpl(
    private val api: ProfileApi,
    private val lessonProgressDao: LessonProgressDao,
    private val cachedProfileDao: CachedProfileDao,
) : ProfileRepository {

    override suspend fun me(): VerticeResult<User> = runResult {
        val user = api.me().toDomain()
        cachedProfileDao.upsert(user.toEntity())
        user
    }

    override suspend fun update(form: ProfileForm): VerticeResult<User> = runResult {
        val user = api.update(
            UpdateProfileRequestDto(
                fullName = form.fullName.trim(),
                bio = form.bio.trim(),
                skills = form.skills,
                email = form.email.trim(),
                phone = form.phone,
                address = form.address?.toDto(),
                notificationsEnabled = form.notificationsEnabled,
            ),
        ).toDomain()
        cachedProfileDao.upsert(user.toEntity())
        user
    }

    override fun trilhaProgress(): Flow<Set<String>> =
        lessonProgressDao.doneLessonIds().map { it.toSet() }

    override suspend fun refreshTrilha(): VerticeResult<Set<String>> = runResult {
        val done = api.trilhaProgress().toSet()
        lessonProgressDao.clear()
        done.forEach { lessonId ->
            lessonProgressDao.upsert(
                LessonProgressEntity(
                    lessonId = lessonId,
                    moduleId = lessonId.takeWhile { !it.isDigit() },
                    done = true,
                    updatedAt = System.currentTimeMillis(),
                ),
            )
        }
        done
    }

    override suspend fun markLessonDone(lessonId: String, done: Boolean): VerticeResult<Unit> = runResult {
        lessonProgressDao.upsert(
            LessonProgressEntity(
                lessonId = lessonId,
                moduleId = lessonId.takeWhile { !it.isDigit() },
                done = done,
                updatedAt = System.currentTimeMillis(),
            ),
        )
        api.setLessonDone(lessonId, done)
    }

    override suspend fun violeta(): VerticeResult<VioletaStatus> = runResult {
        api.violeta().toDomain()
    }

    override suspend fun setVioleta(enabled: Boolean): VerticeResult<VioletaStatus> = runResult {
        api.setVioleta(enabled).toDomain()
    }

    override suspend fun verifyVioleta(step: String, reference: String): VerticeResult<VioletaStatus> =
        runResult { api.verifyVioleta(step, reference).toDomain() }

    private fun User.toEntity() = CachedProfileEntity(
        id = id,
        name = name,
        email = email,
        phone = phone,
        cpfMasked = cpfMasked,
        bio = bio,
        skills = skills.joinToString("|"),
        isPro = isPro,
        violetaVerified = violetaVerified,
        profileCompletion = profileCompletion,
        rating = rating,
        completedServices = completedServices,
        updatedAt = System.currentTimeMillis(),
    )
}
