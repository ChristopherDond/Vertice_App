package com.vertice.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.components.Avatar
import com.vertice.app.components.FilterBottomSheet
import com.vertice.app.components.Pill
import com.vertice.app.components.StatusBar
import com.vertice.app.components.clickableRipple
import com.vertice.app.data.FREELANCERS_SORTED
import com.vertice.app.data.Freelancer
import com.vertice.app.ui.theme.LocalColors

private val CHIPS = listOf("Todos", "Construção", "Comércio", "Serviços", "Beleza", "Alimentação")

private val CHIP_AREAS = mapOf(
    "Construção" to listOf("Construção Civil", "Construção"),
    "Comércio" to listOf("Comércio & Varejo", "Consultoria"),
    "Serviços" to listOf("Instalações Elétr.", "Serviços Hidráulicos", "Pintura Residencial", "Serviços"),
    "Beleza" to listOf("Beleza & Estética", "Beleza"),
    "Alimentação" to listOf("Alimentação"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchScreen(
    violetaOn: Boolean,
    onContact: (Freelancer) -> Unit,
    onProfile: (Freelancer) -> Unit,
) {
    val C = LocalColors
    var activeChips by rememberSaveable { mutableStateOf(listOf("Construção", "Comércio")) }
    var search by rememberSaveable { mutableStateOf("") }
    var showFilters by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    fun toggleChip(c: String) {
        activeChips = when {
            c == "Todos" -> emptyList()
            activeChips.contains(c) -> activeChips - c
            else -> activeChips + c
        }
    }

    val filtered = remember(violetaOn, search, activeChips) {
        FREELANCERS_SORTED.filter { f ->
            if (violetaOn && f.gender == "m") return@filter false
            if (activeChips.isNotEmpty()) {
                val areas = activeChips.flatMap { CHIP_AREAS[it] ?: emptyList() }
                if (areas.isNotEmpty() && f.area !in areas) return@filter false
            }
            if (search.isNotEmpty() &&
                !f.name.lowercase().contains(search.lowercase()) &&
                !f.area.lowercase().contains(search.lowercase()) &&
                !f.role.lowercase().contains(search.lowercase())
            ) return@filter false
            true
        }
    }

    if (showFilters) {
        ModalBottomSheet(
            onDismissRequest = { showFilters = false },
            sheetState = sheetState,
            containerColor = C.navy,
        ) {
            FilterBottomSheet(
                activeChips = activeChips,
                onChipsChange = { activeChips = it },
                violetaOn = violetaOn,
                onVioletaChange = { },
                searchText = search,
                onSearchChange = { search = it },
                onClose = { showFilters = false },
                onApply = { showFilters = false },
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(C.navy)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 90.dp),
        ) {
            StatusBar()

            if (violetaOn) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp)
                        .padding(bottom = 4.dp)
                        .background(C.pink.copy(alpha = 0.10f), RoundedCornerShape(13.dp))
                        .border(1.dp, C.purple.copy(alpha = 0.20f), RoundedCornerShape(13.dp))
                        .padding(horizontal = 14.dp, vertical = 9.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(9.dp),
                ) {
                    Icon(Icons.Filled.Shield, null, tint = C.pink, modifier = Modifier.size(14.dp))
                    Text("Protocolo Violeta ativo — exibindo apenas prestadoras", color = C.pink, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                Column {
                    Text("Match", color = C.white, fontWeight = FontWeight.ExtraBold, fontSize = 23.sp)
                    Spacer(Modifier.height(3.dp))
                    Text(
                        "Encontre parceiros de negócio, prestadores ou apoio para sua gestão",
                        color = C.muted,
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        modifier = Modifier.widthIn(max = 230.dp),
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (activeChips.isNotEmpty()) {
                        Box(
                            modifier = Modifier.background(C.purple, RoundedCornerShape(50)).padding(horizontal = 10.dp, vertical = 3.dp),
                        ) { Text("${activeChips.size}", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    }
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(C.card, RoundedCornerShape(13.dp))
                            .border(1.dp, C.border, RoundedCornerShape(13.dp))
                            .clickable { showFilters = true },
                        contentAlignment = Alignment.Center,
                    ) { Icon(Icons.Filled.FilterList, null, tint = if (activeChips.isNotEmpty()) C.purple else C.mutedL, modifier = Modifier.size(17.dp)) }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .padding(top = 14.dp)
                    .background(C.card, RoundedCornerShape(14.dp))
                    .border(1.dp, C.border, RoundedCornerShape(14.dp))
                    .padding(horizontal = 16.dp, vertical = 13.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(Icons.Filled.Search, null, tint = C.muted, modifier = Modifier.size(16.dp))
                Box(modifier = Modifier.weight(1f)) {
                    if (search.isEmpty()) Text("Buscar habilidade ou área...", color = C.muted, fontSize = 14.sp)
                    BasicTextField(
                        value = search,
                        onValueChange = { search = it },
                        textStyle = TextStyle(color = C.white, fontSize = 14.sp),
                        cursorBrush = SolidColor(C.purple),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 22.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                CHIPS.forEach { c ->
                    val on = if (c == "Todos") activeChips.isEmpty() else activeChips.contains(c)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (on) C.purple else C.card)
                            .let { if (!on) it.border(1.dp, C.border, RoundedCornerShape(50)) else it }
                            .clickableRipple { toggleChip(c) }
                            .padding(horizontal = 17.dp, vertical = 8.dp),
                    ) {
                        Text(
                            c,
                            color = if (on) Color.White else C.muted,
                            fontSize = 13.sp,
                            fontWeight = if (on) FontWeight.Bold else FontWeight.Medium,
                        )
                    }
                }
            }

            if (activeChips.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp)
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(7.dp),
                ) {
                    Text("Filtrando por:", color = C.muted, fontSize = 12.sp, modifier = Modifier.padding(top = 3.dp))
                    activeChips.forEach { ch ->
                        Box(
                            modifier = Modifier
                                .background(C.purple.copy(alpha = 0.10f), RoundedCornerShape(50))
                                .border(1.dp, C.purple.copy(alpha = 0.16f), RoundedCornerShape(50))
                                .padding(horizontal = 10.dp, vertical = 2.dp),
                        ) { Text(ch, color = C.purpleL, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                filtered.forEach { f ->
                    FCard(
                        f = f,
                        onContact = { onContact(f) },
                        onProfile = { onProfile(f) },
                    )
                }
                if (filtered.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(C.card, RoundedCornerShape(20.dp))
                            .border(1.dp, C.border, RoundedCornerShape(20.dp))
                            .padding(horizontal = 20.dp, vertical = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(if (violetaOn) Icons.Filled.Shield else Icons.Filled.Search, null, tint = if (violetaOn) C.pink else C.muted, modifier = Modifier.size(32.dp))
                        Spacer(Modifier.height(12.dp))
                        Text(
                            if (violetaOn) "Protocolo Violeta ativo" else "Nenhum resultado encontrado",
                            color = C.white,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            if (violetaOn) "Nenhuma prestadora encontrada nesta área. Tente outro filtro."
                            else "Tente ajustar a busca ou os filtros selecionados.",
                            color = C.muted,
                            fontSize = 13.sp,
                            lineHeight = 19.sp,
                            modifier = Modifier.widthIn(max = 260.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FCard(
    f: Freelancer,
    onContact: () -> Unit,
    onProfile: () -> Unit,
) {
    val C = LocalColors

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(20.dp), ambientColor = Color.Black.copy(alpha = 0.3f), spotColor = Color.Black.copy(alpha = 0.3f))
            .background(C.card, RoundedCornerShape(20.dp))
            .border(1.dp, C.border, RoundedCornerShape(20.dp))
            .padding(18.dp),
    ) {
        Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(13.dp)) {
            Box {
                Avatar(freelancer = f, size = 50, fontSize = 16)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(10.dp)
                        .background(C.green, CircleShape)
                        .border(2.dp, C.card, CircleShape),
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(f.name, color = C.white, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(Modifier.height(2.dp))
                Text(f.role, color = C.mutedL, fontSize = 13.sp)
                Spacer(Modifier.height(7.dp))
                Pill(label = f.area, color = C.purple)
            }
            Column(
                modifier = Modifier
                    .background(C.purple.copy(alpha = 0.10f), RoundedCornerShape(14.dp))
                    .border(1.dp, C.purple.copy(alpha = 0.16f), RoundedCornerShape(14.dp))
                    .padding(horizontal = 11.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("${f.match}%", color = C.purple, fontWeight = FontWeight.ExtraBold, fontSize = 19.sp)
                Text("match", color = C.muted, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        Text(f.bio, color = C.muted, fontSize = 12.sp, lineHeight = 18.6.sp, modifier = Modifier.padding(top = 12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .background(C.glass, RoundedCornerShape(11.dp))
                .border(1.dp, C.border, RoundedCornerShape(11.dp))
                .padding(horizontal = 12.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                Icon(Icons.Filled.Star, null, tint = C.amber, modifier = Modifier.size(13.dp))
                Text("${f.rating}", color = C.white, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
            Box(modifier = Modifier.width(1.dp).height(13.dp).background(C.border))
            Text("${f.jobs} serviços", color = C.muted, fontSize = 12.sp)
            Box(modifier = Modifier.width(1.dp).height(13.dp).background(C.border))
            Text(f.avail, color = C.green, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
        }

        Row(modifier = Modifier.fillMaxWidth().padding(top = 13.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.5.dp, C.border, RoundedCornerShape(12.dp))
                    .clickableRipple(onProfile)
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center,
            ) { Text("Ver perfil", color = C.white, fontWeight = FontWeight.SemiBold, fontSize = 13.sp) }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(C.pink)
                    .clickableRipple(onContact)
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Icons.AutoMirrored.Filled.Chat, null, tint = Color.White, modifier = Modifier.size(15.dp))
                Spacer(Modifier.width(6.dp))
                Text("Contatar", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }
    }
}