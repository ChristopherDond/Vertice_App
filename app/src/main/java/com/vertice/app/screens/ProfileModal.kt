package com.vertice.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.components.Avatar
import com.vertice.app.components.SLabel
import com.vertice.app.components.clickableNoRipple
import com.vertice.app.components.clickableRipple
import com.vertice.app.data.Freelancer
import com.vertice.app.ui.theme.LocalColors

private data class Review(val name: String, val text: String, val rating: Int)
private val REVIEWS = listOf(
    Review("Mariana O.", "Serviço excelente, pontual e cuidadoso.", 5),
    Review("Fernanda L.", "Ótima profissional, recomendo muito.", 5),
    Review("Cláudia R.", "Trabalho bem feito e preço justo.", 4),
)

@Composable
fun ProfileModal(f: Freelancer, onClose: () -> Unit, onContact: () -> Unit) {
    val C = LocalColors

    Box(modifier = Modifier.fillMaxSize().background(C.navy)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(f.bg.copy(alpha = 0.8f), C.navy)))
                    .padding(horizontal = 22.dp)
                    .padding(top = 48.dp, bottom = 28.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(13.dp))
                        .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(13.dp))
                        .clickableNoRipple(onClose),
                    contentAlignment = Alignment.Center,
                ) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White, modifier = Modifier.size(18.dp)) }

                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box {
                        Avatar(initials = f.initials, size = 74, bg = f.bg, fontSize = 24)
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(14.dp)
                                .background(C.green, CircleShape)
                                .border(2.5.dp, C.navy, CircleShape),
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(f.name, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                    Spacer(Modifier.height(3.dp))
                    Text("${f.role} · ${f.area}", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)

                    Spacer(Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        StatBlock("${f.jobs}", "Serviços")
                        StatBlock("${f.rating}", "Avaliação")
                        StatBlock("${f.match}%", "Match", accent = true)
                    }
                }
            }

            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp).padding(top = 20.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(C.card, RoundedCornerShape(16.dp))
                        .border(1.dp, C.border, RoundedCornerShape(16.dp))
                        .padding(15.dp),
                ) {
                    Text(
                        "${f.bio} Trabalha com dedicação e transparência em todos os projetos.",
                        color = C.mutedL,
                        fontSize = 13.sp,
                        lineHeight = 20.8.sp,
                    )
                }
                Spacer(Modifier.height(20.dp))

                SLabel("Disponibilidade")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(C.card, RoundedCornerShape(14.dp))
                        .border(1.dp, C.border, RoundedCornerShape(14.dp))
                        .padding(horizontal = 14.dp, vertical = 13.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Box(modifier = Modifier.size(8.dp).background(C.green, CircleShape))
                    Text(f.avail, color = C.white, fontWeight = FontWeight.Medium, fontSize = 13.sp)
                }
                Spacer(Modifier.height(20.dp))

                SLabel("Avaliações recentes")
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    REVIEWS.forEach { r ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(C.card, RoundedCornerShape(14.dp))
                                .border(1.dp, C.border, RoundedCornerShape(14.dp))
                                .padding(horizontal = 14.dp, vertical = 13.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(r.name, color = C.white, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Row {
                                    repeat(r.rating) {
                                        Icon(Icons.Filled.Star, null, tint = C.amber, modifier = Modifier.size(12.dp))
                                    }
                                }
                            }
                            Spacer(Modifier.height(6.dp))
                            Text(r.text, color = C.muted, fontSize = 12.sp, lineHeight = 18.sp)
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(C.navy)
                .border(1.dp, C.border, RoundedCornerShape(0.dp))
                .padding(horizontal = 22.dp, vertical = 14.dp)
                .padding(bottom = 14.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(C.pink)
                    .clickableRipple(onContact)
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Icons.AutoMirrored.Filled.Chat, null, tint = Color.White, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Contatar ${f.name.substringBefore(" ")}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun StatBlock(value: String, label: String, accent: Boolean = false) {
    val C = LocalColors
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = if (accent) C.purple else Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
        Text(label, color = Color.White.copy(alpha = 0.5f), fontSize = 11.sp)
    }
}