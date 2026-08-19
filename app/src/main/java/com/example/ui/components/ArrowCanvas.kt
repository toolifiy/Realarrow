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
                    // Safe margins: prevent overlaps with top timer and bottom text
                    val minX = halfL + with(density) { 32.dp.toPx() }
                    val maxX = widthPx - minX
                    cX = if (maxX > minX) Random.nextFloat() * (maxX - minX) + minX else widthPx / 2f

                    // Header/Timer space is roughly top 22% of screen height
                    // Bottom instruction space is roughly bottom 24% of screen height
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
                            // Any touch that is not on the active target tip is counted as a miss immediately!
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
        // --- REALISTIC ARCHER ARROW ---
        ArrowTailStyle.REAL_ARCHER_ARROW -> {
            // 1. Realistic cedar wood shaft
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

            // 2. Realistic Eagle Feather Fletching at Tail
            val fletchLen = strokeWidthPx * 3.2f
            val fletchWid = strokeWidthPx * 1.3f
            for (f in 0..2) {
                val fOffset = f * strokeWidthPx * 0.9f
                val fBaseX = (pos.tailX + (fOffset) * cos(angleRad)).toFloat()
                val fBaseY = (pos.tailY + (fOffset) * sin(angleRad)).toFloat()
                val fTip1 = Offset(
                    (fBaseX - fletchLen * 0.4f * cos(angleRad) + fletchWid * cos(perpAngleRad)).toFloat(),
                    (fBaseY - fletchLen * 0.4f * sin(angleRad) + fletchWid * sin(perpAngleRad)).toFloat()
                )
                val fTip2 = Offset(
                    (fBaseX - fletchLen * 0.4f * cos(angleRad) - fletchWid * cos(perpAngleRad)).toFloat(),
                    (fBaseY - fletchLen * 0.4f * sin(angleRad) - fletchWid * sin(perpAngleRad)).toFloat()
                )
                drawLine(color = Color(0xFFECEFF1), start = Offset(fBaseX, fBaseY), end = fTip1, strokeWidth = strokeWidthPx * 0.35f, cap = StrokeCap.Round)
                drawLine(color = Color(0xFFECEFF1), start = Offset(fBaseX, fBaseY), end = fTip2, strokeWidth = strokeWidthPx * 0.35f, cap = StrokeCap.Round)
            }

            // 3. Forged Steel Broadhead Arrowhead at Tip
            val headLen = strokeWidthPx * 2.8f
            val headWid = strokeWidthPx * 1.5f
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
            drawPath(path = arrowHead, color = Color(0xFFCFD8DC), style = Stroke(width = strokeWidthPx * 0.25f))
            
            // Red binding thread near head
            val bindX = (pos.tipX - headLen * 0.9f * cos(angleRad)).toFloat()
            val bindY = (pos.tipY - headLen * 0.9f * sin(angleRad)).toFloat()
            drawCircle(color = Color(0xFFFF1744), radius = strokeWidthPx * 0.45f, center = Offset(bindX, bindY))
        }

        // --- NATURAL BAMBOO STICK ---
        ArrowTailStyle.BAMBOO_STICK -> {
            // 1. Bamboo Main stalk
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF2E7D32), Color(0xFF43A047), Color(0xFF66BB6A), Color(0xFF2E7D32)),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY)
                ),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 1.3f,
                cap = StrokeCap.Round
            )

            // 2. Realistic Bamboo Knots & Nodes
            val nodeCount = 5
            for (n in 1..nodeCount) {
                val t = n.toFloat() / (nodeCount + 1)
                val nx = pos.tailX + (pos.tipX - pos.tailX) * t
                val ny = pos.tailY + (pos.tipY - pos.tailY) * t

                // Node Ring
                val nodeSpan = strokeWidthPx * 0.9f
                drawLine(
                    color = Color(0xFF1B5E20),
                    start = Offset((nx + nodeSpan * cos(perpAngleRad)).toFloat(), (ny + nodeSpan * sin(perpAngleRad)).toFloat()),
                    end = Offset((nx - nodeSpan * cos(perpAngleRad)).toFloat(), (ny - nodeSpan * sin(perpAngleRad)).toFloat()),
                    strokeWidth = strokeWidthPx * 0.4f,
                    cap = StrokeCap.Round
                )
                // Small bamboo leaf sprout
                val leafLen = strokeWidthPx * 1.6f
                val leafDir = if (n % 2 == 0) 1f else -1f
                val leafTip = Offset(
                    (nx + leafLen * cos(angleRad + leafDir * 0.6)).toFloat(),
                    (ny + leafLen * sin(angleRad + leafDir * 0.6)).toFloat()
                )
                drawLine(color = Color(0xFF81C784), start = Offset(nx, ny), end = leafTip, strokeWidth = strokeWidthPx * 0.25f, cap = StrokeCap.Round)
            }

            // 3. Sharp sliced bamboo point at tip
            val sharpLen = strokeWidthPx * 2.2f
            val bambooTip = Path().apply {
                moveTo(pos.tipX, pos.tipY)
                lineTo(
                    (pos.tipX - sharpLen * cos(angleRad) + strokeWidthPx * 0.65f * cos(perpAngleRad)).toFloat(),
                    (pos.tipY - sharpLen * sin(angleRad) + strokeWidthPx * 0.65f * sin(perpAngleRad)).toFloat()
                )
                lineTo(
                    (pos.tipX - sharpLen * 0.4f * cos(angleRad) - strokeWidthPx * 0.65f * cos(perpAngleRad)).toFloat(),
                    (pos.tipY - sharpLen * 0.4f * sin(angleRad) - strokeWidthPx * 0.65f * sin(perpAngleRad)).toFloat()
                )
                close()
            }
            drawPath(path = bambooTip, color = Color(0xFFC8E6C9))
        }

        // --- WOODEN BRANCH STICK ---
        ArrowTailStyle.WOODEN_BRANCH_STICK -> {
            // Rugged organic tree branch with slight bark textures
            drawLine(
                color = Color(0xFF3E2723),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 1.25f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = Color(0xFF6D4C41),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 0.75f,
                cap = StrokeCap.Round
            )

            // Small rustic side twigs
            val twigs = listOf(0.3f to 1f, 0.55f to -1f, 0.78f to 1f)
            for ((t, dir) in twigs) {
                val bx = pos.tailX + (pos.tipX - pos.tailX) * t
                val by = pos.tailY + (pos.tipY - pos.tailY) * t
                val twigLen = strokeWidthPx * 1.4f
                val twigEnd = Offset(
                    (bx + twigLen * cos(angleRad + dir * 0.7)).toFloat(),
                    (by + twigLen * sin(angleRad + dir * 0.7)).toFloat()
                )
                drawLine(color = Color(0xFF5D4037), start = Offset(bx, by), end = twigEnd, strokeWidth = strokeWidthPx * 0.35f, cap = StrokeCap.Round)
                // tiny green bud
                drawCircle(color = Color(0xFF7CB342), radius = strokeWidthPx * 0.18f, center = twigEnd)
            }

            // Sharpened carved wood point
            val pointLen = strokeWidthPx * 2.0f
            val stickPoint = Path().apply {
                moveTo(pos.tipX, pos.tipY)
                lineTo(
                    (pos.tipX - pointLen * cos(angleRad) + strokeWidthPx * 0.6f * cos(perpAngleRad)).toFloat(),
                    (pos.tipY - pointLen * sin(angleRad) + strokeWidthPx * 0.6f * sin(perpAngleRad)).toFloat()
                )
                lineTo(
                    (pos.tipX - pointLen * cos(angleRad) - strokeWidthPx * 0.6f * cos(perpAngleRad)).toFloat(),
                    (pos.tipY - pointLen * sin(angleRad) - strokeWidthPx * 0.6f * sin(perpAngleRad)).toFloat()
                )
                close()
            }
            drawPath(path = stickPoint, color = Color(0xFFFFCC80))
        }

        // --- WATER PIPE ---
        ArrowTailStyle.WATER_PIPE -> {
            // Metallic pipe cylinder
            drawLine(
                color = Color(0xFF37474F),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 1.4f,
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
                strokeWidth = strokeWidthPx * 0.9f,
                cap = StrokeCap.Square
            )

            // Pipe Joint Rings
            val pipeJoints = listOf(0.25f, 0.6f, 0.85f)
            for (jt in pipeJoints) {
                val jx = pos.tailX + (pos.tipX - pos.tailX) * jt
                val jy = pos.tailY + (pos.tipY - pos.tailY) * jt
                val ringSpan = strokeWidthPx * 0.95f
                drawLine(
                    color = Color(0xFFFFB300), // Brass connector
                    start = Offset((jx + ringSpan * cos(perpAngleRad)).toFloat(), (jy + ringSpan * sin(perpAngleRad)).toFloat()),
                    end = Offset((jx - ringSpan * cos(perpAngleRad)).toFloat(), (jy - ringSpan * sin(perpAngleRad)).toFloat()),
                    strokeWidth = strokeWidthPx * 0.5f,
                    cap = StrokeCap.Round
                )
            }

            // High pressure water jet burst at nozzle
            val jetLen = strokeWidthPx * 2.2f
            val jetWid = strokeWidthPx * 1.2f
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
                    colors = listOf(Color(0xFFE0F7FA), Color(0xFF00E5FF), Color(0x0000B0FF)),
                    center = Offset(pos.tipX, pos.tipY)
                )
            )
        }

        // 1. REALISTIC SNAKE
        ArrowTailStyle.SNAKE_REALISTIC -> {
            val segments = 32
            val path = Path()
            val waveAmp = strokeWidthPx * 0.9f
            val waveFreq = 3.2

            var prevPt: Offset? = null
            for (i in 0..segments) {
                val t = i.toFloat() / segments
                val baseX = pos.tailX + (pos.tipX - pos.tailX) * t
                val baseY = pos.tailY + (pos.tipY - pos.tailY) * t
                val waveOffset = (sin(t * Math.PI * 2.0 * waveFreq) * waveAmp * (1f - t * 0.3f)).toFloat()
                val px = (baseX + waveOffset * cos(perpAngleRad)).toFloat()
                val py = (baseY + waveOffset * sin(perpAngleRad)).toFloat()

                if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
                prevPt = Offset(px, py)
            }

            drawPath(
                path = path,
                color = Color(0xFF1B5E20),
                style = Stroke(width = strokeWidthPx * 1.3f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
            drawPath(
                path = path,
                color = Color(0xFF4CAF50),
                style = Stroke(width = strokeWidthPx * 0.85f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
            for (i in 2 until segments step 2) {
                val t = i.toFloat() / segments
                val baseX = pos.tailX + (pos.tipX - pos.tailX) * t
                val baseY = pos.tailY + (pos.tipY - pos.tailY) * t
                val waveOffset = (sin(t * Math.PI * 2.0 * waveFreq) * waveAmp * (1f - t * 0.3f)).toFloat()
                val px = (baseX + waveOffset * cos(perpAngleRad)).toFloat()
                val py = (baseY + waveOffset * sin(perpAngleRad)).toFloat()

                drawCircle(
                    color = Color(0xFFFFD54F),
                    radius = strokeWidthPx * 0.22f,
                    center = Offset(px, py)
                )
            }

            // Snake Viper Head
            val headLen = strokeWidthPx * 2.2f
            val headWid = strokeWidthPx * 1.5f
            val hLeft = Offset(
                (pos.tipX - headLen * 0.6f * cos(angleRad) + headWid * 0.6f * cos(perpAngleRad)).toFloat(),
                (pos.tipY - headLen * 0.6f * sin(angleRad) + headWid * 0.6f * sin(perpAngleRad)).toFloat()
            )
            val hRight = Offset(
                (pos.tipX - headLen * 0.6f * cos(angleRad) - headWid * 0.6f * cos(perpAngleRad)).toFloat(),
                (pos.tipY - headLen * 0.6f * sin(angleRad) - headWid * 0.6f * sin(perpAngleRad)).toFloat()
            )
            val hBack = Offset(
                (pos.tipX - headLen * cos(angleRad)).toFloat(),
                (pos.tipY - headLen * sin(angleRad)).toFloat()
            )
            val viperHead = Path().apply {
                moveTo(pos.tipX, pos.tipY)
                lineTo(hLeft.x, hLeft.y)
                lineTo(hBack.x, hBack.y)
                lineTo(hRight.x, hRight.y)
                close()
            }
            drawPath(path = viperHead, color = Color(0xFF2E7D32))

            // Snake Eyes
            val eyeDist = strokeWidthPx * 0.35f
            val eyeLeft = Offset(
                (pos.tipX - headLen * 0.4f * cos(angleRad) + eyeDist * cos(perpAngleRad)).toFloat(),
                (pos.tipY - headLen * 0.4f * sin(angleRad) + eyeDist * sin(perpAngleRad)).toFloat()
            )
            val eyeRight = Offset(
                (pos.tipX - headLen * 0.4f * cos(angleRad) - eyeDist * cos(perpAngleRad)).toFloat(),
                (pos.tipY - headLen * 0.4f * sin(angleRad) - eyeDist * sin(perpAngleRad)).toFloat()
            )
            drawCircle(color = Color(0xFFFFEB3B), radius = strokeWidthPx * 0.16f, center = eyeLeft)
            drawCircle(color = Color(0xFFFFEB3B), radius = strokeWidthPx * 0.16f, center = eyeRight)
            drawCircle(color = Color(0xFF000000), radius = strokeWidthPx * 0.08f, center = eyeLeft)
            drawCircle(color = Color(0xFF000000), radius = strokeWidthPx * 0.08f, center = eyeRight)
        }

        // 2. RED TIP VECTOR BEAM
        ArrowTailStyle.RED_TIP_BEAM -> {
            drawLine(
                color = Color(0xFFE0E0E0),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 1.5f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = skin.strokeColor,
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round
            )
            val subTipX = (pos.tipX - (pos.tipX - pos.tailX) * 0.35f)
            val subTipY = (pos.tipY - (pos.tipY - pos.tailY) * 0.35f)
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Transparent, Color(0xFFFF1744)),
                    start = Offset(subTipX, subTipY),
                    end = Offset(pos.tipX, pos.tipY)
                ),
                start = Offset(subTipX, subTipY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 1.1f,
                cap = StrokeCap.Round
            )
        }

        // 3. LIGHTNING BOLT
        ArrowTailStyle.LIGHTNING_BOLT -> {
            val boltPath = Path()
            boltPath.moveTo(pos.tailX, pos.tailY)

            val segs = 6
            val dx = (pos.tipX - pos.tailX) / segs
            val dy = (pos.tipY - pos.tailY) / segs
            val zigzagAmp = strokeWidthPx * 1.4f

            for (i in 1 until segs) {
                val side = if (i % 2 == 0) 1f else -1f
                val targetX = pos.tailX + dx * i + (side * zigzagAmp * cos(perpAngleRad)).toFloat()
                val targetY = pos.tailY + dy * i + (side * zigzagAmp * sin(perpAngleRad)).toFloat()
                boltPath.lineTo(targetX, targetY)
            }
            boltPath.lineTo(pos.tipX, pos.tipY)

            drawPath(
                path = boltPath,
                color = Color(0xFFFFEA00).copy(alpha = 0.4f),
                style = Stroke(width = strokeWidthPx * 2.2f, cap = StrokeCap.Round, join = StrokeJoin.Miter)
            )
            drawPath(
                path = boltPath,
                color = Color(0xFFFFD600),
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round, join = StrokeJoin.Miter)
            )
            drawPath(
                path = boltPath,
                color = Color.White,
                style = Stroke(width = strokeWidthPx * 0.4f, cap = StrokeCap.Round, join = StrokeJoin.Miter)
            )
        }

        // 4. DRAGON KATANA
        ArrowTailStyle.DRAGON_KATANA -> {
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF37474F), Color(0xFFCFD8DC), Color(0xFFECEFF1)),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY)
                ),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 1.1f,
                cap = StrokeCap.Round
            )
            val tsubaX = pos.tailX + (pos.tipX - pos.tailX) * 0.25f
            val tsubaY = pos.tailY + (pos.tipY - pos.tailY) * 0.25f
            val guardW = strokeWidthPx * 2.4f
            val g1X = (tsubaX + guardW * 0.5f * cos(perpAngleRad)).toFloat()
            val g1Y = (tsubaY + guardW * 0.5f * sin(perpAngleRad)).toFloat()
            val g2X = (tsubaX - guardW * 0.5f * cos(perpAngleRad)).toFloat()
            val g2Y = (tsubaY - guardW * 0.5f * sin(perpAngleRad)).toFloat()
            drawLine(
                color = Color(0xFFFFB300),
                start = Offset(g1X, g1Y),
                end = Offset(g2X, g2Y),
                strokeWidth = strokeWidthPx * 0.65f,
                cap = StrokeCap.Round
            )
            val w1Angle = angleRad + Math.PI - Math.toRadians(25.0)
            val w1X = (pos.tipX + headWingLengthPx * 0.8f * cos(w1Angle)).toFloat()
            val w1Y = (pos.tipY + headWingLengthPx * 0.8f * sin(w1Angle)).toFloat()
            drawLine(
                color = Color(0xFFCFD8DC),
                start = Offset(w1X, w1Y),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 0.9f,
                cap = StrokeCap.Round
            )
        }

        // 5. RAINBOW HYPER
        ArrowTailStyle.RAINBOW_HYPER -> {
            val rainbowBrush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFFF1744),
                    Color(0xFFFF9100),
                    Color(0xFFFFEA00),
                    Color(0xFF00E676),
                    Color(0xFF00E5FF),
                    Color(0xFF7C4DFF)
                ),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY)
            )
            drawLine(
                brush = rainbowBrush,
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 1.3f,
                cap = StrokeCap.Round
            )
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 6. MECHA RAILGUN
        ArrowTailStyle.MECHA_RAILGUN -> {
            val railOffset = strokeWidthPx * 0.55f
            val r1StartX = (pos.tailX + railOffset * cos(perpAngleRad)).toFloat()
            val r1StartY = (pos.tailY + railOffset * sin(perpAngleRad)).toFloat()
            val r1EndX = (pos.tipX + railOffset * cos(perpAngleRad)).toFloat()
            val r1EndY = (pos.tipY + railOffset * sin(perpAngleRad)).toFloat()

            val r2StartX = (pos.tailX - railOffset * cos(perpAngleRad)).toFloat()
            val r2StartY = (pos.tailY - railOffset * sin(perpAngleRad)).toFloat()
            val r2EndX = (pos.tipX - railOffset * cos(perpAngleRad)).toFloat()
            val r2EndY = (pos.tipY - railOffset * sin(perpAngleRad)).toFloat()

            drawLine(
                color = Color(0xFF37474F),
                start = Offset(r1StartX, r1StartY),
                end = Offset(r1EndX, r1EndY),
                strokeWidth = strokeWidthPx * 0.5f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = Color(0xFF37474F),
                start = Offset(r2StartX, r2StartY),
                end = Offset(r2EndX, r2EndY),
                strokeWidth = strokeWidthPx * 0.5f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = Color(0xFF00E676),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 0.6f,
                cap = StrokeCap.Round
            )
            drawClassicHead(pos, skin, headWingLengthPx * 0.8f, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 7. NEON CYBER
        ArrowTailStyle.NEON_CYBER -> {
            drawLine(
                color = skin.tipGlowColor.copy(alpha = 0.35f),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 1.9f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = skin.strokeColor,
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round
            )
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 8. GOLDEN CHROME
        ArrowTailStyle.GOLDEN_CHROME -> {
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFFFD54F), Color(0xFFFF8F00), Color(0xFFFFE082)),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY)
                ),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round
            )
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 9. FIRE EMBER
        ArrowTailStyle.FIRE_EMBER -> {
            drawLine(
                color = Color(0xFFFFAB00).copy(alpha = 0.35f),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 1.8f,
                cap = StrokeCap.Round
            )
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFFF3D00), Color(0xFFFF9100), Color(0xFFFF1744)),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY)
                ),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round
            )
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 10. EMERALD CRYSTAL
        ArrowTailStyle.EMERALD_CRYSTAL -> {
            drawLine(
                color = Color(0xFF69F0AE).copy(alpha = 0.25f),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 1.6f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = Color(0xFF00E676),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round
            )
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 11. COSMIC STAR
        ArrowTailStyle.COSMIC_STAR -> {
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF7C4DFF), Color(0xFFE040FB), Color(0xFF651FFF)),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY)
                ),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round
            )
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 12. STEALTH OBSIDIAN
        ArrowTailStyle.STEALTH_OBSIDIAN -> {
            drawLine(
                color = Color(0xFF1E272C),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round
            )
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 13. CLASSIC_SOLID
        ArrowTailStyle.CLASSIC_SOLID -> {
            drawLine(
                color = skin.strokeColor,
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round
            )
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 14. ICE SPIKE
        ArrowTailStyle.ICE_SPIKE -> {
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFE0F7FA), Color(0xFF80DEEA), Color(0xFF00E5FF)),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY)
                ),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Square
            )
            // Frost spike shapes along the shaft
            val subX = pos.tailX + (pos.tipX - pos.tailX) * 0.5f
            val subY = pos.tailY + (pos.tipY - pos.tailY) * 0.5f
            val pX1 = (subX + strokeWidthPx * 1.5f * cos(perpAngleRad)).toFloat()
            val pY1 = (subY + strokeWidthPx * 1.5f * sin(perpAngleRad)).toFloat()
            val pX2 = (subX - strokeWidthPx * 1.5f * cos(perpAngleRad)).toFloat()
            val pY2 = (subY - strokeWidthPx * 1.5f * sin(perpAngleRad)).toFloat()
            drawLine(color = Color(0xFFE0F7FA), start = Offset(pX1, pY1), end = Offset(pX2, pY2), strokeWidth = strokeWidthPx * 0.4f)
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 15. ROYAL SCEPTRE
        ArrowTailStyle.ROYAL_SCEPTRE -> {
            drawLine(
                color = Color(0xFFB71C1C),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round
            )
            // Gold rings ornaments
            val steps = listOf(0.2f, 0.4f, 0.6f, 0.8f)
            for (step in steps) {
                val rx = pos.tailX + (pos.tipX - pos.tailX) * step
                val ry = pos.tailY + (pos.tipY - pos.tailY) * step
                drawCircle(color = Color(0xFFFFD54F), radius = strokeWidthPx * 0.7f, center = Offset(rx, ry))
            }
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 16. SHADOW ASSASSIN
        ArrowTailStyle.SHADOW_ASSASSIN -> {
            drawLine(
                color = Color(0xFF121212),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 1.2f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = Color(0xFF4A148C),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 0.6f,
                cap = StrokeCap.Round
            )
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 17. TOXIC PLAGUE
        ArrowTailStyle.TOXIC_PLAGUE -> {
            drawLine(
                color = Color(0xFF558B2F),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round
            )
            // Radioactive drips
            val steps = listOf(0.3f, 0.65f)
            for (step in steps) {
                val dx_ = pos.tailX + (pos.tipX - pos.tailX) * step + (strokeWidthPx * 0.6f * cos(perpAngleRad)).toFloat()
                val dy_ = pos.tailY + (pos.tipY - pos.tailY) * step + (strokeWidthPx * 0.6f * sin(perpAngleRad)).toFloat()
                drawCircle(color = Color(0xFFCCFF00), radius = strokeWidthPx * 0.45f, center = Offset(dx_, dy_))
            }
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 18. VALKYRIE SPEAR
        ArrowTailStyle.VALKYRIE_SPEAR -> {
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFCFD8DC), Color(0xFF90A4AE), Color(0xFFECEFF1)),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY)
                ),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 0.8f,
                cap = StrokeCap.Round
            )
            // Cyber wings outline
            drawClassicHead(pos, skin, headWingLengthPx * 1.1f, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 19. MAGMA BURST
        ArrowTailStyle.MAGMA_BURST -> {
            drawLine(
                color = Color(0xFFE64A19),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 1.4f,
                cap = StrokeCap.Round
            )
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFFF3D00), Color(0xFFFFC107)),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY)
                ),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 0.8f,
                cap = StrokeCap.Round
            )
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 20. CHRONO GEAR
        ArrowTailStyle.CHRONO_GEAR -> {
            drawLine(
                color = Color(0xFF5D4037),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 0.9f,
                cap = StrokeCap.Round
            )
            // Decorative brass cog centers
            val stepCenters = listOf(0.33f, 0.66f)
            for (st in stepCenters) {
                val gx = pos.tailX + (pos.tipX - pos.tailX) * st
                val gy = pos.tailY + (pos.tipY - pos.tailY) * st
                drawCircle(color = Color(0xFFFFB300), radius = strokeWidthPx * 0.55f, center = Offset(gx, gy))
                drawCircle(color = Color(0xFF3E2723), radius = strokeWidthPx * 0.22f, center = Offset(gx, gy))
            }
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 21. BUBBLE AQUA
        ArrowTailStyle.BUBBLE_AQUA -> {
            drawLine(
                color = Color(0xFF00ACC1),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 0.8f,
                cap = StrokeCap.Round
            )
            // Liquid bubbles floating along body
            val bubbly = listOf(0.2f, 0.45f, 0.7f)
            for (b in bubbly) {
                val bx = pos.tailX + (pos.tipX - pos.tailX) * b
                val by = pos.tailY + (pos.tipY - pos.tailY) * b
                drawCircle(color = Color(0xFFE0F7FA).copy(alpha = 0.75f), radius = strokeWidthPx * 0.4f, center = Offset(bx, by))
            }
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 22. CANDY CANE (Realistic Peppermint Stick)
        ArrowTailStyle.CANDY_CANE -> {
            // White sugary cane base
            drawLine(
                color = Color.White,
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx * 1.25f,
                cap = StrokeCap.Round
            )
            // Realistic diagonal peppermint red spiral bands
            val stripes = 14
            for (i in 0..stripes) {
                val t = i.toFloat() / stripes
                val sx = pos.tailX + (pos.tipX - pos.tailX) * t
                val sy = pos.tailY + (pos.tipY - pos.tailY) * t
                val stripeSpan = strokeWidthPx * 0.75f
                drawLine(
                    color = Color(0xFFD50000),
                    start = Offset((sx + stripeSpan * cos(perpAngleRad + 0.5)).toFloat(), (sy + stripeSpan * sin(perpAngleRad + 0.5)).toFloat()),
                    end = Offset((sx - stripeSpan * cos(perpAngleRad + 0.5)).toFloat(), (sy - stripeSpan * sin(perpAngleRad + 0.5)).toFloat()),
                    strokeWidth = strokeWidthPx * 0.42f,
                    cap = StrokeCap.Round
                )
            }
            // Sugary gloss highlight line
            drawLine(
                color = Color.White.copy(alpha = 0.8f),
                start = Offset((pos.tailX + strokeWidthPx * 0.25f * cos(perpAngleRad)).toFloat(), (pos.tailY + strokeWidthPx * 0.25f * sin(perpAngleRad)).toFloat()),
                end = Offset((pos.tipX + strokeWidthPx * 0.25f * cos(perpAngleRad)).toFloat(), (pos.tipY + strokeWidthPx * 0.25f * sin(perpAngleRad)).toFloat()),
                strokeWidth = strokeWidthPx * 0.22f,
                cap = StrokeCap.Round
            )
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 23. PIXEL RETRO
        ArrowTailStyle.PIXEL_RETRO -> {
            // Drawn as blocky steps
            val pxSize = strokeWidthPx * 0.9f
            val count = 10
            for (i in 0..count) {
                val t = i.toFloat() / count
                val bx = pos.tailX + (pos.tipX - pos.tailX) * t
                val by = pos.tailY + (pos.tipY - pos.tailY) * t
                drawRect(
                    color = Color(0xFFEF6C00),
                    topLeft = Offset(bx - pxSize / 2, by - pxSize / 2),
                    size = Size(pxSize, pxSize)
                )
            }
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 24. PIRATE CUTLASS
        ArrowTailStyle.PIRATE_CUTLASS -> {
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF90A4AE), Color(0xFFECEFF1)),
                    start = Offset(pos.tailX, pos.tailY),
                    end = Offset(pos.tipX, pos.tipY)
                ),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round
            )
            // Hilt Guard
            val hiltX = pos.tailX + (pos.tipX - pos.tailX) * 0.15f
            val hiltY = pos.tailY + (pos.tipY - pos.tailY) * 0.15f
            drawCircle(color = Color(0xFFFFD54F), radius = strokeWidthPx * 1.5f, center = Offset(hiltX, hiltY))
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }

        // 25. ANGELIC WING
        ArrowTailStyle.ANGELIC_WING -> {
            drawLine(
                color = Color(0xFFECEFF1),
                start = Offset(pos.tailX, pos.tailY),
                end = Offset(pos.tipX, pos.tipY),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round
            )
            // Tiny halo rings around shaft
            val halos = listOf(0.35f, 0.7f)
            for (h in halos) {
                val hx = pos.tailX + (pos.tipX - pos.tailX) * h
                val hy = pos.tailY + (pos.tipY - pos.tailY) * h
                drawCircle(
                    color = Color(0xFFFFD54F).copy(alpha = 0.5f),
                    radius = strokeWidthPx * 1.1f,
                    center = Offset(hx, hy),
                    style = Stroke(width = strokeWidthPx * 0.15f)
                )
            }
            drawClassicHead(pos, skin, headWingLengthPx, wingAngleRad, angleRad, strokeWidthPx)
        }
    }

    // Optional tail feathers/accents for unique skins
    if (skin.tailStyle == ArrowTailStyle.COSMIC_STAR || skin.tailStyle == ArrowTailStyle.STEALTH_OBSIDIAN) {
        val tailWingLen = headWingLengthPx * 0.55f
        val tailW1X = (pos.tailX + tailWingLen * cos(angleRad + wingAngleRad)).toFloat()
        val tailW1Y = (pos.tailY + tailWingLen * sin(angleRad + wingAngleRad)).toFloat()
        val tailW2X = (pos.tailX + tailWingLen * cos(angleRad - wingAngleRad)).toFloat()
        val tailW2Y = (pos.tailY + tailWingLen * sin(angleRad - wingAngleRad)).toFloat()

        drawLine(
            color = skin.strokeColor,
            start = Offset(pos.tailX, pos.tailY),
            end = Offset(tailW1X, tailW1Y),
            strokeWidth = strokeWidthPx * 0.75f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = skin.strokeColor,
            start = Offset(pos.tailX, pos.tailY),
            end = Offset(tailW2X, tailW2Y),
            strokeWidth = strokeWidthPx * 0.75f,
            cap = StrokeCap.Round
        )
    }
    } // Closes "if (showArrow)"

    if (showDot) {
        // DRAW GLOWING TIP (THE CLICK TARGET!) USING SELECTED DOT SKIN
        val glowRadiusPx = with(density) { dotSkin.glowRadiusDp.dp.toPx() } * tipPulseScale
    val coreRadiusPx = with(density) { 8.dp.toPx() }
    val centerOffset = Offset(pos.tipX, pos.tipY)

    when (dotSkin.style) {
        com.example.model.DotStyle.CLASSIC_TARGET -> {
            drawCircle(
                color = dotSkin.glowColor.copy(alpha = 0.45f),
                radius = glowRadiusPx,
                center = centerOffset
            )
            drawCircle(
                color = dotSkin.glowColor.copy(alpha = 0.85f),
                radius = coreRadiusPx * 1.4f,
                center = centerOffset
            )
            drawCircle(
                color = dotSkin.centerColor,
                radius = coreRadiusPx,
                center = centerOffset
            )
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
            drawRect(color = dotSkin.glowColor, topLeft = Offset(pos.tipX - coreRadiusPx * 1.2f, pos.tipY - coreRadiusPx * 1.2f), size = androidx.compose.ui.geometry.Size(coreRadiusPx * 2.4f, coreRadiusPx * 2.4f), style = Stroke(width = with(density) { 1.8.dp.toPx() }))
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

private fun DrawScope.drawClassicHead(
    pos: ArrowPosition,
    skin: ArrowSkin,
    headWingLengthPx: Float,
    wingAngleRad: Double,
    angleRad: Double,
    strokeWidthPx: Float
) {
    if (headWingLengthPx <= 0) return
    val wing1Angle = angleRad + Math.PI - wingAngleRad
    val wing2Angle = angleRad + Math.PI + wingAngleRad

    val w1X = (pos.tipX + headWingLengthPx * cos(wing1Angle)).toFloat()
    val w1Y = (pos.tipY + headWingLengthPx * sin(wing1Angle)).toFloat()
    val w2X = (pos.tipX + headWingLengthPx * cos(wing2Angle)).toFloat()
    val w2Y = (pos.tipY + headWingLengthPx * sin(wing2Angle)).toFloat()

    val headPath = Path().apply {
        moveTo(w1X, w1Y)
        lineTo(pos.tipX, pos.tipY)
        lineTo(w2X, w2Y)
    }

    if (skin.tailStyle == ArrowTailStyle.NEON_CYBER) {
        drawPath(
            path = headPath,
            color = skin.tipGlowColor.copy(alpha = 0.35f),
            style = Stroke(
                width = strokeWidthPx * 1.9f,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }

    drawPath(
        path = headPath,
        color = skin.strokeColor,
        style = Stroke(
            width = strokeWidthPx,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}
