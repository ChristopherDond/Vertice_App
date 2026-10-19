package com.vertice.app.data.repository

import com.vertice.app.core.result.VerticeResult
import com.vertice.app.core.result.runResult
import com.vertice.app.data.mapper.toDomain
import com.vertice.app.data.mapper.toDto
import com.vertice.app.data.remote.api.FinanceApi
import com.vertice.app.data.remote.dto.AutoRenewRequestDto
import com.vertice.app.domain.model.Balance
import com.vertice.app.domain.model.Card
import com.vertice.app.domain.model.CardForm
import com.vertice.app.domain.model.PaymentMethod
import com.vertice.app.domain.model.Subscription
import com.vertice.app.domain.model.Transaction
import com.vertice.app.domain.repository.FinanceRepository

class FinanceRepositoryImpl(private val api: FinanceApi) : FinanceRepository {

    override suspend fun balance(): VerticeResult<Balance> = runResult {
        api.balance().toDomain()
    }

    override suspend fun transactions(): VerticeResult<List<Transaction>> = runResult {
        api.transactions().map { it.toDomain() }
    }

    override suspend fun cards(): VerticeResult<List<Card>> = runResult {
        api.cards().map { it.toDomain() }
    }

    override suspend fun addCard(form: CardForm): VerticeResult<Card> = runResult {
        require(form.isComplete) { "Preencha os dados do cartão corretamente." }
        api.addCard(form.toDto()).toDomain()
    }

    override suspend fun removeCard(id: String): VerticeResult<Unit> = runResult {
        api.removeCard(id)
    }

    override suspend fun subscription(): VerticeResult<Subscription> = runResult {
        api.subscription().toDomain()
    }

    override suspend fun setAutoRenew(
        method: PaymentMethod,
        enabled: Boolean,
    ): VerticeResult<Subscription> = runResult {
        api.setAutoRenew(AutoRenewRequestDto(method = method.name, enabled = enabled)).toDomain()
    }

    override suspend fun cancelSubscription(): VerticeResult<Unit> = runResult {
        api.cancelSubscription()
    }
}
