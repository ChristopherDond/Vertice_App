package com.vertice.app.ui.screens.figma

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vertice.app.ui.design.Roboto
import com.vertice.app.ui.design.Inter
import com.vertice.app.ui.design.VColor
import com.vertice.app.ui.design.VText
import com.vertice.app.ui.design.vs
import kotlinx.coroutines.delay

private val SectionBg = Color(0x4DFFFFFF)
private val SectionBg3 = Color(0x4DFEFEFF)
private val SectionLine = Color(0x809657F3)
private val SectionLine3 = Color(0x4D883FF4)
private val InputLine = Color(0x4D883FF4)
private val PlaceholderHalf = Color(0x80000000)
private val PlaceholderWarm = Color(0xFFB8B1AA)

@Composable
fun FigmaCadastro(onBack: () -> Unit = {}, onDone: () -> Unit = {}) {
    androidx.activity.compose.BackHandler(enabled = true) { onBack() }

    var nome by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var nascimento by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var rua by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var complemento by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var uf by remember { mutableStateOf("") }

    Box(Modifier.fillMaxSize().background(VColor.Bg)) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 89.dp),
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

            SectionCard(modifier = Modifier.padding(start = 11.dp, end = 12.dp)) {
                SectionHeader("1", "Dados pessoais", "Conte-nos um pouco sobre você", xInset = 16.dp)
                Spacer(Modifier.height(17.dp))
                FieldStack("Nome completo", "ex: ana maria rodrigues", Modifier.padding(horizontal = 18.5.dp), value = nome, onValueChange = { nome = it })
                Spacer(Modifier.height(20.dp))
                Row(Modifier.padding(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    LabeledInput("CPF", "ex: 000.000.000-00", Modifier.width(168.dp), 14.dp, value = cpf, onValueChange = { cpf = it }, keyboardType = KeyboardType.Number)
                    LabeledInput("Data de nascimento", "DD/MM/AAAA", Modifier.width(150.dp), 14.dp, PlaceholderWarm, value = nascimento, onValueChange = { nascimento = it }, keyboardType = KeyboardType.Number)
                }
                Spacer(Modifier.height(17.dp))
                FieldStack("Senha", "Crie uma senha segura", Modifier.padding(horizontal = 20.dp), PlaceholderWarm, value = senha, onValueChange = { senha = it }, isPassword = true)
                Spacer(Modifier.height(4.dp))
                VText(
                    "Use pelo menos 8 caracteres.",
                    vs(11f, 400, 13.31f, family = Inter),
                    PlaceholderWarm,
                    Modifier.padding(start = 20.dp),
                )
                Spacer(Modifier.height(22.dp))
            }

            Spacer(Modifier.height(30.dp))

            SectionCard(modifier = Modifier.padding(start = 15.dp, end = 8.dp)) {
                SectionHeader("2", "Contato", "para confirmações e novidades", xInset = 16.dp)
                Spacer(Modifier.height(17.dp))
                FieldStack("Email", "ex: você@exemplo.com", Modifier.padding(horizontal = 16.dp), value = email, onValueChange = { email = it }, keyboardType = KeyboardType.Email)
                Spacer(Modifier.height(17.dp))
                FieldStack("telefone ", "ex: (00) 00000-0000", Modifier.padding(horizontal = 16.dp), value = telefone, onValueChange = { telefone = it }, keyboardType = KeyboardType.Phone)
                Spacer(Modifier.height(22.dp))
            }

            Spacer(Modifier.height(30.dp))

            SectionCard(
                modifier = Modifier.padding(start = 14.dp, end = 9.dp),
                borderColor = SectionLine3,
            ) {
                SectionHeader("3", "Endereço", "Seu endereço principal", xInset = 16.dp)
                Spacer(Modifier.height(17.dp))
                FieldStack("CEP", "00000-000", Modifier.padding(horizontal = 16.dp), value = cep, onValueChange = { cep = it }, keyboardType = KeyboardType.Number)
                Spacer(Modifier.height(14.dp))
                FieldStack("Rua e bairro", "Ex.: Rua das Araucárias, Centro", Modifier.padding(horizontal = 16.dp), PlaceholderWarm, value = rua, onValueChange = { rua = it })
                Spacer(Modifier.height(14.dp))
                Row(Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    LabeledInput("Número", "123", Modifier.width(100.dp), 14.dp, PlaceholderWarm, value = numero, onValueChange = { numero = it }, keyboardType = KeyboardType.Number)
                    LabeledInput("Complemento", "Apto, bloco...", Modifier.width(219.dp), 14.dp, value = complemento, onValueChange = { complemento = it })
                }
                Spacer(Modifier.height(14.dp))
                Row(Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    LabeledInput("Cidade", "Sua cidade", Modifier.width(256.dp), 14.dp, value = cidade, onValueChange = { cidade = it })
                    LabeledInput("UF", "PR", Modifier.width(63.dp), 14.dp, value = uf, onValueChange = { uf = it })
                }
                Spacer(Modifier.height(22.dp))
            }

            Spacer(Modifier.height(40.dp))
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
) {
    Column(modifier) {
        VText(label, vs(12f, 600, 14.52f, family = Inter), VColor.Ink)
        Spacer(Modifier.height(6.dp))
        InputBox(placeholder, Modifier.fillMaxWidth(), phColor, value = value, onValueChange = onValueChange, keyboardType = keyboardType, isPassword = isPassword)
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
) {
    Column(modifier) {
        VText(label, vs(12f, 600, 14.52f, family = Inter), VColor.Ink)
        Spacer(Modifier.height(6.dp))
        InputBox(placeholder, Modifier.fillMaxWidth(), phColor, indent, value, onValueChange, keyboardType)
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
    isPassword: Boolean = false,
) {
    val interaction = remember { MutableInteractionSource() }
    val focused by interaction.collectIsFocusedAsState()
    val stroke by animateFloatAsState(if (focused) 2f else 1f, tween(180), label = "stroke")

    Row(
        modifier
            .height(48.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(VColor.White)
            .border(stroke.dp, InputLine, RoundedCornerShape(10.dp))
            .padding(start = indent, end = 14.dp),
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
                visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                interactionSource = interaction,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
