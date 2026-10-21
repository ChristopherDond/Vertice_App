package com.vertice.app.core.di

import android.content.Context
import com.vertice.app.BuildConfig
import com.vertice.app.core.session.SessionManager
import com.vertice.app.data.local.VerticeDatabase
import com.vertice.app.data.remote.AuthInterceptor
import com.vertice.app.data.remote.api.AuthApi
import com.vertice.app.data.remote.api.FinanceApi
import com.vertice.app.data.remote.api.LearningApi
import com.vertice.app.data.remote.api.MatchApi
import com.vertice.app.data.remote.api.ProfileApi
import com.vertice.app.data.remote.api.SubscriptionApi
import com.vertice.app.data.remote.fake.FakeAuthApi
import com.vertice.app.data.remote.fake.FakeFinanceApi
import com.vertice.app.data.remote.fake.FakeLearningApi
import com.vertice.app.data.remote.fake.FakeMatchApi
import com.vertice.app.data.remote.fake.FakeProfileApi
import com.vertice.app.data.remote.fake.FakeSubscriptionApi
import com.vertice.app.data.repository.AuthRepositoryImpl
import com.vertice.app.data.repository.FinanceRepositoryImpl
import com.vertice.app.data.repository.LearningRepositoryImpl
import com.vertice.app.data.repository.MatchRepositoryImpl
import com.vertice.app.data.repository.ProfileRepositoryImpl
import com.vertice.app.data.repository.SubscriptionRepositoryImpl
import com.vertice.app.domain.repository.AuthRepository
import com.vertice.app.domain.repository.FinanceRepository
import com.vertice.app.domain.repository.LearningRepository
import com.vertice.app.domain.repository.MatchRepository
import com.vertice.app.domain.repository.ProfileRepository
import com.vertice.app.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

object ServiceLocator {

    private lateinit var appContext: Context

    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        coerceInputValues = true
    }

    private val currentToken: String?
        get() = runBlocking {
            runCatching { sessionManager.session.first()?.token }.getOrNull()
        }

    lateinit var sessionManager: SessionManager
        private set

    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor { currentToken })
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BASIC
                    })
                }
            }
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttp)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private val useFake: Boolean get() = BuildConfig.USE_FAKE_BACKEND

    val database: VerticeDatabase by lazy { VerticeDatabase.get(appContext) }

    private val authApi: AuthApi by lazy { if (useFake) FakeAuthApi() else retrofit.create(AuthApi::class.java) }
    private val profileApi: ProfileApi by lazy { if (useFake) FakeProfileApi() else retrofit.create(ProfileApi::class.java) }
    private val matchApi: MatchApi by lazy { if (useFake) FakeMatchApi() else retrofit.create(MatchApi::class.java) }
    private val financeApi: FinanceApi by lazy { if (useFake) FakeFinanceApi() else retrofit.create(FinanceApi::class.java) }
    private val subscriptionApi: SubscriptionApi by lazy { if (useFake) FakeSubscriptionApi() else retrofit.create(SubscriptionApi::class.java) }
    private val learningApi: LearningApi by lazy { if (useFake) FakeLearningApi() else retrofit.create(LearningApi::class.java) }

    val authRepository: AuthRepository by lazy { AuthRepositoryImpl(authApi, sessionManager) }
    val profileRepository: ProfileRepository by lazy {
        ProfileRepositoryImpl(profileApi, database.lessonProgressDao(), database.cachedProfileDao())
    }
    val matchRepository: MatchRepository by lazy { MatchRepositoryImpl(matchApi) }
    val financeRepository: FinanceRepository by lazy { FinanceRepositoryImpl(financeApi) }
    val subscriptionRepository: SubscriptionRepository by lazy { SubscriptionRepositoryImpl(subscriptionApi) }
    val learningRepository: LearningRepository by lazy {
        LearningRepositoryImpl(learningApi, database.lessonProgressDao())
    }

    fun init(context: Context) {
        appContext = context.applicationContext
        sessionManager = SessionManager(appContext)
    }
}
