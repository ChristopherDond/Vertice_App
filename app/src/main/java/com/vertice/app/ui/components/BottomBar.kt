package com.vertice.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vertice.app.nav.Tab
import com.vertice.app.ui.theme.LocalVerticeColors

@Composable
fun VerticeBottomBar(currentRoute: String?, onSelect: (Tab) -> Unit) {
    val colors = LocalVerticeColors.current
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        shadowElevation = 12.dp,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 10.dp, vertical = 9.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Tab.entries.forEach { tab ->
                val selected = currentRoute == tab.route
                val tint by animateColorAsState(
                    if (selected) colors.gradientStart else colors.textSecondary,
                    tween(260), label = "tabTint",
                )
                val pillWidth by animateDpAsState(if (selected) 30.dp else 0.dp, tween(260), label = "tabPill")
                val source = remember { MutableInteractionSource() }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .clickable(interactionSource = source, indication = null) { onSelect(tab) }
                        .padding(horizontal = 12.dp, vertical = 5.dp),
                ) {
                    Icon(
                        if (selected) tab.selectedIcon else tab.icon,
                        contentDescription = tab.label,
                        tint = tint,
                        modifier = Modifier.size(23.dp),
                    )
                    Spacer(Modifier.height(3.dp))
                    Text(
                        tab.label,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                        color = tint,
                    )
                    Spacer(Modifier.height(3.dp))
                    Box(
                        Modifier
                            .width(pillWidth)
                            .height(3.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(colors.gradientStart),
                    )
                }
            }
        }
    }
}
