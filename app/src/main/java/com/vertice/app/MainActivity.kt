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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vertice.app.nav.BottomNav
import com.vertice.app.nav.Screen
import com.vertice.app.screens.ContactModal
import com.vertice.app.screens.EditProfileModal
import com.vertice.app.screens.HomeScreen
import com.vertice.app.screens.MatchScreen
import com.vertice.app.screens.PerfilScreen
import com.vertice.app.screens.ProfileModal
import com.vertice.app.screens.TrilhaModal
import com.vertice.app.screens.VioletaScreen
import com.vertice.app.data.Freelancer
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
    var dark by remember { mutableStateOf(true) }
    var screen by remember { mutableStateOf(Screen.Home) }
    var violetaOn by remember { mutableStateOf(false) }
    var contactTarget by remember { mutableStateOf<Freelancer?>(null) }
    var profileTarget by remember { mutableStateOf<Freelancer?>(null) }
    var trilhaOpen by remember { mutableStateOf(false) }
    var editOpen by remember { mutableStateOf(false) }

    VerticeThemeProvider(dark = dark, onToggle = { dark = !dark }) {
        val C = LocalColors
        Box(modifier = Modifier.fillMaxSize().background(C.navy)) {
            when (screen) {
                Screen.Home -> HomeScreen(
                    onNav = { screen = it },
                    openPro = {  },
                    openOffer = {  },
                )
                Screen.Match -> MatchScreen(
                    violetaOn = violetaOn,
                    onContact = { contactTarget = it },
                    onProfile = { profileTarget = it },
                )
                Screen.Violeta -> VioletaScreen(on = violetaOn, setOn = { violetaOn = it })
                Screen.Perfil -> PerfilScreen(
                    openEdit = { editOpen = true },
                    openPro = {  },
                    openTrilha = { trilhaOpen = true },
                )
                Screen.Confirmacao -> PlaceholderScreen("Confirmação")
            }

            BottomNav(active = screen, onNav = { screen = it }, modifier = Modifier.align(Alignment.BottomCenter))

            profileTarget?.let { f ->
                ProfileModal(
                    f = f,
                    onClose = { profileTarget = null },
                    onContact = { profileTarget = null; contactTarget = f },
                )
            }

            contactTarget?.let { f ->
                ContactModal(f = f, onClose = { contactTarget = null })
            }

            if (trilhaOpen) TrilhaModal(onClose = { trilhaOpen = false })
            if (editOpen) EditProfileModal(onClose = { editOpen = false })
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

