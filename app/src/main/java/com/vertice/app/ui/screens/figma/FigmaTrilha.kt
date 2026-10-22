package com.vertice.app.ui.screens.figma

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vertice.app.core.di.ServiceLocator
import com.vertice.app.core.result.VerticeResult
import com.vertice.app.domain.model.Lesson
import com.vertice.app.domain.model.TModule
import com.vertice.app.ui.components.PressAccent
import com.vertice.app.ui.components.PressSoft
import com.vertice.app.ui.components.vClick
import com.vertice.app.ui.design.Roboto
import com.vertice.app.ui.design.VColor
import com.vertice.app.ui.design.VGrad
import com.vertice.app.ui.design.VText
import com.vertice.app.ui.design.vs
import com.vertice.app.ui.icons.IcoArrowRightBold
import com.vertice.app.ui.icons.IcoFileTextFill
import com.vertice.app.ui.icons.IcoBoxiconsShieldFilled

private val TrChipBg = Color(0x269657F3)
private val TrChipLine = Color(0x4D904EF2)
private val TrDoneHeaderBg = Color(0xFFDDEEDD)
private val TrDoneItemBg = Color(0xFFD3E8D3)
private val TrDoneLine = VColor.Green5
private val TrDoneInk = VColor.Green
private val TrPendingHeaderBg = VColor.PurpleLine
private val TrPendingItemBg = VColor.PurpleWash
private val TrPendingLine = VColor.PurpleSoft
private val TrStreakBg = Color(0xFFFFE3C7)
private val TrStreakLine = Color(0xFFF5B67E)
private val TrConnector = VColor.Gray3

// O conteúdo da trilha é estático: guardar evita o "Carregando…" a cada volta da lição.
private var modulesCache: List<TModule>? = null

/** Carrega módulos e progresso reais (Room + repositório) e entrega para [FigmaTrilha]. */
@Composable
fun FigmaTrilhaRoute(onBack: () -> Unit, onOpenLesson: (Lesson) -> Unit) {
    val repo = ServiceLocator.learningRepository
    val progressFlow = remember { repo.progress() }
    val streakFlow = remember { repo.studyStreakDays() }
    val done by progressFlow.collectAsState(initial = emptySet())
    val streak by streakFlow.collectAsState(initial = 0)

    var modules by remember { mutableStateOf(modulesCache) }
    var error by remember { mutableStateOf<String?>(null) }
    var attempt by remember { mutableIntStateOf(0) }

    LaunchedEffect(attempt) {
        error = null
        when (val result = repo.modules()) {
            is VerticeResult.Ok -> {
                modulesCache = result.value
                modules = result.value
            }
            is VerticeResult.Err -> error = result.message
        }
    }

    val loaded = modules
    when {
        loaded != null -> FigmaTrilha(loaded, done, streak, onBack, onOpenLesson)
        else -> TrilhaStatus(
            message = error ?: "Carregando sua trilha…",
            retry = if (error != null) ({ attempt += 1 }) else null,
            onBack = onBack,
        )
    }
}

@Composable
fun FigmaTrilha(
    modules: List<TModule>,
    done: Set<String>,
    streakDays: Int,
    onBack: () -> Unit,
    onOpenLesson: (Lesson) -> Unit,
) {
    BackHandler(enabled = true) { onBack() }

    val totalLessons = modules.sumOf { it.lessons.size }
    val doneLessons = modules.sumOf { m -> m.lessons.count { it.id in done } }
    val doneModules = modules.count { m -> m.lessons.isNotEmpty() && m.lessons.all { it.id in done } }
    val percent = if (totalLessons == 0) 0 else doneLessons * 100 / totalLessons
    val collapsed = remember { mutableStateMapOf<String, Boolean>() }

    Column(
        Modifier
            .fillMaxSize()
            .background(VColor.Bg)
            .statusBarsPadding(),
    ) {
        TrilhaHeader(
            subtitle = "$doneModules de ${modules.size} módulos concluídos",
            percent = percent,
            onBack = onBack,
        )
        LazyColumn(
            Modifier.fillMaxSize().navigationBarsPadding(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 32.dp),
        ) {
            item(key = "streak") { StreakBanner(streakDays) }
            item(key = "progress") { TrilhaProgressBar(percent, Modifier.padding(top = 20.dp)) }
            items(modules, key = { it.id }) { module ->
                ModuleBlock(
                    module = module,
                    done = done,
                    open = collapsed[module.id] != true,
                    onToggle = { collapsed[module.id] = collapsed[module.id] != true },
                    onOpenLesson = onOpenLesson,
                )
            }
        }
    }
}

@Composable
private fun TrilhaStatus(message: String, retry: (() -> Unit)?, onBack: () -> Unit) {
    BackHandler(enabled = true) { onBack() }
    Column(
        Modifier.fillMaxSize().background(VColor.Bg).statusBarsPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        VText(message, vs(16f, 400, 20f, family = Roboto), VColor.Ink, align = TextAlign.Center)
        if (retry != null) {
            Spacer(Modifier.height(16.dp))
            Box(
                Modifier
                    .height(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(TrChipBg)
                    .border(0.75.dp, TrChipLine, RoundedCornerShape(12.dp))
                    .vClick(PressAccent) { retry() }
                    .padding(horizontal = 20.dp)
                    .semantics { contentDescription = "Tentar de novo" },
                contentAlignment = Alignment.Center,
            ) {
                VText("Tentar de novo", vs(16f, 500, 18.82f, family = Roboto), VColor.Purple)
            }
        }
    }
}

@Composable
private fun TrilhaHeader(subtitle: String, percent: Int, onBack: () -> Unit) {
    val shape = RoundedCornerShape(12.dp)
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier
                .size(48.dp)
                .shadow(3.dp, shape)
                .background(Color.White, shape)
                .vClick(PressAccent) { onBack() }
                .semantics { contentDescription = "Voltar" },
            contentAlignment = Alignment.Center,
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, Modifier.size(26.dp), tint = VColor.Ink)
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            VText("Trilha de blindagem", vs(17f, 700, 20f, family = Roboto), VColor.Ink, maxLines = 1)
            VText(subtitle, vs(12f, 400, 14.06f, family = Roboto), VColor.Ink, maxLines = 1)
        }
        Spacer(Modifier.width(12.dp))
        Box(
            Modifier
                .height(36.dp)
                .width(54.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(TrChipBg)
                .border(0.75.dp, TrChipLine, RoundedCornerShape(10.dp))
                .semantics { contentDescription = "$percent por cento concluído" },
            contentAlignment = Alignment.Center,
        ) {
            VText("$percent%", vs(17f, 700, 20f, family = Roboto), VColor.Purple, maxLines = 1)
        }
    }
}

@Composable
private fun StreakBanner(streakDays: Int) {
    val shape = RoundedCornerShape(12.dp)
    val text = when (streakDays) {
        0 -> "Conclua uma lição hoje para começar sua sequência"
        1 -> "1 dia consecutivo aprendendo"
        else -> "$streakDays dias consecutivos aprendendo"
    }
    Row(
        Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(shape)
            .background(TrStreakBg)
            .border(1.dp, TrStreakLine, shape)
            .padding(horizontal = 14.dp)
            .semantics { contentDescription = text },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(Icons.Filled.LocalFireDepartment, null, Modifier.size(28.dp), tint = VColor.Orange2)
        Spacer(Modifier.width(10.dp))
        VText(text, vs(16f, 400, 18.82f, family = Roboto), VColor.Ink, maxLines = 2)
    }
}

@Composable
private fun TrilhaProgressBar(percent: Int, modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxWidth()
            .height(9.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(VColor.Gray4),
    ) {
        if (percent > 0) {
            Box(
                Modifier
                    .fillMaxWidth(percent / 100f)
                    .height(9.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(VGrad.Wordmark),
            )
        }
    }
}

@Composable
private fun ModuleBlock(
    module: TModule,
    done: Set<String>,
    open: Boolean,
    onToggle: () -> Unit,
    onOpenLesson: (Lesson) -> Unit,
) {
    val total = module.lessons.size
    val doneCount = module.lessons.count { it.id in done }
    val complete = total > 0 && doneCount == total
    val shape = RoundedCornerShape(12.dp)
    val chevron by animateFloatAsState(if (open) 0f else 180f, tween(200), label = "trChevron")

    Column(Modifier.fillMaxWidth().padding(top = 22.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(shape)
                .background(if (complete) TrDoneHeaderBg else TrPendingHeaderBg)
                .border(1.dp, if (complete) TrDoneLine.copy(alpha = 0.4f) else TrChipLine, shape)
                .vClick(PressSoft) { onToggle() }
                .padding(horizontal = 12.dp)
                .semantics {
                    contentDescription = "${module.title}, $doneCount de $total lições concluídas, " +
                        if (open) "expandido" else "recolhido"
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                moduleIcon(module.id),
                null,
                Modifier.size(28.dp),
                tint = if (complete) TrDoneInk else VColor.PurpleSoft,
            )
            Spacer(Modifier.width(10.dp))
            VText(
                module.title,
                vs(18f, 500, 21.17f, family = Roboto),
                VColor.Ink,
                Modifier.weight(1f),
                maxLines = 1,
            )
            if (complete) {
                Icon(Icons.Filled.EmojiEvents, null, Modifier.size(28.dp), tint = VColor.Orange)
            } else {
                ProgressRing(doneCount, total)
            }
        }

        Box(
            Modifier
                .align(Alignment.CenterHorizontally)
                .size(36.dp)
                .clip(CircleShape)
                .vClick(PressAccent) { onToggle() }
                .semantics { contentDescription = if (open) "Recolher ${module.title}" else "Expandir ${module.title}" },
            contentAlignment = Alignment.Center,
        ) {
            Icon(Icons.Filled.KeyboardArrowDown, null, Modifier.size(24.dp).rotate(chevron), tint = VColor.Ink)
        }

        AnimatedVisibility(
            visible = open,
            enter = expandVertically(tween(220)) + fadeIn(tween(220)),
            exit = shrinkVertically(tween(200)) + fadeOut(tween(160)),
        ) {
            Column(
                Modifier.fillMaxWidth().padding(horizontal = 24.dp).animateContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                module.lessons.forEachIndexed { index, lesson ->
                    if (index > 0) Box(Modifier.width(1.dp).height(12.dp).background(TrConnector))
                    LessonCard(lesson, lesson.id in done) { onOpenLesson(lesson) }
                }
            }
        }
    }
}

@Composable
private fun LessonCard(lesson: Lesson, done: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(10.dp)
    val bg = if (done) TrDoneItemBg else TrPendingItemBg
    val line = if (done) TrDoneLine else TrPendingLine
    val ink = if (done) TrDoneInk else VColor.Purple
    Row(
        Modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(shape)
            .background(bg)
            .border(1.dp, line, shape)
            .vClick(PressSoft) { onClick() }
            .padding(horizontal = 10.dp)
            .semantics {
                contentDescription = "${lesson.title}, " + if (done) "concluída" else "pendente"
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (done) {
            Box(
                Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.6f))
                    .border(1.dp, line, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Filled.Check, null, Modifier.size(18.dp), tint = ink)
            }
        } else {
            Box(
                Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(VColor.PurpleSoft),
                contentAlignment = Alignment.Center,
            ) {
                VText("!", vs(16f, 700, 18.82f, family = Roboto), Color.White)
            }
        }
        Spacer(Modifier.width(10.dp))
        VText(lesson.title, vs(15f, 500, 17.64f, family = Roboto), VColor.Ink, Modifier.weight(1f), maxLines = 1)
        Icon(IcoArrowRightBold, null, Modifier.size(22.dp), tint = ink)
    }
}

@Composable
private fun ProgressRing(done: Int, total: Int) {
    val fraction = if (total == 0) 0f else done.toFloat() / total
    Canvas(Modifier.size(28.dp)) {
        val stroke = 3.dp.toPx()
        val inset = stroke / 2
        val arcSize = Size(size.width - stroke, size.height - stroke)
        drawArc(
            color = TrChipLine,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(inset, inset),
            size = arcSize,
            style = Stroke(stroke),
        )
        if (fraction > 0f) {
            drawArc(
                color = VColor.PurpleSoft,
                startAngle = -90f,
                sweepAngle = 360f * fraction,
                useCenter = false,
                topLeft = Offset(inset, inset),
                size = arcSize,
                style = Stroke(stroke, cap = StrokeCap.Round),
            )
        }
    }
}

private fun moduleIcon(moduleId: String): ImageVector = when (moduleId) {
    "m1" -> IcoBoxiconsShieldFilled
    "m2" -> IcoFileTextFill
    "m3" -> Icons.Filled.EditNote
    "m4" -> Icons.Filled.Groups
    "m5" -> Icons.Filled.Savings
    else -> Icons.Filled.School
}
