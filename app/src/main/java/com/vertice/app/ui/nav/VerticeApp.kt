package com.vertice.app.ui.nav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.foundation.layout.padding
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.vertice.app.domain.model.Freelancer
import com.vertice.app.domain.model.Lesson
import com.vertice.app.domain.model.ServiceCategory
import com.vertice.app.nav.Routes
import com.vertice.app.ui.components.VerticeBottomBar
import com.vertice.app.ui.screens.auth.ForgotPasswordScreen
import com.vertice.app.ui.screens.auth.LoginScreen
import com.vertice.app.ui.screens.auth.RegisterScreen
import com.vertice.app.ui.screens.auth.SplashScreen
import com.vertice.app.ui.screens.finance.CardScreen
import com.vertice.app.ui.screens.finance.FinanceiroScreen
import com.vertice.app.ui.screens.finance.PayScreen
import com.vertice.app.ui.screens.finance.PixScreen
import com.vertice.app.ui.screens.finance.TransferScreen
import com.vertice.app.ui.screens.home.InicioScreen
import com.vertice.app.ui.screens.learn.LessonScreen
import com.vertice.app.ui.screens.learn.TrilhaScreen
import com.vertice.app.ui.screens.match.MatchScreen
import com.vertice.app.ui.screens.pro.ProConfirmScreen
import com.vertice.app.ui.screens.pro.ProScreen
import com.vertice.app.ui.screens.profile.AlterarEmailScreen
import com.vertice.app.ui.screens.profile.AlterarSenhaScreen
import com.vertice.app.ui.screens.profile.AlterarTelefoneScreen
import com.vertice.app.ui.screens.profile.ConfiguracoesScreen
import com.vertice.app.ui.screens.profile.PerfilScreen
import com.vertice.app.ui.screens.services.ContactProviderScreen
import com.vertice.app.ui.screens.services.FilterScreen
import com.vertice.app.ui.screens.services.OfferServiceScreen
import com.vertice.app.ui.screens.violeta.VioletaScreen
import com.vertice.app.ui.screens.violeta.VioletaVerifyScreen
import kotlinx.coroutines.launch

private val ENTER = tween<androidx.compose.ui.unit.IntOffset>(380)
private val EXIT = tween<androidx.compose.ui.unit.IntOffset>(300)

@Composable
fun VerticeApp() {
    val nav = rememberNavController()
    var loggedIn by remember { mutableStateOf(false) }
    var showSplash by remember { mutableStateOf(true) }
    var filterCategory by remember { mutableStateOf(ServiceCategory.ALL) }
    var filterKm by remember { mutableStateOf(50f) }
    var selectedFreelancer by remember { mutableStateOf<Freelancer?>(null) }
    var selectedLesson by remember { mutableStateOf<Lesson?>(null) }

    val onHome = {
        nav.navigate(Routes.INICIO) {
            popUpTo(Routes.INICIO) { inclusive = true }
            launchSingleTop = true
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
    ) { _ ->
        Box(Modifier.fillMaxSize()) {
            NavHost(
                navController = nav,
                startDestination = if (showSplash) Routes.SPLASH else if (loggedIn) Routes.INICIO else Routes.LOGIN,
                modifier = Modifier.fillMaxSize(),
                enterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, ENTER) + fadeIn(tween(380))
                },
                exitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, EXIT) + fadeOut(tween(300))
                },
                popEnterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, ENTER) + fadeIn(tween(380))
                },
                popExitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, EXIT) + fadeOut(tween(300))
                },
            ) {
                composable(Routes.SPLASH) {
                    SplashScreen(onReady = { logged ->
                        loggedIn = logged
                        showSplash = false
                        nav.navigate(if (logged) Routes.INICIO else Routes.LOGIN) {
                            popUpTo(Routes.SPLASH) { inclusive = true }
                        }
                    })
                }
                composable(Routes.LOGIN) {
                    LoginScreen(
                        onLoggedIn = { loggedIn = true; onHome() },
                        onRegister = { nav.navigate(Routes.REGISTER) },
                        onForgot = { nav.navigate(Routes.FORGOT) },
                    )
                }
                composable(Routes.REGISTER) {
                    RegisterScreen(onBack = { nav.popBackStack() }, onRegistered = { loggedIn = true; onHome() })
                }
                composable(Routes.FORGOT) {
                    ForgotPasswordScreen(onBack = { nav.popBackStack() }, onDone = { nav.popBackStack() })
                }

                composable(Routes.INICIO) {
                    MainTabs(nav) { padding ->
                        InicioScreen(
                            onOfferService = { nav.navigate(Routes.OFFER) },
                            onTrilha = { nav.navigate(Routes.TRILHA) },
                            onVioleta = { nav.navigate(Routes.VIOLETA) },
                            onPro = { nav.navigate(Routes.PRO) },
                            onMatch = { nav.select(Routes.MATCH) },
                        )
                    }
                }
                composable(Routes.MATCH) {
                    MainTabs(nav) { _ ->
                        MatchScreen(
                            onBack = { nav.select(Routes.INICIO) },
                            onOpenFilter = { nav.navigate(Routes.FILTER) },
                            onContact = { f -> selectedFreelancer = f; nav.navigate(Routes.CONTACT) },
                        )
                    }
                }
                composable(Routes.FINANCEIRO) {
                    MainTabs(nav) { _ ->
                        FinanceiroScreen(
                            onPix = { nav.navigate(Routes.PIX) },
                            onTransfer = { nav.navigate(Routes.TRANSFER) },
                            onPay = { nav.navigate(Routes.PAY) },
                            onCard = { nav.navigate(Routes.CARD) },
                        )
                    }
                }
                composable(Routes.PERFIL) {
                    MainTabs(nav) { _ ->
                        PerfilScreen(
                            onConfiguracoes = { nav.navigate(Routes.CONFIG) },
                            onPro = { nav.navigate(Routes.PRO) },
                            onTrilha = { nav.navigate(Routes.TRILHA) },
                            onVioleta = { nav.navigate(Routes.VIOLETA) },
                            onLogout = {
                                kotlinx.coroutines.MainScope().launch {
                                    com.vertice.app.core.di.ServiceLocator.authRepository.logout()
                                }
                                loggedIn = false
                                nav.navigate(Routes.LOGIN) {
                                    popUpTo(Routes.INICIO) { inclusive = true }
                                }
                            },
                        )
                    }
                }

                composable(Routes.TRILHA) {
                    TrilhaScreen(onBack = { nav.popBackStack() }, onOpenLesson = { l -> selectedLesson = l; nav.navigate(Routes.LESSON) })
                }
                composable(Routes.LESSON) {
                    val lesson = selectedLesson
                    if (lesson != null) LessonScreen(lesson, onBack = { nav.popBackStack() })
                    else Box(Modifier.fillMaxSize())
                }
                composable(Routes.VIOLETA) {
                    VioletaScreen(onBack = { nav.popBackStack() }, onVerify = { nav.navigate(Routes.VIOLETA_VERIFY) })
                }
                composable(Routes.VIOLETA_VERIFY) {
                    VioletaVerifyScreen(onBack = { nav.popBackStack() }, onFinish = { nav.popBackStack() })
                }
                composable(Routes.PRO) {
                    ProScreen(onBack = { nav.popBackStack() }, onConfirm = { nav.navigate(Routes.PRO_CONFIRM) })
                }
                composable(Routes.PRO_CONFIRM) {
                    ProConfirmScreen(onGoHome = { onHome() })
                }
                composable(Routes.OFFER) {
                    OfferServiceScreen(onBack = { nav.popBackStack() }, onPublished = { nav.popBackStack() })
                }
                composable(Routes.CONTACT) {
                    ContactProviderScreen(selectedFreelancer, onBack = { nav.popBackStack() }, onSend = { nav.popBackStack() })
                }
                composable(Routes.FILTER) {
                    FilterScreen(
                        selected = filterCategory,
                        maxKm = filterKm,
                        onApply = { cat, km ->
                            filterCategory = cat
                            filterKm = km
                            nav.popBackStack()
                        },
                        onBack = { nav.popBackStack() },
                    )
                }
                composable(Routes.CONFIG) {
                    ConfiguracoesScreen(
                        onBack = { nav.popBackStack() },
                        onAltEmail = { nav.navigate(Routes.ALT_EMAIL) },
                        onAltPhone = { nav.navigate(Routes.ALT_PHONE) },
                        onAltPassword = { nav.navigate(Routes.ALT_PASSWORD) },
                    )
                }
                composable(Routes.ALT_EMAIL) { AlterarEmailScreen(onBack = { nav.popBackStack() }) }
                composable(Routes.ALT_PHONE) { AlterarTelefoneScreen(onBack = { nav.popBackStack() }) }
                composable(Routes.ALT_PASSWORD) { AlterarSenhaScreen(onBack = { nav.popBackStack() }) }
                composable(Routes.PIX) { PixScreen(onBack = { nav.popBackStack() }, onDone = { nav.popBackStack() }) }
                composable(Routes.TRANSFER) { TransferScreen(onBack = { nav.popBackStack() }, onDone = { nav.popBackStack() }) }
                composable(Routes.PAY) { PayScreen(onBack = { nav.popBackStack() }, onDone = { nav.popBackStack() }) }
                composable(Routes.CARD) { CardScreen(onBack = { nav.popBackStack() }) }
            }
        }
    }
}

private fun NavHostController.select(route: String) {
    navigate(route) {
        popUpTo(com.vertice.app.nav.Routes.INICIO) { inclusive = false }
        launchSingleTop = true
    }
}

@Composable
private fun MainTabs(
    nav: NavHostController,
    content: @Composable (androidx.compose.foundation.layout.PaddingValues) -> Unit,
) {
    val backStackEntry by nav.currentBackStackEntryAsState()
    val current = backStackEntry?.destination?.route
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            VerticeBottomBar(
                currentRoute = current,
                onSelect = { tab -> nav.select(tab.route) },
            )
        },
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(bottom = padding.calculateBottomPadding())) {
            content(padding)
        }
    }
}
