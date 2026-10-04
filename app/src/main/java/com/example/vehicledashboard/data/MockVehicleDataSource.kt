package com.example.vehicledashboard.data

import com.example.vehicledashboard.domain.model.Vehicle
import kotlinx.coroutines.delay

class MockVehicleDataSource {

    private var refreshCount = 0

    suspend fun fetchVehicles(): List<Vehicle> {
        // Simulates the delay we would normally get from a REST API call.
        delay(800)

        refreshCount++

        // Every fifth request fails so the app's error handling can be tested.
        if (refreshCount % 5 == 0) {
            throw IllegalStateException("Unable to reach server")
        }

        val currentTime = System.currentTimeMillis()

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
                lastUpdatedMillis = currentTime
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
                lastUpdatedMillis = currentTime
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

                // An offline vehicle keeps its older last-known update time.
                lastUpdatedMillis = currentTime - (60 * 60 * 1000L)
            )
        )
    }
}