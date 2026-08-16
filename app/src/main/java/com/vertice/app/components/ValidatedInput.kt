package com.vertice.app.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.ui.theme.LocalColors
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun digitsOnly(s: String): String = s.filter { it.isDigit() }

fun formatDateMask(digits: String): String {
    val d = digits.filter { it.isDigit() }.take(8)
    val sb = StringBuilder()
    for (i in d.indices) {
        if (i == 2 || i == 4) sb.append('/')
        sb.append(d[i])
    }
    return sb.toString()
}

fun formatTimeMask(digits: String): String {
    val d = digits.filter { it.isDigit() }.take(4)
    val sb = StringBuilder()
    for (i in d.indices) {
        if (i == 2) sb.append(':')
        sb.append(d[i])
    }
    return sb.toString()
}

class DateMaskTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText =
        TransformedText(AnnotatedString(formatDateMask(text.text)), OffsetMapping.Identity)
}

class TimeMaskTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText =
        TransformedText(AnnotatedString(formatTimeMask(text.text)), OffsetMapping.Identity)
}

class CurrencyMaskTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digitsOnly = text.text.filter { it.isDigit() }
        if (digitsOnly.isEmpty()) {
            return TransformedText(AnnotatedString(""), OffsetMapping.Identity)
        }
        val cents = digitsOnly.toLong()
        val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        return TransformedText(
            AnnotatedString(formatter.format(cents / 100.0)),
            OffsetMapping.Identity,
        )
    }

    companion object {
        fun parseToCents(formatted: String): Long {
            val digitsOnly = formatted.filter { it.isDigit() }
            return if (digitsOnly.isEmpty()) 0L else digitsOnly.toLong()
        }
    }
}

@Composable
fun ValidatedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    icon: (@Composable () -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation? = null,
    singleLine: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    readOnly: Boolean = false,
) {
    val C = LocalColors

    val colors = if (isError) Pair(C.pink.copy(alpha = 0.15f), C.pink) else Pair(C.inputBg, C.border)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.first, RoundedCornerShape(13.dp))
            .border(1.dp, colors.second, RoundedCornerShape(13.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            icon?.invoke()

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                if (readOnly) {
                    if (value.isEmpty()) {
                        Text(placeholder, color = C.muted, fontSize = 14.sp)
                    } else {
                        Text(value, color = C.white, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    }
                } else {
                    if (value.isEmpty() && placeholder.isNotBlank()) {
                        Text(placeholder, color = C.muted, fontSize = 14.sp)
                    }
                    val vTransformation = visualTransformation ?: VisualTransformation.None
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        textStyle = TextStyle(color = C.white, fontSize = 14.sp),
                        cursorBrush = SolidColor(C.purple),
                        singleLine = singleLine,
                        keyboardOptions = keyboardOptions,
                        visualTransformation = vTransformation,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            if (isError && value.isNotBlank()) {
                Icon(Icons.Filled.Error, null, tint = C.pink, modifier = Modifier.size(16.dp))
            } else if (!isError && value.isNotBlank()) {
                Icon(Icons.Filled.CheckCircle, null, tint = C.green, modifier = Modifier.size(16.dp))
            }
        }
    }

    if (isError && errorMessage != null) {
        Text(errorMessage, color = C.pink, fontSize = 11.sp, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 14.dp, top = 4.dp))
    }
}

@Composable
fun DateInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Data",
    placeholder: String = "DD/MM/AAAA",
    icon: (@Composable () -> Unit)? = { Icon(Icons.Filled.CalendarToday, null, tint = Color.Gray, modifier = Modifier.size(16.dp)) },
    modifier: Modifier = Modifier,
    isRequired: Boolean = true,
    minDate: Calendar = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) },
    onDatePicked: ((Calendar) -> Unit)? = null,
) {
    val context = LocalContext.current
    var internalValue by rememberSaveable { mutableStateOf(value) }

    LaunchedEffect(value) {
        internalValue = value
    }

    val display = formatDateMask(internalValue)
    val isError = isRequired && display.isNotBlank() && !isValidDate(display)
    val errorMessage = when {
        isError -> "Data inválida. Escolha uma data futura"
        display.isBlank() && isRequired -> "Data é obrigatória"
        else -> null
    }

    val onClick = {
        val calendar = Calendar.getInstance()
        val parsed = try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(display)
        } catch (e: Exception) {
            null
        }
        calendar.time = parsed ?: Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) }.time

        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val picked = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formatted = sdf.format(picked.time)
                internalValue = formatted
                onValueChange(formatted)
                onDatePicked?.invoke(picked)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = minDate.timeInMillis
        }.show()
    }

    ValidatedTextField(
        value = display,
        onValueChange = {},
        label = label,
        placeholder = placeholder,
        icon = icon,
        isError = isError,
        errorMessage = errorMessage,
        modifier = modifier,
        onClick = onClick,
        readOnly = true,
    )
}

internal fun isValidDate(dateStr: String): Boolean {
    if (dateStr.length != 10) return false
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    sdf.isLenient = false
    return try {
        val date = sdf.parse(dateStr)
        val cal = Calendar.getInstance()
        cal.time = date!!
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)
        cal.timeInMillis >= today.timeInMillis
    } catch (e: Exception) {
        false
    }
}

@Composable
fun TimeInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Horário",
    placeholder: String = "HH:mm",
    icon: (@Composable () -> Unit)? = { Icon(Icons.Filled.AccessTime, null, tint = Color.Gray, modifier = Modifier.size(16.dp)) },
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var internalValue by rememberSaveable { mutableStateOf(value) }

    LaunchedEffect(value) {
        internalValue = value
    }

    val display = formatTimeMask(internalValue)
    val isError = display.isNotBlank() && !isValidTime(display)
    val errorMessage = if (isError) "Horário inválido. Use HH:mm" else null

    val onClick = {
        val calendar = Calendar.getInstance()
        try {
            val parts = display.split(":")
            if (parts.size == 2 && parts[0].isNotBlank() && parts[1].isNotBlank()) {
                calendar.set(Calendar.HOUR_OF_DAY, parts[0].toInt())
                calendar.set(Calendar.MINUTE, parts[1].toInt())
            }
        } catch (e: Exception) {
        }

        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                val formatted = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
                internalValue = formatted
                onValueChange(formatted)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    ValidatedTextField(
        value = display,
        onValueChange = {},
        label = label,
        placeholder = placeholder,
        icon = icon,
        isError = isError,
        errorMessage = errorMessage,
        modifier = modifier,
        onClick = onClick,
        readOnly = true,
    )
}

internal fun isValidTime(timeStr: String): Boolean {
    if (timeStr.length != 5) return false
    return try {
        val parts = timeStr.split(":")
        val hour = parts[0].toInt()
        val minute = parts[1].toInt()
        hour in 0..23 && minute in 0..59
    } catch (e: Exception) {
        false
    }
}

@Composable
fun CurrencyInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Orçamento (R$)",
    placeholder: String = "Ex: 300",
    icon: (@Composable () -> Unit)? = { Icon(Icons.Filled.AttachMoney, null, tint = Color.Gray, modifier = Modifier.size(16.dp)) },
    modifier: Modifier = Modifier,
    isRequired: Boolean = false,
) {
    var internalValue by rememberSaveable { mutableStateOf(value) }

    LaunchedEffect(value) {
        internalValue = value
    }

    fun normalized(s: String): String = s.filter { it.isDigit() }.take(14)

    val cents = CurrencyMaskTransformation.parseToCents(normalized(internalValue))
    val isError = isRequired && cents == 0L && internalValue.isNotBlank()
    val errorMessage = if (isError) "Informe um valor maior que zero" else null

    ValidatedTextField(
        value = internalValue,
        onValueChange = {
            val n = normalized(it)
            internalValue = n
            onValueChange(n)
        },
        label = label,
        placeholder = placeholder,
        icon = icon,
        visualTransformation = CurrencyMaskTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = isError,
        errorMessage = errorMessage,
        modifier = modifier,
    )
}