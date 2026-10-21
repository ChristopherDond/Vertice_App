package com.vertice.app.ui.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable

@Composable
fun VScreen(
    bg: Color = VColor.Bg,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(bg),
        content = content,
    )
}

@Composable
fun VCard(
    modifier: Modifier = Modifier,
    bg: Color = VColor.White,
    radius: Dp = 16.dp,
    shadow: VShadowSpec? = VShadow.card,
    padding: PaddingValues = PaddingValues(0.dp),
    brush: Brush? = null,
    border: Color? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(radius)
    Column(
        modifier
            .then(if (shadow != null) Modifier.shadow(shadow.blur, shape) else Modifier)
            .clip(shape)
            .then(if (brush != null) Modifier.background(brush) else Modifier.background(bg))
            .then(if (border != null) Modifier.padding(1.dp) else Modifier)
            .padding(padding),
    ) {
        if (border != null) {
            Box(Modifier.clip(shape).background(bg)) { Column(content = content) }
        } else content()
    }
}

@Composable
fun VRow(
    modifier: Modifier = Modifier,
    gap: Dp = 8.dp,
    vertical: Alignment.Vertical = Alignment.CenterVertically,
    horizontal: Arrangement.Horizontal = Arrangement.Start,
    content: @Composable RowScope.() -> Unit,
) = Row(modifier, verticalAlignment = vertical, horizontalArrangement = horizontal.takeIf { gap == 8.dp } ?: Arrangement.spacedBy(gap), content = content)

@Composable
fun VText(
    text: String,
    style: TextStyle = T.body,
    color: Color = VColor.Ink,
    modifier: Modifier = Modifier,
    align: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
) = Text(text = text, modifier = modifier, color = color, style = style, maxLines = maxLines, textAlign = align)

@Composable
fun VGap(h: Dp = 0.dp, w: Dp = 0.dp) = Spacer(Modifier.size(width = w, height = h))

@Composable
fun VIcon(
    icon: ImageVector,
    size: Dp = 20.dp,
    tint: Color = VColor.Ink,
    modifier: Modifier = Modifier,
) = androidx.compose.material3.Icon(icon, null, modifier.size(size), tint = tint)

@Composable
fun VBox(modifier: Modifier = Modifier, content: @Composable androidx.compose.foundation.layout.BoxScope.() -> Unit) =
    Box(modifier, content = content)

@Composable
fun VDivider(color: Color = VColor.Gray5, modifier: Modifier = Modifier) =
    Box(modifier.fillMaxWidth().height(1.dp).background(color))

@Composable
fun VClickable(modifier: Modifier = Modifier, onClick: () -> Unit, content: @Composable () -> Unit) {
    Box(modifier.clickable(onClick = onClick)) { content() }
}
