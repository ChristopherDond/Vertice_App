package com.vertice.app.ui.screens.figma

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vertice.app.core.di.ServiceLocator
import com.vertice.app.core.result.onErr
import com.vertice.app.core.result.onOk
import com.vertice.app.domain.model.PaymentMethod
import com.vertice.app.domain.model.ServiceOffer
import com.vertice.app.ui.components.PressAccent
import com.vertice.app.ui.components.PressSoft
import com.vertice.app.ui.components.PressWhite
import com.vertice.app.ui.components.moneyInput
import com.vertice.app.ui.components.moneyToDouble
import com.vertice.app.ui.components.vClick
import com.vertice.app.ui.design.Inter
import com.vertice.app.ui.design.Roboto
import com.vertice.app.ui.design.VColor
import com.vertice.app.ui.design.VText
import com.vertice.app.ui.design.vs
import kotlinx.coroutines.launch

/** Dados de exemplo do plano (o preço é do próprio Vertice; nenhum número de mercado). */
object ProMock {
    const val planId = "plan_pro_monthly"
    const val price = "19,99"
    val benefits: List<Triple<ImageVector, String, String>> = listOf(
        Triple(Icons.Filled.AllInclusive, "Matches ilimitados", "Sem limite de conexões por mês"),
        Triple(Icons.Filled.Bolt, "Prioridade nos resultados", "Seu perfil aparece antes dos demais"),
        Triple(Icons.Filled.Groups, "Acesso antecipado", "Veja novos parceiros antes de todos"),
        Triple(Icons.Filled.Timeline, "Relatórios de desempenho", "Acompanhe visualizações e conversões"),
        Triple(Icons.Filled.WorkspacePremium, "Selo Pro no perfil", "Mais credibilidade e confiança"),
    )
}

private val ProCard = Color(0xFFE8D9FF)
private val ProCardLine = Color(0xFFD5BAFC)
private val ErrorRed = Color(0xFFD93025)
private val HeaderGrad = Brush.linearGradient(listOf(Color(0xFF8B45F5), Color(0xFFE7589F)))
private val ButtonGrad = Brush.horizontalGradient(listOf(Color(0xFF883FF4), Color(0xFFEC4899)))
private val DisabledText = Color(0xFF8E8A99)

// ───────────────────────────── Vertice Pro ─────────────────────────────

/** [onSubscribed] recebe (valor da próxima cobrança, data da próxima cobrança). */
@Composable
fun FigmaPro(onBack: () -> Unit = {}, onSubscribed: (String, String) -> Unit = { _, _ -> }) {
    BackHandler(enabled = true) { onBack() }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    fun assinar() {
        if (loading) return
        loading = true
        error = null
        scope.launch {
            val result = ServiceLocator.subscriptionRepository.subscribe(ProMock.planId, PaymentMethod.CREDIT_CARD)
            loading = false
            result.onOk { onSubscribed(it.priceLabel, it.nextChargeDate) }.onErr { error = it.message }
        }
    }

    Box(Modifier.fillMaxSize().background(VColor.Bg)) {
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(HeaderGrad)
                    .statusBarsPadding()
                    .padding(bottom = 28.dp),
            ) {
                BackCircle(onBack, Modifier.padding(start = 12.dp, top = 8.dp), dark = false)
                Column(Modifier.fillMaxWidth().padding(top = 28.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        Modifier.size(52.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.22f)),
                        contentAlignment = Alignment.Center,
                    ) { Icon(Icons.Filled.WorkspacePremium, null, Modifier.size(30.dp), tint = Color.White) }
                    Spacer(Modifier.height(10.dp))
                    VText("Vertice Pro", vs(22f, 800, 25.8f, family = Roboto), Color.White)
                    VText("Desbloqueie todo o potencial da plataforma", vs(11f, 400, 13f, family = Roboto), Color.White.copy(alpha = 0.9f))
                    Spacer(Modifier.height(12.dp))
                    Box(
                        Modifier.clip(RoundedCornerShape(50)).background(Color.White.copy(alpha = 0.22f)).padding(horizontal = 14.dp, vertical = 6.dp),
                    ) { VText("✦  Mais alcance para o seu negócio", vs(11f, 600, 13f, family = Roboto), Color.White) }
                }
            }

            Row(
                Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(ProCard)
                    .border(1.dp, ProCardLine, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(Modifier.weight(1f)) {
                    VText("PLANO MENSAL", vs(10f, 600, 12f, family = Roboto), VColor.PurpleInk)
                    Row(verticalAlignment = Alignment.Bottom) {
                        VText("R$ ", vs(12f, 700, 16f, family = Roboto), VColor.PurpleDeep, Modifier.padding(bottom = 6.dp))
                        VText(ProMock.price, vs(34f, 800, 38f, family = Roboto), VColor.PurpleDeep)
                    }
                    VText("por mês, cancele quando quiser", vs(11f, 400, 13f, family = Roboto), VColor.PurpleInk)
                }
                Box(
                    Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(VColor.PinkSoft.copy(alpha = 0.5f))
                        .border(1.dp, VColor.Pink, RoundedCornerShape(8.dp))
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    contentAlignment = Alignment.Center,
                ) { VText("SEM\nFIDELIDADE", vs(11f, 800, 13f, family = Roboto), VColor.Pink, align = TextAlign.Center) }
            }

            VText(
                "O QUE VOCÊ GANHA",
                vs(10f, 600, 12f, family = Roboto),
                VColor.PurpleInk,
                Modifier.padding(start = 20.dp, top = 22.dp, bottom = 10.dp),
            )
            Column(Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ProMock.benefits.forEach { (icon, title, subtitle) -> BenefitRow(icon, title, subtitle) }
            }

            Row(
                Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Icons.Filled.Lock, null, Modifier.size(14.dp), tint = VColor.PurpleInk)
                Spacer(Modifier.width(10.dp))
                VText(
                    "Cobrança recorrente mensal. Sem fidelidade. Você pode cancelar a qualquer momento.",
                    vs(10f, 400, 13f, family = Roboto),
                    VColor.PurpleInk,
                )
            }
            Spacer(Modifier.height(150.dp))
        }

        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(VColor.Bg)
                .navigationBarsPadding()
                .padding(horizontal = 16.dp)
                .padding(top = 10.dp, bottom = 14.dp),
        ) {
            error?.let {
                VText(it, vs(12f, 600, 14.5f, family = Inter), ErrorRed, Modifier.fillMaxWidth().padding(bottom = 8.dp), align = TextAlign.Center)
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(ButtonGrad)
                    .vClick(PressWhite, enabled = !loading) { assinar() },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (loading) {
                    CircularProgressIndicator(Modifier.size(22.dp), color = Color.White, strokeWidth = 2.5.dp)
                } else {
                    Icon(Icons.Filled.WorkspacePremium, null, Modifier.size(20.dp), tint = Color.White)
                    Spacer(Modifier.width(8.dp))
                    VText("Assinar por R$ ${ProMock.price}/mês", vs(15f, 800, 17.6f, family = Roboto), Color.White)
                }
            }
        }
    }
}

@Composable
private fun BenefitRow(icon: ImageVector, title: String, subtitle: String) {
    Row(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Color.White).padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier.size(34.dp).clip(RoundedCornerShape(10.dp)).background(VColor.PurpleLine),
            contentAlignment = Alignment.Center,
        ) { Icon(icon, null, Modifier.size(18.dp), tint = VColor.PurpleSoft) }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            VText(title, vs(12f, 700, 14.5f, family = Roboto), VColor.Ink)
            VText(subtitle, vs(10f, 400, 12.5f, family = Roboto), VColor.PurpleMuted)
        }
        Icon(Icons.Filled.CheckCircle, "Incluído", Modifier.size(18.dp), tint = VColor.Green)
    }
}

// ───────────────────────── Confirmação do Pro ─────────────────────────

@Composable
fun FigmaProConfirmacao(priceLabel: String, nextCharge: String, onDone: () -> Unit = {}) {
    BackHandler(enabled = true) { onDone() }
    Box(Modifier.fillMaxSize().background(VColor.Bg).statusBarsPadding().navigationBarsPadding()) {
        Column(
            Modifier.fillMaxSize().padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                Modifier.size(86.dp).clip(CircleShape).background(ProCard).border(2.dp, VColor.PurpleLilac2, CircleShape),
                contentAlignment = Alignment.Center,
            ) { Icon(Icons.Filled.WorkspacePremium, null, Modifier.size(44.dp), tint = VColor.PurpleSoft) }
            Spacer(Modifier.height(24.dp))
            VText("Bem-vindo ao Pro!", vs(24f, 800, 28f, family = Roboto), VColor.Ink)
            Spacer(Modifier.height(8.dp))
            VText(
                "Seu plano está ativo. Aproveite todos os benefícios do Vertice Pro.",
                vs(12f, 400, 16f, family = Roboto),
                VColor.PurpleMuted,
                Modifier.padding(horizontal = 24.dp),
                align = TextAlign.Center,
            )
            Spacer(Modifier.height(26.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(ProCard)
                    .border(1.dp, ProCardLine, RoundedCornerShape(12.dp))
                    .padding(vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                VText("Próxima cobrança", vs(11f, 400, 13f, family = Roboto), VColor.Ink)
                Spacer(Modifier.height(4.dp))
                VText("$priceLabel em $nextCharge", vs(16f, 800, 19f, family = Roboto), VColor.PurpleDeep)
            }
            Spacer(Modifier.height(24.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(VColor.Purple)
                    .vClick(PressWhite) { onDone() },
                contentAlignment = Alignment.Center,
            ) { VText("Começar a usar", vs(15f, 800, 17.6f, family = Roboto), Color.White) }
        }
    }
}

// ─────────────────────────── Oferecer serviços ───────────────────────────

private val Availability = listOf("Dias úteis", "Fins de semana", "Todos os dias", "Horário combinado")
private const val MIN_DESCRIPTION = 20

@Composable
fun FigmaOferecerServico(onBack: () -> Unit = {}, onPublished: () -> Unit = {}) {
    BackHandler(enabled = true) { onBack() }
    var category by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var availability by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priceError by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var done by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val ready = category.isNotBlank() && title.trim().length >= 3 && description.trim().length >= MIN_DESCRIPTION

    fun publicar() {
        if (!ready || loading) return
        val value = price.moneyToDouble()
        priceError = value == null || value <= 0.0
        if (priceError) return
        loading = true
        error = null
        scope.launch {
            val result = ServiceLocator.matchRepository.publishOffer(
                ServiceOffer(category, title.trim(), description.trim(), value ?: 0.0, city.trim(), availability),
            )
            loading = false
            result.onOk { done = true }.onErr { error = it.message }
        }
    }

    Box(Modifier.fillMaxSize().background(VColor.Bg)) {
        Column(
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
        ) {
            Box(Modifier.fillMaxWidth().padding(top = 8.dp)) {
                BackCircle(onBack, Modifier.align(Alignment.CenterStart), dark = true)
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    VText("Oferecer serviços", vs(20f, 800, 23.5f, family = Roboto), VColor.Ink)
                    VText("Alcance novos parceiros na sua região", vs(11f, 400, 13f, family = Roboto), VColor.PurpleMuted)
                }
            }
            Spacer(Modifier.height(20.dp))

            OsLabel("Categoria*")
            OsDropdown(category, "Selecionar categoria...", ServiceCatalog.groups.keys.toList()) { category = it }

            OsLabel("Título de serviço*")
            OsInput(title, { title = it.take(60) }, "Ex: Costureira sob medida e ajustes", capitalization = KeyboardCapitalization.Sentences)

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(Modifier.weight(1f)) {
                    OsLabel("Preço (R$)")
                    OsInput(price, { price = it.moneyInput(); priceError = false }, "Ex: 80", keyboardType = KeyboardType.Decimal, hasError = priceError)
                    if (priceError) VText("Informe o preço.", vs(11f, 400, 13f, family = Inter), ErrorRed, Modifier.padding(top = 4.dp))
                }
                Column(Modifier.weight(1f)) {
                    OsLabel("Cidade")
                    OsInput(city, { city = it.filter { c -> c.isLetter() || c in " ,-'." }.take(60) }, "São Paulo, SP", capitalization = KeyboardCapitalization.Words)
                }
            }

            OsLabel("Disponibilidade")
            OsDropdown(availability, "Disponibilidade...", Availability) { availability = it }

            OsLabel("Descrição do serviço*")
            OsInput(
                description,
                { description = it.take(500) },
                "Explique o que você faz, materiais, prazos e diferenciais...",
                singleLine = false,
                fieldHeight = 110.dp,
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Default,
            )
            VText(
                "${description.trim().length}/500 · mínimo de $MIN_DESCRIPTION caracteres",
                vs(10f, 400, 12f, family = Inter),
                VColor.PurpleMuted,
                Modifier.padding(top = 4.dp),
            )
            Spacer(Modifier.height(24.dp))
        }

        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color(0xFFF0EAFB))
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            error?.let {
                VText(it, vs(12f, 600, 14.5f, family = Inter), ErrorRed, Modifier.padding(bottom = 8.dp), align = TextAlign.Center)
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (ready) VColor.Purple else Color.Transparent)
                    .vClick(PressWhite, enabled = ready && !loading) { publicar() },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (loading) {
                    CircularProgressIndicator(Modifier.size(22.dp), color = Color.White, strokeWidth = 2.5.dp)
                } else {
                    Icon(Icons.Filled.CheckCircle, null, Modifier.size(16.dp), tint = if (ready) Color.White else DisabledText)
                    Spacer(Modifier.width(8.dp))
                    VText("Publicar serviço", vs(14f, 700, 16.5f, family = Roboto), if (ready) Color.White else DisabledText)
                }
            }
            if (!ready) {
                VText(
                    "Preencha categoria, título e descrição (*)",
                    vs(10f, 400, 12f, family = Roboto),
                    VColor.PurpleMuted,
                    Modifier.padding(top = 6.dp),
                )
            }
        }

        if (done) {
            Box(
                Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)).clickable(enabled = true) {},
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    Modifier.padding(32.dp).clip(RoundedCornerShape(20.dp)).background(Color.White).padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(Icons.Filled.CheckCircle, null, Modifier.size(48.dp), tint = VColor.Green)
                    Spacer(Modifier.height(12.dp))
                    VText("Serviço publicado!", vs(18f, 800, 21f, family = Roboto), VColor.Ink)
                    Spacer(Modifier.height(6.dp))
                    VText(
                        "Seu anúncio já pode aparecer para parceiros da sua região.",
                        vs(12f, 400, 16f, family = Roboto),
                        VColor.PurpleMuted,
                        align = TextAlign.Center,
                    )
                    Spacer(Modifier.height(18.dp))
                    Box(
                        Modifier.fillMaxWidth().height(44.dp).clip(RoundedCornerShape(12.dp)).background(VColor.Purple).vClick(PressWhite) { onPublished() },
                        contentAlignment = Alignment.Center,
                    ) { VText("Ok", vs(14f, 700, 16.5f, family = Roboto), Color.White) }
                }
            }
        }
    }
}

@Composable
private fun OsLabel(text: String) {
    VText(text, vs(12f, 600, 14.5f, family = Inter), VColor.PurpleInk, Modifier.padding(top = 14.dp, bottom = 6.dp))
}

@Composable
private fun OsInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    singleLine: Boolean = true,
    fieldHeight: Dp = 44.dp,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    imeAction: ImeAction = ImeAction.Next,
    hasError: Boolean = false,
) {
    val interaction = remember { MutableInteractionSource() }
    val focused by interaction.collectIsFocusedAsState()
    val shape = RoundedCornerShape(10.dp)
    Box(
        Modifier
            .fillMaxWidth()
            .height(fieldHeight)
            .clip(shape)
            .background(Color.White.copy(alpha = 0.7f))
            .border(if (focused || hasError) 2.dp else 1.dp, if (hasError) ErrorRed else VColor.InputLine, shape)
            .padding(horizontal = 12.dp, vertical = if (singleLine) 0.dp else 12.dp),
        contentAlignment = if (singleLine) Alignment.CenterStart else Alignment.TopStart,
    ) {
        if (value.isEmpty()) VText(placeholder, vs(13f, 400, 16f, family = Inter), VColor.Gray)
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            modifier = Modifier.fillMaxWidth(),
            textStyle = vs(13f, 400, 16f, family = Inter).copy(color = VColor.Ink),
            cursorBrush = SolidColor(VColor.PurpleMid),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, capitalization = capitalization, imeAction = imeAction),
            interactionSource = interaction,
        )
    }
}

@Composable
private fun OsDropdown(value: String, placeholder: String, options: List<String>, onPick: (String) -> Unit) {
    var open by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(10.dp)
    Box {
        Row(
            Modifier
                .fillMaxWidth()
                .height(44.dp)
                .clip(shape)
                .background(Color.White.copy(alpha = 0.7f))
                .border(BorderStroke(1.dp, VColor.InputLine), shape)
                .vClick(PressSoft) { open = true }
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            VText(
                value.ifBlank { placeholder },
                vs(13f, 400, 16f, family = Inter),
                if (value.isBlank()) VColor.Gray else VColor.Ink,
                Modifier.weight(1f),
                maxLines = 1,
            )
            Icon(Icons.Filled.KeyboardArrowDown, null, Modifier.size(20.dp), tint = VColor.PurpleMuted)
        }
        DropdownMenu(expanded = open, onDismissRequest = { open = false }) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text(option) }, onClick = { onPick(option); open = false })
            }
        }
    }
}

@Composable
private fun BackCircle(onBack: () -> Unit, modifier: Modifier = Modifier, dark: Boolean) {
    Box(
        modifier
            .size(40.dp)
            .clip(if (dark) RoundedCornerShape(10.dp) else CircleShape)
            .background(if (dark) Color.White else Color.White.copy(alpha = 0.22f))
            .vClick(PressAccent) { onBack() }
            .semantics { contentDescription = "Voltar" },
        contentAlignment = Alignment.Center,
    ) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, Modifier.size(22.dp), tint = if (dark) VColor.Ink else Color.White)
    }
}
