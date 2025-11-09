package com.example.dailyquotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.dailyquotes.navigation.AppNavGraph
import com.example.dailyquotes.ui.theme.DailyQuotesTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DailyQuotesTheme {
                val navController = rememberNavController()
                AppNavGraph(navController)
            }
        }
    }
}


