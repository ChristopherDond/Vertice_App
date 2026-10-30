package com.vertice.app.ui.screens.figma

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.SouthWest
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.vertice.app.core.di.ServiceLocator
import com.vertice.app.core.result.onErr
import com.vertice.app.core.result.onOk
import com.vertice.app.domain.model.AutoRenewMethod
import com.vertice.app.domain.model.Card
import com.vertice.app.domain.model.PaymentMethod
import com.vertice.app.domain.model.Subscription
import com.vertice.app.domain.model.SubscriptionStatus
import com.vertice.app.domain.validation.CurrencyFormatter
import com.vertice.app.ui.components.PressAccent
import com.vertice.app.ui.components.PressSoft
import com.vertice.app.ui.components.PressWhite
import com.vertice.app.ui.components.vClick
import com.vertice.app.ui.design.Roboto
import com.vertice.app.ui.design.VCard
import com.vertice.app.ui.design.VColor
import com.vertice.app.ui.design.VText
import com.vertice.app.ui.design.vs
import com.vertice.app.ui.icons.IcoBiCreditCard
import com.vertice.app.ui.icons.IcoIcOutlinePix
import kotlinx.coroutines.launch

/**
 * Movimentações de exemplo. O modelo `Transaction` do domínio hoje só cobre recebimentos de
 * serviço (sem sentido de entrada/saída nem forma de pagamento), então a lista do Figma
 * fica aqui até o backend expor esses campos.
 */
object FinanceiroMock {
    data class Movimento(
        val titulo: String,
        val contraparte: String,
        val data: String,
        val metodo: String,
        val valor: Double,
    ) {
        val entrada: Boolean get() = valor > 0
    }

    val movimentos = listOf(
        Movimento("Pintura residencial", "Profissional: João Lima", "23 out", "PIX", -350.0),
        Movimento("Reparo hidráulico", "Cliente: Carlos Lima", "20 out", "PIX", 150.0),
        Movimento("Pintura residencial", "Cliente: Cañas Lima", "18 out", "PIX", 400.0),
    )
}

private enum class MetodoCobranca(val titulo: String, val subtitulo: String, val icone: ImageVector) {
    PIX("PIX", "Instantâneo", IcoIcOutlinePix),
    CARTAO("Cartão", "Crédito/Débito", IcoBiCreditCard),
    BOLETO("Boleto", "Pagamento por boleto", Icons.Filled.Receipt),
}

private val Roxo = VColor.Purple
private val LinhaCard = VColor.PurpleLine
private val VerdeTexto = VColor.Green
private val VermelhoTexto = Color(0xFFD93025)

@Composable
fun FigmaFinanceiro(
    onBack: () -> Unit = {},
    onPix: () -> Unit = {},
    onPagar: () -> Unit = {},
    onTransferir: () -> Unit = {},
    onVerExtratoCompleto: () -> Unit = {},
    onAdicionarCartao: () -> Unit = {},
) {
    BackHandler(enabled = true) { onBack() }
    val scope = rememberCoroutineScope()

    var saldo by remember { mutableStateOf<Double?>(null) }
    var saldoVisivel by remember { mutableStateOf(false) }
    var cartoes by remember { mutableStateOf<List<Card>>(emptyList()) }
    var assinatura by remember { mutableStateOf<Subscription?>(null) }
    var cartaoEscolhido by remember { mutableStateOf<String?>(null) }
    var metodo by remember { mutableStateOf(MetodoCobranca.PIX) }
    var salvando by remember { mutableStateOf(false) }
    var erro by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val repo = ServiceLocator.financeRepository
        repo.balance().onOk { saldo = it.available }.onErr { erro = it.message }
        repo.cards().onOk { lista ->
            cartoes = lista
            cartaoEscolhido = (lista.firstOrNull { it.isPrimary } ?: lista.firstOrNull())?.id
        }.onErr { erro = it.message }
        repo.subscription().onOk { assinatura = it }.onErr { erro = it.message }
    }

    fun renovarCom(forma: PaymentMethod, ativar: Boolean) {
        if (salvando) return
        salvando = true
        erro = null
        scope.launch {
            ServiceLocator.financeRepository.setAutoRenew(forma, ativar)
                .onOk { assinatura = it }
                .onErr { erro = it.message }
            salvando = false
        }
    }

    val pixAutomatico = assinatura?.let { it.autoRenew && it.autoRenewMethod == AutoRenewMethod.PIX } == true
    val cartaoAutomatico = assinatura?.let { it.autoRenew && it.autoRenewMethod == AutoRenewMethod.CREDIT_CARD } == true

    Column(
        Modifier
            .fillMaxSize()
            .background(VColor.Bg)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .padding(horizontal = 16.dp),
    ) {
        Row(Modifier.padding(top = 12.dp, bottom = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .vClick(PressAccent) { onBack() }
                    .semantics { contentDescription = "Voltar" },
                contentAlignment = Alignment.Center,
            ) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, Modifier.size(22.dp), tint = VColor.Ink) }
            Spacer(Modifier.width(14.dp))
            VText("Extrato Financeiro", vs(20f, 800, 23.5f, family = Roboto), VColor.Ink)
        }

        erro?.let {
            VText(it, vs(12f, 600, 14.5f, family = Roboto), VermelhoTexto, Modifier.padding(bottom = 10.dp))
        }

        // ── Saldo ──
        VCard(Modifier.fillMaxWidth(), padding = PaddingValues(16.dp)) {
            VText("Saldo", vs(14f, 700, 16.5f, family = Roboto), VColor.Ink)
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                val texto = when {
                    saldo == null -> "R$ ••••••"
                    saldoVisivel -> CurrencyFormatter.format(saldo ?: 0.0)
                    else -> "R$ ••••••"
                }
                VText(texto, vs(24f, 900, 28f, family = Roboto), VColor.Ink, Modifier.weight(1f))
                Icon(
                    if (saldoVisivel) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    null,
                    Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .vClick(PressSoft) { saldoVisivel = !saldoVisivel }
                        .padding(5.dp)
                        .semantics { contentDescription = if (saldoVisivel) "Ocultar saldo" else "Mostrar saldo" },
                    tint = VColor.Ink,
                )
            }
            VText("Conta digital", vs(11f, 400, 13f, family = Roboto), VColor.Gray)
            Spacer(Modifier.height(14.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AcaoPilula("PIX", Icons.Filled.QrCode, onPix)
                AcaoPilula("Pagar", Icons.Filled.Payments, onPagar)
                AcaoPilula("Transferir", Icons.Filled.SwapHoriz, onTransferir)
            }
        }

        // ── Últimas movimentações ──
        Spacer(Modifier.height(18.dp))
        VCard(Modifier.fillMaxWidth(), padding = PaddingValues(16.dp)) {
            VText("Últimas movimentações", vs(14f, 700, 16.5f, family = Roboto), VColor.Ink)
            Spacer(Modifier.height(10.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                FinanceiroMock.movimentos.forEach { LinhaMovimento(it) }
            }
            Spacer(Modifier.height(12.dp))
            Row(
                Modifier
                    .align(Alignment.End)
                    .clip(RoundedCornerShape(8.dp))
                    .vClick(PressAccent) { onVerExtratoCompleto() }
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                VText("Ver extrato completo", vs(11f, 600, 13f, family = Roboto), Roxo)
                Spacer(Modifier.width(4.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, null, Modifier.size(14.dp), tint = Roxo)
            }
        }

        // ── Cobrança Vertice Pro ──
        Spacer(Modifier.height(18.dp))
        VCard(Modifier.fillMaxWidth(), padding = PaddingValues(16.dp)) {
            CabecalhoComSelo("Cobrança Vertice Pro", assinatura?.status)
            VText("Escolha o método de pagamento", vs(11f, 400, 13f, family = Roboto), VColor.PurpleMuted)
            Spacer(Modifier.height(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                MetodoCobranca.entries.forEach { m ->
                    LinhaOpcao(
                        icone = m.icone,
                        titulo = m.titulo,
                        subtitulo = m.subtitulo,
                        selecionado = metodo == m,
                        onClick = { metodo = m },
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            VText("Cartões cadastrados", vs(12f, 700, 14.5f, family = Roboto), VColor.Ink)
            Spacer(Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                cartoes.forEach { c ->
                    LinhaOpcao(
                        icone = IcoBiCreditCard,
                        titulo = "${c.brand.label} ${c.maskedNumber.replace('\u2022', '*')}",
                        subtitulo = c.label,
                        selecionado = cartaoEscolhido == c.id,
                        onClick = { cartaoEscolhido = c.id },
                        iconeNeutro = true,
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(VColor.PurpleWash)
                    .vClick(PressAccent) { onAdicionarCartao() }
                    .padding(horizontal = 14.dp)
                    .semantics { contentDescription = "Adicionar cartão" },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                VText("Adicionar cartões", vs(12f, 600, 14.5f, family = Roboto), VColor.PurpleDeep, Modifier.weight(1f))
                Icon(Icons.Filled.Add, null, Modifier.size(18.dp), tint = VColor.PurpleDeep)
            }

            Spacer(Modifier.height(16.dp))
            Box(Modifier.fillMaxWidth().height(1.dp).background(LinhaCard))
            Spacer(Modifier.height(16.dp))

            // ── Pagamento automático ──
            CabecalhoComSelo("Pagamento automático", assinatura?.status)
            VText("Escolha como deseja renovar sua assinatura", vs(11f, 400, 13f, family = Roboto), VColor.PurpleMuted)
            Spacer(Modifier.height(12.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, LinhaCard, RoundedCornerShape(12.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .vClick(PressSoft, enabled = !salvando && !pixAutomatico) { renovarCom(PaymentMethod.PIX, true) },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconeQuadrado(Icons.Filled.QrCode)
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        VText("Pix automático", vs(13f, 700, 15.5f, family = Roboto), VColor.Ink)
                        VText("Renovação via autorização bancária", vs(10f, 400, 12.5f, family = Roboto), VColor.PurpleMuted)
                    }
                    if (pixAutomatico) Selo("Ativo", VColor.GreenBg, VerdeTexto)
                }
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    IconeQuadrado(IcoBiCreditCard)
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        VText("Cartão automático", vs(13f, 700, 15.5f, family = Roboto), VColor.Ink)
                        VText("Cobrança recorrente no cartão escolhido", vs(10f, 400, 12.5f, family = Roboto), VColor.PurpleMuted)
                    }
                    Switch(
                        checked = cartaoAutomatico,
                        enabled = assinatura != null && !salvando,
                        onCheckedChange = { ligar ->
                            // Pix e cartão são excludentes: desligar o cartão volta para o Pix automático.
                            if (ligar) renovarCom(PaymentMethod.CREDIT_CARD, true) else renovarCom(PaymentMethod.PIX, true)
                        },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = Roxo,
                            checkedThumbColor = Color.White,
                            uncheckedTrackColor = VColor.Gray4,
                            uncheckedThumbColor = Color.White,
                            uncheckedBorderColor = Color.Transparent,
                        ),
                    )
                }
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun androidx.compose.foundation.layout.RowScope.AcaoPilula(texto: String, icone: ImageVector, onClick: () -> Unit) {
    Row(
        Modifier
            .weight(1f)
            .height(34.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(Roxo)
            .vClick(PressWhite) { onClick() }
            .semantics { contentDescription = texto },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icone, null, Modifier.size(14.dp), tint = Color.White)
        Spacer(Modifier.width(6.dp))
        VText(texto, vs(12f, 700, 14f, family = Roboto), Color.White)
    }
}

@Composable
private fun LinhaMovimento(m: FinanceiroMock.Movimento) {
    val cor = if (m.entrada) VerdeTexto else VermelhoTexto
    val fundo = if (m.entrada) VColor.GreenBg else Color(0xFFFFE3E3)
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(VColor.BgAlt)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(fundo),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                if (m.entrada) Icons.Filled.NorthEast else Icons.Filled.SouthWest,
                if (m.entrada) "Entrada" else "Saída",
                Modifier.size(16.dp),
                tint = cor,
            )
        }
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            VText(m.titulo, vs(12f, 700, 14.5f, family = Roboto), VColor.Ink)
            VText(m.contraparte, vs(10f, 400, 12f, family = Roboto), VColor.PurpleMuted)
            VText("${m.data} · ${m.metodo}", vs(10f, 400, 12f, family = Roboto), VColor.Gray)
        }
        val sinal = if (m.entrada) "+ " else "- "
        VText(sinal + CurrencyFormatter.format(kotlin.math.abs(m.valor)), vs(12f, 700, 14.5f, family = Roboto), cor)
    }
}

@Composable
private fun CabecalhoComSelo(titulo: String, status: SubscriptionStatus?) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        VText(titulo, vs(14f, 700, 16.5f, family = Roboto), VColor.Ink, Modifier.weight(1f))
        when (status) {
            null -> Unit
            SubscriptionStatus.UP_TO_DATE -> Selo("Em dia", VColor.GreenBg, VerdeTexto)
            SubscriptionStatus.PENDING -> Selo("Pendente", VColor.GoldBg, Color(0xFF8A6A00))
            SubscriptionStatus.OVERDUE -> Selo("Em atraso", Color(0xFFFFE3E3), VermelhoTexto)
            SubscriptionStatus.CANCELED -> Selo("Cancelada", VColor.Gray5, VColor.PurpleMuted)
        }
    }
    Spacer(Modifier.height(2.dp))
}

@Composable
private fun Selo(texto: String, fundo: Color, cor: Color) {
    Box(
        Modifier.clip(RoundedCornerShape(100.dp)).background(fundo).padding(horizontal = 8.dp, vertical = 3.dp),
        contentAlignment = Alignment.Center,
    ) { VText(texto, vs(10f, 700, 12f, family = Roboto), cor) }
}

@Composable
private fun IconeQuadrado(icone: ImageVector, neutro: Boolean = false) {
    Box(
        Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (neutro) Color.White else Roxo),
        contentAlignment = Alignment.Center,
    ) { Icon(icone, null, Modifier.size(18.dp), tint = if (neutro) VColor.Ink else Color.White) }
}

@Composable
private fun LinhaOpcao(
    icone: ImageVector,
    titulo: String,
    subtitulo: String,
    selecionado: Boolean,
    onClick: () -> Unit,
    iconeNeutro: Boolean = false,
) {
    val forma = RoundedCornerShape(12.dp)
    Row(
        Modifier
            .fillMaxWidth()
            .clip(forma)
            .background(if (selecionado) VColor.BgChip else Color.White)
            .border(1.dp, if (selecionado) VColor.PurpleLilac else LinhaCard, forma)
            .vClick(PressAccent) { onClick() }
            .padding(10.dp)
            .semantics { contentDescription = "$titulo, $subtitulo" },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconeQuadrado(icone, iconeNeutro)
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            VText(titulo, vs(13f, 700, 15.5f, family = Roboto), VColor.Ink)
            VText(subtitulo, vs(10f, 400, 12.5f, family = Roboto), VColor.PurpleMuted)
        }
        if (selecionado) Icon(Icons.Filled.Check, "Selecionado", Modifier.size(18.dp), tint = Roxo)
    }
}
