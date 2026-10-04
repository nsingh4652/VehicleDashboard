package com.example.vehicledashboard.domain.usecase

import com.example.vehicledashboard.domain.repository.VehicleRepository

class RefreshVehiclesUseCase(
    private val repository: VehicleRepository
) {

    suspend operator fun invoke() {
        repository.refreshVehicles()
    }
}