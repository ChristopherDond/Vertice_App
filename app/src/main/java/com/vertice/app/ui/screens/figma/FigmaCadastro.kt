package com.vertice.app.ui.screens.figma

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.ui.components.MaskVisualTransformation
import com.vertice.app.ui.components.PhoneVisualTransformation
import com.vertice.app.ui.components.PressWhite
import com.vertice.app.ui.components.vClick
import com.vertice.app.ui.design.Inter
import com.vertice.app.ui.design.Roboto
import com.vertice.app.ui.design.VColor
import com.vertice.app.ui.design.VText
import com.vertice.app.ui.design.vs

private val SectionBg = Color(0x4DFFFFFF)
private val SectionLine = Color(0x809657F3)
private val SectionLine3 = Color(0x4D883FF4)
private val InputLine = Color(0x4D883FF4)
private val PlaceholderHalf = Color(0x80000000)
private val PlaceholderWarm = Color(0xFFB8B1AA)

private val CpfMask = MaskVisualTransformation("###.###.###-##")
private val DateMask = MaskVisualTransformation("##/##/####")
private val CepMask = MaskVisualTransformation("#####-###")
private val PhoneMask = PhoneVisualTransformation()

@Composable
fun FigmaCadastro(onBack: () -> Unit = {}, onDone: () -> Unit = {}) {
    androidx.activity.compose.BackHandler(enabled = true) { onBack() }

    var nome by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var nascimento by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var verSenha by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var rua by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var complemento by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var uf by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var submitted by remember { mutableStateOf(false) }

    fun validate(): Boolean {
        submitted = true
        if (nome.trim().length < 3) {
            errorMessage = "Informe seu nome completo (ao menos 3 letras)."
            return false
        }
        if (cpf.length != 11) {
            errorMessage = "O CPF deve conter exatamente 11 dígitos."
            return false
        }
        if (nascimento.length != 8) {
            errorMessage = "Data de nascimento inválida (use DD/MM/AAAA)."
            return false
        }
        val dia = nascimento.take(2).toIntOrNull() ?: 0
        val mes = nascimento.substring(2, 4).toIntOrNull() ?: 0
        val ano = nascimento.substring(4, 8).toIntOrNull() ?: 0
        if (dia !in 1..31 || mes !in 1..12 || ano !in 1920..2024) {
            errorMessage = "Data de nascimento com dia, mês ou ano inválidos."
            return false
        }
        if (senha.length < 8) {
            errorMessage = "A senha deve conter ao menos 8 caracteres."
            return false
        }
        if (!email.contains("@") || !email.contains(".")) {
            errorMessage = "Informe um endereço de e-mail válido."
            return false
        }
        if (telefone.length !in 10..11) {
            errorMessage = "Informe o telefone com DDD (10 ou 11 dígitos)."
            return false
        }
        if (cep.length != 8) {
            errorMessage = "O CEP deve conter exatamente 8 dígitos numéricos."
            return false
        }
        if (rua.trim().isBlank()) {
            errorMessage = "Informe a rua e o bairro."
            return false
        }
        if (numero.trim().isBlank()) {
            errorMessage = "Informe o número do endereço."
            return false
        }
        if (cidade.trim().isBlank()) {
            errorMessage = "Informe a cidade."
            return false
        }
        if (uf.length != 2) {
            errorMessage = "Informe a UF do estado com 2 letras (ex: SP, PR)."
            return false
        }
        errorMessage = null
        return true
    }

    Box(Modifier.fillMaxSize().background(VColor.Bg)) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .padding(top = 24.dp, bottom = 48.dp),
        ) {
            VText("Crie sua conta", vs(28f, 700, 30.24f, family = Inter), VColor.Ink, Modifier.padding(start = 14.dp))
            Spacer(Modifier.height(8.dp))
            VText(
                "Preencha seus dados e conecte-se a novas oportunidades.",
                vs(14f, 400, 20.3f, family = Inter),
                VColor.Ink.copy(alpha = 0.6f),
                Modifier.padding(start = 14.dp).width(370.dp),
            )
            Spacer(Modifier.height(20.dp))

            // Seção 1: Dados Pessoais
            SectionCard(modifier = Modifier.padding(horizontal = 14.dp)) {
                SectionHeader("1", "Dados pessoais", "Conte-nos um pouco sobre você", xInset = 16.dp)
                Spacer(Modifier.height(17.dp))
                FieldStack(
                    label = "Nome completo",
                    placeholder = "ex: ana maria rodrigues",
                    modifier = Modifier.padding(horizontal = 18.5.dp),
                    value = nome,
                    onValueChange = {
                        nome = it.filter { c -> c.isLetter() || c.isWhitespace() }.take(80)
                    },
                    isError = submitted && nome.trim().length < 3,
                )
                Spacer(Modifier.height(20.dp))
                Row(Modifier.padding(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    LabeledInput(
                        label = "CPF",
                        placeholder = "000.000.000-00",
                        modifier = Modifier.weight(1.1f),
                        indent = 14.dp,
                        value = cpf,
                        onValueChange = { cpf = it.filter(Char::isDigit).take(11) },
                        keyboardType = KeyboardType.Number,
                        visualTransformation = CpfMask,
                        isError = submitted && cpf.length != 11,
                    )
                    LabeledInput(
                        label = "Data de nascimento",
                        placeholder = "DD/MM/AAAA",
                        modifier = Modifier.weight(1f),
                        indent = 14.dp,
                        phColor = PlaceholderWarm,
                        value = nascimento,
                        onValueChange = { nascimento = it.filter(Char::isDigit).take(8) },
                        keyboardType = KeyboardType.Number,
                        visualTransformation = DateMask,
                        isError = submitted && nascimento.length != 8,
                    )
                }
                Spacer(Modifier.height(17.dp))
                FieldStack(
                    label = "Senha",
                    placeholder = "Crie uma senha segura",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    phColor = PlaceholderWarm,
                    value = senha,
                    onValueChange = { senha = it.take(40) },
                    isPassword = true,
                    visualTransformation = if (verSenha) VisualTransformation.None else PasswordVisualTransformation(),
                    trailing = {
                        IconButton(onClick = { verSenha = !verSenha }, modifier = Modifier.size(36.dp)) {
                            Icon(
                                if (verSenha) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = if (verSenha) "Ocultar senha" else "Mostrar senha",
                                tint = VColor.PurpleMuted,
                                modifier = Modifier.size(19.dp),
                            )
                        }
                    },
                    isError = submitted && senha.length < 8,
                )
                Spacer(Modifier.height(4.dp))
                VText(
                    "Use pelo menos 8 caracteres.",
                    vs(11f, 400, 13.31f, family = Inter),
                    PlaceholderWarm,
                    Modifier.padding(start = 20.dp),
                )
                Spacer(Modifier.height(22.dp))
            }

            Spacer(Modifier.height(24.dp))

            // Seção 2: Contato
            SectionCard(modifier = Modifier.padding(horizontal = 14.dp)) {
                SectionHeader("2", "Contato", "Para confirmações e novidades", xInset = 16.dp)
                Spacer(Modifier.height(17.dp))
                FieldStack(
                    label = "Email",
                    placeholder = "ex: você@exemplo.com",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    value = email,
                    onValueChange = { email = it.trim().filter { c -> !c.isWhitespace() }.take(80) },
                    keyboardType = KeyboardType.Email,
                    isError = submitted && (!email.contains("@") || !email.contains(".")),
                )
                Spacer(Modifier.height(17.dp))
                FieldStack(
                    label = "Telefone",
                    placeholder = "(00) 00000-0000",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    value = telefone,
                    onValueChange = { telefone = it.filter(Char::isDigit).take(11) },
                    keyboardType = KeyboardType.Phone,
                    visualTransformation = PhoneMask,
                    isError = submitted && telefone.length !in 10..11,
                )
                Spacer(Modifier.height(22.dp))
            }

            Spacer(Modifier.height(24.dp))

            // Seção 3: Endereço
            SectionCard(
                modifier = Modifier.padding(horizontal = 14.dp),
                borderColor = SectionLine3,
            ) {
                SectionHeader("3", "Endereço", "Seu endereço principal", xInset = 16.dp)
                Spacer(Modifier.height(17.dp))
                FieldStack(
                    label = "CEP",
                    placeholder = "00000-000",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    value = cep,
                    onValueChange = { cep = it.filter(Char::isDigit).take(8) },
                    keyboardType = KeyboardType.Number,
                    visualTransformation = CepMask,
                    isError = submitted && cep.length != 8,
                )
                Spacer(Modifier.height(14.dp))
                FieldStack(
                    label = "Rua e bairro",
                    placeholder = "Ex.: Rua das Araucárias, Centro",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    phColor = PlaceholderWarm,
                    value = rua,
                    onValueChange = { rua = it.take(120) },
                    isError = submitted && rua.trim().isBlank(),
                )
                Spacer(Modifier.height(14.dp))
                Row(Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    LabeledInput(
                        label = "Número",
                        placeholder = "123",
                        modifier = Modifier.width(100.dp),
                        indent = 14.dp,
                        phColor = PlaceholderWarm,
                        value = numero,
                        onValueChange = { numero = it.filter { c -> c.isDigit() || c.isLetter() }.take(10) },
                        keyboardType = KeyboardType.Number,
                        isError = submitted && numero.trim().isBlank(),
                    )
                    LabeledInput(
                        label = "Complemento",
                        placeholder = "Apto, bloco...",
                        modifier = Modifier.weight(1f),
                        indent = 14.dp,
                        value = complemento,
                        onValueChange = { complemento = it.take(50) },
                    )
                }
                Spacer(Modifier.height(14.dp))
                Row(Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    LabeledInput(
                        label = "Cidade",
                        placeholder = "Sua cidade",
                        modifier = Modifier.weight(1f),
                        indent = 14.dp,
                        value = cidade,
                        onValueChange = { cidade = it.filter { c -> c.isLetter() || c.isWhitespace() }.take(60) },
                        isError = submitted && cidade.trim().isBlank(),
                    )
                    LabeledInput(
                        label = "UF",
                        placeholder = "PR",
                        modifier = Modifier.width(72.dp),
                        indent = 14.dp,
                        value = uf,
                        onValueChange = { uf = it.filter(Char::isLetter).uppercase().take(2) },
                        isError = submitted && uf.length != 2,
                    )
                }
                Spacer(Modifier.height(22.dp))
            }

            // Mensagem de Erro
            if (errorMessage != null) {
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(Color(0xFFFFECEC), RoundedCornerShape(10.dp))
                        .border(1.dp, VColor.Red2.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        Icons.Filled.ErrorOutline,
                        contentDescription = null,
                        tint = VColor.Red2,
                        modifier = Modifier.size(18.dp),
                    )
                    VText(
                        errorMessage!!,
                        vs(12.5f, 500, 16f, family = Inter),
                        VColor.Red2,
                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            // Botão "Cadastrar"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(52.dp)
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(14.dp),
                        spotColor = Color(0x40883FF4),
                    )
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF883FF4), Color(0xFF9B13AC)),
                        ),
                    )
                    .vClick(PressWhite) {
                        if (validate()) {
                            onDone()
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                VText(
                    text = "Cadastrar",
                    style = vs(16f, 800, 20f, family = Roboto),
                    color = Color.White,
                    align = TextAlign.Center,
                )
            }

            Spacer(Modifier.height(14.dp))

            // Link "Já tem uma conta? Entre"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                VText(
                    "Já tem uma conta? ",
                    vs(13f, 400, 16f, family = Inter),
                    VColor.Ink.copy(alpha = 0.6f),
                )
                VText(
                    "Entrar",
                    vs(13f, 700, 16f, family = Inter),
                    VColor.Purple,
                    Modifier.clickable { onBack() },
                )
            }

            Spacer(Modifier.height(30.dp))
        }
    }
}

@Composable
private fun SectionCard(
    modifier: Modifier = Modifier,
    borderColor: Color = SectionLine,
    content: @Composable () -> Unit,
) {
    Column(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SectionBg)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp)),
        content = { content() },
    )
}

@Composable
private fun SectionHeader(step: String, title: String, desc: String, xInset: Dp) {
    Column(Modifier.padding(start = xInset, top = 16.dp)) {
        Box(
            Modifier.size(30.dp).clip(RoundedCornerShape(99.dp)).background(VColor.PurpleMid),
            contentAlignment = Alignment.Center,
        ) {
            VText(step, vs(13f, 700, 15.73f, family = Inter), VColor.White)
        }
        Spacer(Modifier.height(12.dp))
        VText(title, vs(17f, 700, 20.57f, family = Inter), VColor.Ink)
        Spacer(Modifier.height(2.dp))
        VText(desc, vs(12f, 400, 14.52f, family = Inter), VColor.Ink.copy(alpha = 0.5f))
    }
}

@Composable
private fun FieldStack(
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    phColor: Color = PlaceholderHalf,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    visualTransformation: VisualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
    trailing: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
) {
    Column(modifier) {
        VText(label, vs(12f, 600, 14.52f, family = Inter), if (isError) VColor.Red2 else VColor.Ink)
        Spacer(Modifier.height(6.dp))
        InputBox(
            placeholder = placeholder,
            modifier = Modifier.fillMaxWidth(),
            phColor = phColor,
            value = value,
            onValueChange = onValueChange,
            keyboardType = keyboardType,
            visualTransformation = visualTransformation,
            trailing = trailing,
            isError = isError,
        )
    }
}

@Composable
private fun LabeledInput(
    label: String,
    placeholder: String,
    modifier: Modifier,
    indent: Dp,
    phColor: Color = PlaceholderHalf,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
) {
    Column(modifier) {
        VText(label, vs(12f, 600, 14.52f, family = Inter), if (isError) VColor.Red2 else VColor.Ink)
        Spacer(Modifier.height(6.dp))
        InputBox(
            placeholder = placeholder,
            modifier = Modifier.fillMaxWidth(),
            phColor = phColor,
            indent = indent,
            value = value,
            onValueChange = onValueChange,
            keyboardType = keyboardType,
            visualTransformation = visualTransformation,
            isError = isError,
        )
    }
}

@Composable
private fun InputBox(
    placeholder: String,
    modifier: Modifier,
    phColor: Color,
    indent: Dp = 14.dp,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailing: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
) {
    val interaction = remember { MutableInteractionSource() }
    val focused by interaction.collectIsFocusedAsState()
    val stroke by animateFloatAsState(if (focused) 2f else 1f, tween(180), label = "stroke")

    Row(
        modifier
            .height(48.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(VColor.White)
            .border(
                width = if (isError) 1.5.dp else stroke.dp,
                color = if (isError) VColor.Red2 else if (focused) VColor.Purple else InputLine,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(start = indent, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.weight(1f)) {
            if (value.isEmpty()) {
                VText(
                    placeholder,
                    vs(14f, 400, 16.94f, family = Inter),
                    phColor,
                )
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = vs(14f, 400, 16.94f, family = Inter).copy(color = VColor.Ink),
                cursorBrush = SolidColor(VColor.PurpleMid),
                visualTransformation = visualTransformation,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                interactionSource = interaction,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        if (trailing != null) {
            trailing()
        }
    }
}
