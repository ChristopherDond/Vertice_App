package com.vertice.app.ui.screens.profile

import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vertice.app.core.di.ServiceLocator
import com.vertice.app.domain.model.User
import com.vertice.app.ui.components.FadeSlideIn
import com.vertice.app.ui.components.PrimaryButton
import com.vertice.app.ui.components.SecondaryButton
import com.vertice.app.ui.components.TopBar
import com.vertice.app.ui.components.VerticeTextField
import com.vertice.app.ui.theme.LocalVerticeColors
import kotlinx.coroutines.launch
import com.vertice.app.core.result.onErr
import com.vertice.app.core.result.onOk

@Composable
private fun ChangeShell(
    title: String,
    subtitle: String,
    currentLabel: String,
    currentValue: String,
    onBack: () -> Unit,
    onConfirm: suspend () -> String?,
    fields: @Composable () -> Unit,
) {
    val colors = LocalVerticeColors.current
    val scope = rememberCoroutineScope()
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TopBar(title = title, onBack = onBack)
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 22.dp).padding(bottom = 32.dp)) {
            FadeSlideIn(0) {
                Column {
                    Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(6.dp))
                    Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = colors.textSecondary)
                }
            }
            Spacer(Modifier.height(22.dp))
            FadeSlideIn(70) {
                Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                    Column(Modifier.padding(20.dp)) {
                        Text(currentLabel, style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
                        Spacer(Modifier.height(4.dp))
                        Text(currentValue, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(Modifier.height(18.dp))
                        fields()
                        if (error != null) {
                            Spacer(Modifier.height(12.dp))
                            Text(error!!, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
                        }
                        Spacer(Modifier.height(22.dp))
                        Row {
                            Box(Modifier.weight(1f)) { SecondaryButton("Cancelar", Modifier.fillMaxWidth()) { onBack() } }
                            Spacer(Modifier.width(12.dp))
                            Box(Modifier.weight(1f)) {
                                PrimaryButton(
                                    text = if (loading) "..." else "Confirmar",
                                    enabled = !loading,
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    scope.launch {
                                        loading = true
                                        val result = onConfirm()
                                        loading = false
                                        error = result
                                        if (result == null) onBack()
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

@Composable
fun AlterarEmailScreen(onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var current by remember { mutableStateOf<User?>(null) }
    LaunchedEffect(Unit) {
        ServiceLocator.profileRepository.me().onOk { current = it }
    }
    ChangeShell(
        title = "Alterar e-mail",
        subtitle = "Informe o novo e-mail e confirme sua senha atual para continuar.",
        currentLabel = "E-mail atual",
        currentValue = current?.email.orEmpty(),
        onBack = onBack,
        onConfirm = {
            var result: String? = "Verifique os dados informados"
            ServiceLocator.authRepository.changeEmail(email)
                .onOk { result = null }
                .onErr { result = it.message }
            result
        },
    ) {
        VerticeTextField(email, { email = it }, "Novo e-mail", "voce@exemplo.com", keyboardType = KeyboardType.Email)
        Spacer(Modifier.height(14.dp))
        VerticeTextField(password, { password = it }, "Senha atual", "Sua senha", isPassword = true)
    }
}

@Composable
fun AlterarTelefoneScreen(onBack: () -> Unit) {
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var current by remember { mutableStateOf<User?>(null) }
    LaunchedEffect(Unit) {
        ServiceLocator.profileRepository.me().onOk { current = it }
    }
    ChangeShell(
        title = "Alterar telefone",
        subtitle = "Informe o novo número e confirme sua senha atual para continuar.",
        currentLabel = "Telefone atual",
        currentValue = current?.phone.orEmpty(),
        onBack = onBack,
        onConfirm = {
            var result: String? = "Verifique os dados informados"
            ServiceLocator.authRepository.changePhone(phone)
                .onOk { result = null }
                .onErr { result = it.message }
            result
        },
    ) {
        VerticeTextField(phone, { phone = it }, "Novo telefone", "(00) 00000-0000", keyboardType = KeyboardType.Phone)
        Spacer(Modifier.height(14.dp))
        VerticeTextField(password, { password = it }, "Senha atual", "Sua senha", isPassword = true)
    }
}

@Composable
fun AlterarSenhaScreen(onBack: () -> Unit) {
    var current by remember { mutableStateOf("") }
    var newPass by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    ChangeShell(
        title = "Alterar senha",
        subtitle = "Crie uma nova senha forte. Você continuará conectada nos outros dispositivos.",
        currentLabel = "Segurança",
        currentValue = "Última alteração há 3 meses",
        onBack = onBack,
        onConfirm = {
            if (newPass != confirm) return@ChangeShell "As senhas não coincidem"
            if (newPass.length < 8) return@ChangeShell "A senha precisa de ao menos 8 caracteres"
            var result: String? = "Não foi possível alterar a senha"
            ServiceLocator.authRepository.changePassword(current, newPass)
                .onOk { result = null }
                .onErr { result = it.message }
            result
        },
    ) {
        VerticeTextField(current, { current = it }, "Senha atual", "Sua senha", isPassword = true)
        Spacer(Modifier.height(14.dp))
        VerticeTextField(newPass, { newPass = it }, "Nova senha", "Mínimo 8 caracteres", isPassword = true)
        Spacer(Modifier.height(14.dp))
        VerticeTextField(confirm, { confirm = it }, "Confirmar nova senha", "Repita a nova senha", isPassword = true)
    }
}
