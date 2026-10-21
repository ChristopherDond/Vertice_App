package com.vertice.app.ui.screens.figma



import androidx.compose.animation.AnimatedVisibility

import androidx.compose.animation.core.FastOutSlowInEasing

import androidx.compose.animation.core.animateFloatAsState

import androidx.compose.animation.core.tween

import androidx.compose.animation.fadeIn

import androidx.compose.animation.slideInVertically

import androidx.compose.foundation.Image

import androidx.compose.foundation.background

import androidx.compose.foundation.border

import androidx.compose.foundation.clickable

import androidx.compose.foundation.horizontalScroll

import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.offset

import androidx.compose.foundation.layout.size

import androidx.compose.foundation.layout.width

import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.shape.CircleShape

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.foundation.verticalScroll

import androidx.compose.foundation.layout.padding

import androidx.compose.ui.zIndex

import androidx.compose.material3.Icon

import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableIntStateOf

import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember

import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.alpha

import androidx.compose.ui.graphics.graphicsLayer

import androidx.compose.ui.draw.clip

import androidx.compose.ui.draw.shadow

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle

import androidx.compose.ui.text.buildAnnotatedString

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.sp

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.text.style.LineHeightStyle

import androidx.compose.ui.unit.dp

import com.vertice.app.R

import com.vertice.app.ui.design.Roboto

import com.vertice.app.ui.design.vs

import com.vertice.app.ui.icons.IcoBiChatLeftTextFill

import com.vertice.app.ui.icons.IcoAntDesignSearchOutlined

import com.vertice.app.ui.icons.IcoAntDesignStarFilled

import com.vertice.app.ui.components.vClick

import com.vertice.app.ui.components.PressWhite



data class ProMatch(

    val photo: Int,

    val name: String,

    val role: String,

    val match: Int,

    val tag: String,

    val desc: String,

    val rating: String,

    val services: Int,

    val status: String,

    val online: Boolean,

)



val pros = listOf(

    ProMatch(

        R.drawable.img_camila,

        "Camila Ferreira",

        "Consultora Financeira",

        93,

        "Consultoria",

        "Planejamento financeiro pessoal e para pequenos negócios. Consultoria tributária e organização de fluxo de caixa.",

        "5.0",

        38,

        "Disponível esta semana",

        true,

    ),

    ProMatch(

        R.drawable.img_carlos,

        "Carlos Mendes",

        "Pedreiro",

        94,

        "Construção Civil",

        "Reformas residenciais, instalações hidráulicas e acabamento. Atendimento em toda a região metropolitana.",

        "4.9",

        48,

        "Disponível agora",

        true,

    ),

    ProMatch(

        R.drawable.img_andre,

        "André Pereira",

        "Mestre de Obras",

        93,

        "Construção Civil",

        "Gestão completa de obra, cronograma físico-financeiro e coordenação de equipes.",

        "4.8",

        62,

        "Disponível esta semana",

        true,

    ),

    ProMatch(

        R.drawable.img_luisa,

        "Luísa Fonseca",

        "Lojista",

        87,

        "Comércio & Varejo",

        "Gestão de loja de moda feminina, vitrinismo e controle de estoque.",

        "4.7",

        31,

        "Disponível amanhã",

        false,

    ),

)



private val Purple = Color(0xFF904EF2)

private val Pink = Color(0xFFEC4899)

private val CardBg = Color(0xFFFFFCFC)

private val Ink = Color(0xFF4D3274)

private val SoftInk = Color(0xC24D3274)

private val Green = Color(0xFF1E7629)

private val BadgeBg = Color(0xFFE8D8FF)

private val TagBg = Color(0x59C9A5FF)

private val Sober = Color(0xFFECECEC)

private val Star = Color(0xFFEBB400)

private val Shadow = Color(0x2E000000)



private val tabs = listOf("Todos", "Comércio", "Serviços", "Beleza", "Alimentação")

private val tabX = listOf(31, 117, 220, 316, 402)

private val tabW = listOf(76, 93, 86, 76, 112)



@Composable

fun FigmaMatch(

    onNav: (VTab) -> Unit,

    onContratar: (ProMatch) -> Unit,

    onFiltros: () -> Unit,

    onVerPerfil: (ProMatch) -> Unit,

) {

    var tab by remember { mutableIntStateOf(0) }

    var entrance by remember { mutableIntStateOf(0) }

    val scroll = rememberScrollState()



    LaunchedEffect(tab) { entrance++ }



    Box(Modifier.fillMaxSize().background(Color(0xFFF6F1FF))) {

        Box(

            Modifier

                .fillMaxWidth()

                .verticalScroll(scroll),

            contentAlignment = Alignment.TopStart,

        ) {

          Box(Modifier.fillMaxWidth().height(1627.dp)) {

            Box(
                Modifier

                    .offset(31.dp, 161.dp)

                    .width(332.dp)

                    .height(41.dp)

                    .clip(RoundedCornerShape(10.dp))

                    .background(Color.White),

            ) {

                Icon(

                    IcoAntDesignSearchOutlined,

                    null,

                    Modifier.offset(15.dp, 12.dp).size(16.dp),

                    tint = Color(0xFF4C3274),

                )

                Text(

                    "Buscar habilidade ou área...",

                    modifier = Modifier.offset(44.dp, 11.dp).width(168.dp),

                    style = vs(13f, 400, 17f, 0f, Roboto),

                    color = Color(0x7A4D3274),

                    maxLines = 1,

                )

            }



            Box(Modifier.offset(0.dp, 222.dp).fillMaxWidth().height(33.dp).horizontalScroll(rememberScrollState())) {

                tabs.forEachIndexed { i, label ->

                    val on = tab == i

                    Box(

                        Modifier

                            .offset(tabX[i].dp, 0.dp)

                            .width(tabW[i].dp)

                            .height(33.dp)

                            .clip(RoundedCornerShape(15.dp))

                            .background(if (on) Purple else Color.White)

                            .vClick(scaleDown = 0.95f) { tab = i },

                        contentAlignment = Alignment.Center,

                    ) {

                        Text(label, style = vs(13f, 700, 17f, 0f, Roboto), color = if (on) Color.White else Ink, maxLines = 1)

                    }

                }

            }



            pros.forEachIndexed { i, p ->

                var shown by remember(entrance, i) { mutableStateOf(false) }

                LaunchedEffect(entrance, i) {

                    kotlinx.coroutines.delay(70L * i)

                    shown = true

                }

                val appear by animateFloatAsState(

                    targetValue = if (shown) 1f else 0f,

                    animationSpec = tween(420, easing = FastOutSlowInEasing),

                    label = "proAppear",

                )

                Box(

                    Modifier

                        .offset(0.dp, (322 + i * 306).dp)

                        .alpha(appear)

                        .graphicsLayer { translationY = (1f - appear) * 32f },

                ) {

                    ProCard(p, 0, onContact = { onContratar(p) }, onVerPerfil = { onVerPerfil(p) })

                }

            }

            Box(Modifier.offset(0.dp, (322 + (pros.size - 1) * 306 + 286).dp).fillMaxWidth().height(101.dp))

          }

        }



        Box(Modifier.fillMaxWidth().height(150.dp).background(Color(0xFFF6F1FF)).zIndex(3f)) {

            Text(

                "Match",

                modifier = Modifier.offset(31.dp, 71.dp).width(157.dp),

                style = vs(24f, 800, 28.13f, 0f, Roboto),

                color = Color.Black,

                maxLines = 1,

            )

            Text(

                "Encontre parceiros de negócio,\nprestadores ou apoio para sua gestão",

                modifier = Modifier.offset(31.dp, 105.dp).width(226.dp),

                style = vs(13f, 400, 18f, 0f, Roboto),

                color = Color(0x70000000),

                maxLines = 2,

            )

            androidx.compose.foundation.Image(

                androidx.compose.ui.res.painterResource(R.drawable.ic_v_filtro),

                contentDescription = "Filtros",

                modifier = Modifier

                    .offset(x = 329.01.dp, y = 110.01.dp)

                    .size(35.9822.dp)

                    .vClick(scaleDown = 0.92f) { onFiltros() },

            )

        }



        VBottomNav(VTab.MATCH, onNav, Modifier.align(Alignment.BottomCenter).zIndex(10f))

    }

}



@Composable

private fun ProCard(p: ProMatch, y: Int, onContact: () -> Unit, onVerPerfil: () -> Unit) {

    Box(

        Modifier

            .offset(30.dp, y.dp)

            .width(333.dp)

            .height(286.dp)

            .clip(RoundedCornerShape(15.dp))

            .background(CardBg),

    ) {

        Box(Modifier.offset(20.dp, 20.dp).size(54.dp).clip(CircleShape)) {

            Image(

                painterResource(p.photo),

                contentDescription = null,

                modifier = Modifier.fillMaxSize(),

                contentScale = ContentScale.Crop,

            )

            Box(

                Modifier

                    .offset(41.dp, 44.dp)

                    .size(8.dp)

                    .clip(CircleShape)

                    .background(Color.White),

            ) {

                Box(Modifier.size(8.dp).clip(CircleShape).background(Green))

            }

        }



        Text(

            "${p.name}\n${p.role}",

            modifier = Modifier.offset(90.dp, 20.dp).width(127.dp),

            style = vs(13f, 400, 19.5f, 0f, Roboto),

            color = Color.Black,

            maxLines = 2,

        )



        Box(

            Modifier

                .offset(90.dp, 65.dp)

                .width(if (p.tag == "Consultoria") 87.dp else 114.dp)

                .height(21.dp)

                .clip(RoundedCornerShape(100.dp))

                .background(Color(0x59CAA6FF))

                .border(1.dp, Color(0x59904EF2), RoundedCornerShape(100.dp)),

            contentAlignment = Alignment.Center,

        ) {

            Text(

                p.tag,

                modifier = Modifier.offset(y = 1.dp),

                style = vs(10f, 500, 11.762f, 0f, Roboto),

                color = Color(0xFF883FF4),

                maxLines = 1,

            )

        }



        Box(

            Modifier

                .offset(258.dp, 20.dp)

                .width(50.dp)

                .height(49.dp)

                .clip(RoundedCornerShape(10.dp))

                .background(Color(0xFFE8D9FF))

                .border(1.dp, Color(0xFF9657F3), RoundedCornerShape(10.dp)),

            contentAlignment = Alignment.TopStart,

        ) {

            Text(

                buildAnnotatedString {

                    withStyle(SpanStyle(fontFamily = Roboto, fontWeight = FontWeight(800), fontSize = 20.sp, color = Color(0xFF9657F3))) {

                        append("${p.match}%")

                    }

                    withStyle(SpanStyle(fontFamily = Roboto, fontWeight = FontWeight(200), fontSize = 8.sp, color = Color.Black)) {

                        append("\u2028match")

                    }

                },

                modifier = Modifier.offset(x = 6.dp, y = 8.dp).width(39.dp).height(34.dp),

                style = TextStyle(lineHeightStyle = LineHeightStyle(LineHeightStyle.Alignment.Top, LineHeightStyle.Trim.None)),

                textAlign = TextAlign.Center,

                maxLines = 2,

            )

        }



        Text(

            p.desc,

            modifier = Modifier.offset(20.dp, 106.dp).width(293.dp),

            style = vs(12f, 400, 15.33f, 0f, Roboto),

            color = Color(0xC24D3274),

            maxLines = 3,

        )



        Box(

            Modifier

                .offset(20.dp, 172.dp)

                .width(293.dp)

                .height(32.dp)

                .clip(RoundedCornerShape(10.dp))

                .background(Color(0xFFEDEDED))

                .border(0.75.dp, Color(0xFFD5D5D5), RoundedCornerShape(10.dp)),

        ) {

            Icon(

                IcoAntDesignStarFilled,

                null,

                Modifier.offset(14.dp, 9.dp).size(14.dp),

                tint = Star,

            )

            Text(

                p.rating,

                modifier = Modifier.offset(33.dp, 10.dp),

                style = vs(10f, 600, 13f, 0f, Roboto),

                color = Color.Black,

                maxLines = 1,

            )

            Box(Modifier.offset(63.dp, 8.dp).width(1.dp).height(16.dp).background(Color(0xFFCFCFCF)))

            Text(

                "${p.services} serviços",

                modifier = Modifier.offset(75.dp, 10.dp),

                style = vs(10f, 300, 13f, 0f, Roboto),

                color = Color(0xFF432473),

                maxLines = 1,

            )

            Box(Modifier.offset(139.dp, 8.dp).width(1.dp).height(16.dp).background(Color(0xFFCFCFCF)))

            Text(

                p.status,

                modifier = Modifier.offset(151.dp, 10.dp).width(130.dp),

                style = vs(10f, 800, 13f, 0f, Roboto),

                color = Green,

                maxLines = 1,

            )

        }



        Box(

            Modifier

                .offset(17.dp, 224.dp)

                .width(140.dp)

                .height(41.dp)

                .clip(RoundedCornerShape(15.dp))

                .background(CardBg)

                .border(0.75.dp, Color(0xFFE0E0E0), RoundedCornerShape(15.dp))

                .vClick(PressWhite) { onVerPerfil() },

        ) {

            Text(

                "Ver perfil",

                modifier = Modifier.offset(x = 40.5.dp, y = 12.5.dp).width(59.dp),

                style = vs(14f, 800, 16.47f, 0f, Roboto),

                color = Color.Black,

                maxLines = 1,

            )

        }



        Box(

            Modifier

                .offset(173.dp, 224.dp)

                .width(140.dp)

                .height(41.dp)

                .shadow(5.dp, RoundedCornerShape(15.dp), ambientColor = Shadow, spotColor = Shadow)

                .clip(RoundedCornerShape(15.dp))

                .background(Pink)

                .vClick(PressWhite) { onContact() },

        ) {

            Icon(

                IcoBiChatLeftTextFill,

                null,

                Modifier.offset(x = 30.5.dp, y = 13.5.dp).size(width = 14.dp, height = 13.38.dp),

                tint = Color.White,

            )

            Text(

                "Contatar",

                modifier = Modifier.offset(x = 54.5.dp, y = 12.5.dp),

                style = vs(14f, 800, 16.41f, 0f, Roboto),

                color = Color.White,

                maxLines = 1,

            )

        }

    }

}

