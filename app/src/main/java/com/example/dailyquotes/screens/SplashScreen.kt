package com.example.dailyquotes.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SplashScreen(navController: NavHostController) {

    // Fade + Scale Animation
    val scale = remember { Animatable(0f) }
    var showSubtitle by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {

        // Logo zoom-in animation
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(900, easing = OvershootInterpolator(2f))
        )

        delay(300)
        showSubtitle = true

        delay(1500)

        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    // Background Gradient
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF4A148C), // Deep Purple
            Color(0xFF7B1FA2),
            Color(0xFFCE93D8)  // Light lavender
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // Circle Logo Animation
            Box(
                modifier = Modifier
                    .scale(scale.value)
                    .size(120.dp)
                    .background(Color.White.copy(alpha = 0.95f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "DQ",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xFF7B1FA2)
                )
            }

            Spacer(Modifier.height(20.dp))

            // App Name fade-in
            AnimatedVisibility(
                visible = showSubtitle,
                enter = fadeIn(tween(600)),
            ) {
                Text(
                    "DailyQuotes",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(Modifier.height(10.dp))

            // Subtitle fade-in
            AnimatedVisibility(
                visible = showSubtitle,
                enter = fadeIn(tween(1000)),
            ) {
                Text(
                    "\"Inspiration Every Day\"",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

// Custom overshoot effect
fun OvershootInterpolator(tension: Float = 2f) = OvershootInterpolatorEasing(tension)

class OvershootInterpolatorEasing(private val tension: Float) : Easing {
    override fun transform(fraction: Float): Float {
        val t = fraction - 1.0f
        return t * t * ((tension + 1) * t + tension) + 1.0f
    }
}
