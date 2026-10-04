package com.example.vehicledashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vehicledashboard.presentation.navigation.VehicleNavigation
import com.example.vehicledashboard.ui.theme.VehicleDashboardTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            VehicleDashboardTheme {
                VehicleNavigation()
            }
        }
    }
}