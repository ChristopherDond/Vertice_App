package com.vertice.app.ui.screens.pro

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.EmojiEvents
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.ui.components.FadeSlideIn
import com.vertice.app.ui.components.PrimaryButton
import com.vertice.app.ui.components.SelectableRow
import com.vertice.app.ui.components.TopBar
import com.vertice.app.ui.theme.LocalVerticeColors

private val BENEFITS = listOf(
    "Destaque nas buscas do Match",
    "Selo Pro no seu perfil",
    "Propostas ilimitadas por mês",
    "Suporte prioritário 24h",
    "Relatórios de desempenho",
    "Sem taxa nos 3 primeiros serviços",
)

@Composable
fun ProScreen(onBack: () -> Unit, onConfirm: () -> Unit) {
    val colors = LocalVerticeColors.current
    var cycle by remember { mutableStateOf(0) }
    val prices = listOf("R$ 19,99" to "/mês", "R$ 199,90" to "/ano")
    val price = prices[cycle]

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TopBar(title = "Vértice Pro", onBack = onBack)
        LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(horizontal = 22.dp, vertical = 8.dp)) {
            item {
                FadeSlideIn(0) {
                    Surface(shape = RoundedCornerShape(24.dp), color = Color.Transparent) {
                        Box(
                            Modifier.fillMaxWidth()
                                .background(Brush.verticalGradient(listOf(colors.gradientStart, colors.gradientEnd)))
                                .padding(24.dp),
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                Box(
                                    Modifier.size(66.dp).clip(RoundedCornerShape(22.dp)).background(Color.White.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(Icons.Filled.EmojiEvents, null, tint = Color(0xFFFFD54F), modifier = Modifier.size(34.dp))
                                }
                                Spacer(Modifier.height(16.dp))
                                Text("Vértice Pro", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black, color = Color.White)
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    "Mais visibilidade, mais clientes,\nmais crescimento.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White.copy(alpha = 0.88f),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                )
                            }
                        }
                    }
                }
            }
            item {
                FadeSlideIn(70) {
                    Column(Modifier.padding(top = 22.dp)) {
                        Text("O que você desbloqueia", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(Modifier.height(12.dp))
                        Surface(shape = RoundedCornerShape(18.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                            Column(Modifier.padding(16.dp)) {
                                BENEFITS.forEach { b ->
                                    Row(Modifier.padding(vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            Modifier.size(22.dp).clip(RoundedCornerShape(8.dp)).background(colors.success.copy(alpha = 0.15f)),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Icon(Icons.Filled.Check, null, tint = colors.success, modifier = Modifier.size(14.dp))
                                        }
                                        Spacer(Modifier.width(12.dp))
                                        Text(b, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            item {
                FadeSlideIn(130) {
                    Column(Modifier.padding(top = 20.dp)) {
                        Text("Escolha seu plano", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(Modifier.height(10.dp))
                        SelectableRow("Mensal · R$ 19,99/mês", cycle == 0) { cycle = 0 }
                        Spacer(Modifier.height(10.dp))
                        SelectableRow("Anual · R$ 199,90/ano (2 meses grátis)", cycle == 1) { cycle = 1 }
                    }
                }
            }
            item {
                FadeSlideIn(180) {
                    Column(Modifier.padding(top = 24.dp)) {
                        Surface(shape = RoundedCornerShape(18.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                            Row(Modifier.fillMaxWidth().padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.weight(1f)) {
                                    Text("Total hoje", style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
                                    Row(verticalAlignment = Alignment.Bottom) {
                                        Text(price.first, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Black, color = colors.gradientStart)
                                        Text(price.second, style = MaterialTheme.typography.labelSmall, color = colors.textSecondary, modifier = Modifier.padding(bottom = 4.dp))
                                    }
                                }
                                Text("Cancelar quando\nquiser", style = MaterialTheme.typography.labelSmall, color = colors.textSecondary, textAlign = androidx.compose.ui.text.style.TextAlign.End)
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                        PrimaryButton("Assinar Vértice Pro", Modifier.fillMaxWidth()) { onConfirm() }
                        Spacer(Modifier.height(10.dp))
                        Text(
                            "Cobrança recorrente. Você pode cancelar a qualquer momento nas configurações.",
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.textSecondary,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Spacer(Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}
