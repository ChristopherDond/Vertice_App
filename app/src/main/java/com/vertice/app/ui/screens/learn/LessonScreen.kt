package com.vertice.app.ui.screens.learn

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vertice.app.domain.model.Lesson
import com.vertice.app.ui.components.FadeSlideIn
import com.vertice.app.ui.components.PrimaryButton
import com.vertice.app.ui.components.TopBar
import com.vertice.app.ui.theme.LocalVerticeColors

@Composable
fun LessonScreen(lesson: Lesson, onBack: () -> Unit, onComplete: () -> Unit = {}) {
    val colors = LocalVerticeColors.current
    var index by remember { mutableIntStateOf(0) }
    val card = lesson.cards.getOrNull(index)

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TopBar(title = lesson.title, onBack = onBack)
        Column(Modifier.fillMaxSize().padding(horizontal = 22.dp)) {
            FadeSlideIn(0) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(lesson.emoji, fontSize = 28.sp)
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(lesson.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        Text(
                            "Card ${index + 1} de ${lesson.cards.size}",
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.textSecondary,
                        )
                    }
                }
            }
            Spacer(Modifier.height(18.dp))
            AnimatedVisibility(
                visible = card != null,
                enter = fadeIn(tween(260)) + slideInHorizontally(tween(260)) { it / 6 },
            ) {
                Surface(
                    Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 2.dp,
                ) {
                    Column(Modifier.padding(22.dp).verticalScroll(rememberScrollState())) {
                        Text(card?.heading ?: "", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(Modifier.height(12.dp))
                        Text(card?.body ?: "", style = MaterialTheme.typography.bodyMedium, color = colors.textSecondary)
                        val tip = card?.tip
                        if (tip != null) {
                            Spacer(Modifier.height(18.dp))
                            Surface(shape = RoundedCornerShape(14.dp), color = colors.gradientStart.copy(alpha = 0.1f)) {
                                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Text("💡", fontSize = 18.sp)
                                    Spacer(Modifier.width(10.dp))
                                    Text(tip, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                                }
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                if (index > 0) {
                    Box(Modifier.weight(1f)) {
                        com.vertice.app.ui.components.SecondaryButton("Anterior", Modifier.fillMaxWidth()) { index-- }
                    }
                }
                Box(Modifier.weight(1f)) {
                    PrimaryButton(
                        if (index == lesson.cards.lastIndex) "Concluir" else "Próximo",
                        Modifier.fillMaxWidth(),
                    ) {
                        if (index == lesson.cards.lastIndex) { onComplete(); onBack() } else index++
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
