package com.example.vehicledashboard.core

import com.example.vehicledashboard.data.MockVehicleDataSource
import com.example.vehicledashboard.data.VehicleRepositoryImpl
import com.example.vehicledashboard.domain.repository.VehicleRepository
import com.example.vehicledashboard.domain.usecase.ObserveVehiclesUseCase
import com.example.vehicledashboard.domain.usecase.RefreshVehiclesUseCase

class AppContainer {

    // One data source instance is shared by the repository.
    private val dataSource = MockVehicleDataSource()

    // Both screens will use the same repository instance.
    val vehicleRepository: VehicleRepository =
        VehicleRepositoryImpl(dataSource)

    val observeVehiclesUseCase =
        ObserveVehiclesUseCase(vehicleRepository)

    val refreshVehiclesUseCase =
        RefreshVehiclesUseCase(vehicleRepository)
}