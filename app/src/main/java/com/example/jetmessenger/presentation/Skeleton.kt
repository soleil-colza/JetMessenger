package com.example.jetmessenger.presentation

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

@Composable
fun Modifier.skeleton(
    animationSpec: InfiniteRepeatableSpec<Float> = infiniteRepeatable(
        animation = tween(1500)
    ),
    colors: List<Color> = listOf(
        Color(0xFFB8B5B5),
        Color(0xFFC0C0C0),
        Color(0xFFD3D3D3),
        Color(0xFF808080),
        Color(0xFFB8B5B5)
    )
): Modifier = composed {

    var size by remember { mutableStateOf(IntSize.Zero) }

    val startOffsetX by rememberInfiniteTransition().animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = animationSpec, ""
    )

    val linearGradient = Brush.linearGradient(
        colors = colors,
        start = Offset(startOffsetX, 0f),
        end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
    )

    drawBehind {
        drawRect(color = Color.White)
        drawRect(brush = linearGradient, alpha = 0.5f)
    }
    onGloballyPositioned { coordinates ->
        size = coordinates.size
    }
}
