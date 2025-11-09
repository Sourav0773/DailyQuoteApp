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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navController: NavHostController) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.08f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    "Welcome Back",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Login to continue your daily inspiration",
                    color = Color(0xFFB8B8FF),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = Color.White)
                )

                Spacer(modifier = Modifier.height(15.dp))

                // ✅ Password field with eye-toggle
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = "Toggle Password Visibility",
                                tint = Color.White
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(25.dp))

                Button(
                    onClick = {
                        if (username == "test" && password == "1234") {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8576FF)
                    ),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text("Login", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))

                TextButton(
                    onClick = { navController.navigate("signup") }
                ) {
                    Text(
                        "Don't have an account? Sign up",
                        color = Color(0xFFB8B8FF)
                    )
                }
            }
        }
    }
}
