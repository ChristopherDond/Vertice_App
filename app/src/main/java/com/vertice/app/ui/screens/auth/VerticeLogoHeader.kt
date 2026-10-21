package com.vertice.app.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vertice.app.R
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun VerticeLogoHeader(size: Int = 120) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Image(
            painterResource(R.drawable.logo_vertice),
            contentDescription = "Vértice",
            modifier = Modifier.size(size.dp).clip(RoundedCornerShape(28.dp)),
        )
    }
}
