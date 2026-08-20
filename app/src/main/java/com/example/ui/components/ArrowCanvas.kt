package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.model.ArrowSkin
import com.example.model.ArrowTailStyle
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

data class ArrowPosition(
    val centerX: Float,
    val centerY: Float,
    val angleDeg: Float,
    val lengthPx: Float,
    val tipX: Float,
    val tipY: Float,
    val tailX: Float,
    val tailY: Float
)

@Composable
fun ArrowGameCanvas(
    skin: ArrowSkin,
    isArrowVisible: Boolean,
    onArrowSpawned: (spawnTimeMs: Long) -> Unit,
    onTipClicked: (reactionTimeMs: Long, tipOffset: Offset) -> Unit,
    onMissClicked: (touchOffset: Offset) -> Unit,
    onTailClicked: (touchOffset: Offset) -> Unit = {},
    dotSkin: com.example.model.DotSkin = com.example.model.DotSkinCatalog.CLASSIC,
    showArrow: Boolean = true,
    showDot: Boolean = true,
    alignCenter: Boolean = true,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    var arrowPos by remember { mutableStateOf<ArrowPosition?>(null) }
    var spawnTimeMs by remember { mutableLongStateOf(0L) }

    // Pulse animation for the glowing tip
    val infiniteTransition = rememberInfiniteTransition(label = "tip_pulse")
    val tipPulseScale by infiniteTransition.animateFloat(
        initialValue = 0.88f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(550, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val strokeWidthPx = with(density) { skin.strokeWidthDp.dp.toPx() }
    val headWingLengthPx = with(density) { skin.headWingLengthDp.dp.toPx() }
    val hitRadiusPx = with(density) { 68.dp.toPx() }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val widthPx = with(density) { maxWidth.toPx() }
        val heightPx = with(density) { maxHeight.toPx() }

        // Recalculate arrow angle whenever it becomes visible again
        LaunchedEffect(isArrowVisible, widthPx, heightPx, alignCenter) {
            if (isArrowVisible && widthPx > 100f && heightPx > 100f) {
                val maxAvailable = minOf(widthPx * 0.82f, heightPx * 0.52f)
                val arrowLength = if (alignCenter) {
                    maxAvailable.coerceIn(
                        with(density) { 200.dp.toPx() },
                        with(density) { 380.dp.toPx() }
                    )
                } else {
                    // Random slightly shorter length to fit anywhere on random spawns
                    val minLen = with(density) { 150.dp.toPx() }
                    val maxLen = with(density) { 220.dp.toPx() }
                    Random.nextFloat() * (maxLen - minLen) + minLen
                }

                val halfL = arrowLength / 2f
                val angleDeg = Random.nextFloat() * 360f
                val angleRad = Math.toRadians(angleDeg.toDouble())

                val cX: Float
                val cY: Float

                if (alignCenter) {
                    cX = widthPx / 2f
                    cY = heightPx / 2f
                } else {
                    // Random spawn in safe playable zone
                    val minX = halfL + with(density) { 32.dp.toPx() }
                    val maxX = widthPx - minX
                    cX = if (maxX > minX) Random.nextFloat() * (maxX - minX) + minX else widthPx / 2f

                    val minY = 0.22f * heightPx + halfL
                    val maxY = 0.76f * heightPx - halfL
                    cY = if (maxY > minY) Random.nextFloat() * (maxY - minY) + minY else heightPx / 2f
                }

                val tipX = (cX + halfL * cos(angleRad)).toFloat()
                val tipY = (cY + halfL * sin(angleRad)).toFloat()
                val tailX = (cX - halfL * cos(angleRad)).toFloat()
                val tailY = (cY - halfL * sin(angleRad)).toFloat()

                arrowPos = ArrowPosition(
                    centerX = cX,
                    centerY = cY,
                    angleDeg = angleDeg,
                    lengthPx = arrowLength,
                    tipX = tipX,
                    tipY = tipY,
                    tailX = tailX,
                    tailY = tailY
                )
                val now = System.currentTimeMillis()
                spawnTimeMs = now
                onArrowSpawned(now)
            }
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .testTag("arrow_game_canvas")
                .pointerInput(isArrowVisible, arrowPos) {
                    detectTapGestures { tapOffset ->
                        if (!isArrowVisible) return@detectTapGestures
                        val currentArrow = arrowPos ?: return@detectTapGestures
                        val dx = tapOffset.x - currentArrow.tipX
                        val dy = tapOffset.y - currentArrow.tipY
                        val distance = sqrt((dx * dx + dy * dy).toDouble()).toFloat()

                        if (distance <= hitRadiusPx) {
                            val now = System.currentTimeMillis()
                            val reaction = (now - spawnTimeMs).coerceAtLeast(1L)
                            onTipClicked(reaction, Offset(currentArrow.tipX, currentArrow.tipY))
                        } else {
                            onMissClicked(tapOffset)
                        }
                    }
                }
        ) {
            if (isArrowVisible && arrowPos != null) {
                val pos = arrowPos!!
                drawSkinObject(
                    pos = pos,
                    skin = skin,
                    strokeWidthPx = strokeWidthPx,
                    headWingLengthPx = headWingLengthPx,
                    tipPulseScale = tipPulseScale,
                    density = density,
                    dotSkin = dotSkin,
                    showArrow = showArrow,
                    showDot = showDot
                )
            }
        }
    }
}

fun DrawScope.drawSkinObject(
    pos: ArrowPosition,
    skin: ArrowSkin,
    strokeWidthPx: Float,
    headWingLengthPx: Float,
    tipPulseScale: Float,
    density: androidx.compose.ui.unit.Density,
    dotSkin: com.example.model.DotSkin,
    showArrow: Boolean = true,
    showDot: Boolean = true
) {
    val angleRad = Math.toRadians(pos.angleDeg.toDouble())
    val wingAngleRad = Math.toRadians(skin.headWingAngleDeg.toDouble())
    val perpAngleRad = angleRad + Math.PI / 2.0

    if (showArrow) {
        when (skin.tailStyle) {

            // 1. CLASSIC SOLID (Original Clean Black Arrow)
            ArrowTailStyle.CLASSIC_SOLID -> {
                // Classic Pure Solid Shaft
                drawLine(
                    color = skin.strokeColor,
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx,
                    cap = StrokeCap.Round
                )
                // Classic Wings
                val w1Angle = angleRad + Math.PI - wingAngleRad
                val w2Angle = angleRad + Math.PI + wingAngleRad
                val w1 = Offset(
                    (pos.tipX + headWingLengthPx * cos(w1Angle)).toFloat(),
                    (pos.tipY + headWingLengthPx * sin(w1Angle)).toFloat()
                )
                val w2 = Offset(
                    (pos.tipX + headWingLengthPx * cos(w2Angle)).toFloat(),
                    (pos.tipY + headWingLengthPx * sin(w2Angle)).toFloat()
                )
                drawLine(
                    color = skin.strokeColor,
                    start = Offset(pos.tipX, pos.tipY),
                    end = w1,
                    strokeWidth = strokeWidthPx,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = skin.strokeColor,
                    start = Offset(pos.tipX, pos.tipY),
                    end = w2,
                    strokeWidth = strokeWidthPx,
                    cap = StrokeCap.Round
                )
            }

            // 2. REAL ARCHER ARROW (Cedar Wood + Triple Eagle Feathers + Steel Broadhead)
            ArrowTailStyle.REAL_ARCHER_ARROW -> {
                drawLine(
                    color = Color(0xFF5D4037),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 1.15f,
                    cap = StrokeCap.Round
                )
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF8D6E63), Color(0xFFA1887F), Color(0xFF6D4C41)),
                        start = Offset(pos.tailX, pos.tailY),
                        end = Offset(pos.tipX, pos.tipY)
                    ),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 0.75f,
                    cap = StrokeCap.Round
                )
                // Eagle Feather Fletching at Tail
                val fletchLen = strokeWidthPx * 3.4f
                val fletchWid = strokeWidthPx * 1.4f
                for (f in 0..2) {
                    val fOffset = f * strokeWidthPx * 0.95f
                    val fBaseX = (pos.tailX + fOffset * cos(angleRad)).toFloat()
                    val fBaseY = (pos.tailY + fOffset * sin(angleRad)).toFloat()
                    val fTip1 = Offset(
                        (fBaseX - fletchLen * 0.45f * cos(angleRad) + fletchWid * cos(perpAngleRad)).toFloat(),
                        (fBaseY - fletchLen * 0.45f * sin(angleRad) + fletchWid * sin(perpAngleRad)).toFloat()
                    )
                    val fTip2 = Offset(
                        (fBaseX - fletchLen * 0.45f * cos(angleRad) - fletchWid * cos(perpAngleRad)).toFloat(),
                        (fBaseY - fletchLen * 0.45f * sin(angleRad) - fletchWid * sin(perpAngleRad)).toFloat()
                    )
                    drawLine(color = Color(0xFFECEFF1), start = Offset(fBaseX, fBaseY), end = fTip1, strokeWidth = strokeWidthPx * 0.38f, cap = StrokeCap.Round)
                    drawLine(color = Color(0xFFB0BEC5), start = Offset(fBaseX, fBaseY), end = fTip2, strokeWidth = strokeWidthPx * 0.38f, cap = StrokeCap.Round)
                }
                // Steel Broadhead
                val headLen = strokeWidthPx * 3.0f
                val headWid = strokeWidthPx * 1.6f
                val arrowHead = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo(
                        (pos.tipX - headLen * cos(angleRad) + headWid * cos(perpAngleRad)).toFloat(),
                        (pos.tipY - headLen * sin(angleRad) + headWid * sin(perpAngleRad)).toFloat()
                    )
                    lineTo(
                        (pos.tipX - headLen * 0.65f * cos(angleRad)).toFloat(),
                        (pos.tipY - headLen * 0.65f * sin(angleRad)).toFloat()
                    )
                    lineTo(
                        (pos.tipX - headLen * cos(angleRad) - headWid * cos(perpAngleRad)).toFloat(),
                        (pos.tipY - headLen * sin(angleRad) - headWid * sin(perpAngleRad)).toFloat()
                    )
                    close()
                }
                drawPath(path = arrowHead, color = Color(0xFF37474F))
                drawPath(path = arrowHead, color = Color(0xFFCFD8DC), style = Stroke(width = strokeWidthPx * 0.28f))
                val bindX = (pos.tipX - headLen * 0.9f * cos(angleRad)).toFloat()
                val bindY = (pos.tipY - headLen * 0.9f * sin(angleRad)).toFloat()
                drawCircle(color = Color(0xFFFF1744), radius = strokeWidthPx * 0.5f, center = Offset(bindX, bindY))
            }

            // 3. NATURAL BAMBOO STICK (Green Segmented Shoot + Sprouts + Angled Cut Point)
            ArrowTailStyle.BAMBOO_STICK -> {
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF2E7D32), Color(0xFF43A047), Color(0xFF66BB6A), Color(0xFF2E7D32)),
                        start = Offset(pos.tailX, pos.tailY),
                        end = Offset(pos.tipX, pos.tipY)
                    ),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 1.35f,
                    cap = StrokeCap.Round
                )
                val nodeCount = 5
                for (n in 1..nodeCount) {
                    val t = n.toFloat() / (nodeCount + 1)
                    val nx = pos.tailX + (pos.tipX - pos.tailX) * t
                    val ny = pos.tailY + (pos.tipY - pos.tailY) * t
                    val nodeSpan = strokeWidthPx * 0.95f
                    drawLine(
                        color = Color(0xFF1B5E20),
                        start = Offset((nx + nodeSpan * cos(perpAngleRad)).toFloat(), (ny + nodeSpan * sin(perpAngleRad)).toFloat()),
                        end = Offset((nx - nodeSpan * cos(perpAngleRad)).toFloat(), (ny - nodeSpan * sin(perpAngleRad)).toFloat()),
                        strokeWidth = strokeWidthPx * 0.45f,
                        cap = StrokeCap.Round
                    )
                    val leafLen = strokeWidthPx * 1.7f
                    val leafDir = if (n % 2 == 0) 1f else -1f
                    val leafTip = Offset(
                        (nx + leafLen * cos(angleRad + leafDir * 0.65)).toFloat(),
                        (ny + leafLen * sin(angleRad + leafDir * 0.65)).toFloat()
                    )
                    drawLine(color = Color(0xFF81C784), start = Offset(nx, ny), end = leafTip, strokeWidth = strokeWidthPx * 0.3f, cap = StrokeCap.Round)
                }
                val sharpLen = strokeWidthPx * 2.4f
                val bambooTip = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo(
                        (pos.tipX - sharpLen * cos(angleRad) + strokeWidthPx * 0.7f * cos(perpAngleRad)).toFloat(),
                        (pos.tipY - sharpLen * sin(angleRad) + strokeWidthPx * 0.7f * sin(perpAngleRad)).toFloat()
                    )
                    lineTo(
                        (pos.tipX - sharpLen * 0.35f * cos(angleRad) - strokeWidthPx * 0.7f * cos(perpAngleRad)).toFloat(),
                        (pos.tipY - sharpLen * 0.35f * sin(angleRad) - strokeWidthPx * 0.7f * sin(perpAngleRad)).toFloat()
                    )
                    close()
                }
                drawPath(path = bambooTip, color = Color(0xFFC8E6C9))
                drawPath(path = bambooTip, color = Color(0xFF2E7D32), style = Stroke(width = strokeWidthPx * 0.2f))
            }

            // 4. WOODEN BRANCH STICK (Textured Forest Twig + Carved Hunting Tip)
            ArrowTailStyle.WOODEN_BRANCH_STICK -> {
                drawLine(
                    color = Color(0xFF3E2723),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 1.3f,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = Color(0xFF6D4C41),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 0.75f,
                    cap = StrokeCap.Round
                )
                val twigs = listOf(0.28f to 1f, 0.52f to -1f, 0.75f to 1f)
                for ((t, dir) in twigs) {
                    val bx = pos.tailX + (pos.tipX - pos.tailX) * t
                    val by = pos.tailY + (pos.tipY - pos.tailY) * t
                    val twigLen = strokeWidthPx * 1.5f
                    val twigEnd = Offset(
                        (bx + twigLen * cos(angleRad + dir * 0.72)).toFloat(),
                        (by + twigLen * sin(angleRad + dir * 0.72)).toFloat()
                    )
                    drawLine(color = Color(0xFF5D4037), start = Offset(bx, by), end = twigEnd, strokeWidth = strokeWidthPx * 0.38f, cap = StrokeCap.Round)
                    drawCircle(color = Color(0xFF7CB342), radius = strokeWidthPx * 0.2f, center = twigEnd)
                }
                val pointLen = strokeWidthPx * 2.2f
                val stickPoint = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo(
                        (pos.tipX - pointLen * cos(angleRad) + strokeWidthPx * 0.65f * cos(perpAngleRad)).toFloat(),
                        (pos.tipY - pointLen * sin(angleRad) + strokeWidthPx * 0.65f * sin(perpAngleRad)).toFloat()
                    )
                    lineTo(
                        (pos.tipX - pointLen * cos(angleRad) - strokeWidthPx * 0.65f * cos(perpAngleRad)).toFloat(),
                        (pos.tipY - pointLen * sin(angleRad) - strokeWidthPx * 0.65f * sin(perpAngleRad)).toFloat()
                    )
                    close()
                }
                drawPath(path = stickPoint, color = Color(0xFFFFCC80))
            }

            // 5. WATER PIPE (Galvanized Pipe + Brass Couplers + Pressure Jet Nozzle)
            ArrowTailStyle.WATER_PIPE -> {
                drawLine(
                    color = Color(0xFF37474F),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 1.5f,
                    cap = StrokeCap.Square
                )
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF78909C), Color(0xFFCFD8DC), Color(0xFF546E7A)),
                        start = Offset(pos.tailX, pos.tailY),
                        end = Offset(pos.tipX, pos.tipY)
                    ),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 0.95f,
                    cap = StrokeCap.Square
                )
                val pipeJoints = listOf(0.22f, 0.55f, 0.82f)
                for (jt in pipeJoints) {
                    val jx = pos.tailX + (pos.tipX - pos.tailX) * jt
                    val jy = pos.tailY + (pos.tipY - pos.tailY) * jt
                    val ringSpan = strokeWidthPx * 1.05f
                    drawLine(
                        color = Color(0xFFFFB300),
                        start = Offset((jx + ringSpan * cos(perpAngleRad)).toFloat(), (jy + ringSpan * sin(perpAngleRad)).toFloat()),
                        end = Offset((jx - ringSpan * cos(perpAngleRad)).toFloat(), (jy - ringSpan * sin(perpAngleRad)).toFloat()),
                        strokeWidth = strokeWidthPx * 0.55f,
                        cap = StrokeCap.Round
                    )
                }
                // High pressure water burst
                val jetLen = strokeWidthPx * 2.5f
                val jetWid = strokeWidthPx * 1.3f
                val waterJet = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo(
                        (pos.tipX - jetLen * cos(angleRad) + jetWid * cos(perpAngleRad)).toFloat(),
                        (pos.tipY - jetLen * sin(angleRad) + jetWid * sin(perpAngleRad)).toFloat()
                    )
                    lineTo(
                        (pos.tipX - jetLen * cos(angleRad) - jetWid * cos(perpAngleRad)).toFloat(),
                        (pos.tipY - jetLen * sin(angleRad) - jetWid * sin(perpAngleRad)).toFloat()
                    )
                    close()
                }
                drawPath(
                    path = waterJet,
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFFE0F7FA), Color(0xFF00E5FF), Color(0x3300B0FF)),
                        center = Offset(pos.tipX, pos.tipY)
                    )
                )
            }

            // 6. CANDY CANE (Glossy Peppermint Striped Spiral + Candy Hook)
            ArrowTailStyle.CANDY_CANE -> {
                drawLine(
                    color = Color.White,
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 1.3f,
                    cap = StrokeCap.Round
                )
                val stripes = 14
                for (i in 0..stripes) {
                    val t = i.toFloat() / stripes
                    val sx = pos.tailX + (pos.tipX - pos.tailX) * t
                    val sy = pos.tailY + (pos.tipY - pos.tailY) * t
                    val stripeSpan = strokeWidthPx * 0.8f
                    drawLine(
                        color = Color(0xFFD50000),
                        start = Offset((sx + stripeSpan * cos(perpAngleRad + 0.55)).toFloat(), (sy + stripeSpan * sin(perpAngleRad + 0.55)).toFloat()),
                        end = Offset((sx - stripeSpan * cos(perpAngleRad + 0.55)).toFloat(), (sy - stripeSpan * sin(perpAngleRad + 0.55)).toFloat()),
                        strokeWidth = strokeWidthPx * 0.44f,
                        cap = StrokeCap.Round
                    )
                }
                // Sweet sugar gloss highlight
                drawLine(
                    color = Color.White.copy(alpha = 0.85f),
                    start = Offset((pos.tailX + strokeWidthPx * 0.28f * cos(perpAngleRad)).toFloat(), (pos.tailY + strokeWidthPx * 0.28f * sin(perpAngleRad)).toFloat()),
                    end = Offset((pos.tipX + strokeWidthPx * 0.28f * cos(perpAngleRad)).toFloat(), (pos.tipY + strokeWidthPx * 0.28f * sin(perpAngleRad)).toFloat()),
                    strokeWidth = strokeWidthPx * 0.24f,
                    cap = StrokeCap.Round
                )
                // Sweet heart candy point
                val cpHead = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo((pos.tipX - strokeWidthPx * 1.6f * cos(angleRad) + strokeWidthPx * 0.8f * cos(perpAngleRad)).toFloat(), (pos.tipY - strokeWidthPx * 1.6f * sin(angleRad) + strokeWidthPx * 0.8f * sin(perpAngleRad)).toFloat())
                    lineTo((pos.tipX - strokeWidthPx * 1.6f * cos(angleRad) - strokeWidthPx * 0.8f * cos(perpAngleRad)).toFloat(), (pos.tipY - strokeWidthPx * 1.6f * sin(angleRad) - strokeWidthPx * 0.8f * sin(perpAngleRad)).toFloat())
                    close()
                }
                drawPath(path = cpHead, color = Color(0xFFFF1744))
            }

            // 7. RED TIP BEAM (Tactical Laser Rail with Guidance Diode)
            ArrowTailStyle.RED_TIP_BEAM -> {
                drawLine(
                    color = Color(0xFF212121),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 1.4f,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = Color(0xFF424242),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 0.8f,
                    cap = StrokeCap.Round
                )
                val subTipX = (pos.tipX - (pos.tipX - pos.tailX) * 0.45f)
                val subTipY = (pos.tipY - (pos.tipY - pos.tailY) * 0.45f)
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Transparent, Color(0xFFFF1744)),
                        start = Offset(subTipX, subTipY),
                        end = Offset(pos.tipX, pos.tipY)
                    ),
                    start = Offset(subTipX, subTipY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 1.15f,
                    cap = StrokeCap.Round
                )
                // Laser chevron head
                val w1 = Offset((pos.tipX + headWingLengthPx * 0.85f * cos(angleRad + Math.PI - wingAngleRad)).toFloat(), (pos.tipY + headWingLengthPx * 0.85f * sin(angleRad + Math.PI - wingAngleRad)).toFloat())
                val w2 = Offset((pos.tipX + headWingLengthPx * 0.85f * cos(angleRad + Math.PI + wingAngleRad)).toFloat(), (pos.tipY + headWingLengthPx * 0.85f * sin(angleRad + Math.PI + wingAngleRad)).toFloat())
                drawLine(color = Color(0xFFFF1744), start = w1, end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 0.8f, cap = StrokeCap.Round)
                drawLine(color = Color(0xFFFF1744), start = w2, end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 0.8f, cap = StrokeCap.Round)
            }

            // 8. REALISTIC SNAKE (Slithering Viper + Diamond Scales + Amber Eyes)
            ArrowTailStyle.SNAKE_REALISTIC -> {
                val segments = 32
                val path = Path()
                val waveAmp = strokeWidthPx * 0.95f
                val waveFreq = 3.2

                for (i in 0..segments) {
                    val t = i.toFloat() / segments
                    val baseX = pos.tailX + (pos.tipX - pos.tailX) * t
                    val baseY = pos.tailY + (pos.tipY - pos.tailY) * t
                    val waveOffset = (sin(t * Math.PI * 2.0 * waveFreq) * waveAmp * (1f - t * 0.3f)).toFloat()
                    val px = (baseX + waveOffset * cos(perpAngleRad)).toFloat()
                    val py = (baseY + waveOffset * sin(perpAngleRad)).toFloat()

                    if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
                }

                drawPath(path = path, color = Color(0xFF1B5E20), style = Stroke(width = strokeWidthPx * 1.35f, cap = StrokeCap.Round, join = StrokeJoin.Round))
                drawPath(path = path, color = Color(0xFF4CAF50), style = Stroke(width = strokeWidthPx * 0.85f, cap = StrokeCap.Round, join = StrokeJoin.Round))
                
                for (i in 2 until segments step 2) {
                    val t = i.toFloat() / segments
                    val baseX = pos.tailX + (pos.tipX - pos.tailX) * t
                    val baseY = pos.tailY + (pos.tipY - pos.tailY) * t
                    val waveOffset = (sin(t * Math.PI * 2.0 * waveFreq) * waveAmp * (1f - t * 0.3f)).toFloat()
                    val px = (baseX + waveOffset * cos(perpAngleRad)).toFloat()
                    val py = (baseY + waveOffset * sin(perpAngleRad)).toFloat()
                    drawCircle(color = Color(0xFFFFD54F), radius = strokeWidthPx * 0.22f, center = Offset(px, py))
                }

                val headLen = strokeWidthPx * 2.3f
                val headWid = strokeWidthPx * 1.55f
                val hLeft = Offset((pos.tipX - headLen * 0.6f * cos(angleRad) + headWid * 0.6f * cos(perpAngleRad)).toFloat(), (pos.tipY - headLen * 0.6f * sin(angleRad) + headWid * 0.6f * sin(perpAngleRad)).toFloat())
                val hRight = Offset((pos.tipX - headLen * 0.6f * cos(angleRad) - headWid * 0.6f * cos(perpAngleRad)).toFloat(), (pos.tipY - headLen * 0.6f * sin(angleRad) - headWid * 0.6f * sin(perpAngleRad)).toFloat())
                val hBack = Offset((pos.tipX - headLen * cos(angleRad)).toFloat(), (pos.tipY - headLen * sin(angleRad)).toFloat())
                
                val viperHead = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo(hLeft.x, hLeft.y)
                    lineTo(hBack.x, hBack.y)
                    lineTo(hRight.x, hRight.y)
                    close()
                }
                drawPath(path = viperHead, color = Color(0xFF2E7D32))

                // Snake Eyes & Tongue
                val eyeDist = strokeWidthPx * 0.38f
                val eyeLeft = Offset((pos.tipX - headLen * 0.4f * cos(angleRad) + eyeDist * cos(perpAngleRad)).toFloat(), (pos.tipY - headLen * 0.4f * sin(angleRad) + eyeDist * sin(perpAngleRad)).toFloat())
                val eyeRight = Offset((pos.tipX - headLen * 0.4f * cos(angleRad) - eyeDist * cos(perpAngleRad)).toFloat(), (pos.tipY - headLen * 0.4f * sin(angleRad) - eyeDist * sin(perpAngleRad)).toFloat())
                drawCircle(color = Color(0xFFFFEB3B), radius = strokeWidthPx * 0.17f, center = eyeLeft)
                drawCircle(color = Color(0xFFFFEB3B), radius = strokeWidthPx * 0.17f, center = eyeRight)
                drawCircle(color = Color(0xFF000000), radius = strokeWidthPx * 0.08f, center = eyeLeft)
                drawCircle(color = Color(0xFF000000), radius = strokeWidthPx * 0.08f, center = eyeRight)
            }

            // 9. LIGHTNING BOLT (Branching Plasma Storm Arc)
            ArrowTailStyle.LIGHTNING_BOLT -> {
                val boltPath = Path()
                boltPath.moveTo(pos.tailX, pos.tailY)

                val segs = 7
                val dx = (pos.tipX - pos.tailX) / segs
                val dy = (pos.tipY - pos.tailY) / segs
                val zigzagAmp = strokeWidthPx * 1.5f

                for (i in 1 until segs) {
                    val side = if (i % 2 == 0) 1f else -1f
                    val targetX = pos.tailX + dx * i + (side * zigzagAmp * cos(perpAngleRad)).toFloat()
                    val targetY = pos.tailY + dy * i + (side * zigzagAmp * sin(perpAngleRad)).toFloat()
                    boltPath.lineTo(targetX, targetY)
                }
                boltPath.lineTo(pos.tipX, pos.tipY)

                drawPath(path = boltPath, color = Color(0xFFFFEA00).copy(alpha = 0.4f), style = Stroke(width = strokeWidthPx * 2.3f, cap = StrokeCap.Round, join = StrokeJoin.Miter))
                drawPath(path = boltPath, color = Color(0xFFFFD600), style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round, join = StrokeJoin.Miter))
                drawPath(path = boltPath, color = Color.White, style = Stroke(width = strokeWidthPx * 0.45f, cap = StrokeCap.Round, join = StrokeJoin.Miter))
            }

            // 10. DRAGON KATANA (Samurai Sword + Gold Tsuba + Tempered Steel Blade)
            ArrowTailStyle.DRAGON_KATANA -> {
                // Cord wrapped Tsuka handle at tail
                val handleLen = (pos.lengthPx * 0.3f)
                val handleEndX = pos.tailX + handleLen * cos(angleRad).toFloat()
                val handleEndY = pos.tailY + handleLen * sin(angleRad).toFloat()
                drawLine(color = Color(0xFF212121), start = Offset(pos.tailX, pos.tailY), end = Offset(handleEndX, handleEndY), strokeWidth = strokeWidthPx * 1.25f, cap = StrokeCap.Round)
                
                // Blade with hamon temper shine
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF78909C), Color(0xFFCFD8DC), Color(0xFFFFFFFF), Color(0xFFECEFF1)),
                        start = Offset(handleEndX, handleEndY),
                        end = Offset(pos.tipX, pos.tipY)
                    ),
                    start = Offset(handleEndX, handleEndY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 0.95f,
                    cap = StrokeCap.Round
                )
                // Golden dragon tsuba guard
                val guardW = strokeWidthPx * 2.6f
                drawLine(
                    color = Color(0xFFFFD700),
                    start = Offset((handleEndX + guardW * 0.5f * cos(perpAngleRad)).toFloat(), (handleEndY + guardW * 0.5f * sin(perpAngleRad)).toFloat()),
                    end = Offset((handleEndX - guardW * 0.5f * cos(perpAngleRad)).toFloat(), (handleEndY - guardW * 0.5f * sin(perpAngleRad)).toFloat()),
                    strokeWidth = strokeWidthPx * 0.7f,
                    cap = StrokeCap.Round
                )
                // Curved Kissaki Katana Tip
                val tipBack = Offset((pos.tipX - strokeWidthPx * 2.2f * cos(angleRad)).toFloat(), (pos.tipY - strokeWidthPx * 2.2f * sin(angleRad)).toFloat())
                val tipEdge = Offset((pos.tipX - strokeWidthPx * 1.8f * cos(angleRad) + strokeWidthPx * 0.8f * cos(perpAngleRad)).toFloat(), (pos.tipY - strokeWidthPx * 1.8f * sin(angleRad) + strokeWidthPx * 0.8f * sin(perpAngleRad)).toFloat())
                val kissaki = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo(tipEdge.x, tipEdge.y)
                    lineTo(tipBack.x, tipBack.y)
                    close()
                }
                drawPath(path = kissaki, color = Color(0xFFECEFF1))
                drawPath(path = kissaki, color = Color(0xFFFF1744), style = Stroke(width = strokeWidthPx * 0.2f))
            }

            // 11. NEON CYBER (Holographic Laser Vector + Dual Energy Rails)
            ArrowTailStyle.NEON_CYBER -> {
                drawLine(color = Color(0xFF00E5FF).copy(alpha = 0.35f), start = Offset(pos.tailX, pos.tailY), end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 2.2f, cap = StrokeCap.Round)
                drawLine(color = Color(0xFF00E5FF), start = Offset(pos.tailX, pos.tailY), end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 0.9f, cap = StrokeCap.Round)
                drawLine(color = Color.White, start = Offset(pos.tailX, pos.tailY), end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 0.3f, cap = StrokeCap.Round)

                val w1 = Offset((pos.tipX + headWingLengthPx * cos(angleRad + Math.PI - wingAngleRad)).toFloat(), (pos.tipY + headWingLengthPx * sin(angleRad + Math.PI - wingAngleRad)).toFloat())
                val w2 = Offset((pos.tipX + headWingLengthPx * cos(angleRad + Math.PI + wingAngleRad)).toFloat(), (pos.tipY + headWingLengthPx * sin(angleRad + Math.PI + wingAngleRad)).toFloat())
                drawLine(color = Color(0xFF00E5FF).copy(alpha = 0.4f), start = w1, end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 2f, cap = StrokeCap.Round)
                drawLine(color = Color(0xFF00E5FF).copy(alpha = 0.4f), start = w2, end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 2f, cap = StrokeCap.Round)
                drawLine(color = Color(0xFF00E5FF), start = w1, end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx, cap = StrokeCap.Round)
                drawLine(color = Color(0xFF00E5FF), start = w2, end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx, cap = StrokeCap.Round)
            }

            // 12. GOLDEN CHROME (24K Gold Mirror Arrow + Specular Highlights)
            ArrowTailStyle.GOLDEN_CHROME -> {
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFFFD700), Color(0xFFFFB300), Color(0xFFFFF9C4), Color(0xFFFFD700)),
                        start = Offset(pos.tailX, pos.tailY),
                        end = Offset(pos.tipX, pos.tipY)
                    ),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 1.3f,
                    cap = StrokeCap.Round
                )
                // Royal gold fleur arrowhead
                val gHeadLen = strokeWidthPx * 2.8f
                val gHeadWid = strokeWidthPx * 1.5f
                val gHead = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo((pos.tipX - gHeadLen * cos(angleRad) + gHeadWid * cos(perpAngleRad)).toFloat(), (pos.tipY - gHeadLen * sin(angleRad) + gHeadWid * sin(perpAngleRad)).toFloat())
                    lineTo((pos.tipX - gHeadLen * 0.7f * cos(angleRad)).toFloat(), (pos.tipY - gHeadLen * 0.7f * sin(angleRad)).toFloat())
                    lineTo((pos.tipX - gHeadLen * cos(angleRad) - gHeadWid * cos(perpAngleRad)).toFloat(), (pos.tipY - gHeadLen * sin(angleRad) - gHeadWid * sin(perpAngleRad)).toFloat())
                    close()
                }
                drawPath(path = gHead, brush = Brush.linearGradient(listOf(Color(0xFFFFF59D), Color(0xFFFFD700), Color(0xFFFF8F00))))
                drawPath(path = gHead, color = Color.White, style = Stroke(width = strokeWidthPx * 0.25f))
            }

            // 13. FIRE EMBER (Inferno Flame Trail + Swirling Heat Sparks)
            ArrowTailStyle.FIRE_EMBER -> {
                drawLine(color = Color(0xFFFF6D00).copy(alpha = 0.4f), start = Offset(pos.tailX, pos.tailY), end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 2.4f, cap = StrokeCap.Round)
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFD50000), Color(0xFFFF3D00), Color(0xFFFFAB00), Color(0xFFFFF9C4)),
                        start = Offset(pos.tailX, pos.tailY),
                        end = Offset(pos.tipX, pos.tipY)
                    ),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 1.1f,
                    cap = StrokeCap.Round
                )
                // Flame spearhead
                val fHeadLen = strokeWidthPx * 3.0f
                val fHeadWid = strokeWidthPx * 1.6f
                val flameHead = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo((pos.tipX - fHeadLen * 0.7f * cos(angleRad) + fHeadWid * cos(perpAngleRad)).toFloat(), (pos.tipY - fHeadLen * 0.7f * sin(angleRad) + fHeadWid * sin(perpAngleRad)).toFloat())
                    lineTo((pos.tipX - fHeadLen * cos(angleRad)).toFloat(), (pos.tipY - fHeadLen * sin(angleRad)).toFloat())
                    lineTo((pos.tipX - fHeadLen * 0.7f * cos(angleRad) - fHeadWid * cos(perpAngleRad)).toFloat(), (pos.tipY - fHeadLen * 0.7f * sin(angleRad) - fHeadWid * sin(perpAngleRad)).toFloat())
                    close()
                }
                drawPath(path = flameHead, brush = Brush.radialGradient(listOf(Color(0xFFFFF9C4), Color(0xFFFF6D00), Color(0xFFFF1744)), center = Offset(pos.tipX, pos.tipY)))
            }

            // 14. EMERALD CRYSTAL (Faceted 3D Gemstone Prism + Refractive Light)
            ArrowTailStyle.EMERALD_CRYSTAL -> {
                drawLine(color = Color(0xFF00E676).copy(alpha = 0.35f), start = Offset(pos.tailX, pos.tailY), end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 2f, cap = StrokeCap.Round)
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF00C853), Color(0xFF69F0AE), Color(0xFF00E676)),
                        start = Offset(pos.tailX, pos.tailY),
                        end = Offset(pos.tipX, pos.tipY)
                    ),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 1.15f,
                    cap = StrokeCap.Round
                )
                // Diamond cut faceted emerald head
                val crHeadLen = strokeWidthPx * 2.8f
                val crHeadWid = strokeWidthPx * 1.5f
                val crHead = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo((pos.tipX - crHeadLen * 0.6f * cos(angleRad) + crHeadWid * cos(perpAngleRad)).toFloat(), (pos.tipY - crHeadLen * 0.6f * sin(angleRad) + crHeadWid * sin(perpAngleRad)).toFloat())
                    lineTo((pos.tipX - crHeadLen * cos(angleRad)).toFloat(), (pos.tipY - crHeadLen * sin(angleRad)).toFloat())
                    lineTo((pos.tipX - crHeadLen * 0.6f * cos(angleRad) - crHeadWid * cos(perpAngleRad)).toFloat(), (pos.tipY - crHeadLen * 0.6f * sin(angleRad) - crHeadWid * sin(perpAngleRad)).toFloat())
                    close()
                }
                drawPath(path = crHead, color = Color(0xFF00E676))
                drawPath(path = crHead, color = Color(0xFFE8F5E9), style = Stroke(width = strokeWidthPx * 0.28f))
            }

            // 15. COSMIC STAR (Deep Space Nebula + Stardust Core + Galactic Warp Head)
            ArrowTailStyle.COSMIC_STAR -> {
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF311B92), Color(0xFF7C4DFF), Color(0xFFE040FB), Color(0xFFEDE7F6)),
                        start = Offset(pos.tailX, pos.tailY),
                        end = Offset(pos.tipX, pos.tipY)
                    ),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 1.3f,
                    cap = StrokeCap.Round
                )
                // Sparkling stars along shaft
                val starPts = listOf(0.3f, 0.6f, 0.85f)
                for (st in starPts) {
                    val sx = pos.tailX + (pos.tipX - pos.tailX) * st
                    val sy = pos.tailY + (pos.tipY - pos.tailY) * st
                    drawCircle(color = Color.White, radius = strokeWidthPx * 0.25f, center = Offset(sx, sy))
                }
                val cW1 = Offset((pos.tipX + headWingLengthPx * 0.9f * cos(angleRad + Math.PI - wingAngleRad)).toFloat(), (pos.tipY + headWingLengthPx * 0.9f * sin(angleRad + Math.PI - wingAngleRad)).toFloat())
                val cW2 = Offset((pos.tipX + headWingLengthPx * 0.9f * cos(angleRad + Math.PI + wingAngleRad)).toFloat(), (pos.tipY + headWingLengthPx * 0.9f * sin(angleRad + Math.PI + wingAngleRad)).toFloat())
                drawLine(color = Color(0xFFE040FB), start = cW1, end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 0.9f, cap = StrokeCap.Round)
                drawLine(color = Color(0xFFE040FB), start = cW2, end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 0.9f, cap = StrokeCap.Round)
            }

            // 16. STEALTH OBSIDIAN (Angular Stealth Composite + Dark Matter Mirage)
            ArrowTailStyle.STEALTH_OBSIDIAN -> {
                drawLine(color = Color(0xFF4A148C).copy(alpha = 0.35f), start = Offset(pos.tailX, pos.tailY), end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 2f, cap = StrokeCap.Square)
                drawLine(color = Color(0xFF1A1A24), start = Offset(pos.tailX, pos.tailY), end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 1.2f, cap = StrokeCap.Square)
                
                // Stealth faceted arrowhead
                val stHeadLen = strokeWidthPx * 2.9f
                val stHeadWid = strokeWidthPx * 1.6f
                val stHead = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo((pos.tipX - stHeadLen * cos(angleRad) + stHeadWid * cos(perpAngleRad)).toFloat(), (pos.tipY - stHeadLen * sin(angleRad) + stHeadWid * sin(perpAngleRad)).toFloat())
                    lineTo((pos.tipX - stHeadLen * 0.65f * cos(angleRad)).toFloat(), (pos.tipY - stHeadLen * 0.65f * sin(angleRad)).toFloat())
                    lineTo((pos.tipX - stHeadLen * cos(angleRad) - stHeadWid * cos(perpAngleRad)).toFloat(), (pos.tipY - stHeadLen * sin(angleRad) - stHeadWid * sin(perpAngleRad)).toFloat())
                    close()
                }
                drawPath(path = stHead, color = Color(0xFF1A1A24))
                drawPath(path = stHead, color = Color(0xFF7B1FA2), style = Stroke(width = strokeWidthPx * 0.25f))
            }

            // 17. RAINBOW HYPER (Chromatic 7-Color Spectral Beam)
            ArrowTailStyle.RAINBOW_HYPER -> {
                val rainbowBrush = Brush.linearGradient(
                    colors = listOf(Color(0xFFFF1744), Color(0xFFFF9100), Color(0xFFFFEA00), Color(0xFF00E676), Color(0xFF00E5FF), Color(0xFF7C4DFF)),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY)
                )
                drawLine(brush = rainbowBrush, start = Offset(pos.tailX, pos.tailY), end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 1.35f, cap = StrokeCap.Round)
                
                val rW1 = Offset((pos.tipX + headWingLengthPx * cos(angleRad + Math.PI - wingAngleRad)).toFloat(), (pos.tipY + headWingLengthPx * sin(angleRad + Math.PI - wingAngleRad)).toFloat())
                val rW2 = Offset((pos.tipX + headWingLengthPx * cos(angleRad + Math.PI + wingAngleRad)).toFloat(), (pos.tipY + headWingLengthPx * sin(angleRad + Math.PI + wingAngleRad)).toFloat())
                drawLine(color = Color(0xFFFF1744), start = rW1, end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 0.9f, cap = StrokeCap.Round)
                drawLine(color = Color(0xFF00E5FF), start = rW2, end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 0.9f, cap = StrokeCap.Round)
            }

            // 18. MECHA RAILGUN (Dual Heavy Magnetic Rails + Plasma Sabot)
            ArrowTailStyle.MECHA_RAILGUN -> {
                val railOffset = strokeWidthPx * 0.6f
                val r1StartX = (pos.tailX + railOffset * cos(perpAngleRad)).toFloat()
                val r1StartY = (pos.tailY + railOffset * sin(perpAngleRad)).toFloat()
                val r1EndX = (pos.tipX + railOffset * cos(perpAngleRad)).toFloat()
                val r1EndY = (pos.tipY + railOffset * sin(perpAngleRad)).toFloat()

                val r2StartX = (pos.tailX - railOffset * cos(perpAngleRad)).toFloat()
                val r2StartY = (pos.tailY - railOffset * sin(perpAngleRad)).toFloat()
                val r2EndX = (pos.tipX - railOffset * cos(perpAngleRad)).toFloat()
                val r2EndY = (pos.tipY - railOffset * sin(perpAngleRad)).toFloat()

                drawLine(color = Color(0xFF37474F), start = Offset(r1StartX, r1StartY), end = Offset(r1EndX, r1EndY), strokeWidth = strokeWidthPx * 0.5f, cap = StrokeCap.Square)
                drawLine(color = Color(0xFF37474F), start = Offset(r2StartX, r2StartY), end = Offset(r2EndX, r2EndY), strokeWidth = strokeWidthPx * 0.5f, cap = StrokeCap.Square)
                drawLine(color = Color(0xFF00E676), start = Offset(pos.tailX, pos.tailY), end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 0.7f, cap = StrokeCap.Round)
                
                // Sabot Penetrator Head
                val sLen = strokeWidthPx * 2.6f
                val sWid = strokeWidthPx * 1.4f
                val sabot = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo((pos.tipX - sLen * cos(angleRad) + sWid * cos(perpAngleRad)).toFloat(), (pos.tipY - sLen * sin(angleRad) + sWid * sin(perpAngleRad)).toFloat())
                    lineTo((pos.tipX - sLen * cos(angleRad) - sWid * cos(perpAngleRad)).toFloat(), (pos.tipY - sLen * sin(angleRad) - sWid * sin(perpAngleRad)).toFloat())
                    close()
                }
                drawPath(path = sabot, color = Color(0xFF455A64))
                drawPath(path = sabot, color = Color(0xFF00E676), style = Stroke(width = strokeWidthPx * 0.25f))
            }

            // 19. ICE SPIKE (Jagged Antarctic Glacial Icicle + Sub-Zero Frost Barbs)
            ArrowTailStyle.ICE_SPIKE -> {
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFE0F7FA), Color(0xFF80DEEA), Color(0xFF00E5FF)),
                        start = Offset(pos.tailX, pos.tailY),
                        end = Offset(pos.tipX, pos.tipY)
                    ),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 1.25f,
                    cap = StrokeCap.Square
                )
                // Frost side barbs
                val subX = pos.tailX + (pos.tipX - pos.tailX) * 0.5f
                val subY = pos.tailY + (pos.tipY - pos.tailY) * 0.5f
                val pX1 = (subX + strokeWidthPx * 1.6f * cos(perpAngleRad)).toFloat()
                val pY1 = (subY + strokeWidthPx * 1.6f * sin(perpAngleRad)).toFloat()
                val pX2 = (subX - strokeWidthPx * 1.6f * cos(perpAngleRad)).toFloat()
                val pY2 = (subY - strokeWidthPx * 1.6f * sin(perpAngleRad)).toFloat()
                drawLine(color = Color(0xFFE0F7FA), start = Offset(pX1, pY1), end = Offset(pX2, pY2), strokeWidth = strokeWidthPx * 0.45f)
                
                // Ice crystal spearhead
                val iHeadLen = strokeWidthPx * 2.8f
                val iHeadWid = strokeWidthPx * 1.4f
                val iceHead = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo((pos.tipX - iHeadLen * cos(angleRad) + iHeadWid * cos(perpAngleRad)).toFloat(), (pos.tipY - iHeadLen * sin(angleRad) + iHeadWid * sin(perpAngleRad)).toFloat())
                    lineTo((pos.tipX - iHeadLen * 0.5f * cos(angleRad)).toFloat(), (pos.tipY - iHeadLen * 0.5f * sin(angleRad)).toFloat())
                    lineTo((pos.tipX - iHeadLen * cos(angleRad) - iHeadWid * cos(perpAngleRad)).toFloat(), (pos.tipY - iHeadLen * sin(angleRad) - iHeadWid * sin(perpAngleRad)).toFloat())
                    close()
                }
                drawPath(path = iceHead, brush = Brush.linearGradient(listOf(Color.White, Color(0xFF80DEEA), Color(0xFF00E5FF))))
            }

            // 20. ROYAL SCEPTRE (Imperial Velvet Crimson + 24K Gold Crowns & Ruby)
            ArrowTailStyle.ROYAL_SCEPTRE -> {
                drawLine(color = Color(0xFFB71C1C), start = Offset(pos.tailX, pos.tailY), end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 1.2f, cap = StrokeCap.Round)
                val steps = listOf(0.25f, 0.5f, 0.75f)
                for (step in steps) {
                    val rx = pos.tailX + (pos.tipX - pos.tailX) * step
                    val ry = pos.tailY + (pos.tipY - pos.tailY) * step
                    drawCircle(color = Color(0xFFFFD54F), radius = strokeWidthPx * 0.75f, center = Offset(rx, ry))
                    drawCircle(color = Color(0xFFFF1744), radius = strokeWidthPx * 0.35f, center = Offset(rx, ry))
                }
                // Crown Ruby Head
                val rHeadLen = strokeWidthPx * 2.6f
                val rHeadWid = strokeWidthPx * 1.5f
                val crownHead = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo((pos.tipX - rHeadLen * cos(angleRad) + rHeadWid * cos(perpAngleRad)).toFloat(), (pos.tipY - rHeadLen * sin(angleRad) + rHeadWid * sin(perpAngleRad)).toFloat())
                    lineTo((pos.tipX - rHeadLen * 0.6f * cos(angleRad)).toFloat(), (pos.tipY - rHeadLen * 0.6f * sin(angleRad)).toFloat())
                    lineTo((pos.tipX - rHeadLen * cos(angleRad) - rHeadWid * cos(perpAngleRad)).toFloat(), (pos.tipY - rHeadLen * sin(angleRad) - rHeadWid * sin(perpAngleRad)).toFloat())
                    close()
                }
                drawPath(path = crownHead, color = Color(0xFFFFD54F))
                drawCircle(color = Color(0xFFFF1744), radius = strokeWidthPx * 0.55f, center = Offset(pos.tipX, pos.tipY))
            }

            // 21. SHADOW ASSASSIN (Dual Ninja Kunai + Cord Wrap & Smoke)
            ArrowTailStyle.SHADOW_ASSASSIN -> {
                drawLine(color = Color(0xFF121212), start = Offset(pos.tailX, pos.tailY), end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 1.2f, cap = StrokeCap.Round)
                drawLine(color = Color(0xFF4A148C), start = Offset(pos.tailX, pos.tailY), end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 0.65f, cap = StrokeCap.Round)
                // Pommel Ring at Tail
                drawCircle(color = Color(0xFF78909C), radius = strokeWidthPx * 0.8f, center = Offset(pos.tailX, pos.tailY), style = Stroke(width = strokeWidthPx * 0.3f))
                
                // Diamond Kunai Head
                val kLen = strokeWidthPx * 2.8f
                val kWid = strokeWidthPx * 1.4f
                val kunai = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo((pos.tipX - kLen * 0.5f * cos(angleRad) + kWid * cos(perpAngleRad)).toFloat(), (pos.tipY - kLen * 0.5f * sin(angleRad) + kWid * sin(perpAngleRad)).toFloat())
                    lineTo((pos.tipX - kLen * cos(angleRad)).toFloat(), (pos.tipY - kLen * sin(angleRad)).toFloat())
                    lineTo((pos.tipX - kLen * 0.5f * cos(angleRad) - kWid * cos(perpAngleRad)).toFloat(), (pos.tipY - kLen * 0.5f * sin(angleRad) - kWid * sin(perpAngleRad)).toFloat())
                    close()
                }
                drawPath(path = kunai, color = Color(0xFF263238))
                drawPath(path = kunai, color = Color(0xFFD500F9), style = Stroke(width = strokeWidthPx * 0.22f))
            }

            // 22. TOXIC PLAGUE (Biohazard Slime Tube + Hazard Stripes)
            ArrowTailStyle.TOXIC_PLAGUE -> {
                drawLine(color = Color(0xFF33691E), start = Offset(pos.tailX, pos.tailY), end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 1.35f, cap = StrokeCap.Round)
                drawLine(color = Color(0xFF76FF03), start = Offset(pos.tailX, pos.tailY), end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 0.75f, cap = StrokeCap.Round)
                // Slime bubblers
                val slimePts = listOf(0.35f, 0.7f)
                for (st in slimePts) {
                    val sx = pos.tailX + (pos.tipX - pos.tailX) * st + (strokeWidthPx * 0.65f * cos(perpAngleRad)).toFloat()
                    val sy = pos.tailY + (pos.tipY - pos.tailY) * st + (strokeWidthPx * 0.65f * sin(perpAngleRad)).toFloat()
                    drawCircle(color = Color(0xFFAEEA00), radius = strokeWidthPx * 0.45f, center = Offset(sx, sy))
                }
                // Bio needle head
                val bLen = strokeWidthPx * 2.8f
                val bWid = strokeWidthPx * 1.3f
                val bHead = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo((pos.tipX - bLen * cos(angleRad) + bWid * cos(perpAngleRad)).toFloat(), (pos.tipY - bLen * sin(angleRad) + bWid * sin(perpAngleRad)).toFloat())
                    lineTo((pos.tipX - bLen * cos(angleRad) - bWid * cos(perpAngleRad)).toFloat(), (pos.tipY - bLen * sin(angleRad) - bWid * sin(perpAngleRad)).toFloat())
                    close()
                }
                drawPath(path = bHead, color = Color(0xFF64DD17))
            }

            // 23. VALKYRIE SPEAR (Norse Divine Winged Spear + Golden Runes)
            ArrowTailStyle.VALKYRIE_SPEAR -> {
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFCFD8DC), Color(0xFFFFD54F), Color(0xFFECEFF1)),
                        start = Offset(pos.tailX, pos.tailY),
                        end = Offset(pos.tipX, pos.tipY)
                    ),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 1.1f,
                    cap = StrokeCap.Round
                )
                // Divine wing crossguard
                val midX = pos.centerX
                val midY = pos.centerY
                val wingSpan = strokeWidthPx * 2.2f
                drawLine(
                    color = Color(0xFFFFD54F),
                    start = Offset((midX + wingSpan * cos(perpAngleRad)).toFloat(), (midY + wingSpan * sin(perpAngleRad)).toFloat()),
                    end = Offset((midX - wingSpan * cos(perpAngleRad)).toFloat(), (midY - wingSpan * sin(perpAngleRad)).toFloat()),
                    strokeWidth = strokeWidthPx * 0.6f,
                    cap = StrokeCap.Round
                )
                // Spearhead
                val vLen = strokeWidthPx * 3.2f
                val vWid = strokeWidthPx * 1.5f
                val vHead = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo((pos.tipX - vLen * 0.7f * cos(angleRad) + vWid * cos(perpAngleRad)).toFloat(), (pos.tipY - vLen * 0.7f * sin(angleRad) + vWid * sin(perpAngleRad)).toFloat())
                    lineTo((pos.tipX - vLen * cos(angleRad)).toFloat(), (pos.tipY - vLen * sin(angleRad)).toFloat())
                    lineTo((pos.tipX - vLen * 0.7f * cos(angleRad) - vWid * cos(perpAngleRad)).toFloat(), (pos.tipY - vLen * 0.7f * sin(angleRad) - vWid * sin(perpAngleRad)).toFloat())
                    close()
                }
                drawPath(path = vHead, color = Color(0xFFFFD54F))
                drawPath(path = vHead, color = Color.White, style = Stroke(width = strokeWidthPx * 0.28f))
            }

            // 24. MAGMA BURST (Volcanic Crust + Glowing Molten Fissures)
            ArrowTailStyle.MAGMA_BURST -> {
                drawLine(color = Color(0xFFBF360C), start = Offset(pos.tailX, pos.tailY), end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 1.5f, cap = StrokeCap.Round)
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFFF3D00), Color(0xFFFFD54F), Color(0xFFFF3D00)),
                        start = Offset(pos.tailX, pos.tailY),
                        end = Offset(pos.tipX, pos.tipY)
                    ),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 0.85f,
                    cap = StrokeCap.Round
                )
                // Magma burst head
                val mLen = strokeWidthPx * 2.8f
                val mWid = strokeWidthPx * 1.6f
                val mHead = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo((pos.tipX - mLen * cos(angleRad) + mWid * cos(perpAngleRad)).toFloat(), (pos.tipY - mLen * sin(angleRad) + mWid * sin(perpAngleRad)).toFloat())
                    lineTo((pos.tipX - mLen * 0.65f * cos(angleRad)).toFloat(), (pos.tipY - mLen * 0.65f * sin(angleRad)).toFloat())
                    lineTo((pos.tipX - mLen * cos(angleRad) - mWid * cos(perpAngleRad)).toFloat(), (pos.tipY - mLen * sin(angleRad) - mWid * sin(perpAngleRad)).toFloat())
                    close()
                }
                drawPath(path = mHead, color = Color(0xFFD84315))
                drawPath(path = mHead, color = Color(0xFFFFD54F), style = Stroke(width = strokeWidthPx * 0.28f))
            }

            // 25. CHRONO GEAR (Steampunk Brass Clockwork + Interlocking Cogs)
            ArrowTailStyle.CHRONO_GEAR -> {
                drawLine(color = Color(0xFF5D4037), start = Offset(pos.tailX, pos.tailY), end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 1.15f, cap = StrokeCap.Round)
                val cogs = listOf(0.33f, 0.66f)
                for (cg in cogs) {
                    val gx = pos.tailX + (pos.tipX - pos.tailX) * cg
                    val gy = pos.tailY + (pos.tipY - pos.tailY) * cg
                    drawCircle(color = Color(0xFFFFB300), radius = strokeWidthPx * 0.85f, center = Offset(gx, gy))
                    drawCircle(color = Color(0xFF3E2723), radius = strokeWidthPx * 0.35f, center = Offset(gx, gy))
                }
                // Clockhand pointer head
                val chLen = strokeWidthPx * 2.8f
                val chWid = strokeWidthPx * 1.3f
                val chHead = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo((pos.tipX - chLen * cos(angleRad) + chWid * cos(perpAngleRad)).toFloat(), (pos.tipY - chLen * sin(angleRad) + chWid * sin(perpAngleRad)).toFloat())
                    lineTo((pos.tipX - chLen * cos(angleRad) - chWid * cos(perpAngleRad)).toFloat(), (pos.tipY - chLen * sin(angleRad) - chWid * sin(perpAngleRad)).toFloat())
                    close()
                }
                drawPath(path = chHead, color = Color(0xFFFFB300))
            }

            // 26. BUBBLE AQUA (Ocean Torrent + Translucent 3D Bubbles)
            ArrowTailStyle.BUBBLE_AQUA -> {
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF0288D1), Color(0xFF00E5FF), Color(0xFFE1F5FE)),
                        start = Offset(pos.tailX, pos.tailY),
                        end = Offset(pos.tipX, pos.tipY)
                    ),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 1.25f,
                    cap = StrokeCap.Round
                )
                val bubbles = listOf(0.25f, 0.5f, 0.75f)
                for (b in bubbles) {
                    val bx = pos.tailX + (pos.tipX - pos.tailX) * b
                    val by = pos.tailY + (pos.tipY - pos.tailY) * b
                    drawCircle(color = Color.White.copy(alpha = 0.8f), radius = strokeWidthPx * 0.45f, center = Offset(bx, by), style = Stroke(width = strokeWidthPx * 0.18f))
                }
                val aqW1 = Offset((pos.tipX + headWingLengthPx * 0.9f * cos(angleRad + Math.PI - wingAngleRad)).toFloat(), (pos.tipY + headWingLengthPx * 0.9f * sin(angleRad + Math.PI - wingAngleRad)).toFloat())
                val aqW2 = Offset((pos.tipX + headWingLengthPx * 0.9f * cos(angleRad + Math.PI + wingAngleRad)).toFloat(), (pos.tipY + headWingLengthPx * 0.9f * sin(angleRad + Math.PI + wingAngleRad)).toFloat())
                drawLine(color = Color(0xFF00E5FF), start = aqW1, end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 0.9f, cap = StrokeCap.Round)
                drawLine(color = Color(0xFF00E5FF), start = aqW2, end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 0.9f, cap = StrokeCap.Round)
            }

            // 27. PIXEL RETRO (8-Bit Stepped Arcade Blocks)
            ArrowTailStyle.PIXEL_RETRO -> {
                val pxSize = strokeWidthPx * 0.95f
                val count = 10
                for (i in 0..count) {
                    val t = i.toFloat() / count
                    val bx = pos.tailX + (pos.tipX - pos.tailX) * t
                    val by = pos.tailY + (pos.tipY - pos.tailY) * t
                    drawRect(color = Color(0xFFEF6C00), topLeft = Offset(bx - pxSize / 2, by - pxSize / 2), size = Size(pxSize, pxSize))
                }
                // Pixelated chevron
                val pw1 = Offset((pos.tipX - pxSize * 2 * cos(angleRad) + pxSize * 2 * cos(perpAngleRad)).toFloat(), (pos.tipY - pxSize * 2 * sin(angleRad) + pxSize * 2 * sin(perpAngleRad)).toFloat())
                val pw2 = Offset((pos.tipX - pxSize * 2 * cos(angleRad) - pxSize * 2 * cos(perpAngleRad)).toFloat(), (pos.tipY - pxSize * 2 * sin(angleRad) - pxSize * 2 * sin(perpAngleRad)).toFloat())
                drawRect(color = Color(0xFFFF9100), topLeft = Offset(pw1.x - pxSize / 2, pw1.y - pxSize / 2), size = Size(pxSize, pxSize))
                drawRect(color = Color(0xFFFF9100), topLeft = Offset(pw2.x - pxSize / 2, pw2.y - pxSize / 2), size = Size(pxSize, pxSize))
                drawRect(color = Color(0xFFFFD54F), topLeft = Offset(pos.tipX - pxSize / 2, pos.tipY - pxSize / 2), size = Size(pxSize, pxSize))
            }

            // 28. PIRATE CUTLASS (Curved Naval Sabre + Basket Guard)
            ArrowTailStyle.PIRATE_CUTLASS -> {
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF78909C), Color(0xFFECEFF1), Color(0xFFCFD8DC)),
                        start = Offset(pos.tailX, pos.tailY),
                        end = Offset(pos.tipX, pos.tipY)
                    ),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY),
                    strokeWidth = strokeWidthPx * 1.15f,
                    cap = StrokeCap.Round
                )
                // Golden basket guard at tail
                val hiltX = pos.tailX + (pos.tipX - pos.tailX) * 0.18f
                val hiltY = pos.tailY + (pos.tipY - pos.tailY) * 0.18f
                drawCircle(color = Color(0xFFFFD54F), radius = strokeWidthPx * 1.4f, center = Offset(hiltX, hiltY), style = Stroke(width = strokeWidthPx * 0.35f))
                
                // Curved Cutlass Blade Tip
                val cLen = strokeWidthPx * 2.8f
                val cWid = strokeWidthPx * 1.5f
                val cutlassTip = Path().apply {
                    moveTo(pos.tipX, pos.tipY)
                    lineTo((pos.tipX - cLen * cos(angleRad) + cWid * cos(perpAngleRad)).toFloat(), (pos.tipY - cLen * sin(angleRad) + cWid * sin(perpAngleRad)).toFloat())
                    lineTo((pos.tipX - cLen * cos(angleRad)).toFloat(), (pos.tipY - cLen * sin(angleRad)).toFloat())
                    close()
                }
                drawPath(path = cutlassTip, color = Color(0xFFECEFF1))
            }

            // 29. ANGELIC WING (Holy Feather Shaft + Halo Rings + Divine Ray Head)
            ArrowTailStyle.ANGELIC_WING -> {
                drawLine(color = Color(0xFFFFFFFF), start = Offset(pos.tailX, pos.tailY), end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 1.1f, cap = StrokeCap.Round)
                val halos = listOf(0.35f, 0.7f)
                for (h in halos) {
                    val hx = pos.tailX + (pos.tipX - pos.tailX) * h
                    val hy = pos.tailY + (pos.tipY - pos.tailY) * h
                    drawCircle(color = Color(0xFFFFD54F).copy(alpha = 0.7f), radius = strokeWidthPx * 1.25f, center = Offset(hx, hy), style = Stroke(width = strokeWidthPx * 0.2f))
                }
                val anW1 = Offset((pos.tipX + headWingLengthPx * cos(angleRad + Math.PI - wingAngleRad)).toFloat(), (pos.tipY + headWingLengthPx * sin(angleRad + Math.PI - wingAngleRad)).toFloat())
                val anW2 = Offset((pos.tipX + headWingLengthPx * cos(angleRad + Math.PI + wingAngleRad)).toFloat(), (pos.tipY + headWingLengthPx * sin(angleRad + Math.PI + wingAngleRad)).toFloat())
                drawLine(color = Color(0xFFFFD54F), start = anW1, end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 0.95f, cap = StrokeCap.Round)
                drawLine(color = Color(0xFFFFD54F), start = anW2, end = Offset(pos.tipX, pos.tipY), strokeWidth = strokeWidthPx * 0.95f, cap = StrokeCap.Round)
            }
        }
    } // Closes "if (showArrow)"

    if (showDot) {
        // DRAW GLOWING TIP (THE CLICK TARGET!) USING SELECTED DOT SKIN
        val glowRadiusPx = with(density) { dotSkin.glowRadiusDp.dp.toPx() } * tipPulseScale
        val coreRadiusPx = with(density) { 8.dp.toPx() }
        val centerOffset = Offset(pos.tipX, pos.tipY)

        when (dotSkin.style) {
            com.example.model.DotStyle.CLASSIC_TARGET -> {
                drawCircle(color = dotSkin.glowColor.copy(alpha = 0.45f), radius = glowRadiusPx, center = centerOffset)
                drawCircle(color = dotSkin.glowColor.copy(alpha = 0.85f), radius = coreRadiusPx * 1.4f, center = centerOffset)
                drawCircle(color = dotSkin.centerColor, radius = coreRadiusPx, center = centerOffset)
            }
            com.example.model.DotStyle.CROSSHAIR -> {
                drawCircle(color = dotSkin.glowColor.copy(alpha = 0.35f), radius = glowRadiusPx, center = centerOffset)
                drawCircle(color = dotSkin.glowColor, radius = coreRadiusPx * 1.8f, center = centerOffset, style = Stroke(width = with(density) { 1.5.dp.toPx() }))
                val arm = coreRadiusPx * 2.5f
                drawLine(color = dotSkin.glowColor, start = Offset(pos.tipX - arm, pos.tipY), end = Offset(pos.tipX + arm, pos.tipY), strokeWidth = with(density) { 1.8.dp.toPx() })
                drawLine(color = dotSkin.glowColor, start = Offset(pos.tipX, pos.tipY - arm), end = Offset(pos.tipX, pos.tipY + arm), strokeWidth = with(density) { 1.8.dp.toPx() })
                drawCircle(color = dotSkin.centerColor, radius = coreRadiusPx * 0.6f, center = centerOffset)
            }
            com.example.model.DotStyle.PULSE_RINGS -> {
                drawCircle(color = dotSkin.glowColor.copy(alpha = 0.25f), radius = glowRadiusPx * 1.3f, center = centerOffset)
                drawCircle(color = dotSkin.glowColor.copy(alpha = 0.55f), radius = glowRadiusPx * 0.85f, center = centerOffset, style = Stroke(width = with(density) { 1.8.dp.toPx() }))
                drawCircle(color = dotSkin.centerColor, radius = coreRadiusPx, center = centerOffset)
            }
            com.example.model.DotStyle.ENERGY_ORB -> {
                drawCircle(color = dotSkin.glowColor.copy(alpha = 0.45f), radius = glowRadiusPx, center = centerOffset)
                drawCircle(color = dotSkin.accentColor.copy(alpha = 0.8f), radius = coreRadiusPx * 1.4f, center = centerOffset)
                drawCircle(color = dotSkin.centerColor, radius = coreRadiusPx * 0.9f, center = centerOffset)
            }
            com.example.model.DotStyle.STAR_CORE -> {
                drawCircle(color = dotSkin.glowColor.copy(alpha = 0.4f), radius = glowRadiusPx, center = centerOffset)
                val rayLength = coreRadiusPx * 3.2f * tipPulseScale
                drawLine(color = dotSkin.glowColor, start = Offset(pos.tipX - rayLength, pos.tipY), end = Offset(pos.tipX + rayLength, pos.tipY), strokeWidth = with(density) { 2.2.dp.toPx() }, cap = StrokeCap.Round)
                drawLine(color = dotSkin.glowColor, start = Offset(pos.tipX, pos.tipY - rayLength), end = Offset(pos.tipX, pos.tipY + rayLength), strokeWidth = with(density) { 2.2.dp.toPx() }, cap = StrokeCap.Round)
                drawCircle(color = dotSkin.centerColor, radius = coreRadiusPx * 1.1f, center = centerOffset)
            }
            com.example.model.DotStyle.DIAMOND_CRYSTAL -> {
                drawCircle(color = dotSkin.glowColor.copy(alpha = 0.35f), radius = glowRadiusPx, center = centerOffset)
                val diamondPath = Path().apply {
                    moveTo(pos.tipX, pos.tipY - coreRadiusPx * 1.8f)
                    lineTo(pos.tipX + coreRadiusPx * 1.5f, pos.tipY)
                    lineTo(pos.tipX, pos.tipY + coreRadiusPx * 1.8f)
                    lineTo(pos.tipX - coreRadiusPx * 1.5f, pos.tipY)
                    close()
                }
                drawPath(path = diamondPath, color = dotSkin.glowColor, style = Stroke(width = with(density) { 1.8.dp.toPx() }))
                drawCircle(color = dotSkin.centerColor, radius = coreRadiusPx * 0.7f, center = centerOffset)
            }
            com.example.model.DotStyle.BIOHAZARD -> {
                drawCircle(color = dotSkin.glowColor.copy(alpha = 0.3f), radius = glowRadiusPx, center = centerOffset)
                drawCircle(color = dotSkin.glowColor, radius = coreRadiusPx * 1.8f, center = centerOffset, style = Stroke(width = with(density) { 2.dp.toPx() }))
                drawCircle(color = dotSkin.centerColor, radius = coreRadiusPx * 0.9f, center = centerOffset)
            }
            com.example.model.DotStyle.CYBER_CHIP -> {
                drawCircle(color = dotSkin.glowColor.copy(alpha = 0.3f), radius = glowRadiusPx, center = centerOffset)
                drawRect(color = dotSkin.glowColor, topLeft = Offset(pos.tipX - coreRadiusPx * 1.2f, pos.tipY - coreRadiusPx * 1.2f), size = Size(coreRadiusPx * 2.4f, coreRadiusPx * 2.4f), style = Stroke(width = with(density) { 1.8.dp.toPx() }))
                drawCircle(color = dotSkin.centerColor, radius = coreRadiusPx * 0.6f, center = centerOffset)
            }
            com.example.model.DotStyle.BLACK_HOLE -> {
                drawCircle(brush = Brush.radialGradient(listOf(dotSkin.centerColor, dotSkin.glowColor, Color.Transparent), center = centerOffset, radius = glowRadiusPx * 1.3f), radius = glowRadiusPx * 1.3f, center = centerOffset)
                drawCircle(color = Color(0xFF111122), radius = coreRadiusPx * 1.1f, center = centerOffset)
                drawCircle(color = dotSkin.glowColor, radius = coreRadiusPx * 1.2f, center = centerOffset, style = Stroke(width = with(density) { 1.5.dp.toPx() }))
            }
            com.example.model.DotStyle.LOTUS_ZEN -> {
                drawCircle(color = dotSkin.glowColor.copy(alpha = 0.3f), radius = glowRadiusPx, center = centerOffset)
                for (i in 0 until 6) {
                    val ang = Math.toRadians((i * 60).toDouble())
                    val px = (pos.tipX + coreRadiusPx * 1.2f * cos(ang)).toFloat()
                    val py = (pos.tipY + coreRadiusPx * 1.2f * sin(ang)).toFloat()
                    drawCircle(color = dotSkin.glowColor, radius = coreRadiusPx * 0.5f, center = Offset(px, py))
                }
                drawCircle(color = dotSkin.centerColor, radius = coreRadiusPx * 0.8f, center = centerOffset)
            }
            com.example.model.DotStyle.HEART_PULSE -> {
                drawCircle(color = dotSkin.glowColor.copy(alpha = 0.35f), radius = glowRadiusPx, center = centerOffset)
                val heartPath = Path().apply {
                    moveTo(pos.tipX, pos.tipY + coreRadiusPx * 0.5f)
                    lineTo(pos.tipX - coreRadiusPx * 1.4f, pos.tipY - coreRadiusPx * 0.8f)
                    lineTo(pos.tipX - coreRadiusPx * 0.7f, pos.tipY - coreRadiusPx * 1.7f)
                    lineTo(pos.tipX, pos.tipY - coreRadiusPx * 0.6f)
                    lineTo(pos.tipX + coreRadiusPx * 0.7f, pos.tipY - coreRadiusPx * 1.7f)
                    lineTo(pos.tipX + coreRadiusPx * 1.4f, pos.tipY - coreRadiusPx * 0.8f)
                    close()
                }
                drawPath(path = heartPath, color = dotSkin.glowColor)
            }
            com.example.model.DotStyle.RADAR_SWEEP -> {
                drawCircle(color = dotSkin.glowColor.copy(alpha = 0.7f), radius = coreRadiusPx * 2.2f * tipPulseScale, center = centerOffset, style = Stroke(width = with(density) { 1.2.dp.toPx() }))
                val lineOffset = coreRadiusPx * 2.8f
                drawLine(color = dotSkin.glowColor, start = Offset(pos.tipX - lineOffset, pos.tipY), end = Offset(pos.tipX + lineOffset, pos.tipY), strokeWidth = with(density) { 1.dp.toPx() })
                drawLine(color = dotSkin.glowColor, start = Offset(pos.tipX, pos.tipY - lineOffset), end = Offset(pos.tipX, pos.tipY + lineOffset), strokeWidth = with(density) { 1.dp.toPx() })
                drawCircle(color = dotSkin.centerColor, radius = coreRadiusPx * 0.6f, center = centerOffset)
            }
            com.example.model.DotStyle.SUN_FLARE -> {
                drawCircle(color = dotSkin.glowColor.copy(alpha = 0.3f), radius = glowRadiusPx * 1.3f, center = centerOffset)
                drawCircle(color = dotSkin.centerColor, radius = coreRadiusPx * 1.3f * tipPulseScale, center = centerOffset)
            }
            com.example.model.DotStyle.MAGIC_RUNE -> {
                drawCircle(color = dotSkin.glowColor.copy(alpha = 0.35f), radius = glowRadiusPx, center = centerOffset)
                drawCircle(color = dotSkin.glowColor, radius = coreRadiusPx * 1.8f, center = centerOffset, style = Stroke(width = with(density) { 1.5.dp.toPx() }))
                drawCircle(color = dotSkin.centerColor, radius = coreRadiusPx * 0.8f, center = centerOffset)
            }
            com.example.model.DotStyle.SHIELD_AEGIS -> {
                drawCircle(color = dotSkin.glowColor.copy(alpha = 0.32f), radius = glowRadiusPx, center = centerOffset)
                val shieldPath = Path().apply {
                    moveTo(pos.tipX, pos.tipY - coreRadiusPx * 1.8f)
                    lineTo(pos.tipX + coreRadiusPx * 1.4f, pos.tipY - coreRadiusPx * 0.9f)
                    lineTo(pos.tipX + coreRadiusPx * 1.1f, pos.tipY + coreRadiusPx * 1.3f)
                    lineTo(pos.tipX, pos.tipY + coreRadiusPx * 2.1f)
                    lineTo(pos.tipX - coreRadiusPx * 1.1f, pos.tipY + coreRadiusPx * 1.3f)
                    lineTo(pos.tipX - coreRadiusPx * 1.4f, pos.tipY - coreRadiusPx * 0.9f)
                    close()
                }
                drawPath(path = shieldPath, color = dotSkin.glowColor)
                drawPath(path = shieldPath, color = Color.White, style = Stroke(width = with(density) { 1.2.dp.toPx() }))
            }
            com.example.model.DotStyle.SKULL_VIPER -> {
                drawCircle(color = dotSkin.glowColor.copy(alpha = 0.3f), radius = glowRadiusPx, center = centerOffset)
                drawCircle(color = dotSkin.centerColor, radius = coreRadiusPx * 1.2f, center = centerOffset)
                drawCircle(color = Color.White, radius = coreRadiusPx * 0.3f, center = Offset(pos.tipX - coreRadiusPx * 0.5f, pos.tipY - coreRadiusPx * 0.2f))
                drawCircle(color = Color.White, radius = coreRadiusPx * 0.3f, center = Offset(pos.tipX + coreRadiusPx * 0.5f, pos.tipY - coreRadiusPx * 0.2f))
            }
            com.example.model.DotStyle.YIN_YANG -> {
                val rad = coreRadiusPx * 1.8f
                drawCircle(color = Color.Black, radius = rad, center = centerOffset)
                drawCircle(color = Color.White, radius = rad, center = centerOffset, style = Stroke(width = with(density) { 1.5.dp.toPx() }))
                drawCircle(color = Color.White, radius = rad * 0.5f, center = Offset(pos.tipX, pos.tipY - rad * 0.5f))
                drawCircle(color = Color.Black, radius = rad * 0.5f, center = Offset(pos.tipX, pos.tipY + rad * 0.5f))
            }
            com.example.model.DotStyle.NEO_HEXAGON -> {
                val hexPath = Path()
                val radius = coreRadiusPx * 2.2f * tipPulseScale
                for (i in 0..5) {
                    val angle = Math.toRadians((i * 60).toDouble())
                    val hx = (pos.tipX + radius * cos(angle)).toFloat()
                    val hy = (pos.tipY + radius * sin(angle)).toFloat()
                    if (i == 0) hexPath.moveTo(hx, hy) else hexPath.lineTo(hx, hy)
                }
                hexPath.close()
                drawPath(path = hexPath, color = dotSkin.glowColor, style = Stroke(width = with(density) { 1.5.dp.toPx() }))
                drawCircle(color = dotSkin.centerColor, radius = coreRadiusPx, center = centerOffset)
            }
            com.example.model.DotStyle.EMERALD_GEM -> {
                drawCircle(color = dotSkin.glowColor.copy(alpha = 0.35f), radius = glowRadiusPx, center = centerOffset)
                drawCircle(color = dotSkin.centerColor, radius = coreRadiusPx * 1.2f, center = centerOffset)
                drawCircle(color = Color.White.copy(alpha = 0.6f), radius = coreRadiusPx * 0.4f, center = Offset(pos.tipX - coreRadiusPx * 0.4f, pos.tipY - coreRadiusPx * 0.4f))
            }
            com.example.model.DotStyle.SUPERNOVA_BLAST -> {
                drawCircle(brush = Brush.sweepGradient(listOf(Color(0xFFE040FB), Color(0xFF00E5FF), Color(0xFFFFEA00), Color(0xFFE040FB)), center = centerOffset), radius = coreRadiusPx * 2.2f, center = centerOffset)
                drawCircle(color = Color.White.copy(alpha = 0.7f), radius = coreRadiusPx * 1.1f, center = centerOffset)
            }
        }
    } // Closes "if (showDot)"
}
