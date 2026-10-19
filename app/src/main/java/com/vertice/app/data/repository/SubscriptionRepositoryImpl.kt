package com.vertice.app.data.repository

import com.vertice.app.core.result.VerticeResult
import com.vertice.app.core.result.runResult
import com.vertice.app.data.mapper.toDomain
import com.vertice.app.data.remote.api.SubscriptionApi
import com.vertice.app.data.remote.dto.SubscribeRequestDto
import com.vertice.app.domain.model.PaymentMethod
import com.vertice.app.domain.model.Plan
import com.vertice.app.domain.model.Subscription
import com.vertice.app.domain.repository.SubscriptionRepository

class SubscriptionRepositoryImpl(private val api: SubscriptionApi) : SubscriptionRepository {

    override suspend fun plans(): VerticeResult<List<Plan>> = runResult {
        api.plans().map { it.toDomain() }
    }

    override suspend fun subscribe(
        planId: String,
        method: PaymentMethod,
    ): VerticeResult<Subscription> = runResult {
        api.subscribe(SubscribeRequestDto(planId = planId, paymentMethod = method.name)).toDomain()
    }
}
