package com.vertice.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.components.ActivityRow
import com.vertice.app.components.ProgressRing
import com.vertice.app.components.StatusBar
import com.vertice.app.components.SLabel
import com.vertice.app.components.clickableNoRipple
import com.vertice.app.components.clickableRipple
import com.vertice.app.nav.Screen
import com.vertice.app.ui.theme.LocalColors
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun HomeScreen(
    onNav: (Screen) -> Unit,
    openPro: () -> Unit,
    openOffer: () -> Unit,
) {
    val C = LocalColors
    var proDismissed by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(C.navy)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 90.dp),
        ) {
            StatusBar()

            if (!proDismissed) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp)
                        .padding(bottom = 4.dp)
                        .background(
                            Brush.horizontalGradient(listOf(C.purple.copy(alpha = 0.16f), C.pink.copy(alpha = 0.10f))),
                            RoundedCornerShape(13.dp),
                        )
                        .border(1.dp, C.purple.copy(alpha = 0.20f), RoundedCornerShape(13.dp))
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(C.purple.copy(alpha = 0.18f), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center,
                    ) { Icon(Icons.Filled.Bolt, null, tint = C.purpleL, modifier = Modifier.size(14.dp)) }

                    Text(
                        buildAnnotatedStringPro(C.purpleL, C.mutedL),
                        modifier = Modifier.weight(1f).clickableNoRipple(openPro),
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                    )

                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "Dispensar",
                        tint = C.muted,
                        modifier = Modifier.size(14.dp).clickableNoRipple { proDismissed = true },
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                Column {
                    Text("Olá, Ana 👋", color = C.white, fontWeight = FontWeight.ExtraBold, fontSize = 23.sp)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Veja suas conexões e oportunidades de hoje",
                        color = C.muted,
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        modifier = Modifier.widthIn(max = 220.dp),
                    )
                }
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(Brush.linearGradient(listOf(C.purple, C.pink)), CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("AS", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(9.dp)
                            .background(C.green, CircleShape)
                            .border(2.dp, C.navy, CircleShape),
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .padding(top = 18.dp)
                    .shadow(10.dp, RoundedCornerShape(20.dp), ambientColor = C.purple.copy(alpha = 0.4f), spotColor = C.purple.copy(alpha = 0.4f))
                    .clip(RoundedCornerShape(20.dp))
                    .background(Brush.linearGradient(listOf(C.purple, Color(0xFF8B46F0), C.purpleL)))
                    .clickableRipple { onNav(Screen.Perfil) }
                    .padding(20.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Box(contentAlignment = Alignment.Center) {
                        ProgressRing(pct = 70f, size = 62, strokeWidth = 5, color = Color.White)
                        Text("70%", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("PERFIL", color = Color.White.copy(alpha = 0.55f), fontWeight = FontWeight.Bold, fontSize = 11.sp, letterSpacing = 0.9.sp)
                        Spacer(Modifier.height(4.dp))
                        Text("Complete seu perfil", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(Modifier.height(3.dp))
                        Text("para mais matches", color = Color.White.copy(alpha = 0.65f), fontSize = 12.sp)
                    }
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = Color.White.copy(alpha = 0.5f))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(C.purple)
                        .clickableRipple { onNav(Screen.Match) }
                        .padding(vertical = 15.dp),
                    contentAlignment = Alignment.Center,
                ) { Text("Encontrar Parceiro", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp) }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.5.dp, C.border, RoundedCornerShape(14.dp))
                        .clickableRipple(openOffer)
                        .padding(vertical = 15.dp),
                    contentAlignment = Alignment.Center,
                ) { Text("Oferecer Serviço", color = C.white, fontWeight = FontWeight.SemiBold, fontSize = 14.sp) }
            }

            Column(modifier = Modifier.padding(horizontal = 22.dp).padding(top = 26.dp)) {
                SLabel("Atividade Recente")
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    ActivityRow(
                        icon = { Icon(Icons.Filled.Build, null, tint = C.purple, modifier = Modifier.size(17.dp)) },
                        iconBg = C.purple.copy(alpha = 0.12f),
                        title = "Carlos Mendes concluiu o serviço",
                        sub = "Conserto de chuveiro elétrico · R$ 120",
                        time = "há 20min",
                    )
                    ActivityRow(
                        icon = { Icon(Icons.Filled.EmojiEvents, null, tint = C.pink, modifier = Modifier.size(17.dp)) },
                        iconBg = C.pink.copy(alpha = 0.12f),
                        title = "Módulo concluído na Trilha de Blindagem 🎉",
                        sub = "Continue e desbloqueie mais matches",
                        time = "há 2h",
                    )
                }
            }
        }
    }
}

@Composable
private fun buildAnnotatedStringPro(purpleL: Color, mutedL: Color) =
    buildAnnotatedString {
        withStyle(SpanStyle(color = purpleL, fontWeight = FontWeight.ExtraBold)) {
            append("Vértice Pro")
        }
        withStyle(SpanStyle(color = mutedL)) {
            append(" — mais alcance, prioridade nos resultados e acesso antecipado a novos parceiros")
        }
    }

