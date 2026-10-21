package com.vertice.app.ui.screens.violeta

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.vertice.app.ui.components.FadeSlideIn
import com.vertice.app.ui.components.PrimaryButton
import com.vertice.app.ui.components.TopBar
import com.vertice.app.ui.theme.LocalVerticeColors

@Composable
private fun FeatureRow(icon: ImageVector, title: String, body: String, delay: Int) {
    val colors = LocalVerticeColors.current
    FadeSlideIn(delay) {
        Surface(
            Modifier.fillMaxWidth().padding(bottom = 10.dp),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
        ) {
            Row(Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(40.dp).clip(RoundedCornerShape(13.dp)).background(colors.gradientStart.copy(alpha = 0.13f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(icon, null, tint = colors.gradientStart, modifier = Modifier.size(20.dp))
                }
                Spacer(Modifier.width(13.dp))
                Column {
                    Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Text(body, style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
                }
            }
        }
    }
}

@Composable
fun VioletaScreen(onBack: () -> Unit, onVerify: () -> Unit) {
    val colors = LocalVerticeColors.current

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TopBar(title = "Protocolo Violeta", onBack = onBack)
        LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(horizontal = 22.dp, vertical = 8.dp)) {
            item {
                FadeSlideIn(0) {
                    Surface(shape = RoundedCornerShape(24.dp), color = Color.Transparent) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .background(Brush.verticalGradient(listOf(colors.gradientStart, colors.gradientEnd)))
                                .padding(24.dp),
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                Image(
                                    painterResource(R.drawable.ic_shield),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.size(72.dp),
                                )
                                Spacer(Modifier.height(16.dp))
                                Text(
                                    "Sua rota segura,\nsempre iluminada.",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                )
                                Spacer(Modifier.height(10.dp))
                                Text(
                                    "O Protocolo Violeta é a rede de proteção do Vértice para mulheres empreendedoras.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White.copy(alpha = 0.88f),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                )
                                Spacer(Modifier.height(20.dp))
                                Surface(shape = RoundedCornerShape(18.dp), color = Color.White.copy(alpha = 0.18f)) {
                                    Row(Modifier.padding(horizontal = 14.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Filled.VerifiedUser, null, tint = Color.White, modifier = Modifier.size(15.dp))
                                        Spacer(Modifier.width(7.dp))
                                        Text("Identidade verificada", style = MaterialTheme.typography.labelSmall, color = Color.White, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(22.dp)) }
            item {
                Text("Como funciona", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                Spacer(Modifier.height(12.dp))
            }
            item { FeatureRow(Icons.Filled.Groups, "Rede 100% feminina", "Somente mulheres verificadas participam", 70) }
            item { FeatureRow(Icons.Filled.VerifiedUser, "Identidade confirmada", "Reconhecimento facial e documentos", 120) }
            item { FeatureRow(Icons.Filled.Route, "Rota segura", "Compartilhe seu trajeto em tempo real", 170) }
            item { FeatureRow(Icons.Filled.PhoneInTalk, "Suporte 24h", "Central de apoio sempre disponível", 220) }
            item { FeatureRow(Icons.Filled.Bolt, "Botão de pânico", "Aciona ajuda em um toque", 270) }
            item {
                Spacer(Modifier.height(14.dp))
                PrimaryButton("Verificar minha identidade", Modifier.fillMaxWidth()) { onVerify() }
                Spacer(Modifier.height(14.dp))
                Surface(shape = RoundedCornerShape(14.dp), color = colors.error.copy(alpha = 0.09f)) {
                    Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("🚨", fontSize = 18.sp)
                        Spacer(Modifier.width(10.dp))
                        Text(
                            "Em emergência ligue 190. O Vértice aciona sua rede de contatos.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}
