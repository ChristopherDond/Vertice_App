package com.vertice.app.ui.screens.figma

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vertice.app.R
import com.vertice.app.ui.design.T
import com.vertice.app.ui.design.VColor
import com.vertice.app.ui.design.VGrad
import com.vertice.app.ui.design.VText
import com.vertice.app.ui.design.vs
import kotlinx.coroutines.delay

private val Montserrat = androidx.compose.ui.text.font.FontFamily(
    androidx.compose.ui.text.font.Font(R.font.montserrat_400, FontWeight(400)),
)
private val MontserratBold = androidx.compose.ui.text.font.FontFamily(
    androidx.compose.ui.text.font.Font(R.font.montserrat_700, FontWeight(700)),
)

@Composable
fun FigmaLogin(
    onEntrar: () -> Unit = {},
    onCadastrar: () -> Unit = {},
    onEsqueci: () -> Unit = {},
) {
    var cpf by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var verSenha by remember { mutableStateOf(false) }

    var canvasSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }

    Box(
        Modifier
            .fillMaxSize()
            .background(if (canvasSize == androidx.compose.ui.geometry.Size.Zero) VGrad.CardPink else VGrad.cardPink(canvasSize))
            .onSizeChanged { canvasSize = androidx.compose.ui.geometry.Size(it.width.toFloat(), it.height.toFloat()) },
    ) {

        Image(
            painter = painterResource(R.drawable.img_94_919),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.offset(x = 99.dp, y = 87.dp).size(195.dp),
        )

        Column(Modifier.offset(x = 44.dp, y = 287.dp).width(203.dp)) {
            VText("Faça seu login e ", vs(24f, 700, 24f), VColor.White)
            VText("conecte-se a novos parceiros.", vs(24f, 400, 24f), VColor.White)
        }

        Box(
            Modifier
                .offset(x = 0.dp, y = 395.dp)
                .fillMaxWidth()
                .height(457.dp)
                .clip(RoundedCornerShape(topStart = 70.dp))
                .background(VColor.White),
        )

        VText("CPF ou e-mail:", T.bodyMd, VColor.PurpleMuted, Modifier.offset(x = 53.dp, y = 419.dp))
        LoginField(
            value = cpf,
            onValueChange = { cpf = it },
            y = 443.dp,
            keyboardType = KeyboardType.Email,
            onImefinished = {},
        )

        VText("Senha:", T.bodyMd, VColor.PurpleMuted, Modifier.offset(x = 53.dp, y = 500.dp))
        LoginField(
            value = senha,
            onValueChange = { senha = it },
            y = 524.dp,
            keyboardType = KeyboardType.Password,
            isPassword = true,
            transformed = !verSenha,
            trailing = {
                AnimatedEye(
                    aberto = !verSenha,
                    onClick = { verSenha = !verSenha },
                )
            },
        )

        VText(
            "Esqueceu a senha?",
            vs(10f, 700, 10f),
            VColor.PurpleMuted2,
            Modifier.offset(x = 258.dp, y = 565.dp).clickable { onEsqueci() },
        )

        Box(
            Modifier
                .offset(x = 126.dp, y = 596.dp)
                .width(142.dp)
                .height(36.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(VColor.Purple)
                .clickable { onEntrar() },
        ) {
            VText("Entrar", vs(14f, 800, 16.47f), VColor.White, Modifier.align(Alignment.Center))
        }

        Row(
            Modifier.offset(x = 30.dp, y = 643.dp).width(333.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            VText("Ainda não tem uma conta? ", vs(12f, 400, 14.56f, family = Montserrat), VColor.PurpleMuted)
            VText(
                "Cadastre-se!",
                vs(12f, 700, 14.56f, family = MontserratBold),
                VColor.PurpleMuted,
                Modifier.clickable { onCadastrar() },
            )
        }

        Box(Modifier.offset(x = 30.dp, y = 683.dp).width(157.dp).height(1.dp).background(VColor.Gray3))
        Box(Modifier.offset(x = 207.dp, y = 683.dp).width(156.dp).height(1.dp).background(VColor.Gray3))
        VText("OU", vs(10f, 400, 10f), VColor.Gray3, Modifier.offset(x = 191.dp, y = 678.dp))

        SocialButton(
            y = 702.dp,
            label = "Continue com Google",
            bg = VColor.White,
            textColor = VColor.Ink,
            borderColor = VColor.Gray3,
            icon = {
                Image(
                    painterResource(R.drawable.svg_google),
                    null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(20.dp),
                )
            },
        )

        SocialButton(
            y = 760.dp,
            label = "Continue com Apple",
            bg = VColor.AppleDark,
            textColor = VColor.White,
            borderColor = null,
            icon = {
                Image(
                    painterResource(R.drawable.svg_apple),
                    null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(20.dp),
                )
            },
        )
    }
}

@Composable
private fun LoginField(
    value: String,
    onValueChange: (String) -> Unit,
    y: androidx.compose.ui.unit.Dp,
    keyboardType: KeyboardType,
    isPassword: Boolean = false,
    transformed: Boolean = false,
    onImefinished: () -> Unit = {},
    trailing: @Composable (() -> Unit)? = null,
) {
    val interaction = remember { MutableInteractionSource() }
    val focused by interaction.collectIsFocusedAsState()
    val stroke by animateFloatAsState(if (focused) 2.5f else 1f, tween(180), label = "stroke")

    Row(
        Modifier
            .offset(x = 52.dp, y = y)
            .width(288.dp)
            .height(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(VColor.White)
            .border(stroke.dp, VColor.FieldLine, RoundedCornerShape(10.dp))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.weight(1f)) {
            if (value.isEmpty()) {
                VText(
                    if (keyboardType == KeyboardType.Password) "••••••••" else "",
                    vs(13f, 400, 16f),
                    VColor.InkSoft,
                )
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = vs(13f, 400, 16f).copy(color = VColor.Ink),
                cursorBrush = SolidColor(VColor.PurpleMid),
                visualTransformation = if (isPassword && transformed) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else keyboardType),
                interactionSource = interaction,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        if (trailing != null) {
            Box(Modifier.padding(start = 8.dp)) { trailing() }
        }
    }
}

@Composable
private fun SocialButton(
    y: androidx.compose.ui.unit.Dp,
    label: String,
    bg: androidx.compose.ui.graphics.Color,
    textColor: androidx.compose.ui.graphics.Color,
    borderColor: androidx.compose.ui.graphics.Color?,
    icon: @Composable () -> Unit,
) {
    Box(
        Modifier
            .offset(x = 52.dp, y = y)
            .width(288.dp)
            .height(43.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(bg)
            .then(if (borderColor != null) Modifier.border(1.dp, borderColor, RoundedCornerShape(10.dp)) else Modifier),
    ) {
        Box(Modifier.align(Alignment.CenterStart).padding(start = 20.dp)) { icon() }
        VText(
            label,
            vs(12f, 500, 12f, -0.48f),
            textColor,
            Modifier.align(Alignment.Center).fillMaxWidth(),
            align = TextAlign.Center,
        )
    }
}

@Composable
private fun AnimatedEye(aberto: Boolean, onClick: () -> Unit) {
    var mostradoAberto by remember { mutableStateOf(aberto) }
    var piscando by remember { mutableStateOf(false) }

    LaunchedEffect(aberto) {
        piscando = true
        delay(150)
        mostradoAberto = aberto
        delay(150)
        piscando = false
    }

    val altura by animateFloatAsState(
        targetValue = if (piscando) 0.04f else 1f,
        animationSpec = tween(durationMillis = 150, easing = FastOutSlowInEasing),
        label = "palpebra",
    )

    Box(
        modifier = Modifier
            .size(width = 20.44.dp, height = 18.07.dp)
            .graphicsLayer { scaleY = altura }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(if (mostradoAberto) R.drawable.ic_eye_open else R.drawable.ic_eye_off),
            contentDescription = if (aberto) "Ocultar senha" else "Mostrar senha",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
