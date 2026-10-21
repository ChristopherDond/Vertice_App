package com.vertice.app.ui.design

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.vertice.app.R

val Roboto = FontFamily(
    Font(R.font.roboto_200, FontWeight(200)),
    Font(R.font.roboto_300, FontWeight(300)),
    Font(R.font.roboto_400, FontWeight(400)),
    Font(R.font.roboto_500, FontWeight(500)),
    Font(R.font.roboto_600, FontWeight(600)),
    Font(R.font.roboto_700, FontWeight(700)),
    Font(R.font.roboto_800, FontWeight(800)),
)

val Inter = FontFamily(
    Font(R.font.inter_400, FontWeight(400)),
    Font(R.font.inter_600, FontWeight(600)),
    Font(R.font.inter_700, FontWeight(700)),
)

val Karma = FontFamily(Font(R.font.karma_500, FontWeight(500)))

private val lh = LineHeightStyle(LineHeightStyle.Alignment.Center, LineHeightStyle.Trim.None)

fun vs(
    size: Float,
    weight: Int = 400,
    lineHeight: Float = size * 1.17f,
    letterSpacing: Float = 0f,
    family: FontFamily = Roboto,
): TextStyle = TextStyle(
    fontFamily = family,
    fontSize = size.sp,
    fontWeight = FontWeight(weight),
    lineHeight = lineHeight.sp,
    lineHeightStyle = lh,
    letterSpacing = letterSpacing.sp,
)

object T {
    val greeting = vs(24f, 800, 28.23f)
    val screenTitle = vs(21f, 700, 24.61f)
    val h1 = vs(25f, 500, 27.5f)
    val h2 = vs(20f, 500, 23.52f)
    val h3 = vs(18f, 500, 19.8f)
    val money = vs(24f, 900, 27f)
    val cardName = vs(16f, 500, 18.82f)
    val body = vs(16f, 400, 18.75f)
    val body18 = vs(16f, 400, 18.82f)
    val bodySm = vs(15f, 400, 17.64f)
    val bodyMd = vs(14f, 400, 16.41f)
    val caption = vs(13f, 400, 15.23f)
    val micro = vs(12f, 400, 14.06f)
    val button = vs(14f, 800, 16.47f)
    val buttonBig = vs(16f, 800, 23.36f)
    val buttonHuge = vs(21f, 900, 31.5f)
    val section = vs(10f, 500, 11.76f, 1.2f)
    val secLabel = vs(10f, 800, 13f)
    val labelStrong = vs(12f, 600, 14.11f)
    val labelMuted = vs(12f, 500, 18f)
    val placeholder = vs(15f, 300, 22.5f)
    val tiny = vs(10f, 400, 15f)
    val nano = vs(8f, 400, 10.4f)
    val tab = vs(10f, 400, 15f)
    val formLabel = TextStyle(
        fontFamily = Inter, fontSize = 12.sp, fontWeight = FontWeight(600),
        lineHeight = 14.52.sp, letterSpacing = 0.sp, lineHeightStyle = lh,
    )
    val formPlaceholder = TextStyle(
        fontFamily = Inter, fontSize = 14.sp, fontWeight = FontWeight(400),
        lineHeight = 16.94.sp, letterSpacing = 0.sp, lineHeightStyle = lh,
    )
    val karma = TextStyle(
        fontFamily = Karma, fontSize = 16.sp, fontWeight = FontWeight(500),
        lineHeight = 23.89.sp, lineHeightStyle = lh,
    )
}
