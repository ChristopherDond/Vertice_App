package com.vertice.app.ui.design

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object VColor {
    val FieldLine = Color(0xFF7F6D9C)
    val SectionLine = Color(0x809657F3)
    val InputLine = Color(0x4D883FF4)
    val Purple = Color(0xFF883FF4)
    val PurpleMid = Color(0xFF904EF2)
    val PurpleSoft = Color(0xFF9657F3)
    val PurpleDeep = Color(0xFF4F258E)
    val PurpleDarkest = Color(0xFF2E1065)
    val PurpleInk = Color(0xFF4D3274)
    val PurpleMuted = Color(0xFF7F6D9C)
    val PurpleMuted2 = Color(0xFF868686)
    val AppleDark = Color(0xFF283544)
    val PurpleLine = Color(0xFFEADDFE)
    val PurpleLineSoft = Color(0xFFE9D7FF)
    val PurpleWash = Color(0xFFE8D9FF)
    val PurpleLilac = Color(0xFFD5BAFC)
    val PurpleLilac2 = Color(0xFFCAA6FF)
    val PurpleTint = Color(0xFFF1E9FD)
    val Bg = Color(0xFFF7F2FF)
    val BgAlt = Color(0xFFFAF6FF)
    val BgChip = Color(0xFFF3ECFF)
    val Ink = Color(0xFF000000)
    val InkSoft = Color(0xFF1B1B1B)
    val Ink2 = Color(0xFF1E1E1E)
    val Gray = Color(0xFF9D9D9D)
    val Gray2 = Color(0xFF929292)
    val Gray3 = Color(0xFFBFBDBD)
    val Gray4 = Color(0xFFD9D9D9)
    val Gray5 = Color(0xFFEDEDED)
    val Gray6 = Color(0xFFB3B3B3)
    val Warm = Color(0xFF4B4040)
    val Warm2 = Color(0xFFB8B1AA)
    val White = Color(0xFFFFFFFF)
    val WhitePaper = Color(0xFFFFFDFD)
    val WhiteWarm = Color(0xFFFFF9F9)
    val Green = Color(0xFF1F7729)
    val Green2 = Color(0xFF118706)
    val Green3 = Color(0xFF37C724)
    val Green4 = Color(0xFF13AA25)
    val Green5 = Color(0xFF219417)
    val Green6 = Color(0xFF28D206)
    val Green7 = Color(0xFF21C001)
    val GreenBg = Color(0xFFE8F5E9)
    val GreenBg2 = Color(0xFFE8FFE5)
    val Pink = Color(0xFFEC4899)
    val PinkSoft = Color(0xFFFFC4E1)
    val Gold = Color(0xFFEBB400)
    val Gold2 = Color(0xFFFFDB58)
    val GoldBg = Color(0xFFFFF5AC)
    val Orange = Color(0xFFFAA52F)
    val Orange2 = Color(0xFFFF6A00)
    val Orange3 = Color(0xFFCD7924)
    val Red = Color(0xFFD96A6A)
    val Red2 = Color(0xFFFF0000)
    val Blue = Color(0xFF4285F4)
    val BlueDeep = Color(0xFF283544)
    val GoogleGreen = Color(0xFF34A853)
    val GoogleYellow = Color(0xFFFBBC05)
    val GoogleRed = Color(0xFFEB4335)
    val IosGreen = Color(0xFF34C759)
    val IosSeparator = Color(0xFF3C3C43)
    val Pink2 = Color(0xFFF0529C)
    val Pink3 = Color(0xFFFF5EB5)
    val Ink3 = Color(0xFF4D3274)
    val MatchBadge = Color(0xFF7B2FF7)
}

object VGrad {
    val Pix = Brush.linearGradient(listOf(Color(0xFFEA58A3), Color(0xFFE275AE)))
    val CardDeep = Brush.verticalGradient(listOf(Color(0xFFA978F2), Color(0xFF4F258E)))
    val Card = Brush.verticalGradient(listOf(Color(0xFF883FF4), Color(0xFF4F258E)))
    val Wordmark = Brush.horizontalGradient(listOf(Color(0xFF641BE2), Color(0xFF9B13AC), Color(0xFFD20B76)))
    val Hero = Brush.linearGradient(listOf(Color(0xFF883FF4), Color(0xFF9B13AC), Color(0xFFFF5EB5)))
    val CardPink = Brush.linearGradient(
        colorStops = arrayOf(
            0.3125f to Color(0xFF904EF2),
            0.9188f to Color(0xFFEC4899),
        ),
        start = androidx.compose.ui.geometry.Offset(0.0318f, 0.108f),
        end = androidx.compose.ui.geometry.Offset(1f, 0.4448f),
        tileMode = androidx.compose.ui.graphics.TileMode.Clamp,
    )

    fun cardPink(size: androidx.compose.ui.geometry.Size) = Brush.linearGradient(
        colorStops = arrayOf(
            0.3125f to Color(0xFF904EF2),
            0.9188f to Color(0xFFEC4899),
        ),
        start = androidx.compose.ui.geometry.Offset(size.width * 0.0318f, size.height * 0.108f),
        end = androidx.compose.ui.geometry.Offset(size.width * 1f, size.height * 0.4448f),
        tileMode = androidx.compose.ui.graphics.TileMode.Clamp,
    )

    val Shield = Brush.verticalGradient(listOf(Color(0xFF300083), Color(0xFF641BE2), Color(0xFFFFFFFF)))
    val Fire = Brush.linearGradient(listOf(Color(0xFFEB4824), Color(0xFFFFCD0F)))
    val HeaderPix = Brush.linearGradient(listOf(Color(0xFF883FF4), Color(0xFFEC4899)))
    val Violet = Brush.verticalGradient(
        colorStops = arrayOf(
            0f to Color(0xFF883FF4),
            0.35f to Color(0xFF9B13AC),
            0.72f to Color(0xFFD2108A),
            1f to Color(0xFFEC4899),
        ),
    )
    val VioletOn = Brush.verticalGradient(
        colorStops = arrayOf(
            0f to Color(0xFF4C1795),
            0.45f to Color(0xFF7A21C9),
            1f to Color(0xFFA834D8),
        ),
    )
}

object VRadius {
    val xs = 4.dp
    val sm = 8.dp
    val md = 12.dp
    val lg = 16.dp
    val xl = 20.dp
    val xxl = 24.dp
    val pill = 100.dp
}

object VShadow {
    val card = VShadowSpec(0.dp, 4.dp, 4.dp, 0.dp, Color(0x40000000))
    val soft = VShadowSpec(0.dp, 2.dp, 6.dp, 0.dp, Color(0x1A000000))
}

data class VShadowSpec(
    val dx: androidx.compose.ui.unit.Dp,
    val dy: androidx.compose.ui.unit.Dp,
    val blur: androidx.compose.ui.unit.Dp,
    val spread: androidx.compose.ui.unit.Dp,
    val color: Color,
)

object VType {
    val display = 24.sp
    val title = 21.sp
    val heading = 20.sp
    val subhead = 18.sp
    val body = 16.sp
    val bodySm = 15.sp
    val label = 14.sp
    val caption = 13.sp
    val micro = 12.sp
    val tiny = 10.sp
    val nano = 8.sp
}
