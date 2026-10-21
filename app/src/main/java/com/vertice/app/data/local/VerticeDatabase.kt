package com.vertice.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        LessonProgressEntity::class,
        CachedProfileEntity::class,
        ServiceDraftEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class VerticeDatabase : RoomDatabase() {
    abstract fun lessonProgressDao(): LessonProgressDao
    abstract fun cachedProfileDao(): CachedProfileDao
    abstract fun serviceDraftDao(): ServiceDraftDao

    companion object {
        @Volatile private var instance: VerticeDatabase? = null

        fun get(context: Context): VerticeDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                VerticeDatabase::class.java,
                "vertice.db",
            ).fallbackToDestructiveMigration().build().also { instance = it }
        }
    }
}
