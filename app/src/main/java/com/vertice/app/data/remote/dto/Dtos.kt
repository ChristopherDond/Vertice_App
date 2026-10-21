package com.vertice.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    @SerialName("credential") val credential: String,
    @SerialName("password") val password: String,
)

@Serializable
data class ProviderLoginRequestDto(
    @SerialName("provider") val provider: String,
    @SerialName("id_token") val idToken: String,
)

@Serializable
data class RegisterRequestDto(
    @SerialName("full_name") val fullName: String,
    @SerialName("cpf") val cpf: String,
    @SerialName("birth_date") val birthDate: String,
    @SerialName("password") val password: String,
    @SerialName("email") val email: String,
    @SerialName("phone") val phone: String,
)

@Serializable
data class RecoveryCodeRequestDto(
    @SerialName("channel") val channel: String,
    @SerialName("contact") val contact: String,
)

@Serializable
data class ResetPasswordRequestDto(
    @SerialName("contact") val contact: String,
    @SerialName("code") val code: String,
    @SerialName("new_password") val newPassword: String,
)

@Serializable
data class ChangePasswordRequestDto(
    @SerialName("current_password") val currentPassword: String,
    @SerialName("new_password") val newPassword: String,
)

@Serializable
data class ChangeEmailRequestDto(@SerialName("email") val email: String)

@Serializable
data class ChangePhoneRequestDto(
    @SerialName("phone") val phone: String,
    @SerialName("code") val code: String? = null,
)

@Serializable
data class SessionDto(
    @SerialName("token") val token: String,
    @SerialName("user_id") val userId: String,
    @SerialName("display_name") val displayName: String,
    @SerialName("is_pro") val isPro: Boolean = false,
    @SerialName("violeta_verified") val violetaVerified: Boolean = false,
)

@Serializable
data class AddressDto(
    @SerialName("cep") val cep: String,
    @SerialName("street") val street: String,
    @SerialName("number") val number: String,
    @SerialName("complement") val complement: String = "",
    @SerialName("district") val district: String,
    @SerialName("city") val city: String,
    @SerialName("state") val state: String,
)

@Serializable
data class UserDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("email") val email: String,
    @SerialName("phone") val phone: String,
    @SerialName("cpf_masked") val cpfMasked: String,
    @SerialName("bio") val bio: String = "",
    @SerialName("skills") val skills: List<String> = emptyList(),
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("address") val address: AddressDto? = null,
    @SerialName("is_pro") val isPro: Boolean = false,
    @SerialName("violeta_verified") val violetaVerified: Boolean = false,
    @SerialName("profile_completion") val profileCompletion: Int = 0,
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("completed_services") val completedServices: Int = 0,
)

@Serializable
data class UpdateProfileRequestDto(
    @SerialName("full_name") val fullName: String,
    @SerialName("bio") val bio: String,
    @SerialName("skills") val skills: List<String>,
    @SerialName("email") val email: String,
    @SerialName("phone") val phone: String,
    @SerialName("address") val address: AddressDto?,
    @SerialName("notifications_enabled") val notificationsEnabled: Boolean,
)

@Serializable
data class LessonProgressDto(
    @SerialName("lesson_id") val lessonId: String,
    @SerialName("done") val done: Boolean,
)

@Serializable
data class FreelancerDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("role") val role: String,
    @SerialName("area") val area: String,
    @SerialName("category") val category: String,
    @SerialName("match_percent") val matchPercent: Int,
    @SerialName("rating") val rating: Double,
    @SerialName("services_count") val servicesCount: Int,
    @SerialName("availability") val availability: String,
    @SerialName("bio") val bio: String,
    @SerialName("gender") val gender: String,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("hours_worked") val hoursWorked: Int = 0,
    @SerialName("jobs_done") val jobsDone: Int = 0,
    @SerialName("is_violeta") val isVioleta: Boolean = false,
    @SerialName("is_pro") val isPro: Boolean = false,
    @SerialName("skills") val skills: List<String> = emptyList(),
    @SerialName("hourly_rate") val hourlyRate: Double = 0.0,
    @SerialName("distance_km") val distanceKm: Int = 1,
)

@Serializable
data class ServiceRequestDto(
    @SerialName("freelancer_id") val freelancerId: String,
    @SerialName("service_type") val serviceType: String,
    @SerialName("date") val date: String,
    @SerialName("time") val time: String,
    @SerialName("budget") val budget: Double,
    @SerialName("location") val location: String,
    @SerialName("description") val description: String,
    @SerialName("photo_urls") val photoUrls: List<String> = emptyList(),
    @SerialName("urgency") val urgency: String = "NORMAL",
)

@Serializable
data class ServiceOfferDto(
    @SerialName("category") val category: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("price") val price: Double,
    @SerialName("city") val city: String,
    @SerialName("availability") val availability: String,
)

@Serializable
data class BalanceDto(
    @SerialName("available") val available: Double,
    @SerialName("pending") val pending: Double,
    @SerialName("currency") val currency: String = "BRL",
)

@Serializable
data class TransactionDto(
    @SerialName("id") val id: String,
    @SerialName("client_name") val clientName: String,
    @SerialName("service") val service: String,
    @SerialName("location") val location: String,
    @SerialName("amount") val amount: Double,
    @SerialName("date") val date: String,
    @SerialName("status") val status: String,
    @SerialName("withdrawable_at") val withdrawableAt: String? = null,
)

@Serializable
data class CardDto(
    @SerialName("id") val id: String,
    @SerialName("brand") val brand: String,
    @SerialName("last4") val last4: String,
    @SerialName("holder_name") val holderName: String,
    @SerialName("expiry") val expiry: String,
    @SerialName("label") val label: String,
    @SerialName("is_primary") val isPrimary: Boolean = false,
)

@Serializable
data class CardRequestDto(
    @SerialName("number") val number: String,
    @SerialName("holder_name") val holderName: String,
    @SerialName("expiry") val expiry: String,
    @SerialName("cvv") val cvv: String,
    @SerialName("document") val document: String,
    @SerialName("bank") val bank: String,
)

@Serializable
data class SubscriptionDto(
    @SerialName("active") val active: Boolean,
    @SerialName("plan_name") val planName: String,
    @SerialName("monthly_price") val monthlyPrice: Double,
    @SerialName("next_charge_date") val nextChargeDate: String,
    @SerialName("auto_renew") val autoRenew: Boolean,
    @SerialName("auto_renew_method") val autoRenewMethod: String,
    @SerialName("status") val status: String,
)

@Serializable
data class AutoRenewRequestDto(
    @SerialName("method") val method: String,
    @SerialName("enabled") val enabled: Boolean,
)

@Serializable
data class PlanDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("monthly_price") val monthlyPrice: Double,
    @SerialName("benefits") val benefits: List<String>,
)

@Serializable
data class SubscribeRequestDto(
    @SerialName("plan_id") val planId: String,
    @SerialName("payment_method") val paymentMethod: String,
)

@Serializable
data class VioletaStatusDto(
    @SerialName("enabled") val enabled: Boolean,
    @SerialName("identity_verified") val identityVerified: Boolean,
    @SerialName("phone_verified") val phoneVerified: Boolean,
    @SerialName("identity_verified_at") val identityVerifiedAt: String? = null,
    @SerialName("phone_verified_at") val phoneVerifiedAt: String? = null,
    @SerialName("safe_route_enabled") val safeRouteEnabled: Boolean = false,
    @SerialName("emergency_button_enabled") val emergencyButtonEnabled: Boolean = false,
)

@Serializable
data class ModuleDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("lessons") val lessons: List<LessonDto>,
)

@Serializable
data class LessonDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("cards") val cards: List<LessonCardDto>,
)

@Serializable
data class LessonCardDto(
    @SerialName("heading") val heading: String,
    @SerialName("body") val body: String,
    @SerialName("tip") val tip: String? = null,
)

@Serializable
data class ApiErrorDto(
    @SerialName("code") val code: String,
    @SerialName("message") val message: String,
)
