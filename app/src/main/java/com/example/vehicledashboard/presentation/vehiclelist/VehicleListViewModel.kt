package com.example.vehicledashboard.presentation.vehiclelist

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

data class VehicleListUiState(
    val vehicles: List<Vehicle> = emptyList(),
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null
)

class VehicleListViewModel(
    private val observeVehiclesUseCase: ObserveVehiclesUseCase,
    private val refreshVehiclesUseCase: RefreshVehiclesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(VehicleListUiState())
    val uiState: StateFlow<VehicleListUiState> = _uiState.asStateFlow()

    init {
        observeVehicles()
        refreshVehicles()
    }

    private fun observeVehicles() {
        viewModelScope.launch {
            observeVehiclesUseCase().collect { vehicles ->
                _uiState.value = _uiState.value.copy(
                    vehicles = vehicles,
                    isLoading = false
                )
            }
        }
    }

    fun refreshVehicles() {
        viewModelScope.launch {
            val hasExistingData = _uiState.value.vehicles.isNotEmpty()

            _uiState.value = _uiState.value.copy(
                isLoading = !hasExistingData,
                isRefreshing = hasExistingData,
                errorMessage = null
            )

            try {
                refreshVehiclesUseCase()
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Unable to load vehicles. Please try again."
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