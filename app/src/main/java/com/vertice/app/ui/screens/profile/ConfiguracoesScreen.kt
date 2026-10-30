package com.vertice.app.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vertice.app.core.di.ServiceLocator
import com.vertice.app.core.result.onOk
import com.vertice.app.domain.model.User
import com.vertice.app.ui.components.FadeSlideIn
import com.vertice.app.ui.components.TopBar
import com.vertice.app.ui.components.VerticeSwitch
import com.vertice.app.ui.components.VerticeTextField
import com.vertice.app.ui.theme.LocalVerticeColors

@Composable
private fun SettingRow(
    icon: ImageVector,
    title: String,
    value: String?,
    onClick: () -> Unit,
) {
    val colors = LocalVerticeColors.current
    Row(
        Modifier.fillMaxWidth().clickable { onClick() }.padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier.size(38.dp).clip(RoundedCornerShape(12.dp)).background(colors.gradientStart.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, null, tint = colors.gradientStart, modifier = Modifier.size(19.dp))
        }
        Spacer(Modifier.width(14.dp))
        Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
        if (value != null) Text(value, style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
        Spacer(Modifier.width(8.dp))
        Text("›", style = MaterialTheme.typography.titleLarge, color = colors.textSecondary)
    }
}

@Composable
private fun SwitchRow(icon: ImageVector, title: String, subtitle: String) {
    val colors = LocalVerticeColors.current
    var on by remember { mutableStateOf(true) }
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier.size(38.dp).clip(RoundedCornerShape(12.dp)).background(colors.gradientStart.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, null, tint = colors.gradientStart, modifier = Modifier.size(19.dp))
        }
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
            Text(subtitle, style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
        }
        VerticeSwitch(on, { on = it })
    }
}

@Suppress("UNUSED_PARAMETER")
@Composable
fun ConfiguracoesScreen(
    onBack: () -> Unit,
    onAltEmail: () -> Unit,
    onAltPhone: () -> Unit,
    onAltPassword: () -> Unit,
) {
    val colors = LocalVerticeColors.current
    var user by remember { mutableStateOf<User?>(null) }
    var popup by remember { mutableStateOf(ConfigPopup.NENHUM) }

    LaunchedEffect(Unit) {
        ServiceLocator.profileRepository.me().onOk { user = it }
    }

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TopBar(title = "Configurações", onBack = onBack)
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 28.dp),
        ) {
            item {
                FadeSlideIn(0) {
                    Column(Modifier.padding(horizontal = 22.dp).padding(top = 8.dp, bottom = 18.dp)) {
                        Text("Gerencie seus dados, endereço e preferências.", style = MaterialTheme.typography.bodyMedium, color = colors.textSecondary)
                    }
                }
            }
            item {
                FadeSlideIn(60) {
                    Column(Modifier.padding(horizontal = 22.dp)) {
                        Text("Perfil", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(Modifier.height(10.dp))
                        Surface(shape = RoundedCornerShape(18.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                            Column {
                                SettingRow(Icons.Filled.Person, "Nome completo", user?.name ?: "", onClick = {})
                                SettingRow(Icons.Filled.Email, "E-mail", user?.email ?: "", onClick = { popup = ConfigPopup.EMAIL_CODIGO })
                                SettingRow(Icons.Filled.Phone, "Telefone", user?.phone ?: "", onClick = onAltPhone)
                                SettingRow(Icons.Filled.Lock, "Senha", "Alterar", onClick = { popup = ConfigPopup.SENHA_RECUPERAR })
                            }
                        }
                    }
                }
            }
            item {
                FadeSlideIn(110) {
                    Column(Modifier.padding(horizontal = 22.dp).padding(top = 20.dp)) {
                        Text("Endereço", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(Modifier.height(10.dp))
                        Surface(shape = RoundedCornerShape(18.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                            Column(Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Filled.LocationOn, null, tint = colors.gradientStart, modifier = Modifier.size(19.dp))
                                    Spacer(Modifier.width(10.dp))
                                    Text(user?.address?.street ?: "", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                                }
                                Spacer(Modifier.height(14.dp))
                                VerticeTextField(user?.address?.district ?: "", {}, "Bairro", "Seu bairro")
                                Spacer(Modifier.height(14.dp))
                                Row {
                                    Box(Modifier.weight(1f)) { VerticeTextField(user?.address?.city ?: "", {}, "Cidade", "") }
                                    Spacer(Modifier.width(12.dp))
                                    Box(Modifier.width(80.dp)) { VerticeTextField(user?.address?.state ?: "", {}, "UF", "") }
                                }
                                Spacer(Modifier.height(14.dp))
                                VerticeTextField(user?.address?.cep ?: "", {}, "CEP", "")
                            }
                        }
                    }
                }
            }
            item {
                FadeSlideIn(160) {
                    Column(Modifier.padding(horizontal = 22.dp).padding(top = 20.dp)) {
                        Text("Notificações", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(Modifier.height(10.dp))
                        Surface(shape = RoundedCornerShape(18.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                            Column {
                                SwitchRow(Icons.Filled.Notifications, "Novos matches", "Avisar quando surgir um serviço")
                                SwitchRow(Icons.Filled.Notifications, "Pagamentos", "Confirmações e extratos")
                                SwitchRow(Icons.Filled.Notifications, "Protocolo Violeta", "Alertas de segurança")
                            }
                        }
                    }
                }
            }
        }
        ConfiguracoesPopups(
            popup = popup,
            userName = user?.name.orEmpty(),
            userEmail = user?.email.orEmpty(),
            userPhone = user?.phone.orEmpty(),
            onPopup = { popup = it },
            onDismiss = { popup = ConfigPopup.NENHUM },
        )
    }
}
