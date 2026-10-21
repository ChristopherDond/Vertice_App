package com.vertice.app.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.core.di.ServiceLocator
import com.vertice.app.ui.components.BreathingScale
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@Composable
fun SplashScreen(onReady: (Boolean) -> Unit) {
    var appear by remember { mutableStateOf(false) }
    val scale = BreathingScale(0.96f, 1.04f, 2200)
    LaunchedEffect(Unit) {
        appear = true
        val logged = ServiceLocator.sessionManager.session.first() != null
        delay(2100)
        onReady(logged)
    }
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF3A0B5C), Color(0xFF7E22CE), Color(0xFFEC4899)),
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .size(150.dp)
                    .scale(if (appear) scale else 0.7f)
                    .alpha(if (appear) 1f else 0f)
                    .clip(RoundedCornerShape(44.dp))
                    .background(Color.White.copy(alpha = 0.14f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    "V",
                    fontSize = 78.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                )
            }
            Spacer(Modifier.height(26.dp))
            Text(
                "Vértice",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                color = Color.White,
                modifier = Modifier.alpha(if (appear) 1f else 0f),
            )
            Spacer(Modifier.height(10.dp))
            Text(
                "Conectando empreendedores\na novas oportunidades",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.85f),
            )
        }
    }
}
