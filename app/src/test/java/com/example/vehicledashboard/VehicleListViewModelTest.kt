package com.example.vehicledashboard

import com.example.vehicledashboard.domain.model.Vehicle
import com.example.vehicledashboard.domain.repository.VehicleRepository
import com.example.vehicledashboard.domain.usecase.ObserveVehiclesUseCase
import com.example.vehicledashboard.domain.usecase.RefreshVehiclesUseCase
import com.example.vehicledashboard.presentation.vehiclelist.VehicleListViewModel
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
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VehicleListViewModelTest {

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
    fun `successful refresh exposes vehicle data`() = runTest(testDispatcher) {
        val repository = FakeVehicleRepository()

        val viewModel = VehicleListViewModel(
            observeVehiclesUseCase = ObserveVehiclesUseCase(repository),
            refreshVehiclesUseCase = RefreshVehiclesUseCase(repository)
        )

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertFalse(state.isLoading)
        assertFalse(state.isRefreshing)
        assertNull(state.errorMessage)

        assertEquals(1, state.vehicles.size)
        assertEquals("Simple One", state.vehicles.first().name)
        assertEquals(86, state.vehicles.first().batteryPercentage)
    }

    @Test
    fun `failed refresh exposes error message`() = runTest(testDispatcher) {
        val repository = FakeVehicleRepository(
            shouldFailOnRefresh = true
        )

        val viewModel = VehicleListViewModel(
            observeVehiclesUseCase = ObserveVehiclesUseCase(repository),
            refreshVehiclesUseCase = RefreshVehiclesUseCase(repository)
        )

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertFalse(state.isLoading)
        assertFalse(state.isRefreshing)
        assertTrue(state.vehicles.isEmpty())

        assertEquals(
            "Unable to load vehicles. Please try again.",
            state.errorMessage
        )
    }

    private class FakeVehicleRepository(
        private val shouldFailOnRefresh: Boolean = false
    ) : VehicleRepository {

        private val vehicles =
            MutableStateFlow<List<Vehicle>>(emptyList())

        override fun observeVehicles(): Flow<List<Vehicle>> {
            return vehicles
        }

        override suspend fun refreshVehicles() {
            if (shouldFailOnRefresh) {
                throw IllegalStateException("Server error")
            }

            vehicles.value = listOf(
                Vehicle(
                    id = "vehicle_1",
                    name = "Simple One",
                    model = "Gen 2",
                    batteryPercentage = 86,
                    estimatedRangeKm = 181,
                    currentSpeedKmph = 42,
                    odometerKm = 1248,
                    isOnline = true,
                    lastUpdatedMillis = System.currentTimeMillis()
                )
            )
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