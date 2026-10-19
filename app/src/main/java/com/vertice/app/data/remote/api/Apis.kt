package com.vertice.app.data.remote.api

import com.vertice.app.data.remote.dto.BalanceDto
import com.vertice.app.data.remote.dto.CardDto
import com.vertice.app.data.remote.dto.CardRequestDto
import com.vertice.app.data.remote.dto.ChangeEmailRequestDto
import com.vertice.app.data.remote.dto.ChangePasswordRequestDto
import com.vertice.app.data.remote.dto.ChangePhoneRequestDto
import com.vertice.app.data.remote.dto.AutoRenewRequestDto
import com.vertice.app.data.remote.dto.FreelancerDto
import com.vertice.app.data.remote.dto.LoginRequestDto
import com.vertice.app.data.remote.dto.ModuleDto
import com.vertice.app.data.remote.dto.PlanDto
import com.vertice.app.data.remote.dto.ProviderLoginRequestDto
import com.vertice.app.data.remote.dto.RecoveryCodeRequestDto
import com.vertice.app.data.remote.dto.RegisterRequestDto
import com.vertice.app.data.remote.dto.ResetPasswordRequestDto
import com.vertice.app.data.remote.dto.ServiceOfferDto
import com.vertice.app.data.remote.dto.ServiceRequestDto
import com.vertice.app.data.remote.dto.SessionDto
import com.vertice.app.data.remote.dto.SubscribeRequestDto
import com.vertice.app.data.remote.dto.SubscriptionDto
import com.vertice.app.data.remote.dto.TransactionDto
import com.vertice.app.data.remote.dto.UpdateProfileRequestDto
import com.vertice.app.data.remote.dto.UserDto
import com.vertice.app.data.remote.dto.VioletaStatusDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body body: LoginRequestDto): SessionDto

    @POST("auth/provider")
    suspend fun loginWithProvider(@Body body: ProviderLoginRequestDto): SessionDto

    @POST("auth/register")
    suspend fun register(@Body body: RegisterRequestDto): SessionDto

    @POST("auth/recovery")
    suspend fun requestRecoveryCode(@Body body: RecoveryCodeRequestDto)

    @POST("auth/reset-password")
    suspend fun resetPassword(@Body body: ResetPasswordRequestDto)

    @POST("auth/change-password")
    suspend fun changePassword(@Body body: ChangePasswordRequestDto)

    @POST("auth/change-email")
    suspend fun changeEmail(@Body body: ChangeEmailRequestDto): UserDto

    @POST("auth/change-phone")
    suspend fun changePhone(@Body body: ChangePhoneRequestDto): UserDto

    @POST("auth/logout")
    suspend fun logout()
}

interface ProfileApi {
    @GET("profile/me")
    suspend fun me(): UserDto

    @PUT("profile/me")
    suspend fun update(@Body body: UpdateProfileRequestDto): UserDto

    @GET("profile/trilha")
    suspend fun trilhaProgress(): List<String>

    @PUT("profile/trilha/{lessonId}")
    suspend fun setLessonDone(@Path("lessonId") lessonId: String, @Query("done") done: Boolean)

    @GET("profile/violeta")
    suspend fun violeta(): VioletaStatusDto

    @PUT("profile/violeta")
    suspend fun setVioleta(@Query("enabled") enabled: Boolean): VioletaStatusDto

    @POST("profile/violeta/verify")
    suspend fun verifyVioleta(@Query("step") step: String, @Query("reference") reference: String): VioletaStatusDto
}

interface MatchApi {
    @GET("match/search")
    suspend fun search(
        @Query("q") query: String,
        @Query("category") category: String,
        @Query("violeta") violetaOnly: Boolean,
    ): List<FreelancerDto>

    @GET("match/{id}")
    suspend fun byId(@Path("id") id: String): FreelancerDto

    @POST("match/requests")
    suspend fun requestService(@Body body: ServiceRequestDto)

    @POST("match/offers")
    suspend fun publishOffer(@Body body: ServiceOfferDto)
}

interface FinanceApi {
    @GET("finance/balance")
    suspend fun balance(): BalanceDto

    @GET("finance/transactions")
    suspend fun transactions(): List<TransactionDto>

    @GET("finance/cards")
    suspend fun cards(): List<CardDto>

    @POST("finance/cards")
    suspend fun addCard(@Body body: CardRequestDto): CardDto

    @DELETE("finance/cards/{id}")
    suspend fun removeCard(@Path("id") id: String)

    @GET("finance/subscription")
    suspend fun subscription(): SubscriptionDto

    @PUT("finance/subscription/auto-renew")
    suspend fun setAutoRenew(@Body body: AutoRenewRequestDto): SubscriptionDto

    @DELETE("finance/subscription")
    suspend fun cancelSubscription()
}

interface SubscriptionApi {
    @GET("plans")
    suspend fun plans(): List<PlanDto>

    @POST("subscriptions")
    suspend fun subscribe(@Body body: SubscribeRequestDto): SubscriptionDto
}

interface LearningApi {
    @GET("learning/modules")
    suspend fun modules(): List<ModuleDto>
}
