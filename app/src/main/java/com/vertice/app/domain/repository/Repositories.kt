package com.vertice.app.domain.repository

import com.vertice.app.core.result.VerticeResult
import com.vertice.app.core.session.Session
import com.vertice.app.domain.model.AuthProvider
import com.vertice.app.domain.model.Balance
import com.vertice.app.domain.model.Card
import com.vertice.app.domain.model.CardForm
import com.vertice.app.domain.model.Freelancer
import com.vertice.app.domain.model.MatchFilters
import com.vertice.app.domain.model.Plan
import com.vertice.app.domain.model.PaymentMethod
import com.vertice.app.domain.model.ProfileForm
import com.vertice.app.domain.model.RecoveryChannel
import com.vertice.app.domain.model.RegistrationForm
import com.vertice.app.domain.model.ServiceOffer
import com.vertice.app.domain.model.ServiceRequest
import com.vertice.app.domain.model.Subscription
import com.vertice.app.domain.model.TModule
import com.vertice.app.domain.model.Transaction
import com.vertice.app.domain.model.User
import com.vertice.app.domain.model.VioletaStatus
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val session: Flow<Session?>
    suspend fun login(credential: String, password: String): VerticeResult<Session>
    suspend fun loginWithProvider(provider: AuthProvider): VerticeResult<Session>
    suspend fun register(form: RegistrationForm): VerticeResult<Session>
    suspend fun requestRecoveryCode(channel: RecoveryChannel, contact: String): VerticeResult<Unit>
    suspend fun resetPassword(contact: String, code: String, newPassword: String): VerticeResult<Unit>
    suspend fun changePassword(current: String, new: String): VerticeResult<Unit>
    suspend fun changeEmail(newEmail: String): VerticeResult<User>
    suspend fun changePhone(newPhone: String): VerticeResult<User>
    suspend fun logout()
}

interface ProfileRepository {
    suspend fun me(): VerticeResult<User>
    suspend fun update(form: ProfileForm): VerticeResult<User>
    fun trilhaProgress(): Flow<Set<String>>
    suspend fun refreshTrilha(): VerticeResult<Set<String>>
    suspend fun markLessonDone(lessonId: String, done: Boolean): VerticeResult<Unit>
    suspend fun violeta(): VerticeResult<VioletaStatus>
    suspend fun setVioleta(enabled: Boolean): VerticeResult<VioletaStatus>
    suspend fun verifyVioleta(step: String, reference: String): VerticeResult<VioletaStatus>
}

interface MatchRepository {
    suspend fun search(filters: MatchFilters): VerticeResult<List<Freelancer>>
    suspend fun byId(id: String): VerticeResult<Freelancer>
    suspend fun requestService(request: ServiceRequest): VerticeResult<Unit>
    suspend fun publishOffer(offer: ServiceOffer): VerticeResult<Unit>
}

interface FinanceRepository {
    suspend fun balance(): VerticeResult<Balance>
    suspend fun transactions(): VerticeResult<List<Transaction>>
    suspend fun cards(): VerticeResult<List<Card>>
    suspend fun addCard(form: CardForm): VerticeResult<Card>
    suspend fun removeCard(id: String): VerticeResult<Unit>
    suspend fun subscription(): VerticeResult<Subscription>
    suspend fun setAutoRenew(method: PaymentMethod, enabled: Boolean): VerticeResult<Subscription>
    suspend fun cancelSubscription(): VerticeResult<Unit>
}

interface LearningRepository {
    suspend fun modules(): VerticeResult<List<TModule>>
    fun progress(): Flow<Set<String>>
    suspend fun markLesson(lessonId: String, done: Boolean): VerticeResult<Unit>
}

interface SubscriptionRepository {
    suspend fun plans(): VerticeResult<List<Plan>>
    suspend fun subscribe(planId: String, method: PaymentMethod): VerticeResult<Subscription>
}
