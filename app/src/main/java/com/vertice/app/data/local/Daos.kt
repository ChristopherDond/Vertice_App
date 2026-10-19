package com.vertice.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonProgressDao {

    @Query("SELECT lessonId FROM lesson_progress WHERE done = 1")
    fun doneLessonIds(): Flow<List<String>>

    @Query("SELECT lessonId FROM lesson_progress WHERE moduleId = :moduleId AND done = 1")
    suspend fun doneByModule(moduleId: String): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: LessonProgressEntity)

    @Query("DELETE FROM lesson_progress")
    suspend fun clear()
}

@Dao
interface CachedProfileDao {

    @Query("SELECT * FROM cached_profile LIMIT 1")
    fun profile(): Flow<CachedProfileEntity?>

    @Query("SELECT * FROM cached_profile LIMIT 1")
    suspend fun profileOnce(): CachedProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: CachedProfileEntity)

    @Query("DELETE FROM cached_profile")
    suspend fun clear()
}

@Dao
interface ServiceDraftDao {

    @Query("SELECT * FROM service_draft WHERE freelancerId = :freelancerId LIMIT 1")
    suspend fun draft(freelancerId: String): ServiceDraftEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: ServiceDraftEntity)

    @Query("DELETE FROM service_draft WHERE freelancerId = :freelancerId")
    suspend fun clearDraft(freelancerId: String)

    @Query("DELETE FROM service_draft")
    suspend fun clearAll()
}
