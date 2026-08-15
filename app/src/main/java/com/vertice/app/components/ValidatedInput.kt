package com.vertice.app.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardType
import androidx.compose.foundation.text.VisualTransformation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.ui.theme.LocalColors
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.atomic.AtomicReference

// ============================================================================
// VisualTransformations for masks
// ============================================================================

class DateMaskTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TextFieldValue {
        val digitsOnly = text.text.filter { it.isDigit() }
        val formatted = StringBuilder()
        for (i in digitsOnly.indices) {
            if (i == 2 || i == 4) formatted.append('/')
            formatted.append(digitsOnly[i])
        }
        return TextFieldValue(
            text = AnnotatedString(formatted.toString()),
            selection = androidx.compose.ui.text.TextRange(formatted.length),
            composition = text.composition
        )
    }
}

class TimeMaskTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TextFieldValue {
        val digitsOnly = text.text.filter { it.isDigit() }
        val formatted = StringBuilder()
        for (i in digitsOnly.indices) {
            if (i == 2) formatted.append(':')
            formatted.append(digitsOnly[i])
        }
        return TextFieldValue(
            text = AnnotatedString(formatted.toString()),
            selection = androidx.compose.ui.text.TextRange(formatted.length),
            composition = text.composition
        )
    }
}

class CurrencyMaskTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TextFieldValue {
        val digitsOnly = text.text.filter { it.isDigit() }
        if (digitsOnly.isEmpty()) {
            return TextFieldValue(text = AnnotatedString(""))
        }
        val cents = digitsOnly.toLong()
        val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        val formatted = formatter.format(cents / 100.0)
        return TextFieldValue(
            text = AnnotatedString(formatted),
            selection = androidx.compose.ui.text.TextRange(formatted.length),
            composition = text.composition
        )
    }

    companion object {
        fun parseToCents(formatted: String): Long {
            val digitsOnly = formatted.filter { it.isDigit() }
            return if (digitsOnly.isEmpty()) 0L else digitsOnly.toLong()
        }
    }
}

// ============================================================================
// ValidatedTextField - Base component with validation state
// ============================================================================

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
) {
    val C = LocalColors
    val context = LocalContext.current
    var showError by remember { mutableStateOf(isError && value.isNotBlank()) }

    val fieldColors = if (isError) {
        Pair(C.red.copy(alpha = 0.15f), C.red)
    } else {
        Pair(C.inputBg, C.border)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(fieldColors.first, RoundedCornerShape(13.dp))
            .border(1.dp, fieldColors.second, RoundedCornerShape(13.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        contentAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            icon?.invoke()
            
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clickable(onClick = onClick)
            ) {
                if (value.isEmpty() && placeholder.isNotBlank()) {
                    Text(placeholder, color = C.muted, fontSize = 14.sp)
                }
                BasicTextField(
                    value = value,
                    onValueChange = { newValue ->
                        onValueChange(newValue)
                        showError = isError && newValue.isNotBlank()
                    },
                    textStyle = TextStyle(color = C.white, fontSize = 14.sp),
                    cursorBrush = SolidColor(C.purple),
                    singleLine = singleLine,
                    keyboardOptions = keyboardOptions,
                    visualTransformation = visualTransformation,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            
            if (isError && value.isNotBlank()) {
                Icon(Icons.Filled.Error, null, tint = C.red, modifier = Modifier.size(16.dp))
            } else if (!isError && value.isNotBlank()) {
                Icon(Icons.Filled.CheckCircle, null, tint = C.green, modifier = Modifier.size(16.dp))
            }
        }
    }
    
    if (showError && errorMessage != null) {
        Text(errorMessage, color = C.red, fontSize = 11.sp, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 14.dp, top = 4.dp))
    }
}

// ============================================================================
// DateInput - DD/MM/YYYY mask with DatePickerDialog
// ============================================================================

@Composable
fun DateInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Data",
    placeholder: String = "DD/MM/AAAA",
    icon: (@Composable () -> Unit)? = { Icon(Icons.Filled.CalendarToday, null, tint = LocalColors.current.muted, modifier = Modifier.size(16.dp)) },
    modifier: Modifier = Modifier,
    isRequired: Boolean = true,
    minDate: Calendar = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) },
    onDatePicked: ((Calendar) -> Unit)? = null,
) {
    val C = LocalColors
    val context = LocalContext.current
    var showPicker by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var internalValue by rememberSaveable { mutableStateOf(value) }
    
    // Sync with external value
    LaunchedEffect(value) {
        internalValue = value
    }
    
    val isError = isRequired && internalValue.isNotBlank() && !isValidDate(internalValue)
    if (isError) {
        errorMessage = "Data inválida. Use DD/MM/AAAA e data futura"
    } else if (internalValue.isBlank() && isRequired) {
        errorMessage = "Data é obrigatória"
    } else {
        errorMessage = null
    }
    
    val onClick = {
        val calendar = Calendar.getInstance()
        if (internalValue.isNotBlank()) {
            try {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                sdf.isLenient = false
                val date = sdf.parse(internalValue)
                calendar.time = date
            } catch (e: Exception) {
                calendar.add(Calendar.DAY_OF_YEAR, 1)
            }
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        
        val dpd = DatePickerDialog(
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
        )
        dpd.datePicker.minDate = minDate.timeInMillis
        dpd.show()
    }
    
    ValidatedTextField(
        value = internalValue,
        onValueChange = { internalValue = it; onValueChange(it) },
        label = label,
        placeholder = placeholder,
        icon = icon,
        visualTransformation = DateMaskTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = isError,
        errorMessage = errorMessage,
        modifier = modifier,
        onClick = onClick,
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

// ============================================================================
// TimeInput - HH:mm mask with TimePickerDialog
// ============================================================================

@Composable
fun TimeInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Horário",
    placeholder: String = "HH:mm",
    icon: (@Composable () -> Unit)? = { Icon(Icons.Filled.AccessTime, null, tint = LocalColors.current.muted, modifier = Modifier.size(16.dp)) },
    modifier: Modifier = Modifier,
) {
    val C = LocalColors
    val context = LocalContext.current
    var internalValue by rememberSaveable { mutableStateOf(value) }
    
    LaunchedEffect(value) {
        internalValue = value
    }
    
    val isError = internalValue.isNotBlank() && !isValidTime(internalValue)
    val errorMessage = if (isError) "Horário inválido. Use HH:mm" else null
    
    val onClick = {
        val calendar = Calendar.getInstance()
        if (internalValue.isNotBlank()) {
            try {
                val parts = internalValue.split(":")
                if (parts.size == 2) {
                    calendar.set(Calendar.HOUR_OF_DAY, parts[0].toInt())
                    calendar.set(Calendar.MINUTE, parts[1].toInt())
                }
            } catch (e: Exception) {
                // Use current time
            }
        }
        
        val tpd = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                val formatted = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
                internalValue = formatted
                onValueChange(formatted)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true // 24 hour format
        )
        tpd.show()
    }
    
    ValidatedTextField(
        value = internalValue,
        onValueChange = { internalValue = it; onValueChange(it) },
        label = label,
        placeholder = placeholder,
        icon = icon,
        visualTransformation = TimeMaskTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = isError,
        errorMessage = errorMessage,
        modifier = modifier,
        onClick = onClick,
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

// ============================================================================
// CurrencyInput - Brazilian R$ format, numeric only
// ============================================================================

@Composable
fun CurrencyInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Orçamento (R$)",
    placeholder: String = "Ex: 300",
    icon: (@Composable () -> Unit)? = { Icon(Icons.Filled.AttachMoney, null, tint = LocalColors.current.muted, modifier = Modifier.size(16.dp)) },
    modifier: Modifier = Modifier,
    isRequired: Boolean = false,
) {
    val C = LocalColors
    var internalValue by rememberSaveable { mutableStateOf(value) }
    
    LaunchedEffect(value) {
        internalValue = value
    }
    
    val cents = CurrencyMaskTransformation.parseToCents(internalValue)
    val isError = isRequired && cents == 0L && internalValue.isNotBlank()
    val errorMessage = if (isError) "Informe um valor maior que zero" else null
    
    ValidatedTextField(
        value = internalValue,
        onValueChange = { internalValue = it; onValueChange(it) },
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