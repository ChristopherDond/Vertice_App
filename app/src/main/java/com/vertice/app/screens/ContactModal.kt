package com.vertice.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Build
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.components.Avatar
import com.vertice.app.components.FLabel
import com.vertice.app.components.SInput
import com.vertice.app.components.TArea
import com.vertice.app.components.TInput
import com.vertice.app.components.clickableNoRipple
import com.vertice.app.data.Freelancer
import com.vertice.app.ui.theme.LocalColors

private val SVC_OPTS = listOf(
    "Selecionar serviço...", "Reforma / Construção", "Instalação Elétrica",
    "Instalação Hidráulica", "Pintura", "Limpeza", "Consultoria", "Outro",
)

@Composable
fun ContactModal(f: Freelancer, onClose: () -> Unit) {
    val C = LocalColors
    var service by remember { mutableStateOf(SVC_OPTS[0]) }
    var date by remember { mutableStateOf("") }
    var hour by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var urgent by remember { mutableStateOf(false) }
    var sent by remember { mutableStateOf(false) }
    val valid = service != SVC_OPTS[0] && date.isNotEmpty() && desc.length > 5

    if (sent) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(C.navy)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .background(C.green.copy(alpha = 0.12f), CircleShape)
                    .border(2.dp, C.green.copy(alpha = 0.25f), CircleShape),
                contentAlignment = Alignment.Center,
            ) { Icon(Icons.Filled.CheckCircle, null, tint = C.green, modifier = Modifier.size(42.dp)) }

            Spacer(Modifier.height(24.dp))
            Text("Solicitação enviada!", color = C.white, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
            Spacer(Modifier.height(10.dp))
            Text(
                "Aguarde a resposta de ${f.name}. Você será notificada assim que ela aceitar.",
                color = C.muted,
                fontSize = 14.sp,
                lineHeight = 23.sp,
                modifier = Modifier.widthIn(max = 240.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            )
            Spacer(Modifier.height(22.dp))
            Row(
                modifier = Modifier
                    .background(C.card, RoundedCornerShape(14.dp))
                    .border(1.dp, C.border, RoundedCornerShape(14.dp))
                    .padding(horizontal = 18.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(9.dp),
            ) {
                Box(modifier = Modifier.size(8.dp).background(C.green, CircleShape))
                Text("Aguardando confirmação", color = C.mutedL, fontSize = 13.sp)
            }
            Spacer(Modifier.height(36.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(C.purple)
                    .clickableNoRipple(onClose)
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center,
            ) { Text("Voltar ao Match", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp) }
        }
        return
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
                    .clickableNoRipple(onClose),
                contentAlignment = Alignment.Center,
            ) { Icon(Icons.Filled.ArrowBack, null, tint = C.white, modifier = Modifier.size(18.dp)) }
            Column {
                Text("Solicitar Serviço", color = C.white, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                Text("Preencha e envie para ${f.name}", color = C.muted, fontSize = 12.sp)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(top = 14.dp)
                .background(C.card, RoundedCornerShape(14.dp))
                .border(1.dp, C.border, RoundedCornerShape(14.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Avatar(initials = f.initials, size = 40, bg = f.bg)
            Column(modifier = Modifier.weight(1f)) {
                Text(f.name, color = C.white, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("${f.role} · ${f.area}", color = C.muted, fontSize = 12.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                Box(modifier = Modifier.size(7.dp).background(C.green, CircleShape))
                Text("Online", color = C.green, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp)
                .padding(top = 18.dp),
        ) {
            FLabel("Tipo de serviço *")
            SInput(value = service, onChange = { service = it }, options = SVC_OPTS, icon = { Icon(Icons.Filled.Build, null, tint = C.muted, modifier = Modifier.size(16.dp)) })
            Spacer(Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    FLabel("Data *")
                    TInput(value = date, onChange = { date = it }, placeholder = "DD/MM/AAAA", icon = { Icon(Icons.Filled.CalendarToday, null, tint = C.muted, modifier = Modifier.size(16.dp)) })
                }
                Column(modifier = Modifier.weight(1f)) {
                    FLabel("Horário")
                    TInput(value = hour, onChange = { hour = it }, placeholder = "Ex: 14h", icon = { Icon(Icons.Filled.AccessTime, null, tint = C.muted, modifier = Modifier.size(16.dp)) })
                }
            }
            Spacer(Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    FLabel("Orçamento (R$)")
                    TInput(value = price, onChange = { price = it }, placeholder = "Ex: 300", icon = { Icon(Icons.Filled.AttachMoney, null, tint = C.muted, modifier = Modifier.size(16.dp)) })
                }
                Column(modifier = Modifier.weight(1f)) {
                    FLabel("Local")
                    TInput(value = address, onChange = { address = it }, placeholder = "Bairro", icon = { Icon(Icons.Filled.LocationOn, null, tint = C.muted, modifier = Modifier.size(16.dp)) })
                }
            }
            Spacer(Modifier.height(14.dp))

            FLabel("Descrição do serviço *")
            TArea(value = desc, onChange = { desc = it }, placeholder = "Descreva o que precisa ser feito com o máximo de detalhes...", minLines = 4)
            Spacer(Modifier.height(14.dp))

            FLabel("Fotos do local (opcional)")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(C.inputBg, RoundedCornerShape(13.dp))
                    .border(1.5.dp, C.border, RoundedCornerShape(13.dp))
                    .clickableNoRipple {  }
                    .padding(14.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Icons.Filled.PhotoCamera, null, tint = C.muted, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Adicionar fotos", color = C.muted, fontSize = 13.sp)
            }
            Spacer(Modifier.height(14.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(C.card, RoundedCornerShape(13.dp))
                    .border(1.dp, C.border, RoundedCornerShape(13.dp))
                    .padding(horizontal = 14.dp, vertical = 13.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text("Serviço urgente", color = C.white, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    Text("Prioridade máxima de resposta", color = C.muted, fontSize = 11.sp)
                }
                Box(
                    modifier = Modifier
                        .width(46.dp)
                        .height(26.dp)
                        .background(if (urgent) C.pink else C.card2, RoundedCornerShape(50))
                        .border(1.dp, if (urgent) C.pink else C.border, RoundedCornerShape(50))
                        .clickableNoRipple { urgent = !urgent },
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = if (urgent) 23.dp else 3.dp, top = 3.dp)
                            .size(18.dp)
                            .background(Color.White, CircleShape),
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(C.navy)
                .border(1.dp, C.border, RoundedCornerShape(0.dp))
                .padding(horizontal = 22.dp, vertical = 14.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(if (valid) C.pink else C.card2)
                    .clickableNoRipple { if (valid) sent = true }
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                    Icon(Icons.Filled.Send, null, tint = if (valid) Color.White else C.muted, modifier = Modifier.size(18.dp))
                    Text("Enviar solicitação", color = if (valid) Color.White else C.muted, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                if (valid) "${f.name} receberá sua solicitação por notificação" else "Preencha os campos obrigatórios (*)",
                color = C.muted,
                fontSize = 11.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            )
        }
    }
}

