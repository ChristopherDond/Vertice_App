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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.vertice.app.components.DateInput
import com.vertice.app.components.TimeInput
import com.vertice.app.components.CurrencyInput
import com.vertice.app.components.CurrencyMaskTransformation
import com.vertice.app.components.ValidatedTextField
import com.vertice.app.components.clickableNoRipple
import com.vertice.app.components.clickableRipple
import com.vertice.app.data.Freelancer
import com.vertice.app.ui.theme.LocalColors
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private val SVC_OPTS = listOf(
    "Selecionar serviço...", "Reforma / Construção", "Instalação Elétrica",
    "Instalação Hidráulica", "Pintura", "Limpeza", "Consultoria", "Outro",
)

@Composable
fun ContactModal(f: Freelancer, onClose: () -> Unit) {
    val C = LocalColors
    var service by rememberSaveable { mutableStateOf(SVC_OPTS[0]) }
    var date by rememberSaveable { mutableStateOf("") }
    var hour by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var desc by rememberSaveable { mutableStateOf("") }
    var urgent by rememberSaveable { mutableStateOf(false) }
    var sent by rememberSaveable { mutableStateOf(false) }

    val dateValid = date.length == 10 && com.vertice.app.components.isValidDate(date)
        val timeValid = hour.isBlank() || (hour.length == 5 && com.vertice.app.components.isValidTime(hour))
    val priceValid = price.isBlank() || CurrencyMaskTransformation.parseToCents(price) > 0
    val valid = service != SVC_OPTS[0] && dateValid && timeValid && priceValid && desc.length > 5

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
                    .clickableRipple(onClose)
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
            ) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = C.white, modifier = Modifier.size(18.dp)) }
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
            Avatar(freelancer = f, size = 40, bg = f.bg)
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
                    DateInput(
                        value = date,
                        onValueChange = { date = it },
                        label = "Data *",
                        placeholder = "DD/MM/AAAA",
                        modifier = Modifier.fillMaxWidth(),
                        minDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) }
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    FLabel("Horário")
                    TimeInput(
                        value = hour,
                        onValueChange = { hour = it },
                        label = "Horário",
                        placeholder = "HH:mm",
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
            Spacer(Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    FLabel("Orçamento (R$)")
                    CurrencyInput(
                        value = price,
                        onValueChange = { price = it },
                        label = "Orçamento (R$)",
                        placeholder = "Ex: 300",
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    FLabel("Local")
                    ValidatedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = "Local",
                        placeholder = "Bairro",
                        icon = { Icon(Icons.Filled.LocationOn, null, tint = C.muted, modifier = Modifier.size(16.dp)) },
                        modifier = Modifier.fillMaxWidth(),
                    )
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
                    .clickableNoRipple { }
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
                    .clickableRipple { if (valid) sent = true }
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                    Icon(Icons.AutoMirrored.Filled.Send, null, tint = if (valid) Color.White else C.muted, modifier = Modifier.size(18.dp))
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