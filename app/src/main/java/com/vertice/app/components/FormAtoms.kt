package com.vertice.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.ui.theme.LocalColors

/** Equivalente ao FLabel do React (rótulo de campo). */
@Composable
fun FLabel(text: String) {
    val C = LocalColors
    Text(text, color = C.mutedL, fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.padding(bottom = 7.dp))
}

/** Equivalente ao TInput do React (campo de texto de linha única, com ícone opcional). */
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

/** Equivalente ao TArea do React (textarea multi-linha). */
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

/** Equivalente ao SInput do React (select ⇒ dropdown de opções). */
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
