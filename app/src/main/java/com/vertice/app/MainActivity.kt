package com.vertice.app

import androidx.compose.foundation.clickable

import androidx.compose.foundation.background

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.vertice.app.core.di.ServiceLocator
import com.vertice.app.ui.screens.figma.ContratarSheet
import com.vertice.app.ui.screens.figma.FigmaCadastro
import com.vertice.app.ui.screens.figma.FigmaInicial
import com.vertice.app.ui.screens.figma.FigmaLogin
import com.vertice.app.ui.screens.figma.FigmaMatch
import com.vertice.app.ui.screens.figma.FigmaEditarPerfil
import com.vertice.app.ui.screens.figma.FigmaPerfilUser
import com.vertice.app.ui.screens.figma.FigmaSplash
import com.vertice.app.ui.screens.figma.FigmaVioleta
import com.vertice.app.ui.screens.figma.FiltrosDialog
import com.vertice.app.ui.screens.figma.ProMatch
import com.vertice.app.ui.screens.figma.VTab
import com.vertice.app.ui.theme.VerticeTheme
import androidx.compose.animation.core.animateFloatAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ServiceLocator.init(applicationContext)
        enableEdgeToEdge()
        setContent {
            VerticeTheme {
                Flow()
            }
        }
    }
}

private enum class Step { SPLASH, LOGIN, CADASTRO, INICIAL, MATCH, VIOLETA, PERFIL, EDITAR_PERFIL }

@Composable
private fun Flow() {
    var step by remember { mutableStateOf(Step.SPLASH) }
    var contratar by remember { mutableStateOf<ProMatch?>(null) }
    var filtros by remember { mutableStateOf(false) }

    BackHandler(enabled = contratar != null || filtros) {
        if (filtros) filtros = false else contratar = null
    }

    Crossfade(targetState = step, animationSpec = tween(320), label = "flow") { current ->
        when (current) {
            Step.SPLASH -> FigmaSplash(onDone = { step = Step.LOGIN })
            Step.LOGIN -> FigmaLogin(onEntrar = { step = Step.INICIAL }, onCadastrar = { step = Step.CADASTRO })
            Step.CADASTRO -> FigmaCadastro(onBack = { step = Step.LOGIN })
            Step.INICIAL -> FigmaInicial(onNav = { tab ->
                when (tab) {
                    VTab.MATCH -> step = Step.MATCH
                    VTab.VIOLETA -> step = Step.VIOLETA
                    else -> {}
                }
            })
            Step.MATCH -> FigmaMatch(
                onNav = { tab ->
                    when (tab) {
                        VTab.INICIO -> step = Step.INICIAL
                        VTab.VIOLETA -> step = Step.VIOLETA
                        VTab.PERFIL -> step = Step.PERFIL
                        else -> {}
                    }
                },
                onContratar = { contratar = it },
                onFiltros = { filtros = true },
                onVerPerfil = { step = Step.PERFIL },
            )
            Step.VIOLETA -> FigmaVioleta(onNav = { tab ->
                when (tab) {
                    VTab.INICIO -> step = Step.INICIAL
                    VTab.MATCH -> step = Step.MATCH
                    VTab.PERFIL -> step = Step.PERFIL
                    else -> {}
                }
            })
            Step.PERFIL -> FigmaPerfilUser(
                onNav = { tab ->
                    when (tab) {
                        VTab.INICIO -> step = Step.INICIAL
                        VTab.MATCH -> step = Step.MATCH
                        VTab.VIOLETA -> step = Step.VIOLETA
                        else -> {}
                    }
                },
                onSettings = { step = Step.EDITAR_PERFIL },
            )
            Step.EDITAR_PERFIL -> FigmaEditarPerfil(
                onBack = { step = Step.PERFIL },
                onLogout = { step = Step.LOGIN },
            )
        }
    }

    Box(Modifier.fillMaxSize()) {
        contratar?.let { p ->
            ContratarSheet(p) { contratar = null }
        }
        if (filtros) {
            val scrim by animateFloatAsState(if (filtros) 1f else 0f, tween(300), label = "scrim")
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f * scrim))
                    .clickable { filtros = false },
            )
            Box(Modifier.align(Alignment.BottomCenter)) {
                FiltrosDialog { filtros = false }
            }
        }
    }
}
