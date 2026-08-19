package com.vertice.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.filled.Sell
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.components.FLabel
import com.vertice.app.components.SInput
import com.vertice.app.components.TArea
import com.vertice.app.components.TInput
import com.vertice.app.components.clickableNoRipple
import com.vertice.app.components.clickableRipple
import com.vertice.app.components.verticalMouseScroll
import com.vertice.app.nav.HapticFeedback
import com.vertice.app.ui.theme.LocalColors

private val CATEGORY_OPTS = listOf(
    "Selecionar categoria...",
    "Costura & Moda", "Construção Civil", "Instalação Elétrica", "Instalação Hidráulica",
    "Pintura", "Limpeza", "Consultoria", "Beleza & Estética", "Alimentação", "Outro",
)
private val AVAIL_OPTS = listOf(
    "Disponibilidade...", "Disponível agora", "Disponível amanhã", "Disponível esta semana",
    "Fins de semana", "Somente agendado",
)

@Composable
fun OfferModal(
    onClose: () -> Unit,
) {
    val C = LocalColors
    var category by rememberSaveable { mutableStateOf(CATEGORY_OPTS[0]) }
    var title by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }
    var avail by rememberSaveable { mutableStateOf(AVAIL_OPTS[0]) }
    var desc by rememberSaveable { mutableStateOf("") }
    var published by rememberSaveable { mutableStateOf(false) }
    val valid = category != CATEGORY_OPTS[0] && title.length > 2 && desc.length > 10

    if (published) {
        Column(
            modifier = Modifier.fillMaxSize().background(C.navy).padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .background(C.purple.copy(alpha = 0.12f), RoundedCornerShape(50))
                    .border(2.dp, C.purple.copy(alpha = 0.25f), RoundedCornerShape(50)),
                contentAlignment = Alignment.Center,
            ) { Icon(Icons.Filled.WorkspacePremium, null, tint = C.purpleL, modifier = Modifier.size(42.dp)) }

            Spacer(Modifier.height(24.dp))
            Text("Serviço publicado!", color = C.white, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
            Spacer(Modifier.height(10.dp))
            Text(
                "Seu serviço \"$title\" já aparece nos resultados de Match para quem busca na sua área.",
                color = C.muted, fontSize = 14.sp, lineHeight = 23.sp,
                modifier = Modifier.widthIn(max = 250.dp), textAlign = TextAlign.Center,
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
                Box(modifier = Modifier.size(8.dp).background(C.green, RoundedCornerShape(50)))
                Text("Aguardando candidaturas", color = C.mutedL, fontSize = 13.sp)
            }
            Spacer(Modifier.height(36.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(C.purple)
                    .clickableRipple {
                        onClose()
                        HapticFeedback.success()
                    }
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center,
            ) { Text("Voltar ao início", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp) }
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
                    .clickableNoRipple {
                        onClose()
                        HapticFeedback.lightClick()
                    },
                contentAlignment = Alignment.Center,
            ) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = C.white, modifier = Modifier.size(18.dp)) }
            Column {
                Text("Oferecer Serviço", color = C.white, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                Text("Divulgue seu trabalho para quem busca na sua área", color = C.muted, fontSize = 12.sp)
            }
        }

        Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalMouseScroll(rememberScrollState())
                        .padding(horizontal = 22.dp)
                        .padding(top = 20.dp),
                ) {
            FLabel("Categoria *")
            SInput(value = category, onChange = { category = it }, options = CATEGORY_OPTS, icon = { Icon(Icons.Filled.Sell, null, tint = C.muted, modifier = Modifier.size(16.dp)) })
            Spacer(Modifier.height(14.dp))

            FLabel("Título do serviço *")
            TInput(value = title, onChange = { title = it }, placeholder = "Ex: Costura sob medida e ajustes", icon = { Icon(Icons.Filled.WorkspacePremium, null, tint = C.muted, modifier = Modifier.size(16.dp)) })
            Spacer(Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    FLabel("Preço (R$)")
                    TInput(value = price, onChange = { price = it }, placeholder = "Ex: 80", icon = { Icon(Icons.Filled.AttachMoney, null, tint = C.muted, modifier = Modifier.size(16.dp)) })
                }
                Column(modifier = Modifier.weight(1f)) {
                    FLabel("Cidade")
                    TInput(value = city, onChange = { city = it }, placeholder = "São Paulo, SP")
                }
            }
            Spacer(Modifier.height(14.dp))

            FLabel("Disponibilidade")
            SInput(value = avail, onChange = { avail = it }, options = AVAIL_OPTS)
            Spacer(Modifier.height(14.dp))

            FLabel("Descrição do serviço *")
            TArea(value = desc, onChange = { desc = it }, placeholder = "Explique o que você faz, materiais, prazos e diferenciais...", minLines = 4)
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
                    .background(if (valid) C.purple else C.card2)
                    .clickableRipple {
                        if (valid) {
                            published = true
                            HapticFeedback.success()
                        }
                    }
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                    Icon(Icons.Filled.CheckCircle, null, tint = if (valid) Color.White else C.muted, modifier = Modifier.size(18.dp))
                    Text("Publicar serviço", color = if (valid) Color.White else C.muted, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                if (valid) "Seu anúncio aparecerá para usuárias buscando esta categoria" else "Preencha categoria, título e descrição (*)",
                color = C.muted, fontSize = 11.sp, modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}