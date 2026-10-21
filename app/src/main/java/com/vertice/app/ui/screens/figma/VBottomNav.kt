package com.vertice.app.ui.screens.figma

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vertice.app.ui.design.Roboto
import com.vertice.app.ui.design.VColor
import com.vertice.app.ui.design.VText
import com.vertice.app.ui.design.vs
import com.vertice.app.ui.icons.IcoAntDesignHomeFilled
import com.vertice.app.ui.icons.IcoBiShuffle
import com.vertice.app.ui.icons.IcoBoxiconsShieldFilled
import com.vertice.app.ui.icons.IcoIconamoonProfileFill
import com.vertice.app.ui.components.vClick
import com.vertice.app.ui.components.PressAccent

enum class VTab(val label: String, val icon: ImageVector) {
    INICIO("Início", IcoAntDesignHomeFilled),
    MATCH("Match", IcoBiShuffle),
    VIOLETA("Violeta", IcoBoxiconsShieldFilled),
    PERFIL("Perfil", IcoIconamoonProfileFill),
}

@Composable
fun VBottomNav(
    active: VTab,
    onSelect: (VTab) -> Unit,
    modifier: Modifier = Modifier,
    height: Int = 76,
    navPadding: Boolean = false,
) {
    Row(
        modifier
            .fillMaxWidth()
            .then(if (navPadding) Modifier.navigationBarsPadding() else Modifier)
            .height(height.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        VTab.entries.forEach { tab ->
            val tint = if (tab == active) VColor.PurpleMid else VColor.Gray
            Column(
                Modifier
                    .weight(1f)
                    .vClick(PressAccent, scaleDown = 0.92f) { onSelect(tab) },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(tab.icon, null, Modifier.size(23.dp), tint = tint)
                Spacer(Modifier.height(2.dp))
                VText(
                    tab.label,
                    vs(10f, 400, 11.73f, 0f, Roboto),
                    tint,
                    Modifier.width(60.dp),
                    align = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun VNavSpacer(height: Int = 76) = Box(Modifier.fillMaxWidth().height(height.dp))
