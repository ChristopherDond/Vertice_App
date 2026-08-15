package com.vertice.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialDatePicker
import androidx.compose.material3.MaterialTimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.ui.theme.LocalColors
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun FLabel(text: String) {
    val C = LocalColors
    Text(text, color = C.mutedL, fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.padding(bottom = 7.dp))
}

@Composable
fun TInput(
    value: String,
    onChange: (String) -> Unit,
    placeholder: String,
    icon: (@Composable () -> Unit)? = null,
) {
    val C = LocalColors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(C.inputBg, RoundedCornerShape(13.dp))
            .border(1.dp, C.border, RoundedCornerShape(13.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        icon?.invoke()
        Box(modifier = Modifier.weight(1f)) {
            if (value.isEmpty()) {
                Text(placeholder, color = C.muted, fontSize = 14.sp)
            }
            BasicTextField(
                value = value,
                onValueChange = onChange,
                textStyle = TextStyle(color = C.white, fontSize = 14.sp),
                cursorBrush = SolidColor(C.purple),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
fun TArea(
    value: String,
    onChange: (String) -> Unit,
    placeholder: String,
    minLines: Int = 4,
) {
    val C = LocalColors
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(C.inputBg, RoundedCornerShape(13.dp))
            .border(1.dp, C.border, RoundedCornerShape(13.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
    ) {
        if (value.isEmpty()) {
            Text(placeholder, color = C.muted, fontSize = 14.sp, lineHeight = 21.sp)
        }
        BasicTextField(
            value = value,
            onValueChange = onChange,
            textStyle = TextStyle(color = C.white, fontSize = 14.sp, lineHeight = 21.sp),
            cursorBrush = SolidColor(C.purple),
            minLines = minLines,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun SInput(
    value: String,
    onChange: (String) -> Unit,
    options: List<String>,
    icon: (@Composable () -> Unit)? = null,
) {
    val C = LocalColors
    var expanded by remember { mutableStateOf(false) }
    val isPlaceholder = value.contains("Selecionar")

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(C.inputBg, RoundedCornerShape(13.dp))
                .border(1.dp, C.border, RoundedCornerShape(13.dp))
                .clickableNoRipple { expanded = true }
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            icon?.invoke()
            Text(value, color = if (isPlaceholder) C.muted else C.white, fontSize = 14.sp, modifier = Modifier.weight(1f))
            Icon(Icons.Filled.KeyboardArrowDown, contentDescription = null, tint = C.muted, modifier = Modifier.size(15.dp))
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { opt ->
                DropdownMenuItem(text = { Text(opt) }, onClick = { onChange(opt); expanded = false })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateInput(
    value: String,
    onChange: (String) -> Unit,
    placeholder: String = "DD/MM/AAAA",
    error: String? = null,
    icon: (@Composable () -> Unit)? = null,
) {
    val C = LocalColors
    var showPicker by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val formattedValue = remember(value) { value }
    
    val handleDateSelected = { calendar: Calendar ->
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val selectedDate = sdf.format(calendar.time)
        onChange(selectedDate)
        showPicker = false
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(C.inputBg, RoundedCornerShape(13.dp))
                .border(
                    width = if (error != null) 1.5.dp else 1.dp,
                    color = if (error != null) C.pink else C.border,
                    shape = RoundedCornerShape(13.dp)
                )
                .padding(horizontal = 14.dp, vertical = 12.dp)
                .clickableNoRipple { showPicker = true },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            icon?.invoke()
            Box(modifier = Modifier.weight(1f)) {
                if (formattedValue.isEmpty()) {
                    Text(placeholder, color = C.muted, fontSize = 14.sp)
                }
                Text(formattedValue, color = C.white, fontSize = 14.sp)
            }
            Icon(Icons.Filled.CalendarToday, contentDescription = null, tint = C.muted, modifier = Modifier.size(16.dp))
        }
        
        if (error != null) {
            Text(error, color = C.pink, fontSize = 11.sp, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, start = 14.dp)
            )
        }
        
        MaterialDatePicker(
            onDateSelected = handleDateSelected,
            onDismissRequest = { showPicker = false },
            state = remember { androidx.compose.material3.MaterialDatePickerState() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInput(
    value: String,
    onChange: (String) -> Unit,
    placeholder: String = "Ex: 14h",
    error: String? = null,
    icon: (@Composable () -> Unit)? = null,
) {
    val C = LocalColors
    var showPicker by remember { mutableStateOf(false) }
    val timePickerState = remember { TimePickerState() }

    val handleTimeSelected = { time: Long ->
        val hours = TimeUnit.MILLISECONDS.toHours(time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(time) % 60
        val formatted = String.format(Locale.getDefault(), "%02dh%02d", hours, minutes)
        onChange(formatted)
        showPicker = false
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(C.inputBg, RoundedCornerShape(13.dp))
                .border(
                    width = if (error != null) 1.5.dp else 1.dp,
                    color = if (error != null) C.pink else C.border,
                    shape = RoundedCornerShape(13.dp)
                )
                .padding(horizontal = 14.dp, vertical = 12.dp)
                .clickableNoRipple { showPicker = true },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            icon?.invoke()
            Box(modifier = Modifier.weight(1f)) {
                if (value.isEmpty()) {
                    Text(placeholder, color = C.muted, fontSize = 14.sp)
                }
                Text(value, color = C.white, fontSize = 14.sp)
            }
            Icon(Icons.Filled.AccessTime, contentDescription = null, tint = C.muted, modifier = Modifier.size(16.dp))
        }
        
        if (error != null) {
            Text(error, color = C.pink, fontSize = 11.sp, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, start = 14.dp)
            )
        }
        
        MaterialTimePicker(
            state = timePickerState,
            onTimeSelected = handleTimeSelected,
            onDismissRequest = { showPicker = false },
        )
    }
}

@Composable
fun CurrencyInput(
    value: String,
    onChange: (String) -> Unit,
    placeholder: String = "Ex: 300",
    error: String? = null,
    icon: (@Composable () -> Unit)? = null,
) {
    val C = LocalColors
    
    val formattedValue = remember(value) { value }
    val hasError = error != null
    
    val handleValueChange = { newValue: String ->
        val digitsOnly = newValue.filter { it.isDigit() }
        onChange(digitsOnly)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(C.inputBg, RoundedCornerShape(13.dp))
            .border(
                width = if (hasError) 1.5.dp else 1.dp,
                color = if (hasError) C.pink else C.border,
                shape = RoundedCornerShape(13.dp)
            )
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        icon?.invoke()
        Box(modifier = Modifier.weight(1f)) {
            if (formattedValue.isEmpty()) {
                Text(placeholder, color = C.muted, fontSize = 14.sp)
            }
            BasicTextField(
                value = formattedValue,
                onValueChange = handleValueChange,
                textStyle = TextStyle(color = C.white, fontSize = 14.sp),
                cursorBrush = SolidColor(C.purple),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
            )
        }
        Icon(Icons.Filled.AttachMoney, contentDescription = null, tint = C.muted, modifier = Modifier.size(16.dp))
    }
    
    if (hasError) {
        // Error text would need to be rendered in a column wrapper
    }
}

