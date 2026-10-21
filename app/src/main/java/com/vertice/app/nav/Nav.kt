package com.vertice.app.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class Tab(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
) {
    INICIO("inicio", "Início", Icons.Outlined.Home, Icons.Filled.Home),
    MATCH("match", "Match", Icons.Outlined.Search, Icons.Filled.Search),
    FINANCEIRO("financeiro", "Financeiro", Icons.Outlined.AttachMoney, Icons.Filled.AttachMoney),
    PERFIL("perfil", "Perfil", Icons.Outlined.Person, Icons.Filled.Person),
}

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val FORGOT = "forgot"

    const val INICIO = "inicio"
    const val MATCH = "match"
    const val FINANCEIRO = "financeiro"
    const val PERFIL = "perfil"

    const val VIOLETA = "violeta"
    const val VIOLETA_VERIFY = "violeta_verify"
    const val TRILHA = "trilha"
    const val LESSON = "lesson"
    const val PRO = "pro"
    const val PRO_CONFIRM = "pro_confirm"
    const val OFFER = "offer"
    const val CONTACT = "contact"
    const val FILTER = "filter"
    const val CONFIG = "config"
    const val ALT_EMAIL = "alt_email"
    const val ALT_PHONE = "alt_phone"
    const val ALT_PASSWORD = "alt_password"
    const val PIX = "pix"
    const val TRANSFER = "transfer"
    const val PAY = "pay"
    const val CARD = "card"
}
