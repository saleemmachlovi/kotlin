package com.example.navigation.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun PageOne(navController: NavHostController){
    Scaffold {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            val userFirstName = remember { mutableStateOf("") }
            Text(text = "Page 1", modifier = Modifier.padding(it))
            OutlinedTextField(
                value = userFirstName.value,
                onValueChange = {newValue -> userFirstName.value = newValue},
                label = { Text("Enter your First Name...")}
            )
            Button(onClick =  {
                val firstName = userFirstName.value
                navController.navigate("page2/$firstName")}) {
                Text(text = "Go to Page 2")
            }
        }
    }
}
