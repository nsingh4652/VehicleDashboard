package com.example.vehicledashboard.presentation.vehicledetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehicledashboard.domain.model.Vehicle
import com.example.vehicledashboard.domain.usecase.ObserveVehiclesUseCase
import com.example.vehicledashboard.domain.usecase.RefreshVehiclesUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class VehicleDetailsUiState(
    val vehicle: Vehicle? = null,
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null
)

class VehicleDetailsViewModel(
    private val vehicleId: String,
    private val observeVehiclesUseCase: ObserveVehiclesUseCase,
    private val refreshVehiclesUseCase: RefreshVehiclesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(VehicleDetailsUiState())
    val uiState: StateFlow<VehicleDetailsUiState> = _uiState.asStateFlow()

    init {
        observeVehicle()
    }

    private fun observeVehicle() {
        viewModelScope.launch {
            observeVehiclesUseCase().collect { vehicles ->
                val vehicle = vehicles.find { it.id == vehicleId }

                _uiState.value = _uiState.value.copy(
                    vehicle = vehicle,
                    isLoading = false,
                    errorMessage = if (vehicle == null) {
                        "Vehicle not found"
                    } else {
                        null
                    }
                )
            }
        }
    }

    fun refreshVehicle() {
        viewModelScope.launch {
            val hasVehicle = _uiState.value.vehicle != null

            _uiState.value = _uiState.value.copy(
                isLoading = !hasVehicle,
                isRefreshing = hasVehicle,
                errorMessage = null
            )

            try {
                refreshVehiclesUseCase()
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Unable to refresh vehicle details."
                )
            } finally {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isRefreshing = false
                )
            }
        }
    }
}