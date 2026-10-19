package com.vertice.app.data.repository

import com.vertice.app.core.result.VerticeResult
import com.vertice.app.core.result.runResult
import com.vertice.app.core.session.Session
import com.vertice.app.core.session.SessionManager
import com.vertice.app.data.mapper.toDomain
import com.vertice.app.data.remote.api.AuthApi
import com.vertice.app.data.remote.dto.ChangeEmailRequestDto
import com.vertice.app.data.remote.dto.ChangePasswordRequestDto
import com.vertice.app.data.remote.dto.ChangePhoneRequestDto
import com.vertice.app.data.remote.dto.LoginRequestDto
import com.vertice.app.data.remote.dto.ProviderLoginRequestDto
import com.vertice.app.data.remote.dto.RecoveryCodeRequestDto
import com.vertice.app.data.remote.dto.RegisterRequestDto
import com.vertice.app.data.remote.dto.ResetPasswordRequestDto
import com.vertice.app.domain.model.AuthProvider
import com.vertice.app.domain.model.RecoveryChannel
import com.vertice.app.domain.model.RegistrationForm
import com.vertice.app.domain.model.User
import com.vertice.app.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sessionManager: SessionManager,
) : AuthRepository {

    override val session: Flow<Session?> = sessionManager.session

    override suspend fun login(credential: String, password: String): VerticeResult<Session> =
        runResult {
            val dto = api.login(LoginRequestDto(credential = credential.trim(), password = password))
            dto.toSession().also { sessionManager.save(it) }
        }

    override suspend fun loginWithProvider(provider: AuthProvider): VerticeResult<Session> =
        runResult {
            val dto = api.loginWithProvider(
                ProviderLoginRequestDto(provider = provider.apiValue, idToken = "fake-id-token"),
            )
            dto.toSession().also { sessionManager.save(it) }
        }

    override suspend fun register(form: RegistrationForm): VerticeResult<Session> = runResult {
        require(form.canSubmit) { "Preencha todos os campos obrigatórios." }
        val dto = api.register(
            RegisterRequestDto(
                fullName = form.fullName.trim(),
                cpf = form.cpf.filter { it.isDigit() },
                birthDate = form.birthDate,
                password = form.password,
                email = form.email.trim(),
                phone = form.phone,
            ),
        )
        dto.toSession().also { sessionManager.save(it) }
    }

    override suspend fun requestRecoveryCode(
        channel: RecoveryChannel,
        contact: String,
    ): VerticeResult<Unit> = runResult {
        api.requestRecoveryCode(RecoveryCodeRequestDto(channel = channel.apiValue, contact = contact.trim()))
    }

    override suspend fun resetPassword(
        contact: String,
        code: String,
        newPassword: String,
    ): VerticeResult<Unit> = runResult {
        api.resetPassword(
            ResetPasswordRequestDto(
                contact = contact.trim(),
                code = code.trim(),
                newPassword = newPassword,
            ),
        )
    }

    override suspend fun changePassword(current: String, new: String): VerticeResult<Unit> = runResult {
        api.changePassword(ChangePasswordRequestDto(currentPassword = current, newPassword = new))
    }

    override suspend fun changeEmail(newEmail: String): VerticeResult<User> = runResult {
        api.changeEmail(ChangeEmailRequestDto(newEmail.trim())).toDomain()
    }

    override suspend fun changePhone(newPhone: String): VerticeResult<User> = runResult {
        api.changePhone(ChangePhoneRequestDto(newPhone)).toDomain()
    }

    override suspend fun logout() {
        runCatching { api.logout() }
        sessionManager.clear()
    }

    suspend fun currentToken(): String? = sessionManager.session.first()?.token

    private fun com.vertice.app.data.remote.dto.SessionDto.toSession() = Session(
        token = token,
        userId = userId,
        displayName = displayName,
        isPro = isPro,
        violetaVerified = violetaVerified,
    )
}
