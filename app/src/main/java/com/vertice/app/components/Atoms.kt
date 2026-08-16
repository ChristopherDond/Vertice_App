package com.vertice.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.ui.theme.LocalColors
import com.vertice.app.ui.theme.LocalVerticeTheme
import androidx.compose.ui.res.painterResource

@Composable
fun Avatar(initials: String, size: Int = 46, bg: Color = LocalColors.purple, fontSize: Int = 15) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .background(bg, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = initials, color = Color.White, fontWeight = FontWeight.Bold, fontSize = fontSize.sp)
    }
}

@Composable
fun Avatar(
    freelancer: com.vertice.app.data.Freelancer,
    size: Int = 46,
    bg: Color = LocalColors.purple,
    fontSize: Int = 15,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(size.dp)
            .background(bg, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        val drawableRes = freelancer.photoRes
        if (drawableRes != null) {
            androidx.compose.foundation.Image(
                painter = painterResource(drawableRes),
                contentDescription = freelancer.name,
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                modifier = Modifier.size(size.dp).clip(CircleShape),
            )
        } else {
            Text(text = freelancer.initials, color = Color.White, fontWeight = FontWeight.Bold, fontSize = fontSize.sp)
        }
    }
}

@Composable
fun Pill(label: String, color: Color = LocalColors.purple, small: Boolean = false) {
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.10f), RoundedCornerShape(50))
            .border(1.dp, color.copy(alpha = 0.16f), RoundedCornerShape(50))
            .padding(horizontal = if (small) 10.dp else 13.dp, vertical = if (small) 2.dp else 4.dp),
    ) {
        Text(text = label, color = color, fontSize = if (small) 11.sp else 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SLabel(text: String) {
    val C = LocalColors
    Text(
        text = text.uppercase(),
        color = C.muted,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.9.sp,
        modifier = Modifier.padding(bottom = 13.dp),
    )
}

@Composable
fun HDivider(verticalPadding: Int = 20) {
    val C = LocalColors
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = verticalPadding.dp)
            .height(1.dp)
            .background(C.border),
    )
}

@Composable
fun ProgressRing(pct: Float, size: Int = 60, strokeWidth: Int = 5, color: Color = Color.White) {
    androidx.compose.foundation.Canvas(modifier = Modifier.size(size.dp)) {
        val stroke = Stroke(width = strokeWidth.dp.toPx(), cap = StrokeCap.Round)
        val inset = strokeWidth.dp.toPx() / 2
        val arcSize = Size(this.size.width - strokeWidth.dp.toPx(), this.size.height - strokeWidth.dp.toPx())
        drawArc(
            color = Color.White.copy(alpha = 0.18f),
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = androidx.compose.ui.geometry.Offset(inset, inset),
            size = arcSize,
            style = stroke,
        )
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 360f * (pct / 100f),
            useCenter = false,
            topLeft = androidx.compose.ui.geometry.Offset(inset, inset),
            size = arcSize,
            style = stroke,
        )
    }
}

@Composable
fun StatusBar() {
    val C = LocalColors
    val theme = LocalVerticeTheme.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 22.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        IconButton(
            onClick = theme.toggle,
            modifier = Modifier
                .size(30.dp)
                .background(C.card2, RoundedCornerShape(9.dp))
                .border(1.dp, C.border, RoundedCornerShape(9.dp)),
        ) {
            Icon(
                imageVector = if (theme.dark) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                contentDescription = "Alternar tema",
                tint = if (theme.dark) C.amber else C.purple,
                modifier = Modifier.size(14.dp),
            )
        }
    }
}

@Composable
fun ActivityRow(icon: @Composable () -> Unit, iconBg: Color, title: String, sub: String, time: String) {
    val C = LocalColors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(C.card, RoundedCornerShape(16.dp))
            .border(1.dp, C.border, RoundedCornerShape(16.dp))
            .padding(14.dp),
        horizontalArrangement = Arrangement.spacedBy(13.dp),
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(iconBg, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center,
        ) { icon() }
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = C.white, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, lineHeight = 18.sp)
            Spacer(Modifier.height(3.dp))
            Text(sub, color = C.muted, fontSize = 12.sp, lineHeight = 16.sp)
        }
        Text(time, color = C.muted, fontSize = 11.sp)
    }
}

