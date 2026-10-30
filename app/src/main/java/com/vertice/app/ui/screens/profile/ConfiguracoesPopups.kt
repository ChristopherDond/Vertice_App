package com.vertice.app.ui.screens.profile

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vertice.app.ui.design.Roboto
import com.vertice.app.ui.design.VColor
import com.vertice.app.ui.design.VGrad
import com.vertice.app.ui.icons.IcoAkarIconsCheck

enum class ConfigPopup {
    NENHUM,
    SENHA_RECUPERAR,
    SENHA_NOVA,
    SENHA_OK,
    EMAIL_CODIGO,
    EMAIL_NOVO,
    SUPORTE,
}

private val PopupScrim = Color(0x66231139)
private val PopupGlow = Color(0x1F000000)

private val FTitle = TextStyle(fontFamily = Roboto, fontSize = 28.sp, fontWeight = FontWeight.Bold, lineHeight = 32.8.sp)
private val FDesc = TextStyle(fontFamily = Roboto, fontSize = 14.sp, fontWeight = FontWeight.Normal, lineHeight = 20.3.sp)
private val FField = TextStyle(fontFamily = Roboto, fontSize = 15.sp, fontWeight = FontWeight.Normal, lineHeight = 17.6.sp)
private val FToggleOn = TextStyle(fontFamily = Roboto, fontSize = 15.sp, fontWeight = FontWeight.Bold, lineHeight = 17.6.sp)
private val FToggleOff = TextStyle(fontFamily = Roboto, fontSize = 15.sp, fontWeight = FontWeight.Medium, lineHeight = 17.6.sp)
private val FBtn = TextStyle(fontFamily = Roboto, fontSize = 15.sp, fontWeight = FontWeight.Bold, lineHeight = 17.6.sp)
private val FLink = TextStyle(fontFamily = Roboto, fontSize = 14.sp, fontWeight = FontWeight.Bold, lineHeight = 16.4.sp)
private val FLabel = TextStyle(fontFamily = Roboto, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, lineHeight = 15.2.sp)
private val FHelper = TextStyle(fontFamily = Roboto, fontSize = 12.sp, fontWeight = FontWeight.Normal, lineHeight = 16.8.sp)
private val FOptional = TextStyle(fontFamily = Roboto, fontSize = 12.sp, fontWeight = FontWeight.Normal, lineHeight = 14.1.sp)
private val FFooter = TextStyle(fontFamily = Roboto, fontSize = 13.sp, fontWeight = FontWeight.Normal, lineHeight = 15.2.sp)
private val FSuccessTitle = TextStyle(fontFamily = Roboto, fontSize = 24.sp, fontWeight = FontWeight.Bold, lineHeight = 29.sp)
private val FSuccessMsg = TextStyle(fontFamily = Roboto, fontSize = 15.sp, fontWeight = FontWeight.Normal, lineHeight = 22.sp)

@Composable
private fun Modifier.tap(onClick: () -> Unit): Modifier {
    val source = remember { MutableInteractionSource() }
    return clickable(interactionSource = source, indication = null, onClick = onClick)
}

@Composable
fun ConfiguracoesPopups(
    popup: ConfigPopup,
    userName: String,
    userEmail: String,
    userPhone: String,
    onPopup: (ConfigPopup) -> Unit,
    onDismiss: () -> Unit,
) {
    if (popup == ConfigPopup.NENHUM) return

    val ctx = LocalContext.current
    var shown by remember(popup) { mutableStateOf(false) }
    var canal by remember(popup) { mutableStateOf(0) }
    var codigo by remember(popup) { mutableStateOf("") }
    var novaSenha by remember(popup) { mutableStateOf("") }
    var confSenha by remember(popup) { mutableStateOf("") }
    var telefone by remember(popup) { mutableStateOf(userPhone) }
    var novoEmail by remember(popup) { mutableStateOf("") }
    var confEmail by remember(popup) { mutableStateOf("") }
    var nome by remember(popup) { mutableStateOf(userName) }
    var contato by remember(popup) { mutableStateOf(userEmail) }
    var mensagem by remember(popup) { mutableStateOf("") }

    LaunchedEffect(popup) { shown = true }
    val alpha by animateFloatAsState(if (shown) 1f else 0f, tween(180), label = "popup-alpha")
    val scale by animateFloatAsState(if (shown) 1f else 0.96f, tween(220), label = "popup-scale")

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            Modifier.fillMaxSize().background(PopupScrim.copy(alpha = 0.4f * alpha)).tap { onDismiss() },
            contentAlignment = Alignment.Center,
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        this.alpha = alpha
                        scaleX = scale
                        scaleY = scale
                    }
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when (popup) {
                    ConfigPopup.SENHA_RECUPERAR -> VPopupShell(
                        cardWidth = 343.dp,
                        title = "Esqueceu a senha?",
                        desc = "Selecione como receber o código de recuperação e insira o código abaixo.",
                        onBack = onDismiss,
                    ) {
                        VToggle(listOf("E-mail", "Telefone"), canal) { canal = it }
                        VInput(
                            value = if (canal == 0) userEmail.ifBlank { "seuemail@exemplo.com" } else userPhone,
                            placeholder = "",
                            icon = if (canal == 0) Icons.Outlined.MailOutline else Icons.Outlined.Smartphone,
                            readOnly = true,
                            iconSize = if (canal == 0) 19.dp else 18.dp,
                        )
                        VInput(
                            value = codigo,
                            placeholder = "Insira o código de recuperação",
                            icon = Icons.Outlined.Lock,
                            onValueChange = { codigo = it },
                            iconSize = 19.dp,
                            keyboardType = KeyboardType.Number,
                        )
                        VActionRow("Cancelar", "Continuar", onDismiss) { onPopup(ConfigPopup.SENHA_NOVA) }
                    }

                    ConfigPopup.SENHA_NOVA -> VPopupShell(
                        cardWidth = 343.dp,
                        title = "Defina sua nova senha",
                        desc = "Crie uma nova senha segura para continuar acessando sua conta.",
                        onBack = { onPopup(ConfigPopup.SENHA_RECUPERAR) },
                    ) {
                        VInput(
                            value = novaSenha,
                            placeholder = "Nova senha",
                            icon = Icons.Outlined.Lock,
                            onValueChange = { novaSenha = it },
                            iconSize = 19.dp,
                            password = true,
                        )
                        VInput(
                            value = confSenha,
                            placeholder = "Confirmar senha",
                            icon = Icons.Outlined.Lock,
                            onValueChange = { confSenha = it },
                            iconSize = 19.dp,
                            password = true,
                        )
                        VActionRow("Voltar", "Salvar senha", { onPopup(ConfigPopup.SENHA_RECUPERAR) }) {
                            onPopup(ConfigPopup.SENHA_OK)
                        }
                    }

                    ConfigPopup.SENHA_OK -> VSuccessCard(
                        title = "Senha alterada com sucesso!",
                        message = "Você já pode acessar sua conta com a nova senha.",
                        button = "Voltar para o login",
                        onClick = onDismiss,
                    )

                    ConfigPopup.EMAIL_CODIGO -> VPopupShell(
                        cardWidth = 343.dp,
                        title = "Código de recuperação",
                        desc = "Receba um código no telefone cadastrado para recuperar seu e-mail.",
                        onBack = onDismiss,
                        footer = {
                            VFooter(
                                title = "Não tem acesso ao telefone cadastrado?",
                                link = "Fale com o suporte",
                                onClick = { onPopup(ConfigPopup.SUPORTE) },
                            )
                        },
                    ) {
                        VToggle(listOf("SMS", "WhatsApp"), canal) { canal = it }
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            VInput(
                                value = telefone,
                                placeholder = "(11) 00000-0000",
                                icon = Icons.Outlined.Smartphone,
                                onValueChange = { telefone = it },
                                iconSize = 18.dp,
                                keyboardType = KeyboardType.Phone,
                            )
                            VLink("Enviar código") {
                                Toast.makeText(
                                    ctx,
                                    if (canal == 0) "Código enviado por SMS" else "Código enviado pelo WhatsApp",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }
                        VInput(
                            value = codigo,
                            placeholder = "Insira o código de recuperação",
                            icon = Icons.Outlined.Lock,
                            onValueChange = { codigo = it },
                            iconSize = 19.dp,
                            keyboardType = KeyboardType.Number,
                        )
                        VActionRow("Cancelar", "Continuar", onDismiss) { onPopup(ConfigPopup.EMAIL_NOVO) }
                    }

                    ConfigPopup.EMAIL_NOVO -> VPopupShell(
                        cardWidth = 343.dp,
                        title = "Novo e-mail",
                        desc = "Insira e confirme o novo e-mail que deseja usar na sua conta.",
                        onBack = { onPopup(ConfigPopup.EMAIL_CODIGO) },
                        footer = {
                            VFooter(
                                title = "Precisa de ajuda para alterar seu e-mail?",
                                link = "Fale com o suporte",
                                onClick = { onPopup(ConfigPopup.SUPORTE) },
                            )
                        },
                    ) {
                        VInput(
                            value = novoEmail,
                            placeholder = "Insira seu novo e-mail",
                            icon = Icons.Outlined.MailOutline,
                            onValueChange = { novoEmail = it },
                            iconSize = 19.dp,
                            keyboardType = KeyboardType.Email,
                        )
                        VInput(
                            value = confEmail,
                            placeholder = "Confirme seu novo e-mail",
                            icon = Icons.Outlined.MailOutline,
                            onValueChange = { confEmail = it },
                            iconSize = 19.dp,
                            keyboardType = KeyboardType.Email,
                        )
                        VActionRow("Cancelar", "Confirmar", onDismiss) {
                            Toast.makeText(ctx, "E-mail atualizado", Toast.LENGTH_SHORT).show()
                            onDismiss()
                        }
                    }

                    ConfigPopup.SUPORTE -> VPopupShell(
                        cardWidth = 345.dp,
                        spacing = 16.dp,
                        title = "Fale com o suporte",
                        desc = "Perdeu acesso ao telefone cadastrado? Nossa equipe pode ajudar você por e-mail, sem usar o número antigo.",
                        onBack = onDismiss,
                        footer = {
                            Text(
                                "Por segurança, não envie senhas ou códigos de verificação.",
                                style = FFooter,
                                color = VColor.PurpleMuted,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        },
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Nome completo", style = FLabel, color = VColor.PurpleDarkest)
                            VInput(
                                value = nome,
                                placeholder = "Informe seu nome",
                                icon = Icons.Outlined.PersonOutline,
                                onValueChange = { nome = it },
                            )
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("E-mail para contato", style = FLabel, color = VColor.PurpleDarkest)
                            VInput(
                                value = contato,
                                placeholder = "seunome@exemplo.com",
                                icon = Icons.Outlined.MailOutline,
                                onValueChange = { contato = it },
                                iconSize = 19.dp,
                                keyboardType = KeyboardType.Email,
                            )
                            Text(
                                "Use um e-mail ao qual você tenha acesso. Enviaremos a resposta para ele.",
                                style = FHelper,
                                color = VColor.PurpleMuted,
                            )
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Como podemos ajudar?", style = FLabel, color = VColor.PurpleDarkest)
                                Spacer(Modifier.width(6.dp))
                                Text("Opcional", style = FOptional, color = VColor.PurpleMuted)
                            }
                            VTextArea(
                                value = mensagem,
                                placeholder = "Conte o que aconteceu com seu acesso ao telefone.",
                                onValueChange = { mensagem = it },
                            )
                        }
                        VGradeButton(
                            label = "Enviar solicitação",
                            icon = Icons.Outlined.ChatBubbleOutline,
                            onClick = {
                                Toast.makeText(ctx, "Solicitação enviada", Toast.LENGTH_SHORT).show()
                                onDismiss()
                            },
                        )
                    }

                    ConfigPopup.NENHUM -> Unit
                }
            }
        }
    }
}

@Composable
private fun VPopupShell(
    cardWidth: Dp,
    title: String,
    desc: String,
    onBack: () -> Unit,
    spacing: Dp = 20.dp,
    footer: (@Composable () -> Unit)? = null,
    body: @Composable ColumnScope.() -> Unit,
) {
    Column(Modifier.width(cardWidth), horizontalAlignment = Alignment.Start) {
        VBackButton(onBack)
        Spacer(Modifier.height(20.dp))
        Text(title, style = FTitle, color = VColor.PurpleDarkest, modifier = Modifier.width(cardWidth - 8.dp))
        Spacer(Modifier.height(8.dp))
        Text(desc, style = FDesc, color = VColor.PurpleMuted, modifier = Modifier.width(cardWidth - 8.dp))
        Spacer(Modifier.height(20.dp))
        VCard(cardWidth, spacing, body)
        if (footer != null) {
            Spacer(Modifier.height(20.dp))
            footer()
        }
    }
}

@Composable
private fun VBackButton(onClick: () -> Unit) {
    val shape = RoundedCornerShape(10.dp)
    Box(
        Modifier
            .width(40.dp)
            .height(37.dp)
            .shadow(4.dp, shape, ambientColor = PopupGlow, spotColor = PopupGlow)
            .clip(shape)
            .background(VColor.WhiteWarm)
            .tap(onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar", tint = VColor.Ink, modifier = Modifier.size(14.dp))
    }
}

@Composable
private fun VCard(
    width: Dp,
    spacing: Dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(24.dp)
    Column(
        Modifier
            .width(width)
            .shadow(20.dp, shape, ambientColor = PopupGlow, spotColor = PopupGlow)
            .clip(shape)
            .background(VColor.White)
            .border(1.dp, VColor.PurpleLineSoft, shape)
            .tap {}
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(spacing),
        content = content,
    )
}

@Composable
private fun VToggle(labels: List<String>, selected: Int, onSelect: (Int) -> Unit) {
    val shape = RoundedCornerShape(16.dp)
    Row(
        Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(shape)
            .background(VColor.BgChip)
            .border(1.dp, VColor.PurpleLineSoft, shape)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        labels.forEachIndexed { index, label ->
            val active = index == selected
            val tabShape = RoundedCornerShape(12.dp)
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(tabShape)
                    .then(if (active) Modifier.background(VGrad.Pix) else Modifier.tap { onSelect(index) }),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    label,
                    style = if (active) FToggleOn else FToggleOff,
                    color = if (active) VColor.BgAlt else VColor.PurpleMuted,
                )
            }
        }
    }
}

@Composable
private fun VInput(
    value: String,
    placeholder: String,
    icon: ImageVector,
    onValueChange: (String) -> Unit = {},
    readOnly: Boolean = false,
    iconSize: Dp = 19.dp,
    keyboardType: KeyboardType = KeyboardType.Text,
    password: Boolean = false,
) {
    val shape = RoundedCornerShape(12.dp)
    Row(
        Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(shape)
            .background(VColor.White)
            .border(1.dp, VColor.PurpleLineSoft, shape)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icon, null, tint = VColor.PurpleMuted, modifier = Modifier.size(iconSize))
        Spacer(Modifier.width(12.dp))
        Box(Modifier.weight(1f)) {
            if (value.isEmpty() && placeholder.isNotEmpty()) {
                Text(placeholder, style = FField, color = VColor.PurpleMuted, maxLines = 1)
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                readOnly = readOnly,
                singleLine = true,
                textStyle = FField.copy(color = VColor.PurpleDarkest),
                cursorBrush = SolidColor(VColor.Purple),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun VTextArea(value: String, placeholder: String, onValueChange: (String) -> Unit) {
    val shape = RoundedCornerShape(12.dp)
    Box(
        Modifier
            .fillMaxWidth()
            .height(88.dp)
            .clip(shape)
            .background(VColor.White)
            .border(1.dp, VColor.PurpleLineSoft, shape)
            .padding(14.dp),
    ) {
        if (value.isEmpty()) {
            Text(placeholder, style = FDesc, color = VColor.PurpleMuted)
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = FDesc.copy(color = VColor.PurpleDarkest),
            cursorBrush = SolidColor(VColor.Purple),
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun VLink(label: String, onClick: () -> Unit) {
    Box(Modifier.fillMaxWidth().height(16.dp), contentAlignment = Alignment.CenterEnd) {
        Text(label, style = FLink, color = VColor.PurpleDarkest, modifier = Modifier.tap(onClick))
    }
}

@Composable
private fun VActionRow(cancel: String, confirm: String, onCancel: () -> Unit, onConfirm: () -> Unit) {
    val shape = RoundedCornerShape(12.dp)
    Row(Modifier.fillMaxWidth().height(48.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(
            Modifier
                .weight(146.5f)
                .fillMaxHeight()
                .clip(shape)
                .background(VColor.White)
                .border(1.dp, VColor.PurpleLineSoft, shape)
                .tap(onCancel),
            contentAlignment = Alignment.Center,
        ) {
            Text(cancel, style = FBtn, color = VColor.PurpleMuted)
        }
        Box(
            Modifier
                .weight(144.5f)
                .fillMaxHeight()
                .clip(shape)
                .background(VGrad.Pix)
                .tap(onConfirm),
            contentAlignment = Alignment.Center,
        ) {
            Text(confirm, style = FBtn, color = VColor.BgAlt)
        }
    }
}

@Composable
private fun VGradeButton(label: String, icon: ImageVector, onClick: () -> Unit) {
    val shape = RoundedCornerShape(12.dp)
    Box(
        Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(shape)
            .background(VGrad.Pix)
            .tap(onClick),
        contentAlignment = Alignment.Center,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(icon, null, tint = VColor.BgAlt, modifier = Modifier.size(14.dp))
            Text(label, style = FBtn, color = VColor.BgAlt)
        }
    }
}

@Composable
private fun VFooter(title: String, link: String, onClick: () -> Unit) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(title, style = FFooter, color = VColor.PurpleMuted, textAlign = TextAlign.Center)
        Text(link, style = FLink, color = VColor.PurpleDarkest, modifier = Modifier.tap(onClick))
    }
}

@Composable
private fun VSuccessCard(title: String, message: String, button: String, onClick: () -> Unit) {
    val shape = RoundedCornerShape(24.dp)
    val buttonShape = RoundedCornerShape(12.dp)
    Column(
        Modifier
            .width(343.dp)
            .shadow(20.dp, shape, ambientColor = PopupGlow, spotColor = PopupGlow)
            .clip(shape)
            .background(VColor.White)
            .border(1.dp, VColor.PurpleLineSoft, shape)
            .tap {}
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Box(
            Modifier.size(64.dp).clip(CircleShape).background(VColor.PurpleTint),
            contentAlignment = Alignment.Center,
        ) {
            Icon(IcoAkarIconsCheck, null, tint = VColor.Purple, modifier = Modifier.size(30.dp))
        }
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(title, style = FSuccessTitle, color = VColor.PurpleDarkest, textAlign = TextAlign.Center)
            Text(message, style = FSuccessMsg, color = VColor.PurpleMuted, textAlign = TextAlign.Center)
        }
        Box(
            Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(buttonShape)
                .background(VGrad.Pix)
                .tap(onClick),
            contentAlignment = Alignment.Center,
        ) {
            Text(button, style = FBtn, color = VColor.BgAlt)
        }
    }
}