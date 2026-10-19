package com.vertice.app.ui.screens.auth

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vertice.app.R
import com.vertice.app.core.di.ServiceLocator
import com.vertice.app.ui.components.FadeSlideIn
import com.vertice.app.ui.components.clickableText
import com.vertice.app.ui.components.PrimaryButton
import com.vertice.app.ui.components.SocialButton
import com.vertice.app.ui.components.VerticeTextField
import com.vertice.app.ui.theme.LocalVerticeColors
import kotlinx.coroutines.launch
import com.vertice.app.core.result.onOk
import com.vertice.app.core.result.onErr

@Composable
fun LoginScreen(
    onLoggedIn: () -> Unit,
    onRegister: () -> Unit,
    onForgot: () -> Unit,
) {
    val colors = LocalVerticeColors.current
    var credential by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    androidx.compose.foundation.layout.Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 30.dp)
                .padding(top = 84.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            FadeSlideIn(0) {
                Image(
                    painterResource(R.drawable.logo_vertice),
                    contentDescription = "Vértice",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(34.dp)),
                )
            }
            Spacer(Modifier.height(22.dp))
            FadeSlideIn(60) {
                Text(
                    "Faça seu login e conecte-se a novos parceiros.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Spacer(Modifier.height(38.dp))
            FadeSlideIn(120) {
                VerticeTextField(
                    value = credential,
                    onValueChange = { credential = it },
                    label = "CPF ou e-mail:",
                    placeholder = "000.000.000-00",
                    keyboardType = KeyboardType.Email,
                )
            }
            Spacer(Modifier.height(14.dp))
            FadeSlideIn(170) {
                VerticeTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Senha:",
                    placeholder = "Sua senha",
                    isPassword = true,
                )
            }
            if (error != null) {
                Spacer(Modifier.height(10.dp))
                Text(error!!, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
            }
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text(
                    "Esqueceu a senha?",
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.textSecondary,
                    modifier = Modifier
                        .clickableText { onForgot() }
                        .padding(4.dp),
                )
            }
            Spacer(Modifier.height(18.dp))
            FadeSlideIn(220) {
                PrimaryButton(
                    text = if (loading) "Entrando..." else "Entrar",
                    enabled = !loading,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        loading = true
                        error = null
                        kotlinx.coroutines.MainScope().launch {
                            val r = ServiceLocator.authRepository.login(credential, password)
                            loading = false
                            r.onOk { onLoggedIn() }.onErr { error = it.message }
                        }
                    },
                )
            }
            Spacer(Modifier.height(30.dp))
            Text("OU", style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
            Spacer(Modifier.height(16.dp))
            FadeSlideIn(280) {
                SocialButton(
                    text = "Continue com Google",
                    provider = "google",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {},
                )
            }
            Spacer(Modifier.height(12.dp))
            FadeSlideIn(330) {
                SocialButton(
                    text = "Continue com Apple",
                    provider = "apple",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {},
                )
            }
            Spacer(Modifier.height(28.dp))
            Row(horizontalArrangement = Arrangement.Center) {
                Text(
                    "Ainda não tem uma conta? ",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary,
                )
                Text(
                    "Cadastre-se!",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.gradientStart,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickableText { onRegister() },
                )
            }
        }
    }
}
