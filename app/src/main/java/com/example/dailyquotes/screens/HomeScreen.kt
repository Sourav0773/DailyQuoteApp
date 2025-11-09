package com.example.dailyquotes.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    // ✅ Quotes
    val quotes = listOf(
        "Believe you can and you're halfway there.",
        "Dream big and dare to fail.",
        "It always seems impossible until it's done.",
        "Success is not final, failure is not fatal.",
        "The best time to start was yesterday. The next best time is today."
    )

    var currentQuote by remember { mutableStateOf(quotes.random()) }

    // ✅ Drawer
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // ✅ Background Gradient
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF141E30),
            Color(0xFF243B55)
        )
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxHeight(),
                drawerContainerColor = Color(0xFF101820)
            ) {
                Text(
                    "Menu",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    modifier = Modifier.padding(20.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Home", color = Color.White) },
                    selected = false,
                    onClick = {}
                )

                NavigationDrawerItem(
                    label = { Text("My Quotes", color = Color.White) },
                    selected = false,
                    onClick = {
                        // navController.navigate("my_quotes")
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Profile", color = Color.White) },
                    selected = false,
                    onClick = {
                        // navController.navigate("profile")
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Logout", color = Color.White) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                )
            }
        }
    ) {

        // ✅ Scaffold with transparent TopBar
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Daily Quotes",
                            color = Color.White
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                        }
                    },
                    actions = {
                        IconButton(onClick = { currentQuote = quotes.random() }) {
                            Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = Color.White)
                        }
                        IconButton(onClick = { /* navController.navigate("profile") */ }) {
                            Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
                        }
                    }
                )
            },

            bottomBar = {
                NavigationBar(
                    containerColor = Color(0xFF0F1A27)
                ) {
                    NavigationBarItem(
                        selected = false,
                        onClick = {},
                        icon = { Icon(Icons.Default.Home, null, tint = Color.White) },
                        label = { Text("Home", color = Color.White) }
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = { /* navController.navigate("my_quotes") */ },
                        icon = { Icon(Icons.Default.Favorite, null, tint = Color.White) },
                        label = { Text("My Quotes", color = Color.White) }
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = { /* navController.navigate("profile") */ },
                        icon = { Icon(Icons.Default.Person, null, tint = Color.White) },
                        label = { Text("Profile", color = Color.White) }
                    )
                }
            }
        ) { innerPadding ->

            // ✅ Background Layer
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundBrush)
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // ✅ Glowing Quote Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()

                            .blur(2.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0x33FFFFFF)
                        )
                    ) {

                        Box(
                            modifier = Modifier
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {

                            AnimatedContent(
                                targetState = currentQuote,
                                transitionSpec = {
                                    fadeIn(tween(600)) togetherWith fadeOut(tween(600))
                                }
                            ) { quote ->
                                Text(
                                    text = quote,
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    // ✅ Button with glow effect
                    Button(
                        onClick = { currentQuote = quotes.random() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6C63FF)
                        ),
                        modifier = Modifier
                            .shadow(12.dp, shape = RoundedCornerShape(20.dp))
                    ) {
                        Text("New Quote", color = Color.White)
                    }
                }
            }
        }
    }
}
