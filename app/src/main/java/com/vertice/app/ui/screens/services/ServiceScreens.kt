package com.vertice.app.ui.screens.services

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.R
import com.vertice.app.domain.model.Freelancer
import com.vertice.app.domain.model.ServiceCategory
import com.vertice.app.ui.components.FadeSlideIn
import com.vertice.app.ui.components.PrimaryButton
import com.vertice.app.ui.components.SecondaryButton
import com.vertice.app.ui.components.SelectableChip
import com.vertice.app.ui.components.TopBar
import com.vertice.app.ui.components.VerticeTextField
import com.vertice.app.ui.theme.LocalVerticeColors

@Composable
fun OfferServiceScreen(onBack: () -> Unit, onPublished: () -> Unit) {
    val colors = LocalVerticeColors.current
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(ServiceCategory.SERVICES) }

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TopBar(title = "Oferecer serviço", onBack = onBack)
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 22.dp).padding(bottom = 30.dp)) {
            FadeSlideIn(0) {
                Column {
                    Text("Anuncie seu serviço", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(6.dp))
                    Text("Publique e apareça para clientes da sua região.", style = MaterialTheme.typography.bodyMedium, color = colors.textSecondary)
                }
            }
            Spacer(Modifier.height(22.dp))
            FadeSlideIn(70) {
                Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                    Column(Modifier.padding(18.dp)) {
                        VerticeTextField(title, { title = it }, "Título do serviço", "ex: Instalação elétrica residencial")
                        Spacer(Modifier.height(14.dp))
                        Text("Categoria", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(Modifier.height(8.dp))
                        androidx.compose.foundation.lazy.LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(ServiceCategory.entries.drop(1).toList()) { cat ->
                                SelectableChip(cat.emoji + " " + cat.label, cat == category) { category = cat }
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                        VerticeTextField(description, { description = it }, "Descrição", "Descreva o que está incluso, prazo e diferenciais", singleLine = false, minLines = 4)
                        Spacer(Modifier.height(14.dp))
                        VerticeTextField(price, { price = it }, "Valor por hora", "R$ 0,00", keyboardType = KeyboardType.Number)
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            FadeSlideIn(130) {
                Surface(shape = RoundedCornerShape(16.dp), color = colors.gradientStart.copy(alpha = 0.08f)) {
                    Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("🔒", fontSize = 18.sp)
                        Spacer(Modifier.width(10.dp))
                        Text(
                            "O valor fica retido em escrow e é liberado após a confirmação do cliente.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            FadeSlideIn(180) {
                PrimaryButton("Publicar serviço", Modifier.fillMaxWidth()) { onPublished() }
            }
        }
    }
}

@Composable
fun ContactProviderScreen(freelancer: Freelancer?, onBack: () -> Unit, onSend: () -> Unit) {
    val colors = LocalVerticeColors.current
    var message by remember { mutableStateOf("") }
    val name = freelancer?.name ?: "Prestador"
    val trade = freelancer?.trade ?: "Serviços gerais"
    val avatar = when (freelancer?.avatarKey) {
        "b" -> R.drawable.freelancer_b
        "c" -> R.drawable.freelancer_c
        "ana" -> R.drawable.ana_profile
        else -> R.drawable.freelancer_m
    }

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TopBar(title = "Contatar prestador", onBack = onBack)
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 22.dp).padding(bottom = 30.dp)) {
            FadeSlideIn(0) {
                Surface(shape = RoundedCornerShape(22.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                    Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painterResource(avatar),
                            contentDescription = name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(90.dp).clip(CircleShape),
                        )
                        Spacer(Modifier.height(14.dp))
                        Text(name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Text(trade, style = MaterialTheme.typography.bodySmall, color = colors.textSecondary)
                        Spacer(Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Star, null, tint = colors.warning, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(5.dp))
                            Text(
                                "${freelancer?.ratingLabel ?: "4,9"} · ${freelancer?.reviewCount ?: 0} avaliações",
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.textSecondary,
                            )
                        }
                        Spacer(Modifier.height(18.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Box(Modifier.weight(1f)) { SecondaryButton("Ver perfil", Modifier.fillMaxWidth()) {} }
                            Box(Modifier.weight(1f)) { PrimaryButton("Conversar", Modifier.fillMaxWidth()) {} }
                        }
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            FadeSlideIn(90) {
                Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                    Column(Modifier.padding(18.dp)) {
                        Text("Descreva o que você precisa", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(Modifier.height(12.dp))
                        VerticeTextField(
                            message, { message = it }, "", "ex: preciso de um orçamento para pintura de 2 quartos",
                            singleLine = false, minLines = 4,
                        )
                        Spacer(Modifier.height(16.dp))
                        PrimaryButton("Enviar solicitação", Modifier.fillMaxWidth()) { onSend() }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            FadeSlideIn(140) {
                Surface(shape = RoundedCornerShape(16.dp), color = colors.success.copy(alpha = 0.09f)) {
                    Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Check, null, tint = colors.success, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(10.dp))
                        Text(
                            "Pagamento protegido: você só libera após o serviço concluído.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilterScreen(
    selected: ServiceCategory,
    maxKm: Float,
    verified: Boolean,
    pro: Boolean,
    onApply: (ServiceCategory, Float, Boolean, Boolean) -> Unit,
    onBack: () -> Unit,
) {
    val colors = LocalVerticeColors.current
    var category by remember { mutableStateOf(selected) }
    var distance by remember { mutableStateOf(maxKm) }
    var verifiedOnly by remember { mutableStateOf(verified) }
    var proOnly by remember { mutableStateOf(pro) }

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TopBar(title = "Filtros", onBack = onBack)
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 22.dp).padding(bottom = 30.dp)) {
            FadeSlideIn(0) {
                Column {
                    Text("Refine sua busca", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(6.dp))
                    Text("Escolha categoria, distância e preferências.", style = MaterialTheme.typography.bodyMedium, color = colors.textSecondary)
                }
            }
            Spacer(Modifier.height(22.dp))
            FadeSlideIn(70) {
                Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                    Column(Modifier.padding(18.dp)) {
                        Text("Categoria", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(Modifier.height(12.dp))
                        ServiceCategory.entries.chunked(2).forEach { row ->
                            Row(Modifier.fillMaxWidth().padding(bottom = 10.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                row.forEach { cat ->
                                    Box(Modifier.weight(1f)) {
                                        SelectableChip(cat.emoji + " " + cat.label, cat == category, Modifier.fillMaxWidth()) { category = cat }
                                    }
                                }
                                if (row.size == 1) Spacer(Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            FadeSlideIn(120) {
                Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                    Column(Modifier.padding(18.dp)) {
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Text("Distância máxima", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
                            Text("${distance.toInt()} km", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = colors.gradientStart)
                        }
                        androidx.compose.material3.Slider(
                            value = distance,
                            onValueChange = { distance = it },
                            valueRange = 1f..50f,
                            steps = 48,
                            colors = androidx.compose.material3.SliderDefaults.colors(
                                thumbColor = colors.gradientStart,
                                activeTrackColor = colors.gradientStart,
                                inactiveTrackColor = colors.textSecondary.copy(alpha = 0.25f),
                            ),
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Text("Somente verificadas", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
                            com.vertice.app.ui.components.VerticeSwitch(verifiedOnly, { verifiedOnly = it })
                        }
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Text("Somente Vértice Pro", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
                            com.vertice.app.ui.components.VerticeSwitch(proOnly, { proOnly = it })
                        }
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            FadeSlideIn(170) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(Modifier.weight(1f)) {
                        SecondaryButton("Limpar", Modifier.fillMaxWidth()) {
                            category = ServiceCategory.ALL
                            distance = 50f
                            verifiedOnly = false
                            proOnly = false
                        }
                    }
                    Box(Modifier.weight(1f)) {
                        PrimaryButton("Aplicar", Modifier.fillMaxWidth()) {
                            onApply(category, distance, verifiedOnly, proOnly)
                        }
                    }
                }
            }
        }
    }
}
