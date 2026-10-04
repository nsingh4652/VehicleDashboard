package com.example.vehicledashboard.domain.usecase

import com.example.vehicledashboard.domain.model.Vehicle
import com.example.vehicledashboard.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow

class ObserveVehiclesUseCase(
    private val repository: VehicleRepository
) {

    operator fun invoke(): Flow<List<Vehicle>> {
        return repository.observeVehicles()
    }
}