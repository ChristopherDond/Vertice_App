package com.vertice.app.data.remote.fake

import com.vertice.app.data.remote.api.AuthApi
import com.vertice.app.data.remote.api.FinanceApi
import com.vertice.app.data.remote.api.LearningApi
import com.vertice.app.data.remote.api.MatchApi
import com.vertice.app.data.remote.api.ProfileApi
import com.vertice.app.data.remote.api.SubscriptionApi
import com.vertice.app.data.remote.dto.AutoRenewRequestDto
import com.vertice.app.data.remote.dto.BalanceDto
import com.vertice.app.data.remote.dto.CardDto
import com.vertice.app.data.remote.dto.CardRequestDto
import com.vertice.app.data.remote.dto.ChangeEmailRequestDto
import com.vertice.app.data.remote.dto.ChangePasswordRequestDto
import com.vertice.app.data.remote.dto.ChangePhoneRequestDto
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
import kotlinx.coroutines.delay
import java.util.UUID

class FakeApiException(val code: String, override val message: String) : Exception(message)

private suspend fun tick(ms: Long = 350) = delay(ms)

class FakeAuthApi : AuthApi {
    private var pendingCode: String? = null
    private var pendingContact: String? = null
    private var password: String = FakeStore.DEMO_PASSWORD

    override suspend fun login(body: LoginRequestDto): SessionDto {
        tick()
        val credential = body.credential.trim().lowercase()
        val matchesEmail = credential == FakeStore.DEMO_EMAIL.lowercase() ||
            credential == FakeStore.currentUser.email.lowercase()
        val cpfDigits = credential.filter { it.isDigit() }
        val matchesCpf = cpfDigits.length == 11 && cpfDigits == FakeStore.DEMO_CPF
        if (!matchesEmail && !matchesCpf) throw FakeApiException("NOT_FOUND", "Conta não encontrada. Verifique o CPF ou e-mail.")
        if (body.password != password) throw FakeApiException("UNAUTHORIZED", "Senha incorreta. Tente novamente.")
        return session()
    }

    override suspend fun loginWithProvider(body: ProviderLoginRequestDto): SessionDto {
        tick(600)
        if (body.idToken.isBlank()) throw FakeApiException("VALIDATION", "Não foi possível concluir com ${body.provider}.")
        return session()
    }

    override suspend fun register(body: RegisterRequestDto): SessionDto {
        tick(800)
        FakeStore.currentUser = FakeStore.currentUser.copy(
            name = body.fullName,
            email = body.email,
            phone = body.phone,
            cpfMasked = "***.***.***-" + body.cpf.filter { it.isDigit() }.takeLast(2),
            bio = "",
            skills = emptyList(),
            profileCompletion = 35,
        )
        password = body.password
        return session()
    }

    override suspend fun requestRecoveryCode(body: RecoveryCodeRequestDto) {
        tick(600)
        if (body.contact.isBlank()) throw FakeApiException("VALIDATION", "Informe seu ${body.channel.lowercase()}.")
        pendingCode = "123456"
        pendingContact = body.contact
    }

    override suspend fun resetPassword(body: ResetPasswordRequestDto) {
        tick(600)
        if (body.code != pendingCode) throw FakeApiException("VALIDATION", "Código inválido ou expirado.")
        password = body.newPassword
        pendingCode = null
        pendingContact = null
    }

    override suspend fun changePassword(body: ChangePasswordRequestDto) {
        tick()
        if (body.currentPassword != password) throw FakeApiException("UNAUTHORIZED", "Senha atual incorreta.")
        password = body.newPassword
    }

    override suspend fun changeEmail(body: ChangeEmailRequestDto): UserDto {
        tick()
        if (!body.email.contains('@')) throw FakeApiException("VALIDATION", "Formato inválido. Use nome@exemplo.com")
        FakeStore.currentUser = FakeStore.currentUser.copy(email = body.email)
        return FakeStore.currentUser
    }

    override suspend fun changePhone(body: ChangePhoneRequestDto): UserDto {
        tick()
        if (body.phone.filter { it.isDigit() }.length < 10) throw FakeApiException("VALIDATION", "Telefone inválido.")
        FakeStore.currentUser = FakeStore.currentUser.copy(phone = body.phone)
        return FakeStore.currentUser
    }

    override suspend fun logout() {
        tick(150)
    }

    private fun session() = SessionDto(
        token = "fake." + UUID.randomUUID().toString(),
        userId = FakeStore.currentUser.id,
        displayName = FakeStore.currentUser.name,
        isPro = FakeStore.currentUser.isPro,
        violetaVerified = FakeStore.currentUser.violetaVerified,
    )
}

class FakeProfileApi : ProfileApi {
    override suspend fun me(): UserDto {
        tick(250)
        return FakeStore.currentUser
    }

    override suspend fun update(body: UpdateProfileRequestDto): UserDto {
        tick(600)
        val current = FakeStore.currentUser
        FakeStore.currentUser = current.copy(
            name = body.fullName,
            bio = body.bio,
            skills = body.skills,
            email = body.email,
            phone = body.phone,
            address = body.address,
            profileCompletion = completion(body),
        )
        return FakeStore.currentUser
    }

    override suspend fun trilhaProgress(): List<String> {
        tick(200)
        return FakeStore.trilhaDone.toList()
    }

    override suspend fun setLessonDone(lessonId: String, done: Boolean) {
        tick(120)
        if (done) FakeStore.trilhaDone.add(lessonId) else FakeStore.trilhaDone.remove(lessonId)
    }

    override suspend fun violeta(): VioletaStatusDto {
        tick(200)
        return FakeStore.violeta
    }

    override suspend fun setVioleta(enabled: Boolean): VioletaStatusDto {
        tick(300)
        FakeStore.violeta = FakeStore.violeta.copy(enabled = enabled)
        return FakeStore.violeta
    }

    override suspend fun verifyVioleta(step: String, reference: String): VioletaStatusDto {
        tick(900)
        if (reference.isBlank()) throw FakeApiException("VALIDATION", "Anexe o arquivo para continuar.")
        FakeStore.violeta = when (step) {
            "DOCUMENTS" -> FakeStore.violeta.copy(identityVerified = true, identityVerifiedAt = "hoje")
            "SELFIE" -> FakeStore.violeta.copy(phoneVerified = true, phoneVerifiedAt = "hoje", enabled = true)
            else -> FakeStore.violeta
        }
        return FakeStore.violeta
    }

    private fun completion(body: UpdateProfileRequestDto): Int {
        var score = 20
        if (body.fullName.isNotBlank()) score += 15
        if (body.bio.isNotBlank()) score += 15
        if (body.skills.isNotEmpty()) score += 15
        if (body.phone.isNotBlank()) score += 10
        if (body.address != null) score += 15
        if (body.email.isNotBlank()) score += 10
        return score.coerceAtMost(100)
    }
}

class FakeMatchApi : MatchApi {
    override suspend fun search(
        query: String,
        category: String,
        violetaOnly: Boolean,
        maxKm: Float,
        proOnly: Boolean,
    ): List<FreelancerDto> {
        tick(400)
        return FakeStore.freelancers
            .filter { f ->
                category == "ALL" || f.category == category
            }
            .filter { f ->
                !violetaOnly || f.isVioleta
            }
            .filter { f ->
                !proOnly || f.isPro
            }
            .filter { f ->
                f.distanceKm <= maxKm
            }
            .filter { f ->
                query.isBlank() ||
                    f.name.contains(query, ignoreCase = true) ||
                    f.role.contains(query, ignoreCase = true) ||
                    f.area.contains(query, ignoreCase = true) ||
                    f.bio.contains(query, ignoreCase = true)
            }
            .sortedByDescending { it.matchPercent }
    }

    override suspend fun byId(id: String): FreelancerDto {
        tick(200)
        return FakeStore.freelancers.find { it.id == id }
            ?: throw FakeApiException("NOT_FOUND", "Prestador não encontrado.")
    }

    override suspend fun requestService(body: ServiceRequestDto) {
        tick(700)
        if (body.serviceType.isBlank()) throw FakeApiException("VALIDATION", "Selecione o tipo de serviço.")
        if (body.date.isBlank()) throw FakeApiException("VALIDATION", "Informe a data.")
        if (body.budget <= 0) throw FakeApiException("VALIDATION", "Informe o orçamento.")
        if (body.description.length < 20) throw FakeApiException("VALIDATION", "Descreva o serviço com pelo menos 20 caracteres.")
    }

    override suspend fun publishOffer(body: ServiceOfferDto) {
        tick(700)
        if (body.title.isBlank()) throw FakeApiException("VALIDATION", "Informe o título do serviço.")
        if (body.description.length < 20) throw FakeApiException("VALIDATION", "Descreva o serviço com pelo menos 20 caracteres.")
        if (body.price <= 0) throw FakeApiException("VALIDATION", "Informe o preço.")
    }
}

class FakeFinanceApi : FinanceApi {
    override suspend fun balance(): BalanceDto {
        tick(300)
        return FakeStore.balance
    }

    override suspend fun transactions(): List<TransactionDto> {
        tick(350)
        return FakeStore.transactions
    }

    override suspend fun cards(): List<CardDto> {
        tick(250)
        return FakeStore.cards
    }

    override suspend fun addCard(body: CardRequestDto): CardDto {
        tick(700)
        val digits = body.number.filter { it.isDigit() }
        if (digits.length < 13) throw FakeApiException("VALIDATION", "Número do cartão inválido.")
        val brand = when {
            digits.startsWith("4") -> "VISA"
            digits.startsWith("5") -> "MASTERCARD"
            digits.startsWith("3") -> "AMEX"
            else -> "OTHER"
        }
        val card = CardDto(
            id = "crd_" + UUID.randomUUID().toString().take(6),
            brand = brand,
            last4 = digits.takeLast(4),
            holderName = body.holderName.uppercase(),
            expiry = body.expiry,
            label = if (FakeStore.cards.isEmpty()) "Cartão principal" else "Reserva",
            isPrimary = FakeStore.cards.isEmpty(),
        )
        FakeStore.cards.add(card)
        return card
    }

    override suspend fun removeCard(id: String) {
        tick(300)
        val removed = FakeStore.cards.removeAll { it.id == id }
        if (!removed) throw FakeApiException("NOT_FOUND", "Cartão não encontrado.")
    }

    override suspend fun subscription(): SubscriptionDto {
        tick(250)
        return FakeStore.subscription
    }

    override suspend fun setAutoRenew(body: AutoRenewRequestDto): SubscriptionDto {
        tick(400)
        FakeStore.subscription = FakeStore.subscription.copy(
            autoRenew = body.enabled,
            autoRenewMethod = if (body.enabled) body.method else "NONE",
        )
        return FakeStore.subscription
    }

    override suspend fun cancelSubscription() {
        tick(600)
        FakeStore.subscription = FakeStore.subscription.copy(
            active = false,
            autoRenew = false,
            autoRenewMethod = "NONE",
            status = "CANCELED",
        )
        FakeStore.currentUser = FakeStore.currentUser.copy(isPro = false)
    }
}

class FakeSubscriptionApi : SubscriptionApi {
    override suspend fun plans(): List<PlanDto> {
        tick(250)
        return FakeStore.plans
    }

    override suspend fun subscribe(body: SubscribeRequestDto): SubscriptionDto {
        tick(1000)
        if (body.paymentMethod.isBlank()) throw FakeApiException("VALIDATION", "Escolha a forma de pagamento.")
        FakeStore.subscription = FakeStore.subscription.copy(
            active = true,
            status = "UP_TO_DATE",
            autoRenew = true,
            autoRenewMethod = body.paymentMethod,
            nextChargeDate = "08/01/2026",
        )
        FakeStore.currentUser = FakeStore.currentUser.copy(isPro = true)
        return FakeStore.subscription
    }
}

class FakeLearningApi : LearningApi {
    override suspend fun modules(): List<ModuleDto> {
        tick(300)
        return FakeStore.modules
    }
}
