package com.vertice.app.ui.screens.figma

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vertice.app.R
import com.vertice.app.core.di.ServiceLocator
import com.vertice.app.core.result.onOk
import com.vertice.app.domain.model.Balance
import com.vertice.app.domain.model.User
import com.vertice.app.domain.model.VioletaStatus
import com.vertice.app.ui.design.Roboto
import com.vertice.app.ui.design.VColor
import com.vertice.app.ui.design.vs
import com.vertice.app.ui.icons.IcoDashiconsAdminTools
import com.vertice.app.ui.icons.IcoDashiconsArrowRightAlt2
import com.vertice.app.ui.icons.Lightning
import com.vertice.app.ui.icons.Trophy
import kotlinx.coroutines.delay

private val ProBg = Color(0xFFE0CEFC)
private val ProIconBg = Color(0xFFD1B2FF)
private val ProIconFg = Color(0xFF9657F3)
private val Purple = Color(0xFF904EF2)
private val BtnLight = Color(0xFFFAF6FF)
private val Green = Color(0xFF1E7629)
private val Label = Color(0xFF53357F)
private val ToolBg = Color(0xFFE7D7FF)
private val TrophyBg = Color(0xFFFFF5AB)
private val TrophyFg = Color(0xFFFFDB58)
private val Shadow = Color(0x40000000)

@Composable
private fun T(
    text: String,
    x: Int,
    y: Int,
    w: Int,
    size: Float,
    weight: Int,
    color: Color,
    lines: Int = 1,
    lineHeight: Float = size * 1.3f,
) {
    Text(
        text = text,
        modifier = Modifier.offset(x.dp, y.dp).width(w.dp),
        maxLines = lines,
        style = vs(size, weight, lineHeight, 0f, Roboto),
        color = color,
    )
}

@Composable
private fun Ico(v: ImageVector, x: Int, y: Int, w: Int, h: Int, tint: Color) {
    Icon(
        imageVector = v,
        contentDescription = null,
        modifier = Modifier.offset(x.dp, y.dp).size(width = w.dp, height = h.dp),
        tint = tint,
    )
}

@Composable
fun FigmaInicial(
    onNav: (VTab) -> Unit = {},
    onPro: () -> Unit = {},
    onEncontrarParceiro: () -> Unit = { onNav(VTab.MATCH) },
    onOferecerServico: () -> Unit = {},
    onEditarPerfil: () -> Unit = { onNav(VTab.PERFIL) },
) {
    var proVisible by remember { mutableStateOf(true) }
    var showMissionsSheet by remember { mutableStateOf(false) }
    var user by remember { mutableStateOf<User?>(null) }
    var balance by remember { mutableStateOf<Balance?>(null) }
    var violetaStatus by remember { mutableStateOf<VioletaStatus?>(null) }
    var exploredMatch by remember { mutableStateOf(false) }

    val appear = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        appear.animateTo(1f, tween(420, easing = LinearEasing))
        ServiceLocator.profileRepository.me().onOk { user = it }
        ServiceLocator.financeRepository.balance().onOk { balance = it }
        ServiceLocator.profileRepository.violeta().onOk { violetaStatus = it }
    }

    val isContaOk = user != null && user?.id?.isNotBlank() == true
    val isVioletaOk = user?.violetaVerified == true || violetaStatus?.identityVerified == true
    val isBioOk = !user?.bio.isNullOrBlank()
    val isSkillsOk = !user?.skills.isNullOrEmpty()
    val isMatchOk = exploredMatch

    val profileScore = remember(user, violetaStatus, exploredMatch) {
        var score = 0
        if (isContaOk) score += 20
        if (isMatchOk) score += 20
        if (isVioletaOk) score += 20
        if (isBioOk) score += 20
        if (isSkillsOk) score += 20
        score.coerceIn(0, 100)
    }

    val session by ServiceLocator.sessionManager.session.collectAsState(initial = null)
    val userName = user?.name?.ifBlank { session?.displayName.orEmpty() }
        ?: session?.displayName.orEmpty()
    val firstName = userName.trim().split(Regex("\\s+")).firstOrNull()?.replaceFirstChar { it.uppercase() } ?: ""
    val saudacao = if (firstName.isNotBlank()) "Olá, $firstName!" else "Olá!"

    Box(Modifier.fillMaxSize().background(Color(0xFFF6F1FF))) {

        if (proVisible) {
            androidx.compose.animation.AnimatedVisibility(
                visible = proVisible,
                enter = androidx.compose.animation.fadeIn(tween(320)) + androidx.compose.animation.expandVertically(tween(320)),
                exit = androidx.compose.animation.fadeOut(tween(260)) + androidx.compose.animation.shrinkVertically(tween(300)),
            ) {
                Box(
                    Modifier
                        .offset(30.dp, 78.dp)
                        .width(333.dp)
                        .height(46.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(ProBg)
                        .clickable { onPro() },
                ) {
                    Box(
                        Modifier
                            .offset(12.dp, 10.dp)
                            .size(25.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(ProIconBg),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(Lightning, null, Modifier.size(width = 8.dp, height = 14.dp), tint = ProIconFg)
                    }
                    Text(
                        "Vertice Pro — mais alcance, prioridade nos resultados e acesso antecipado a novos parceiros",
                        modifier = Modifier.offset(49.dp, 10.dp).width(267.dp),
                        maxLines = 2,
                        style = vs(10f, 400, 13f, 0f, Roboto),
                        color = Color.Black,
                    )
                    Image(
                        painterResource(R.drawable.ic_close),
                        contentDescription = "Fechar",
                        modifier = Modifier
                            .offset(308.dp, 15.dp)
                            .size(16.dp)
                            .clickable { proVisible = false },
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color(0x5E000000)),
                    )
                }
            }
        }

        Box(
            Modifier
                .offset(0.dp, if (proVisible) 0.dp else (-46).dp)
                .alpha(appear.value),
        ) {
            T(saudacao, 30, 158, 260, 24f, 800, Color.Black, 1, 28.13f)
            T("Veja suas conexões e oportunidades de hoje", 30, 190, 226, 13f, 400, Color(0x70000000), 2, 18f)

            Box(
                Modifier
                    .offset(298.dp, 155.dp)
                    .size(65.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(0.75.dp, Color(0x33000000), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                if (!user?.avatarUrl.isNullOrBlank() || user?.name?.contains("Ana", ignoreCase = true) == true) {
                    Image(
                        painter = painterResource(R.drawable.ana_profile),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Icon(
                        com.vertice.app.ui.icons.IcoUsersFill,
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.size(30.dp),
                        tint = Purple,
                    )
                }
            }

            Box(
                Modifier
                    .offset(29.dp, 245.dp)
                    .width(333.dp)
                    .height(106.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Purple)
                    .clickable { showMissionsSheet = true },
            ) {
                Box(Modifier.offset(21.dp, 17.dp).size(72.dp), contentAlignment = Alignment.Center) {
                    Canvas(Modifier.fillMaxSize()) {
                        val stroke = 5.dp.toPx()
                        val d = size.minDimension - stroke
                        val tl = Offset((size.width - d) / 2f, (size.height - d) / 2f)
                        drawArc(
                            color = Color(0x6BD9D9D9),
                            startAngle = -90f,
                            sweepAngle = 360f,
                            useCenter = false,
                            topLeft = tl,
                            size = Size(d, d),
                            style = Stroke(stroke, cap = StrokeCap.Round),
                        )
                        drawArc(
                            color = Color.White,
                            startAngle = -90f,
                            sweepAngle = 360f * (profileScore / 100f),
                            useCenter = false,
                            topLeft = tl,
                            size = Size(d, d),
                            style = Stroke(stroke, cap = StrokeCap.Round),
                        )
                    }
                    Text("$profileScore%", style = vs(15.6f, 800, 19f, 0f, Roboto), color = Color.White)
                }
                T("PERFIL", 140, 28, 60, 10f, 800, Color.White, 1, 11.76f)
                T(
                    if (profileScore == 100) "Perfil completo!\nMáxima visibilidade" else "Complete seu perfil\npara mais matches",
                    140,
                    39,
                    135,
                    10f,
                    500,
                    Color.White,
                    2,
                    15.3f,
                )
                Ico(IcoDashiconsArrowRightAlt2, 293, 43, 20, 20, Color(0xB3FFFFFF))
            }

            Btn("Encontrar Parceiro", 30, 371, 157, Purple, Color.White) { onEncontrarParceiro() }
            Btn("Oferecer Serviço", 207, 371, 156, BtnLight, Color.Black) { onOferecerServico() }

            T("ATIVIDADE RECENTE", 30, 459, 118, 10f, 500, Label, 1, 11.73f)

            Box(
                Modifier
                    .offset(30.dp, 488.dp)
                    .width(333.dp)
                    .height(68.dp)
                    .shadow(5.dp, RoundedCornerShape(15.dp), ambientColor = Shadow, spotColor = Shadow)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (balance != null) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column {
                            Text("Saldo em conta", style = vs(11f, 400, 14f, 0f, Roboto), color = Color.Gray)
                            Text(
                                "R$ " + String.format("%.2f", balance?.available ?: 0.0).replace('.', ','),
                                style = vs(16f, 700, 20f, 0f, Roboto),
                                color = Color.Black,
                            )
                        }
                        Box(
                            Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFEFE8FC))
                                .clickable { onNav(VTab.PERFIL) }
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                        ) {
                            Text("Ver carteira", style = vs(11f, 600, 14f, 0f, Roboto), color = Purple)
                        }
                    }
                } else {
                    Text(
                        "Nenhuma atividade recente.\nSuas conexões e serviços aparecerão aqui.",
                        style = vs(12f, 400, 17f, 0f, Roboto),
                        color = Color.Gray,
                    )
                }
            }
        }

        VBottomNav(VTab.INICIO, onNav, Modifier.align(Alignment.BottomCenter))

        if (showMissionsSheet) {
            MissoesPerfilBottomSheet(
                score = profileScore,
                isContaOk = isContaOk,
                isMatchOk = isMatchOk,
                isVioletaOk = isVioletaOk,
                isBioOk = isBioOk,
                isSkillsOk = isSkillsOk,
                onDismiss = { showMissionsSheet = false },
                onNavMatch = {
                    showMissionsSheet = false
                    exploredMatch = true
                    onNav(VTab.MATCH)
                },
                onNavVioleta = {
                    showMissionsSheet = false
                    onNav(VTab.VIOLETA)
                },
                onNavEditarPerfil = {
                    showMissionsSheet = false
                    onEditarPerfil()
                },
            )
        }
    }
}

@Composable
private fun Btn(label: String, x: Int, y: Int, w: Int, bg: Color, fg: Color, onClick: () -> Unit) {
    Box(
        Modifier
            .offset(x.dp, y.dp)
            .width(w.dp)
            .height(48.dp)
            .shadow(5.dp, RoundedCornerShape(15.dp), ambientColor = Shadow, spotColor = Shadow)
            .clip(RoundedCornerShape(15.dp))
            .background(bg)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(label, style = vs(14f, 800, 16.41f, 0f, Roboto), color = fg, maxLines = 1)
    }
}

@Composable
private fun Act(
    y: Int,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    body: String,
    bodyX: Int,
    bodyW: Int,
    time: String,
    timeX: Int,
) {
    Box(
        Modifier
            .offset(30.dp, y.dp)
            .width(333.dp)
            .height(61.dp)
            .shadow(5.dp, RoundedCornerShape(15.dp), ambientColor = Shadow, spotColor = Shadow)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White),
    ) {
        Box(
            Modifier.offset(14.dp, 13.dp).size(35.dp).clip(RoundedCornerShape(10.dp)).background(iconBg),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, null, Modifier.size(17.dp), tint = iconTint)
        }
        Text(
            body,
            modifier = Modifier.offset(bodyX.dp, 13.dp).width(bodyW.dp),
            maxLines = 2,
            style = vs(12f, 500, 18f, 0f, Roboto),
            color = Color.Black,
        )
        Text(
            time,
            modifier = Modifier.offset(timeX.dp, 15.dp),
            style = vs(10f, 300, 13f, 0f, Roboto),
            color = Color(0x8F000000),
            maxLines = 1,
        )
    }
}

@Composable
private fun MissoesPerfilBottomSheet(
    score: Int,
    isContaOk: Boolean,
    isMatchOk: Boolean,
    isVioletaOk: Boolean,
    isBioOk: Boolean,
    isSkillsOk: Boolean,
    onDismiss: () -> Unit,
    onNavMatch: () -> Unit,
    onNavVioleta: () -> Unit,
    onNavEditarPerfil: () -> Unit,
) {
    data class Missao(
        val num: Int,
        val titulo: String,
        val desc: String,
        val feita: Boolean,
        val acaoLabel: String,
        val onAcao: () -> Unit,
    )

    val missoes = listOf(
        Missao(1, "Criar sua conta", "Cadastro inicial e credenciais salvas", isContaOk, "Concluído", {}),
        Missao(2, "Explorar o Match", "Descubra parceiros e serviços na rede", isMatchOk, "Explorar", onNavMatch),
        Missao(3, "Protocolo Violeta", "Envie documentos e foto para verificação", isVioletaOk, "Verificar", onNavVioleta),
        Missao(4, "Bio e Foto de Perfil", "Apresente quem você é e sua atuação", isBioOk, "Editar", onNavEditarPerfil),
        Missao(5, "Habilidades e Tags", "Adicione ao menos 1 competência", isSkillsOk, "Adicionar", onNavEditarPerfil),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .clickable(onClick = onDismiss),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
                .clickable(enabled = false) {}
                .padding(horizontal = 22.dp, vertical = 20.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
            ) {
                // Drag handle
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(42.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFFDDDDDD)),
                )
                Spacer(Modifier.height(16.dp))

                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text(
                            text = "Complete seu perfil",
                            style = vs(19f, 700, 23f, 0f, Roboto),
                            color = Color.Black,
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = "$score% concluído para mais visibilidade",
                            style = vs(12f, 500, 16f, 0f, Roboto),
                            color = Purple,
                        )
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Image(
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = "Fechar",
                            modifier = Modifier.size(16.dp),
                        )
                    }
                }

                Spacer(Modifier.height(14.dp))

                // Barra de progresso
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFEFE8FC)),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction = (score / 100f).coerceIn(0.02f, 1f))
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Brush.horizontalGradient(listOf(Color(0xFF883FF4), Color(0xFFEA58A3)))),
                    )
                }

                Spacer(Modifier.height(20.dp))

                // Lista de missões
                missoes.forEachIndexed { idx, m ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            if (m.feita) {
                                Image(
                                    painter = painterResource(R.drawable.ic_v_check_mark),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .border(1.5.dp, Color(0xFFB8B1AA), CircleShape),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = "${m.num}",
                                        style = vs(10f, 700, 12f, 0f, Roboto),
                                        color = Color(0xFF888888),
                                    )
                                }
                            }

                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                ) {
                                    Text(
                                        text = m.titulo,
                                        style = vs(13f, 700, 16f, 0f, Roboto),
                                        color = if (m.feita) Color.Black else Color.Black.copy(alpha = 0.85f),
                                    )
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(if (m.feita) Color(0xFFEAF8ED) else Color(0xFFF2EBFF))
                                            .padding(horizontal = 5.dp, vertical = 2.dp),
                                    ) {
                                        Text(
                                            text = "+20%",
                                            style = vs(9f, 700, 11f, 0f, Roboto),
                                            color = if (m.feita) Color(0xFF1E7629) else Purple,
                                        )
                                    }
                                }
                                Spacer(Modifier.height(2.dp))
                                Text(
                                    text = m.desc,
                                    style = vs(11f, 400, 14f, 0f, Roboto),
                                    color = Color.Gray,
                                )
                            }
                        }

                        Spacer(Modifier.width(8.dp))

                        if (m.feita) {
                            Text(
                                text = "Feito ✓",
                                style = vs(12f, 600, 14f, 0f, Roboto),
                                color = Color(0xFF1E7629),
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Purple)
                                    .clickable(onClick = m.onAcao)
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = m.acaoLabel,
                                    style = vs(11.5f, 700, 14f, 0f, Roboto),
                                    color = Color.White,
                                )
                            }
                        }
                    }

                    if (idx < missoes.size - 1) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.5.dp)
                                .background(Color(0xFFEEEEEE)),
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}
