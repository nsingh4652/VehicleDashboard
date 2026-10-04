package com.example.vehicledashboard.presentation.vehicledetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleDetailsScreen(
    state: VehicleDetailsUiState,
    onBack: () -> Unit,
    onRefresh: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Vehicle Details")
                },
                navigationIcon = {
                    TextButton(
                        onClick = onBack
                    ) {
                        Text("Back")
                    }
                }
            )
        }
    ) { innerPadding ->

        when {

            state.isLoading && state.vehicle == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.vehicle == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.errorMessage ?: "Vehicle not found",
                            color = MaterialTheme.colorScheme.error
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Button(
                            onClick = onRefresh
                        ) {
                            Text("Retry")
                        }
                    }
                }
            }

            else -> {
                val vehicle = state.vehicle

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Text(
                        text = vehicle.name,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Text(
                        text = vehicle.model,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp)
                        ) {

                            Text(
                                text = "Battery",
                                style = MaterialTheme.typography.labelLarge
                            )

                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )

                            Text(
                                text = "${vehicle.batteryPercentage}%",
                                style = MaterialTheme.typography.headlineLarge
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            LinearProgressIndicator(
                                progress = {
                                    vehicle.batteryPercentage
                                        .coerceIn(0, 100) / 100f
                                },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )

                            Text(
                                text = "Estimated range: ${vehicle.estimatedRangeKm} km"
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        DetailCard(
                            title = "Current speed",
                            value = "${vehicle.currentSpeedKmph} km/h",
                            modifier = Modifier.weight(1f)
                        )

                        DetailCard(
                            title = "Odometer",
                            value = "${vehicle.odometerKm} km",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    DetailCard(
                        title = "Connectivity",
                        value = if (vehicle.isOnline) {
                            "Online"
                        } else {
                            "Offline"
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    DetailCard(
                        title = "Last updated",
                        value = vehicle.lastUpdated,
                        modifier = Modifier.fillMaxWidth()
                    )

                    state.errorMessage?.let { message ->
                        Text(
                            text = message,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    if (state.isRefreshing) {
                        LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Button(
                        onClick = onRefresh,
                        enabled = !state.isRefreshing,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (state.isRefreshing) {
                                "Refreshing..."
                            } else {
                                "Refresh"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}