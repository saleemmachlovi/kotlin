package com.example.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.navigation.ui.theme.NavigationTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationTheme {
                Surface( modifier = Modifier
                    .padding()
                    .fillMaxSize()) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "firstScreen") {

        // First screen
        composable(route = "firstScreen") {
            FirstScreen(
                navigationToSecondScreen = { name,age ->
                    navController.navigate("secondScreen/$name/$age")
                }
            )
        }

        // Second screen with argument
        composable(route = "secondScreen/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            SecondScreen(
                navigationToFirstScreen = {
                    navController.navigate("firstScreen")
                },
                name = name
            )
        }
    }
}
