package com.example.vehicledashboard.data

import com.example.vehicledashboard.domain.model.Vehicle
import com.example.vehicledashboard.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class VehicleRepositoryImpl(
    private val dataSource: MockVehicleDataSource
) : VehicleRepository {

    private val vehicles = MutableStateFlow<List<Vehicle>>(emptyList())

    override fun observeVehicles(): Flow<List<Vehicle>> {
        return vehicles.asStateFlow()
    }

    override suspend fun refreshVehicles() {
        // Only replace the current data after a successful response.
        val latestVehicles = dataSource.fetchVehicles()
        vehicles.value = latestVehicles
    }

    override suspend fun getVehicleById(vehicleId: String): Vehicle? {
        return vehicles.value.find { vehicle ->
            vehicle.id == vehicleId
        }
    }
}