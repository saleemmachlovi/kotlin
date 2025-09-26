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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigation.Screen.PageOne
import com.example.navigation.Screen.PageThree
import com.example.navigation.Screen.PageTwo
import com.example.navigation.ui.theme.NavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationTheme {
                Surface(modifier = Modifier.fillMaxSize().padding()){
                        MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "page1"){
        composable("page1"){ PageOne(navController) }
        composable("page2/{firstName}"){backStackEntry ->
            val firstName = backStackEntry.arguments?.getString("firstName")
            PageTwo(navController,firstName) }
        composable("page3/{firstName}/{lastName}"){backStackEntry ->
            val firstName = backStackEntry.arguments?.getString("firstName")
            val lastName = backStackEntry.arguments?.getString("lastName")
            PageThree(navController,firstName,lastName)}
    }
}


