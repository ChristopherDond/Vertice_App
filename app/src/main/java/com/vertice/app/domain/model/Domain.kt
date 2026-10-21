package com.vertice.app.domain.model

import com.vertice.app.domain.validation.CpfValidator
import com.vertice.app.domain.validation.PasswordValidator

enum class ServiceCategory(val label: String, val emoji: String) {
    ALL("Todos", "\u2728"),
    CONSTRUCTION("Construção", "\uD83D\uDD28"),
    COMMERCE("Comércio", "\uD83D\uDECD"),
    SERVICES("Serviços", "\uD83D\uDD27"),
    BEAUTY("Beleza", "\uD83D\uDC85"),
    FOOD("Alimentação", "\uD83C\uDF7D"),
    CONSULTING("Consultoria", "\uD83D\uDCC8"),
    ;

    companion object {
        fun fromName(name: String): ServiceCategory =
            entries.find { it.name == name } ?: ALL
    }
}

enum class Gender { FEMALE, MALE, OTHER }

enum class CardBrand(val label: String) {
    VISA("Visa"), MASTERCARD("Mastercard"), AMEX("Amex"), ELO("Elo"), OTHER("Cartão")
}

enum class AutoRenewMethod(val label: String) {
    PIX("PIX"), CREDIT_CARD("Cartão de crédito"), BOLETO("Boleto"), NONE("Desativada")
}

enum class PaymentMethod(val apiValue: String, val label: String) {
    PIX("PIX", "PIX"),
    CREDIT_CARD("CREDIT_CARD", "Cartão de crédito"),
}

enum class SubscriptionStatus { UP_TO_DATE, PENDING, OVERDUE, CANCELED }

enum class TransactionStatus(val label: String) {
    WITHDRAWABLE("Sacável hoje"),
    COMPLETED("Concluído"),
    WITHDRAWN("Sacado"),
    PENDING("Pendente"),
}

enum class RecoveryChannel(val apiValue: String) {
    EMAIL("EMAIL"), PHONE("PHONE")
}

enum class AuthProvider(val apiValue: String) {
    GOOGLE("GOOGLE"), APPLE("APPLE")
}

enum class ServiceUrgency(val label: String) {
    NORMAL("Normal"), URGENT("Urgente"), SCHEDULED("Agendado")
}

data class Address(
    val cep: String,
    val street: String,
    val number: String,
    val complement: String = "",
    val district: String,
    val city: String,
    val state: String,
)

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val cpfMasked: String,
    val bio: String = "",
    val skills: List<String> = emptyList(),
    val avatarUrl: String? = null,
    val address: Address? = null,
    val isPro: Boolean = false,
    val violetaVerified: Boolean = false,
    val profileCompletion: Int = 0,
    val rating: Double = 0.0,
    val completedServices: Int = 0,
) {
    val firstName: String get() = name.trim().split(" ").firstOrNull().orEmpty()
}

data class Freelancer(
    val id: String,
    val name: String,
    val role: String,
    val area: String,
    val category: ServiceCategory,
    val matchPercent: Int,
    val rating: Double,
    val servicesCount: Int,
    val availability: String,
    val bio: String,
    val gender: Gender,
    val avatarUrl: String? = null,
    val hoursWorked: Int = 0,
    val jobsDone: Int = 0,
    val isVioleta: Boolean = false,
    val isPro: Boolean = false,
    val skills: List<String> = emptyList(),
    val hourlyRate: Double = 0.0,
    val distanceKm: Int = 1,
) {
    val firstName: String get() = name.trim().split(" ").firstOrNull().orEmpty()
    val ratingLabel: String get() = String.format("%.1f", rating).replace('.', ',')
    val trade: String get() = role
    val reviewCount: Int get() = servicesCount
    val avatarKey: String get() = when {
        gender == Gender.FEMALE -> "b"
        else -> "m"
    }
}

data class MatchFilters(
    val query: String = "",
    val category: ServiceCategory = ServiceCategory.ALL,
    val violetaOnly: Boolean = false,
    val maxKm: Float = 50f,
    val proOnly: Boolean = false,
)

data class ServiceRequest(
    val freelancerId: String,
    val serviceType: String,
    val date: String,
    val time: String,
    val budget: Double,
    val location: String,
    val description: String,
    val photoUrls: List<String> = emptyList(),
    val urgency: ServiceUrgency = ServiceUrgency.NORMAL,
)

data class ServiceOffer(
    val category: String,
    val title: String,
    val description: String,
    val price: Double,
    val city: String,
    val availability: String,
)

data class Balance(
    val available: Double,
    val pending: Double,
    val currency: String = "BRL",
) {
    val availableCents: Long get() = Math.round(available * 100)
    val pendingCents: Long get() = Math.round(pending * 100)
}

data class Transaction(
    val id: String,
    val clientName: String,
    val service: String,
    val location: String,
    val amount: Double,
    val date: String,
    val status: TransactionStatus,
    val withdrawableAt: String? = null,
) {
    val title: String get() = service
    val dateLabel: String get() = date
    val amountCents: Long get() = Math.round(amount * 100)
    val error: Boolean get() = status == TransactionStatus.PENDING
    fun formattedAmount(): String = com.vertice.app.domain.validation.CurrencyFormatter.formatBare(amount)
}

data class Card(
    val id: String,
    val brand: CardBrand,
    val last4: String,
    val holderName: String,
    val expiry: String,
    val label: String,
    val isPrimary: Boolean = false,
) {
    val maskedNumber: String get() = "\u2022\u2022\u2022\u2022 $last4"
}

data class CardForm(
    val number: String = "",
    val holderName: String = "",
    val expiry: String = "",
    val cvv: String = "",
    val document: String = "",
    val bank: String = "",
) {
    val isNumberValid: Boolean get() = number.filter { it.isDigit() }.length in 13..19
    val isExpiryValid: Boolean get() = Regex("^(0[1-9]|1[0-2])/\\d{2}$").matches(expiry)
    val isCvvValid: Boolean get() = cvv.filter { it.isDigit() }.length in 3..4
    val isHolderValid: Boolean get() = holderName.trim().length >= 5
    val isDocumentValid: Boolean get() = document.filter { it.isDigit() }.length == 11
    val isComplete: Boolean
        get() = isNumberValid && isExpiryValid && isCvvValid && isHolderValid && isDocumentValid
}

data class Subscription(
    val active: Boolean,
    val planName: String,
    val monthlyPrice: Double,
    val nextChargeDate: String,
    val autoRenew: Boolean,
    val autoRenewMethod: AutoRenewMethod,
    val status: SubscriptionStatus,
) {
    val priceLabel: String get() = "R\$ " + String.format("%.2f", monthlyPrice).replace('.', ',')
}

data class Plan(
    val id: String,
    val name: String,
    val monthlyPrice: Double,
    val benefits: List<String>,
)

data class VioletaStatus(
    val enabled: Boolean,
    val identityVerified: Boolean,
    val phoneVerified: Boolean,
    val identityVerifiedAt: String? = null,
    val phoneVerifiedAt: String? = null,
    val safeRouteEnabled: Boolean = false,
    val emergencyButtonEnabled: Boolean = false,
) {
    val fullyVerified: Boolean get() = identityVerified && phoneVerified
    val stepLabel: String get() = when {
        fullyVerified && enabled -> "Proteção ativa"
        fullyVerified -> "Pronta para ativar"
        identityVerified -> "Falta verificar telefone"
        else -> "Falta enviar documentos"
    }
}

data class ProfileForm(
    val fullName: String,
    val bio: String,
    val skills: List<String>,
    val email: String,
    val phone: String,
    val address: Address?,
    val notificationsEnabled: Boolean,
) {
    val isNameValid: Boolean get() = fullName.trim().length >= 3
    val isEmailValid: Boolean get() = REGEX_EMAIL.matches(email.trim())
    val isPhoneValid: Boolean get() = phone.filter { it.isDigit() }.length in 10..11
    val isValid: Boolean get() = isNameValid && isEmailValid && isPhoneValid

    companion object {
        private val REGEX_EMAIL = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    }
}

data class RegistrationForm(
    val fullName: String = "",
    val cpf: String = "",
    val birthDate: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val email: String = "",
    val phone: String = "",
    val acceptedTerms: Boolean = false,
    val address: Address? = null,
) {
    val isNameValid: Boolean get() = fullName.trim().length >= 3
    val isCpfValid: Boolean get() = CpfValidator.isValid(cpf)
    val isBirthDateValid: Boolean get() = Regex("^\\d{2}/\\d{2}/\\d{4}$").matches(birthDate)
    val isPasswordValid: Boolean get() = PasswordValidator.isValid(password)
    val isConfirmValid: Boolean get() = password.isNotEmpty() && password == confirmPassword
    val isEmailValid: Boolean get() = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").matches(email.trim())
    val isPhoneValid: Boolean get() = phone.filter { it.isDigit() }.length in 10..11
    val isSectionOneComplete: Boolean get() = isNameValid && isCpfValid && isBirthDateValid && isPasswordValid && isConfirmValid
    val isSectionTwoComplete: Boolean get() = isEmailValid && isPhoneValid && acceptedTerms
    val canSubmit: Boolean get() = isSectionOneComplete && isSectionTwoComplete
}
