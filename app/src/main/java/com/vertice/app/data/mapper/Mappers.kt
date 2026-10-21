package com.vertice.app.data.mapper

import com.vertice.app.data.remote.dto.AddressDto
import com.vertice.app.data.remote.dto.BalanceDto
import com.vertice.app.data.remote.dto.CardDto
import com.vertice.app.data.remote.dto.CardRequestDto
import com.vertice.app.data.remote.dto.FreelancerDto
import com.vertice.app.data.remote.dto.LessonCardDto
import com.vertice.app.data.remote.dto.LessonDto
import com.vertice.app.data.remote.dto.ModuleDto
import com.vertice.app.data.remote.dto.PlanDto
import com.vertice.app.data.remote.dto.TransactionDto
import com.vertice.app.data.remote.dto.UserDto
import com.vertice.app.data.remote.dto.VioletaStatusDto
import com.vertice.app.domain.model.Address
import com.vertice.app.domain.model.AutoRenewMethod
import com.vertice.app.domain.model.Balance
import com.vertice.app.domain.model.Card
import com.vertice.app.domain.model.CardBrand
import com.vertice.app.domain.model.CardForm
import com.vertice.app.domain.model.Freelancer
import com.vertice.app.domain.model.Gender
import com.vertice.app.domain.model.Lesson
import com.vertice.app.domain.model.LessonCard
import com.vertice.app.domain.model.Plan
import com.vertice.app.domain.model.ServiceCategory
import com.vertice.app.domain.model.Subscription
import com.vertice.app.domain.model.SubscriptionStatus
import com.vertice.app.domain.model.TModule
import com.vertice.app.domain.model.Transaction
import com.vertice.app.domain.model.TransactionStatus
import com.vertice.app.domain.model.User
import com.vertice.app.domain.model.VioletaStatus

fun AddressDto.toDomain() = Address(
    cep = cep, street = street, number = number, complement = complement,
    district = district, city = city, state = state,
)

fun Address.toDto() = AddressDto(
    cep = cep, street = street, number = number, complement = complement,
    district = district, city = city, state = state,
)

fun UserDto.toDomain() = User(
    id = id, name = name, email = email, phone = phone, cpfMasked = cpfMasked,
    bio = bio, skills = skills, avatarUrl = avatarUrl, address = address?.toDomain(),
    isPro = isPro, violetaVerified = violetaVerified,
    profileCompletion = profileCompletion, rating = rating, completedServices = completedServices,
)

fun FreelancerDto.toDomain() = Freelancer(
    id = id, name = name, role = role, area = area,
    category = ServiceCategory.entries.find { it.name == category } ?: ServiceCategory.ALL,
    matchPercent = matchPercent, rating = rating, servicesCount = servicesCount,
    availability = availability, bio = bio,
    gender = when (gender) {
        "FEMALE" -> Gender.FEMALE
        "MALE" -> Gender.MALE
        else -> Gender.OTHER
    },
    avatarUrl = avatarUrl, hoursWorked = hoursWorked, jobsDone = jobsDone,
    isVioleta = isVioleta, isPro = isPro,
    skills = skills, hourlyRate = hourlyRate, distanceKm = distanceKm,
)

fun BalanceDto.toDomain() = Balance(available = available, pending = pending, currency = currency)

fun TransactionDto.toDomain() = Transaction(
    id = id, clientName = clientName, service = service, location = location,
    amount = amount, date = date,
    status = TransactionStatus.entries.find { it.name == status } ?: TransactionStatus.COMPLETED,
    withdrawableAt = withdrawableAt,
)

fun CardDto.toDomain() = Card(
    id = id,
    brand = CardBrand.entries.find { it.name == brand } ?: CardBrand.OTHER,
    last4 = last4, holderName = holderName, expiry = expiry,
    label = label, isPrimary = isPrimary,
)

fun CardForm.toDto() = CardRequestDto(
    number = number.filter { it.isDigit() },
    holderName = holderName, expiry = expiry, cvv = cvv,
    document = document, bank = bank,
)

fun com.vertice.app.data.remote.dto.SubscriptionDto.toDomain() = Subscription(
    active = active, planName = planName, monthlyPrice = monthlyPrice,
    nextChargeDate = nextChargeDate, autoRenew = autoRenew,
    autoRenewMethod = AutoRenewMethod.entries.find { it.name == autoRenewMethod } ?: AutoRenewMethod.NONE,
    status = SubscriptionStatus.entries.find { it.name == status } ?: SubscriptionStatus.UP_TO_DATE,
)

fun PlanDto.toDomain() = Plan(id = id, name = name, monthlyPrice = monthlyPrice, benefits = benefits)

fun VioletaStatusDto.toDomain() = VioletaStatus(
    enabled = enabled, identityVerified = identityVerified, phoneVerified = phoneVerified,
    identityVerifiedAt = identityVerifiedAt, phoneVerifiedAt = phoneVerifiedAt,
    safeRouteEnabled = safeRouteEnabled, emergencyButtonEnabled = emergencyButtonEnabled,
)

fun LessonCardDto.toDomain() = LessonCard(heading = heading, body = body, tip = tip)

fun LessonDto.toDomain() = Lesson(id = id, title = title, emoji = "", cards = cards.map { it.toDomain() })

fun ModuleDto.toDomain() = TModule(id = id, title = title, lessons = lessons.map { it.toDomain() })
