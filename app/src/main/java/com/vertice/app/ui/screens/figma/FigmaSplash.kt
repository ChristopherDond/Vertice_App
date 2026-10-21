package com.vertice.app.ui.screens.figma

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vertice.app.R
import com.vertice.app.ui.design.VColor
import com.vertice.app.ui.design.VGrad
import kotlinx.coroutines.delay

@Composable
fun FigmaSplash(onDone: () -> Unit = {}) {
    var visible by remember { mutableStateOf(false) }
    var glow by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(60)
        visible = true
        delay(650)
        glow = true
        delay(1150)
        onDone()
    }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.78f,
        animationSpec = tween(760, easing = androidx.compose.animation.core.FastOutSlowInEasing),
        label = "logoScale",
    )
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(620),
        label = "logoAlpha",
    )
    val breathe by animateFloatAsState(
        targetValue = if (glow) 1f else 0f,
        animationSpec = tween(700),
        label = "breathe",
    )
    val circle by animateFloatAsState(
        targetValue = if (glow) 1.55f else 1f,
        animationSpec = tween(1200, easing = androidx.compose.animation.core.FastOutSlowInEasing),
        label = "circle",
    )
    val circleAlpha by animateFloatAsState(
        targetValue = if (glow) 0f else 0.55f,
        animationSpec = tween(1200),
        label = "circleAlpha",
    )
    val bar by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(1100, easing = LinearEasing),
        label = "bar",
    )

    Box(Modifier.fillMaxSize().background(VColor.White), contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .size(220.dp)
                .scale(circle)
                .alpha(circleAlpha)
                .clip(RoundedCornerShape(999.dp))
                .background(VColor.PurpleWash),
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.img_22_235),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(200.dp).scale(scale).alpha(alpha),
            )
            Spacer(Modifier.height(64.dp))
            Box(
                Modifier
                    .width(96.dp)
                    .height(3.dp)
                    .clip(RoundedCornerShape(99.dp))
                    .background(VColor.PurpleWash),
            ) {
                Box(
                    Modifier
                        .width(96.dp * bar)
                        .height(3.dp)
                        .clip(RoundedCornerShape(99.dp))
                        .background(VGrad.CardPink),
                )
            }
        }
    }
}
