package com.example.vehicledashboard.domain.model

/**
 * Main vehicle model used by the app.
 *
 * This stays in the domain layer so the rest of the app does not depend
 * directly on the API or UI representation.
 */
data class Vehicle(
    val id: String,
    val name: String,
    val model: String,
    val batteryPercentage: Int,
    val estimatedRangeKm: Int,
    val currentSpeedKmph: Int,
    val odometerKm: Int,
    val isOnline: Boolean,
    val lastUpdatedMillis: Long
)