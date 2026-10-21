package com.vertice.app.ui.screens.figma

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vertice.app.ui.components.PressAccent
import com.vertice.app.ui.components.PressSoft
import com.vertice.app.ui.components.vClick
import com.vertice.app.ui.design.Inter
import com.vertice.app.ui.design.Roboto
import com.vertice.app.ui.design.VColor
import com.vertice.app.ui.design.VText
import com.vertice.app.ui.design.vs
import com.vertice.app.ui.icons.IcoAkarIconsPlus
import com.vertice.app.ui.icons.IcoImageFill

private val EpCardBg = VColor.BgAlt
private val EpCardLine = Color(0x809657F3)
private val EpInputLine = Color(0x4D883FF4)
private val EpPlaceholder = Color(0x80000000)
private val EpChipBg = Color(0x269657F3)
private val EpChipLine = Color(0x4D904EF2)
private val EpShadow = Color(0x40883FF4)
private val EpToggleRowLine = Color(0x33883FF4)

object EditarPerfilMock {
    const val name = "Carlos Mendes"
    const val subtitle = "Atualize seus dados e informações de perfil"
    const val birthDigits = ""
    const val bio = "Costureira Pirulitos felizes"
    val skills = listOf("Costura", "Concreto", "Manejo")
    const val email = "Vorcaro@gmail.com"
    const val phoneDigits = "11999999999"
    const val cepDigits = "04552040"
    const val street = "Rua Elvira Ferraz"
    const val number = "2.197"
    const val city = "São Paulo"
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FigmaEditarPerfil(
    onBack: () -> Unit = {},
    onChangePassword: () -> Unit = {},
    onLogout: () -> Unit = {},
    @DrawableRes avatarRes: Int? = null,
) {
    BackHandler(enabled = true) { onBack() }

    var birth by remember { mutableStateOf(EditarPerfilMock.birthDigits) }
    var bio by remember { mutableStateOf(EditarPerfilMock.bio) }
    var email by remember { mutableStateOf(EditarPerfilMock.email) }
    var phone by remember { mutableStateOf(EditarPerfilMock.phoneDigits) }
    var cep by remember { mutableStateOf(EditarPerfilMock.cepDigits) }
    var street by remember { mutableStateOf(EditarPerfilMock.street) }
    var number by remember { mutableStateOf(EditarPerfilMock.number) }
    var city by remember { mutableStateOf(EditarPerfilMock.city) }
    var notifyProposals by remember { mutableStateOf(true) }
    var fullName by remember { mutableStateOf(EditarPerfilMock.name) }
    val skills = remember { mutableStateListOf(*EditarPerfilMock.skills.toTypedArray()) }

    Box(Modifier.fillMaxSize().background(VColor.Bg)) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding(),
        ) {
            EpTopBar(onBack)

            EpAvatar(avatarRes, Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp))
            VText(
                fullName.ifBlank { EditarPerfilMock.name },
                vs(24f, 800, 28.23f, family = Roboto),
                VColor.Ink,
                Modifier.fillMaxWidth().padding(top = 12.dp),
                align = TextAlign.Center,
                maxLines = 1,
            )
            VText(
                EditarPerfilMock.subtitle,
                vs(12f, 400, 14.52f, family = Inter),
                VColor.Ink.copy(alpha = 0.5f),
                Modifier.fillMaxWidth().padding(top = 4.dp),
                align = TextAlign.Center,
            )

            EpCard(Modifier.padding(horizontal = 16.dp).padding(top = 20.dp)) {
                EpSectionHeader(1, "Informações pessoais", "Atualize seus dados básicos")
                Spacer(Modifier.height(16.dp))
                EpField("Nome completo") {
                    EpInput(fullName, { fullName = it.take(80) }, placeholder = "ex: ana maria rodrigues")
                }
                Spacer(Modifier.height(14.dp))
                EpField("Senha") { EpPasswordRow(onChangePassword) }
                Spacer(Modifier.height(14.dp))
                EpField("Data de Nascimento") {
                    EpInput(
                        value = birth,
                        onValueChange = { birth = it.digitsOnly(8) },
                        placeholder = "DD/MM/AAAA",
                        keyboardType = KeyboardType.Number,
                        transformation = EpMask("##/##/####"),
                    )
                }
                Spacer(Modifier.height(14.dp))
                EpField("Bio") {
                    EpInput(
                        value = bio,
                        onValueChange = { bio = it.take(160) },
                        placeholder = "Conte um pouco sobre o seu trabalho",
                        singleLine = false,
                        fieldHeight = 84.dp,
                        imeAction = ImeAction.Default,
                    )
                }
                Spacer(Modifier.height(14.dp))
                EpField("Habilidades") {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        skills.forEach { skill -> EpSkillChip(skill) { skills.remove(skill) } }
                        EpAddSkillChip { new ->
                            if (skills.none { it.equals(new, ignoreCase = true) }) skills.add(new)
                        }
                    }
                }
            }

            EpCard(Modifier.padding(horizontal = 16.dp).padding(top = 24.dp)) {
                EpSectionHeader(2, "Contato", "Para confirmações e novidades")
                Spacer(Modifier.height(16.dp))
                EpField("E-mail") {
                    EpInput(
                        value = email,
                        onValueChange = { email = it.trim() },
                        placeholder = "ex: você@exemplo.com",
                        keyboardType = KeyboardType.Email,
                    )
                }
                Spacer(Modifier.height(14.dp))
                EpField("Telefone") {
                    EpInput(
                        value = phone,
                        onValueChange = { phone = it.digitsOnly(11) },
                        placeholder = "(00) 00000-0000",
                        keyboardType = KeyboardType.Phone,
                        transformation = EpMask("(##) #####-####"),
                    )
                }
            }

            EpCard(Modifier.padding(horizontal = 16.dp).padding(top = 24.dp)) {
                EpSectionHeader(3, "Endereço", "Seu endereço principal")
                Spacer(Modifier.height(16.dp))
                EpField("CEP") {
                    EpInput(
                        value = cep,
                        onValueChange = { cep = it.digitsOnly(8) },
                        placeholder = "00000-000",
                        keyboardType = KeyboardType.Number,
                        transformation = EpMask("#####-###"),
                    )
                }
                Spacer(Modifier.height(14.dp))
                EpField("Rua / Logradouro") {
                    EpInput(street, { street = it.take(100) }, placeholder = "Ex.: Rua das Araucárias")
                }
                Spacer(Modifier.height(14.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    EpField("Número", Modifier.weight(0.42f)) {
                        EpInput(
                            value = number,
                            onValueChange = { number = it.take(10) },
                            placeholder = "123",
                            keyboardType = KeyboardType.Number,
                        )
                    }
                    EpField("Cidade", Modifier.weight(0.58f)) {
                        EpInput(
                            value = city,
                            onValueChange = { city = it.take(60) },
                            placeholder = "Sua cidade",
                            imeAction = ImeAction.Done,
                        )
                    }
                }
            }

            EpCard(Modifier.padding(horizontal = 16.dp).padding(top = 24.dp)) {
                EpSectionHeader(4, "Notificações", "Gerencie seus alertas")
                Spacer(Modifier.height(14.dp))
                EpToggleRow(
                    title = "Propostas aceitas ou recusadas",
                    description = "Receba alertas sobre status de propostas",
                    checked = notifyProposals,
                    onCheckedChange = { notifyProposals = it },
                )
            }

            EpLogoutButton(onLogout, Modifier.padding(horizontal = 16.dp).padding(top = 24.dp))
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun EpTopBar(onBack: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().padding(start = 12.dp, end = 16.dp, top = 12.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier
                .size(40.dp)
                .clip(CircleShape)
                .vClick(PressAccent) { onBack() }
                .semantics { contentDescription = "Voltar" },
            contentAlignment = Alignment.Center,
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, Modifier.size(24.dp), tint = VColor.Ink)
        }
        Spacer(Modifier.width(8.dp))
        VText("Editar Perfil", vs(20f, 700, 23.52f, family = Roboto), VColor.Ink, maxLines = 1)
    }
}

@Composable
private fun EpAvatar(@DrawableRes avatarRes: Int?, modifier: Modifier = Modifier) {
    Box(
        modifier
            .size(110.dp)
            .clip(CircleShape)
            .background(Color.White)
            .semantics { contentDescription = "Foto de perfil" },
        contentAlignment = Alignment.Center,
    ) {
        if (avatarRes != null) {
            Image(
                painterResource(avatarRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        } else {
            Icon(IcoImageFill, null, Modifier.size(36.dp), tint = VColor.InkSoft)
        }
    }
}

@Composable
private fun EpCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    val shape = RoundedCornerShape(16.dp)
    Column(
        modifier
            .fillMaxWidth()
            .shadow(6.dp, shape, ambientColor = EpShadow, spotColor = EpShadow)
            .background(EpCardBg, shape)
            .border(1.dp, EpCardLine, shape)
            .padding(16.dp),
        content = content,
    )
}

@Composable
private fun EpSectionHeader(step: Int, title: String, description: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier.size(24.dp).clip(CircleShape).background(VColor.PurpleMid),
            contentAlignment = Alignment.Center,
        ) {
            VText(step.toString(), vs(12f, 700, 14.52f, family = Inter), VColor.White)
        }
        Spacer(Modifier.width(8.dp))
        Column {
            VText(title, vs(16f, 700, 19.36f, family = Inter), VColor.Ink)
            VText(description, vs(11f, 400, 13.31f, family = Inter), VColor.Ink.copy(alpha = 0.5f))
        }
    }
}

@Composable
private fun EpField(label: String, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Column(modifier.fillMaxWidth()) {
        VText(label, vs(12f, 600, 14.52f, family = Inter), VColor.Ink)
        Spacer(Modifier.height(6.dp))
        content()
    }
}

@Composable
private fun EpInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    singleLine: Boolean = true,
    fieldHeight: Dp = 48.dp,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    transformation: VisualTransformation = VisualTransformation.None,
) {
    val interaction = remember { MutableInteractionSource() }
    val focused by interaction.collectIsFocusedAsState()
    val stroke by animateFloatAsState(if (focused) 2f else 1f, tween(180), label = "epStroke")
    val shape = RoundedCornerShape(10.dp)
    val textStyle = vs(14f, 400, 16.94f, family = Inter)

    Box(
        modifier
            .fillMaxWidth()
            .height(fieldHeight)
            .clip(shape)
            .background(VColor.White)
            .border(stroke.dp, EpInputLine, shape)
            .padding(horizontal = 14.dp, vertical = if (singleLine) 0.dp else 14.dp),
        contentAlignment = if (singleLine) Alignment.CenterStart else Alignment.TopStart,
    ) {
        if (value.isEmpty() && placeholder.isNotEmpty()) {
            VText(placeholder, textStyle, EpPlaceholder)
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = textStyle.copy(color = VColor.Ink),
            singleLine = singleLine,
            cursorBrush = SolidColor(VColor.PurpleMid),
            visualTransformation = transformation,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            interactionSource = interaction,
        )
    }
}

/** Senha nunca é pré-preenchida: o campo só mostra máscara e leva para "Alterar senha". */
@Composable
private fun EpPasswordRow(onClick: () -> Unit) {
    val shape = RoundedCornerShape(10.dp)
    Row(
        Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(shape)
            .background(VColor.White)
            .border(1.dp, EpInputLine, shape)
            .vClick(PressSoft) { onClick() }
            .semantics { contentDescription = "Senha, toque para alterar" }
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        VText("••••••••••••", vs(14f, 400, 16.94f, family = Inter), VColor.Ink, Modifier.weight(1f))
        VText("Alterar", vs(12f, 600, 14.52f, family = Inter), VColor.Purple)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun EpSkillChip(label: String, onRemove: () -> Unit) {
    val shape = RoundedCornerShape(100.dp)
    Box(
        Modifier
            .height(32.dp)
            .clip(shape)
            .background(EpChipBg)
            .border(0.75.dp, EpChipLine, shape)
            .combinedClickable(
                onClick = {},
                onLongClick = onRemove,
                onLongClickLabel = "Remover habilidade $label",
            )
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        VText(label, vs(14f, 500, 16.47f, family = Roboto), VColor.Purple, maxLines = 1)
    }
}

@Composable
private fun EpAddSkillChip(onAdd: (String) -> Unit) {
    val shape = RoundedCornerShape(100.dp)
    var editing by remember { mutableStateOf(false) }
    var draft by remember { mutableStateOf("") }

    if (!editing) {
        Row(
            Modifier
                .height(32.dp)
                .clip(shape)
                .background(VColor.White)
                .border(1.dp, EpChipLine, shape)
                .vClick(PressAccent) { editing = true }
                .semantics { contentDescription = "Adicionar habilidade" }
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            VText("Adicionar", vs(14f, 500, 16.47f, family = Roboto), VColor.Ink, maxLines = 1)
            Spacer(Modifier.width(4.dp))
            Icon(IcoAkarIconsPlus, null, Modifier.size(12.dp), tint = VColor.Ink)
        }
        return
    }

    val focus = remember { FocusRequester() }
    var hadFocus by remember { mutableStateOf(false) }
    fun commit() {
        val skill = draft.trim()
        draft = ""
        editing = false
        if (skill.isNotEmpty()) onAdd(skill)
    }
    LaunchedEffect(Unit) { focus.requestFocus() }

    Box(
        Modifier
            .height(32.dp)
            .width(132.dp)
            .clip(shape)
            .background(VColor.White)
            .border(1.5.dp, VColor.PurpleMid, shape)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        BasicTextField(
            value = draft,
            onValueChange = { draft = it.take(24) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focus)
                .onFocusChanged { state ->
                    if (state.isFocused) hadFocus = true else if (hadFocus) commit()
                },
            textStyle = vs(14f, 500, 16.47f, family = Roboto).copy(color = VColor.Ink),
            singleLine = true,
            cursorBrush = SolidColor(VColor.PurpleMid),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { commit() }),
        )
    }
}

@Composable
private fun EpToggleRow(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    val shape = RoundedCornerShape(12.dp)
    Row(
        Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(VColor.White)
            .border(1.dp, EpToggleRowLine, shape)
            .toggleable(value = checked, role = Role.Switch, onValueChange = onCheckedChange)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            VText(title, vs(13f, 700, 15.73f, family = Inter), VColor.Ink)
            Spacer(Modifier.height(2.dp))
            VText(description, vs(11f, 400, 13.31f, family = Inter), VColor.Ink.copy(alpha = 0.5f))
        }
        Spacer(Modifier.width(12.dp))
        Switch(
            checked = checked,
            onCheckedChange = null,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = VColor.IosGreen,
                checkedBorderColor = Color.Transparent,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = VColor.Gray4,
                uncheckedBorderColor = Color.Transparent,
            ),
        )
    }
}

@Composable
private fun EpLogoutButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(12.dp)
    Row(
        modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(shape)
            .background(VColor.White)
            .border(1.dp, VColor.Red2.copy(alpha = 0.55f), shape)
            .vClick(PressSoft) { onClick() }
            .semantics { contentDescription = "Sair da conta" },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(Icons.AutoMirrored.Filled.Logout, null, Modifier.size(20.dp), tint = VColor.Red2)
        Spacer(Modifier.width(8.dp))
        VText("Sair da conta", vs(15f, 500, 17.64f, family = Roboto), VColor.Red2, maxLines = 1)
    }
}

private fun String.digitsOnly(max: Int): String = filter(Char::isDigit).take(max)

/** Guarda só dígitos no estado e exibe com máscara ('#' = dígito). */
private class EpMask(private val mask: String) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text
        val shown = StringBuilder()
        var next = 0
        for (m in mask) {
            if (next >= digits.length) break
            if (m == '#') shown.append(digits[next++]) else shown.append(m)
        }
        val mapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var pos = 0
                var seen = 0
                while (pos < mask.length && seen < offset) {
                    if (mask[pos] == '#') seen++
                    pos++
                }
                return pos.coerceAtMost(shown.length)
            }

            override fun transformedToOriginal(offset: Int): Int =
                mask.take(offset.coerceAtMost(mask.length)).count { it == '#' }.coerceAtMost(digits.length)
        }
        return TransformedText(AnnotatedString(shown.toString()), mapping)
    }
}
