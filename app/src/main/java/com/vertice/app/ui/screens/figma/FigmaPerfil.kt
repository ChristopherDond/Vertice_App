package com.vertice.app.ui.screens.figma

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.vertice.app.R
import com.vertice.app.core.di.ServiceLocator
import com.vertice.app.core.result.onOk
import com.vertice.app.domain.model.User
import com.vertice.app.ui.components.PressSoft
import com.vertice.app.ui.components.vClick
import com.vertice.app.ui.design.Roboto
import com.vertice.app.ui.design.VColor
import com.vertice.app.ui.design.vs
import com.vertice.app.ui.icons.IcoAkarIconsCheck
import com.vertice.app.ui.icons.IcoArrowRightBold
import com.vertice.app.ui.icons.IcoBoxiconsShieldFilled
import com.vertice.app.ui.icons.IcoChevronRightBold
import com.vertice.app.ui.icons.IcoCrosshairFill
import com.vertice.app.ui.icons.IcoFileTextFill
import com.vertice.app.ui.icons.IcoImageFill
import com.vertice.app.ui.icons.IcoPencilSimpleFill

private object VPerfil {
    val bg = VColor.Bg
    val gradPro = listOf(Color(0xFF641BE2), Color(0xFF9B13AC), Color(0xFFD20B76))
    val gradVioleta = listOf(Color(0xFF4C10D8), Color(0xFF8B12B6), Color(0xFFD61476))
    val gradBar = listOf(Color(0xFF641BE2), Color(0xFF9B13AC), Color(0xFFD20B76))
    val chipBg = Color(0x269657F3)
    val chipLine = Color(0x4D904EF2)
    val chipText = VColor.Purple
    val divider = Color(0xFFCDC9D4)
    val track = VColor.Gray4
    val doneBg = Color(0x269657F3)
    val doneLine = Color(0x4D904EF2)
    val doneSquare = VColor.PurpleSoft
    val green = Color(0xFF21C001)
    val lockBg = Color(0xFFF4F4F4)
    val lockLine = Color(0xFFEAEAEA)
    val lockInk = VColor.Gray2
    val configBg = Color(0xFFEDE6F5)
}

data class TrailItem(
    val title: String,
    val icon: ImageVector,
    val done: Boolean,
    val locked: Boolean,
)

object PerfilMock {
    const val name = "Meu Perfil"
    const val planTitle = "Vertice Pro"
    const val planSubtitle = "Desbloqueie novos recursos, matchs e muito mais!"
    val skills = emptyList<String>()
    const val trailTitle = "Trilha de blindagem"
    const val trailProgress = 0
    const val trailFooter = "Toque para ver a trilha completa"
    const val settings = "Configurações"
    val trail = listOf(
        TrailItem("Segurança pessoal", IcoBoxiconsShieldFilled, false, false),
        TrailItem("Direitos do MEI", IcoFileTextFill, false, false),
        TrailItem("Gestão de clientes", IcoCrosshairFill, false, false),
    )
}

@Composable
fun FigmaPerfilUser(onNav: (VTab) -> Unit, onSettings: () -> Unit = {}, onTrail: () -> Unit = {}, onFinanceiro: () -> Unit = {}, onPro: () -> Unit = {}) {
    val scroll = rememberScrollState()
    var user by remember { mutableStateOf<User?>(null) }
    var isPro by remember { mutableStateOf(false) }
    var violetaAtiva by remember { mutableStateOf(false) }
    val session by ServiceLocator.sessionManager.session.collectAsState(initial = null)
    val trilhaDone by ServiceLocator.profileRepository.trilhaProgress().collectAsState(initial = emptySet())
    val trilhaPercent = if (trilhaDone.isNotEmpty()) ((trilhaDone.size.toFloat() / 15f) * 100).toInt().coerceIn(0, 100) else 0

    val profileName = user?.name?.ifBlank { session?.displayName.orEmpty().ifBlank { PerfilMock.name } }
        ?: session?.displayName.orEmpty().ifBlank { PerfilMock.name }

    LaunchedEffect(Unit) {
        ServiceLocator.profileRepository.me().onOk { user = it }
        ServiceLocator.financeRepository.subscription().onOk { isPro = it.active }
        ServiceLocator.profileRepository.violeta().onOk { violetaAtiva = it.enabled }
    }

    val showPro = isPro || user?.isPro == true
    val showVioleta = violetaAtiva || user?.violetaVerified == true

    Box(Modifier.fillMaxSize().background(VPerfil.bg)) {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(scroll)
                .statusBarsPadding()
                .padding(bottom = 110.dp),
        ) {
            Avatar(
                user = user,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 12.dp),
            )
            Text(
                profileName,
                modifier = Modifier.fillMaxWidth().padding(top = 13.dp),
                style = vs(32f, 800, 37.64f, 0f, Roboto),
                color = Color.Black,
                maxLines = 1,
                textAlign = TextAlign.Center,
            )
            if (showPro || showVioleta) {
                Row(
                    Modifier.fillMaxWidth().padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                ) {
                    if (showPro) {
                        Insignia("Vertice Pro", Icons.Filled.WorkspacePremium, VPerfil.gradPro)
                    }
                    if (showVioleta) {
                        Insignia("Protocolo Violeta", IcoBoxiconsShieldFilled, VPerfil.gradVioleta)
                    }
                }
            }
            if (!showPro) {
                PlanCard(onPro, Modifier.padding(top = 18.dp).padding(horizontal = 16.dp))
            }
            Box(
                Modifier
                    .padding(top = 12.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(VPerfil.divider),
            )
            Text(
                "Minhas habilidades:",
                modifier = Modifier.padding(start = 16.dp, top = 5.dp),
                style = vs(18f, 400, 21.17f, 0f, Roboto),
                color = Color.Black,
                maxLines = 1,
            )
            SkillsRow(skills = user?.skills ?: emptyList(), modifier = Modifier.padding(start = 17.dp, top = 7.dp))
            TrailCard(
                progress = trilhaPercent,
                onOpen = onTrail,
                modifier = Modifier
                    .padding(start = 19.dp, top = 15.dp, end = 14.dp)
                    .fillMaxWidth(),
            )
            FinanceButton(
                onClick = onFinanceiro,
                modifier = Modifier
                    .padding(start = 19.dp, top = 13.dp, end = 14.dp)
                    .fillMaxWidth(),
            )
            SettingsButton(
                onClick = onSettings,
                modifier = Modifier
                    .padding(start = 19.dp, top = 13.dp, end = 14.dp)
                    .fillMaxWidth(),
            )
            Spacer(Modifier.height(24.dp))
        }
        VBottomNav(
            VTab.PERFIL,
            onNav,
            Modifier.align(Alignment.BottomCenter).zIndex(10f),
        )
    }
}

@Composable
private fun Avatar(user: User?, modifier: Modifier = Modifier) {
    Box(
        modifier
            .size(135.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(0.75.dp, VColor.InkSoft, CircleShape)
            .semantics { contentDescription = "Foto de perfil do usuário" },
        contentAlignment = Alignment.Center,
    ) {
        if (!user?.avatarUrl.isNullOrBlank() || user?.name?.contains("Ana", ignoreCase = true) == true) {
            Image(
                painter = painterResource(R.drawable.ana_profile),
                contentDescription = null,
                modifier = Modifier.fillMaxSize().clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
        } else {
            Icon(
                IcoImageFill,
                contentDescription = null,
                modifier = Modifier.size(42.dp),
                tint = VColor.InkSoft,
            )
        }
    }
}

@Composable
private fun PlanCard(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .height(82.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Brush.horizontalGradient(VPerfil.gradPro))
            .vClick(PressSoft) { onClick() }
            .semantics { contentDescription = "Vertice Pro, assinar" }
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            IcoBoxiconsShieldFilled,
            contentDescription = null,
            modifier = Modifier.size(35.dp),
            tint = Color.White,
        )
        Spacer(Modifier.width(19.dp))
        Column(Modifier.weight(1f)) {
            Text(
                PerfilMock.planTitle,
                style = vs(20f, 800, 23.52f, 0f, Roboto),
                color = Color.White,
                maxLines = 1,
            )
            Text(
                PerfilMock.planSubtitle,
                modifier = Modifier.padding(top = 2.dp),
                style = vs(13f, 400, 15.29f, 0f, Roboto),
                color = Color.White,
                maxLines = 2,
            )
        }
        Spacer(Modifier.width(8.dp))
        Icon(
            IcoChevronRightBold,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color.White,
        )
    }
}

@Composable
private fun Insignia(label: String, icon: ImageVector, cores: List<Color>) {
    val shape = RoundedCornerShape(100.dp)
    Row(
        Modifier
            .shadow(4.dp, shape, ambientColor = cores.last(), spotColor = cores.first())
            .clip(shape)
            .background(Brush.horizontalGradient(cores))
            .border(1.dp, Color.White.copy(alpha = 0.35f), shape)
            .padding(start = 8.dp, end = 12.dp)
            .height(32.dp)
            .semantics { contentDescription = "Insígnia $label" },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier.size(20.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center,
        ) { Icon(icon, null, Modifier.size(13.dp), tint = Color.White) }
        Spacer(Modifier.width(6.dp))
        Text(label, style = vs(14f, 700, 16.5f, 0f, Roboto), color = Color.White, maxLines = 1)
    }
}

@Composable
private fun SkillsRow(skills: List<String>, modifier: Modifier = Modifier) {
    if (skills.isEmpty()) {
        Text(
            "Nenhuma habilidade cadastrada ainda.",
            modifier = modifier.padding(end = 16.dp),
            style = vs(14f, 400, 18f, 0f, Roboto),
            color = VColor.Gray,
        )
    } else {
        Row(
            modifier = modifier
                .padding(end = 16.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            skills.forEach { label ->
                Box(
                    Modifier
                        .height(32.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(VPerfil.chipBg)
                        .border(0.75.dp, VPerfil.chipLine, RoundedCornerShape(100.dp))
                        .padding(horizontal = 14.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        label,
                        style = vs(16f, 500, 18.82f, 0f, Roboto),
                        color = VPerfil.chipText,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

@Composable
private fun TrailCard(progress: Int, onOpen: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
            .padding(start = 10.dp, end = 13.dp, top = 15.dp, bottom = 15.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                PerfilMock.trailTitle,
                modifier = Modifier.weight(1f),
                style = vs(18f, 400, 21.17f, 0f, Roboto),
                color = Color.Black,
                maxLines = 1,
            )
            Box(
                Modifier
                    .height(32.dp)
                    .width(46.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(VPerfil.chipBg)
                    .border(0.75.dp, VPerfil.chipLine, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    "$progress%",
                    style = vs(16f, 500, 18.82f, 0f, Roboto),
                    color = VPerfil.chipText,
                    maxLines = 1,
                )
            }
            Spacer(Modifier.width(6.dp))
            Icon(
                IcoChevronRightBold,
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                tint = Color.Black,
            )
        }
        ProgressBar(progress, Modifier.fillMaxWidth().padding(top = 8.dp))
        Column(Modifier.padding(top = 13.dp)) {
            PerfilMock.trail.forEachIndexed { index, item ->
                if (index > 0) Spacer(Modifier.height(9.dp))
                TrailRow(item)
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .vClick(PressSoft, onClick = onOpen)
                .semantics { contentDescription = PerfilMock.trailFooter },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                PerfilMock.trailFooter,
                modifier = Modifier.weight(1f),
                style = vs(16f, 400, 18.82f, 0f, Roboto),
                color = Color.Black,
                maxLines = 1,
            )
            Icon(
                IcoArrowRightBold,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Black,
            )
        }
    }
}

@Composable
private fun ProgressBar(progress: Int, modifier: Modifier = Modifier) {
    Box(
        modifier
            .height(9.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(VPerfil.track),
    ) {
        Box(
            Modifier
                .fillMaxWidth((progress / 100f).coerceIn(0.02f, 1f))
                .height(9.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(Brush.horizontalGradient(VPerfil.gradBar)),
        )
    }
}

@Composable
private fun TrailRow(item: TrailItem) {
    val bg = if (item.locked) VPerfil.lockBg else VPerfil.doneBg
    val line = if (item.locked) VPerfil.lockLine else VPerfil.doneLine
    val square = if (item.locked) VPerfil.lockInk else VPerfil.doneSquare
    val label = if (item.locked) VPerfil.lockInk else Color.Black
    Row(
        Modifier
            .fillMaxWidth()
            .height(61.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(bg)
            .border(
                if (item.locked) 1.dp else 0.75.dp,
                line,
                RoundedCornerShape(10.dp),
            )
            .padding(horizontal = 12.dp)
            .semantics {
                contentDescription =
                    if (item.locked) "${item.title}, bloqueado" else "${item.title}, concluído"
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier
                .size(35.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(square),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                item.icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = Color.White,
            )
        }
        Spacer(Modifier.width(9.dp))
        Text(
            item.title,
            modifier = Modifier.weight(1f),
            style = vs(16f, 500, 18.82f, 0f, Roboto),
            color = label,
            maxLines = 1,
        )
        if (item.done) {
            Box(
                Modifier
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(VPerfil.green),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    IcoAkarIconsCheck,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = Color.White,
                )
            }
        }
    }
}

@Composable
private fun SettingsButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier
            .height(61.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(VPerfil.configBg)
            .vClick(PressSoft, onClick = onClick)
            .semantics { contentDescription = PerfilMock.settings },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            IcoPencilSimpleFill,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.Black,
        )
        Spacer(Modifier.width(6.dp))
        Text(
            PerfilMock.settings,
            style = vs(16f, 400, 18.82f, 0f, Roboto),
            color = Color.Black,
            maxLines = 1,
        )
    }
}

@Composable
private fun FinanceButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier
            .height(61.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(VPerfil.configBg)
            .vClick(PressSoft, onClick = onClick)
            .semantics { contentDescription = "Financeiro" },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Filled.AccountBalanceWallet,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.Black,
        )
        Spacer(Modifier.width(6.dp))
        Text(
            "Financeiro",
            style = vs(16f, 400, 18.82f, 0f, Roboto),
            color = Color.Black,
            maxLines = 1,
        )
    }
}
