package dev.mslalith.interactive_math.contents.fibonacci

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.mslalith.interactive_math.extensions.HorizontalSpace
import dev.mslalith.interactive_math.extensions.toSize
import dev.mslalith.interactive_math.viewmodel.FibonacciViewModel
import kotlin.math.abs
import kotlin.math.sqrt


@Composable
fun Fibonacci() {
    val density = LocalDensity.current
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val fibonacciViewModel = hiltViewModel<FibonacciViewModel>()
    val itemsSize = 10

    var enableDebug by remember { mutableStateOf(false) }
    val fibonacciSum = fibonacciViewModel.sumOfFibonacciAt(itemsSize)
    val eachPartSize = screenWidth.times(sqrt(3.5f)) / fibonacciSum

    val rotationAngle by fibonacciViewModel.rotationAngle.collectAsState()
    val animatedRotation by animateFloatAsState(
        targetValue = rotationAngle,
        animationSpec = tween(durationMillis = 3000),
        finishedListener = { fibonacciViewModel.nextFibonacciState() }
    )


    LaunchedEffect(key1 = Unit) { fibonacciViewModel.nextFibonacciState() }


    Column {
        Box(
            modifier = Modifier
                .weight(weight = 1f)
                .padding(
                    horizontal = 18.dp,
                    vertical = 24.dp
                ),
            contentAlignment = Alignment.BottomStart
        ) {
            Square(
                index = itemsSize,
                showPoints = enableDebug,
                calculateSize = { index ->
                    density.run {
                        (eachPartSize * fibonacciViewModel.fibonacciNumberAt(index)).toDp()
                    }
                },
                calculateOffset = { _, sizeInDp ->
                    val size = density.run { sizeInDp.toPx() }.toInt()
                    IntOffset(x = size, y = -size)
                },
                calculateRotation = { animatedRotation },
                calculateAlpha = { rotation ->
                    when (val cycleFraction = abs(rotation).div(180).mod(2f)) {
                        in 0.5f..1f -> lerp(
                            start = 1f.toSize(),
                            stop = 0f.toSize(),
                            fraction = (cycleFraction * 2f) - 1f
                        ).width.coerceIn(0f, 1f)

                        in 1f..1.5f -> lerp(
                            start = 0f.toSize(),
                            stop = 1f.toSize(),
                            fraction = (cycleFraction - 1f) * 2f
                        ).width.coerceIn(0f, 1f)

                        else -> 1f
                    }
                },
                calculateCurveControlPoint = { size, rotation ->
                    val cycleFraction = abs(rotation).div(180).mod(2f)

                    val offset1 = when (cycleFraction) {
                        in 0f..0.5f -> lerp(
                            Offset(0f, size.height),
                            Offset(size.width * 0.5f, size.height),
                            cycleFraction * 2f
                        )
                        in 0.5f..1f -> lerp(
                            Offset(size.width * 0.5f, size.height),
                            Offset(size.width * 1.425f, size.height * 1.5f),
                            (cycleFraction * 2f) - 1f
                        )
                        in 1f..1.5f -> lerp(
                            Offset(size.width * -0.5f, size.height * -0.5f),
                            Offset(0f, size.height * 0.5f),
                            ((cycleFraction - 1f) * 2f)
                        )
                        in 1.5f..2f -> lerp(
                            Offset(0f, 0f),
                            Offset(0f, size.height * 0.5f),
                            ((cycleFraction - 1f) * 2f)
                        )
                        else -> Offset.Zero
                    }

                    val offset2 = when (cycleFraction) {
                        in 0f..0.5f -> lerp(
                            Offset(size.width, 0f),
                            Offset(size.width, size.height * 0.5f),
                            cycleFraction * 2f
                        )
                        in 0.5f..1f -> lerp(
                            Offset(size.width, size.height * 0.5f),
                            Offset(size.width * 1.425f, size.height * 0.25f),
                            (cycleFraction * 2f) - 1f
                        )
                        in 1f..1.5f -> lerp(
                            Offset(size.width * 1f, size.height * -0.5f),
                            Offset(size.width * 0.75f, size.height * -0.25f),
                            ((cycleFraction - 0.5f) * 2f)
                        )
                        in 1.5f..2f -> lerp(
                            Offset(0f, 0f),
                            Offset(size.width * 0.5f, 0f),
                            ((cycleFraction - 1f) * 2f)
                        )
                        else -> Offset.Zero
                    }

                    offset1 to offset2
                },
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = MaterialTheme.colorScheme.primary)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalSpace(width = 12.dp)
            Button(
                onClick = { enableDebug = !enableDebug },
                content = { Text(text = if (enableDebug) "Disable Debug" else "Enable Debug") }
            )
            AnimatedVisibility(visible = enableDebug) {
                Text(
                    text = "$itemsSize = $animatedRotation",
                    textAlign = TextAlign.Center,
                )
            }
            HorizontalSpace(width = 12.dp)
        }
    }
}

@Composable
private fun Square(
    index: Int,
    showPoints: Boolean,
    calculateSize: ((Int) -> Dp)?,
    calculateOffset: ((Int, Dp) -> IntOffset)?,
    calculateRotation: (() -> Float)?,
    calculateAlpha: ((Float) -> Float)?,
    calculateCurveControlPoint: ((Size, Float) -> Pair<Offset, Offset>)?,
) {
    if (index < 1 || calculateSize == null || calculateOffset == null || calculateRotation == null || calculateAlpha == null || calculateCurveControlPoint == null) return

    val size = calculateSize(index)
    val rotation = calculateRotation()
    Box(
        modifier = Modifier
            .size(size = size)
            .background(color = Color.Black.copy(alpha = 0.1f))
            .drawBehind {
                val drawSize = this.size
                val controlPoints = calculateCurveControlPoint(drawSize, rotation)
                drawPath(
                    color = Color.Blue,
                    style = Stroke(width = 1f),
                    path = Path().apply {
                        moveTo(0f, drawSize.height)
                        cubicTo(
                            x1 = controlPoints.first.x, y1 = controlPoints.first.y,
                            x2 = controlPoints.second.x, y2 = controlPoints.second.y,
                            x3 = drawSize.width, y3 = 0f
                        )
                    },
                    alpha = calculateAlpha(rotation)
                )

                if (showPoints) {
                    drawPoints(
                        points = controlPoints.toList(),
                        pointMode = PointMode.Points,
                        color = Color.Red,
                        strokeWidth = 14f,
                        cap = StrokeCap.Round
                    )
                }
            }
            .offset { calculateOffset(index, size) }
            .graphicsLayer {
                rotationZ = rotation
                transformOrigin = TransformOrigin(pivotFractionX = 0f, pivotFractionY = 1f)
            },
        contentAlignment = Alignment.BottomStart
    ) {
        val nextIndex = index - 1
        Square(
            index = nextIndex,
            showPoints = showPoints,
            calculateSize = calculateSize,
            calculateOffset = calculateOffset,
            calculateRotation = calculateRotation,
            calculateAlpha = calculateAlpha,
            calculateCurveControlPoint = calculateCurveControlPoint,
        )
    }
}
