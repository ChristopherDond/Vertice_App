package com.vertice.app.ui.screens.auth

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vertice.app.core.di.ServiceLocator
import com.vertice.app.domain.model.RecoveryChannel
import com.vertice.app.ui.components.FadeSlideIn
import com.vertice.app.ui.components.PrimaryButton
import com.vertice.app.ui.components.SecondaryButton
import com.vertice.app.ui.components.TopBar
import com.vertice.app.ui.components.VerticeTextField
import com.vertice.app.ui.theme.LocalVerticeColors
import kotlinx.coroutines.launch
import com.vertice.app.core.result.onOk
import com.vertice.app.core.result.onErr

@Composable
fun ForgotPasswordScreen(onBack: () -> Unit, onDone: () -> Unit) {
    val colors = LocalVerticeColors.current
    var channel by remember { mutableStateOf(RecoveryChannel.EMAIL) }
    var contact by remember(channel) {
        mutableStateOf(if (channel == RecoveryChannel.EMAIL) "vorcaro@gmail.com" else "(11) 9273-3802")
    }
    var code by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        TopBar(title = "Recuperar senha", onBack = onBack)
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp)
                .padding(bottom = 36.dp),
        ) {
            FadeSlideIn(0) {
                Column {
                    Text("Esqueceu a senha?", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Selecione como quer receber o código de recuperação e atualize sua senha.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.textSecondary,
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            FadeSlideIn(70) {
                Surface(shape = RoundedCornerShape(22.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                    Column(Modifier.padding(20.dp)) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(colors.textSecondary.copy(alpha = 0.1f))
                                .padding(4.dp),
                        ) {
                            RecoveryChannel.entries.forEach { c ->
                                val selected = c == channel
                                val bg by animateColorAsState(
                                    if (selected) colors.gradientStart else Color.Transparent,
                                    tween(220), label = "tab",
                                )
                                Box(
                                    Modifier
                                        .weight(1f)
                                        .height(44.dp)
                                        .clip(RoundedCornerShape(11.dp))
                                        .background(bg)
                                        .clickable { channel = c },
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        if (c == RecoveryChannel.EMAIL) "E-mail" else "Telefone",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface,
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.height(22.dp))
                        VerticeTextField(
                            contact, { contact = it }, "",
                            if (channel == RecoveryChannel.EMAIL) "vorcaro@gmail.com" else "(11) 9273-3802",
                            leadingIcon = {
                                Icon(
                                    if (channel == RecoveryChannel.EMAIL) Icons.Filled.Email else Icons.Filled.Phone,
                                    null, tint = colors.gradientStart,
                                )
                            },
                        )
                        Spacer(Modifier.height(18.dp))
                        VerticeTextField(
                            code, { code = it }, "", "Insira o código de recuperação",
                            leadingIcon = { Icon(Icons.Filled.Lock, null, tint = colors.textSecondary) },
                        )
                        Spacer(Modifier.height(14.dp))
                        VerticeTextField(
                            newPassword, { newPassword = it }, "", "Insira a nova senha",
                            isPassword = true,
                            leadingIcon = { Icon(Icons.Filled.Lock, null, tint = colors.textSecondary) },
                        )
                        Spacer(Modifier.height(14.dp))
                        VerticeTextField(
                            confirmPassword, { confirmPassword = it }, "", "Confirme a nova senha",
                            isPassword = true,
                            leadingIcon = { Icon(Icons.Filled.Lock, null, tint = colors.textSecondary) },
                        )
                        if (error != null) {
                            Spacer(Modifier.height(12.dp))
                            Text(error!!, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
                        }
                        Spacer(Modifier.height(22.dp))
                        Row {
                            Box(Modifier.weight(1f)) {
                                SecondaryButton("Cancelar", Modifier.fillMaxWidth()) { onBack() }
                            }
                            Spacer(Modifier.width(12.dp))
                            Box(Modifier.weight(1f)) {
                                PrimaryButton(
                                    text = if (loading) "..." else "Confirmar",
                                    enabled = !loading,
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    if (newPassword != confirmPassword) {
                                        error = "As senhas não coincidem"
                                        return@PrimaryButton
                                    }
                                    loading = true
                                    error = null
                                    kotlinx.coroutines.MainScope().launch {
                                        val r = ServiceLocator.authRepository.resetPassword(contact, code, newPassword)
                                        loading = false
                                        r.onOk { onDone() }.onErr { error = it.message }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
