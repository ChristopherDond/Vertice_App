package com.vertice.app.ui.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.vertice.app.ui.design.VColor
import com.vertice.app.ui.design.VText
import com.vertice.app.ui.design.vs
import com.vertice.app.ui.design.Roboto

@Composable
fun Probe() {
    val d = LocalDensity.current
    val cfg = LocalConfiguration.current
    Box(Modifier.fillMaxSize().background(VColor.Bg)) {
        Box(Modifier.size(100.dp).background(Color.Magenta))
        Box(
            Modifier
                .align(Alignment.TopEnd)
                .size(50.dp)
                .background(Color.Cyan),
        )
        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(40.dp)
                .background(Color(0xFF00FF00)),
        )
        Column(Modifier.align(Alignment.Center)) {
            VText("d=${d.density} fs=${d.fontScale}", vs(14f, 700, 18f, 0f, Roboto), Color.Black)
            VText("sw=${cfg.screenWidthDp} sh=${cfg.screenHeightDp} dp=${cfg.densityDpi}", vs(14f, 700, 18f, 0f, Roboto), Color.Black)
            Box(Modifier.width(333.dp).height(8.dp).background(Color.Red))
            Box(Modifier.width(157.dp).height(8.dp).background(Color.Blue))
        }
    }
}
