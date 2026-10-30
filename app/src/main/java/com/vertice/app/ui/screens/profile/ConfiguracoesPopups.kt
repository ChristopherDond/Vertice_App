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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
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

enum class ConfigPopup {
    NENHUM,
    NOME,
    SENHA_RECUPERAR,
    SENHA_NOVA,
    SENHA_OK,
    EMAIL_CODIGO,
    EMAIL_NOVO,
    EMAIL_OK,
    TELEFONE_CODIGO,
    TELEFONE_NOVO,
    TELEFONE_OK,
    SUPORTE,
}

private val PopupScrim = Color(0x99231139)
private val PopupGlow = Color(0x1F000000)

/**
 * Check do popup desenhado como traço. Fica local de propósito: o ícone compartilhado
 * `IcoAkarIconsCheck` registra um path de traço com `fill`, o que o renderiza como triângulo.
 * Corrigir aquele ícone mudaria também Perfil e Violeta, então o popup tem o seu.
 */
private val PopupCheck: ImageVector by lazy {
    ImageVector.Builder(
        name = "popup-check",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    )
        .addPath(
            pathData = addPathNodes("m4 12l6 6L20 6"),
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
        )
        .build()
}

private val FTitle = TextStyle(fontFamily = Roboto, fontSize = 24.sp, fontWeight = FontWeight.Bold, lineHeight = 28.sp)
private val FDesc = TextStyle(fontFamily = Roboto, fontSize = 14.sp, fontWeight = FontWeight.Normal, lineHeight = 20.sp)
private val FField = TextStyle(fontFamily = Roboto, fontSize = 15.sp, fontWeight = FontWeight.Normal, lineHeight = 17.6.sp)
private val FToggleOn = TextStyle(fontFamily = Roboto, fontSize = 15.sp, fontWeight = FontWeight.Bold, lineHeight = 17.6.sp)
private val FToggleOff = TextStyle(fontFamily = Roboto, fontSize = 15.sp, fontWeight = FontWeight.Medium, lineHeight = 17.6.sp)
private val FBtn = TextStyle(fontFamily = Roboto, fontSize = 15.sp, fontWeight = FontWeight.Bold, lineHeight = 17.6.sp)
private val FLink = TextStyle(fontFamily = Roboto, fontSize = 14.sp, fontWeight = FontWeight.Bold, lineHeight = 16.4.sp)
private val FLabel = TextStyle(fontFamily = Roboto, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, lineHeight = 15.2.sp)
private val FHelper = TextStyle(fontFamily = Roboto, fontSize = 12.sp, fontWeight = FontWeight.Normal, lineHeight = 16.8.sp)
private val FOptional = TextStyle(fontFamily = Roboto, fontSize = 12.sp, fontWeight = FontWeight.Normal, lineHeight = 14.1.sp)
private val FFooter = TextStyle(fontFamily = Roboto, fontSize = 13.sp, fontWeight = FontWeight.Normal, lineHeight = 15.2.sp)
private val FError = TextStyle(fontFamily = Roboto, fontSize = 13.sp, fontWeight = FontWeight.Medium, lineHeight = 17.sp)
private val FSuccessTitle = TextStyle(fontFamily = Roboto, fontSize = 22.sp, fontWeight = FontWeight.Bold, lineHeight = 26.sp)
private val FSuccessMsg = TextStyle(fontFamily = Roboto, fontSize = 15.sp, fontWeight = FontWeight.Normal, lineHeight = 22.sp)

@Composable
private fun Modifier.tap(onClick: () -> Unit): Modifier {
    val source = remember { MutableInteractionSource() }
    return clickable(interactionSource = source, indication = null, onClick = onClick)
}

/**
 * Popups da área de configuração. Todos usam um único cartão branco centralizado,
 * com o botão de voltar, o título e a descrição dentro do cartão para não disputar
 * espaço com o conteúdo da tela por trás.
 */
@Composable
fun ConfiguracoesPopups(
    popup: ConfigPopup,
    userName: String,
    userEmail: String,
    userPhone: String,
    onPopup: (ConfigPopup) -> Unit,
    onDismiss: () -> Unit,
    senhaVoltar: ConfigPopup = ConfigPopup.SENHA_RECUPERAR,
) {
    if (popup == ConfigPopup.NENHUM) return

    val ctx = LocalContext.current
    var shown by remember(popup) { mutableStateOf(false) }
    var canal by remember(popup) { mutableStateOf(0) }
    var codigo by remember(popup) { mutableStateOf("") }
    var novaSenha by remember(popup) { mutableStateOf("") }
    var confSenha by remember(popup) { mutableStateOf("") }
    var telefone by remember(popup) { mutableStateOf(userPhone) }
    var novoTelefone by remember(popup) { mutableStateOf("") }
    var novoEmail by remember(popup) { mutableStateOf("") }
    var confEmail by remember(popup) { mutableStateOf("") }
    var nome by remember(popup) { mutableStateOf(userName) }
    var contato by remember(popup) { mutableStateOf(userEmail) }
    var mensagem by remember(popup) { mutableStateOf("") }
    var erro by remember(popup) { mutableStateOf<String?>(null) }

    LaunchedEffect(popup) { shown = true }
    val alpha by animateFloatAsState(if (shown) 1f else 0f, tween(180), label = "popup-alpha")
    val scale by animateFloatAsState(if (shown) 1f else 0.96f, tween(220), label = "popup-scale")

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false),
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(PopupScrim.copy(alpha = PopupScrim.alpha * alpha))
                .tap { onDismiss() },
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .imePadding()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                contentAlignment = Alignment.Center,
            ) {
                BoxWithConstraints(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    val alturaMax = maxHeight
                    Box(
                        Modifier
                            .widthIn(max = 343.dp)
                            .fillMaxWidth()
                            .graphicsLayer {
                                this.alpha = alpha
                                scaleX = scale
                                scaleY = scale
                            },
                    ) {
                        when (popup) {
                            ConfigPopup.NOME -> VPopupShell(
                                title = "Nome completo",
                                desc = "Como seu nome aparece para os parceiros e nos seus serviços.",
                                alturaMax = alturaMax,
                                onBack = onDismiss,
                            ) {
                                VInput(
                                    value = nome,
                                    placeholder = "Informe seu nome completo",
                                    icon = Icons.Outlined.PersonOutline,
                                    onValueChange = { nome = it.take(80) },
                                )
                                VActionRow("Cancelar", "Salvar", onDismiss) {
                                    if (nome.isBlank()) {
                                        erro = "Informe seu nome completo"
                                    } else {
                                        Toast.makeText(ctx, "Nome atualizado", Toast.LENGTH_SHORT).show()
                                        onDismiss()
                                    }
                                }
                                VError(erro)
                            }

                            ConfigPopup.SENHA_RECUPERAR -> VPopupShell(
                                title = "Esqueceu a senha?",
                                desc = "Selecione como receber o código de recuperação e insira o código abaixo.",
                                alturaMax = alturaMax,
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
                                    onValueChange = { codigo = it.filter(Char::isDigit).take(6) },
                                    iconSize = 19.dp,
                                    keyboardType = KeyboardType.Number,
                                )
                                VActionRow("Cancelar", "Continuar", onDismiss) {
                                    if (codigo.isBlank()) {
                                        erro = "Insira o código que você recebeu"
                                    } else {
                                        onPopup(ConfigPopup.SENHA_NOVA)
                                    }
                                }
                                VError(erro)
                            }

                            ConfigPopup.SENHA_NOVA -> VPopupShell(
                                title = "Defina sua nova senha",
                                desc = "Crie uma nova senha segura para continuar acessando sua conta.",
                                alturaMax = alturaMax,
                                onBack = { onPopup(senhaVoltar) },
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
                                Text(
                                    "Use pelo menos 8 caracteres.",
                                    style = FHelper,
                                    color = VColor.PurpleMuted,
                                )
                                VActionRow("Voltar", "Salvar senha", { onPopup(senhaVoltar) }) {
                                    erro = when {
                                        novaSenha.length < 8 -> "A senha precisa ter pelo menos 8 caracteres"
                                        novaSenha != confSenha -> "As senhas não coincidem"
                                        else -> null
                                    }
                                    if (erro == null) onPopup(ConfigPopup.SENHA_OK)
                                }
                                VError(erro)
                            }

                            ConfigPopup.SENHA_OK -> VSuccessCard(
                                title = "Senha alterada com sucesso!",
                                message = "Você já pode acessar sua conta com a nova senha.",
                                button = "Concluir",
                                alturaMax = alturaMax,
                                onClick = onDismiss,
                            )

                            ConfigPopup.EMAIL_CODIGO -> VPopupShell(
                                title = "Código de recuperação",
                                desc = "Receba um código no telefone cadastrado para recuperar seu e-mail.",
                                alturaMax = alturaMax,
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
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
                                    onValueChange = { codigo = it.filter(Char::isDigit).take(6) },
                                    iconSize = 19.dp,
                                    keyboardType = KeyboardType.Number,
                                )
                                VActionRow("Cancelar", "Continuar", onDismiss) {
                                    if (codigo.isBlank()) {
                                        erro = "Insira o código que você recebeu"
                                    } else {
                                        onPopup(ConfigPopup.EMAIL_NOVO)
                                    }
                                }
                                VError(erro)
                            }

                            ConfigPopup.EMAIL_NOVO -> VPopupShell(
                                title = "Novo e-mail",
                                desc = "Insira e confirme o novo e-mail que deseja usar na sua conta.",
                                alturaMax = alturaMax,
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
                                    onValueChange = { novoEmail = it.trim() },
                                    iconSize = 19.dp,
                                    keyboardType = KeyboardType.Email,
                                )
                                VInput(
                                    value = confEmail,
                                    placeholder = "Confirme seu novo e-mail",
                                    icon = Icons.Outlined.MailOutline,
                                    onValueChange = { confEmail = it.trim() },
                                    iconSize = 19.dp,
                                    keyboardType = KeyboardType.Email,
                                )
                                VActionRow("Cancelar", "Confirmar", onDismiss) {
                                    erro = when {
                                        !emailValido(novoEmail) -> "Informe um e-mail válido"
                                        novoEmail != confEmail -> "Os e-mails não coincidem"
                                        else -> null
                                    }
                                    if (erro == null) onPopup(ConfigPopup.EMAIL_OK)
                                }
                                VError(erro)
                            }

                            ConfigPopup.EMAIL_OK -> VSuccessCard(
                                title = "E-mail atualizado!",
                                message = "Seu novo e-mail já está ativo. Use ele para entrar na conta.",
                                button = "Concluir",
                                alturaMax = alturaMax,
                                onClick = onDismiss,
                            )

                            ConfigPopup.TELEFONE_CODIGO -> VPopupShell(
                                title = "Confirme seu telefone",
                                desc = "Enviaremos um código para o número cadastrado antes de trocar seu telefone.",
                                alturaMax = alturaMax,
                                onBack = onDismiss,
                                footer = {
                                    VFooter(
                                        title = "Não tem acesso a esse número?",
                                        link = "Fale com o suporte",
                                        onClick = { onPopup(ConfigPopup.SUPORTE) },
                                    )
                                },
                            ) {
                                VToggle(listOf("SMS", "WhatsApp"), canal) { canal = it }
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    VInput(
                                        value = userPhone.ifBlank { "Nenhum telefone cadastrado" },
                                        placeholder = "",
                                        icon = Icons.Outlined.Smartphone,
                                        readOnly = true,
                                        iconSize = 18.dp,
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
                                    placeholder = "Insira o código recebido",
                                    icon = Icons.Outlined.Lock,
                                    onValueChange = { codigo = it.filter(Char::isDigit).take(6) },
                                    iconSize = 19.dp,
                                    keyboardType = KeyboardType.Number,
                                )
                                VActionRow("Cancelar", "Continuar", onDismiss) {
                                    if (codigo.isBlank()) {
                                        erro = "Insira o código que você recebeu"
                                    } else {
                                        onPopup(ConfigPopup.TELEFONE_NOVO)
                                    }
                                }
                                VError(erro)
                            }

                            ConfigPopup.TELEFONE_NOVO -> VPopupShell(
                                title = "Novo telefone",
                                desc = "Informe o novo número com DDD. Ele será usado para contato e recuperação.",
                                alturaMax = alturaMax,
                                onBack = { onPopup(ConfigPopup.TELEFONE_CODIGO) },
                            ) {
                                VInput(
                                    value = novoTelefone,
                                    placeholder = "(11) 99999-9999",
                                    icon = Icons.Outlined.Smartphone,
                                    onValueChange = { novoTelefone = it },
                                    iconSize = 18.dp,
                                    keyboardType = KeyboardType.Phone,
                                )
                                Text(
                                    "Enviaremos um código de confirmação para o novo número.",
                                    style = FHelper,
                                    color = VColor.PurpleMuted,
                                )
                                VActionRow("Cancelar", "Salvar", onDismiss) {
                                    if (novoTelefone.count(Char::isDigit) < 10) {
                                        erro = "Informe um telefone com DDD"
                                    } else {
                                        onPopup(ConfigPopup.TELEFONE_OK)
                                    }
                                }
                                VError(erro)
                            }

                            ConfigPopup.TELEFONE_OK -> VSuccessCard(
                                title = "Telefone atualizado!",
                                message = "Seu novo número já está cadastrado na conta.",
                                button = "Concluir",
                                alturaMax = alturaMax,
                                onClick = onDismiss,
                            )

                            ConfigPopup.SUPORTE -> VPopupShell(
                                title = "Fale com o suporte",
                                desc = "Perdeu acesso ao telefone cadastrado? Nossa equipe pode ajudar você por e-mail, sem usar o número antigo.",
                                alturaMax = alturaMax,
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
    }
}

private fun emailValido(valor: String): Boolean =
    Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]{2,}$").matches(valor.trim())

@Composable
private fun VPopupShell(
    title: String,
    desc: String,
    onBack: () -> Unit,
    alturaMax: Dp,
    spacing: Dp = 20.dp,
    footer: (@Composable () -> Unit)? = null,
    body: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(24.dp)
    Column(
        Modifier
            .fillMaxWidth()
            .heightIn(max = alturaMax)
            .shadow(20.dp, shape, ambientColor = PopupGlow, spotColor = PopupGlow)
            .clip(shape)
            .background(VColor.White)
            .border(1.dp, VColor.PurpleLineSoft, shape)
            .tap {}
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
    ) {
        VBackButton(onBack)
        Spacer(Modifier.height(16.dp))
        Text(title, style = FTitle, color = VColor.PurpleDarkest)
        Spacer(Modifier.height(8.dp))
        Text(desc, style = FDesc, color = VColor.PurpleMuted)
        Spacer(Modifier.height(20.dp))
        Column(verticalArrangement = Arrangement.spacedBy(spacing), content = body)
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
            .background(VColor.BgChip)
            .tap(onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar", tint = VColor.PurpleDarkest, modifier = Modifier.size(18.dp))
    }
}

@Composable
private fun VError(mensagem: String?) {
    if (mensagem.isNullOrBlank()) return
    Text(mensagem, style = FError, color = VColor.Red)
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
                textStyle = FField.copy(color = if (readOnly) VColor.PurpleMuted else VColor.PurpleDarkest),
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
    Box(Modifier.fillMaxWidth().height(20.dp), contentAlignment = Alignment.CenterEnd) {
        Text(label, style = FLink, color = VColor.PurpleDarkest, modifier = Modifier.tap(onClick))
    }
}

@Composable
private fun VActionRow(cancel: String, confirm: String, onCancel: () -> Unit, onConfirm: () -> Unit) {
    val shape = RoundedCornerShape(12.dp)
    Row(Modifier.fillMaxWidth().height(48.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(
            Modifier
                .weight(1f)
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
                .weight(1f)
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
private fun VSuccessCard(
    title: String,
    message: String,
    button: String,
    alturaMax: Dp,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(24.dp)
    val buttonShape = RoundedCornerShape(12.dp)
    Column(
        Modifier
            .fillMaxWidth()
            .heightIn(max = alturaMax)
            .shadow(20.dp, shape, ambientColor = PopupGlow, spotColor = PopupGlow)
            .clip(shape)
            .background(VColor.White)
            .border(1.dp, VColor.PurpleLineSoft, shape)
            .tap {}
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Box(
            Modifier.size(64.dp).clip(CircleShape).background(VColor.PurpleTint),
            contentAlignment = Alignment.Center,
        ) {
            Icon(PopupCheck, null, tint = VColor.Purple, modifier = Modifier.size(30.dp))
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
