// MainActivity.kt
package com.example.dailyquote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.material3.Text

/**
 * Single-file Jetpack Compose splash screen for "DailyQuote+"
 *
 * - Gradient background (warm yellow / orange)
 * - Center rounded square "quote" icon with drop shadow
 * - App name "DailyQuote+" and tagline "Inspirational quotes"
 * - Entrance animation + subtle pulse on icon
 *
 * Make sure your project has Compose (material3 optional). This file requires no external assets.
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = Color.Transparent) {
                DailyQuoteSplash()
            }
        }
    }
}

@Composable
fun DailyQuoteSplash(modifier: Modifier = Modifier.fillMaxSize()) {
    // Colors for the warm gradient
    val gradientColors = listOf(
        Color(0xFFFFC857), // light warm yellow
        Color(0xFFFF9F1C)  // deep orange
    )

    // Entrance animation: fade + slide in + icon pop
    val transition = rememberInfiniteTransition()
    val pulse by transition.animateFloat(
        initialValue = 0.985f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        )
    )

    // For a one-shot entrance we use LaunchedEffect with Animatable
    val scaleAnim = remember { Animatable(0.88f) }
    val alphaAnim = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        // entrance: scale & fade
        scaleAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 650, easing = OvershootInterpolatorEasing(2.5f))
        )
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 450)
        )
    }

    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = gradientColors,
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            )
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scaleAnim.value * pulse
                    scaleY = scaleAnim.value * pulse
                    alpha = alphaAnim.value
                }
        ) {
            QuoteIconTile(size = 140.dp, elevation = 16.dp, modifier = Modifier.padding(bottom = 36.dp))

            Text(
                text = "DailyQuote+",
                color = Color.White,
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Text(
                text = "Inspirational quotes",
                color = Color.White.copy(alpha = 0.92f),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

/**
 * Rounded square tile with quotation mark glyph.
 * Drawn via Canvas for precise control and crispness.
 */
@Composable
fun QuoteIconTile(
    size: Dp,
    elevation: Dp = 12.dp,
    modifier: Modifier = Modifier
) {
    val tileColor = Color(0xFFFFBD59) // slightly lighter tile than the background
    val innerQuoteColor = Color.White
    val cornerRadius = 20.dp

    val tileSizePx = with(LocalDensity.current) { size.toPx() }

    Box(
        modifier = modifier
            .size(size)
            // shadow provides a soft drop shadow
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = Color.Black.copy(alpha = 0.18f),
                spotColor = Color.Black.copy(alpha = 0.12f)
            )
            .background(color = tileColor, shape = RoundedCornerShape(cornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        // Draw the quotation marks using Canvas shapes for a modern stylized look
        Canvas(modifier = Modifier.fillMaxSize(0.64f)) {
            val w = size.width * 0.64f
            val h = size.height * 0.64f
            // We will draw two stylized single-quotes (like two commas) arranged as double quotes
            val radius = minOf(w, h) / 5f
            val leftCenter = Offset(x = size.width * 0.28f, y = size.height * 0.40f)
            val rightCenter = Offset(x = size.width * 0.58f, y = size.height * 0.40f)

            fun drawQuotePiece(center: Offset, flip: Boolean = false) {
                // main oval
                drawOval(
                    color = innerQuoteColor,
                    topLeft = Offset(center.x - radius * 0.9f, center.y - radius * 1.1f),
                    size = androidx.compose.ui.geometry.Size(radius * 1.8f, radius * 2.1f),
                )
                // cutout to create the tail / comma shape
                drawArc(
                    color = tileColor,
                    startAngle = if (flip) 180f else -40f,
                    sweepAngle = 210f,
                    useCenter = true,
                    topLeft = Offset(center.x - radius * 0.8f, center.y - radius * 0.15f),
                    size = androidx.compose.ui.geometry.Size(radius * 1.6f, radius * 1.6f)
                )
            }

            // scale centers relative to canvas size
            drawQuotePiece(leftCenter)
            drawQuotePiece(rightCenter, flip = true)
        }
    }
}

/** Easing helper: overshoot effect */
fun OvershootInterpolatorEasing(tension: Float) = Easing { fraction ->
    // Android OvershootInterpolator formula approximation
    val t = fraction - 1.0f
    val result = t * t * ((tension + 1f) * t + tension) + 1f
    result.toDouble()
}.let { androidx.compose.animation.core.Easing { it.toFloat() } } // adapt to Compose Easing

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
fun PreviewDailyQuoteSplash() {
    MaterialTheme {
        DailyQuoteSplash()
    }
}
