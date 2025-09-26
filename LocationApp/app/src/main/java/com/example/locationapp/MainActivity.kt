package com.example.locationapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.locationapp.ui.theme.LocationAppTheme
import android.Manifest
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel : LocationViewModel = LocationViewModel()
            LocationAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Surface(modifier = Modifier.padding(it)) {
                        MyApp(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun MyApp(viewModel1: LocationViewModel) {
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    val viewModel = LocationViewModel()
    LocationApp(locationUtils, viewModel, context)
}

@Composable
fun LocationApp(
    locationUtils: LocationUtils,
    viewModel: LocationViewModel,
    context: Context
) {
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                // Permission granted
            } else {
                // Permission denied
                val rationalRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION // ✅ fixed duplicate coarse
                )

                if (rationalRequired) {
                    Toast.makeText(context, "Permission is required to show your location", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Permission is required, Please enable it in Settings", Toast.LENGTH_SHORT).show()
                }
            }
        }
    ) // <-- you forgot this closing parenthesis

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Location App")
        Spacer(modifier = Modifier.height(16.dp)) // <-- fixed the 16.dp syntax
        Text(text = "Location Not Available")

        Button(onClick = {
            if (locationUtils.hasLocationPermissionGranted(context)) {
                // Already has permission → get location here

            } else {
                // Request permission
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            }
        }) {
            Text("Get Location")
        }
    }
}