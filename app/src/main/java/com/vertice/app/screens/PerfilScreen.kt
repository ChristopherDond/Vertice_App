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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.components.HDivider
import com.vertice.app.components.Pill
import com.vertice.app.components.SLabel
import com.vertice.app.components.StatusBar
import com.vertice.app.components.clickableRipple
import com.vertice.app.data.TRILHA
import com.vertice.app.nav.Screen
import com.vertice.app.ui.theme.LocalColors

@Composable
fun PerfilScreen(
    done: Set<String>,
    openEdit: () -> Unit,
    openPro: () -> Unit,
    openTrilha: () -> Unit,
) {
    val C = LocalColors

    val modMapped = TRILHA.map { m ->
        Triple(m, m.title, m.lessons.all { it.id in done })
    }
    val doneCount = modMapped.count { it.third }
    val totalLessons = TRILHA.sumOf { it.lessons.size }
    val pct = if (totalLessons == 0) 0 else done.size * 100 / totalLessons

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(C.navy)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 90.dp),
    ) {
        StatusBar()

        Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp).padding(top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // Profile avatar
                                        Box(
                                            modifier = Modifier
                                                .size(120.dp)
                                                .background(Brush.linearGradient(listOf(C.purple, C.pink)), CircleShape)
                                                .border(3.dp, C.purple.copy(alpha = 0.35f), CircleShape),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            androidx.compose.foundation.Image(
                                                painter = androidx.compose.ui.res.painterResource(com.vertice.app.R.drawable.foto_ana),
                                                contentDescription = "Ana Silva",
                                                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                                                modifier = Modifier.size(120.dp).clip(CircleShape),
                                            )
                                        }

            Spacer(Modifier.height(12.dp))
            Text("Ana Silva", color = C.white, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
            Spacer(Modifier.height(7.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                Pill(label = "MEI", color = C.purple, small = true)
                Row(
                    modifier = Modifier
                        .background(C.pink.copy(alpha = 0.08f), RoundedCornerShape(50))
                        .border(1.dp, C.pink.copy(alpha = 0.16f), RoundedCornerShape(50))
                        .padding(horizontal = 11.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    Icon(Icons.Filled.Shield, null, tint = C.pink, modifier = Modifier.size(11.dp))
                    Text("Protocolo Violeta", color = C.pink, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Brush.linearGradient(listOf(C.purple, Color(0xFF9333EA), C.pink)), RoundedCornerShape(18.dp))
                .clickableRipple(openPro)
                .padding(horizontal = 18.dp, vertical = 16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .background(Color.White.copy(alpha = 0.18f), RoundedCornerShape(13.dp))
                        .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(13.dp)),
                    contentAlignment = Alignment.Center,
                ) { Icon(Icons.Filled.WorkspacePremium, null, tint = Color.White, modifier = Modifier.size(22.dp)) }

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Vértice Pro", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                        Box(
                            modifier = Modifier
                                .background(Color.White.copy(alpha = 0.22f), RoundedCornerShape(50))
                                .padding(horizontal = 8.dp, vertical = 2.dp),
                        ) { Text("PRO", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 10.sp) }
                    }
                    Spacer(Modifier.height(2.dp))
                    Text(
                        "Desbloqueie matches ilimitados, prioridade e acesso antecipado",
                        color = Color.White.copy(alpha = 0.85f), fontSize = 12.sp, lineHeight = 17.sp,
                    )
                }
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(20.dp))
            }
        }

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp)) {
            HDivider(18)

            SLabel("Minhas Habilidades")
            Row(modifier = Modifier.padding(bottom = 20.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Costura", "Confecção", "Moda Sob Medida").forEach { Pill(label = it, color = C.purple) }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(C.card)
                    .border(1.dp, C.border, RoundedCornerShape(20.dp))
                    .clickableRipple(openTrilha)
                    .padding(18.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text("Trilha de Blindagem", color = C.white, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text("$doneCount de ${modMapped.size} módulos concluídos · $done.size de $totalLessons lições", color = C.muted, fontSize = 12.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(
                            modifier = Modifier
                                .background(Brush.linearGradient(listOf(C.purple.copy(alpha = 0.19f), C.pink.copy(alpha = 0.13f))), RoundedCornerShape(12.dp))
                                .border(1.dp, C.purple.copy(alpha = 0.19f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                        ) { Text("$pct%", color = C.purpleL, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp) }
                        Icon(Icons.Filled.ChevronRight, null, tint = C.muted, modifier = Modifier.size(16.dp))
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .height(6.dp)
                        .background(Color(0x33808080), RoundedCornerShape(50)),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(pct / 100f)
                            .fillMaxHeight()
                            .background(Brush.horizontalGradient(listOf(C.purple, C.purpleL)), RoundedCornerShape(50)),
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    modMapped.forEach { (mod, label, isModDone) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (isModDone) C.purple.copy(alpha = 0.07f) else C.glass, RoundedCornerShape(13.dp))
                                .border(1.dp, if (isModDone) C.purple.copy(alpha = 0.19f) else C.border, RoundedCornerShape(13.dp))
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(11.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(if (isModDone) C.purple.copy(alpha = 0.15f) else Color(0x1A808080), RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center,
                            ) { Icon(mod.icon, null, tint = if (isModDone) C.purpleL else C.muted, modifier = Modifier.size(15.dp)) }
                            Text(
                                label,
                                color = if (isModDone) C.white else C.muted,
                                fontWeight = if (isModDone) FontWeight.SemiBold else FontWeight.Normal,
                                fontSize = 13.sp,
                                modifier = Modifier.weight(1f),
                            )
                            if (isModDone) Icon(Icons.Filled.CheckCircle, null, tint = C.green, modifier = Modifier.size(16.dp))
                            else Icon(Icons.Filled.Circle, null, tint = C.border, modifier = Modifier.size(16.dp))
                        }
                    }
                }

                Text(
                    "Toque para ver o roadmap completo →",
                    color = C.muted,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(top = 12.dp),
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .border(1.5.dp, C.border, RoundedCornerShape(15.dp))
                    .clickableRipple(openEdit)
                    .padding(vertical = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Icons.Filled.Edit, null, tint = C.white, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text("Editar Perfil", color = C.white, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}