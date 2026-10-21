package com.vertice.app.ui.screens.figma

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.R
import com.vertice.app.ui.design.Roboto
import com.vertice.app.ui.design.VColor
import com.vertice.app.ui.design.vs
import com.vertice.app.ui.icons.IcoAkarIconsCheck
import com.vertice.app.ui.icons.IcoBiShuffle
import com.vertice.app.ui.icons.IcoBoxiconsShieldFilled
import com.vertice.app.ui.icons.IcoCameraFill
import com.vertice.app.ui.icons.IcoFileTextFill
import com.vertice.app.ui.icons.IcoPathFill
import com.vertice.app.ui.icons.IcoSirenFill
import com.vertice.app.ui.icons.IcoShieldCheckFill
import com.vertice.app.ui.icons.IcoUsersFill
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign

enum class VioletaState { VERIFICACAO, DESATIVADO, ATIVO }

private val TopWhite = Color(0xFFFDFCFF)
private val OffBg = Color(0xFFF7F2FF)
private val CardWhite = Color(0xFFFFFFFF)
private val Muted = Color(0xFF9D9D9D)
private val DeepViolet = Color(0xFF4D3274)
private val GradCustomer = listOf(Color(0xFF641BE2), Color(0xFF9B13AC), Color(0xFFD20B76))
private val PinkBtn = listOf(Color(0xFFEA58A3), Color(0xFFE275AE))
private val DashGray = Color(0xFFD9D9D9)

@Composable
private fun WhiteTop() {
    Box(Modifier.fillMaxWidth().height(60.dp).background(TopWhite))
}

@Composable
private fun HeaderBar() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(Brush.linearGradient(GradCustomer)),
    )
}

@Composable
private fun ShieldBadge(x: Int, y: Int, size: Int) {
    Box(
        Modifier
            .offset(x.dp, y.dp)
            .size(size.dp)
            .clip(CircleShape)
            .background(Color(0x33FFFFFF))
            .border(0.6.dp, Color(0x66FFFFFF), CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            IcoBoxiconsShieldFilled,
            contentDescription = null,
            modifier = Modifier.size((size * 0.45f).dp),
            tint = Color.White,
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
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
    ) {
        content()
    }
}

@Composable
fun FigmaVioleta(onNav: (VTab) -> Unit) {
    var state by remember { mutableStateOf(VioletaState.VERIFICACAO) }

    Crossfade(state, animationSpec = tween(340), label = "viol") { s ->
        when (s) {
            VioletaState.VERIFICACAO -> Verificacao(onGo = { state = VioletaState.DESATIVADO }, onNav = onNav)
            VioletaState.DESATIVADO -> Desativado(onGo = { state = VioletaState.ATIVO }, onNav = onNav)
            VioletaState.ATIVO -> Ativo(onGo = { state = VioletaState.DESATIVADO })
        }
    }
}

@Composable
private fun Verificacao(onGo: () -> Unit, onNav: (VTab) -> Unit) {
    var docsOk by remember { mutableStateOf(false) }
    var selfieOk by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize().background(OffBg)) {
        HeaderBar()
        WhiteTop()

        ShieldBadge(178, 102, 38)

        Text(
            "Protocolo Violeta",
            modifier = Modifier.offset(133.dp, 158.dp),
            style = vs(15f, 700, 20f, 0f, Roboto),
            color = Color.White,
            maxLines = 1,
        )
        Text(
            "Ambiente exclusivamente feminino, seguro e verificado",
            modifier = Modifier.offset(31.dp, 179.dp).width(333.dp),
            style = vs(12f, 400, 16f, 0f, Roboto),
            color = Color.White,
            maxLines = 2,
        )

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

        Box(Modifier.offset(0.dp, 776.dp)) {
            VBottomNav(VTab.VIOLETA, onNav)
        }
    }
}

@Composable
private fun Desativado(onGo: () -> Unit, onNav: (VTab) -> Unit) {
    Box(Modifier.fillMaxSize().background(OffBg)) {
        HeaderBar()
        WhiteTop()

        Image(
            painterResource(R.drawable.ic_v_shield_header),
            contentDescription = null,
            modifier = Modifier.offset(175.dp, 102.dp).size(38.dp),
        )

        Text(
            "Protocolo Violeta",
            modifier = Modifier.offset(130.dp, 158.dp).width(129.dp),
            style = vs(15f, 700, 17.64f, 0f, Roboto),
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 1,
        )
        Text(
            "Ambiente exclusivamente feminino, seguro e verificado",
            modifier = Modifier.offset(50.dp, 179.dp).width(294.dp),
            style = vs(11f, 400, 15f, 0f, Roboto),
            color = Color.White.copy(alpha = 0.8f),
            maxLines = 1,
        )

        val cardIcons = listOf(
            R.drawable.v_ic_card1,
            R.drawable.v_ic_card2,
            R.drawable.v_ic_card3,
            R.drawable.v_ic_card4,
        )
        data class Card(val x: Float, val y: Float, val label: String, val labelX: Float, val labelW: Float, val labelTop: Float)
        val cards = listOf(
            Card(25f, 234f, "De mulheres para mulheres", 32f, 126f, 285.5f),
            Card(200f, 234f, "Identidade verificada", 257.5f, 95f, 285.5f),
            Card(25f, 320f, "Rota segura", 62f, 54f, 371.5f),
            Card(200f, 320f, "Botão de emergência", 258.5f, 97f, 371.5f),
        )
        cards.forEachIndexed { i, c ->
            Box(
                Modifier
                    .offset(c.x.dp, c.y.dp)
                    .width(167.dp)
                    .height(74.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(CardWhite),
            ) {
                Image(
                    painterResource(cardIcons[i]),
                    contentDescription = null,
                    modifier = Modifier.offset(10.dp, 10.dp).size(31.dp),
                )
                Text(
                    c.label,
                    modifier = Modifier
                        .offset(x = (c.labelX - c.x).dp, y = (c.labelTop - c.y - 6.5f).dp)
                        .width(c.labelW.dp),
                    style = vs(10f, 700, 11.76f, 0f, Roboto),
                    color = Color.Black.copy(alpha = 0.8f),
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                )
            }
        }

        Box(
            Modifier
                .offset(20.dp, 418.dp)
                .width(353.dp)
                .height(108.dp)
                .shadow(4.dp, RoundedCornerShape(15.dp), spotColor = Color(0x40000000))
                .clip(RoundedCornerShape(15.dp))
                .background(CardWhite)
                .border(1.dp, Color(0xFFE5E5E5), RoundedCornerShape(15.dp))
                .alpha(0.6f),
        ) {
            data class Row(
                val label: String,
                val date: String,
                val labelX: Float,
                val labelW: Float,
                val labelTop: Float,
                val dateTop: Float,
                val checkTop: Float,
                val checkW: Float,
            )
            val rows = listOf(
                Row("Identidade verificada", "Verificado em jan 2026", 108.5f, 153f, 445f, 444.5f, 434f, 18f),
                Row("Telefone confirmado", "Verificado em jan 2025", 106.5f, 103f, 473f, 473.5f, 464f, 19f),
                Row("Protocolo violeta", "Verificado em jan 2025", 100.5f, 89f, 499f, 501.5f, 492f, 19f),
            )
            rows.forEachIndexed { i, r ->
                Image(
                    painterResource(R.drawable.ic_v_check_mark),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(x = (34f - 20f).dp, y = (r.checkTop - 418f).dp)
                        .size(width = r.checkW.dp, height = 17.dp),
                )
                Text(
                    r.label,
                    modifier = Modifier
                        .offset(x = (r.labelX - 20f).dp, y = (r.labelTop - 418f - 8f).dp)
                        .width(r.labelW.dp),
                    style = vs(10f, 700, 11.76f, 0f, Roboto),
                    color = Color.Black.copy(alpha = 0.8f),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                )
                Text(
                    r.date,
                    modifier = Modifier
                        .offset(x = (273f - 20f).dp, y = (r.dateTop - 418f - 6.5f).dp)
                        .width(90.dp),
                    style = vs(8f, 400, 10.4f, 0f, Roboto),
                    color = Muted,
                    maxLines = 1,
                )
                if (i < 2) {
                    Box(
                        Modifier
                            .offset(8.dp, if (i == 0) 37.dp else 68.dp)
                            .width(332.dp)
                            .height(0.5.dp)
                            .background(Color(0xFF000000)),
                    )
                }
            }
        }

        Box(
            Modifier
                .offset(20.dp, 538.dp)
                .width(353.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(
                    Brush.horizontalGradient(
                        0f to Color(0xFFEA58A3),
                        0.63f to Color(0xFFEA58A3),
                        0.952f to Color(0xFFE275AE),
                        1f to Color(0xFFE376AF),
                    ),
                ),
        ) {
            Text(
                "Ativar proteção agora",
                modifier = Modifier.offset(110.dp, 16.dp).width(144.dp),
                style = vs(13f, 800, 16f, 0f, Roboto),
                color = Color.White,
                textAlign = TextAlign.Center,
                maxLines = 1,
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .clickable(onClick = onGo),
            )
        }

        Image(
            painterResource(R.drawable.ic_v_check_shield11),
            contentDescription = null,
            modifier = Modifier.offset(137.dp, 595.dp).size(11.dp).alpha(0.6f),
        )
        Text(
            "Verificado pelo Vertice",
            modifier = Modifier.offset(154.25.dp, 596.dp).width(102.dp),
            style = vs(10f, 400, 13f, 0f, Roboto),
            color = Color.Black.copy(alpha = 0.6f),
            maxLines = 1,
        )

        Box(Modifier.offset(0.dp, 776.dp)) {
            VBottomNav(VTab.VIOLETA, onNav)
        }
    }
}

@Composable
private fun Ativo(onGo: () -> Unit) {
    Box(Modifier.fillMaxSize().background(Color(0xFFF7F2FF))) {
        Box(
            Modifier
                .offset(0.dp, 60.dp)
                .fillMaxWidth()
                .height(792.dp)
                .background(
                    Brush.verticalGradient(
                        0f to Color(0xFF300083),
                        0.5f to Color(0xFF641BE2),
                        1f to Color(0xFFFFFFFF),
                    ),
                ),
        )
        WhiteTop()

        Box(
            Modifier
                .offset(157.dp, 108.dp)
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

        Text(
            "Você está protegida",
            modifier = Modifier.offset(106.dp, 201.dp).width(200.dp),
            style = vs(20f, 700, 23.5f, 0f, Roboto),
            color = Color.White,
            maxLines = 1,
        )

        for (i in 0 until 4) {
            val y = 229 + i * 61
            Box(
                Modifier
                    .offset(72.dp, y.dp)
                    .width(250.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color(0x33FFFFFF))
                    .border(0.6.dp, Color(0x66FFFFFF), RoundedCornerShape(15.dp)),
            ) {
                Box(
                    Modifier
                        .offset(10.dp, 10.dp)
                        .size(30.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color(0xFF641BE2)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(IcoUsersFill, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.White)
                }
                Text(
                    "Rede 100% feminina ativada",
                    modifier = Modifier.offset(47.dp, 16.dp).width(160.dp),
                    style = vs(12f, 700, 14.1f, 0f, Roboto),
                    color = Color.White,
                    maxLines = 1,
                )
                Box(
                    Modifier
                        .offset(223.dp, 17.dp)
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF13AA25)),
                )
                Icon(
                    IcoAkarIconsCheck,
                    contentDescription = null,
                    modifier = Modifier.offset(226.dp, 21.dp).size(10.dp),
                    tint = Color.White,
                )
            }
        }

        Box(
            Modifier
                .offset(125.dp, 533.dp)
                .width(144.dp)
                .height(35.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0x33FFFFFF))
                .border(0.6.dp, Color(0x66FFFFFF), RoundedCornerShape(15.dp))
                .clickable { onGo() },
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
