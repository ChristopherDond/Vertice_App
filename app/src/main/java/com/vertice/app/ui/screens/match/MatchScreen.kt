package com.vertice.app.ui.screens.match

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vertice.app.R
import com.vertice.app.core.di.ServiceLocator
import com.vertice.app.domain.model.Freelancer
import com.vertice.app.domain.model.MatchFilters
import com.vertice.app.domain.model.ServiceCategory
import com.vertice.app.domain.validation.CurrencyFormatter
import com.vertice.app.ui.components.FadeSlideIn
import com.vertice.app.ui.components.PrimaryButton
import com.vertice.app.ui.components.SearchBar
import com.vertice.app.ui.components.SelectableChip
import com.vertice.app.ui.theme.LocalVerticeColors
import com.vertice.app.core.result.onOk

@Composable
private fun FreelancerAvatar(f: Freelancer) {
    val drawable = when (f.avatarKey) {
        "ana" -> R.drawable.ana_profile
        "b" -> R.drawable.freelancer_b
        else -> R.drawable.freelancer_m
    }
    Image(
        painterResource(drawable),
        contentDescription = f.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(58.dp).clip(CircleShape),
    )
}

@Composable
fun MatchScreen(onBack: () -> Unit, onOpenFilter: () -> Unit, onContact: (Freelancer) -> Unit) {
    val colors = LocalVerticeColors.current
    var query by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(ServiceCategory.ALL) }
    var results by remember { mutableStateOf<List<Freelancer>>(emptyList()) }

    LaunchedEffect(query, category) {
        ServiceLocator.matchRepository
            .search(MatchFilters(query = query, category = category))
            .onOk { results = it }
    }

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(Modifier.padding(horizontal = 20.dp).padding(top = 50.dp, bottom = 14.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Match", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Text("${results.size} profissionais encontrados", style = MaterialTheme.typography.bodySmall, color = colors.textSecondary)
                }
                Box(
                    Modifier.size(42.dp).clip(RoundedCornerShape(13.dp)).background(colors.gradientStart.copy(alpha = 0.13f)).clickable { onOpenFilter() },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Icons.Filled.FilterList, "Filtros", tint = colors.gradientStart, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(Modifier.height(16.dp))
            SearchBar(query, { query = it }, "Buscar por nome, ofício ou bairro")
            Spacer(Modifier.height(14.dp))
            androidx.compose.foundation.lazy.LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(ServiceCategory.entries.toList()) { cat ->
                    SelectableChip(cat.label, cat == category) { category = cat }
                }
            }
        }

        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(start = 20.dp, end = 20.dp, bottom = 26.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            itemsIndexed(results) { i, f ->
                FadeSlideIn(i * 55) {
                    Surface(
                        Modifier.fillMaxWidth().clickable { onContact(f) },
                        shape = RoundedCornerShape(18.dp),
                        color = MaterialTheme.colorScheme.surface,
                        tonalElevation = 2.dp,
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                FreelancerAvatar(f)
                                Spacer(Modifier.width(14.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(f.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                    Text(f.trade, style = MaterialTheme.typography.bodySmall, color = colors.textSecondary)
                                    Spacer(Modifier.height(4.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Filled.Star, null, tint = colors.warning, modifier = Modifier.size(13.dp))
                                        Spacer(Modifier.width(4.dp))
                                        Text(
                                            "${f.ratingLabel} · ${f.reviewCount} avaliações · ${f.distanceKm} km",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = colors.textSecondary,
                                        )
                                    }
                                }
                                Text(
                                    "${CurrencyFormatter.format(f.hourlyRate)}/h",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = colors.gradientStart,
                                )
                            }
                            Spacer(Modifier.height(12.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                f.skills.take(3).forEach { s ->
                                    Surface(shape = RoundedCornerShape(8.dp), color = colors.gradientStart.copy(alpha = 0.1f)) {
                                        Text(
                                            s,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = colors.gradientStart,
                                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp),
                                        )
                                    }
                                }
                            }
                            Spacer(Modifier.height(12.dp))
                            PrimaryButton("Contatar", Modifier.fillMaxWidth()) { onContact(f) }
                        }
                    }
                }
            }
        }
    }
}
