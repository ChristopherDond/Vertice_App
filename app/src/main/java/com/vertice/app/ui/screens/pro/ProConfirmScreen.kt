package com.vertice.app.ui.screens.pro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vertice.app.ui.components.BreathingScale
import com.vertice.app.ui.components.PrimaryButton
import com.vertice.app.ui.theme.LocalVerticeColors
import kotlinx.coroutines.delay

@Composable
fun ProConfirmScreen(onGoHome: () -> Unit) {
    val colors = LocalVerticeColors.current
    var appear by remember { mutableStateOf(false) }
    val pulse = BreathingScale(0.94f, 1.06f, 1900)
    LaunchedEffect(Unit) {
        delay(120)
        appear = true
    }
    Box(
        Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(colors.gradientStart, colors.gradientEnd))),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            Modifier.fillMaxWidth().padding(horizontal = 34.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                Modifier
                    .size(112.dp)
                    .scale(if (appear) pulse else 0.6f)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Filled.Check, null, tint = Color.White, modifier = Modifier.size(58.dp))
            }
            Spacer(Modifier.height(28.dp))
            Text(
                "Você é Pro!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Black,
                color = Color.White,
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "Seu perfil já está em destaque nas buscas e todos os recursos exclusivos foram liberados.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(30.dp))
            Surface(shape = RoundedCornerShape(16.dp), color = Color.White.copy(alpha = 0.18f)) {
                Column(Modifier.padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Plano ativo", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.8f))
                    Text("Vértice Pro · Mensal", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(Modifier.height(4.dp))
                    Text("Renova em 19/11/2026", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.8f))
                }
            }
            Spacer(Modifier.height(30.dp))
            PrimaryButton("Voltar ao início", Modifier.fillMaxWidth()) { onGoHome() }
        }
    }
}
