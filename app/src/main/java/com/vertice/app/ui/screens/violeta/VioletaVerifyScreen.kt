package com.vertice.app.ui.screens.violeta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vertice.app.ui.components.FadeSlideIn
import com.vertice.app.ui.components.PrimaryButton
import com.vertice.app.ui.components.TopBar
import com.vertice.app.ui.theme.LocalVerticeColors

@Composable
private fun VerifyStep(icon: ImageVector, title: String, body: String, done: Boolean, delay: Int, onClick: () -> Unit) {
    val colors = LocalVerticeColors.current
    FadeSlideIn(delay) {
        Surface(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
        ) {
            Column(Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.size(44.dp).clip(RoundedCornerShape(14.dp))
                            .background(if (done) colors.success.copy(alpha = 0.15f) else colors.gradientStart.copy(alpha = 0.13f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            if (done) Icons.Filled.Check else icon,
                            null,
                            tint = if (done) colors.success else colors.gradientStart,
                            modifier = Modifier.size(21.dp),
                        )
                    }
                    Spacer(Modifier.width(14.dp))
                    Column(Modifier.weight(1f)) {
                        Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Text(if (done) "Concluído" else body, style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
                    }
                    if (!done) Text("›", style = MaterialTheme.typography.titleLarge, color = colors.textSecondary)
                }
            }
        }
    }
}

@Composable
fun VioletaVerifyScreen(onBack: () -> Unit, onFinish: () -> Unit) {
    val colors = LocalVerticeColors.current
    var step1 by remember { mutableStateOf(true) }
    var step2 by remember { mutableStateOf(false) }
    var step3 by remember { mutableStateOf(false) }
    val progress = if (step3) 1f else if (step2) 0.66f else if (step1) 0.33f else 0f

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TopBar(title = "Verificação", onBack = onBack)
        Column(Modifier.fillMaxSize().padding(horizontal = 22.dp)) {
            FadeSlideIn(0) {
                Column {
                    Text("Verifique sua identidade", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Todos os dados são criptografados e usados apenas para a segurança da rede Violeta.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.textSecondary,
                    )
                    Spacer(Modifier.height(20.dp))
                    androidx.compose.material3.LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                        color = colors.gradientStart,
                        trackColor = colors.textSecondary.copy(alpha = 0.18f),
                    )
                }
            }
            Spacer(Modifier.height(22.dp))
            VerifyStep(Icons.Filled.Person, "Dados pessoais", "Nome, CPF e data de nascimento", step1, 70) { step1 = true }
            VerifyStep(Icons.Filled.CameraAlt, "Reconhecimento facial", "Uma selfie rápida para confirmar", step2, 120) { step2 = true }
            VerifyStep(Icons.Filled.Badge, "Documento com foto", "RG ou CNH em boa qualidade", step3, 170) { step3 = true }
            Spacer(Modifier.height(14.dp))
            FadeSlideIn(220) {
                Surface(shape = RoundedCornerShape(16.dp), color = colors.gradientStart.copy(alpha = 0.08f)) {
                    Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("🛡", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.width(10.dp))
                        Text(
                            "Sua verificação é analisada em até 24h. Você recebe uma notificação quando estiver pronta.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            PrimaryButton("Enviar verificação", Modifier.fillMaxWidth()) {
                step1 = true; step2 = true; step3 = true; onFinish()
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
