package com.vertice.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.components.clickableRipple
import com.vertice.app.ui.theme.LocalColors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

private val CHIPS = listOf("Todos", "Construção", "Comércio", "Serviços", "Beleza", "Alimentação")

private val CHIP_AREAS = mapOf(
    "Construção" to listOf("Construção Civil"),
    "Comércio" to listOf("Comércio & Varejo"),
    "Serviços" to listOf("Instalações Elétr.", "Serviços Hidráulicos", "Pintura Residencial"),
    "Beleza" to listOf("Beleza & Estética"),
    "Alimentação" to listOf("Alimentação"),
)

@Composable
fun FilterBottomSheet(
    activeChips: List<String>,
    onChipsChange: (List<String>) -> Unit,
    violetaOn: Boolean,
    onVioletaChange: (Boolean) -> Unit,
    searchText: String,
    onSearchChange: (String) -> Unit,
    onClose: () -> Unit,
    onApply: () -> Unit,
) {
    val C = LocalColors
    
    fun toggleChip(c: String) {
        val newChips = when {
            c == "Todos" -> emptyList<String>()
            activeChips.contains(c) -> activeChips - c
            else -> activeChips + c
        }
        onChipsChange(newChips)
    }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .background(C.navy, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            // Handle bar
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(4.dp)
                    .background(C.border, RoundedCornerShape(50))
                    .align(Alignment.CenterHorizontally)
            )
            
            // Title
            Text("Filtros", color = C.white, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
            
            // Search
            Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(C.inputBg, RoundedCornerShape(13.dp))
                                .border(1.dp, C.border, RoundedCornerShape(13.dp))
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Icon(Icons.Filled.Search, null, tint = C.muted, modifier = Modifier.size(16.dp))
                                Box(modifier = Modifier.weight(1f)) {
                                    if (searchText.isEmpty()) Text("Buscar habilidade ou área...", color = C.muted, fontSize = 14.sp)
                                    androidx.compose.foundation.text.BasicTextField(
                                        value = searchText,
                                        onValueChange = onSearchChange,
                                        textStyle = androidx.compose.ui.text.TextStyle(color = C.white, fontSize = 14.sp),
                                        cursorBrush = androidx.compose.ui.graphics.SolidColor(C.purple),
                                        singleLine = true,
                                        modifier = Modifier.fillMaxWidth(),
                                    )
                                }
                            }
                        }
            
            // Violeta toggle
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Protocolo Violeta", color = C.white, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Box(
                    modifier = Modifier
                        .width(56.dp)
                        .height(30.dp)
                        .background(if (violetaOn) C.pink else C.card2, RoundedCornerShape(50))
                        .border(1.dp, if (violetaOn) C.pink else C.border, RoundedCornerShape(50))
                        .clickable { onVioletaChange(!violetaOn) },
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = if (violetaOn) 28.dp else 2.dp, top = 2.dp)
                            .size(24.dp)
                            .background(Color.White, CircleShape),
                    )
                }
            }
            
            // Category chips
            Text("Categorias", color = C.mutedL, fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.fillMaxWidth().padding(top = 16.dp))
            
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                CHIPS.forEach { c ->
                    val on = if (c == "Todos") activeChips.isEmpty() else activeChips.contains(c)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (on) C.purple.copy(alpha = 0.10f) else C.card)
                            .border(1.dp, if (on) C.purple.copy(alpha = 0.20f) else C.border, RoundedCornerShape(13.dp))
                            .padding(horizontal = 16.dp, vertical = 14.dp)
                            .clickable { toggleChip(c) },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            c,
                            color = if (on) C.purple else C.white,
                            fontSize = 14.sp,
                            fontWeight = if (on) FontWeight.Bold else FontWeight.Medium,
                        )
                        if (on) {
                            Icon(Icons.Filled.Check, null, tint = C.purple, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }
            
            // Active filters summary
            if (activeChips.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(C.purple.copy(alpha = 0.10f), RoundedCornerShape(13.dp))
                        .border(1.dp, C.purple.copy(alpha = 0.16f), RoundedCornerShape(13.dp))
                        .padding(16.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text("Filtrando por:", color = C.purple, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        activeChips.forEach { ch ->
                            Box(
                                modifier = Modifier
                                    .background(C.purple.copy(alpha = 0.15f), RoundedCornerShape(50))
                                    .border(1.dp, C.purple.copy(alpha = 0.20f), RoundedCornerShape(50))
                                    .padding(horizontal = 12.dp, vertical = 4.dp),
                            ) { Text(ch, color = C.purple, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                        }
                    }
                }
            }
            
            // Clear filters button
                        if (activeChips.isNotEmpty() || violetaOn || searchText.isNotEmpty()) {
                            Button(
                                onClick = {
                                    onChipsChange(emptyList())
                                    onVioletaChange(false)
                                    onSearchChange("")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = C.card,
                                    contentColor = C.pink,
                                ),
                            ) {
                                Text("Limpar todos os filtros", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                            Spacer(Modifier.height(8.dp))
                        }
            
                        // Apply button
                        Button(
                            onClick = {
                                onApply()
                                onClose()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = C.purple,
                                contentColor = Color.White,
                            ),
                        ) {
                            Text("Aplicar filtros", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
            
            Spacer(Modifier.height(24.dp))
        }
    }
}