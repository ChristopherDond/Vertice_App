package com.vertice.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.ui.theme.LocalVerticeColors

@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leading: (@Composable () -> Unit)? = null,
    onClick: () -> Unit,
) {
    val colors = LocalVerticeColors.current
    val alpha = if (enabled) 1f else 0.45f
    Surface(
        modifier = modifier
            .height(48.dp)
            .alphaAnim(enabled)
            .clip(RoundedCornerShape(14.dp))
            .clickable(enabled = enabled) { onClick() },
        color = Color.Transparent,
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Brush.horizontalGradient(listOf(colors.gradientStart, colors.gradientEnd))),
            contentAlignment = Alignment.Center,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (leading != null) { leading(); Spacer(Modifier.width(8.dp)) }
                Text(
                    text,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                )
            }
        }
    }
}

@Composable
fun SecondaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val colors = LocalVerticeColors.current
    Surface(
        modifier = modifier
            .height(48.dp)
            .alphaAnim(enabled)
            .clip(RoundedCornerShape(14.dp))
            .border(1.5.dp, colors.gradientStart.copy(alpha = 0.55f), RoundedCornerShape(14.dp))
            .clickable(enabled = enabled) { onClick() },
        color = Color.Transparent,
    ) {
        Box(Modifier.fillMaxWidth().height(48.dp), contentAlignment = Alignment.Center) {
            Text(
                text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = colors.gradientStart,
            )
        }
    }
}

private fun Modifier.alphaAnim(enabled: Boolean): Modifier = this

@Composable
fun VerticeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = 4,
    leadingIcon: (@Composable (() -> Unit))? = null,
    trailing: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    supportingText: String? = null,
) {
    val colors = LocalVerticeColors.current
    var revealed by remember { mutableStateOf(false) }
    Column(modifier.fillMaxWidth()) {
        Text(
            label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(7.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp),
            singleLine = singleLine,
            minLines = minLines,
            maxLines = maxLines,
            placeholder = {
                Text(placeholder, style = MaterialTheme.typography.bodyMedium, color = colors.textSecondary)
            },
            visualTransformation = if (isPassword && !revealed) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else keyboardType),
            shape = RoundedCornerShape(13.dp),
            isError = isError,
            leadingIcon = leadingIcon,
            trailingIcon = {
                when {
                    isPassword -> TextButton(onClick = { revealed = !revealed }, modifier = Modifier.size(44.dp)) {
                        Icon(
                            if (revealed) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = null,
                            tint = colors.textSecondary,
                            modifier = Modifier.size(19.dp),
                        )
                    }
                    trailing != null -> trailing()
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colors.gradientStart,
                unfocusedBorderColor = colors.textSecondary.copy(alpha = 0.35f),
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                errorContainerColor = MaterialTheme.colorScheme.surface,
            ),
        )
        if (supportingText != null) {
            Spacer(Modifier.height(5.dp))
            Text(
                supportingText,
                style = MaterialTheme.typography.labelSmall,
                color = if (isError) MaterialTheme.colorScheme.error else colors.textSecondary,
            )
        }
    }
}

@Composable
fun SocialButton(
    text: String,
    provider: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val colors = LocalVerticeColors.current
    Surface(
        modifier = modifier
            .height(43.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, colors.textSecondary.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .clickable { onClick() },
        color = MaterialTheme.colorScheme.surface,
    ) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                if (provider == "google") "G" else "",
                fontWeight = FontWeight.Black,
                fontSize = 16.sp,
                color = if (provider == "google") Color(0xFF4285F4) else MaterialTheme.colorScheme.onSurface,
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}


@Composable
fun SelectableChip(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val colors = LocalVerticeColors.current
    val bg by animateColorAsState(
        if (selected) colors.gradientStart else MaterialTheme.colorScheme.surface,
        tween(220), label = "chipBg",
    )
    val border by animateColorAsState(
        if (selected) colors.gradientStart else colors.textSecondary.copy(alpha = 0.3f),
        tween(220), label = "chipBorder",
    )
    Surface(
        modifier = modifier
            .height(32.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, border, RoundedCornerShape(16.dp))
            .clickable { onClick() },
        color = bg,
    ) {
        Box(Modifier.padding(horizontal = 16.dp), contentAlignment = Alignment.Center) {
            Text(
                text,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
fun SelectableRow(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val colors = LocalVerticeColors.current
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                1.dp,
                if (selected) colors.gradientStart else colors.textSecondary.copy(alpha = 0.25f),
                RoundedCornerShape(12.dp),
            )
            .clickable { onClick() },
        color = if (selected) colors.gradientStart.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface,
    ) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface,
            )
            if (selected) {
                Icon(
                    androidx.compose.material.icons.Icons.Filled.Check,
                    contentDescription = null,
                    tint = colors.gradientStart,
                    modifier = Modifier.size(16.dp),
                )
            }
        }
    }
}
