package com.vertice.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vertice.app.nav.BottomNav
import com.vertice.app.nav.Screen
import com.vertice.app.screens.ContactModal
import com.vertice.app.screens.EditProfileModal
import com.vertice.app.screens.HomeScreen
import com.vertice.app.screens.MatchScreen
import com.vertice.app.screens.OfferModal
import com.vertice.app.screens.PerfilScreen
import com.vertice.app.screens.ProfileModal
import com.vertice.app.screens.ProModal
import com.vertice.app.screens.TrilhaModal
import com.vertice.app.screens.VioletaScreen
import com.vertice.app.data.FREELANCERS
import com.vertice.app.ui.theme.LocalColors
import com.vertice.app.ui.theme.VerticeThemeProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { VerticeApp() }
    }
}

@Composable
fun VerticeApp() {
    var dark by rememberSaveable { mutableStateOf(true) }
    var screen by rememberSaveable { mutableStateOf(Screen.Home) }
    var violetaOn by rememberSaveable { mutableStateOf(false) }
    // Salvamos só o nome dos freelancers (serializável); resolvemos o objeto
    // na hora de renderizar, assim o estado sobrevive à rotação sem crash.
    var contactName by rememberSaveable { mutableStateOf<String?>(null) }
    var profileName by rememberSaveable { mutableStateOf<String?>(null) }
    var trilhaOpen by rememberSaveable { mutableStateOf(false) }
    // Progresso da Trilha de Blindagem (ids das lições concluídas), compartilhado
    // entre a TrilhaModal e a PerfilScreen. List<String> é serializável p/ rotação.
    var trilhaDone by rememberSaveable { mutableStateOf(listOf("s1", "s2", "s3", "d1", "d2", "d3")) }
    var editOpen by rememberSaveable { mutableStateOf(false) }
    var offerOpen by rememberSaveable { mutableStateOf(false) }
    var proOpen by rememberSaveable { mutableStateOf(false) }

    val contactTarget = contactName?.let { n -> FREELANCERS.find { it.name == n } }
    val profileTarget = profileName?.let { n -> FREELANCERS.find { it.name == n } }

    VerticeThemeProvider(dark = dark, onToggle = { dark = !dark }) {
        val C = LocalColors
        Box(modifier = Modifier.fillMaxSize().background(C.navy)) {
            when (screen) {
                Screen.Home -> HomeScreen(
                    onNav = { screen = it },
                    openPro = { proOpen = true },
                    openOffer = { offerOpen = true },
                )
                Screen.Match -> MatchScreen(
                    violetaOn = violetaOn,
                    onContact = { contactName = it.name },
                    onProfile = { profileName = it.name },
                )
                Screen.Violeta -> VioletaScreen(on = violetaOn, setOn = { violetaOn = it })
                Screen.Perfil -> PerfilScreen(
                    done = trilhaDone.toSet(),
                    openEdit = { editOpen = true },
                    openPro = { proOpen = true },
                    openTrilha = { trilhaOpen = true },
                )
                Screen.Confirmacao -> PlaceholderScreen("Confirmação")
            }

            BottomNav(active = screen, onNav = { screen = it }, modifier = Modifier.align(Alignment.BottomCenter))

            profileTarget?.let { f ->
                ProfileModal(
                    f = f,
                    onClose = { profileName = null },
                    onContact = { profileName = null; contactName = f.name },
                )
            }

            contactTarget?.let { f ->
                ContactModal(f = f, onClose = { contactName = null })
            }

            if (trilhaOpen) TrilhaModal(
                onClose = { trilhaOpen = false },
                initialDone = trilhaDone.toSet(),
                onDoneChange = { trilhaDone = it.toList() },
            )
            if (editOpen) EditProfileModal(onClose = { editOpen = false })
            if (offerOpen) OfferModal(onClose = { offerOpen = false })
            if (proOpen) ProModal(onClose = { proOpen = false })
        }
    }
}

@Composable
private fun PlaceholderScreen(name: String) {
    val C = LocalColors
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(C.navy),
        contentAlignment = Alignment.Center,
    ) {
        Text("$name — próxima entrega", color = C.muted)
    }
}

