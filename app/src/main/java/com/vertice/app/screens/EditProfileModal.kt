package com.vertice.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.components.FLabel
import com.vertice.app.components.TArea
import com.vertice.app.components.TInput
import com.vertice.app.components.clickableNoRipple
import com.vertice.app.components.clickableRipple
import com.vertice.app.ui.theme.LocalColors
import com.vertice.app.nav.HapticFeedback
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditProfileModal(
    onClose: () -> Unit,
) {
    val C = LocalColors
    var name by rememberSaveable { mutableStateOf("Ana Silva") }
    var bio by rememberSaveable { mutableStateOf("Costureira especializada em moda sob medida e confecção feminina.") }
    var city by rememberSaveable { mutableStateOf("São Paulo, SP") }
    var skills by rememberSaveable { mutableStateOf(listOf("Costura", "Confecção", "Moda Sob Medida")) }
    var saved by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(saved) {
        if (saved) {
            delay(900)
            onClose()
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(C.navy)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(top = 50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(C.card, RoundedCornerShape(13.dp))
                    .border(1.dp, C.border, RoundedCornerShape(13.dp))
                    .clickableNoRipple {
                        onClose()
                        HapticFeedback.lightClick()
                    },
                contentAlignment = Alignment.Center,
            ) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = C.white, modifier = Modifier.size(18.dp)) }
            Text("Editar Perfil", color = C.white, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp)
                .padding(top = 24.dp),
        ) {
            Box(modifier = Modifier.fillMaxWidth().padding(bottom = 28.dp), contentAlignment = Alignment.Center) {
                Box {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Brush.linearGradient(listOf(C.purple, C.pink)), CircleShape),
                        contentAlignment = Alignment.Center,
                    ) { Text("AS", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 26.sp) }
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(28.dp)
                            .background(C.purple, CircleShape)
                            .border(2.dp, C.navy, CircleShape)
                            .clickableNoRipple { HapticFeedback.lightClick() },
                        contentAlignment = Alignment.Center,
                    ) { Icon(Icons.Filled.CameraAlt, null, tint = Color.White, modifier = Modifier.size(13.dp)) }
                }
            }

            FLabel("Nome completo")
            TInput(value = name, onChange = { name = it }, placeholder = "Seu nome", icon = { Icon(Icons.Filled.Person, null, tint = C.muted, modifier = Modifier.size(16.dp)) })
            Spacer(Modifier.height(16.dp))

            FLabel("Bio profissional")
            TArea(value = bio, onChange = { bio = it }, placeholder = "Fale sobre você...", minLines = 3)
            Spacer(Modifier.height(16.dp))

            FLabel("Cidade")
            TInput(value = city, onChange = { city = it }, placeholder = "Sua cidade", icon = { Icon(Icons.Filled.LocationOn, null, tint = C.muted, modifier = Modifier.size(16.dp)) })
            Spacer(Modifier.height(16.dp))

            FLabel("Habilidades")
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                skills.forEach { s ->
                    Row(
                        modifier = Modifier
                            .background(C.purple.copy(alpha = 0.10f), RoundedCornerShape(50))
                            .border(1.dp, C.purple.copy(alpha = 0.16f), RoundedCornerShape(50))
                            .padding(horizontal = 12.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Text(s, color = C.purple, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Icon(
                            Icons.Filled.Close, null, tint = C.purple,
                            modifier = Modifier.size(11.dp).clickableNoRipple { 
                                skills = skills - s
                                HapticFeedback.lightClick()
                            },
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .background(C.card, RoundedCornerShape(50))
                        .border(1.dp, C.border, RoundedCornerShape(50))
                        .clickableNoRipple { HapticFeedback.lightClick() }
                        .padding(horizontal = 12.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    Icon(Icons.Filled.Add, null, tint = C.muted, modifier = Modifier.size(12.dp))
                    Text("Adicionar", color = C.muted, fontSize = 12.sp)
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, C.border, RoundedCornerShape(0.dp))
                .background(C.navy)
                .padding(horizontal = 22.dp, vertical = 12.dp)
                .padding(bottom = 16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(if (saved) C.green else C.purple)
                    .clickableRipple { 
                        saved = true
                        HapticFeedback.success()
                    }
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(if (saved) Icons.Filled.CheckCircle else Icons.Filled.Edit, null, tint = Color.White, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(if (saved) "Salvo!" else "Salvar alterações", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}