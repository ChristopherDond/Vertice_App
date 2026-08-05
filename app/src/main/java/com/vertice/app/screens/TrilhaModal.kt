package com.vertice.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.components.clickableNoRipple
import com.vertice.app.components.clickableRipple
import com.vertice.app.data.Lesson
import com.vertice.app.data.TModule
import com.vertice.app.data.TRILHA
import com.vertice.app.ui.theme.LocalColors

@Composable
fun TrilhaModal(onClose: () -> Unit, initialDone: Set<String>, onDoneChange: (Set<String>) -> Unit) {
    val C = LocalColors
    var done by rememberSaveable(initialDone) { mutableStateOf(initialDone) }
    // Salvamos só o id da lição ativa (serializável); resolvemos o objeto abaixo.
    var activeLessonId by rememberSaveable { mutableStateOf<String?>(null) }
    var cardIdx by rememberSaveable { mutableIntStateOf(0) }
    val total = TRILHA.sumOf { it.lessons.size }
    val activeLesson = activeLessonId?.let { id ->
        TRILHA.asSequence().map { it.lessons }.flatten().find { it.id == id }
    }

    fun markDone(id: String) {
        done = done + id
        onDoneChange(done)
    }

    activeLesson?.let { lesson ->
        val mod = TRILHA.first { m -> m.lessons.any { it.id == lesson.id } }
        LessonView(
            lesson = lesson,
            moduleColor = mod.color,
            cardIdx = cardIdx,
            isDone = done.contains(lesson.id),
            onBack = { activeLessonId = null },
            onPrev = { cardIdx-- },
            onNext = { cardIdx++ },
            onComplete = { markDone(lesson.id); activeLessonId = null },
        )
        return
    }

    Column(modifier = Modifier.fillMaxSize().background(C.navy)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(top = 50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(C.card, RoundedCornerShape(13.dp))
                    .border(1.dp, C.border, RoundedCornerShape(13.dp))
                    .clickableNoRipple(onClose),
                contentAlignment = Alignment.Center,
            ) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = C.white, modifier = Modifier.size(18.dp)) }

            Column(modifier = Modifier.weight(1f)) {
                Text("Trilha de Blindagem", color = C.white, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                Text("${done.size} de $total lições concluídas", color = C.muted, fontSize = 12.sp)
            }

            Box(
                modifier = Modifier
                    .background(C.purple.copy(alpha = 0.20f), RoundedCornerShape(12.dp))
                    .border(1.dp, C.purple.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
            ) { Text("${done.size * 100 / total}%", color = C.purpleL, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp) }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(top = 14.dp)
                .background(Color(0xFFF59E0B).copy(alpha = 0.13f), RoundedCornerShape(14.dp))
                .border(1.dp, C.amber.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
                .padding(horizontal = 16.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Icon(Icons.Filled.LocalFireDepartment, null, tint = C.amber, modifier = Modifier.size(20.dp))
            Text("Sequência de 3 dias estudando", color = C.white, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }

        // Barra de progresso estilo Duolingo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(top = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(10.dp)
                    .background(C.card2, RoundedCornerShape(50)),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(if (total == 0) 0f else done.size.toFloat() / total)
                        .fillMaxHeight()
                        .background(Brush.horizontalGradient(listOf(C.purple, C.pink)), RoundedCornerShape(50)),
                )
            }
            Text("${done.size * 100 / total}%", color = C.purpleL, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp)
                .padding(top = 20.dp)
                .padding(bottom = 100.dp),
        ) {
            TRILHA.forEach { mod ->
                ModuleBlock(
                    mod = mod,
                    done = done,
                    onOpenLesson = { l ->
                        if (!(l.locked && !done.contains(l.id))) {
                            activeLessonId = l.id
                            cardIdx = 0
                        }
                    },
                )
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun ModuleBlock(mod: TModule, done: Set<String>, onOpenLesson: (Lesson) -> Unit) {
    val C = LocalColors
    val modDone = mod.lessons.all { done.contains(it.id) }
    val modLocked = mod.lessons.all { it.locked } && mod.lessons.none { it.current }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (modLocked) C.card else mod.color.copy(alpha = 0.13f), RoundedCornerShape(16.dp))
            .border(1.dp, if (modLocked) C.border else mod.color.copy(alpha = 0.25f), RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(if (modLocked) C.card2 else mod.color.copy(alpha = 0.18f), RoundedCornerShape(12.dp))
                .border(1.dp, if (modLocked) C.border else mod.color.copy(alpha = 0.25f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center,
        ) {
            if (modLocked) Icon(Icons.Filled.Lock, null, tint = C.muted, modifier = Modifier.size(18.dp))
            else Icon(mod.icon, null, tint = mod.color, modifier = Modifier.size(18.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(mod.title, color = if (modLocked) C.muted else C.white, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            val doneCount = mod.lessons.count { done.contains(it.id) }
            Text(
                if (modDone) "Concluído ✓" else if (modLocked) "Bloqueado" else "$doneCount/${mod.lessons.size} lições",
                color = C.muted, fontSize = 12.sp,
            )
        }
        if (modDone) Icon(Icons.Filled.EmojiEvents, null, tint = C.amber, modifier = Modifier.size(18.dp))
    }

    Spacer(Modifier.height(18.dp))

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        mod.lessons.forEachIndexed { li, l ->
            val isDone = done.contains(l.id)
            val isCurrent = !isDone && !l.locked && (l.current || (li == 0 && !mod.lessons[0].locked))
            val isLocked = l.locked && !isDone
            val clickable = isDone || isCurrent
            val nodeColor = if (isDone) C.green else if (isCurrent) mod.color else C.card2

            if (li > 0) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(28.dp)
                        .background(if (isDone) C.green.copy(alpha = 0.4f) else C.border),
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 300.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (clickable) nodeColor.copy(alpha = 0.13f) else C.card)
                    .border(2.dp, if (isDone) C.green else if (isCurrent) mod.color else C.border, RoundedCornerShape(16.dp))
                    .clickableRipple { if (!isLocked) onOpenLesson(l) }
                    .padding(horizontal = 16.dp, vertical = 13.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(nodeColor.copy(alpha = if (isDone) 0.18f else if (isCurrent) 0.15f else 0.08f), CircleShape)
                        .border(2.dp, if (isDone) C.green else if (isCurrent) mod.color else C.border, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    when {
                        isDone -> Icon(Icons.Filled.CheckCircle, null, tint = C.green, modifier = Modifier.size(22.dp))
                        isLocked -> Icon(Icons.Filled.Lock, null, tint = C.muted, modifier = Modifier.size(18.dp))
                        else -> Text(l.emoji, fontSize = 20.sp)
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(l.title, color = if (isLocked) C.muted else C.white, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text(
                        if (isDone) "Concluída" else if (isCurrent) "Disponível agora" else "Bloqueada",
                        color = if (isDone) C.green else if (isCurrent) mod.color else C.muted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                if (clickable) Icon(Icons.Filled.ChevronRight, null, tint = if (isDone) C.green else mod.color, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun LessonView(
    lesson: Lesson,
    moduleColor: Color,
    cardIdx: Int,
    isDone: Boolean,
    onBack: () -> Unit,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onComplete: () -> Unit,
) {
    val C = LocalColors
    val card = lesson.cards[cardIdx]
    val isLast = cardIdx == lesson.cards.size - 1

    Column(modifier = Modifier.fillMaxSize().background(C.navy)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(top = 50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(C.card, RoundedCornerShape(13.dp))
                    .border(1.dp, C.border, RoundedCornerShape(13.dp))
                    .clickableNoRipple(onBack),
                contentAlignment = Alignment.Center,
            ) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = C.white, modifier = Modifier.size(18.dp)) }

            Text(lesson.title, color = C.white, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, modifier = Modifier.weight(1f))

            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                lesson.cards.indices.forEach { i ->
                    Box(
                        modifier = Modifier
                            .width(if (i == cardIdx) 18.dp else 7.dp)
                            .height(7.dp)
                            .background(if (i <= cardIdx) moduleColor else C.card2, RoundedCornerShape(50)),
                    )
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(moduleColor.copy(alpha = 0.13f), RoundedCornerShape(24.dp))
                    .border(2.dp, moduleColor.copy(alpha = 0.19f), RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center,
            ) { Text(lesson.emoji, fontSize = 38.sp) }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp)
                .padding(top = 20.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(C.card, RoundedCornerShape(20.dp))
                    .border(1.dp, C.border, RoundedCornerShape(20.dp))
                    .padding(22.dp),
            ) {
                Text(card.heading, color = C.white, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                Spacer(Modifier.height(12.dp))
                Text(card.body, color = C.mutedL, fontSize = 14.sp, lineHeight = 23.8.sp)
                card.tip?.let { tip ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .background(moduleColor.copy(alpha = 0.08f), RoundedCornerShape(13.dp))
                            .border(1.dp, moduleColor.copy(alpha = 0.15f), RoundedCornerShape(13.dp))
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Text("\uD83D\uDCA1", fontSize = 16.sp)
                        Text(tip, color = C.mutedL, fontSize = 13.sp, lineHeight = 20.15.sp, modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(C.navy)
                .padding(horizontal = 22.dp, vertical = 16.dp)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (cardIdx > 0) {
                Box(
                    modifier = Modifier
                        .size(width = 48.dp, height = 52.dp)
                        .background(C.card, RoundedCornerShape(14.dp))
                        .border(1.dp, C.border, RoundedCornerShape(14.dp))
                        .clickableNoRipple(onPrev),
                    contentAlignment = Alignment.Center,
                ) { Icon(Icons.Filled.ChevronLeft, null, tint = C.mutedL, modifier = Modifier.size(20.dp)) }
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (isLast) (if (isDone) C.green else moduleColor) else C.purple)
                    .clickableRipple { if (isLast) onComplete() else onNext() }
                    .padding(vertical = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (isLast) {
                    Icon(if (isDone) Icons.Filled.CheckCircle else Icons.Filled.EmojiEvents, null, tint = Color.White, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(if (isDone) "Concluída" else "Concluir lição", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                } else {
                    Text("Próximo", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.Filled.ChevronRight, null, tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

