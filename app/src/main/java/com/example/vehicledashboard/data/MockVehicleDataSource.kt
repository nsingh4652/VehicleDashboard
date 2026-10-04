package com.example.vehicledashboard.data

import com.example.vehicledashboard.domain.model.Vehicle
import kotlinx.coroutines.delay

class MockVehicleDataSource {

    private var refreshCount = 0

    suspend fun fetchVehicles(): List<Vehicle> {
        // Simulate a small network delay.
        delay(800)

        refreshCount++

        // Simulate an occasional backend failure.
        // This helps us verify the error state in the UI.
        if (refreshCount % 5 == 0) {
            throw IllegalStateException("Unable to reach server")
        }

        return listOf(
            Vehicle(
                id = "vehicle_1",
                name = "Simple One",
                model = "Gen 2",
                batteryPercentage = 86,
                estimatedRangeKm = 181,
                currentSpeedKmph = 42,
                odometerKm = 1248,
                isOnline = true,
                lastUpdated = "Just now"
            ),
            Vehicle(
                id = "vehicle_2",
                name = "Simple Dot One",
                model = "Standard",
                batteryPercentage = 64,
                estimatedRangeKm = 116,
                currentSpeedKmph = 28,
                odometerKm = 2384,
                isOnline = true,
                lastUpdated = "2 minutes ago"
            ),
            Vehicle(
                id = "vehicle_3",
                name = "Simple One",
                model = "Long Range",
                batteryPercentage = 31,
                estimatedRangeKm = 67,
                currentSpeedKmph = 0,
                odometerKm = 3210,
                isOnline = false,
                lastUpdated = "1 hour ago"
            )
        )
    }
}