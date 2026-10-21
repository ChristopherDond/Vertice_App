package com.vertice.app.ui.screens.learn

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.core.di.ServiceLocator
import com.vertice.app.domain.model.Lesson
import com.vertice.app.ui.components.FadeSlideIn
import com.vertice.app.ui.components.TopBar
import com.vertice.app.ui.theme.LocalVerticeColors
import com.vertice.app.core.result.onOk

@Composable
private fun LessonRow(lesson: Lesson, index: Int, onOpen: () -> Unit) {
    val colors = LocalVerticeColors.current
    val completed = index < 3
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(enabled = !lesson.locked) { onOpen() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(
                    when {
                        completed -> colors.success
                        lesson.locked -> colors.textSecondary.copy(alpha = 0.25f)
                        else -> colors.gradientStart
                    },
                ),
            contentAlignment = Alignment.Center,
        ) {
            when {
                completed -> Icon(Icons.Filled.Check, null, tint = Color.White, modifier = Modifier.size(21.dp))
                lesson.locked -> Icon(Icons.Filled.Lock, null, tint = Color.White, modifier = Modifier.size(18.dp))
                else -> Text(lesson.emoji, fontSize = 20.sp)
            }
        }
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(
                "${index + 1}. ${lesson.title}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = if (lesson.locked) colors.textSecondary else MaterialTheme.colorScheme.onSurface,
            )
            Text(
                when {
                    completed -> "Concluída"
                    lesson.locked -> "Bloqueada · conclua a anterior"
                    else -> "Disponível agora"
                },
                style = MaterialTheme.typography.labelSmall,
                color = colors.textSecondary,
            )
        }
        Text("›", style = MaterialTheme.typography.titleLarge, color = colors.textSecondary)
    }
}

@Composable
fun TrilhaScreen(onBack: () -> Unit, onOpenLesson: (Lesson) -> Unit) {
    val colors = LocalVerticeColors.current
    var modules by remember { mutableStateOf<List<com.vertice.app.domain.model.TModule>>(emptyList()) }
    var expanded by remember { mutableStateOf<Int?>(0) }

    LaunchedEffect(Unit) {
        ServiceLocator.learningRepository.modules().onOk { modules = it }
    }

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TopBar(title = "Trilha de Blindagem", onBack = onBack)
        LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(horizontal = 22.dp, vertical = 8.dp)) {
            item {
                FadeSlideIn(0) {
                    Surface(shape = RoundedCornerShape(22.dp), color = Color.Transparent) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .background(Brush.horizontalGradient(listOf(colors.gradientStart, colors.gradientEnd)))
                                .padding(20.dp),
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Filled.School, null, tint = Color.White, modifier = Modifier.size(24.dp))
                                    Spacer(Modifier.width(10.dp))
                                    Text("Seu progresso", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                                Spacer(Modifier.height(14.dp))
                                LinearProgressIndicator(
                                    progress = { 0.25f },
                                    modifier = Modifier.fillMaxWidth().height(9.dp).clip(RoundedCornerShape(5.dp)),
                                    color = Color.White,
                                    trackColor = Color.White.copy(alpha = 0.28f),
                                )
                                Spacer(Modifier.height(10.dp))
                                Text("3 de 12 lições concluídas · 25%", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.9f))
                            }
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(20.dp)) }
            modules.forEachIndexed { mi, module ->
                item {
                    FadeSlideIn(mi * 70) {
                        Surface(
                            Modifier.fillMaxWidth().padding(bottom = 12.dp),
                            shape = RoundedCornerShape(18.dp),
                            color = MaterialTheme.colorScheme.surface,
                            tonalElevation = 2.dp,
                        ) {
                            Column(Modifier.padding(16.dp).animateContentSize()) {
                                Row(
                                    Modifier.fillMaxWidth().clickable { expanded = if (expanded == mi) null else mi },
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Column(Modifier.weight(1f)) {
                                        Text(module.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                        Text("${module.lessons.size} lições", style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
                                    }
                                    Text(if (expanded == mi) "⌃" else "⌄", style = MaterialTheme.typography.titleLarge, color = colors.textSecondary)
                                }
                                if (expanded == mi) {
                                    Spacer(Modifier.height(6.dp))
                                    module.lessons.forEachIndexed { li, lesson ->
                                        LessonRow(lesson, mi * 3 + li) { onOpenLesson(lesson) }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
