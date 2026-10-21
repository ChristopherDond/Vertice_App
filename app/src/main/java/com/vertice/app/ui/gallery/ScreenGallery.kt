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
import com.vertice.app.ui.screens.figma.FigmaCadastro
import com.vertice.app.ui.screens.figma.FigmaInicial
import com.vertice.app.ui.screens.figma.FigmaLogin
import com.vertice.app.ui.screens.figma.FigmaSplash

object Screens {
    val all = listOf(
        "Splash", "Login", "Cadastro", "EsqueciSenha", "Inicial", "Match",
        "Filtro", "Perfil", "Configuracoes", "Trilha", "Pro", "ProConfirmacao",
        "OferecerServico", "ContatarPrestador", "VioletaOff", "VioletaVerify",
        "VioletaOn", "Financeiro", "FinanceiroReal", "FinanceiroAlt",
        "CartaoDebito", "BotaoAssinar", "Menu",
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
            "EsqueciSenha" -> Placeholder("EsqueciSenha")
            "Inicial" -> FigmaInicial()
            "Match" -> Placeholder("Match")
            "Filtro" -> Placeholder("Filtro")
            "Perfil" -> Placeholder("Perfil")
            "Configuracoes" -> Placeholder("Configuracoes")
            "Trilha" -> Placeholder("Trilha")
            "Pro" -> Placeholder("Pro")
            "ProConfirmacao" -> Placeholder("ProConfirmacao")
            "OferecerServico" -> Placeholder("OferecerServico")
            "ContatarPrestador" -> Placeholder("ContatarPrestador")
            "VioletaOff" -> Placeholder("VioletaOff")
            "VioletaVerify" -> Placeholder("VioletaVerify")
            "VioletaOn" -> Placeholder("VioletaOn")
            "Financeiro" -> Placeholder("Financeiro")
            "FinanceiroReal" -> Placeholder("FinanceiroReal")
            "FinanceiroAlt" -> Placeholder("FinanceiroAlt")
            "CartaoDebito" -> Placeholder("CartaoDebito")
            "BotaoAssinar" -> Placeholder("BotaoAssinar")
            "Menu" -> Placeholder("Menu")
            else -> Placeholder("?$id")
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
