package com.vertice.app.ui.screens.figma

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.R
import com.vertice.app.ui.components.PressWhite
import com.vertice.app.ui.components.vClick
import com.vertice.app.ui.design.Roboto
import com.vertice.app.ui.design.vs
import com.vertice.app.ui.icons.IcoAkarIconsCheck
import com.vertice.app.ui.icons.IcoBoxiconsShieldFilled
import com.vertice.app.ui.icons.IcoUsersFill

enum class VioletaState { VERIFICACAO, DESATIVADO, ATIVO }

private val OffBg = Color(0xFFF7F2FF)
private val CardWhite = Color(0xFFFFFFFF)
private val Muted = Color(0xFF9D9D9D)
private val DeepViolet = Color(0xFF4D3274)
private val PinkBtn = listOf(Color(0xFFEA58A3), Color(0xFFE275AE))

private val VioletaGradient = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF4C10D8),
        Color(0xFF8B12B6),
        Color(0xFFD61476),
    ),
)

private val ButtonPinkGradient = Brush.horizontalGradient(
    listOf(Color(0xFFEA58A3), Color(0xFFE275AE)),
)

private data class VioletaFeature(
    val iconRes: Int,
    val label: String,
)

private val VioletaFeatures = listOf(
    VioletaFeature(R.drawable.v_ic_card1, "De mulheres para mulheres"),
    VioletaFeature(R.drawable.v_ic_card2, "Identidade verificada"),
    VioletaFeature(R.drawable.v_ic_card3, "Rota segura"),
    VioletaFeature(R.drawable.v_ic_card4, "Botão de emergência"),
)

@Composable
fun FigmaVioleta(onNav: (VTab) -> Unit, initialState: VioletaState = VioletaState.DESATIVADO) {
    var state by remember { mutableStateOf(initialState) }

    Crossfade(state, animationSpec = tween(340), label = "viol") { s ->
        when (s) {
            VioletaState.VERIFICACAO -> Verificacao(onGo = { state = VioletaState.DESATIVADO }, onNav = onNav)
            VioletaState.DESATIVADO -> Desativado(onGo = { state = VioletaState.ATIVO }, onNav = onNav)
            VioletaState.ATIVO -> Ativo(onGo = { state = VioletaState.DESATIVADO })
        }
    }
}

@Composable
private fun Desativado(onGo: () -> Unit, onNav: (VTab) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OffBg),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 76.dp),
        ) {
            // Header com gradiente idêntico ao Figma
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VioletaGradient)
                    .statusBarsPadding()
                    .padding(top = 22.dp, bottom = 26.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.22f))
                        .border(1.dp, Color.White.copy(alpha = 0.35f), CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_v_shield38),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                    )
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Protocolo Violeta",
                    style = vs(18f, 700, 22f, 0f, Roboto),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Ambiente exclusivamente feminino, seguro e verificado",
                    style = vs(11.5f, 400, 15f, 0f, Roboto),
                    color = Color.White.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 28.dp),
                )
            }

            // Conteúdo principal
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                // Grid 2x2 de Cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    VioletaFeatureCard(VioletaFeatures[0], Modifier.weight(1f))
                    VioletaFeatureCard(VioletaFeatures[1], Modifier.weight(1f))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    VioletaFeatureCard(VioletaFeatures[2], Modifier.weight(1f))
                    VioletaFeatureCard(VioletaFeatures[3], Modifier.weight(1f))
                }

                // Card de Verificação
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 3.dp,
                            shape = RoundedCornerShape(15.dp),
                            spotColor = Color(0x18000000),
                            ambientColor = Color(0x08000000),
                        )
                        .clip(RoundedCornerShape(15.dp))
                        .background(CardWhite)
                        .border(0.5.dp, Color(0xFFE5E5E5), RoundedCornerShape(15.dp))
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                ) {
                    val items = listOf(
                        "Identidade verificada" to "Verificado em jan 2026",
                        "Telefone confirmado" to "Verificado em jan 2025",
                        "Protocolo violeta" to "Verificado em jan 2025",
                    )
                    items.forEachIndexed { index, (title, date) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 11.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.ic_v_check_mark),
                                    contentDescription = null,
                                    modifier = Modifier.size(17.dp),
                                )
                                Text(
                                    text = title,
                                    style = vs(11.5f, 700, 14f, 0f, Roboto),
                                    color = Color.Black.copy(alpha = 0.85f),
                                )
                            }
                            Text(
                                text = date,
                                style = vs(9.5f, 400, 12f, 0f, Roboto),
                                color = Muted,
                            )
                        }
                        if (index < items.size - 1) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(0.5.dp)
                                    .background(Color.Black.copy(alpha = 0.08f)),
                            )
                        }
                    }
                }

                // Botão "Ativar proteção agora"
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(15.dp),
                            spotColor = Color(0x33EA58A3),
                        )
                        .clip(RoundedCornerShape(15.dp))
                        .background(ButtonPinkGradient)
                        .clickable(onClick = onGo),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Ativar proteção agora",
                        style = vs(14f, 800, 18f, 0f, Roboto),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                }

                // Rodapé "Verificado pelo Vertice"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_v_check_shield11),
                        contentDescription = null,
                        modifier = Modifier.size(12.dp).alpha(0.6f),
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "Verificado pelo Vertice",
                        style = vs(10.5f, 400, 13f, 0f, Roboto),
                        color = Color.Black.copy(alpha = 0.6f),
                    )
                }
            }
        }

        // Barra inferior de navegação ancorada
        VBottomNav(
            active = VTab.VIOLETA,
            onSelect = onNav,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun VioletaFeatureCard(
    feature: VioletaFeature,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(78.dp)
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(15.dp),
                spotColor = Color(0x18000000),
                ambientColor = Color(0x08000000),
            )
            .clip(RoundedCornerShape(15.dp))
            .background(CardWhite)
            .border(0.5.dp, Color(0xFFEFEFEF), RoundedCornerShape(15.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            Image(
                painter = painterResource(feature.iconRes),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = feature.label,
                style = vs(10.5f, 700, 13f, 0f, Roboto),
                color = Color.Black.copy(alpha = 0.85f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun Verificacao(onGo: () -> Unit, onNav: (VTab) -> Unit) {
    var docsOk by remember { mutableStateOf(false) }
    var selfieOk by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize().background(OffBg)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(VioletaGradient)
                .statusBarsPadding()
                .padding(top = 22.dp, bottom = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.22f))
                    .border(1.dp, Color.White.copy(alpha = 0.35f), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_v_shield38),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                )
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Protocolo Violeta",
                style = vs(18f, 700, 22f, 0f, Roboto),
                color = Color.White,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "Ambiente exclusivamente feminino, seguro e verificado",
                style = vs(11.5f, 400, 15f, 0f, Roboto),
                color = Color.White.copy(alpha = 0.85f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 28.dp),
            )
        }

        Text(
            "Verificação:",
            modifier = Modifier.offset(12.dp, 236.dp),
            style = vs(20f, 500, 26f, 0f, Roboto),
            color = Color.Black,
            maxLines = 1,
        )
        Text(
            "Etapa 01 - verificação de documentos",
            modifier = Modifier.offset(4.dp, 269.dp),
            style = vs(15f, 500, 20f, 0f, Roboto),
            color = DeepViolet,
            maxLines = 1,
        )

        DashUploadBox(18, 291, 358, 133, onClick = { docsOk = true }) {
            Text(
                "Envie seus documentos ou tire uma foto deles",
                modifier = Modifier.offset(18.dp, 32.dp).width(322.dp),
                style = vs(20f, 400, 27f, 0f, Roboto),
                color = Color.Black,
                maxLines = 3,
            )
            if (docsOk) {
                Icon(
                    IcoAkarIconsCheck,
                    contentDescription = null,
                    modifier = Modifier.offset(320.dp, 100.dp).size(18.dp),
                    tint = Color(0xFF12AA24),
                )
            }
        }

        Text(
            "Etapa 02 - Tire uma foto sua para verificar",
            modifier = Modifier.offset(4.dp, 452.dp),
            style = vs(15f, 500, 20f, 0f, Roboto),
            color = DeepViolet,
            maxLines = 1,
        )

        DashUploadBox(106, 477, 181, 212, onClick = { selfieOk = true }) {
            Image(
                painter = painterResource(R.drawable.ic_v_clarity_badge),
                contentDescription = null,
                modifier = Modifier.offset(25.dp, 38.dp).size(135.dp),
            )
            if (selfieOk) {
                Icon(
                    IcoAkarIconsCheck,
                    contentDescription = null,
                    modifier = Modifier.offset(150.dp, 186.dp).size(18.dp),
                    tint = Color(0xFF12AA24),
                )
            }
        }

        Box(
            Modifier
                .offset(22.dp, 711.dp)
                .width(353.dp)
                .height(48.dp)
                .shadow(0.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Brush.horizontalGradient(PinkBtn))
                .clickable(onClick = onGo),
            contentAlignment = Alignment.Center,
        ) {
            Text("Verificar", style = vs(20f, 700, 26f, 0f, Roboto), color = Color.White)
        }

        VBottomNav(
            active = VTab.VIOLETA,
            onSelect = onNav,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun DashUploadBox(
    x: Int,
    y: Int,
    w: Int,
    h: Int,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Box(
        Modifier
            .offset(x.dp, y.dp)
            .width(w.dp)
            .height(h.dp)
            .drawBehind {
                val r = 10.dp.toPx()
                val inset = 0.5.dp.toPx()
                val path = Path().apply {
                    addRoundRect(
                        RoundRect(
                            left = inset,
                            top = inset,
                            right = size.width - inset,
                            bottom = size.height - inset,
                            radiusX = r,
                            radiusY = r,
                        ),
                    )
                }
                drawPath(
                    path = path,
                    color = Color.Black,
                    style = Stroke(
                        width = 1.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 4f), 0f),
                    ),
                )
            }
            .then(if (onClick != null) Modifier.vClick(scaleDown = 0.95f) { onClick() } else Modifier),
    ) {
        content()
    }
}

@Composable
private fun Ativo(onGo: () -> Unit) {
    Box(Modifier.fillMaxSize().background(Color(0xFFF7F2FF))) {
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color(0xFF300083),
                        0.5f to Color(0xFF641BE2),
                        1f to Color(0xFFFFFFFF),
                    ),
                ),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                Modifier
                    .size(79.dp)
                    .clip(CircleShape)
                    .background(Color(0x33FFFFFF))
                    .border(0.6.dp, Color(0x66FFFFFF), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    Modifier
                        .size(65.dp)
                        .clip(CircleShape)
                        .background(Color(0x2EFFFFFF))
                        .border(0.6.dp, Color(0x66FFFFFF), CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        IcoBoxiconsShieldFilled,
                        contentDescription = null,
                        modifier = Modifier.size(width = 26.dp, height = 29.dp),
                        tint = Color.White,
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            Text(
                "Você está protegida",
                style = vs(20f, 700, 23.5f, 0f, Roboto),
                color = Color.White,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(20.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(11.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                for (i in 0 until 4) {
                    Row(
                        Modifier
                            .width(280.dp)
                            .height(50.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color(0x33FFFFFF))
                            .border(0.6.dp, Color(0x66FFFFFF), RoundedCornerShape(15.dp))
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Box(
                                Modifier
                                    .size(30.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(Color(0xFF641BE2)),
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(IcoUsersFill, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.White)
                            }
                            Text(
                                "Rede 100% feminina ativada",
                                style = vs(12f, 700, 14.1f, 0f, Roboto),
                                color = Color.White,
                            )
                        }
                        Box(
                            Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF13AA25)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                IcoAkarIconsCheck,
                                contentDescription = null,
                                modifier = Modifier.size(11.dp),
                                tint = Color.White,
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(26.dp))

            Box(
                Modifier
                    .width(160.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color(0x33FFFFFF))
                    .border(0.6.dp, Color(0x66FFFFFF), RoundedCornerShape(15.dp))
                    .vClick(PressWhite) { onGo() },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    "Desativar Proteção",
                    style = vs(14f, 700, 16.5f, 0f, Roboto),
                    color = Color.White,
                    maxLines = 1,
                )
            }
        }
    }
}
