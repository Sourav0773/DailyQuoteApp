package com.example.dailyquotes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavHostController) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // ✅ Same gradient as login
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0F0C29),
            Color(0xFF302B63),
            Color(0xFF24243E)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {

        // ✅ GLASSMORPHISM CARD
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .graphicsLayer {
                    alpha = 0.90f        // soft transparency
                    shadowElevation = 18f
                    shape = RoundedCornerShape(30.dp)
                    clip = true
                },
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.10f) // frosted
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {

            Column(
                modifier = Modifier.padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // ✅ Premium Title
                Text(
                    "Create Account",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    "Start your journey of daily inspiration",
                    fontSize = 15.sp,
                    color = Color(0xFFB8B8FF)
                )

                Spacer(Modifier.height(25.dp))

                // ✅ Name
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF8576FF),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        cursorColor = Color.White
                    )
                )

                Spacer(Modifier.height(12.dp))

                // ✅ Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF8576FF),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        cursorColor = Color.White
                    )
                )

                Spacer(Modifier.height(12.dp))

                // ✅ Password
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF8576FF),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        cursorColor = Color.White
                    )
                )

                Spacer(Modifier.height(12.dp))

                // ✅ Confirm Password
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                imageVector = if (confirmPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF8576FF),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        cursorColor = Color.White
                    )
                )

                Spacer(Modifier.height(12.dp))

                // ✅ Error Message
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color(0xFFFF6B6B),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // ✅ Neon Gradient Sign Up Button
                Button(
                    onClick = {
                        when {
                            name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() -> {
                                errorMessage = "All fields are required"
                            }
                            password != confirmPassword -> {
                                errorMessage = "Passwords do not match"
                            }
                            else -> {
                                errorMessage = ""
                                navController.navigate("login") {
                                    popUpTo("signup") { inclusive = true }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8576FF)
                    )
                ) {
                    Text(
                        "Sign Up",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(12.dp))

                TextButton(onClick = { navController.navigate("login") }) {
                    Text(
                        "Already have an account? Login",
                        color = Color(0xFFB8B8FF)
                    )
                }
            }
        }
    }
}
