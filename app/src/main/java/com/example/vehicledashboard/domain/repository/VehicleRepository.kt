package com.example.vehicledashboard.domain.repository

import com.example.vehicledashboard.domain.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {

    // Emits the latest vehicle list whenever data changes.
    fun observeVehicles(): Flow<List<Vehicle>>

    // Requests fresh vehicle data from the data source.
    suspend fun refreshVehicles()

    // Returns one vehicle by its unique id.
    suspend fun getVehicleById(vehicleId: String): Vehicle?
}