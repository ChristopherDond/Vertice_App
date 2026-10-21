package com.vertice.app.ui.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vertice.app.ui.design.VColor
import com.vertice.app.ui.design.VText
import com.vertice.app.ui.design.T
import com.vertice.app.ui.screens.figma.ContratarSheet
import com.vertice.app.ui.screens.figma.FigmaCadastro
import com.vertice.app.ui.screens.figma.FigmaInicial
import com.vertice.app.ui.screens.figma.FigmaLogin
import com.vertice.app.ui.screens.figma.FigmaMatch
import com.vertice.app.ui.screens.figma.FigmaPerfilUser
import com.vertice.app.ui.screens.figma.FigmaSplash
import com.vertice.app.ui.screens.figma.FigmaVioleta
import com.vertice.app.ui.screens.figma.VioletaState
import com.vertice.app.ui.screens.figma.FiltrosDialog
import com.vertice.app.ui.screens.figma.pros

object Screens {
    val all = listOf(
        "PROBE", "Splash", "Login", "Cadastro", "Inicial", "Match", "Perfil", "Violeta",
        "VioletaDesativado", "VioletaAtivo", "PopupContratar", "PopupFiltros",
    )
}

@Composable
fun ScreenGallery(id: String) {
    Box(Modifier.fillMaxSize().background(VColor.Bg)) {
        when (id) {
            "PROBE" -> Probe()
            "Splash" -> FigmaSplash()
            "Login" -> FigmaLogin()
            "Cadastro" -> FigmaCadastro()
            "Inicial" -> FigmaInicial()
            "Match", "PopupContratar", "PopupFiltros" ->
                FigmaMatch(onNav = {}, onContratar = {}, onFiltros = {}, onVerPerfil = {})
            "Perfil" -> FigmaPerfilUser(onNav = {})
            "Violeta" -> FigmaVioleta(onNav = {})
            "VioletaDesativado" -> FigmaVioleta(onNav = {}, initialState = VioletaState.DESATIVADO)
            "VioletaAtivo" -> FigmaVioleta(onNav = {}, initialState = VioletaState.ATIVO)
            else -> Placeholder(id)
        }
        when (id) {
            "PopupContratar" -> ContratarSheet(pros[0], onDismiss = {})
            "PopupFiltros" -> FiltrosDialog(onDismiss = {})
            else -> Unit
        }
    }
}

@Composable
private fun Placeholder(name: String) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        VText(name, T.greeting, VColor.Ink)
        Box(
            Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(top = 12.dp)
                .background(VColor.PurpleLine),
        )
    }
}