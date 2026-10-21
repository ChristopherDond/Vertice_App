package com.vertice.app.ui.screens.profile

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VerifiedUser
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
import com.vertice.app.R
import com.vertice.app.core.di.ServiceLocator
import com.vertice.app.ui.components.FadeSlideIn
import com.vertice.app.ui.theme.LocalVerticeColors

@Composable
private fun MenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String?,
    tint: Color,
    onClick: () -> Unit,
) {
    val colors = LocalVerticeColors.current
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier.size(38.dp).clip(RoundedCornerShape(12.dp)).background(tint.copy(alpha = 0.13f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, null, tint = tint, modifier = Modifier.size(19.dp))
        }
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
            if (subtitle != null) {
                Text(subtitle, style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
            }
        }
        Text("›", style = MaterialTheme.typography.titleLarge, color = colors.textSecondary)
    }
}

@Composable
fun PerfilScreen(
    onConfiguracoes: () -> Unit,
    onPro: () -> Unit,
    onTrilha: () -> Unit,
    onVioleta: () -> Unit,
    onLogout: () -> Unit,
) {
    val colors = LocalVerticeColors.current
    val session by ServiceLocator.sessionManager.session.collectAsState(initial = null)

    LazyColumn(
        Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 26.dp),
    ) {
        item {
            FadeSlideIn(0) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(Brush.verticalGradient(listOf(colors.gradientStart, colors.gradientEnd.copy(alpha = 0.9f))))
                        .padding(horizontal = 22.dp)
                        .padding(top = 54.dp, bottom = 34.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box {
                        Image(
                            painterResource(R.drawable.ana_profile),
                            contentDescription = "Foto de perfil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(104.dp).clip(CircleShape),
                        )
                        Box(
                            Modifier
                                .align(Alignment.BottomEnd)
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(Icons.Filled.VerifiedUser, null, tint = colors.gradientStart, modifier = Modifier.size(19.dp))
                        }
                    }
                    Spacer(Modifier.height(14.dp))
                    Text(
                        session?.displayName ?: "Ana Silva",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                    Spacer(Modifier.height(6.dp))
                    Surface(shape = RoundedCornerShape(20.dp), color = Color.White.copy(alpha = 0.2f)) {
                        Row(
                            Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(Icons.Filled.CheckCircle, null, tint = Color.White, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Identidade verificada", style = MaterialTheme.typography.labelSmall, color = Color.White)
                        }
                    }
                }
            }
        }

        item {
            FadeSlideIn(80) {
                Surface(
                    Modifier.fillMaxWidth().padding(horizontal = 22.dp).padding(top = 20.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 2.dp,
                ) {
                    Column(Modifier.padding(18.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("18", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = colors.gradientStart)
                                Text("Serviços", style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("4,9", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = colors.gradientStart)
                                Text("Avaliação", style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("25%", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = colors.gradientStart)
                                Text("Trilha", style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
                            }
                        }
                    }
                }
            }
        }

        item {
            FadeSlideIn(130) {
                Surface(
                    Modifier.fillMaxWidth().padding(horizontal = 22.dp).padding(top = 16.dp).clickable { onPro() },
                    shape = RoundedCornerShape(20.dp),
                    color = Color.Transparent,
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .background(Brush.horizontalGradient(listOf(colors.gradientStart, colors.gradientEnd)))
                            .padding(18.dp),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.EmojiEvents, null, tint = Color(0xFFFFD54F), modifier = Modifier.size(26.dp))
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text("Vértice Pro", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = Color.White)
                                Text("R$ 19,99/mês · destaque e recursos exclusivos", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.85f))
                            }
                        }
                    }
                }
            }
        }

        item {
            FadeSlideIn(180) {
                Column(Modifier.padding(horizontal = 22.dp).padding(top = 22.dp)) {
                    Text("Sua conta", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(10.dp))
                    Surface(shape = RoundedCornerShape(18.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                        Column {
                            MenuItem(Icons.Filled.Settings, "Configurações", "Perfil, endereço, notificações", colors.gradientStart, onConfiguracoes)
                            MenuItem(Icons.Filled.CreditCard, "Formas de pagamento", "Cartão de débito e PIX", colors.info) {}
                            MenuItem(Icons.Filled.Shield, "Protocolo Violeta", "Sua rota segura 24h", colors.gradientEnd, onVioleta)
                            MenuItem(Icons.Filled.CheckCircle, "Trilha de Blindagem", "25% concluída", colors.success, onTrilha)
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Surface(shape = RoundedCornerShape(18.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                        Column {
                            MenuItem(Icons.Filled.LocationOn, "Endereço", "Vila Madalena, São Paulo", colors.gradientStart) {}
                            MenuItem(Icons.Filled.Notifications, "Notificações", "Ativadas", colors.warning) {}
                            MenuItem(Icons.Filled.HelpOutline, "Ajuda e suporte", "Atendimento 24h", colors.textSecondary) {}
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Surface(shape = RoundedCornerShape(18.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                        MenuItem(Icons.Filled.Logout, "Sair da conta", null, MaterialTheme.colorScheme.error, onLogout)
                    }
                    Spacer(Modifier.height(28.dp))
                    Text(
                        "Vértice · versão 2.0",
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.textSecondary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    )
                }
            }
        }
    }
}
