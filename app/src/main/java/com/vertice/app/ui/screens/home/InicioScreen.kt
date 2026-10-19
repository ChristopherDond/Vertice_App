package com.vertice.app.ui.screens.home

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.R
import com.vertice.app.core.di.ServiceLocator
import com.vertice.app.core.session.Session
import com.vertice.app.ui.components.FadeSlideIn
import com.vertice.app.ui.components.PrimaryButton
import com.vertice.app.ui.theme.LocalVerticeColors

@Composable
private fun QuickAction(icon: ImageVector, label: String, tint: Color, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onClick() }) {
        Box(
            Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(tint.copy(alpha = 0.14f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, contentDescription = label, tint = tint, modifier = Modifier.size(24.dp))
        }
        Spacer(Modifier.height(8.dp))
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
fun InicioScreen(
    onOfferService: () -> Unit,
    onTrilha: () -> Unit,
    onVioleta: () -> Unit,
    onPro: () -> Unit,
    onMatch: () -> Unit,
) {
    val colors = LocalVerticeColors.current
    val session by ServiceLocator.sessionManager.session.collectAsState(initial = null)

    LazyColumn(
        Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 24.dp),
    ) {
        item {
            FadeSlideIn(0) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(listOf(colors.gradientStart, colors.gradientEnd.copy(alpha = 0.92f))),
                        )
                        .padding(horizontal = 22.dp)
                        .padding(top = 54.dp, bottom = 46.dp),
                ) {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painterResource(R.drawable.ana_profile),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(46.dp).clip(CircleShape),
                        )
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text("Bem-vinda de volta", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.78f))
                            Text(
                                session?.displayName ?: "Ana Silva",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                            )
                        }
                        Box(
                            Modifier.size(40.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.18f)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(Icons.Filled.Notifications, null, tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                    }
                    Spacer(Modifier.height(26.dp))
                    Text(
                        "Seu próximo grande\npasso começa aqui.",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        lineHeight = 34.sp,
                    )
                    Spacer(Modifier.height(18.dp))
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color.White.copy(alpha = 0.16f),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.EmojiEvents, null, tint = Color(0xFFFFD54F), modifier = Modifier.size(22.dp))
                            Spacer(Modifier.width(10.dp))
                            Column(Modifier.weight(1f)) {
                                Text("Vértice Pro", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = Color.White)
                                Text("Desbloqueie recursos exclusivos", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.8f))
                            }
                            Text(
                                "R$ 19,99/mês",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.clickable { onPro() },
                            )
                        }
                    }
                }
            }
        }

        item {
            FadeSlideIn(90) {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 22.dp, vertical = 22.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    QuickAction(Icons.Filled.Shield, "Protocolo\nVioleta", colors.gradientStart, onVioleta)
                    QuickAction(Icons.Filled.School, "Trilha de\nBlindagem", colors.gradientEnd, onTrilha)
                    QuickAction(Icons.Filled.TrendingUp, "Oferecer\nserviço", colors.info, onOfferService)
                    QuickAction(Icons.Filled.EmojiEvents, "Vértice\nPro", colors.warning, onPro)
                }
            }
        }

        item {
            FadeSlideIn(150) {
                Surface(
                    Modifier.fillMaxWidth().padding(horizontal = 22.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 2.dp,
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                Modifier.size(38.dp).clip(RoundedCornerShape(12.dp)).background(colors.gradientStart.copy(alpha = 0.14f)),
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(Icons.Filled.TrendingUp, null, tint = colors.gradientStart, modifier = Modifier.size(19.dp))
                            }
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text("Encontre seu cliente", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                Text("Prestadores e oportunidades perto de você", style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                        PrimaryButton("Explorar Match", Modifier.fillMaxWidth()) { onMatch() }
                    }
                }
            }
        }

        item { Spacer(Modifier.height(22.dp)) }

        item {
            FadeSlideIn(210) {
                Column(Modifier.padding(horizontal = 22.dp)) {
                    Text("Continuar aprendendo", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(12.dp))
                    Surface(
                        Modifier.fillMaxWidth().clickable { onTrilha() },
                        shape = RoundedCornerShape(18.dp),
                        color = MaterialTheme.colorScheme.surface,
                        tonalElevation = 2.dp,
                    ) {
                        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                Modifier.size(46.dp).clip(RoundedCornerShape(14.dp)).background(colors.gradientEnd.copy(alpha = 0.14f)),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text("🛡", fontSize = 22.sp)
                            }
                            Spacer(Modifier.width(14.dp))
                            Column(Modifier.weight(1f)) {
                                Text("Trilha de Blindagem", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                Text("3 de 12 lições concluídas", style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
                            }
                            Text("25%", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = colors.gradientStart)
                        }
                    }
                }
            }
        }
    }
}
