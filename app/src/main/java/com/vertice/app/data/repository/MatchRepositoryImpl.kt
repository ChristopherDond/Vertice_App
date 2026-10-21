package com.vertice.app.data.repository

import com.vertice.app.core.result.VerticeResult
import com.vertice.app.core.result.runResult
import com.vertice.app.data.mapper.toDomain
import com.vertice.app.data.remote.api.MatchApi
import com.vertice.app.data.remote.dto.ServiceOfferDto
import com.vertice.app.data.remote.dto.ServiceRequestDto
import com.vertice.app.domain.model.Freelancer
import com.vertice.app.domain.model.MatchFilters
import com.vertice.app.domain.model.ServiceOffer
import com.vertice.app.domain.model.ServiceRequest
import com.vertice.app.domain.repository.MatchRepository

class MatchRepositoryImpl(private val api: MatchApi) : MatchRepository {

    private var cache: List<Freelancer> = emptyList()

    override suspend fun search(filters: MatchFilters): VerticeResult<List<Freelancer>> = runResult {
        val result = api.search(
            query = filters.query.trim(),
            category = filters.category.name,
            violetaOnly = filters.violetaOnly,
            maxKm = filters.maxKm,
            proOnly = filters.proOnly,
        ).map { it.toDomain() }
        cache = result
        result
    }

    override suspend fun byId(id: String): VerticeResult<Freelancer> = runResult {
        cache.find { it.id == id } ?: api.byId(id).toDomain()
    }

    override suspend fun requestService(request: ServiceRequest): VerticeResult<Unit> = runResult {
        api.requestService(
            ServiceRequestDto(
                freelancerId = request.freelancerId,
                serviceType = request.serviceType,
                date = request.date,
                time = request.time,
                budget = request.budget,
                location = request.location,
                description = request.description,
                photoUrls = request.photoUrls,
                urgency = request.urgency.name,
            ),
        )
    }

    override suspend fun publishOffer(offer: ServiceOffer): VerticeResult<Unit> = runResult {
        api.publishOffer(
            ServiceOfferDto(
                category = offer.category,
                title = offer.title,
                description = offer.description,
                price = offer.price,
                city = offer.city,
                availability = offer.availability,
            ),
        )
    }
}
