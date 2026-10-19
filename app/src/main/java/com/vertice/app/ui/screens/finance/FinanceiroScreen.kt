package com.vertice.app.ui.screens.finance

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vertice.app.core.di.ServiceLocator
import com.vertice.app.domain.model.Transaction
import com.vertice.app.domain.model.TransactionStatus
import com.vertice.app.ui.components.FadeSlideIn
import com.vertice.app.ui.theme.LocalVerticeColors
import com.vertice.app.core.result.onOk

@Composable
private fun ActionButton(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onClick() }) {
        Box(
            Modifier.size(54.dp).clip(RoundedCornerShape(17.dp)).background(Color.White.copy(alpha = 0.19f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, label, tint = Color.White, modifier = Modifier.size(24.dp))
        }
        Spacer(Modifier.height(8.dp))
        Text(label, style = MaterialTheme.typography.labelSmall, color = Color.White)
    }
}

@Composable
private fun TransactionRow(t: Transaction) {
    val colors = LocalVerticeColors.current
    val incoming = t.amountCents > 0
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier.size(40.dp).clip(RoundedCornerShape(13.dp))
                .background(if (incoming) colors.success.copy(alpha = 0.13f) else colors.error.copy(alpha = 0.13f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                if (incoming) Icons.Filled.ArrowDownward else Icons.Filled.ArrowUpward,
                null,
                tint = if (incoming) colors.success else colors.error,
                modifier = Modifier.size(19.dp),
            )
        }
        Spacer(Modifier.width(13.dp))
        Column(Modifier.weight(1f)) {
            Text(t.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
            Text("${t.dateLabel} · ${t.status.label}", style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
        }
        Text(
            (if (incoming) "+ " else "- ") + t.formattedAmount(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = if (incoming) colors.success else MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
fun FinanceiroScreen(onPix: () -> Unit, onTransfer: () -> Unit, onPay: () -> Unit, onCard: () -> Unit) {
    val colors = LocalVerticeColors.current
    var balance by remember { mutableStateOf(0L) }
    var visible by remember { mutableStateOf(true) }
    var transactions by remember { mutableStateOf<List<Transaction>>(emptyList()) }

    LaunchedEffect(Unit) {
        ServiceLocator.financeRepository.balance().onOk { balance = it.availableCents }
        ServiceLocator.financeRepository.transactions().onOk { transactions = it }
    }

    LazyColumn(
        Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 26.dp),
    ) {
        item {
            FadeSlideIn(0) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(Brush.verticalGradient(listOf(colors.gradientStart, colors.gradientEnd)))
                        .padding(horizontal = 22.dp)
                        .padding(top = 54.dp, bottom = 30.dp),
                ) {
                    Text("Saldo disponível", style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.8f))
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            if (visible) formatBrl(balance) else "R$ ••••••",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                        )
                        Spacer(Modifier.width(12.dp))
                        Icon(
                            if (visible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            "Mostrar saldo",
                            tint = Color.White.copy(alpha = 0.85f),
                            modifier = Modifier.size(22.dp).clickable { visible = !visible },
                        )
                    }
                    Spacer(Modifier.height(26.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        ActionButton(Icons.Filled.QrCode, "PIX", onPix)
                        ActionButton(Icons.Filled.Payments, "Pagar", onPay)
                        ActionButton(Icons.Filled.SwapHoriz, "Transferir", onTransfer)
                        ActionButton(Icons.Filled.CreditCard, "Cartão", onCard)
                    }
                }
            }
        }

        item {
            FadeSlideIn(90) {
                Surface(
                    Modifier.fillMaxWidth().padding(horizontal = 22.dp).padding(top = 20.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 2.dp,
                ) {
                    Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier.size(44.dp).clip(RoundedCornerShape(14.dp)).background(colors.gradientStart.copy(alpha = 0.13f)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(Icons.Filled.CreditCard, null, tint = colors.gradientStart, modifier = Modifier.size(21.dp))
                        }
                        Spacer(Modifier.width(14.dp))
                        Column(Modifier.weight(1f)) {
                            Text("Cartão de débito", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                            Text("•••• 4821 · Vértice", style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
                        }
                        Text("Gerenciar", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = colors.gradientStart, modifier = Modifier.clickable { onCard() })
                    }
                }
            }
        }

        item {
            FadeSlideIn(150) {
                Column(Modifier.padding(horizontal = 22.dp).padding(top = 24.dp)) {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text("Extrato", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(1f))
                        Text("Ver tudo", style = MaterialTheme.typography.labelMedium, color = colors.gradientStart)
                    }
                    Spacer(Modifier.height(12.dp))
                }
            }
        }

        item {
            FadeSlideIn(200) {
                Surface(
                    Modifier.fillMaxWidth().padding(horizontal = 22.dp),
                    shape = RoundedCornerShape(18.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 2.dp,
                ) {
                    Column {
                        transactions.forEachIndexed { i, t ->
                            TransactionRow(t)
                            if (i != transactions.lastIndex) {
                                androidx.compose.material3.HorizontalDivider(
                                    modifier = Modifier.padding(start = 68.dp),
                                    color = colors.textSecondary.copy(alpha = 0.13f),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun formatBrl(cents: Long): String {
    val reais = cents / 100
    val cent = Math.abs(cents % 100)
    val grouped = reais.toString().reversed().chunked(3).joinToString(".").reversed()
    return "R$ $grouped,${cent.toString().padStart(2, '0')}"
}
