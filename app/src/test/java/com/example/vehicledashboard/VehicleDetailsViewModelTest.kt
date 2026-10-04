package com.example.vehicledashboard

import com.example.vehicledashboard.domain.model.Vehicle
import com.example.vehicledashboard.domain.repository.VehicleRepository
import com.example.vehicledashboard.domain.usecase.ObserveVehiclesUseCase
import com.example.vehicledashboard.domain.usecase.RefreshVehiclesUseCase
import com.example.vehicledashboard.presentation.vehicledetails.VehicleDetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VehicleDetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `correct vehicle is selected using vehicle id`() =
        runTest(testDispatcher) {

            val repository = FakeVehicleRepository()

            val viewModel = VehicleDetailsViewModel(
                vehicleId = "vehicle_2",
                observeVehiclesUseCase =
                    ObserveVehiclesUseCase(repository),
                refreshVehiclesUseCase =
                    RefreshVehiclesUseCase(repository)
            )

            advanceUntilIdle()

            val state = viewModel.uiState.value

            assertFalse(state.isLoading)
            assertEquals(
                "Simple Dot One",
                state.vehicle?.name
            )
            assertEquals(
                "Standard",
                state.vehicle?.model
            )
        }

    private class FakeVehicleRepository : VehicleRepository {

        private val vehicles = MutableStateFlow(
            listOf(
                Vehicle(
                    id = "vehicle_1",
                    name = "Simple One",
                    model = "Gen 2",
                    batteryPercentage = 86,
                    estimatedRangeKm = 181,
                    currentSpeedKmph = 42,
                    odometerKm = 1248,
                    isOnline = true,
                    lastUpdatedMillis = 1L
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
                    lastUpdatedMillis = 1L
                )
            )
        )

        override fun observeVehicles(): Flow<List<Vehicle>> {
            return vehicles
        }

        override suspend fun refreshVehicles() {
            // No refresh behaviour needed for this test.
        }

        override suspend fun getVehicleById(
            vehicleId: String
        ): Vehicle? {
            return vehicles.value.find { vehicle ->
                vehicle.id == vehicleId
            }
        }
    }
}