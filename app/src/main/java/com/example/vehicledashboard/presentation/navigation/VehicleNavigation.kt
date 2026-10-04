package com.example.vehicledashboard.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vehicledashboard.VehicleDashboardApp
import com.example.vehicledashboard.presentation.vehicledetails.VehicleDetailsScreen
import com.example.vehicledashboard.presentation.vehicledetails.VehicleDetailsViewModel
import com.example.vehicledashboard.presentation.vehiclelist.VehicleListScreen
import com.example.vehicledashboard.presentation.vehiclelist.VehicleListViewModel

private const val VEHICLE_LIST_ROUTE = "vehicle_list"
private const val VEHICLE_DETAILS_ROUTE = "vehicle_details/{vehicleId}"

@Composable
fun VehicleNavigation() {

    val navController = rememberNavController()

    val application =
        LocalContext.current.applicationContext as VehicleDashboardApp

    val container = application.container

    NavHost(
        navController = navController,
        startDestination = VEHICLE_LIST_ROUTE
    ) {

        composable(VEHICLE_LIST_ROUTE) {

            val factory = object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    return VehicleListViewModel(
                        observeVehiclesUseCase =
                            container.observeVehiclesUseCase,
                        refreshVehiclesUseCase =
                            container.refreshVehiclesUseCase
                    ) as T
                }
            }

            val viewModel: VehicleListViewModel =
                viewModel(factory = factory)

            val state by viewModel.uiState.collectAsState()

            VehicleListScreen(
                state = state,
                onVehicleClick = { vehicle ->
                    navController.navigate(
                        "vehicle_details/${vehicle.id}"
                    )
                },
                onRefresh = viewModel::refreshVehicles
            )
        }

        composable(
            route = VEHICLE_DETAILS_ROUTE,
            arguments = listOf(
                navArgument("vehicleId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val vehicleId =
                backStackEntry.arguments
                    ?.getString("vehicleId")
                    .orEmpty()

            val factory = object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    return VehicleDetailsViewModel(
                        vehicleId = vehicleId,
                        observeVehiclesUseCase =
                            container.observeVehiclesUseCase,
                        refreshVehiclesUseCase =
                            container.refreshVehiclesUseCase
                    ) as T
                }
            }

            val viewModel: VehicleDetailsViewModel =
                viewModel(factory = factory)

            val state by viewModel.uiState.collectAsState()

            VehicleDetailsScreen(
                state = state,
                onBack = {
                    navController.popBackStack()
                },
                onRefresh = viewModel::refreshVehicle
            )
        }
    }
}