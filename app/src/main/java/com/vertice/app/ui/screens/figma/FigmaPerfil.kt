package com.vertice.app.ui.screens.figma

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vertice.app.R
import com.vertice.app.ui.design.Roboto
import com.vertice.app.ui.design.vs
import com.vertice.app.ui.icons.IcoAkarIconsCheck
import com.vertice.app.ui.icons.IcoBoxiconsShieldFilled

private val PerfilBg = Color(0xFFF7F2FF)
private val GradPro = listOf(Color(0x00641BE2), Color(0xFF9B13AC), Color(0xFFD20B76))
private val GradBar = listOf(Color(0x00641BE2), Color(0xFF9B13AC), Color(0xFFD20B76))
private val KSkill = Color(0xFF9657F3).copy(alpha = 0.25f)
private val KSkillLine = Color(0xFF904EF2)
private val KSkillText = Color(0xFF883FF4)
private val KGreen = Color(0xFF21C001)
private val KMuted = Color(0xFF929292)

@Composable
fun FigmaPerfilUser(onNav: (VTab) -> Unit) {
    val scroll = rememberScrollState()
    Box(Modifier.fillMaxSize().background(PerfilBg)) {
        Box(Modifier.fillMaxSize().verticalScroll(scroll)) {
            Box(Modifier.fillMaxWidth().height(889.dp)) {
                Image(
                    painterResource(R.drawable.img_avatar_ana),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(129.dp, 89.dp)
                        .size(135.dp)
                        .clip(CircleShape)
                        .border(0.75.dp, Color(0xFF1B1B1B), CircleShape),
                    contentScale = ContentScale.Crop,
                )
                Text(
                    "Ana Silva",
                    modifier = Modifier.offset(129.dp, 237.dp).width(300.dp),
                    style = vs(32f, 800, 37.64f, 0f, Roboto),
                    color = Color.Black,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                )

                Box(
                    Modifier
                        .offset(16.dp, 292.dp)
                        .width(361.dp)
                        .height(82.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Brush.horizontalGradient(GradPro))
                        .clickable { },
                ) {
                    Image(
                        painterResource(R.drawable.ic_v_medal),
                        contentDescription = null,
                        modifier = Modifier.offset(24.dp, 24.dp).size(35.dp),
                    )
                    Text(
                        "Vertice Pro",
                        modifier = Modifier.offset(78.dp, 10.dp),
                        style = vs(20f, 800, 23.52f, 0f, Roboto),
                        color = Color.White,
                        maxLines = 1,
                    )
                    Text(
                        "Desbloqueie novos recursos, matchs e suporte",
                        modifier = Modifier.offset(78.dp, 35.dp).width(226.dp),
                        style = vs(13f, 400, 15.29f, 0f, Roboto),
                        color = Color.White,
                        maxLines = 2,
                    )
                    Image(
                        painterResource(R.drawable.ic_v_chevron_right),
                        contentDescription = null,
                        modifier = Modifier.offset(316.dp, 26.dp).size(30.dp),
                    )
                }

                Box(Modifier.offset(16.dp, 387.dp).width(361.dp).height(0.75.dp).background(Color(0xFFE5E5E5)))

                Text(
                    "Minhas habilidades:",
                    modifier = Modifier.offset(16.dp, 392.dp),
                    style = vs(18f, 400, 21.17f, 0f, Roboto),
                    color = Color.Black,
                    maxLines = 1,
                )

                Skill("Costura", 17, 420, 83)
                Skill("Confecção", 106, 420, 97)
                Skill("Manejo", 209, 420, 78)

                Box(
                    Modifier
                        .offset(19.dp, 467.dp)
                        .width(360.dp)
                        .height(331.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.White),
                ) {
                    Text(
                        "Trilha de blindagem",
                        modifier = Modifier.offset(10.dp, 15.dp),
                        style = vs(18f, 400, 21.17f, 0f, Roboto),
                        color = Color.Black,
                        maxLines = 1,
                    )
                    Box(
                        Modifier
                            .offset(273.dp, 11.dp)
                            .width(38.dp)
                            .height(32.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0x169657F3))
                            .border(0.6.dp, KSkillLine, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            "50%",
                            style = vs(16f, 500, 18.82f, 0f, Roboto),
                            color = KSkillText,
                            maxLines = 1,
                        )
                    }
                    Image(
                        painterResource(R.drawable.ic_v_chevron_black),
                        contentDescription = null,
                        modifier = Modifier.offset(317.dp, 12.dp).size(30.dp),
                    )
                    Box(
                        Modifier
                            .offset(9.dp, 54.dp)
                            .width(338.dp)
                            .height(9.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(Color(0xFFD9D9D9)),
                    )
                    Box(
                        Modifier
                            .offset(9.dp, 54.dp)
                            .width(176.dp)
                            .height(9.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(Brush.horizontalGradient(GradBar)),
                    )

                    ModuleRow("Segurança pessoal", 6, 76, true)
                    ModuleRow("Direitos do MEI", 6, 146, true)
                    ModuleRow("Gestão de clientes", 6, 216, false)

                    Image(
                        painterResource(R.drawable.ic_v_arrow_right_black),
                        contentDescription = null,
                        modifier = Modifier.offset(243.dp, 293.dp).size(20.dp),
                    )
                    Text(
                        "Toque para ver a trilha completa",
                        modifier = Modifier.offset(9.dp, 293.dp).width(229.dp),
                        style = vs(16f, 400, 18.82f, 0f, Roboto),
                        color = Color.Black,
                        maxLines = 1,
                    )
                }

                Box(
                    Modifier
                        .offset(19.dp, 811.dp)
                        .width(360.dp)
                        .height(61.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF9386A1))
                        .clickable { },
                ) {
                    Image(
                        painterResource(R.drawable.ic_v_pencil),
                        contentDescription = null,
                        modifier = Modifier.offset(100.dp, 18.dp).size(24.dp),
                    )
                    Text(
                        "Configurações",
                        modifier = Modifier.offset(126.dp, 20.dp),
                        style = vs(16f, 400, 18.82f, 0f, Roboto),
                        color = Color.Black,
                        maxLines = 1,
                    )
                }
            }
        }

        VBottomNav(VTab.PERFIL, onNav, Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
private fun Skill(label: String, x: Int, y: Int, w: Int) {
    Box(
        Modifier
            .offset(x.dp, y.dp)
            .width(w.dp)
            .height(32.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(KSkill)
            .border(0.6.dp, KSkillLine, RoundedCornerShape(100.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text(label, style = vs(16f, 500, 18.82f, 0f, Roboto), color = KSkillText, maxLines = 1)
    }
}

@Composable
private fun ModuleRow(label: String, x: Int, y: Int, done: Boolean) {
    val bg = if (done) Color(0x169657F3) else Color(0xFFF0F0F0)
    val line = if (done) KSkillLine else KMuted
    Box(
        Modifier
            .offset(x.dp, y.dp)
            .width(337.dp)
            .height(61.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(bg)
            .border(0.6.dp, line, RoundedCornerShape(10.dp)),
    ) {
        Box(
            Modifier
                .offset(12.dp, 13.dp)
                .size(35.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(if (done) Color(0xFF9657F3) else Color(0xFF979797)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                IcoBoxiconsShieldFilled,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = Color.White,
            )
        }
        Text(
            label,
            modifier = Modifier.offset(56.dp, 21.dp),
            style = vs(16f, 500, 18.82f, 0f, Roboto),
            color = if (done) Color.Black else KMuted,
            maxLines = 1,
        )
        if (done) {
            Box(
                Modifier
                    .offset(300.dp, 20.dp)
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(KGreen),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    IcoAkarIconsCheck,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = Color.White,
                )
            }
        }
    }
}
