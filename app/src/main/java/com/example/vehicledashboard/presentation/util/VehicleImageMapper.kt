package com.example.vehicledashboard.presentation.util

import androidx.annotation.DrawableRes
import com.example.vehicledashboard.R
import com.example.vehicledashboard.domain.model.Vehicle

@DrawableRes
fun getVehicleImage(vehicle: Vehicle): Int {
    return when (vehicle.id) {
        "vehicle_1" -> R.drawable.simple1
        "vehicle_2" -> R.drawable.simple2
        "vehicle_3" -> R.drawable.simple3
        else -> R.drawable.simple1
    }
}