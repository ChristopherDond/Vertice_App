package com.vertice.app.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.components.clickableNoRipple
import com.vertice.app.components.drawTopBorder
import com.vertice.app.ui.theme.LocalColors

private data class NavItem(val screen: Screen, val label: String, val icon: ImageVector)

private val NAV_ITEMS = listOf(
    NavItem(Screen.Home, "Início", Icons.Filled.Home),
    NavItem(Screen.Match, "Match", Icons.Filled.Shuffle),
    NavItem(Screen.Violeta, "Violeta", Icons.Filled.Shield),
    NavItem(Screen.Perfil, "Perfil", Icons.Filled.Person),
)

@Composable
fun BottomNav(active: Screen, onNav: (Screen) -> Unit, modifier: Modifier = Modifier) {
    val C = LocalColors
    val effective = if (active == Screen.Confirmacao) Screen.Match else active

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(76.dp)
            .background(C.card)
            .drawTopBorder(C.border),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        NAV_ITEMS.forEach { item ->
            val isOn = effective == item.screen
            Column(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clickableNoRipple { onNav(item.screen) }
                    .padding(horizontal = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = if (isOn) C.purple else C.muted,
                    modifier = Modifier.size(21.dp),
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = item.label,
                    fontSize = 10.sp,
                    color = if (isOn) C.purple else C.muted,
                )
                Spacer(Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .background(if (isOn) C.purple else Color.Transparent, RoundedCornerShape(50)),
                )
            }
        }
    }
}

