package com.vertice.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.components.clickableRipple
import com.vertice.app.components.verticalMouseScroll
import com.vertice.app.nav.Screen
import com.vertice.app.ui.theme.LocalColors
import com.vertice.app.nav.HapticFeedback

private data class VFItem(val icon: ImageVector, val label: String, val color: Color)
private val VF = listOf(
    VFItem(Icons.Filled.Group, "Só de mulher pra mulher", Color(0xFF7C3AED)),
    VFItem(Icons.Filled.Person, "Identidade verificada", Color(0xFF9B5FF7)),
    VFItem(Icons.Filled.LocationOn, "Rota segura", Color(0xFFEC4899)),
    VFItem(Icons.Filled.Warning, "Botão de emergência", Color(0xFFF59E0B)),
)
private val VERIFICATIONS = listOf(
    "Identidade verificada" to "jan 2025",
    "Telefone confirmado" to "jan 2025",
    "Protocolo Violeta" to "mar 2025",
)
private data class OverlayItem(val icon: ImageVector, val label: String)
private val OVERLAY_ITEMS = listOf(
    OverlayItem(Icons.Filled.Group, "Rede 100% feminina ativa"),
    OverlayItem(Icons.Filled.Lock, "Identidade verificada"),
    OverlayItem(Icons.Filled.LocationOn, "Rota segura disponível"),
    OverlayItem(Icons.Filled.Call, "Suporte 24h habilitado"),
)

@Composable
fun VioletaScreen(
    on: Boolean,
    setOn: (Boolean) -> Unit,
) {
    val C = LocalColors

    Box(modifier = Modifier.fillMaxSize().background(C.navy)) {
        Column(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.linearGradient(listOf(C.purple, Color(0xFF9333EA), C.pink)))
                    .padding(horizontal = 24.dp)
                    .padding(top = 46.dp, bottom = 22.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color.White.copy(alpha = 0.2f), CircleShape)
                        .border(1.dp, Color.White.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center,
                ) { Icon(Icons.Filled.Shield, null, tint = Color.White, modifier = Modifier.size(26.dp)) }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Protocolo Violeta", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                    Spacer(Modifier.height(3.dp))
                    Text(
                        "Ambiente exclusivamente feminino, seguro e verificado",
                        color = Color.White.copy(alpha = 0.65f),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                    )
                }

                if (on) {
                    Row(
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(50))
                            .border(1.dp, Color.White.copy(alpha = 0.35f), RoundedCornerShape(50))
                            .padding(horizontal = 14.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(7.dp),
                    ) {
                        Box(modifier = Modifier.size(6.dp).background(Color(0xFF4ADE80), CircleShape))
                        Text("Protocolo ativo", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }

            Column(
                            modifier = Modifier
                                .weight(1f)
                                .verticalMouseScroll(rememberScrollState())
                                .padding(horizontal = 22.dp)
                                .padding(top = 16.dp)
                                .padding(bottom = 88.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                    VFCard(VF[0], on, Modifier.weight(1f))
                    VFCard(VF[1], on, Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                    VFCard(VF[2], on, Modifier.weight(1f))
                    VFCard(VF[3], on, Modifier.weight(1f))
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(C.card, RoundedCornerShape(14.dp))
                        .border(1.dp, C.border, RoundedCornerShape(14.dp))
                        .padding(horizontal = 14.dp, vertical = 4.dp),
                ) {
                    VERIFICATIONS.forEachIndexed { i, (label, date) ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(Icons.Filled.CheckCircle, null, tint = C.green, modifier = Modifier.size(14.dp))
                                Text(label, color = C.white, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            }
                            Text("Verificado em $date", color = C.muted, fontSize = 11.sp)
                        }
                        if (i < VERIFICATIONS.size - 1) {
                            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(C.border))
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .background(
                            if (on) Brush.linearGradient(listOf(Color(0xFF059669), Color(0xFF10B981)))
                            else Brush.linearGradient(listOf(C.pink, Color(0xFFF472B6))),
                        )
                        .clickableRipple { 
                            setOn(!on)
                            HapticFeedback.mediumClick()
                        }
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (on) Icon(Icons.Filled.CheckCircle, null, tint = Color.White, modifier = Modifier.size(18.dp))
                        Text(
                            if (on) "Você está protegida" else "Ativar proteção agora",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(Icons.Filled.Shield, null, tint = C.muted, modifier = Modifier.size(12.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Verificado pelo Vértice", color = C.muted, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                }
            }
        }

        if (on) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xF54A1095), Color(0xFF2D0D6E), C.navy.copy(alpha = 0.97f)),
                        ),
                    )
                    .padding(horizontal = 32.dp)
                    .padding(top = 24.dp, bottom = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Box(modifier = Modifier.size(110.dp), contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier.size(110.dp).border(1.5.dp, Color.White.copy(alpha = 0.07f), CircleShape))
                    Box(modifier = Modifier.size(84.dp).border(1.5.dp, Color.White.copy(alpha = 0.14f), CircleShape))
                    Box(
                        modifier = Modifier.size(60.dp).border(1.5.dp, Color.White.copy(alpha = 0.35f), CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(Brush.linearGradient(listOf(C.purple, C.pink)), CircleShape),
                            contentAlignment = Alignment.Center,
                        ) { Icon(Icons.Filled.Shield, null, tint = Color.White, modifier = Modifier.size(26.dp)) }
                    }
                }

                Spacer(Modifier.height(28.dp))
                Text("Você está protegida", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, textAlign = TextAlign.Center)
                Spacer(Modifier.height(8.dp))
                Text(
                    "Seus matches são exclusivamente de mulher para mulher.",
                    color = Color.White.copy(alpha = 0.55f),
                    fontSize = 13.sp,
                    lineHeight = 21.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.widthIn(max = 230.dp),
                )

                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    OVERLAY_ITEMS.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White.copy(alpha = 0.08f), RoundedCornerShape(13.dp))
                                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(13.dp))
                                .padding(horizontal = 13.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(11.dp),
                        ) {
                            Box(
                                modifier = Modifier.size(30.dp).background(C.purple.copy(alpha = 0.25f), RoundedCornerShape(9.dp)),
                                contentAlignment = Alignment.Center,
                            ) { Icon(item.icon, null, tint = Color.White, modifier = Modifier.size(14.dp)) }
                            Text(item.label, color = Color.White, fontWeight = FontWeight.Medium, fontSize = 13.sp, modifier = Modifier.weight(1f))
                            Icon(Icons.Filled.CheckCircle, null, tint = Color(0xFF4ADE80), modifier = Modifier.size(14.dp))
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(top = 22.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White.copy(alpha = 0.1f))
                        .border(1.5.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(14.dp))
                        .clickableRipple { 
                            setOn(false)
                            HapticFeedback.lightClick()
                        }
                        .padding(horizontal = 32.dp, vertical = 12.dp),
                ) { Text("Desativar proteção", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 14.sp) }
            }
        }
    }
}

@Composable
private fun VFCard(item: VFItem, on: Boolean, modifier: Modifier = Modifier) {
    val C = LocalColors
    Column(
        modifier = modifier
            .background(C.card, RoundedCornerShape(16.dp))
            .border(1.dp, if (on) item.color.copy(alpha = 0.20f) else C.border, RoundedCornerShape(16.dp))
            .padding(horizontal = 13.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(item.color.copy(alpha = 0.10f), RoundedCornerShape(11.dp))
                .border(1.dp, item.color.copy(alpha = 0.16f), RoundedCornerShape(11.dp)),
            contentAlignment = Alignment.Center,
        ) { Icon(item.icon, null, tint = item.color, modifier = Modifier.size(17.dp)) }
        Text(item.label, color = C.white, fontWeight = FontWeight.Bold, fontSize = 12.sp, lineHeight = 16.sp)
        if (on) Icon(Icons.Filled.CheckCircle, null, tint = item.color, modifier = Modifier.size(14.dp))
    }
}