package com.vertice.app.ui.screens.figma

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import com.vertice.app.R
import com.vertice.app.ui.design.Inter
import com.vertice.app.ui.design.Roboto
import com.vertice.app.ui.design.VColor
import com.vertice.app.ui.design.vs
import com.vertice.app.ui.icons.IcoAkarIconsCircleCheckFill
import com.vertice.app.ui.components.vClick
import com.vertice.app.ui.components.PressWhite
import com.vertice.app.ui.components.PressAccent
import com.vertice.app.ui.components.PressSoft
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.alpha

private val FieldBg = Color(0x99FFFFFF)
private val CardBg = Color(0xFFFFF9F9)
private val SheetBg = Color(0xFFF1E9FD)
private val BarBg = Color(0xFFF2EEF9)
private val Hint = Color(0x7A4B4040)
private val Label = Color(0xCC000000)
private val FilterBg = Color(0xFFEFE9F7)
private val LineGray = Color(0xFFD9D9D9)
private val OnlineGreen = Color(0xFF28D206)
private val GreenDot = Color(0xFF28D206)

@Composable
private fun FText(
    text: String,
    x: Int,
    y: Int,
    w: Int,
    size: Float,
    weight: Int,
    color: Color = VColor.Ink,
    family: FontFamily = Inter,
) {
    Text(
        text = text,
        modifier = Modifier.offset(x.dp, y.dp).width(w.dp),
        maxLines = 3,
        style = vs(size, weight, size * 1.3f, 0f, family),
        color = color,
    )
}

@Composable
private fun VLabel(text: String, x: Int, y: Int) {
    Text(
        text = text,
        modifier = Modifier.offset(x.dp, y.dp),
        style = vs(12f, 500, 15f, 0f, Inter),
        color = Label,
    )
}

@Composable
private fun VField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    x: Int,
    y: Int,
    w: Int,
    h: Int,
    leading: Int? = null,
    leadingW: Int = 16,
    trailing: Int? = null,
    singleLine: Boolean = true,
) {
    Box(
        Modifier
            .offset(x.dp, y.dp)
            .width(w.dp)
            .height(h.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(FieldBg),
    ) {
        if (leading != null) {
            Image(
                painterResource(leading),
                contentDescription = null,
                modifier = Modifier.offset(10.dp, ((h - leadingW) / 2f).dp).size(leadingW.dp),
            )
        }
        if (trailing != null) {
            Image(
                painterResource(trailing),
                contentDescription = null,
                modifier = Modifier.offset((w - 25).dp, ((h - 6) / 2f).dp).width(12.dp).height(6.dp),
            )
        }
        val leftPad = if (leading != null) 33 else 10
        val rightPad = if (trailing != null) 30 else 10
        Box(
            Modifier
                .offset(leftPad.dp, 0.dp)
                .width((w - leftPad - rightPad).dp)
                .height(h.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            if (value.isEmpty()) {
                Text(hint, style = vs(15f, 300, 18f, 0f, Inter), color = Hint, maxLines = 1)
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = singleLine,
                textStyle = vs(15f, 400, 18f, 0f, Inter),
                cursorBrush = SolidColor(VColor.Purple),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
fun VToggle(checked: Boolean, onChange: (Boolean) -> Unit, track: Color = Color(0x4D3C3C43)) {
    val pos by animateFloatAsState(if (checked) 1f else 0f, label = "tg")
    Box(
        Modifier
            .size(width = 64.dp, height = 28.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(if (checked) VColor.Purple else track)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) { onChange(!checked) },
    ) {
        Box(
            Modifier
                .offset(x = (2 + pos * 36).dp, y = 2.dp)
                .size(24.dp)
                .clip(CircleShape)
                .background(VColor.White),
        )
    }
}

@Composable
fun ContratarSheet(p: ProMatch, onDismiss: () -> Unit) {
    var shown by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { shown = true }
    val enter by animateFloatAsState(if (shown) 1f else 0f, tween(280), label = "enter")

    val ctx = LocalContext.current
    var tipo by remember { mutableStateOf("") }
    var data by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }
    var local by remember { mutableStateOf("") }
    var orcamento by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var urgente by remember { mutableStateOf(false) }
    var comboOpen by remember { mutableStateOf(false) }
    var enviado by remember { mutableStateOf(false) }

    val completo = tipo.isNotBlank() && data.isNotBlank() && horario.isNotBlank() &&
        local.isNotBlank() && orcamento.isNotBlank() && descricao.isNotBlank()

    val datePicker = remember {
        DatePickerDialog(
            ctx,
            { _, y, m, d -> data = "%02d/%02d/%04d".format(d, m + 1, y) },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
        ).apply { datePicker.minDate = System.currentTimeMillis() - 1000 }
    }

    val timePicker = remember {
        TimePickerDialog(
            ctx,
            { _, h, mi ->
                val cal = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, h); set(Calendar.MINUTE, mi) }
                horario = SimpleDateFormat("HH:mm", Locale("pt", "BR")).format(cal.time)
            },
            12,
            0,
            true,
        )
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(SheetBg)
            .alpha(enter)
            .offset(y = (28f * (1f - enter)).dp),
    ) {
        Box(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Box(Modifier.fillMaxWidth().height(852.dp)) {
                Box(
                    Modifier
                        .offset(25.dp, 84.dp)
                        .size(width = 46.dp, height = 37.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(CardBg)
                        .clickable(onClick = onDismiss),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painterResource(R.drawable.ic_close),
                        contentDescription = "Fechar",
                        modifier = Modifier.size(14.dp),
                        tint = VColor.Ink,
                    )
                }

                FText("Contatar", 78, 79, 237, 21f, 900, VColor.Ink, Roboto)

                Box(
                    Modifier
                        .offset(34.dp, 151.dp)
                        .width(326.dp)
                        .height(68.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(CardBg),
                ) {
                    Box(Modifier.offset(16.dp, 7.dp).size(54.dp)) {
                        Image(
                            painterResource(p.photo),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize().clip(CircleShape),
                            contentScale = ContentScale.Crop,
                        )
                        Box(
                            Modifier
                                .offset(39.dp, 38.dp)
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(VColor.White),
                            contentAlignment = Alignment.Center,
                        ) {
                            Box(Modifier.size(8.dp).clip(CircleShape).background(Color(0xFF1F7729)))
                        }
                    }
                    Text(
                        "${p.name}\n${p.role}",
                        modifier = Modifier.offset(86.dp, 14.dp).width(150.dp),
                        style = vs(13f, 400, 17f, 0f, Inter),
                        color = VColor.Ink,
                        maxLines = 2,
                    )
                    Box(Modifier.offset(262.dp, 30.dp).size(4.dp).clip(CircleShape).background(OnlineGreen))
                    Text(
                        "Online",
                        modifier = Modifier.offset(271.dp, 23.dp),
                        style = vs(12f, 500, 16f, 0f, Inter),
                        color = OnlineGreen,
                    )
                }

                VLabel("Tipo de serviço*", 25, 234)
                Box(
                    Modifier
                        .offset(25.dp, 252.dp)
                        .width(343.dp)
                        .height(39.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(FieldBg)
                        .vClick(PressSoft) { comboOpen = true },
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        tipo.ifBlank { "Selecione o tipo do serviço" },
                        modifier = Modifier.offset(10.dp, 0.dp).width(290.dp),
                        style = vs(15f, if (tipo.isBlank()) 300 else 400, 19f, 0f, Inter),
                        color = if (tipo.isBlank()) Hint else VColor.Ink,
                        maxLines = 1,
                    )
                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        null,
                        Modifier.offset(314.dp, 0.dp).size(20.dp),
                        tint = VColor.Ink,
                    )
                }

                VLabel("Data*", 25, 299)
                Box(
                    Modifier
                        .offset(25.dp, 317.dp)
                        .width(160.dp)
                        .height(39.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(FieldBg)
                        .vClick(PressSoft) { datePicker.show() },
                    contentAlignment = Alignment.TopStart,
                ) {
                    Image(
                        painterResource(R.drawable.ic_v_calendar),
                        null,
                        Modifier.offset(x = 10.dp, y = 11.dp).size(16.dp),
                    )
                    Text(
                        data.ifBlank { "DD/MM/AAAA" },
                        modifier = Modifier.offset(x = 33.dp, y = 11.dp),
                        style = vs(15f, if (data.isBlank()) 300 else 400, 18f, 0f, Inter),
                        color = if (data.isBlank()) Hint else VColor.Ink,
                        maxLines = 1,
                    )
                }

                VLabel("Horário*", 208, 299)
                Box(
                    Modifier
                        .offset(208.dp, 317.dp)
                        .width(160.dp)
                        .height(39.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(FieldBg)
                        .vClick(PressSoft) { timePicker.show() },
                    contentAlignment = Alignment.TopStart,
                ) {
                    Image(
                        painterResource(R.drawable.ic_v_clock),
                        null,
                        Modifier.offset(x = 7.dp, y = 7.dp).size(24.dp),
                    )
                    Text(
                        horario.ifBlank { "HH:MM" },
                        modifier = Modifier.offset(x = 11.dp, y = 10.dp).width(98.dp),
                        style = vs(15f, if (horario.isBlank()) 300 else 400, 18f, 0f, Inter),
                        color = if (horario.isBlank()) Hint else VColor.Ink,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                    )
                }

                VLabel("Orçamento (R$)*", 25, 363)
                Box(
                    Modifier
                        .offset(25.dp, 381.dp)
                        .width(160.dp)
                        .height(39.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(FieldBg),
                    contentAlignment = Alignment.TopStart,
                ) {
                    Image(
                        painterResource(R.drawable.ic_v_money),
                        null,
                        Modifier.offset(x = 5.dp, y = 5.dp).size(28.dp),
                    )
                    BasicTextField(
                        value = orcamento,
                        onValueChange = { v -> orcamento = v.filter { c -> c.isDigit() || c == ',' } },
                        singleLine = true,
                        textStyle = vs(15f, 300, 18f, 0f, Inter).copy(color = VColor.Ink),
                        modifier = Modifier
                            .offset(x = 34.dp, y = 10.dp)
                            .width(115.dp),
                        decorationBox = { inner ->
                            Box {
                                if (orcamento.isBlank()) {
                                    Text("Ex: 350", style = vs(15f, 300, 18f, 0f, Inter), color = Hint, maxLines = 1)
                                }
                                inner()
                            }
                        },
                    )
                }

                VLabel("Local*", 208, 363)
                Box(
                    Modifier
                        .offset(208.dp, 381.dp)
                        .width(160.dp)
                        .height(39.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(FieldBg),
                    contentAlignment = Alignment.TopStart,
                ) {
                    Image(
                        painterResource(R.drawable.ic_v_pin),
                        null,
                        Modifier.offset(x = 7.dp, y = 7.dp).size(24.dp),
                    )
                    BasicTextField(
                        value = local,
                        onValueChange = { local = it },
                        singleLine = true,
                        textStyle = vs(15f, 300, 18f, 0f, Inter).copy(color = VColor.Ink),
                        modifier = Modifier
                            .offset(x = 33.dp, y = 10.dp)
                            .width(115.dp),
                        decorationBox = { inner ->
                            Box {
                                if (local.isBlank()) {
                                    Text("Bairro", style = vs(15f, 300, 18f, 0f, Inter), color = Hint, maxLines = 1)
                                }
                                inner()
                            }
                        },
                    )
                }

                VLabel("Descrição do serviço*", 25, 430)
                Box(
                    Modifier
                        .offset(25.dp, 448.dp)
                        .width(343.dp)
                        .height(146.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(FieldBg),
                ) {
                    if (descricao.isEmpty()) {
                        Text(
                            "Descreva o que precisa ser feito com o máximo de detalhes",
                            modifier = Modifier.offset(10.dp, 8.dp).width(323.dp),
                            style = vs(15f, 300, 19f, 0f, Inter),
                            color = Hint,
                        )
                    }
                    BasicTextField(
                        value = descricao,
                        onValueChange = { descricao = it },
                        textStyle = vs(15f, 400, 19f, 0f, Inter).copy(color = VColor.Ink),
                        cursorBrush = SolidColor(VColor.Purple),
                        modifier = Modifier.offset(10.dp, 8.dp).width(323.dp),
                    )
                }

                VLabel("Fotos do local (Opcional)", 27, 603)
                Box(
                    Modifier
                        .offset(25.dp, 621.dp)
                        .width(343.dp)
                        .height(47.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(FieldBg)
                        .clickable { },
                ) {
                    Image(
                        painterResource(R.drawable.ic_v_camera),
                        contentDescription = null,
                        modifier = Modifier.offset(98.dp, 15.dp).size(width = 20.dp, height = 17.dp),
                    )
                    Text(
                        "Adicionar foto",
                        modifier = Modifier.offset(125.dp, 14.dp),
                        style = vs(15f, 300, 18f, 0f, Inter),
                        color = VColor.Ink,
                    )
                }

                Box(
                    Modifier
                        .offset(27.dp, 689.dp)
                        .width(341.dp)
                        .height(53.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFFEFEFF)),
                ) {
                    Text(
                        "Serviço urgente",
                        modifier = Modifier.offset(7.dp, 7.dp),
                        style = vs(16f, 500, 20f, 0f, Roboto),
                        color = VColor.Ink,
                    )
                    Text(
                        "Prioridade máxima de resposta",
                        modifier = Modifier.offset(7.dp, 29.dp),
                        style = vs(14f, 300, 18f, 0f, Roboto),
                        color = VColor.Ink,
                    )
                    Box(Modifier.offset(267.dp, 12.dp)) {
                        VToggle(urgente, { urgente = it })
                    }
                }
            }
        }

        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(82.dp)
                .background(BarBg),
        ) {
            Box(
                Modifier
                    .offset(88.dp, 8.dp)
                    .width(218.dp)
                    .height(35.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (completo) VColor.Purple else SheetBg)
                    .vClick(PressWhite, enabled = completo) { enviado = true },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    "Enviar solicitação",
                    style = vs(15f, 900, 18f, 0f, Roboto),
                    color = if (completo) Color.White else Color(0x80000000),
                )
            }
            Text(
                if (completo) "Pronto para enviar" else "Preencha os campos obrigatórios (*)",
                modifier = Modifier.offset(116.dp, 51.dp),
                style = vs(10f, 300, 13f, 0f, Roboto),
                color = if (completo) OnlineGreen else Color(0xC44B4040),
            )
        }

        if (comboOpen) {
            ServiceCombo(
                onPick = {
                    tipo = it
                    comboOpen = false
                },
                onDismiss = { comboOpen = false },
            )
        }

        if (enviado) {
            SucessoDialog(name = p.name, onOk = onDismiss)
        }
    }
}

@Composable
private fun ServiceCombo(onPick: (String) -> Unit, onDismiss: () -> Unit) {
    var q by remember { mutableStateOf("") }
    var niche by remember { mutableStateOf("Construção") }
    val lista = remember(q, niche) {
        ServiceCatalog.groups[niche].orEmpty().filter { it.contains(q, ignoreCase = true) }
    }

    Box(Modifier.fillMaxSize().background(Color(0x99000000)).clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
    ) { onDismiss() }, contentAlignment = Alignment.BottomCenter) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(560.dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color(0xFFFBF7FF))
                .pointerInput(Unit) { detectTapGestures { } },
        ) {
            Box(Modifier.offset(0.dp, 14.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Box(Modifier.size(width = 59.dp, height = 5.dp).clip(RoundedCornerShape(100.dp)).background(Color(0xFFCCCCCC)))
            }
            Text(
                "Tipo de serviço",
                modifier = Modifier.offset(24.dp, 36.dp),
                style = vs(20f, 800, 24f, 0f, Roboto),
                color = VColor.Ink,
            )
            Box(
                Modifier
                    .offset(24.dp, 74.dp)
                    .width(345.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(10.dp)),
            ) {
                Icon(Icons.Filled.Search, null, Modifier.offset(12.dp, 11.dp).size(18.dp), tint = Color(0xFF9D9D9D))
                BasicTextField(
                    value = q,
                    onValueChange = { q = it },
                    textStyle = vs(14f, 400, 18f, 0f, Inter).copy(color = VColor.Ink),
                    cursorBrush = SolidColor(VColor.Purple),
                    modifier = Modifier.offset(40.dp, 10.dp).width(290.dp),
                )
                if (q.isEmpty()) {
                    Text(
                        "Buscar serviço...",
                        modifier = Modifier.offset(40.dp, 10.dp),
                        style = vs(14f, 300, 18f, 0f, Inter),
                        color = Hint,
                    )
                }
            }
            Row(
                Modifier
                    .offset(24.dp, 126.dp)
                    .width(345.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ServiceCatalog.groups.keys.forEach { n ->
                    val on = n == niche
                    Box(
                        Modifier
                            .height(30.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(if (on) VColor.Purple else Color.White)
                            .vClick(PressAccent, scaleDown = 0.94f) { niche = n }
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(n, style = vs(12f, 700, 16f, 0f, Roboto), color = if (on) Color.White else VColor.Ink, maxLines = 1)
                    }
                }
            }
            Box(Modifier.offset(0.dp, 170.dp).fillMaxWidth().height(380.dp)) {
                LazyColumn(Modifier.fillMaxSize()) {
                    itemsIndexed(lista) { idx, item ->
                        Column(Modifier.fillMaxWidth()) {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                                    .vClick(PressAccent, scaleDown = 0.95f) { onPick(item) },
                                contentAlignment = Alignment.CenterStart,
                            ) {
                                Text(
                                    item,
                                    modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                                    style = vs(14f, 400, 18f, 0f, Inter),
                                    color = VColor.Ink,
                                    maxLines = 1,
                                )
                            }
                            if (idx < lista.lastIndex) {
                                Box(Modifier.padding(start = 24.dp).width(345.dp).height(1.dp).background(Color(0x14000000)))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SucessoDialog(name: String, onOk: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color(0x99000000))
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { onOk() },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            Modifier
                .width(310.dp)
                .clip(RoundedCornerShape(22.dp))
                .background(Color.White)
                .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { }
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE8F9EA)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(IcoAkarIconsCircleCheckFill, null, Modifier.size(38.dp), tint = Color(0xFF12AA24))
            }
            Spacer(Modifier.height(14.dp))
            Text(
                "Solicitação enviada!",
                style = vs(18f, 800, 22f, 0f, Roboto),
                color = VColor.Ink,
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "Sua solicitação foi enviada para $name. Você será avisada assim que houver uma resposta.",
                style = vs(13f, 400, 18f, 0f, Roboto),
                color = Color(0xB34D3274),
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(18.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(VColor.Purple)
                    .vClick(PressWhite) { onOk() },
                contentAlignment = Alignment.Center,
            ) {
                Text("Ok", style = vs(15f, 800, 19f, 0f, Roboto), color = Color.White)
            }
        }
    }
}


object ServiceCatalog {
    val groups: Map<String, List<String>> = linkedMapOf(
        "Construção" to listOf(
            "Pedreiro", "Mestre de obras", "Eletricista", "Encanador", "Hidráulica",
            "Pintura residencial", "Gesso e drywall", "Azulejista", "Marcenaria",
            "Serralheria", "Telhadista", "Impermeabilização", "Desentupimento",
            "Instalação de ar-condicionado", "Reforma de banheiro", "Reforma de cozinha",
            "Muro e alvenaria", "Calçamento",
        ),
        "Comércio" to listOf(
            "Consultoria financeira", "Contabilidade", "Marketing digital", "Social media",
            "Design gráfico", "Desenvolvimento de site", "Desenvolvimento de app",
            "Gestão de estoque", "Vitrinismo", "Fotografia de produto", "Logística e entrega",
            "Compras e fornecedores", "Abertura de MEI", "Planejamento tributário",
        ),
        "Serviços" to listOf(
            "Diarista", "Passadeira", "Cozinheira", "Mudança e frete", "Montagem de móveis",
            "Manutenção de computador", "Suporte técnico", "Aulas particulares",
            "Personal trainer", "Fisioterapia", "Massoterapia", "Fretes pequenos",
            "Chaveiro", "Dedetização", "Limpeza pós-obra", "Cuidador de idosos",
            "Passeio com cães", "Jardinagem",
        ),
        "Beleza" to listOf(
            "Cabeleireira", "Manicure e pedicure", "Design de sobrancelha", "Maquiagem",
            "Depilação", "Design de cílios", "Estética facial", "Massagem relaxante",
            "Barbearia", "Podologia", "Tranças e penteados", "Alongamento de unhas",
        ),
        "Alimentação" to listOf(
            "Confeitaria", "Bolos e doces", "Salgados para festa", "Marmitas saudáveis",
            "Buffet e eventos", "Churrasqueiro", "Bartender", "Café e brunch",
            "Pizzaiolo", "Comida vegana", "Catering corporativo",
        ),
        "Saúde" to listOf(
            "Nutricionista", "Psicóloga", "Enfermagem domiciliar", "Dentista",
            "Terapia ocupacional", "Acompanhante terapêutico", "Consultoria em amamentação",
        ),
        "Educação" to listOf(
            "Reforço escolar", "Inglês", "Espanhol", "Matemática", "Redação",
            "Música e violão", "Informática básica", "Preparatório ENEM",
        ),
    )
}

private val cats = listOf("Todos", "Construção", "Comércio", "Serviços", "Beleza", "Alimentação")

@Composable
fun FiltrosDialog(onDismiss: () -> Unit) {
    var shown by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { shown = true }
    val enter by animateFloatAsState(if (shown) 1f else 0f, tween(300), label = "fenter")
    var busca by remember { mutableStateOf("") }
    var violeta by remember { mutableStateOf(false) }
    var cat by remember { mutableStateOf("Todos") }

    Box(
        Modifier
            .fillMaxWidth()
            .height(744.dp)
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(FilterBg)
            .alpha(enter)
            .offset(y = (70f * (1f - enter)).dp),
    ) {
        Box(
            Modifier
                .offset(167.dp, 31.dp)
                .size(width = 59.dp, height = 5.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(Color(0xFFCCCCCC)),
        )

        Text(
            "Filtros",
            modifier = Modifier.offset(31.dp, 63.dp),
            style = vs(24f, 800, 28f, 0f, Roboto),
            color = VColor.Ink,
        )

        Box(
            Modifier
                .offset(30.dp, 107.dp)
                .width(333.dp)
                .height(41.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(VColor.White)
                .border(1.dp, LineGray, RoundedCornerShape(10.dp)),
        ) {
            Image(
                painterResource(R.drawable.ic_v_search),
                contentDescription = null,
                modifier = Modifier.offset(15.dp, 14.5.dp).size(12.dp),
            )
            if (busca.isEmpty()) {
                Text(
                    "Buscar habilidade ou área.",
                    modifier = Modifier.offset(44.dp, 12.dp),
                    style = vs(13f, 400, 16f, 0f, Roboto),
                    color = Color(0x7A4D3274),
                )
            }
            BasicTextField(
                value = busca,
                onValueChange = { busca = it },
                singleLine = true,
                textStyle = vs(13f, 400, 16f, 0f, Roboto),
                cursorBrush = SolidColor(VColor.Purple),
                modifier = Modifier.offset(44.dp, 12.dp).width(280.dp),
            )
        }

        Text(
            "Protocolo Violeta",
            modifier = Modifier.offset(31.dp, 168.dp),
            style = vs(14f, 600, 18f, 0f, Roboto),
            color = VColor.Ink,
        )
        Box(Modifier.offset(307.dp, 161.dp)) {
            VToggle(violeta, { violeta = it }, track = Color(0xFFD9D9D9))
        }

        Box(Modifier.offset(30.dp, 206.dp).width(333.dp).height(1.dp).background(Color(0xFFD5D1DE)))

        Text(
            "Categorias",
            modifier = Modifier.offset(31.dp, 226.dp),
            style = vs(13f, 400, 16f, 0f, Roboto),
            color = Color(0xFF776395),
        )

        cats.forEachIndexed { i, c ->
            val sel = c == cat
            Box(
                Modifier
                    .offset((if (sel) 31 else 30).dp, (261 + i * 55).dp)
                    .width(333.dp)
                    .height(45.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (sel) Color(0xFFEBDDFF) else VColor.White)
                    .border(1.dp, if (sel) VColor.Purple else Color(0xFF9D9D9D), RoundedCornerShape(10.dp))
                    .vClick(PressAccent, scaleDown = 0.94f) { cat = c },
            ) {
                Text(
                    c,
                    modifier = Modifier.offset((if (sel) 20 else 19).dp, 14.dp),
                    style = vs(13f, if (sel) 800 else 400, 16f, 0f, Roboto),
                    color = if (sel) VColor.Purple else VColor.Ink,
                )
                if (sel) {
                    Image(
                        painterResource(R.drawable.ic_v_check_purple),
                        contentDescription = null,
                        modifier = Modifier.offset(287.dp, 18.dp).size(width = 10.dp, height = 8.dp),
                    )
                }
            }
        }

        Box(
            Modifier
                .offset(30.dp, 636.dp)
                .width(333.dp)
                .height(39.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(VColor.Purple)
                .clickable(onClick = onDismiss),
            contentAlignment = Alignment.Center,
        ) {
            Text("Aplicar filtros", style = vs(16f, 800, 19f, 0f, Roboto), color = VColor.White)
        }
    }
}
