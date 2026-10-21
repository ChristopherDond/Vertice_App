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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun FigmaInicial(onNav: (VTab) -> Unit = {}) {
    var proVisible by remember { mutableStateOf(true) }
    val appear = remember { Animatable(0f) }
    LaunchedEffect(Unit) { appear.animateTo(1f, tween(420, easing = LinearEasing)) }

    val pulse = rememberInfiniteTransition(label = "live")
    val dot by pulse.animateFloat(
        0.45f,
        1f,
        infiniteRepeatable(tween(1100), RepeatMode.Reverse),
        label = "dot",
    )

    val ring = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        delay(160)
        ring.animateTo(0.70f, tween(850, easing = LinearEasing))
    }

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
                        .background(ProBg),
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
            T("Olá, Ana!", 30, 158, 157, 24f, 800, Color.Black, 1, 28.13f)
            T("Veja suas conexões e oportunidades de hoje", 30, 190, 226, 13f, 400, Color(0x70000000), 2, 18f)

            Box(
                Modifier.offset(298.dp, 155.dp).size(65.dp).clip(CircleShape).background(Color.White),
            ) {
                Image(
                    painterResource(R.drawable.img_avatar_ana),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
                Box(
                    Modifier
                        .offset(58.dp, 0.dp)
                        .size(7.dp)
                        .alpha(dot)
                        .clip(CircleShape)
                        .background(Green),
                )
            }

            Box(
                Modifier
                    .offset(29.dp, 245.dp)
                    .width(333.dp)
                    .height(106.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Purple),
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
                            sweepAngle = 360f * ring.value,
                            useCenter = false,
                            topLeft = tl,
                            size = Size(d, d),
                            style = Stroke(stroke, cap = StrokeCap.Round),
                        )
                    }
                    Text("70%", style = vs(15.6f, 800, 19f, 0f, Roboto), color = Color.White)
                }
                T("PERFIL", 140, 28, 60, 10f, 800, Color.White, 1, 11.76f)
                T("Complete seu perfil\npara mais matches", 140, 39, 135, 10f, 500, Color.White, 2, 15.3f)
                Ico(IcoDashiconsArrowRightAlt2, 322, 42, 20, 20, Color(0x6EFFFFFF))
            }

            Btn("Encontrar Parceiro", 30, 371, 157, Purple, Color.White) {}
            Btn("Oferecer Serviço", 207, 371, 156, BtnLight, Color.Black) {}

            T("ATIVIDADE RECENTE", 30, 459, 118, 10f, 500, Label, 1, 11.73f)

            Act(
                y = 488,
                icon = IcoDashiconsAdminTools,
                iconBg = ToolBg,
                iconTint = ProIconFg,
                body = "Carlos Mendes concluiu o serviço\nConserto de chuveiro elétrico • R$120",
                bodyX = 63,
                bodyW = 219,
                time = "há 20min",
                timeX = 278,
            )
            Act(
                y = 561,
                icon = Trophy,
                iconBg = TrophyBg,
                iconTint = TrophyFg,
                body = "Módulo concluído na Trilha de Blindagem\nContinue e desbloqueie mais matches",
                bodyX = 63,
                bodyW = 229,
                time = "há 2h",
                timeX = 297,
            )
        }

        VBottomNav(VTab.INICIO, onNav, Modifier.align(Alignment.BottomCenter))
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
