package com.vertice.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.WorkspacePremium
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.components.SLabel
import com.vertice.app.components.clickableRipple
import com.vertice.app.components.verticalMouseScroll
import com.vertice.app.nav.HapticFeedback
import com.vertice.app.ui.theme.LocalColors

private data class ProBenefit(val icon: ImageVector, val label: String, val desc: String)
private val PRO_BENEFITS = listOf(
    ProBenefit(Icons.Filled.AllInclusive, "Matches ilimitados", "Sem limite de conexões por mês"),
    ProBenefit(Icons.Filled.Bolt, "Prioridade nos resultados", "Seu perfil aparece antes dos demais"),
    ProBenefit(Icons.Filled.Group, "Acesso antecipado", "Veja novos parceiros antes de todos"),
    ProBenefit(Icons.Filled.Shield, "Protocolo Violeta Premium", "Suporte prioritário e verificação expressa"),
    ProBenefit(Icons.AutoMirrored.Filled.TrendingUp, "Relatórios de desempenho", "Acompanhe visualizações e conversões"),
    ProBenefit(Icons.Filled.WorkspacePremium, "Selo Pro no perfil", "Mais credibilidade e confiança"),
)

@Composable
fun ProModal(
    onClose: () -> Unit,
) {
    val C = LocalColors
    var subscribed by rememberSaveable { mutableStateOf(false) }

    if (subscribed) {
        Column(
            modifier = Modifier.fillMaxSize().background(C.navy).padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(Brush.linearGradient(listOf(C.purple.copy(alpha = 0.19f), C.pink.copy(alpha = 0.13f))), CircleShape)
                    .border(2.dp, C.purple.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center,
            ) { Icon(Icons.Filled.WorkspacePremium, null, tint = C.purpleL, modifier = Modifier.size(44.dp)) }

            Spacer(Modifier.height(24.dp))
            Text("Bem-vinda ao Pro! \uD83C\uDF89", color = C.white, fontWeight = FontWeight.ExtraBold, fontSize = 26.sp, textAlign = TextAlign.Center)
            Spacer(Modifier.height(10.dp))
            Text(
                "Seu plano está ativo. Aproveite todos os benefícios do Vértice Pro.",
                color = C.muted, fontSize = 14.sp, lineHeight = 23.sp, textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(max = 240.dp),
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 22.dp)
                    .background(Brush.linearGradient(listOf(C.purple.copy(alpha = 0.13f), C.pink.copy(alpha = 0.09f))), RoundedCornerShape(16.dp))
                    .border(1.dp, C.purple.copy(alpha = 0.21f), RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Próxima cobrança", color = C.mutedL, fontSize = 13.sp)
                Spacer(Modifier.height(4.dp))
                Text("R$ 19,99 em 08/08/2025", color = C.white, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(C.purple)
                    .clickableRipple {
                        onClose()
                        HapticFeedback.success()
                    }
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center,
            ) { Text("Começar a usar", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp) }
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize().background(C.navy)) {
        Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalMouseScroll(rememberScrollState()),
                ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.linearGradient(listOf(Color(0xFF3B0E8F), C.purple, C.pink.copy(alpha = 0.53f))))
                    .padding(horizontal = 24.dp)
                    .padding(top = 52.dp, bottom = 32.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(13.dp))
                        .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(13.dp))
                        .clip(RoundedCornerShape(13.dp))
                        .clickableRipple {
                            onClose()
                            HapticFeedback.lightClick()
                        },
                    contentAlignment = Alignment.Center,
                ) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White, modifier = Modifier.size(18.dp)) }

                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .background(Color.White.copy(alpha = 0.18f), CircleShape)
                            .border(1.dp, Color.White.copy(alpha = 0.25f), CircleShape),
                        contentAlignment = Alignment.Center,
                    ) { Icon(Icons.Filled.WorkspacePremium, null, tint = Color.White, modifier = Modifier.size(32.dp)) }

                    Spacer(Modifier.height(12.dp))
                    Text("Vértice Pro", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                    Spacer(Modifier.height(4.dp))
                    Text("Desbloqueie todo o potencial da plataforma", color = Color.White.copy(alpha = 0.65f), fontSize = 13.sp)

                    Box(
                        modifier = Modifier
                            .padding(top = 14.dp)
                            .background(Color.White.copy(alpha = 0.18f), RoundedCornerShape(50))
                            .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(50))
                            .padding(horizontal = 20.dp, vertical = 6.dp),
                    ) { Text("\u2726 Mais de 3.000 usuárias Pro", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp) }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .padding(top = 20.dp)
                    .background(Brush.linearGradient(listOf(C.purple.copy(alpha = 0.15f), C.pink.copy(alpha = 0.10f))), RoundedCornerShape(20.dp))
                    .border(1.dp, C.purple.copy(alpha = 0.25f), RoundedCornerShape(20.dp))
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text("PLANO MENSAL", color = C.muted, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("R$", color = C.mutedL, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text("19", color = C.white, fontWeight = FontWeight.ExtraBold, fontSize = 36.sp)
                        Text(",99", color = C.white, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
                    }
                    Text("por mês · cancele quando quiser", color = C.muted, fontSize = 12.sp)
                }
                Column(
                    modifier = Modifier
                        .background(C.pink.copy(alpha = 0.13f), RoundedCornerShape(14.dp))
                        .border(1.dp, C.pink.copy(alpha = 0.21f), RoundedCornerShape(14.dp))
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("ECONOMIA", color = C.pink, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    Text("40%", color = C.pink, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                    Text("vs. avulso", color = C.muted, fontSize = 10.sp)
                }
            }

            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp).padding(top = 22.dp)) {
                SLabel("O que você ganha")
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    PRO_BENEFITS.forEach { b ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(C.card, RoundedCornerShape(15.dp))
                                .border(1.dp, C.border, RoundedCornerShape(15.dp))
                                .padding(horizontal = 14.dp, vertical = 13.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(13.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(C.purple.copy(alpha = 0.13f), RoundedCornerShape(12.dp))
                                    .border(1.dp, C.purple.copy(alpha = 0.16f), RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center,
                            ) { Icon(b.icon, null, tint = C.purpleL, modifier = Modifier.size(18.dp)) }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(b.label, color = C.white, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(b.desc, color = C.muted, fontSize = 12.sp)
                            }
                            Icon(Icons.Filled.CheckCircle, null, tint = C.green, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .padding(top = 16.dp, bottom = 24.dp)
                    .background(C.card, RoundedCornerShape(14.dp))
                    .border(1.dp, C.border, RoundedCornerShape(14.dp))
                    .padding(horizontal = 16.dp, vertical = 13.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(Icons.Filled.Shield, null, tint = C.muted, modifier = Modifier.size(16.dp))
                Text(
                    "Cobrança recorrente mensal. Sem fidelidade. Cancele a qualquer momento em Perfil → Minha Assinatura.",
                    color = C.muted, fontSize = 12.sp, lineHeight = 18.sp,
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(C.navy)
                .border(1.dp, C.border, RoundedCornerShape(0.dp))
                .padding(horizontal = 22.dp, vertical = 14.dp)
                .padding(bottom = 16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(Brush.linearGradient(listOf(C.purple, C.pink)))
                    .clickableRipple {
                        subscribed = true
                        HapticFeedback.heavyClick()
                    }
                    .padding(vertical = 17.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Icons.Filled.WorkspacePremium, null, tint = Color.White, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(9.dp))
                Text("Assinar por R$ 19,99/mês", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
            }
        }
    }
}