package com.vertice.app.ui.screens.auth

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vertice.app.core.di.ServiceLocator
import com.vertice.app.domain.model.RegistrationForm
import com.vertice.app.ui.components.FadeSlideIn
import com.vertice.app.ui.components.PrimaryButton
import com.vertice.app.ui.components.TopBar
import com.vertice.app.ui.components.VerticeTextField
import com.vertice.app.ui.theme.LocalVerticeColors
import kotlinx.coroutines.launch
import com.vertice.app.core.result.onOk
import com.vertice.app.core.result.onErr

@Composable
private fun StepBadge(n: String) {
    val colors = LocalVerticeColors.current
    Box(
        Modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(colors.gradientStart),
        contentAlignment = Alignment.Center,
    ) {
        Text(n, color = Color.White, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SectionHeading(n: String, title: String, subtitle: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        StepBadge(n)
        Spacer(Modifier.width(12.dp))
        Column {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = LocalVerticeColors.current.textSecondary)
        }
    }
}

@Composable
fun RegisterScreen(onBack: () -> Unit, onRegistered: () -> Unit) {
    val colors = LocalVerticeColors.current
    var name by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var birth by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var complement by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var uf by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        TopBar(title = "Criar conta", onBack = onBack)
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 40.dp),
        ) {
            FadeSlideIn(0) {
                Column {
                    Text("Crie sua conta", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(4.dp))
                    Text("Preencha seus dados e conecte-se a novas oportunidades.", style = MaterialTheme.typography.bodyMedium, color = colors.textSecondary)
                }
            }

            Spacer(Modifier.height(26.dp))
            FadeSlideIn(60) {
                Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                    Column(Modifier.padding(18.dp)) {
                        SectionHeading("1", "Dados pessoais", "Conte-nos um pouco sobre você")
                        Spacer(Modifier.height(16.dp))
                        VerticeTextField(name, { name = it }, "Nome completo", "ex: ana maria rodrigues")
                        Spacer(Modifier.height(14.dp))
                        Row {
                            Box(Modifier.weight(1f)) {
                                VerticeTextField(cpf, { cpf = it }, "CPF", "000.000.000-00", keyboardType = KeyboardType.Number)
                            }
                            Spacer(Modifier.width(12.dp))
                            Box(Modifier.weight(1f)) {
                                VerticeTextField(birth, { birth = it }, "Data de nascimento", "DD/MM/AAAA", keyboardType = KeyboardType.Number)
                            }
                        }
                        Spacer(Modifier.height(14.dp))
                        VerticeTextField(password, { password = it }, "Senha", "Crie uma senha segura", isPassword = true, supportingText = "Use pelo menos 8 caracteres.")
                    }
                }
            }

            Spacer(Modifier.height(18.dp))
            FadeSlideIn(120) {
                Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                    Column(Modifier.padding(18.dp)) {
                        SectionHeading("2", "Contato", "para confirmações e novidades")
                        Spacer(Modifier.height(16.dp))
                        VerticeTextField(email, { email = it }, "E-mail", "ex: voce@exemplo.com", keyboardType = KeyboardType.Email)
                        Spacer(Modifier.height(14.dp))
                        VerticeTextField(phone, { phone = it }, "Telefone", "ex: (00) 00000-0000", keyboardType = KeyboardType.Phone)
                    }
                }
            }

            Spacer(Modifier.height(18.dp))
            FadeSlideIn(180) {
                Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                    Column(Modifier.padding(18.dp)) {
                        SectionHeading("3", "Endereço", "Seu endereço principal")
                        Spacer(Modifier.height(16.dp))
                        VerticeTextField(cep, { cep = it }, "CEP", "00000-000", keyboardType = KeyboardType.Number)
                        Spacer(Modifier.height(14.dp))
                        VerticeTextField(street, { street = it }, "Rua e bairro", "Ex.: Rua das Araucárias, Centro")
                        Spacer(Modifier.height(14.dp))
                        Row {
                            Box(Modifier.width(110.dp)) {
                                VerticeTextField(number, { number = it }, "Número", "123", keyboardType = KeyboardType.Number)
                            }
                            Spacer(Modifier.width(12.dp))
                            Box(Modifier.weight(1f)) {
                                VerticeTextField(complement, { complement = it }, "Complemento", "Apto, bloco...")
                            }
                        }
                        Spacer(Modifier.height(14.dp))
                        Row {
                            Box(Modifier.weight(1f)) {
                                VerticeTextField(city, { city = it }, "Cidade", "Sua cidade")
                            }
                            Spacer(Modifier.width(12.dp))
                            Box(Modifier.width(80.dp)) {
                                VerticeTextField(uf, { uf = it }, "UF", "SP")
                            }
                        }
                    }
                }
            }

            if (error != null) {
                Spacer(Modifier.height(14.dp))
                Text(error!!, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(24.dp))
            FadeSlideIn(240) {
                PrimaryButton(
                    text = if (loading) "Criando conta..." else "Criar conta",
                    enabled = !loading,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    loading = true
                    error = null
                    kotlinx.coroutines.MainScope().launch {
                        val r = ServiceLocator.authRepository.register(
                            RegistrationForm(
                                fullName = name, cpf = cpf, birthDate = birth,
                                password = password, confirmPassword = password,
                                email = email, phone = phone, acceptedTerms = true,
                                address = com.vertice.app.domain.model.Address(
                                    cep = cep, street = street, number = number,
                                    complement = complement, district = "", city = city, state = uf,
                                ),
                            ),
                        )
                        loading = false
                        r.onOk { onRegistered() }.onErr { error = it.message }
                    }
                }
            }
        }
    }
}
