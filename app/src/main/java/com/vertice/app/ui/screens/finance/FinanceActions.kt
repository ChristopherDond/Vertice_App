package com.vertice.app.ui.screens.finance

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.QrCode2
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vertice.app.R
import com.vertice.app.ui.components.FadeSlideIn
import com.vertice.app.ui.components.PrimaryButton
import com.vertice.app.ui.components.SelectableRow
import com.vertice.app.ui.components.TopBar
import com.vertice.app.ui.components.VerticeSwitch
import com.vertice.app.ui.components.VerticeTextField
import com.vertice.app.ui.theme.LocalVerticeColors

@Composable
private fun AmountHeader(label: String, amount: String, gradient: Boolean = true) {
    val colors = LocalVerticeColors.current
    Column(
        Modifier
            .fillMaxWidth()
            .then(
                if (gradient) Modifier.background(Brush.verticalGradient(listOf(colors.gradientStart, colors.gradientEnd)))
                else Modifier.background(MaterialTheme.colorScheme.surface),
            )
            .padding(horizontal = 22.dp)
            .padding(top = 16.dp, bottom = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(label, style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.82f))
        Spacer(Modifier.height(6.dp))
        Text(amount, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Black, color = Color.White)
    }
}

@Composable
fun PixScreen(onBack: () -> Unit, onDone: () -> Unit) {
    val colors = LocalVerticeColors.current
    var key by remember { mutableStateOf("ana.silva@vertice.app") }
    var amount by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TopBar(title = "Receber via PIX", onBack = onBack)
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(bottom = 30.dp)) {
            FadeSlideIn(0) { AmountHeader("Sua chave PIX", "ana.silva@vertice.app") }
            Column(Modifier.padding(horizontal = 22.dp)) {
                Spacer(Modifier.height(20.dp))
                FadeSlideIn(70) {
                    Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                        Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Escaneie para pagar", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            Spacer(Modifier.height(16.dp))
                            Box(
                                Modifier.size(180.dp).clip(RoundedCornerShape(18.dp)).background(colors.textSecondary.copy(alpha = 0.08f)),
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(Icons.Filled.QrCode2, null, tint = colors.gradientStart, modifier = Modifier.size(130.dp))
                            }
                            Spacer(Modifier.height(18.dp))
                            Surface(shape = RoundedCornerShape(13.dp), color = colors.textSecondary.copy(alpha = 0.09f), modifier = Modifier.fillMaxWidth()) {
                                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Text(key, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
                                    Icon(Icons.Filled.ContentCopy, "Copiar", tint = colors.gradientStart, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                FadeSlideIn(120) {
                    Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                        Column(Modifier.padding(18.dp)) {
                            VerticeTextField(amount, { amount = it }, "Cobrar um valor (opcional)", "R$ 0,00", keyboardType = KeyboardType.Number)
                            Spacer(Modifier.height(16.dp))
                            PrimaryButton("Gerar cobrança", Modifier.fillMaxWidth()) { onDone() }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TransferScreen(onBack: () -> Unit, onDone: () -> Unit) {
    val colors = LocalVerticeColors.current
    var key by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var schedule by remember { mutableStateOf(0) }
    var save by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TopBar(title = "Transferir", onBack = onBack)
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(bottom = 30.dp)) {
            FadeSlideIn(0) { AmountHeader("Saldo disponível", "R$ 4.820,00") }
            Column(Modifier.padding(horizontal = 22.dp)) {
                Spacer(Modifier.height(20.dp))
                FadeSlideIn(70) {
                    Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                        Column(Modifier.padding(18.dp)) {
                            VerticeTextField(key, { key = it }, "Chave PIX ou conta", "CPF, e-mail, telefone ou chave aleatória")
                            Spacer(Modifier.height(14.dp))
                            VerticeTextField(amount, { amount = it }, "Valor", "R$ 0,00", keyboardType = KeyboardType.Number)
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                FadeSlideIn(120) {
                    Column {
                        Text("Quando transferir", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(Modifier.height(10.dp))
                        SelectableRow("Agora", schedule == 0) { schedule = 0 }
                        Spacer(Modifier.height(10.dp))
                        SelectableRow("Agendar para depois", schedule == 1) { schedule = 1 }
                    }
                }
                Spacer(Modifier.height(16.dp))
                FadeSlideIn(170) {
                    Surface(shape = RoundedCornerShape(18.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text("Salvar este contato", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                                Text("Facilita transferências futuras", style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
                            }
                            VerticeSwitch(save, { save = it })
                        }
                    }
                }
                Spacer(Modifier.height(22.dp))
                FadeSlideIn(220) { PrimaryButton("Continuar", Modifier.fillMaxWidth()) { onDone() } }
            }
        }
    }
}

@Composable
fun PayScreen(onBack: () -> Unit, onDone: () -> Unit) {
    val colors = LocalVerticeColors.current
    var code by remember { mutableStateOf("") }
    var useBalance by remember { mutableStateOf(true) }
    var useCard by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TopBar(title = "Pagar", onBack = onBack)
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(bottom = 30.dp)) {
            FadeSlideIn(0) { AmountHeader("Pagar boleto ou QR Code", "R$ 0,00") }
            Column(Modifier.padding(horizontal = 22.dp)) {
                Spacer(Modifier.height(20.dp))
                FadeSlideIn(70) {
                    Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                        Column(Modifier.padding(18.dp)) {
                            VerticeTextField(code, { code = it }, "Código de barras ou QR Code", "Digite ou cole o código")
                            Spacer(Modifier.height(14.dp))
                            Surface(shape = RoundedCornerShape(14.dp), color = colors.textSecondary.copy(alpha = 0.09f)) {
                                Row(
                                    Modifier.fillMaxWidth().clickable { }.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Image(
                                        painterResource(R.drawable.ic_barcode),
                                        contentDescription = null,
                                        modifier = Modifier.size(34.dp),
                                    )
                                    Spacer(Modifier.width(12.dp))
                                    Text("Escanear com a câmera", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                                }
                            }
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                FadeSlideIn(120) {
                    Column {
                        Text("Forma de pagamento", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(Modifier.height(10.dp))
                        Surface(shape = RoundedCornerShape(18.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                            Column(Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Column(Modifier.weight(1f)) {
                                        Text("Saldo Vértice", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                                        Text("R$ 4.820,00 disponível", style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
                                    }
                                    VerticeSwitch(useBalance, { useBalance = it })
                                }
                                Spacer(Modifier.height(6.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Column(Modifier.weight(1f)) {
                                        Text("Cartão de débito", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                                        Text("•••• 4821", style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
                                    }
                                    VerticeSwitch(useCard, { useCard = it })
                                }
                            }
                        }
                    }
                }
                Spacer(Modifier.height(22.dp))
                FadeSlideIn(170) { PrimaryButton("Confirmar pagamento", Modifier.fillMaxWidth()) { onDone() } }
            }
        }
    }
}

@Composable
fun CardScreen(onBack: () -> Unit) {
    val colors = LocalVerticeColors.current
    var blocked by remember { mutableStateOf(false) }
    var online by remember { mutableStateOf(true) }
    var contactless by remember { mutableStateOf(true) }

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TopBar(title = "Cartão de débito", onBack = onBack)
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(bottom = 30.dp)) {
            Column(Modifier.padding(horizontal = 22.dp).padding(top = 10.dp)) {
                FadeSlideIn(0) {
                    Surface(shape = RoundedCornerShape(22.dp), color = Color.Transparent) {
                        Box(
                            Modifier.fillMaxWidth()
                                .background(Brush.linearGradient(listOf(colors.gradientStart, colors.gradientEnd, colors.gradientStart)))
                                .padding(22.dp),
                        ) {
                            Column {
                                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                    Text("Vértice", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = Color.White, modifier = Modifier.weight(1f))
                                    Text("DÉBITO", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.85f))
                                }
                                Spacer(Modifier.height(30.dp))
                                Text("••••  ••••  ••••  4821", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                                Spacer(Modifier.height(22.dp))
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Column {
                                        Text("TITULAR", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.72f))
                                        Text("ANA SILVA", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = Color.White)
                                    }
                                    Column {
                                        Text("VALIDADE", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.72f))
                                        Text("09/29", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
                FadeSlideIn(80) {
                    Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                        Column(Modifier.padding(16.dp)) {
                            Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.weight(1f)) {
                                    Text("Bloquear cartão", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                                    Text("Bloqueio temporário imediato", style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
                                }
                                VerticeSwitch(blocked, { blocked = it })
                            }
                            Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.weight(1f)) {
                                    Text("Compras online", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                                    Text("Permitir em lojas virtuais", style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
                                }
                                VerticeSwitch(online, { online = it })
                            }
                            Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.weight(1f)) {
                                    Text("Pagamento por aproximação", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                                    Text("Ativar NFC", style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
                                }
                                VerticeSwitch(contactless, { contactless = it })
                            }
                        }
                    }
                }
                Spacer(Modifier.height(18.dp))
                FadeSlideIn(130) {
                    Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
                        Column(Modifier.padding(18.dp)) {
                            Text("Limite de débito", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            Spacer(Modifier.height(10.dp))
                            Text("R$ 2.500,00 por dia · R$ 800,00 por compra", style = MaterialTheme.typography.bodySmall, color = colors.textSecondary)
                            Spacer(Modifier.height(14.dp))
                            VerticeTextField("2500", {}, "Ajustar limite diário", "R$ 0,00", keyboardType = KeyboardType.Number)
                        }
                    }
                }
                Spacer(Modifier.height(22.dp))
                FadeSlideIn(180) { PrimaryButton("Salvar alterações", Modifier.fillMaxWidth()) { } }
            }
        }
    }
}
